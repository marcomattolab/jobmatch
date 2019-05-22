package it.aranciaict.jobmatch.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
// for static metamodels
import it.aranciaict.jobmatch.domain.CompanySkill;
import it.aranciaict.jobmatch.domain.CompanySkill_;
import it.aranciaict.jobmatch.domain.Company_;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.repository.CompanySkillRepository;
import it.aranciaict.jobmatch.service.dto.CompanySkillCriteria;
import it.aranciaict.jobmatch.service.dto.CompanySkillDTO;
import it.aranciaict.jobmatch.service.mapper.CompanySkillMapper;

/**
 * Service for executing complex queries for CompanySkill entities in the database.
 * The main input is a {@link CompanySkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanySkillDTO} or a {@link Page} of {@link CompanySkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanySkillQueryService extends QueryService<CompanySkill> {

    private final Logger log = LoggerFactory.getLogger(CompanySkillQueryService.class);

    private final CompanySkillRepository companySkillRepository;

    private final CompanySkillMapper companySkillMapper;

    /**
     * Instantiates a new company skill query service.
     *
     * @param companySkillRepository the company skill repository
     * @param companySkillMapper the company skill mapper
     */
    public CompanySkillQueryService(CompanySkillRepository companySkillRepository, CompanySkillMapper companySkillMapper) {
        this.companySkillRepository = companySkillRepository;
        this.companySkillMapper = companySkillMapper;
    }

    /**
     * Return a {@link List} of {@link CompanySkillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanySkillDTO> findByCriteria(CompanySkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanySkill> specification = createSpecification(criteria);
        return companySkillMapper.toDto(companySkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanySkillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanySkillDTO> findByCriteria(CompanySkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanySkill> specification = createSpecification(criteria);
        return companySkillRepository.findAll(specification, page)
            .map(companySkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanySkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanySkill> specification = createSpecification(criteria);
        return companySkillRepository.count(specification);
    }

    /**
     * Function to convert CompanySkillCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<CompanySkill> createSpecification(CompanySkillCriteria criteria) {
        Specification<CompanySkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CompanySkill_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CompanySkill_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CompanySkill_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), CompanySkill_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), CompanySkill_.lastModifiedDate));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getLevel(), CompanySkill_.level));
            }
            if (criteria.getProficNumberOfYear() != null) {
                specification = specification.and(buildSpecification(criteria.getProficNumberOfYear(), CompanySkill_.proficNumberOfYear));
            }
            if (criteria.getSkillTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillTagId(),
                    root -> root.join(CompanySkill_.skillTag, JoinType.LEFT).get(SkillTag_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(CompanySkill_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
