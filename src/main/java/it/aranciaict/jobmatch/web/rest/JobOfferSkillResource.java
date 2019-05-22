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
import it.aranciaict.jobmatch.service.JobOfferSkillQueryService;
import it.aranciaict.jobmatch.service.JobOfferSkillService;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillCriteria;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing JobOfferSkill.
 */
@RestController
@RequestMapping("/api")
public class JobOfferSkillResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(JobOfferSkillResource.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "jobOfferSkill";

    /** The job offer skill service. */
    private final JobOfferSkillService jobOfferSkillService;

    /** The job offer skill query service. */
    private final JobOfferSkillQueryService jobOfferSkillQueryService;

    /**
     * Instantiates a new job offer skill resource.
     *
     * @param jobOfferSkillService the job offer skill service
     * @param jobOfferSkillQueryService the job offer skill query service
     */
    public JobOfferSkillResource(JobOfferSkillService jobOfferSkillService, JobOfferSkillQueryService jobOfferSkillQueryService) {
        this.jobOfferSkillService = jobOfferSkillService;
        this.jobOfferSkillQueryService = jobOfferSkillQueryService;
    }

    /**
     * POST  /job-offer-skills : Create a new jobOfferSkill.
     *
     * @param jobOfferSkillDTO the jobOfferSkillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobOfferSkillDTO, or with status 400 (Bad Request) if the jobOfferSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-offer-skills")
    public ResponseEntity<JobOfferSkillDTO> createJobOfferSkill(@Valid @RequestBody JobOfferSkillDTO jobOfferSkillDTO) throws URISyntaxException {
        log.debug("REST request to save JobOfferSkill : {}", jobOfferSkillDTO);
        if (jobOfferSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobOfferSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobOfferSkillDTO result = jobOfferSkillService.save(jobOfferSkillDTO);
        return ResponseEntity.created(new URI("/api/job-offer-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-offer-skills : Updates an existing jobOfferSkill.
     *
     * @param jobOfferSkillDTO the jobOfferSkillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobOfferSkillDTO,
     * or with status 400 (Bad Request) if the jobOfferSkillDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobOfferSkillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-offer-skills")
    public ResponseEntity<JobOfferSkillDTO> updateJobOfferSkill(@Valid @RequestBody JobOfferSkillDTO jobOfferSkillDTO) throws URISyntaxException {
        log.debug("REST request to update JobOfferSkill : {}", jobOfferSkillDTO);
        if (jobOfferSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobOfferSkillDTO result = jobOfferSkillService.save(jobOfferSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobOfferSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-offer-skills : get all the jobOfferSkills.
     *
     * @param criteria the criterias which the requested entities should match
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobOfferSkills in body
     */
    @GetMapping("/job-offer-skills")
    public ResponseEntity<List<JobOfferSkillDTO>> getAllJobOfferSkills(JobOfferSkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobOfferSkills by criteria: {}", criteria);
        Page<JobOfferSkillDTO> page = jobOfferSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-offer-skills");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /job-offer-skills/count : count all the jobOfferSkills.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/job-offer-skills/count")
    public ResponseEntity<Long> countJobOfferSkills(JobOfferSkillCriteria criteria) {
        log.debug("REST request to count JobOfferSkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobOfferSkillQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /job-offer-skills/:id : get the "id" jobOfferSkill.
     *
     * @param id the id of the jobOfferSkillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobOfferSkillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/job-offer-skills/{id}")
    public ResponseEntity<JobOfferSkillDTO> getJobOfferSkill(@PathVariable Long id) {
        log.debug("REST request to get JobOfferSkill : {}", id);
        Optional<JobOfferSkillDTO> jobOfferSkillDTO = jobOfferSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobOfferSkillDTO);
    }

    /**
     * DELETE  /job-offer-skills/:id : delete the "id" jobOfferSkill.
     *
     * @param id the id of the jobOfferSkillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-offer-skills/{id}")
    public ResponseEntity<Void> deleteJobOfferSkill(@PathVariable Long id) {
        log.debug("REST request to delete JobOfferSkill : {}", id);
        jobOfferSkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
