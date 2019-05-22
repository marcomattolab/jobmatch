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
import it.aranciaict.jobmatch.service.JobExperienceQueryService;
import it.aranciaict.jobmatch.service.JobExperienceService;
import it.aranciaict.jobmatch.service.dto.JobExperienceCriteria;
import it.aranciaict.jobmatch.service.dto.JobExperienceDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing JobExperience.
 */
@RestController
@RequestMapping("/api/job-experiences")
public class JobExperienceResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(JobExperienceResource.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "jobExperience";

    /** The job experience service. */
    private final JobExperienceService jobExperienceService;

    /** The job experience query service. */
    private final JobExperienceQueryService jobExperienceQueryService;

    /**
     * Instantiates a new job experience resource.
     *
     * @param jobExperienceService the job experience service
     * @param jobExperienceQueryService the job experience query service
     */
    public JobExperienceResource(JobExperienceService jobExperienceService, JobExperienceQueryService jobExperienceQueryService) {
        this.jobExperienceService = jobExperienceService;
        this.jobExperienceQueryService = jobExperienceQueryService;
    }

    /**
     * POST  /job-experiences : Create a new jobExperience.
     *
     * @param jobExperienceDTO the jobExperienceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobExperienceDTO, or with status 400 (Bad Request) if the jobExperience has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<JobExperienceDTO> createJobExperience(@Valid @RequestBody JobExperienceDTO jobExperienceDTO) throws URISyntaxException {
        log.debug("REST request to save JobExperience : {}", jobExperienceDTO);
        if (jobExperienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobExperienceDTO result = jobExperienceService.save(jobExperienceDTO);
        return ResponseEntity.created(new URI("/api/job-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-experiences : Updates an existing jobExperience.
     *
     * @param jobExperienceDTO the jobExperienceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobExperienceDTO,
     * or with status 400 (Bad Request) if the jobExperienceDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobExperienceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<JobExperienceDTO> updateJobExperience(@Valid @RequestBody JobExperienceDTO jobExperienceDTO) throws URISyntaxException {
        log.debug("REST request to update JobExperience : {}", jobExperienceDTO);
        if (jobExperienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobExperienceDTO result = jobExperienceService.save(jobExperienceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobExperienceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-experiences : get all the jobExperiences.
     *
     * @param criteria the criterias which the requested entities should match
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobExperiences in body
     */
    @GetMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<JobExperienceDTO>> getAllJobExperiences(JobExperienceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobExperiences by criteria: {}", criteria);
        Page<JobExperienceDTO> page = jobExperienceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-experiences");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /job-experiences/count : count all the jobExperiences.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/count")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Long> countJobExperiences(JobExperienceCriteria criteria) {
        log.debug("REST request to count JobExperiences by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobExperienceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /job-experiences/:id : get the "id" jobExperience.
     *
     * @param id the id of the jobExperienceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobExperienceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobExperienceDTO> getJobExperience(@PathVariable Long id) {
        log.debug("REST request to get JobExperience : {}", id);
        Optional<JobExperienceDTO> jobExperienceDTO = jobExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobExperienceDTO);
    }

    /**
     * DELETE  /job-experiences/:id : delete the "id" jobExperience.
     *
     * @param id the id of the jobExperienceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<Void> deleteJobExperience(@PathVariable Long id) {
        log.debug("REST request to delete JobExperience : {}", id);
        jobExperienceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    

    /**
     * Gets the candidate job experiences.
     *
     * @param idCandidate the id candidate
     * @return the candidate job experiences
     */
    @GetMapping("/candidate/{idCandidate}")
    public ResponseEntity<List<JobExperienceDTO>> getCandidateJobExperiences(@PathVariable Long idCandidate) {
        log.debug("REST request to get Job Experiences by candidate id: {}", idCandidate);
        List<JobExperienceDTO> educations = jobExperienceService.findAllByCandidateId(idCandidate);
        return ResponseEntity.ok().body(educations);
    }
}
