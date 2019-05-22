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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;
import it.aranciaict.jobmatch.service.AppliedJobIterationQueryService;
import it.aranciaict.jobmatch.service.AppliedJobIterationService;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationCriteria;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing AppliedJobIteration.
 */
@RestController
@RequestMapping("/api")
public class AppliedJobIterationResource {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(AppliedJobIterationResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "appliedJobIteration";

	/** The applied job iteration service. */
	private final AppliedJobIterationService appliedJobIterationService;

	/** The applied job iteration query service. */
	private final AppliedJobIterationQueryService appliedJobIterationQueryService;

	/**
	 * Instantiates a new applied job iteration resource.
	 *
	 * @param appliedJobIterationService      the applied job iteration service
	 * @param appliedJobIterationQueryService the applied job iteration query
	 *                                        service
	 */
	public AppliedJobIterationResource(AppliedJobIterationService appliedJobIterationService,
			AppliedJobIterationQueryService appliedJobIterationQueryService) {
		this.appliedJobIterationService = appliedJobIterationService;
		this.appliedJobIterationQueryService = appliedJobIterationQueryService;
	}

	/**
	 * POST /applied-job-iterations : Create a new appliedJobIteration.
	 *
	 * @param appliedJobIterationDTO the appliedJobIterationDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         appliedJobIterationDTO, or with status 400 (Bad Request) if the
	 *         appliedJobIteration has already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/applied-job-iterations")
	public ResponseEntity<AppliedJobIterationDTO> createAppliedJobIteration(
			@Valid @RequestBody AppliedJobIterationDTO appliedJobIterationDTO) throws URISyntaxException {
		log.debug("REST request to save AppliedJobIteration : {}", appliedJobIterationDTO);
		if (appliedJobIterationDTO.getId() != null) {
			throw new BadRequestAlertException("A new appliedJobIteration cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		AppliedJobIterationDTO result = appliedJobIterationService.save(appliedJobIterationDTO);
		return ResponseEntity.created(new URI("/api/applied-job-iterations/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /applied-job-iterations : Updates an existing appliedJobIteration.
	 *
	 * @param appliedJobIterationDTO the appliedJobIterationDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         appliedJobIterationDTO, or with status 400 (Bad Request) if the
	 *         appliedJobIterationDTO is not valid, or with status 500 (Internal
	 *         Server Error) if the appliedJobIterationDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/applied-job-iterations")
	public ResponseEntity<AppliedJobIterationDTO> updateAppliedJobIteration(
			@Valid @RequestBody AppliedJobIterationDTO appliedJobIterationDTO) throws URISyntaxException {
		log.debug("REST request to update AppliedJobIteration : {}", appliedJobIterationDTO);
		if (appliedJobIterationDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AppliedJobIterationDTO result = appliedJobIterationService.save(appliedJobIterationDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appliedJobIterationDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /applied-job-iterations : get all the appliedJobIterations.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         appliedJobIterations in body
	 */
	@GetMapping("/applied-job-iterations")
	public ResponseEntity<List<AppliedJobIterationDTO>> getAllAppliedJobIterations(AppliedJobIterationCriteria criteria,
			Pageable pageable) {
		log.debug("REST request to get AppliedJobIterations by criteria: {}", criteria);
		Page<AppliedJobIterationDTO> page = appliedJobIterationQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applied-job-iterations");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /applied-job-iterations/count : count all the appliedJobIterations.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/applied-job-iterations/count")
	public ResponseEntity<Long> countAppliedJobIterations(AppliedJobIterationCriteria criteria) {
		log.debug("REST request to count AppliedJobIterations by criteria: {}", criteria);
		return ResponseEntity.ok().body(appliedJobIterationQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /applied-job-iterations/:id : get the "id" appliedJobIteration.
	 *
	 * @param id the id of the appliedJobIterationDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         appliedJobIterationDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/applied-job-iterations/{id}")
	public ResponseEntity<AppliedJobIterationDTO> getAppliedJobIteration(@PathVariable Long id) {
		log.debug("REST request to get AppliedJobIteration : {}", id);
		Optional<AppliedJobIterationDTO> appliedJobIterationDTO = appliedJobIterationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(appliedJobIterationDTO);
	}

	/**
	 * DELETE /applied-job-iterations/:id : delete the "id" appliedJobIteration.
	 *
	 * @param id the id of the appliedJobIterationDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/applied-job-iterations/{id}")
	public ResponseEntity<Void> deleteAppliedJobIteration(@PathVariable Long id) {
		log.debug("REST request to delete AppliedJobIteration : {}", id);
		appliedJobIterationService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
