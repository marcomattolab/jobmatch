package it.aranciaict.jobmatch.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.jsonwebtoken.lang.Collections;
import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.JobOfferSkill;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.enumeration.ProficNumberOfYear;
import it.aranciaict.jobmatch.domain.enumeration.SkillLevelType;
import it.aranciaict.jobmatch.domain.enumeration.SkillType;
import it.aranciaict.jobmatch.domain.item.CandidateForOfferPromotion;
import it.aranciaict.jobmatch.domain.item.JobOfferToPromote;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.JobOfferRepository;
import it.aranciaict.jobmatch.repository.JobOfferSkillRepository;
import it.aranciaict.jobmatch.service.JobOfferService;
import it.aranciaict.jobmatch.service.JobOfferSkillQueryService;
import it.aranciaict.jobmatch.service.MailService;
import it.aranciaict.jobmatch.service.SkillTagQueryService;
import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.service.dto.JobOfferDTO;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillCriteria;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;
import it.aranciaict.jobmatch.service.dto.SkillTagCriteria;
import it.aranciaict.jobmatch.service.dto.SkillTagCriteria.SkillTypeFilter;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.service.dto.email.PromoteJobOffersEmailDTO;
import it.aranciaict.jobmatch.service.dto.request.ChangeJobOfferStateRequestDTO;
import it.aranciaict.jobmatch.service.dto.request.PromoteJobOffersRequestDTO;
import it.aranciaict.jobmatch.service.mapper.JobOfferMapper;
import it.aranciaict.jobmatch.service.mapper.JobOfferSkillMapper;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing JobOffer.
 */
