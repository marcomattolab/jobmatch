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
import it.aranciaict.jobmatch.domain.CompanySector;
import it.aranciaict.jobmatch.domain.CompanySector_;
import it.aranciaict.jobmatch.repository.CompanySectorRepository;
import it.aranciaict.jobmatch.service.dto.CompanySectorCriteria;
import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;
import it.aranciaict.jobmatch.service.mapper.CompanySectorMapper;

/**
 * Service for executing complex queries for CompanySector entities in the
 * database. The main input is a {@link CompanySectorCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanySectorDTO} or a {@link Page} of
 * {@link CompanySectorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanySectorQueryService extends QueryService<CompanySector> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CompanySectorQueryService.class);

	/** The company sector repository. */
	private final CompanySectorRepository companySectorRepository;

	/** The company sector mapper. */
	private final CompanySectorMapper companySectorMapper;

	/**
	 * Instantiates a new company sector query service.
	 *
	 * @param companySectorRepository the company sector repository
	 * @param companySectorMapper     the company sector mapper
	 */
	public CompanySectorQueryService(CompanySectorRepository companySectorRepository,
			CompanySectorMapper companySectorMapper) {
		this.companySectorRepository = companySectorRepository;
		this.companySectorMapper = companySectorMapper;
	}

	/**
	 * Return a {@link List} of {@link CompanySectorDTO} which matches the criteria
	 * from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<CompanySectorDTO> findByCriteria(CompanySectorCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<CompanySector> specification = createSpecification(criteria);
		return companySectorMapper.toDto(companySectorRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link CompanySectorDTO} which matches the criteria
	 * from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<CompanySectorDTO> findByCriteria(CompanySectorCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<CompanySector> specification = createSpecification(criteria);
		return companySectorRepository.findAll(specification, page).map(companySectorMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(CompanySectorCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<CompanySector> specification = createSpecification(criteria);
		return companySectorRepository.count(specification);
	}

	/**
	 * Function to convert CompanySectorCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	private Specification<CompanySector> createSpecification(CompanySectorCriteria criteria) {
		Specification<CompanySector> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildSpecification(criteria.getId(), CompanySector_.id));
			}
			if (criteria.getCreatedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCreatedBy(), CompanySector_.createdBy));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastModifiedBy(), CompanySector_.lastModifiedBy));
			}
			if (criteria.getCreatedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCreatedDate(), CompanySector_.createdDate));
			}
			if (criteria.getLastModifiedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModifiedDate(), CompanySector_.lastModifiedDate));
			}
			if (criteria.getCode() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCode(), CompanySector_.code));
			}
			if (criteria.getDescription() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getDescription(), CompanySector_.descriptionEN)
								.or(buildStringSpecification(criteria.getDescription(), CompanySector_.descriptionIT)));
			}
		}
		return specification;
	}
}
