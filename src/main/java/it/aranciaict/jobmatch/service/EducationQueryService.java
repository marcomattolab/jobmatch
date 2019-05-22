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
import it.aranciaict.jobmatch.domain.Education;
import it.aranciaict.jobmatch.domain.Education_;
import it.aranciaict.jobmatch.repository.EducationRepository;
import it.aranciaict.jobmatch.service.dto.EducationCriteria;
import it.aranciaict.jobmatch.service.dto.EducationDTO;
import it.aranciaict.jobmatch.service.mapper.EducationMapper;

/**
 * Service for executing complex queries for Education entities in the database.
 * The main input is a {@link EducationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EducationDTO} or a {@link Page} of {@link EducationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EducationQueryService extends QueryService<Education> {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(EducationQueryService.class);

    /** The education repository. */
    private final EducationRepository educationRepository;

    /** The education mapper. */
    private final EducationMapper educationMapper;

    /**
     * Instantiates a new education query service.
     *
     * @param educationRepository the education repository
     * @param educationMapper the education mapper
     */
    public EducationQueryService(EducationRepository educationRepository, EducationMapper educationMapper) {
        this.educationRepository = educationRepository;
        this.educationMapper = educationMapper;
    }

    /**
     * Return a {@link List} of {@link EducationDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EducationDTO> findByCriteria(EducationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Education> specification = createSpecification(criteria);
        return educationMapper.toDto(educationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EducationDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EducationDTO> findByCriteria(EducationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Education> specification = createSpecification(criteria);
        return educationRepository.findAll(specification, page)
            .map(educationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EducationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Education> specification = createSpecification(criteria);
        return educationRepository.count(specification);
    }

    /**
     * Function to convert EducationCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<Education> createSpecification(EducationCriteria criteria) {
        Specification<Education> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Education_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Education_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Education_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Education_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Education_.lastModifiedDate));
            }
            if (criteria.getSchoolName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchoolName(), Education_.schoolName));
            }
            if (criteria.getFieldOfStudy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldOfStudy(), Education_.fieldOfStudy));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Education_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Education_.endDate));
            }
            if (criteria.getCurrent() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrent(), Education_.current));
            }
            if (criteria.getCandidateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidateId(),
                    root -> root.join(Education_.candidate, JoinType.LEFT).get(Candidate_.id)));
            }
        }
        return specification;
    }
}
