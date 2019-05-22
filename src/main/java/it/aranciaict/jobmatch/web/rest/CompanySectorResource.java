package it.aranciaict.jobmatch.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
import it.aranciaict.jobmatch.domain.comparators.CompanySectorComparator;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.service.CompanySectorQueryService;
import it.aranciaict.jobmatch.service.CompanySectorService;
import it.aranciaict.jobmatch.service.dto.CompanySectorCriteria;
import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing CompanySector.
 */
@RestController
@RequestMapping("/api/company-sectors")
public class CompanySectorResource {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CompanySectorResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "companySector";

	/** The company sector service. */
	private final CompanySectorService companySectorService;

	/** The company sector query service. */
	private final CompanySectorQueryService companySectorQueryService;

	/**
	 * Instantiates a new company sector resource.
	 *
	 * @param companySectorService      the company sector service
	 * @param companySectorQueryService the company sector query service
	 */
	public CompanySectorResource(CompanySectorService companySectorService,
			CompanySectorQueryService companySectorQueryService) {
		this.companySectorService = companySectorService;
		this.companySectorQueryService = companySectorQueryService;
	}

	/**
	 * POST /company-sectors : Create a new companySector.
	 *
	 * @param companySectorDTO the companySectorDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         companySectorDTO, or with status 400 (Bad Request) if the
	 *         companySector has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<CompanySectorDTO> createCompanySector(@Valid @RequestBody CompanySectorDTO companySectorDTO)
			throws URISyntaxException {
		log.debug("REST request to save CompanySector : {}", companySectorDTO);
		if (companySectorDTO.getId() != null) {
			throw new BadRequestAlertException("A new companySector cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		CompanySectorDTO result = companySectorService.save(companySectorDTO);
		return ResponseEntity.created(new URI("/api/company-sectors/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /company-sectors : Updates an existing companySector.
	 *
	 * @param companySectorDTO the companySectorDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         companySectorDTO, or with status 400 (Bad Request) if the
	 *         companySectorDTO is not valid, or with status 500 (Internal Server
	 *         Error) if the companySectorDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<CompanySectorDTO> updateCompanySector(@Valid @RequestBody CompanySectorDTO companySectorDTO)
			throws URISyntaxException {
		log.debug("REST request to update CompanySector : {}", companySectorDTO);
		if (companySectorDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		CompanySectorDTO result = companySectorService.save(companySectorDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companySectorDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /company-sectors : get all the companySectors.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         companySectors in body
	 */
	@GetMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<List<CompanySectorDTO>> getAllCompanySectors(CompanySectorCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get CompanySectors by criteria: {}", criteria);
		Page<CompanySectorDTO> page = companySectorQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-sectors");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * Gets the all company sectors.
	 *
	 * @param languageKey the language key
	 * @return the all company sectors
	 */
	@GetMapping("/all/{languageKey}")
	public ResponseEntity<List<CompanySectorDTO>> getAllCompanySectors(@PathVariable String languageKey) {
		log.debug("REST request to get All CompanySectors");
		List<CompanySectorDTO> list = companySectorService.findAll(new Locale(languageKey));
		Collections.sort(list, new CompanySectorComparator());
		return ResponseEntity.ok().body(list);
	}

	/**
	 * GET /company-sectors/count : count all the companySectors.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/count")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Long> countCompanySectors(CompanySectorCriteria criteria) {
		log.debug("REST request to count CompanySectors by criteria: {}", criteria);
		return ResponseEntity.ok().body(companySectorQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /company-sectors/:id : get the "id" companySector.
	 *
	 * @param languageKey the language key
	 * @param id          the id of the companySectorDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         companySectorDTO, or with status 404 (Not Found)
	 */
	@GetMapping("{languageKey}/{id}")
	public ResponseEntity<CompanySectorDTO> getCompanySector(@PathVariable String languageKey, @PathVariable Long id) {
		log.debug("REST request to get CompanySector : {}", id);
		Optional<CompanySectorDTO> companySectorDTO = companySectorService.findOne(new Locale(languageKey), id);
		return ResponseUtil.wrapOrNotFound(companySectorDTO);
	}

	/**
	 * DELETE /company-sectors/:id : delete the "id" companySector.
	 *
	 * @param id the id of the companySectorDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Void> deleteCompanySector(@PathVariable Long id) {
		log.debug("REST request to delete CompanySector : {}", id);
		companySectorService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
