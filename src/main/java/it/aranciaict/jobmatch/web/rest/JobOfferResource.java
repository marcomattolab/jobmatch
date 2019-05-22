package it.aranciaict.jobmatch.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.ResponseUtil;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.security.SecurityUtils;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.JobOfferQueryService;
import it.aranciaict.jobmatch.service.JobOfferService;
import it.aranciaict.jobmatch.service.dto.JobOfferCriteria;
import it.aranciaict.jobmatch.service.dto.JobOfferCriteria.JobOfferStatusFilter;
import it.aranciaict.jobmatch.service.dto.JobOfferDTO;
import it.aranciaict.jobmatch.service.dto.request.ChangeJobOfferStateRequestDTO;
import it.aranciaict.jobmatch.service.dto.request.PromoteJobOffersCustomMessageDTO;
import it.aranciaict.jobmatch.service.dto.request.PromoteJobOffersRequestDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing JobOffer.
 */
@RestController
@RequestMapping("/api/job-offers")
public class JobOfferResource {

	private static final int DAYS_FROM_START = 7;
	private static final int JOB_OFFERS_PAGE_FOR_COMPANY = 20;

	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(JobOfferResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "jobOffer";

	/** The job offer service. */
	private final JobOfferService jobOfferService;

	/** The job offer query service. */
	private final JobOfferQueryService jobOfferQueryService;

	/** The company service. */
	private final CompanyService companyService;

	/** The message source. */
	private final MessageSource messageSource;

	/**
	 * Instantiates a new job offer resource.
	 *
	 * @param jobOfferService      the job offer service
	 * @param jobOfferQueryService the job offer query service
	 * @param companyService       the company service
	 * @param messageSource the message source
	 */
	public JobOfferResource(JobOfferService jobOfferService, JobOfferQueryService jobOfferQueryService,
			CompanyService companyService, MessageSource messageSource) {
		this.jobOfferService = jobOfferService;
		this.jobOfferQueryService = jobOfferQueryService;
		this.companyService = companyService;
		this.messageSource = messageSource;
	}

	/**
	 * POST /job-offers : Create a new jobOffer.
	 *
	 * @param jobOfferDTO the jobOfferDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         jobOfferDTO, or with status 400 (Bad Request) if the jobOffer has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<JobOfferDTO> createJobOffer(@Valid @RequestBody JobOfferDTO jobOfferDTO)
			throws URISyntaxException {
		LOG.debug("REST request to save JobOffer : {}", jobOfferDTO);
		if (jobOfferDTO.getId() != null) {
			throw new BadRequestAlertException("A new jobOffer cannot already have an ID", ENTITY_NAME, "idexists");
		}
		JobOfferDTO result = jobOfferService.save(jobOfferDTO);
		return ResponseEntity.created(new URI("/api/job-offers/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /job-offers : Updates an existing jobOffer.
	 *
	 * @param jobOfferDTO the jobOfferDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         jobOfferDTO, or with status 400 (Bad Request) if the jobOfferDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         jobOfferDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<JobOfferDTO> updateJobOffer(@Valid @RequestBody JobOfferDTO jobOfferDTO)
			throws URISyntaxException {
		LOG.debug("REST request to update JobOffer : {}", jobOfferDTO);
		if (jobOfferDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		JobOfferDTO result = jobOfferService.save(jobOfferDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobOfferDTO.getId().toString())).body(result);
	}

	/**
	 * Change job offer state.
	 *
	 * @param changeJobOfferStateRequestDTO the change job offer state request DTO
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/changeStatus")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<JobOfferDTO> changeJobOfferStatus(
			@Valid @RequestBody ChangeJobOfferStateRequestDTO changeJobOfferStateRequestDTO) throws URISyntaxException {
		LOG.debug("REST request to update state of JobOffer : {}", changeJobOfferStateRequestDTO);
		JobOfferDTO result = jobOfferService.changeStatus(changeJobOfferStateRequestDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * GET /job-offers : get all the jobOffers.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of jobOffers in
	 *         body
	 */
	@GetMapping("")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(JobOfferCriteria criteria, Pageable pageable) {
		LOG.debug("REST request to get JobOffers by criteria: {}", criteria);
		applyCriteriaProfilazioneJobOffers(criteria);
		Page<JobOfferDTO> page = jobOfferQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-offers");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /job-offers : get all the jobOffers.
	 *
	 * @param companyId the company id
	 * @return the ResponseEntity with status 200 (OK) and the list of jobOffers in
	 *         body
	 */
	@GetMapping("/company/{companyId}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.COMPANY + "') or hasRole('"
			+ AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(Long companyId) {
		LOG.debug("REST request to get JobOffers by companyId: {}", companyId);
		JobOfferCriteria criteria = new JobOfferCriteria();
		LongFilter companyFilter = new LongFilter();
		companyFilter.setEquals(companyId);
		criteria.setCompanyId(companyFilter);
		JobOfferStatusFilter statusFilter = new JobOfferStatusFilter();
		statusFilter.setEquals(JobOfferStatus.ACTIVE);
		criteria.setStatus(statusFilter);
		Page<JobOfferDTO> page = jobOfferQueryService.findByCriteria(criteria,
				PageRequest.of(0, JOB_OFFERS_PAGE_FOR_COMPANY));
		return ResponseEntity.ok().body(page.getContent());
	}

	/**
	 * GET /job-offers : get all the jobOffers.
	 *
	 * @param candidateId the candidate id
	 * @return the ResponseEntity with status 200 (OK) and the list of jobOffers in
	 *         body
	 */
	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
	@GetMapping("/suggested/{candidateId}")
	public ResponseEntity<List<JobOfferDTO>> getSuggestedJobOffers(@PathVariable Long candidateId) {
		LOG.debug("REST request to get Suggested JobOffers for candidate: {}", candidateId);
		List<JobOfferDTO> list = jobOfferQueryService.findSuggestedByCandidateId(candidateId);
		return ResponseEntity.ok().body(list);
	}

	/**
	 * GET /job-offers/count : count all the jobOffers.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/count")
	public ResponseEntity<Long> countJobOffers(JobOfferCriteria criteria) {
		LOG.debug("REST request to count JobOffers by criteria: {}", criteria);
		applyCriteriaProfilazioneJobOffers(criteria);
		return ResponseEntity.ok().body(jobOfferQueryService.countByCriteria(criteria));
	}

	/**
	 * Apply criteria profilazione job offers.
	 *
	 * @param criteria the criteria
	 */
	private void applyCriteriaProfilazioneJobOffers(JobOfferCriteria criteria) {
		if (criteria.isSearchMode()) {
			JobOfferStatusFilter statusFilter = new JobOfferStatusFilter();
			statusFilter.setEquals(JobOfferStatus.ACTIVE);
			criteria.setStatus(statusFilter);
		} else {
			if (SecurityUtils.isCurrentUserCompany() || SecurityUtils.isCurrentUserSponsoringInstitution()) {
				Long companyId = companyService.findCompanyIdByCurrentUser().orElse(null);
				if (companyId == null) {
					throw new BadRequestAlertException(
							"Nessuna Azienda associata allo User " + SecurityUtils.getCurrentUserLogin(), ENTITY_NAME,
							"companyByCurrentUser.notFound");
				}
				LongFilter companyFilter = new LongFilter();
				companyFilter.setEquals(companyId);
				criteria.setCompanyId(companyFilter);
			}
		}
	}

	/**
	 * GET /job-offers/:id : get the "id" jobOffer.
	 *
	 * @param id the id of the jobOfferDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         jobOfferDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<JobOfferDTO> getJobOffer(@PathVariable Long id) {
		LOG.debug("REST request to get JobOffer : {}", id);
		Optional<JobOfferDTO> jobOfferDTO = jobOfferService.findOne(id);
		return ResponseUtil.wrapOrNotFound(jobOfferDTO);
	}

	/**
	 * DELETE /job-offers/:id : delete the "id" jobOffer.
	 *
	 * @param id the id of the jobOfferDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY + "')")
	public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
		LOG.debug("REST request to delete JobOffer : {}", id);
		jobOfferService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * GET /newJobOffer/{companyId} : count all the appliedJobs.
	 *
	 * @param companyId the company id
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/newJobOfferCount/{companyId}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.COMPANY + "')")
	public ResponseEntity<Long> countNewJobOffersByCompanyId(@PathVariable Long companyId) {
		LOG.debug("REST request to count new Job Offers by companyId: {}", companyId);

		JobOfferCriteria criteria = new JobOfferCriteria();
		// Offerte di lavoro appartenenti all'azienda (companyId)
		LongFilter companyIdFilter = new LongFilter();
		companyIdFilter.setEquals(companyId);
		criteria.setCompanyId(companyIdFilter);
		// Offerte di lavoro create nell'ultima settimana
		LocalDateFilter startDateFilter = new LocalDateFilter();
		LocalDate now = LocalDate.now();
		startDateFilter.setGreaterOrEqualThan(now.minus(DAYS_FROM_START, ChronoUnit.DAYS));
		criteria.setStartDate(startDateFilter);
		return ResponseEntity.ok().body(jobOfferQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /newJobOffer/{companyId} : count all the appliedJobs.
	 *
	 * @param companyId the company id
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/activeJobOffersCount/{companyId}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.COMPANY + "')")
	public ResponseEntity<Long> activeJobOffersCountByCompanyId(@PathVariable Long companyId) {
		LOG.debug("REST request to count Active Job Offers by companyId: {}", companyId);

		JobOfferCriteria criteria = new JobOfferCriteria();
		// Offerte di lavoro appartenenti all'azienda (companyId)
		LongFilter companyIdFilter = new LongFilter();
		companyIdFilter.setEquals(companyId);
		criteria.setCompanyId(companyIdFilter);
		// Offerte di lavoro attive
		JobOfferStatusFilter statusFilter = new JobOfferStatusFilter();
		statusFilter.setEquals(JobOfferStatus.ACTIVE);
		criteria.setStatus(statusFilter);
		return ResponseEntity.ok().body(jobOfferQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /activeJobOffersCount : count all active JOB OFFERS.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/activeJobOffersCount")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<Long> allActiveJobOffersCount() {
		LOG.debug("REST request to count Active Job Offers");

		JobOfferCriteria criteria = new JobOfferCriteria();
		JobOfferStatusFilter statusFilter = new JobOfferStatusFilter();
		statusFilter.setEquals(JobOfferStatus.ACTIVE);
		criteria.setStatus(statusFilter);
		return ResponseEntity.ok().body(jobOfferQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /activeJobOffersCount : count all active JOB OFFERS.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/nonDraftJobOffersCount")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<Long> allNonDraftJobOffersCount() {
		LOG.debug("REST request to count Active Job Offers");

		JobOfferCriteria criteria = new JobOfferCriteria();
		JobOfferStatusFilter statusFilter = new JobOfferStatusFilter();
		statusFilter.setIn(Arrays.asList(JobOfferStatus.ACTIVE, JobOfferStatus.ENDED));
		criteria.setStatus(statusFilter);
		return ResponseEntity.ok().body(jobOfferQueryService.countByCriteria(criteria));
	}
	

	/**
	 * GET /newJobOffer : count all the jobs offer in a week.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/newJobOfferCount")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
	public ResponseEntity<Long> countWeeklyJobOffers() {
		LOG.debug("REST request to count new Job Offers");

		JobOfferCriteria criteria = new JobOfferCriteria();
		// Offerte di lavoro create nell'ultima settimana
		LocalDateFilter startDateFilter = new LocalDateFilter();
		LocalDate now = LocalDate.now();
//		startDateFilter.setLessOrEqualThan(now);
		startDateFilter.setGreaterOrEqualThan(now.minus(DAYS_FROM_START, ChronoUnit.DAYS));
		criteria.setStartDate(startDateFilter);

		JobOfferStatusFilter statusFilter = new JobOfferStatusFilter();
		statusFilter.setEquals(JobOfferStatus.ACTIVE);
		criteria.setStatus(statusFilter);

		return ResponseEntity.ok().body(jobOfferQueryService.countByCriteria(criteria));
	}

	/**
	 * POST /promoteJobOffers : promote some offers to some candidates.
	 *
	 * @param promoteRequest the promote request
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@PostMapping("/promoteJobOffers")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<Void> promoteJobOffers(@Valid @RequestBody PromoteJobOffersRequestDTO promoteRequest) {
		LOG.debug("REST request to promoteJobOffers");
		if(promoteRequest.getJobOffersId() == null || promoteRequest.getJobOffersId().size() == 0) {
			throw new BadRequestAlertException("Invalid job offers id list", ENTITY_NAME, "idnull");
		}
		if(promoteRequest.getCandidatesId() == null || promoteRequest.getCandidatesId().size() == 0) {
			throw new BadRequestAlertException("Invalid candidates id list", ENTITY_NAME, "idnull");
		}

		boolean everythingValid = jobOfferService.promoteJobOffers(promoteRequest);
		return ResponseEntity.status(everythingValid ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT).body(null);
	}


	/**
	 * GET /customPromoteJobOfferMessages : get all custom messages.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the custom messages
	 */
	@GetMapping("/customPromoteJobOfferMessages")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<List<PromoteJobOffersCustomMessageDTO>> customPromoteJobOfferMessages() {
		LOG.debug("REST request to customPromoteJobOfferMessages");

		List<PromoteJobOffersCustomMessageDTO> customMessages = new ArrayList<PromoteJobOffersCustomMessageDTO>();

		customMessages.add(new PromoteJobOffersCustomMessageDTO(
			Locale.ITALIAN.getLanguage(),
			messageSource.getMessage("email.promoteJobOffers.defaultMessage", null, Locale.ITALIAN))
		);

		customMessages.add(new PromoteJobOffersCustomMessageDTO(
			Locale.ENGLISH.getLanguage(),
			messageSource.getMessage("email.promoteJobOffers.defaultMessage", null, Locale.ENGLISH))
		);

		return ResponseEntity.ok().body(customMessages);
	}

}
