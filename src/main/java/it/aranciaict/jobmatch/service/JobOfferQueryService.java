package it.aranciaict.jobmatch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.Candidate;
// for static metamodels
import it.aranciaict.jobmatch.domain.CompanySector_;
import it.aranciaict.jobmatch.domain.Company_;
import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.JobOfferSkill;
import it.aranciaict.jobmatch.domain.JobOfferSkill_;
import it.aranciaict.jobmatch.domain.JobOffer_;
import it.aranciaict.jobmatch.domain.Project_;
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.item.JobOfferInfo;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.JobOfferRepository;
import it.aranciaict.jobmatch.repository.SkillRepository;
import it.aranciaict.jobmatch.service.dto.JobOfferCriteria;
import it.aranciaict.jobmatch.service.dto.JobOfferCriteria.JobOfferStatusFilter;
import it.aranciaict.jobmatch.service.dto.JobOfferDTO;
import it.aranciaict.jobmatch.service.mapper.JobOfferInfoMapper;

/**
 * Service for executing complex queries for JobOffer entities in the database.
 * The main input is a {@link JobOfferCriteria} which gets converted to
 * {@link Specification}, in a way that all the filters must apply. It returns a
 * {@link List} of {@link JobOfferDTO} or a {@link Page} of {@link JobOfferDTO}
 * which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobOfferQueryService extends QueryService<JobOffer> {

	private static final int NUMBER_OF_SUGGESTED_JOB_OFFER = 6;

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(JobOfferQueryService.class);

	/** The job offer repository. */
	private final JobOfferRepository jobOfferRepository;

