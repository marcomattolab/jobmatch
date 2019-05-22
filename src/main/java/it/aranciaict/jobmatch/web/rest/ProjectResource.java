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
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.security.SecurityUtils;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.ProjectQueryService;
import it.aranciaict.jobmatch.service.ProjectService;
import it.aranciaict.jobmatch.service.dto.ProjectCriteria;
import it.aranciaict.jobmatch.service.dto.ProjectDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "project";

	/** The project service. */
	private final ProjectService projectService;

	/** The project query service. */
	private final ProjectQueryService projectQueryService;

	/** The company service. */
	private final CompanyService companyService;

	/**
	 * Instantiates a new project resource.
	 *
	 * @param projectService      the project service
	 * @param projectQueryService the project query service
	 * @param companyService      the company service
	 */
	public ProjectResource(ProjectService projectService, ProjectQueryService projectQueryService,
			CompanyService companyService) {
		this.projectService = projectService;
		this.projectQueryService = projectQueryService;
		this.companyService = companyService;
	}

	/**
	 * POST /projects : Create a new project.
	 *
	 * @param projectDTO the projectDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         projectDTO, or with status 400 (Bad Request) if the project has
	 *         already an ID
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PostMapping("/projects")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO)
			throws URISyntaxException {
		log.debug("REST request to save Project : {}", projectDTO);
		if (projectDTO.getId() != null) {
			throw new BadRequestAlertException("A new project cannot already have an ID", ENTITY_NAME, "idexists");
		}
		ProjectDTO result = projectService.save(projectDTO);
		return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /projects : Updates an existing project.
	 *
	 * @param projectDTO the projectDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         projectDTO, or with status 400 (Bad Request) if the projectDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the projectDTO
	 *         couldn't be updated
	 * @throws URISyntaxException if the Location URI syntax is incorrect
	 */
	@PutMapping("/projects")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<ProjectDTO> updateProject(@Valid @RequestBody ProjectDTO projectDTO)
			throws URISyntaxException {
		log.debug("REST request to update Project : {}", projectDTO);
		if (projectDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		ProjectDTO result = projectService.save(projectDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectDTO.getId().toString())).body(result);
	}

	/**
	 * GET /projects : get all the projects.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of projects in
	 *         body
	 */
	@GetMapping("/projects")
	public ResponseEntity<List<ProjectDTO>> getAllProjects(ProjectCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Projects by criteria: {}", criteria);
		applyCriteriaProfilazioneProject(criteria);
		Page<ProjectDTO> page = projectQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * GET /projects/count : count all the projects.
	 *
	 * @param criteria the criterias which the requested entities should match
	 * @return the ResponseEntity with status 200 (OK) and the count in body
	 */
	@GetMapping("/projects/count")
	public ResponseEntity<Long> countProjects(ProjectCriteria criteria) {
		log.debug("REST request to count Projects by criteria: {}", criteria);
		applyCriteriaProfilazioneProject(criteria);
		return ResponseEntity.ok().body(projectQueryService.countByCriteria(criteria));
	}

	/**
	 * Apply criteria profilazione job offers.
	 *
	 * @param criteria the criteria
	 */
	private void applyCriteriaProfilazioneProject(ProjectCriteria criteria) {
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

	/**
	 * GET /projects/:id : get the "id" project.
	 *
	 * @param id the id of the projectDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the projectDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
		log.debug("REST request to get Project : {}", id);
		Optional<ProjectDTO> projectDTO = projectService.findOne(id);
		return ResponseUtil.wrapOrNotFound(projectDTO);
	}

	/**
	 * DELETE /projects/:id : delete the "id" project.
	 *
	 * @param id the id of the projectDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/projects/{id}")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.COMPANY
			+ "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
		log.debug("REST request to delete Project : {}", id);
		projectService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
