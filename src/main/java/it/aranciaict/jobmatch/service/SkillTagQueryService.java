package it.aranciaict.jobmatch.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;
// for static metamodels
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.repository.SkillTagRepository;
import it.aranciaict.jobmatch.service.dto.SkillTagCriteria;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.service.mapper.SkillTagMapper;

// TODO: Auto-generated Javadoc
/**
 * Service for executing complex queries for SkillTag entities in the database.
 * The main input is a {@link SkillTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillTagDTO} or a {@link Page} of {@link SkillTagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillTagQueryService extends QueryService<SkillTag> {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(SkillTagQueryService.class);

    /** The skill tag repository. */
    private final SkillTagRepository skillTagRepository;

    /** The skill tag mapper. */
    private final SkillTagMapper skillTagMapper;

    /**
     * Instantiates a new skill tag query service.
     *
     * @param skillTagRepository the skill tag repository
     * @param skillTagMapper the skill tag mapper
     */
    public SkillTagQueryService(SkillTagRepository skillTagRepository, SkillTagMapper skillTagMapper) {
        this.skillTagRepository = skillTagRepository;
        this.skillTagMapper = skillTagMapper;
    }

    /**
     * Return a {@link List} of {@link SkillTagDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillTagDTO> findByCriteria(SkillTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SkillTag> specification = createSpecification(criteria);
        return skillTagMapper.toDto(skillTagRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillTagDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillTagDTO> findByCriteria(SkillTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillTag> specification = createSpecification(criteria);
        return skillTagRepository.findAll(specification, page)
            .map(skillTagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillTag> specification = createSpecification(criteria);
        return skillTagRepository.count(specification);
    }

    /**
     * Function to convert SkillTagCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<SkillTag> createSpecification(SkillTagCriteria criteria) {
        Specification<SkillTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SkillTag_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SkillTag_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SkillTag_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SkillTag_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SkillTag_.lastModifiedDate));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SkillTag_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SkillTag_.type));
            }
        }
        return specification;
    }
}
