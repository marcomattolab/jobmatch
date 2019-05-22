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
import it.aranciaict.jobmatch.domain.Company_;
import it.aranciaict.jobmatch.domain.DirectApplication;
import it.aranciaict.jobmatch.domain.DirectApplication_;
import it.aranciaict.jobmatch.repository.DirectApplicationRepository;
import it.aranciaict.jobmatch.service.dto.DirectApplicationCriteria;
import it.aranciaict.jobmatch.service.dto.DirectApplicationDTO;
import it.aranciaict.jobmatch.service.mapper.DirectApplicationMapper;

/**
 * Service for executing complex queries for DirectApplication entities in the database.
 * The main input is a {@link DirectApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DirectApplicationDTO} or a {@link Page} of {@link DirectApplicationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DirectApplicationQueryService extends QueryService<DirectApplication> {

    private final Logger log = LoggerFactory.getLogger(DirectApplicationQueryService.class);

    private final DirectApplicationRepository directApplicationRepository;

    private final DirectApplicationMapper directApplicationMapper;

    /**
     * Instantiates a new direct application query service.
     *
     * @param directApplicationRepository the direct application repository
     * @param directApplicationMapper the direct application mapper
     */
    public DirectApplicationQueryService(DirectApplicationRepository directApplicationRepository, DirectApplicationMapper directApplicationMapper) {
        this.directApplicationRepository = directApplicationRepository;
        this.directApplicationMapper = directApplicationMapper;
    }

    /**
     * Return a {@link List} of {@link DirectApplicationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DirectApplicationDTO> findByCriteria(DirectApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DirectApplication> specification = createSpecification(criteria);
        return directApplicationMapper.toDto(directApplicationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DirectApplicationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DirectApplicationDTO> findByCriteria(DirectApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DirectApplication> specification = createSpecification(criteria);
        return directApplicationRepository.findAll(specification, page)
            .map(directApplicationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DirectApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DirectApplication> specification = createSpecification(criteria);
        return directApplicationRepository.count(specification);
    }

    /**
     * Function to convert DirectApplicationCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<DirectApplication> createSpecification(DirectApplicationCriteria criteria) {
        Specification<DirectApplication> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DirectApplication_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), DirectApplication_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), DirectApplication_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), DirectApplication_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), DirectApplication_.lastModifiedDate));
            }
            if (criteria.getAppiedJobStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getAppiedJobStatus(), DirectApplication_.appiedJobStatus));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(DirectApplication_.company, JoinType.LEFT).get(Company_.id)));
            }
            if (criteria.getCandidateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidateId(),
                    root -> root.join(DirectApplication_.candidate, JoinType.LEFT).get(Candidate_.id)));
            }
        }
        return specification;
    }
}
