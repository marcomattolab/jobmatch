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
import it.aranciaict.jobmatch.domain.AppliedJobIteration;
import it.aranciaict.jobmatch.domain.AppliedJobIteration_;
import it.aranciaict.jobmatch.domain.AppliedJob_;
import it.aranciaict.jobmatch.repository.AppliedJobIterationRepository;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationCriteria;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationDTO;
import it.aranciaict.jobmatch.service.mapper.AppliedJobIterationMapper;

/**
 * Service for executing complex queries for AppliedJobIteration entities in the
 * database. The main input is a {@link AppliedJobIterationCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppliedJobIterationDTO} or a {@link Page}
 * of {@link AppliedJobIterationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppliedJobIterationQueryService extends QueryService<AppliedJobIteration> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(AppliedJobIterationQueryService.class);

	/** The applied job iteration repository. */
	private final AppliedJobIterationRepository appliedJobIterationRepository;

	/** The applied job iteration mapper. */
	private final AppliedJobIterationMapper appliedJobIterationMapper;

	/**
	 * Instantiates a new applied job iteration query service.
	 *
	 * @param appliedJobIterationRepository the applied job iteration repository
	 * @param appliedJobIterationMapper the applied job iteration mapper
	 */
	public AppliedJobIterationQueryService(AppliedJobIterationRepository appliedJobIterationRepository,
			AppliedJobIterationMapper appliedJobIterationMapper) {
		this.appliedJobIterationRepository = appliedJobIterationRepository;
		this.appliedJobIterationMapper = appliedJobIterationMapper;
	}

	/**
	 * Return a {@link List} of {@link AppliedJobIterationDTO} which matches the
	 * criteria from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<AppliedJobIterationDTO> findByCriteria(AppliedJobIterationCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<AppliedJobIteration> specification = createSpecification(criteria);
		return appliedJobIterationMapper.toDto(appliedJobIterationRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link AppliedJobIterationDTO} which matches the
	 * criteria from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<AppliedJobIterationDTO> findByCriteria(AppliedJobIterationCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<AppliedJobIteration> specification = createSpecification(criteria);
		return appliedJobIterationRepository.findAll(specification, page).map(appliedJobIterationMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(AppliedJobIterationCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<AppliedJobIteration> specification = createSpecification(criteria);
		return appliedJobIterationRepository.count(specification);
	}

	/**
	 * Function to convert AppliedJobIterationCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	private Specification<AppliedJobIteration> createSpecification(AppliedJobIterationCriteria criteria) {
		Specification<AppliedJobIteration> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildSpecification(criteria.getId(), AppliedJobIteration_.id));
			}
			if (criteria.getCreatedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCreatedBy(), AppliedJobIteration_.createdBy));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification.and(
						buildStringSpecification(criteria.getLastModifiedBy(), AppliedJobIteration_.lastModifiedBy));
			}
			if (criteria.getCreatedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCreatedDate(), AppliedJobIteration_.createdDate));
			}
			if (criteria.getLastModifiedDate() != null) {
				specification = specification.and(
						buildRangeSpecification(criteria.getLastModifiedDate(), AppliedJobIteration_.lastModifiedDate));
			}
			if (criteria.getIterationDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getIterationDate(), AppliedJobIteration_.iterationDate));
			}
			if (criteria.getIterationType() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getIterationType(), AppliedJobIteration_.iterationType));
			}
			if (criteria.getAppliedJobId() != null) {
				specification = specification.and(buildSpecification(criteria.getAppliedJobId(),
						root -> root.join(AppliedJobIteration_.appliedJob, JoinType.LEFT).get(AppliedJob_.id)));
			}
		}
		return specification;
	}
}
