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
import it.aranciaict.jobmatch.domain.JobExperience;
import it.aranciaict.jobmatch.domain.JobExperience_;
import it.aranciaict.jobmatch.repository.JobExperienceRepository;
import it.aranciaict.jobmatch.service.dto.JobExperienceCriteria;
import it.aranciaict.jobmatch.service.dto.JobExperienceDTO;
import it.aranciaict.jobmatch.service.mapper.JobExperienceMapper;

// TODO: Auto-generated Javadoc
/**
 * Service for executing complex queries for JobExperience entities in the database.
 * The main input is a {@link JobExperienceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobExperienceDTO} or a {@link Page} of {@link JobExperienceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobExperienceQueryService extends QueryService<JobExperience> {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(JobExperienceQueryService.class);

    /** The job experience repository. */
    private final JobExperienceRepository jobExperienceRepository;

    /** The job experience mapper. */
    private final JobExperienceMapper jobExperienceMapper;

    /**
     * Instantiates a new job experience query service.
     *
     * @param jobExperienceRepository the job experience repository
     * @param jobExperienceMapper the job experience mapper
     */
    public JobExperienceQueryService(JobExperienceRepository jobExperienceRepository, JobExperienceMapper jobExperienceMapper) {
        this.jobExperienceRepository = jobExperienceRepository;
        this.jobExperienceMapper = jobExperienceMapper;
    }

    /**
     * Return a {@link List} of {@link JobExperienceDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobExperienceDTO> findByCriteria(JobExperienceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobExperience> specification = createSpecification(criteria);
        return jobExperienceMapper.toDto(jobExperienceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobExperienceDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobExperienceDTO> findByCriteria(JobExperienceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobExperience> specification = createSpecification(criteria);
        return jobExperienceRepository.findAll(specification, page)
            .map(jobExperienceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobExperienceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobExperience> specification = createSpecification(criteria);
        return jobExperienceRepository.count(specification);
    }

    /**
     * Function to convert JobExperienceCriteria to a {@link Specification}.
     *
     * @param criteria the criteria
     * @return the specification
     */
    private Specification<JobExperience> createSpecification(JobExperienceCriteria criteria) {
        Specification<JobExperience> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JobExperience_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), JobExperience_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), JobExperience_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), JobExperience_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), JobExperience_.lastModifiedDate));
            }
            if (criteria.getJobTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobTitle(), JobExperience_.jobTitle));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), JobExperience_.companyName));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), JobExperience_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), JobExperience_.endDate));
            }
            if (criteria.getCurrent() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrent(), JobExperience_.current));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), JobExperience_.enabled));
            }
            if (criteria.getCandidateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCandidateId(),
                    root -> root.join(JobExperience_.candidate, JoinType.LEFT).get(Candidate_.id)));
            }
        }
        return specification;
    }
}
