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
import it.aranciaict.jobmatch.service.SkillTagQueryService;
import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.service.dto.SkillTagCriteria;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * REST controller for managing SkillTag.
 */
@RestController
@RequestMapping("/api")
public class SkillTagResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(SkillTagResource.class);

    /** The Constant ENTITY_NAME. */
    private static final String ENTITY_NAME = "skillTag";

    /** The skill tag service. */
    private final SkillTagService skillTagService;

    /** The skill tag query service. */
    private final SkillTagQueryService skillTagQueryService;

    /**
     * Instantiates a new skill tag resource.
     *
     * @param skillTagService the skill tag service
     * @param skillTagQueryService the skill tag query service
     */
    public SkillTagResource(SkillTagService skillTagService, SkillTagQueryService skillTagQueryService) {
        this.skillTagService = skillTagService;
        this.skillTagQueryService = skillTagQueryService;
    }

    /**
     * POST  /skill-tags : Create a new skillTag.
     *
     * @param skillTagDTO the skillTagDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillTagDTO, or with status 400 (Bad Request) if the skillTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-tags")
    public ResponseEntity<SkillTagDTO> createSkillTag(@Valid @RequestBody SkillTagDTO skillTagDTO) throws URISyntaxException {
        log.debug("REST request to save SkillTag : {}", skillTagDTO);
        if (skillTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillTagDTO result = skillTagService.save(skillTagDTO);
        return ResponseEntity.created(new URI("/api/skill-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-tags : Updates an existing skillTag.
     *
     * @param skillTagDTO the skillTagDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillTagDTO,
     * or with status 400 (Bad Request) if the skillTagDTO is not valid,
     * or with status 500 (Internal Server Error) if the skillTagDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-tags")
    public ResponseEntity<SkillTagDTO> updateSkillTag(@Valid @RequestBody SkillTagDTO skillTagDTO) throws URISyntaxException {
        log.debug("REST request to update SkillTag : {}", skillTagDTO);
        if (skillTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillTagDTO result = skillTagService.save(skillTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-tags : get all the skillTags.
     *
     * @param criteria the criterias which the requested entities should match
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of skillTags in body
     */
    @GetMapping("/skill-tags")
    public ResponseEntity<List<SkillTagDTO>> getAllSkillTags(SkillTagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SkillTags by criteria: {}", criteria);
        Page<SkillTagDTO> page = skillTagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skill-tags");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /skill-tags/count : count all the skillTags.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/skill-tags/count")
    public ResponseEntity<Long> countSkillTags(SkillTagCriteria criteria) {
        log.debug("REST request to count SkillTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillTagQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /skill-tags/:id : get the "id" skillTag.
     *
     * @param id the id of the skillTagDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillTagDTO, or with status 404 (Not Found)
     */
    @GetMapping("/skill-tags/{id}")
    public ResponseEntity<SkillTagDTO> getSkillTag(@PathVariable Long id) {
        log.debug("REST request to get SkillTag : {}", id);
        Optional<SkillTagDTO> skillTagDTO = skillTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillTagDTO);
    }

    /**
     * DELETE  /skill-tags/:id : delete the "id" skillTag.
     *
     * @param id the id of the skillTagDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-tags/{id}")
    public ResponseEntity<Void> deleteSkillTag(@PathVariable Long id) {
        log.debug("REST request to delete SkillTag : {}", id);
        skillTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