@Service
@Transactional
public class JobOfferServiceImpl implements JobOfferService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(JobOfferServiceImpl.class);

	/** The job offer repository. */
	private final JobOfferRepository jobOfferRepository;

	/** The candidate repository. */
	private final CandidateRepository candidateRepository;

	/** The job offer mapper. */
	private final JobOfferMapper jobOfferMapper;

	/** The jobOfferSkillQueryService. */
	private final JobOfferSkillQueryService jobOfferSkillQueryService;

	/** The jobOfferSkillService. */
	private final JobOfferSkillRepository jobOfferSkillRepository;

	/** The jobOfferSkillMapper. */
	private final JobOfferSkillMapper jobOfferSkillMapper;

	/** The SkillTagQueryService. */
	private final SkillTagQueryService skillTagQueryService;

	/** The skillTagService. */
	private final SkillTagService skillTagService;

	/** Mail Service */
	private final MailService mailService;

	/**
	 * Instantiates a new job offer service impl.
	 *
	 * @param jobOfferRepository        the job offer repository
	 * @param jobOfferMapper            the job offer mapper
	 * @param jobOfferSkillQueryService the jobOfferSkillQueryService
	 * @param jobOfferSkillRepository   the jobOfferSkillRepository
	 * @param skillTagQueryService      the skillTagQueryService
	 * @param skillTagService           the skillTagService
	 * @param jobOfferSkillMapper       the jobOfferSkillMapper
	 * @param candidateRepository       the candidateRepository
	 * @param mailService               the mailService
	 * 
	 */
	@SuppressWarnings("checkstyle:parameterNumber")
	public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, JobOfferMapper jobOfferMapper,
			JobOfferSkillQueryService jobOfferSkillQueryService, JobOfferSkillRepository jobOfferSkillRepository,
			SkillTagQueryService skillTagQueryService, SkillTagService skillTagService,
			JobOfferSkillMapper jobOfferSkillMapper, CandidateRepository candidateRepository, MailService mailService) {
		this.jobOfferRepository = jobOfferRepository;
		this.jobOfferMapper = jobOfferMapper;
		this.jobOfferSkillQueryService = jobOfferSkillQueryService;
		this.jobOfferSkillRepository = jobOfferSkillRepository;
		this.skillTagQueryService = skillTagQueryService;
		this.skillTagService = skillTagService;
		this.jobOfferSkillMapper = jobOfferSkillMapper;
		this.candidateRepository = candidateRepository;
		this.mailService = mailService;
	}

	/**
	 * Save a jobOffer.
	 *
	 * @param jobOfferDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public JobOfferDTO save(JobOfferDTO jobOfferDTO) {
		log.debug("Request to save JobOffer : {}", jobOfferDTO);
		JobOffer jobOffer = jobOfferMapper.toEntity(jobOfferDTO);

		if (!Collections.isEmpty(jobOfferDTO.getSkills())) {
			List<JobOfferSkill> previousSkills = new ArrayList<JobOfferSkill>();
			if (jobOffer.getId() != null && jobOffer.getId() > 0) {
				final JobOfferSkillCriteria criteriaSkill = new JobOfferSkillCriteria();
				LongFilter idFilter = new LongFilter();
				idFilter.setEquals(jobOffer.getId());
				criteriaSkill.setJobOfferId(idFilter);
				previousSkills = jobOfferSkillQueryService.findByCriteriaEntities(criteriaSkill);
			} else {
				jobOffer = jobOfferRepository.save(jobOffer);
			}

			final SkillTagCriteria skillTagCriteria = new SkillTagCriteria();
			SkillTypeFilter skillFilter = new SkillTypeFilter();
			skillFilter.setEquals(SkillType.OTHER);
			for (JobOfferSkillDTO dto : jobOfferDTO.getSkills()) {
				handleJobOfferSkill(jobOffer, previousSkills, skillTagCriteria, dto);
			}

			if (jobOffer.getId() != null && jobOffer.getId() > 0) {
				jobOffer = jobOfferRepository.save(jobOffer);
			}
		} else {
			jobOffer = jobOfferRepository.save(jobOffer);
		}

		return jobOfferMapper.toDto(jobOffer);
	}

	/**
	 * Handle job offer skill.
	 *
	 * @param jobOffer         the job offer
	 * @param previousSkills   the previous skills
	 * @param skillTagCriteria the skill tag criteria
	 * @param dto              the dto
	 */
	private void handleJobOfferSkill(JobOffer jobOffer, List<JobOfferSkill> previousSkills,
			final SkillTagCriteria skillTagCriteria, JobOfferSkillDTO dto) {
		if (!StringUtils.isBlank(dto.getSkillTagName())) {
			JobOfferSkillDTO newSkillDTO = new JobOfferSkillDTO();
			newSkillDTO.setJobOfferId(jobOffer.getId());
			newSkillDTO.setLevel(SkillLevelType.BEGINNER);
			newSkillDTO.setProficNumberOfYear(ProficNumberOfYear.ZERO_TO_ONE);

			Optional<JobOfferSkill> matchedSkill = previousSkills.stream()
					.filter(skill -> skill.getSkillTag().getName().equals(dto.getSkillTagName())).findFirst();

			if (matchedSkill.isPresent()) {
				jobOffer.getSkills().add(matchedSkill.get());
				return;
			}

			StringFilter nameFilter = new StringFilter();
			nameFilter.setEquals(dto.getSkillTagName());
			skillTagCriteria.setName(nameFilter);
			List<SkillTagDTO> skillTags = skillTagQueryService.findByCriteria(skillTagCriteria);
			SkillTagDTO skillTagDTO = null;
			if (!Collections.isEmpty(skillTags)) {
				skillTagDTO = skillTags.get(0);
				newSkillDTO.setSkillTagId(skillTagDTO.getId());
			} else {
				skillTagDTO = new SkillTagDTO();
				skillTagDTO.setName(dto.getSkillTagName());
				skillTagDTO.setType(SkillType.OTHER);
				newSkillDTO.setSkillTagId(skillTagService.save(skillTagDTO).getId());
			}

			if (jobOffer.getId() != null && jobOffer.getId() > 0) {
				jobOffer.getSkills().add(jobOfferSkillRepository.save(jobOfferSkillMapper.toEntity(newSkillDTO)));
			}
		}
	}

	@Override
	public boolean promoteJobOffers(PromoteJobOffersRequestDTO promoteRequest) {
		List<JobOfferToPromote> jobOfferToPromote = jobOfferRepository
				.findAllJobOfferToPromoteByIds(promoteRequest.getJobOffersId());
		List<CandidateForOfferPromotion> candidateToNotify = candidateRepository
				.findAllCandidatesForJobOffersPromotionByIds(promoteRequest.getCandidatesId());

		if ((jobOfferToPromote == null || jobOfferToPromote.size() == 0)
				&& (candidateToNotify == null || candidateToNotify.size() == 0)) {
			throw new BadRequestAlertException("Job offer to promote ids or candidates ids not valid", "JOB_OFFER",
					"idnull");
		}

		final Map<String, List<CandidateForOfferPromotion>> candidateGroupedByLang = candidateToNotify.stream()
				.collect(Collectors.groupingBy(cand -> cand.getLangKey()));

		promoteRequest.getCustomMessages().forEach(customMessage -> {
			if (candidateGroupedByLang.containsKey(customMessage.getLangKey())) {
				List<CandidateForOfferPromotion> currentLangCand = candidateGroupedByLang
						.get(customMessage.getLangKey());
				PromoteJobOffersEmailDTO emailObject = new PromoteJobOffersEmailDTO(customMessage.getCustomMessage(),
						jobOfferToPromote);
				emailObject.setDefaultLanguageKey(customMessage.getLangKey());
				for (CandidateForOfferPromotion curr : currentLangCand) {
					emailObject.addReceiverContact(curr.getEmail(), customMessage.getLangKey());
				}

				mailService.sendPromoteJobOffersEmail(emailObject);
			} else {
				log.warn("Lang " + customMessage.getLangKey() + " isnt appearing in the candidate list");
			}
		});

		return true;
	}

	/**
	 * Get all the jobOffers.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<JobOfferDTO> findAll(Pageable pageable) {
		log.debug("Request to get all JobOffers");
		return jobOfferRepository.findAll(pageable).map(jobOfferMapper::toDto);
	}

	/**
	 * Get one jobOffer by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<JobOfferDTO> findOne(Long id) {
		log.debug("Request to get JobOffer : {}", id);
		return jobOfferRepository.findById(id).map(jobOfferMapper::toDto);
	}

	/**
	 * Delete the jobOffer by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete JobOffer : {}", id);
		jobOfferRepository.deleteById(id);
	}

	@Override
	public JobOfferDTO changeStatus(ChangeJobOfferStateRequestDTO changeJobOfferStateRequestDTO) {
		log.debug("Request to change state of JobOffer : {}", changeJobOfferStateRequestDTO);
		JobOffer jobOffer = jobOfferRepository.findById(changeJobOfferStateRequestDTO.getJobOfferId()).orElse(null);
		jobOffer.setStatus(changeJobOfferStateRequestDTO.getStatus());
		if (JobOfferStatus.ACTIVE.equals(changeJobOfferStateRequestDTO.getStatus())) {
			jobOffer.setStartDate(LocalDate.now());
		}
		jobOffer = jobOfferRepository.save(jobOffer);
		return jobOfferMapper.toDto(jobOffer);
	}

}
