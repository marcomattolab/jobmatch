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
import it.aranciaict.jobmatch.service.CompanySkillQueryService;
import it.aranciaict.jobmatch.service.CompanySkillService;
import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.service.dto.CompanySkillCriteria;
import it.aranciaict.jobmatch.service.dto.CompanySkillDTO;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.web.rest.errors.BadRequestAlertException;
import it.aranciaict.jobmatch.web.rest.util.HeaderUtil;
import it.aranciaict.jobmatch.web.rest.util.PaginationUtil;

/**
 * REST controller for managing CompanySkill.
 */
@RestController
@RequestMapping("/api")
public class CompanySkillResource {

    private final Logger log = LoggerFactory.getLogger(CompanySkillResource.class);

    private static final String ENTITY_NAME = "companySkill";

    private final CompanySkillService companySkillService;

    /** The skill tag service. */
    private final SkillTagService skillTagService;

    private final CompanySkillQueryService companySkillQueryService;

    /**
     * Instantiates a new company skill resource.
     *
     * @param companySkillService the company skill service
     * @param companySkillQueryService the company skill query service
     * @param skillTagService the skillTagService
     */
    public CompanySkillResource(CompanySkillService companySkillService, CompanySkillQueryService companySkillQueryService,
        SkillTagService skillTagService) {
        this.companySkillService = companySkillService;
        this.companySkillQueryService = companySkillQueryService;
        this.skillTagService = skillTagService;
    }

    /**
     * POST  /company-skills : Create a new companySkill.
     *
     * @param companySkillDTO the companySkillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companySkillDTO, or with status 400 (Bad Request) if the companySkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-skills")
    public ResponseEntity<CompanySkillDTO> createCompanySkill(@Valid @RequestBody CompanySkillDTO companySkillDTO) throws URISyntaxException {
        log.debug("REST request to save CompanySkill : {}", companySkillDTO);
        if (companySkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new companySkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(companySkillDTO.getSkillTagId() == null) {
            companySkillDTO.setSkillTagId(skillTagService.save(
                new SkillTagDTO(companySkillDTO.getSkillTagName(), companySkillDTO.getSkillTagType())).getId()
            );
        }

        CompanySkillDTO result = companySkillService.save(companySkillDTO);
        return ResponseEntity.created(new URI("/api/company-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-skills : Updates an existing companySkill.
     *
     * @param companySkillDTO the companySkillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companySkillDTO,
     * or with status 400 (Bad Request) if the companySkillDTO is not valid,
     * or with status 500 (Internal Server Error) if the companySkillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-skills")
    public ResponseEntity<CompanySkillDTO> updateCompanySkill(@Valid @RequestBody CompanySkillDTO companySkillDTO) throws URISyntaxException {
        log.debug("REST request to update CompanySkill : {}", companySkillDTO);
        if (companySkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanySkillDTO result = companySkillService.save(companySkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companySkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-skills : get all the companySkills.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of companySkills in body
     */
    @GetMapping("/company-skills")
    public ResponseEntity<List<CompanySkillDTO>> getAllCompanySkills(CompanySkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompanySkills by criteria: {}", criteria);
        Page<CompanySkillDTO> page = companySkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-skills");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /company-skills/count : count all the companySkills.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/company-skills/count")
    public ResponseEntity<Long> countCompanySkills(CompanySkillCriteria criteria) {
        log.debug("REST request to count CompanySkills by criteria: {}", criteria);
        return ResponseEntity.ok().body(companySkillQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /company-skills/:id : get the "id" companySkill.
     *
     * @param id the id of the companySkillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companySkillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-skills/{id}")
    public ResponseEntity<CompanySkillDTO> getCompanySkill(@PathVariable Long id) {
        log.debug("REST request to get CompanySkill : {}", id);
        Optional<CompanySkillDTO> companySkillDTO = companySkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companySkillDTO);
    }

    /**
     * DELETE  /company-skills/:id : delete the "id" companySkill.
     *
     * @param id the id of the companySkillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-skills/{id}")
    public ResponseEntity<Void> deleteCompanySkill(@PathVariable Long id) {
        log.debug("REST request to delete CompanySkill : {}", id);
        companySkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
