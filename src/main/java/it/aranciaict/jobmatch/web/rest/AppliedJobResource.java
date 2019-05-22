package it.aranciaict.jobmatch.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.ResponseUtil;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.security.SecurityUtils;
import it.aranciaict.jobmatch.service.AppliedJobQueryService;
import it.aranciaict.jobmatch.service.AppliedJobService;
import it.aranciaict.jobmatch.service.CandidateService;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.dto.AppliedJobCriteria;
import it.aranciaict.jobmatch.service.dto.AppliedJobCriteria.AppiedJobStatusFilter;
import it.aranciaict.jobmatch.service.dto.AppliedJobDTO;
import it.aranciaict.jobmatch.service.dto.AppliedJobItemDTO;
import it.aranciaict.jobmatch.service.dto.request.ApplyToJobOfferRequestDTO;
import it.aranciaict.jobmatch.service.exceptions.AppliedJobAlreadyExistException;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing AppliedJob.
 */
@RestController
@RequestMapping("/api/applied-jobs")
public class AppliedJobResource {

	private static final int DAYS_FROM_START = 7;

	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(AppliedJobResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "appliedJob";

	/** The applied job service. */
	private final AppliedJobService appliedJobService;

	/** The applied job query service. */
	private final AppliedJobQueryService appliedJobQueryService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CandidateService candidateService;

	/**
	 * Instantiates a new applied job resource.
	 *
	 * @param appliedJobService      the applied job service
	 * @param appliedJobQueryService the applied job query service
	 */
	public AppliedJobResource(AppliedJobService appliedJobService, AppliedJobQueryService appliedJobQueryService) {
		this.appliedJobService = appliedJobService;
		this.appliedJobQueryService = appliedJobQueryService;
	}

	/**
	 * POST /applied-jobs : Create a new appliedJob.
	 *
	 * @param appliedJobDTO the appliedJobDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         appliedJobDTO, or with status 400 (Bad Request) if the appliedJob has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<AppliedJobDTO> createAppliedJob(@Valid @RequestBody AppliedJobDTO appliedJobDTO)
			throws URISyntaxException {
		LOG.debug("REST request to save AppliedJob : {}", appliedJobDTO);
		if (appliedJobDTO.getId() != null) {
			throw new BadRequestAlertException("A new appliedJob cannot already have an ID", ENTITY_NAME, "idexists");
		}
		AppliedJobDTO result = appliedJobService.save(appliedJobDTO);
		return ResponseEntity.created(new URI("/api/applied-jobs/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /applied-jobs : Updates an existing appliedJob.
	 *
	 * @param appliedJobDTO the appliedJobDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         appliedJobDTO, or with status 400 (Bad Request) if the appliedJobDTO
	 *         is not valid, or with status 500 (Internal Server Error) if the
	 *         appliedJobDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<AppliedJobDTO> updateAppliedJob(@Valid @RequestBody AppliedJobDTO appliedJobDTO)
			throws URISyntaxException {
		LOG.debug("REST request to update AppliedJob : {}", appliedJobDTO);
		if (appliedJobDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AppliedJobDTO result = appliedJobService.save(appliedJobDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appliedJobDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /applied-jobs : get all the appliedJobs.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of appliedJobs
	 *         in body
	 */
	@GetMapping("")
	public ResponseEntity<List<AppliedJobDTO>> getAllAppliedJobs(AppliedJobCriteria criteria, Pageable pageable) {
		LOG.debug("REST request to get AppliedJobs by criteria: {}", criteria);
		applyCriteriaProfilazioneAppliedJob(criteria);
		Page<AppliedJobDTO> page = appliedJobQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applied-jobs");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /applied-jobs/applyments : get all the appliedJobs.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of appliedJobs
	 *         in body
	 */
	@GetMapping("/applyments")
	public ResponseEntity<List<AppliedJobItemDTO>> getAllAppliedJobsItem(AppliedJobCriteria criteria,
			Pageable pageable) {
		LOG.debug("REST request to get AppliedJobsItem by criteria: {}", criteria);
		applyCriteriaProfilazioneAppliedJob(criteria);
		Page<AppliedJobItemDTO> page = appliedJobQueryService.findItemsByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applied-jobs/applyments");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /applied-jobs/count : count all the appliedJobs.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/count")
	public ResponseEntity<Long> countAppliedJobs(AppliedJobCriteria criteria) {
		LOG.debug("REST request to count AppliedJobs by criteria: {}", criteria);
		applyCriteriaProfilazioneAppliedJob(criteria);
		return ResponseEntity.ok().body(appliedJobQueryService.countByCriteria(criteria));
	}

	/**
	 * Apply criteria profilazione job offers.
	 *
	 * @param criteria the criteria
	 */
	private void applyCriteriaProfilazioneAppliedJob(AppliedJobCriteria criteria) {

		if (SecurityUtils.isCurrentUserCompany() || SecurityUtils.isCurrentUserSponsoringInstitution()) {
			if (!criteria.isSearchMode()) {
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
		} else if (SecurityUtils.isCurrentUserCandidate()) {
			Long candidateId = candidateService.findCandidateIdByCurrentUser().orElse(null);
			LongFilter candidateFilter = new LongFilter();
			candidateFilter.setEquals(candidateId);
			criteria.setCandidateId(candidateFilter);
		}
	}

	/**
	 * GET /applied-jobs/:id : get the "id" appliedJob.
	 *
	 * @param id the id of the appliedJobDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         appliedJobDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AppliedJobDTO> getAppliedJob(@PathVariable Long id) {
		LOG.debug("REST request to get AppliedJob : {}", id);
		Optional<AppliedJobDTO> appliedJobDTO = appliedJobService.findOne(id);
		return ResponseUtil.wrapOrNotFound(appliedJobDTO);
	}

	/**
	 * DELETE /applied-jobs/:id : delete the "id" appliedJob.
	 *
	 * @param id the id of the appliedJobDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Void> deleteAppliedJob(@PathVariable Long id) {
		LOG.debug("REST request to delete AppliedJob : {}", id);
		appliedJobService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * Creates the applied job.
	 *
	 * @param applyToJobOfferRequestDTO the apply to job offer request DTO
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@PutMapping("/applyToJobOffer")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
	public ResponseEntity<AppliedJobDTO> applyToJobOffer(
			@Valid @RequestBody ApplyToJobOfferRequestDTO applyToJobOfferRequestDTO) throws URISyntaxException {
		LOG.debug("REST request to create  AppliedJob : {}", applyToJobOfferRequestDTO);

		AppliedJobDTO result;
		try {
			result = appliedJobService.createAppliedJob(applyToJobOfferRequestDTO);
		} catch (AppliedJobAlreadyExistException e) {
			LOG.error("Errore in fase di candidatura: " + e.getMessage(), e);
			throw new BadRequestAlertException(e.getMessage(), e.getParams(), e.getErrorKey());
		}

		return ResponseEntity.created(new URI("/api/applied-jobs/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

//	/**
//	 * Creates the applied job.
//	 *
//	 * @param request the request
//	 * @return the response entity
//	 * @throws URISyntaxException the URI syntax exception
//	 */
//	@PutMapping("/applyToCompany")
//	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
//	public ResponseEntity<AppliedJobDTO> applyToCompany(@Valid @RequestBody ApplyToCompanyDTO request)
//			throws URISyntaxException {
//		LOG.debug("REST request to create  applyToCompany : {}", request);
//
//		try {
//			appliedJobService.createAppliedJobToCompany(request);
//		} catch (Exception e) {
//			LOG.error("Errore in fase di candidatura: " + e.getMessage(), e);
//			throw new BadRequestAlertException(e.getMessage(), "", "");
//		}
//
//		return ResponseEntity.ok().body(null);
//	}

	/**
	 * GET /newAppliedJobCount/{companyId} : count all the appliedJobs.
	 *
	 * @param companyId the company id
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/newAppliedJobCount/{companyId}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.COMPANY + "') or hasRole('"
			+ AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<Long> countNewAppliedJobsByCompanyId(@PathVariable Long companyId) {
		LOG.debug("REST request to count new Applied Jobs by companyId: {}", companyId);
		AppliedJobCriteria criteria = new AppliedJobCriteria();

		// Candidature appartenenti all'azienda (companyId)
		LongFilter companyIdFilter = new LongFilter();
		companyIdFilter.setEquals(companyId);
		criteria.setCompanyId(companyIdFilter);

		// Candidature eseguite nell'ultima settimana
		InstantFilter createDateFilter = new InstantFilter();
		Instant now = Instant.now();
		createDateFilter.setLessOrEqualThan(now);
		createDateFilter.setGreaterOrEqualThan(now.minus(DAYS_FROM_START, ChronoUnit.DAYS));
		criteria.setCreatedDate(createDateFilter);

		// Candidature con AppiedJobStatus = NEW
		AppiedJobStatusFilter appiedJobStatusFilter = new AppiedJobStatusFilter();
		appiedJobStatusFilter.setEquals(AppiedJobStatus.NEW);
		criteria.setAppliedJobStatus(appiedJobStatusFilter);

		return ResponseEntity.ok().body(appliedJobQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /newAppliedJobCountCandidate/{candidateId} : count all the appliedJobs.
	 *
	 * @param candidateId the candidateId
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/newAppliedJobCountCandidate/{candidateId}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
	public ResponseEntity<Long> countAppliedJobByCandidateId(@PathVariable Long candidateId) {
		LOG.debug("REST request to count new Applied Jobs by candidateId: {}", candidateId);
		AppliedJobCriteria criteria = new AppliedJobCriteria();

		// Candidature appartenenti all'azienda (companyId)
		LongFilter candidateIdFilter = new LongFilter();
		candidateIdFilter.setEquals(candidateId);
		criteria.setCandidateId(candidateIdFilter);

		return ResponseEntity.ok().body(appliedJobQueryService.countByCriteria(criteria));
	}

}
