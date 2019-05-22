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
import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.domain.AppliedJobIteration_;
import it.aranciaict.jobmatch.domain.AppliedJob_;
import it.aranciaict.jobmatch.domain.Candidate_;
import it.aranciaict.jobmatch.domain.Company_;
import it.aranciaict.jobmatch.domain.JobOffer_;
import it.aranciaict.jobmatch.repository.AppliedJobRepository;
import it.aranciaict.jobmatch.service.dto.AppliedJobCriteria;
import it.aranciaict.jobmatch.service.dto.AppliedJobDTO;
import it.aranciaict.jobmatch.service.dto.AppliedJobItemDTO;
import it.aranciaict.jobmatch.service.mapper.AppliedJobItemMapper;
import it.aranciaict.jobmatch.service.mapper.AppliedJobMapper;

/**
 * Service for executing complex queries for AppliedJob entities in the
 * database. The main input is a {@link AppliedJobCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It
 * returns a {@link List} of {@link AppliedJobDTO} or a {@link Page} of
 * {@link AppliedJobDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppliedJobQueryService extends QueryService<AppliedJob> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(AppliedJobQueryService.class);

	/** The applied job repository. */
	private final AppliedJobRepository appliedJobRepository;

	/** The applied job mapper. */
	private final AppliedJobMapper appliedJobMapper;

	/** The applied job mapper. */
	private final AppliedJobItemMapper appliedJobItemMapper;

	/**
	 * Instantiates a new applied job query service.
	 *
	 * @param appliedJobRepository the applied job repository
	 * @param appliedJobMapper     the applied job mapper
	 * @param appliedJobItemMapper the applied job item mapper
	 */
	public AppliedJobQueryService(AppliedJobRepository appliedJobRepository, AppliedJobMapper appliedJobMapper,
			AppliedJobItemMapper appliedJobItemMapper) {
		this.appliedJobRepository = appliedJobRepository;
		this.appliedJobMapper = appliedJobMapper;
		this.appliedJobItemMapper = appliedJobItemMapper;
	}

	/**
	 * Return a {@link List} of {@link AppliedJobDTO} which matches the criteria
	 * from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<AppliedJobDTO> findByCriteria(AppliedJobCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<AppliedJob> specification = createSpecification(criteria);
		return appliedJobMapper.toDto(appliedJobRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link AppliedJobDTO} which matches the criteria
	 * from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<AppliedJobDTO> findByCriteria(AppliedJobCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<AppliedJob> specification = createSpecification(criteria);
		return appliedJobRepository.findAll(specification, page).map(appliedJobMapper::toDto);
	}

	/**
	 * Return a {@link Page} of {@link AppliedJobDTO} which matches the criteria
	 * from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<AppliedJobItemDTO> findItemsByCriteria(AppliedJobCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<AppliedJob> specification = createSpecification(criteria);
		return appliedJobRepository.findAll(specification, page).map(appliedJobItemMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(AppliedJobCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<AppliedJob> specification = createSpecification(criteria);
		return appliedJobRepository.count(specification);
	}

	/**
	 * Function to convert AppliedJobCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	private Specification<AppliedJob> createSpecification(AppliedJobCriteria criteria) {
		Specification<AppliedJob> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildSpecification(criteria.getId(), AppliedJob_.id));
			}
			if (criteria.getCreatedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCreatedBy(), AppliedJob_.createdBy));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastModifiedBy(), AppliedJob_.lastModifiedBy));
			}
			if (criteria.getCreatedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCreatedDate(), AppliedJob_.createdDate));
			}
			if (criteria.getLastModifiedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModifiedDate(), AppliedJob_.lastModifiedDate));
			}
			if (criteria.getAppliedJobFeedback() != null) {
				specification = specification
						.and(buildSpecification(criteria.getAppliedJobFeedback(), AppliedJob_.appliedJobFeedback));
			}
			if (criteria.getAppliedJobStatus() != null) {
				specification = specification
						.and(buildSpecification(criteria.getAppliedJobStatus(), AppliedJob_.appiedJobStatus));
			}
			if (criteria.getAppliedJobIterationId() != null) {
				specification = specification.and(buildSpecification(criteria.getAppliedJobIterationId(), root -> root
						.join(AppliedJob_.appliedJobIterations, JoinType.LEFT).get(AppliedJobIteration_.id)));
			}
			if (criteria.getCandidateId() != null) {
				specification = specification.and(buildSpecification(criteria.getCandidateId(),
						root -> root.join(AppliedJob_.candidate, JoinType.LEFT).get(Candidate_.id)));
			}
			if (criteria.getJobOfferId() != null) {
				specification = specification.and(buildSpecification(criteria.getJobOfferId(),
						root -> root.join(AppliedJob_.jobOffer, JoinType.LEFT).get(JobOffer_.id)));
			}
			if (criteria.getJobOffer() != null) {
				specification = specification.and(buildSpecification(criteria.getJobOffer(),
						root -> root.join(AppliedJob_.jobOffer, JoinType.LEFT).get(JobOffer_.jobTitle)));
			}
			if (criteria.getCompanyId() != null) {
				specification = specification.and(buildSpecification(criteria.getCompanyId(),
						root -> root.join(AppliedJob_.jobOffer, JoinType.LEFT).join(JobOffer_.company, JoinType.INNER)
								.get(Company_.id)));
			}
		}
		return specification;
	}
}
