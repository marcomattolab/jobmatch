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
import it.aranciaict.jobmatch.domain.Candidate_;
import it.aranciaict.jobmatch.domain.Skill;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.domain.Skill_;
import it.aranciaict.jobmatch.repository.SkillRepository;
import it.aranciaict.jobmatch.service.dto.SkillCriteria;
import it.aranciaict.jobmatch.service.dto.SkillDTO;
import it.aranciaict.jobmatch.service.mapper.SkillMapper;

// TODO: Auto-generated Javadoc
/**
 * Service for executing complex queries for Skill entities in the database.
 * The main input is a {@link SkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillDTO} or a {@link Page} of {@link SkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillQueryService extends QueryService<Skill> {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(SkillQueryService.class);

    /** The skill repository. */
    private final SkillRepository skillRepository;

    /** The skill mapper. */
    private final SkillMapper skillMapper;

    /**
     * Instantiates a new skill query service.
     *
     * @param skillRepository the skill repository
     * @param skillMapper the skill mapper
     */
    public SkillQueryService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Return a {@link List} of {@link SkillDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillDTO> findByCriteria(SkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillMapper.toDto(skillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillDTO> findByCriteria(SkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.findAll(specification, page)
            .map(skillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.count(specification);
    }

    /**
     * Function to convert SkillCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<Skill> createSpecification(SkillCriteria criteria) {
        Specification<Skill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Skill_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Skill_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Skill_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Skill_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Skill_.lastModifiedDate));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getLevel(), Skill_.level));
            }
            if (criteria.getProficNumberOfYear() != null) {
                specification = specification.and(buildSpecification(criteria.getProficNumberOfYear(), Skill_.proficNumberOfYear));
            }
            if (criteria.getSkillTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillTagId(),
                    root -> root.join(Skill_.skillTag, JoinType.LEFT).get(SkillTag_.id)));
            }
            if (criteria.getCandidateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidateId(),
                    root -> root.join(Skill_.candidate, JoinType.LEFT).get(Candidate_.id)));
            }
        }
        return specification;
    }
}
