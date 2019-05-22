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
import it.aranciaict.jobmatch.service.CandidateQueryService;
import it.aranciaict.jobmatch.service.CandidateService;
import it.aranciaict.jobmatch.service.CompanySkillService;
import it.aranciaict.jobmatch.service.dto.CandidateCriteria;
import it.aranciaict.jobmatch.service.dto.CandidateDTO;
import it.aranciaict.jobmatch.service.dto.CandidateItemDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api/candidates")
public class CandidateResource {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "candidate";

	/** The candidate service. */
	private final CandidateService candidateService;

	/** The candidate query service. */
	private final CandidateQueryService candidateQueryService;

	/** The company skill service */
	private final CompanySkillService companySkillService;

	/**
	 * Instantiates a new candidate resource.
	 *
	 * @param candidateService      the candidate service
	 * @param candidateQueryService the candidate query service
	 * @param companySkillService the companySkillService
	 */
	public CandidateResource(CandidateService candidateService, CandidateQueryService candidateQueryService,
		CompanySkillService companySkillService) {
		this.candidateService = candidateService;
		this.candidateQueryService = candidateQueryService;
		this.companySkillService = companySkillService;
	}

	/**
	 * POST /candidates : Create a new candidate.
	 *
	 * @param candidateDTO the candidateDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         candidateDTO, or with status 400 (Bad Request) if the candidate has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<CandidateDTO> createCandidate(@Valid @RequestBody CandidateDTO candidateDTO)
			throws URISyntaxException {
		log.debug("REST request to save Candidate : {}", candidateDTO);
		if (candidateDTO.getId() != null) {
			throw new BadRequestAlertException("A new candidate cannot already have an ID", ENTITY_NAME, "idexists");
		}
		CandidateDTO result = candidateService.save(candidateDTO);
		return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /candidates : Updates an existing candidate.
	 *
	 * @param candidateDTO the candidateDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         candidateDTO, or with status 400 (Bad Request) if the candidateDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         candidateDTO couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
	public ResponseEntity<CandidateDTO> updateCandidate(@Valid @RequestBody CandidateDTO candidateDTO)
			throws URISyntaxException {
		log.debug("REST request to update Candidate : {}", candidateDTO);
		if (candidateDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		CandidateDTO result = candidateService.save(candidateDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateDTO.getId().toString())).body(result);
	}

	/**
	 * GET /candidates : get all the candidates.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of candidates in
	 *         body
	 */
	@GetMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<List<CandidateItemDTO>> getAllCandidates(CandidateCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Candidates by criteria: {}", criteria);
		Page<CandidateItemDTO> page = candidateQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candidates");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}


	/**
	 * Gets the suggested candidates.
	 *
	 * @param idCompany the id company
	 * @return the suggested candidates
	 */
	@GetMapping("/suggestedCandidates/{idCompany}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.COMPANY + "')")
	public ResponseEntity<List<CandidateItemDTO>> getSuggestedCandidates(@PathVariable Long idCompany) {
		log.debug("REST request to get suggested Candidates by Company Id: {}", idCompany);
		List<CandidateItemDTO> listCandidates = candidateQueryService.findSuggestedByCompanyId(idCompany);
		return ResponseEntity.ok().body(listCandidates);
	}

	/**
	 * GET /candidates/count : count all the candidates.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/count")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<Long> countCandidates(CandidateCriteria criteria) {
		log.debug("REST request to count Candidates by criteria: {}", criteria);
		return ResponseEntity.ok().body(candidateQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /candidates/:id : get the "id" candidate.
	 *
	 * @param id the id of the candidateDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         candidateDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CandidateDTO> getCandidate(@PathVariable Long id) {
		log.debug("REST request to get Candidate : {}", id);
		Optional<CandidateDTO> candidateDTO = candidateService.findOne(id);
		return ResponseUtil.wrapOrNotFound(candidateDTO);
	}

	/**
	 * GET /candidates/currentUser : get the candidate by user. Gets the candidate
	 * user.
	 *
	 * @return the candidate user
	 */
	@GetMapping("/currentUser")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
	public ResponseEntity<CandidateDTO> getCandidateByCurrentUser() {
		log.debug("REST request to get Candidate User ");
		Optional<CandidateDTO> candidateDTO = candidateService.findByCurrentUser();
		return ResponseUtil.wrapOrNotFound(candidateDTO);
	}

	/**
	 * DELETE /candidates/:id : delete the "id" candidate.
	 *
	 * @param id the id of the candidateDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
		log.debug("REST request to delete Candidate : {}", id);
		candidateService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
