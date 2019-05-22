package it.aranciaict.jobmatch.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.ResponseUtil;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.service.DirectApplicationQueryService;
import it.aranciaict.jobmatch.service.DirectApplicationService;
import it.aranciaict.jobmatch.service.dto.DirectApplicationCriteria;
import it.aranciaict.jobmatch.service.dto.DirectApplicationCriteria.AppiedJobStatusFilter;
import it.aranciaict.jobmatch.service.dto.DirectApplicationDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing DirectApplication.
 */
@RestController
@RequestMapping("/api")
public class DirectApplicationResource {

	private final Logger log = LoggerFactory.getLogger(DirectApplicationResource.class);

	private static final String ENTITY_NAME = "directApplication";

	private final DirectApplicationService directApplicationService;

	private final DirectApplicationQueryService directApplicationQueryService;

	/**
	 * Instantiates a new direct application resource.
	 *
	 * @param directApplicationService      the direct application service
	 * @param directApplicationQueryService the direct application query service
	 */
	public DirectApplicationResource(DirectApplicationService directApplicationService,
			DirectApplicationQueryService directApplicationQueryService) {
		this.directApplicationService = directApplicationService;
		this.directApplicationQueryService = directApplicationQueryService;
	}

	/**
	 * POST /direct-applications : Create a new directApplication.
	 *
	 * @param directApplicationDTO the directApplicationDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         directApplicationDTO, or with status 400 (Bad Request) if the
	 *         directApplication has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/direct-applications")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE+ "')")
	public ResponseEntity<DirectApplicationDTO> createDirectApplication(
			@Valid @RequestBody DirectApplicationDTO directApplicationDTO) throws URISyntaxException {
		log.debug("REST request to save DirectApplication : {}", directApplicationDTO);
		if (directApplicationDTO.getId() != null) {
			throw new BadRequestAlertException("A new directApplication cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		DirectApplicationDTO result = directApplicationService.createAppliedJobToCompany(directApplicationDTO);
		return ResponseEntity.created(new URI("/api/direct-applications/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /direct-applications : Updates an existing directApplication.
	 *
	 * @param directApplicationDTO the directApplicationDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         directApplicationDTO, or with status 400 (Bad Request) if the
	 *         directApplicationDTO is not valid, or with status 500 (Internal
	 *         Server Error) if the directApplicationDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/direct-applications")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<DirectApplicationDTO> updateDirectApplication(
			@Valid @RequestBody DirectApplicationDTO directApplicationDTO) throws URISyntaxException {
		log.debug("REST request to update DirectApplication : {}", directApplicationDTO);
		if (directApplicationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		DirectApplicationDTO result = directApplicationService.save(directApplicationDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, directApplicationDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /direct-applications : get all the directApplications.
	 *
	 * @param pageable the pagination information
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         directApplications in body
	 */
	@GetMapping("/direct-applications")
	public ResponseEntity<List<DirectApplicationDTO>> getAllDirectApplications(DirectApplicationCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get DirectApplications by criteria: {}", criteria);
		Page<DirectApplicationDTO> page = directApplicationQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/direct-applications");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /direct-applications/count : count all the directApplications.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/direct-applications/count")
	public ResponseEntity<Long> countDirectApplications(DirectApplicationCriteria criteria) {
		log.debug("REST request to count DirectApplications by criteria: {}", criteria);
		return ResponseEntity.ok().body(directApplicationQueryService.countByCriteria(criteria));
	}
	
	
	/**
	 * GET /direct-applications/count : count all the directApplications.
	 *
	 * @param directApplicationDTO the direct application DTO
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/direct-applications/alreadyApplied")
	public ResponseEntity<Boolean> isAlreadyAppliedToCompany(DirectApplicationDTO directApplicationDTO) {
		DirectApplicationCriteria criteria = new DirectApplicationCriteria();
		LongFilter candidateIdFilter = new LongFilter();
		candidateIdFilter.setEquals(directApplicationDTO.getCandidateId());
		criteria.setCandidateId(candidateIdFilter);
		LongFilter companyIdFilter = new LongFilter();
		companyIdFilter.setEquals(directApplicationDTO.getCompanyId());
		criteria.setCompanyId(companyIdFilter);
		AppiedJobStatusFilter appiedJobStatusFilter = new AppiedJobStatusFilter();
		appiedJobStatusFilter.setEquals(AppiedJobStatus.NEW);
		criteria.setAppiedJobStatus(appiedJobStatusFilter);
		long countByCriteria = directApplicationQueryService.countByCriteria(criteria);
		return ResponseEntity.ok().body(countByCriteria > 0);
		
	}

	/**
	 * GET /direct-applications/:id : get the "id" directApplication.
	 *
	 * @param id the id of the directApplicationDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         directApplicationDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/direct-applications/{id}")
	public ResponseEntity<DirectApplicationDTO> getDirectApplication(@PathVariable Long id) {
		log.debug("REST request to get DirectApplication : {}", id);
		Optional<DirectApplicationDTO> directApplicationDTO = directApplicationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(directApplicationDTO);
	}

	/**
	 * DELETE /direct-applications/:id : delete the "id" directApplication.
	 *
	 * @param id the id of the directApplicationDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/direct-applications/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Void> deleteDirectApplication(@PathVariable Long id) {
		log.debug("REST request to delete DirectApplication : {}", id);
		directApplicationService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
	

}
