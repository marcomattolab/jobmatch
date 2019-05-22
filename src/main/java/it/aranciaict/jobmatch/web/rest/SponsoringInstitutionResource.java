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

import io.github.jhipster.web.util.ResponseUtil;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.service.SponsoringInstitutionQueryService;
import it.aranciaict.jobmatch.service.SponsoringInstitutionService;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionCriteria;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionDTO;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionItemDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing SponsoringInstitution.
 */
@RestController
@RequestMapping("/api/sponsoring-institutions")
public class SponsoringInstitutionResource {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SponsoringInstitutionResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "sponsoringInstitution";

	/** The sponsoring institution service. */
	private final SponsoringInstitutionService sponsoringInstitutionService;

	/** The sponsoring institution query service. */
	private final SponsoringInstitutionQueryService sponsoringInstitutionQueryService;

	/**
	 * Instantiates a new sponsoring institution resource.
	 *
	 * @param sponsoringInstitutionService      the sponsoring institution service
	 * @param sponsoringInstitutionQueryService the sponsoring institution query
	 *                                          service
	 */
	public SponsoringInstitutionResource(SponsoringInstitutionService sponsoringInstitutionService,
			SponsoringInstitutionQueryService sponsoringInstitutionQueryService) {
		this.sponsoringInstitutionService = sponsoringInstitutionService;
		this.sponsoringInstitutionQueryService = sponsoringInstitutionQueryService;
	}

	/**
	 * POST /sponsoring-institutions : Create a new sponsoringInstitution.
	 *
	 * @param sponsoringInstitutionDTO the sponsoringInstitutionDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         sponsoringInstitutionDTO, or with status 400 (Bad Request) if the
	 *         sponsoringInstitution has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<SponsoringInstitutionDTO> createSponsoringInstitution(
			@Valid @RequestBody SponsoringInstitutionDTO sponsoringInstitutionDTO) throws URISyntaxException {
		log.debug("REST request to save SponsoringInstitution : {}", sponsoringInstitutionDTO);
		if (sponsoringInstitutionDTO.getId() != null) {
			throw new BadRequestAlertException("A new sponsoringInstitution cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		SponsoringInstitutionDTO result = sponsoringInstitutionService.save(sponsoringInstitutionDTO);
		return ResponseEntity.created(new URI("/api/sponsoring-institutions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /sponsoring-institutions : Updates an existing sponsoringInstitution.
	 *
	 * @param sponsoringInstitutionDTO the sponsoringInstitutionDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         sponsoringInstitutionDTO, or with status 400 (Bad Request) if the
	 *         sponsoringInstitutionDTO is not valid, or with status 500 (Internal
	 *         Server Error) if the sponsoringInstitutionDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<SponsoringInstitutionDTO> updateSponsoringInstitution(
			@Valid @RequestBody SponsoringInstitutionDTO sponsoringInstitutionDTO) throws URISyntaxException {
		log.debug("REST request to update SponsoringInstitution : {}", sponsoringInstitutionDTO);
		if (sponsoringInstitutionDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		SponsoringInstitutionDTO result = sponsoringInstitutionService.save(sponsoringInstitutionDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sponsoringInstitutionDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /sponsoring-institutions : get all the sponsoringInstitutions.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         sponsoringInstitutions in body
	 */
	@GetMapping("")
	public ResponseEntity<List<SponsoringInstitutionItemDTO>> getAllSponsoringInstitutions(
			SponsoringInstitutionCriteria criteria, Pageable pageable) {
		log.debug("REST request to get SponsoringInstitutions by criteria: {}", criteria);
		Page<SponsoringInstitutionItemDTO> page = sponsoringInstitutionQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sponsoring-institutions");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /sponsoring-institutions/count : count all the sponsoringInstitutions.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/count")
	public ResponseEntity<Long> countSponsoringInstitutions(SponsoringInstitutionCriteria criteria) {
		log.debug("REST request to count SponsoringInstitutions by criteria: {}", criteria);
		return ResponseEntity.ok().body(sponsoringInstitutionQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /sponsoring-institutions/:id : get the "id" sponsoringInstitution.
	 *
	 * @param id the id of the sponsoringInstitutionDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         sponsoringInstitutionDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<SponsoringInstitutionDTO> getSponsoringInstitution(@PathVariable Long id) {
		log.debug("REST request to get SponsoringInstitution : {}", id);
		Optional<SponsoringInstitutionDTO> sponsoringInstitutionDTO = sponsoringInstitutionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(sponsoringInstitutionDTO);
	}


	/**
	 * Gets the sponsoring institution by current user.
	 *
	 * @return the sponsoring institution by current user
	 */
	@GetMapping("/currentUser")
	public ResponseEntity<SponsoringInstitutionDTO> getSponsoringInstitutionByCurrentUser() {
		log.debug("REST request to get Sponsoring Institution By Current User ");
		Optional<SponsoringInstitutionDTO> sponsoringInstitutionDTO = sponsoringInstitutionService.findByCurrentUser();
		return ResponseUtil.wrapOrNotFound(sponsoringInstitutionDTO);
	}

	/**
	 * DELETE /sponsoring-institutions/:id : delete the "id" sponsoringInstitution.
	 *
	 * @param id the id of the sponsoringInstitutionDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Void> deleteSponsoringInstitution(@PathVariable Long id) {
		log.debug("REST request to delete SponsoringInstitution : {}", id);
		sponsoringInstitutionService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
