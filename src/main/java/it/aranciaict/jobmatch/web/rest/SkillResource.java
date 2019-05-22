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
import it.aranciaict.jobmatch.service.SkillQueryService;
import it.aranciaict.jobmatch.service.SkillService;
import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.service.dto.SkillCriteria;
import it.aranciaict.jobmatch.service.dto.SkillDTO;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api/skills")
public class SkillResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "skill";

    /** The skill service. */
    private final SkillService skillService;

    /** The skill tag service. */
    private final SkillTagService skillTagService;

    /** The skill query service. */
    private final SkillQueryService skillQueryService;

    /**
     * Instantiates a new skill resource.
     *
     * @param skillService the skill service
     * @param skillQueryService the skill query service
     * @param skillTagService the skillTagService
     */
    public SkillResource(SkillService skillService, SkillQueryService skillQueryService,
            SkillTagService skillTagService) {
        this.skillService = skillService;
        this.skillQueryService = skillQueryService;
        this.skillTagService = skillTagService;
    }

    /**
     * POST  /skills : Create a new skill.
     *
     * @param skillDTO the skillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillDTO, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<SkillDTO> createSkill(@Valid @RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skillDTO);
        if (skillDTO.getId() != null || !skillDTO.isSkillTagValid()) {
            throw new BadRequestAlertException("A new skill cannot already have an ID or invalid skilltag", ENTITY_NAME, "idexists");
        } 
        if(skillDTO.getSkillTagId() == null) {
            skillDTO.setSkillTagId(skillTagService.save(
                new SkillTagDTO(skillDTO.getSkillTagName(), skillDTO.getSkillTagType())).getId()
            );
        }
        
        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills : Updates an existing skill.
     *
     * @param skillDTO the skillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillDTO,
     * or with status 400 (Bad Request) if the skillDTO is not valid,
     * or with status 500 (Internal Server Error) if the skillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<SkillDTO> updateSkill(@Valid @RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skillDTO);
        if (skillDTO.getId() == null || !skillDTO.isSkillTagValid()) {
            throw new BadRequestAlertException("Invalid id or skillTag", ENTITY_NAME, "idnull");
        }
        if(skillDTO.getSkillTagId() == null) {
            skillDTO.setSkillTagId(skillTagService.save(
                new SkillTagDTO(skillDTO.getSkillTagName(), skillDTO.getSkillTagType())).getId()
            );
        }

        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills : get all the skills.
     *
     * @param criteria the criterias which the requested entities should match
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skills in body
     */
    @GetMapping("")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<SkillDTO>> getAllSkills(SkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Skills by criteria: {}", criteria);
        Page<SkillDTO> page = skillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skills");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /skills/count : count all the skills.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/count")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Long> countSkills(SkillCriteria criteria) {
        log.debug("REST request to count Skills by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /skills/:id : get the "id" skill.
     *
     * @param id the id of the skillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Optional<SkillDTO> skillDTO = skillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillDTO);
    }

    /**
     * DELETE  /skills/:id : delete the "id" skill.
     *
     * @param id the id of the skillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + AuthoritiesConstants.ADMIN + "') or hasRole('" + AuthoritiesConstants.CANDIDATE + "')")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * Gets the candidate job experiences.
     *
     * @param idCandidate the id candidate
     * @return the candidate job experiences
     */
    @GetMapping("/candidate/{idCandidate}")
    public ResponseEntity<List<SkillDTO>> getCandidateJobExperiences(@PathVariable Long idCandidate) {
        log.debug("REST request to get Skills by candidate id: {}", idCandidate);
        List<SkillDTO> skills = skillService.findAllByCandidateId(idCandidate);
        return ResponseEntity.ok().body(skills);
    }

}