//	/** The job offer mapper. */
//	private final JobOfferMapper jobOfferMapper;

	/** The job offer info mapper. */
	private final JobOfferInfoMapper jobOfferInfoMapper;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private SkillRepository skillRepository;

	/**
	 * Instantiates a new job offer query service.
	 *
	 * @param jobOfferRepository the job offer repository
	 * @param jobOfferInfoMapper the job offer info mapper
	 */
	public JobOfferQueryService(JobOfferRepository jobOfferRepository, JobOfferInfoMapper jobOfferInfoMapper) {
		this.jobOfferRepository = jobOfferRepository;
		this.jobOfferInfoMapper = jobOfferInfoMapper;
	}

	/**
	 * Return a {@link List} of {@link JobOfferDTO} which matches the criteria from
	 * the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<JobOfferDTO> findByCriteria(JobOfferCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<JobOffer> specification = createSpecification(criteria);
		return jobOfferInfoMapper.toDto(jobOfferRepository.findAll(specification, JobOfferInfo.class));
	}

	/**
	 * Return a {@link Page} of {@link JobOfferDTO} which matches the criteria from
	 * the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<JobOfferDTO> findByCriteria(JobOfferCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<JobOffer> specification = createSpecification(criteria);
		return jobOfferRepository.findAll(specification, JobOfferInfo.class, page).map(jobOfferInfoMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(JobOfferCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<JobOffer> specification = createSpecification(criteria);
		return jobOfferRepository.count(specification);
	}

	/**
	 * Return a {@link Page} of {@link JobOfferDTO} which matches the criteria from
	 * the database.
	 *
	 * @param candidateId the candidate id
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<JobOfferDTO> findSuggestedByCandidateId(Long candidateId) {
		log.debug("find suggested for candidate : {} ", candidateId);
		List<JobOfferDTO> list = new ArrayList<JobOfferDTO>();
		Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
		JobOfferCriteria criteria = createCriteriaForForCandidateSuggestedOffers(candidate);

		if (criteria.getSkillTagId() != null && !CollectionUtils.isEmpty(criteria.getSkillTagId().getIn())) {
			final Specification<JobOffer> specification = createSpecification(criteria);
			PageRequest pageable = PageRequest.of(0, NUMBER_OF_SUGGESTED_JOB_OFFER,
					new Sort(Direction.DESC, "startDate"));
			Page<JobOfferDTO> page = jobOfferRepository.findAll(specification, JobOfferInfo.class, pageable)
					.map(jobOfferInfoMapper::toDto);
			list.addAll(page.getContent());
		}
		return list;
	}

	/**
	 * Creates the criteria for for candidate suggested offers.
	 *
	 * @param candidate the candidate
	 * @return the job offer criteria
	 */
	private JobOfferCriteria createCriteriaForForCandidateSuggestedOffers(Candidate candidate) {
		JobOfferCriteria criteria = new JobOfferCriteria();

		// Skill tag condition
		List<Long> tagIds = skillRepository.findAllTagIdByCandidateId(candidate.getId());
		LongFilter skillTagFilter = new LongFilter();
		skillTagFilter.setIn(tagIds);
		criteria.setSkillTagId(skillTagFilter);
		criteria.setMacthAllSkillTag(false);

//		if (!CollectionUtils.isEmpty(candidate.getSkills())) {
//			List<Long> tagIds = candidate.getSkills().stream().map(s -> s.getSkillTag().getId())
//					.collect(Collectors.toList());
//			LongFilter skillTagFilter = new LongFilter();
//			skillTagFilter.setIn(tagIds);
//			criteria.setSkillTagId(skillTagFilter);
//			criteria.setMacthAllSkillTag(false);
//		}

		// Status = ACTIVE
		JobOfferStatusFilter jobOfferStatusFilter = new JobOfferStatusFilter();
		jobOfferStatusFilter.setEquals(JobOfferStatus.ACTIVE);
		criteria.setStatus(jobOfferStatusFilter);

		return criteria;
	}

	/**
	 * Function to convert JobOfferCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	@SuppressWarnings("checkstyle:executablestatementcount")
	private Specification<JobOffer> createSpecification(JobOfferCriteria criteria) {
		Specification<JobOffer> specification = Specification.where(null);
		if (criteria != null) {

			if (criteria.getKeyWord() != null) {
				List<String> keyWords = getKeyWords(criteria.getKeyWord());
				Specification<JobOffer> jobTitleKeyWordsSpecification = Specification.where(null);
				for (String keyWord : keyWords) {
					jobTitleKeyWordsSpecification.or(getJobTitleKeyWordSpecification(keyWord));
				}
				specification = specification.and(jobTitleKeyWordsSpecification);
			} else {
				if (criteria.getId() != null) {
					specification = specification.and(buildSpecification(criteria.getId(), JobOffer_.id));
				}
				if (criteria.getStartDate() != null) {
					specification = specification
							.and(buildRangeSpecification(criteria.getStartDate(), JobOffer_.startDate));
				}
				if (criteria.getCreatedDate() != null) {
					specification = specification
							.and(buildRangeSpecification(criteria.getCreatedDate(), JobOffer_.createdDate));
				}
				if (criteria.getJobTitle() != null) {
					specification = specification
							.and(buildStringSpecification(criteria.getJobTitle(), JobOffer_.jobTitle));
				}
				if (criteria.getJobCity() != null) {
					specification = specification
							.and(buildStringSpecification(criteria.getJobCity(), JobOffer_.jobCity));
				}
				if (criteria.getJobCountry() != null) {
					specification = specification
							.and(buildSpecification(criteria.getJobCountry(), JobOffer_.jobCountry));
				}
				if (criteria.getEmploymentType() != null) {
					specification = specification
							.and(buildSpecification(criteria.getEmploymentType(), JobOffer_.employmentType));
				}
				if (criteria.getSeniorityLevel() != null) {
					specification = specification
							.and(buildSpecification(criteria.getSeniorityLevel(), JobOffer_.seniorityLevel));
				}
				if (criteria.getSalaryOffered() != null) {
					specification = specification
							.and(buildStringSpecification(criteria.getSalaryOffered(), JobOffer_.salaryOffered));
				}
				if (criteria.getStatus() != null) {
					specification = specification.and(buildSpecification(criteria.getStatus(), JobOffer_.status));
				}
				if (criteria.getEnabled() != null) {
					specification = specification.and(buildSpecification(criteria.getEnabled(), JobOffer_.enabled));
				}
				if (criteria.getCompanyId() != null) {
					specification = specification.and(buildSpecification(criteria.getCompanyId(),
							root -> root.join(JobOffer_.company, JoinType.LEFT).get(Company_.id)));
				}
				if (criteria.getSectorId() != null) {
					specification = specification.and(buildSpecification(criteria.getSectorId(),
							root -> root.join(JobOffer_.sector, JoinType.LEFT).get(CompanySector_.id)));
				}
				if (criteria.getProjectId() != null) {
					specification = specification.and(buildSpecification(criteria.getProjectId(),
							root -> root.join(JobOffer_.project, JoinType.LEFT).get(Project_.id)));
				}
				if (criteria.getJobOfferType() != null) {
					specification = specification
							.and(buildSpecification(criteria.getJobOfferType(), JobOffer_.jobOfferType));
				}
				if (criteria.getSkillTagId() != null) {
					if (!criteria.isMacthAllSkillTag()) {
						specification = specification.and(getSkillTagSpecification(criteria.getSkillTagId()));
					} else {
						if (criteria.getSkillTagId().getEquals() != null) {
							specification = specification.and(getSkillTagSpecification(criteria.getSkillTagId()));
						} else if (criteria.getSkillTagId().getIn() != null) {
							for (Long skillTagId : criteria.getSkillTagId().getIn()) {
								LongFilter skillTagIdEqualsFilter = new LongFilter();
								skillTagIdEqualsFilter.setEquals(skillTagId);
								specification = specification.and(getSkillTagSpecification(skillTagIdEqualsFilter));
							}
						}
					}
				}
			}
		}
		return specification;
	}

	/**
	 * Gets the key words.
	 *
	 * @param keyWordsFilter the key words filter
	 * @return the key words
	 */
	public List<String> getKeyWords(StringFilter keyWordsFilter) {
		List<String> keyWords = new ArrayList<String>();
		if (keyWordsFilter.getEquals() != null) {
			keyWords.addAll(Arrays.asList(StringUtils.split(keyWordsFilter.getEquals())));
		} else if (keyWordsFilter.getIn() != null) {
			for (String keyWord : keyWordsFilter.getIn()) {
				keyWords.addAll(Arrays.asList(StringUtils.split(keyWord)));
			}
		}
		return keyWords;
	}

	/**
	 * Gets the key word specification.
	 *
	 * @param keyWord the key word
	 * @return the key word specification
	 */
	public Specification<JobOffer> getJobTitleKeyWordSpecification(String keyWord) {
		return (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.like(root.get(JobOffer_.jobTitle), wrapLikeQuery(keyWord));
			query.distinct(true);
			return predicate;
		};
	}

	/**
	 * Gets the skill tag specification.
	 *
	 * @param skillTagId the skill tag id
	 * @return the skill tag specification
	 */
	public Specification<JobOffer> getSkillTagSpecification(LongFilter skillTagId) {
		return (root, query, criteriaBuilder) -> {
			Join<JobOfferSkill, SkillTag> skillTagJoin = root.join(JobOffer_.skills, JoinType.LEFT)
					.join(JobOfferSkill_.skillTag);
			Predicate predicate = null;
			if (skillTagId.getEquals() != null) {
				predicate = criteriaBuilder.equal(skillTagJoin.get(SkillTag_.id), skillTagId.getEquals());
			} else if (skillTagId.getIn() != null) {
				predicate = skillTagJoin.get(SkillTag_.id).in(skillTagId.getIn());
			}
			query.distinct(true);
			return predicate;
		};
	}

}
