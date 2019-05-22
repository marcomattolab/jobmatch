package it.aranciaict.jobmatch.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.DirectApplication;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.repository.DirectApplicationRepository;
import it.aranciaict.jobmatch.service.DirectApplicationService;
import it.aranciaict.jobmatch.service.DocumentService;
import it.aranciaict.jobmatch.service.MailService;
import it.aranciaict.jobmatch.service.dto.DirectApplicationDTO;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;
import it.aranciaict.jobmatch.service.dto.email.AppliedJobEmailDTO;
import it.aranciaict.jobmatch.service.mapper.DirectApplicationMapper;

/**
 * Service Implementation for managing DirectApplication.
 */
@Service
@Transactional
public class DirectApplicationServiceImpl implements DirectApplicationService {

	private static final Logger LOG = LoggerFactory.getLogger(DirectApplicationServiceImpl.class);

	private static final int DIRECT_APPLICATION_VALID_DAYS = 7;

	private final DirectApplicationRepository directApplicationRepository;

	private final DirectApplicationMapper directApplicationMapper;

	/** The candidate repository. */
	@Autowired
	private CandidateRepository candidateRepository;

	/** The company repository. */
	@Autowired
	private CompanyRepository companyRepository;

	/** The mail service */
	@Autowired
	private MailService mailService;

	/** The document service */
	@Autowired
	private DocumentService documentService;

	/**
	 * Instantiates a new direct application service impl.
	 *
	 * @param directApplicationRepository the direct application repository
	 * @param directApplicationMapper     the direct application mapper
	 */
	public DirectApplicationServiceImpl(DirectApplicationRepository directApplicationRepository,
			DirectApplicationMapper directApplicationMapper) {
		this.directApplicationRepository = directApplicationRepository;
		this.directApplicationMapper = directApplicationMapper;
	}

	/**
	 * Save a directApplication.
	 *
	 * @param directApplicationDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public DirectApplicationDTO save(DirectApplicationDTO directApplicationDTO) {
		LOG.debug("Request to save DirectApplication : {}", directApplicationDTO);
		DirectApplication directApplication = directApplicationMapper.toEntity(directApplicationDTO);
		directApplication = directApplicationRepository.save(directApplication);
		return directApplicationMapper.toDto(directApplication);
	}

	/**
	 * Get all the directApplications.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DirectApplicationDTO> findAll(Pageable pageable) {
		LOG.debug("Request to get all DirectApplications");
		return directApplicationRepository.findAll(pageable).map(directApplicationMapper::toDto);
	}

	/**
	 * Get one directApplication by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DirectApplicationDTO> findOne(Long id) {
		LOG.debug("Request to get DirectApplication : {}", id);
		return directApplicationRepository.findById(id).map(directApplicationMapper::toDto);
	}

	/**
	 * Delete the directApplication by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete DirectApplication : {}", id);
		directApplicationRepository.deleteById(id);
	}

	@Override
	public DirectApplicationDTO createAppliedJobToCompany(DirectApplicationDTO directApplicationDTO) {
		directApplicationDTO.setAppiedJobStatus(AppiedJobStatus.NEW);
		DirectApplicationDTO directApplication = this.save(directApplicationDTO);

		Candidate candidate = candidateRepository.findById(directApplicationDTO.getCandidateId()).get();
		Company company = companyRepository.findById(directApplicationDTO.getCompanyId()).get();

		AppliedJobEmailDTO emailObject = new AppliedJobEmailDTO();
		emailObject.setCandidateId(directApplicationDTO.getCandidateId());
		emailObject.setFirstName(candidate.getFirstName());
		emailObject.setLastName(candidate.getLastName());
		emailObject.addReceiverContact(company.getEmail(), company.getUser().getLangKey());
		emailObject.setDefaultLanguageKey(company.getUser().getLangKey());
		DocumentDTO cv = documentService.findCvByCandidateId(directApplicationDTO.getCandidateId()).orElse(null);
		if (cv != null) {
			emailObject.setAttachment(cv);
		}
		mailService.sendAppliedToCompanyEmail(emailObject);
		return directApplication;
	}

	/**
	 * Update old direct application.
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateOldDirectApplication() {
		LOG.info("start update Old Direct Application");
		Instant createdDateGreaterThan = Instant.now().minus(DIRECT_APPLICATION_VALID_DAYS, ChronoUnit.DAYS);
		List<DirectApplication> oldDirectAplications = directApplicationRepository
				.findAllByAppiedJobStatusEqualsAndCreatedDateGreaterThan(AppiedJobStatus.NEW, createdDateGreaterThan);
		for (DirectApplication directApplication : oldDirectAplications) {
			directApplication.setAppiedJobStatus(AppiedJobStatus.REJECTED);
			directApplicationRepository.save(directApplication);
		}

	}

}
