package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.repository.AppliedJobRepository;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.JobOfferRepository;
import it.aranciaict.jobmatch.service.AppliedJobService;
import it.aranciaict.jobmatch.service.DocumentService;
import it.aranciaict.jobmatch.service.MailService;
import it.aranciaict.jobmatch.service.dto.AppliedJobDTO;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;
import it.aranciaict.jobmatch.service.dto.email.AppliedJobEmailDTO;
import it.aranciaict.jobmatch.service.dto.request.ApplyToJobOfferRequestDTO;
import it.aranciaict.jobmatch.service.exceptions.AppliedJobAlreadyExistException;
import it.aranciaict.jobmatch.service.mapper.AppliedJobEmailMapper;
import it.aranciaict.jobmatch.service.mapper.AppliedJobMapper;

/**
 * Service Implementation for managing AppliedJob.
 */
@Service
@Transactional
public class AppliedJobServiceImpl implements AppliedJobService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(AppliedJobServiceImpl.class);

	/** The applied job repository. */
	private final AppliedJobRepository appliedJobRepository;

	/** The applied job mapper. */
	private final AppliedJobMapper appliedJobMapper;

	/** The applied job email mapper. */
	private final AppliedJobEmailMapper appliedJobEmailMapper;

	/** The job offer repository. */
	@Autowired
	private JobOfferRepository jobOfferRepository;

	/** The candidate repository. */
	@Autowired
	private CandidateRepository candidateRepository;

	/** The mail service */
	@Autowired
	private MailService mailService;

	/** The mail service */
	@Autowired
	private DocumentService documentService;

	/**
	 * Instantiates a new applied job service impl.
	 *
	 * @param appliedJobRepository  the applied job repository
	 * @param appliedJobMapper      the applied job mapper
	 * @param appliedJobEmailMapper the applied job email mapper
	 */
	public AppliedJobServiceImpl(AppliedJobRepository appliedJobRepository, AppliedJobMapper appliedJobMapper,
			AppliedJobEmailMapper appliedJobEmailMapper) {
		this.appliedJobRepository = appliedJobRepository;
		this.appliedJobMapper = appliedJobMapper;
		this.appliedJobEmailMapper = appliedJobEmailMapper;
	}

	/**
	 * Save a appliedJob.
	 *
	 * @param appliedJobDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public AppliedJobDTO save(AppliedJobDTO appliedJobDTO) {
		log.debug("Request to save AppliedJob : {}", appliedJobDTO);
		AppliedJob appliedJob = appliedJobMapper.toEntity(appliedJobDTO);
		appliedJob = appliedJobRepository.save(appliedJob);
		return appliedJobMapper.toDto(appliedJob);
	}

	/**
	 * Get all the appliedJobs.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<AppliedJobDTO> findAll(Pageable pageable) {
		log.debug("Request to get all AppliedJobs");
		return appliedJobRepository.findAll(pageable).map(appliedJobMapper::toDto);
	}

	/**
	 * Get one appliedJob by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AppliedJobDTO> findOne(Long id) {
		log.debug("Request to get AppliedJob : {}", id);
		return appliedJobRepository.findById(id).map(appliedJobMapper::toDto);
	}

	/**
	 * Delete the appliedJob by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete AppliedJob : {}", id);
		appliedJobRepository.deleteById(id);
	}

	@Override
	public AppliedJobDTO createAppliedJob(@Valid ApplyToJobOfferRequestDTO applyToJobOfferRequestDTO)
			throws AppliedJobAlreadyExistException {
		log.debug("Request to create AppliedJob : {}", applyToJobOfferRequestDTO);
		Long candidateId = applyToJobOfferRequestDTO.getCandidateId();
		Long jobOfferId = applyToJobOfferRequestDTO.getJobOfferId();
		checkExistAppliedJob(jobOfferId, candidateId);
		AppliedJob appliedJob = new AppliedJob();
		Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
		appliedJob.setCandidate(candidate);
		JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElse(null);
		appliedJob.setJobOffer(jobOffer);
		appliedJob = appliedJobRepository.save(appliedJob);
		AppliedJobEmailDTO emailObject = appliedJobEmailMapper.toDto(appliedJob);
		DocumentDTO cv = documentService.findCvByCandidateId(candidateId).orElse(null);
		if (cv != null) {
			emailObject.setAttachment(cv);
		}
		mailService.sendAppliedToOfferEmail(emailObject);
		return appliedJobMapper.toDto(appliedJob);
	}

	/**
	 * Check exist applied job.
	 *
	 * @param jobOfferId  the job offer id
	 * @param candidateId the candidate id
	 * @throws AppliedJobAlreadyExistException the applied job already exist
	 *                                         exception
	 */
	private void checkExistAppliedJob(Long jobOfferId, Long candidateId) throws AppliedJobAlreadyExistException {
		long count = appliedJobRepository.countByJobOfferIdAndCandidateId(jobOfferId, candidateId);
		if (count > 0) {
			throw new AppliedJobAlreadyExistException("Applied job already exist");
		}
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @return the appliedJobRepository
	 */
	public AppliedJobRepository getAppliedJobRepository() {
		return appliedJobRepository;
	}

	/**
	 * @return the appliedJobMapper
	 */
	public AppliedJobMapper getAppliedJobMapper() {
		return appliedJobMapper;
	}

	/**
	 * @return the appliedJobEmailMapper
	 */
	public AppliedJobEmailMapper getAppliedJobEmailMapper() {
		return appliedJobEmailMapper;
	}

	/**
	 * @return the jobOfferRepository
	 */
	public JobOfferRepository getJobOfferRepository() {
		return jobOfferRepository;
	}

	/**
	 * @param jobOfferRepository the jobOfferRepository to set
	 */
	public void setJobOfferRepository(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

	/**
	 * @return the candidateRepository
	 */
	public CandidateRepository getCandidateRepository() {
		return candidateRepository;
	}

	/**
	 * @param candidateRepository the candidateRepository to set
	 */
	public void setCandidateRepository(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

//    @Override
//    public void createAppliedJobToCompany(ApplyToCompanyDTO request) {
//        Candidate candidate = candidateRepository.findById(request.getCandidateId()).get();
//        Company company = companyRepository.findById(request.getCompanyId()).get();
//        DocumentDTO cv = documentMapper.toDto(documentRepository.findByCandidateIdAndDocumentType(request.getCandidateId(), DocumentType.CV));
//        AppliedJobEmailDTO emailObject = new AppliedJobEmailDTO();
//        emailObject.setCandidateId(request.getCandidateId());
//        emailObject.setFirstName(candidate.getFirstName());
//        emailObject.setLastName(candidate.getLastName());
//        emailObject.setEmail(company.getEmail());
//        emailObject.setLangKey(company.getUser().getLangKey());
//        emailObject.setAttachment(cv);
//        mailService.sendAppliedToCompanyEmail(emailObject);
//    }

}
