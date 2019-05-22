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
import it.aranciaict.jobmatch.service.EducationQueryService;
import it.aranciaict.jobmatch.service.EducationService;
import it.aranciaict.jobmatch.service.dto.EducationCriteria;
import it.aranciaict.jobmatch.service.dto.EducationDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing Education.
 */
@RestController
@RequestMapping("/api/educations")
public class EducationResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(EducationResource.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "education";

    /** The education service. */
    private final EducationService educationService;

    /** The education query service. */
    private final EducationQueryService educationQueryService;

    /**
     * Instantiates a new education resource.
     *
     * @param educationService      the education service
     * @param educationQueryService the education query service
     */
    public EducationResource(EducationService educationService, EducationQueryService educationQueryService) {
        this.educationService = educationService;
        this.educationQueryService = educationQueryService;
    }

    /**
     * POST /educations : Create a new education.
     *
     * @param educationDTO the educationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         educationDTO, or with status 400 (Bad Request) if the education has
     *         already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<EducationDTO> createEducation(@Valid @RequestBody EducationDTO educationDTO)
            throws URISyntaxException {
        log.debug("REST request to save Education : {}", educationDTO);
        if (educationDTO.getId() != null) {
            throw new BadRequestAlertException("A new education cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.created(new URI("/api/educations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /educations : Updates an existing education.
     *
     * @param educationDTO the educationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         educationDTO, or with status 400 (Bad Request) if the educationDTO is
     *         not valid, or with status 500 (Internal Server Error) if the
     *         educationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<EducationDTO> updateEducation(@Valid @RequestBody EducationDTO educationDTO)
            throws URISyntaxException {
        log.debug("REST request to update Education : {}", educationDTO);
        if (educationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, educationDTO.getId().toString())).body(result);
    }

    /**
     * GET /educations : get all the educations.
     *
     * @param criteria the criteria
     * @param pageable the pageable
     * @return the ResponseEntity with status 200 (OK) and the list of educations in
     *         body
     */
    @GetMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<EducationDTO>> getAllEducations(EducationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Educations by criteria: {}", criteria);
        Page<EducationDTO> page = educationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/educations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET /educations/count : count all the educations.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the count in body
     */
    @GetMapping("/count")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Long> countEducations(EducationCriteria criteria) {
        log.debug("REST request to count Educations by criteria: {}", criteria);
        return ResponseEntity.ok().body(educationQueryService.countByCriteria(criteria));
    }

    /**
     * GET /educations/:id : get the "id" education.
     *
     * @param id the id of the educationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         educationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EducationDTO> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        Optional<EducationDTO> educationDTO = educationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationDTO);
    }

    /**
     * DELETE /educations/:id : delete the "id" education.
     *
     * @param id the id of the educationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Gets the candidate educations.
     *
     * @param idCandidate the id candidate
     * @return the candidate educations
     */
    @GetMapping("/candidate/{idCandidate}")
    public ResponseEntity<List<EducationDTO>> getCandidateEducations(@PathVariable Long idCandidate) {
        log.debug("REST request to get Educations by idCandidate: {}", idCandidate);
        List<EducationDTO> educations = educationService.findAllByCandidateId(idCandidate);
        return ResponseEntity.ok().body(educations);
    }
}
