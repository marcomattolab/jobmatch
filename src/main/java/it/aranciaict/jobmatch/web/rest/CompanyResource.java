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
import it.aranciaict.jobmatch.service.CompanyQueryService;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.dto.CompanyCriteria;
import it.aranciaict.jobmatch.service.dto.CompanyDTO;
import it.aranciaict.jobmatch.service.dto.CompanyItemDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyResource {

	private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

	private static final String ENTITY_NAME = "company";

	private final CompanyService companyService;

	private final CompanyQueryService companyQueryService;

	/**
	 * Instantiates a new company resource.
	 *
	 * @param companyService      the company service
	 * @param companyQueryService the company query service
	 */
	public CompanyResource(CompanyService companyService, CompanyQueryService companyQueryService) {
		this.companyService = companyService;
		this.companyQueryService = companyQueryService;
	}

	/**
	 * POST /companies : Create a new company.
	 *
	 * @param companyDTO the companyDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         companyDTO, or with status 400 (Bad Request) if the company has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO)
			throws URISyntaxException {
		log.debug("REST request to save Company : {}", companyDTO);
		if (companyDTO.getId() != null) {
			throw new BadRequestAlertException("A new company cannot already have an ID", ENTITY_NAME, "idexists");
		}
		CompanyDTO result = companyService.save(companyDTO);
		return ResponseEntity.created(new URI("/api/companies/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /companies : Updates an existing company.
	 *
	 * @param companyDTO the companyDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         companyDTO, or with status 400 (Bad Request) if the companyDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the companyDTO
	 *         couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY + "')")
	public ResponseEntity<CompanyDTO> updateCompany(@Valid @RequestBody CompanyDTO companyDTO)
			throws URISyntaxException {
		log.debug("REST request to update Company : {}", companyDTO);
		if (companyDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		CompanyDTO result = companyService.save(companyDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDTO.getId().toString())).body(result);
	}

	/**
	 * GET /companies : get all the companies.
	 *
	 * @param pageable the pagination information
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the list of companies in
	 *         body
	 */
	@GetMapping("")
	public ResponseEntity<List<CompanyItemDTO>> getAllCompanies(CompanyCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Companies by criteria: {}", criteria);
		Page<CompanyItemDTO> page = companyQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/companies");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
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
	public ResponseEntity<List<CompanyItemDTO>> getSuggestedCompanies(@PathVariable Long candidateId) {
		log.debug("REST request to get Suggested Companies for candidate: {}", candidateId);
		List<CompanyItemDTO> list = companyQueryService.findSuggestedByCandidateId(candidateId);
		return ResponseEntity.ok().body(list);
	}

	/**
	 * GET /companies/count : count all the companies.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/count")
	public ResponseEntity<Long> countCompanies(CompanyCriteria criteria) {
		log.debug("REST request to count Companies by criteria: {}", criteria);
		return ResponseEntity.ok().body(companyQueryService.countByCriteria(criteria));
	}

	/**
	 * GET /companies/:id : get the "id" company.
	 *
	 * @param id the id of the companyDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the companyDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
		log.debug("REST request to get Company : {}", id);
		Optional<CompanyDTO> companyDTO = companyService.findOne(id);
		return ResponseUtil.wrapOrNotFound(companyDTO);
	}

	/**
	 * Gets the company by current user.
	 *
	 * @return the company by current user
	 */
	@GetMapping("/currentUser")
	public ResponseEntity<CompanyDTO> getCompanyByCurrentUser() {
		log.debug("REST request to get Company User ");
		Optional<CompanyDTO> companyDTO = companyService.findByCurrentUser();
		return ResponseUtil.wrapOrNotFound(companyDTO);
	}

	/**
	 * DELETE /companies/:id : delete the "id" company.
	 *
	 * @param id the id of the companyDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
	public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
		log.debug("REST request to delete Company : {}", id);
		companyService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
