/*
 * 
 */
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
import it.aranciaict.jobmatch.domain.JobOfferSkill;
import it.aranciaict.jobmatch.domain.JobOfferSkill_;
import it.aranciaict.jobmatch.domain.JobOffer_;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.repository.JobOfferSkillRepository;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillCriteria;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;
import it.aranciaict.jobmatch.service.mapper.JobOfferSkillMapper;

// TODO: Auto-generated Javadoc
/**
 * Service for executing complex queries for JobOfferSkill entities in the database.
 * The main input is a {@link JobOfferSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobOfferSkillDTO} or a {@link Page} of {@link JobOfferSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobOfferSkillQueryService extends QueryService<JobOfferSkill> {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(JobOfferSkillQueryService.class);

    /** The job offer skill repository. */
    private final JobOfferSkillRepository jobOfferSkillRepository;

    /** The job offer skill mapper. */
    private final JobOfferSkillMapper jobOfferSkillMapper;

    /**
     * Instantiates a new job offer skill query service.
     *
     * @param jobOfferSkillRepository the job offer skill repository
     * @param jobOfferSkillMapper the job offer skill mapper
     */
    public JobOfferSkillQueryService(JobOfferSkillRepository jobOfferSkillRepository, JobOfferSkillMapper jobOfferSkillMapper) {
        this.jobOfferSkillRepository = jobOfferSkillRepository;
        this.jobOfferSkillMapper = jobOfferSkillMapper;
    }

    /**
     * Return a {@link List} of {@link JobOfferSkillDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobOfferSkillDTO> findByCriteria(JobOfferSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobOfferSkill> specification = createSpecification(criteria);
        return jobOfferSkillMapper.toDto(jobOfferSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link List} of {@link JobOfferSkillDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobOfferSkill> findByCriteriaEntities(JobOfferSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobOfferSkill> specification = createSpecification(criteria);
        return jobOfferSkillRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link JobOfferSkillDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobOfferSkillDTO> findByCriteria(JobOfferSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobOfferSkill> specification = createSpecification(criteria);
        return jobOfferSkillRepository.findAll(specification, page)
            .map(jobOfferSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobOfferSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobOfferSkill> specification = createSpecification(criteria);
        return jobOfferSkillRepository.count(specification);
    }

    /**
     * Function to convert JobOfferSkillCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<JobOfferSkill> createSpecification(JobOfferSkillCriteria criteria) {
        Specification<JobOfferSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JobOfferSkill_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), JobOfferSkill_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), JobOfferSkill_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), JobOfferSkill_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), JobOfferSkill_.lastModifiedDate));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getLevel(), JobOfferSkill_.level));
            }
            if (criteria.getProficNumberOfYear() != null) {
                specification = specification.and(buildSpecification(criteria.getProficNumberOfYear(), JobOfferSkill_.proficNumberOfYear));
            }
            if (criteria.getSkillTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillTagId(),
                    root -> root.join(JobOfferSkill_.skillTag, JoinType.LEFT).get(SkillTag_.id)));
            }
            if (criteria.getJobOfferId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobOfferId(),
                    root -> root.join(JobOfferSkill_.jobOffer, JoinType.LEFT).get(JobOffer_.id)));
            }
        }
        return specification;
    }
}
