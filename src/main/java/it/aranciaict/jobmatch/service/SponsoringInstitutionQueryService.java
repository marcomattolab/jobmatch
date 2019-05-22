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
import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.domain.SponsoringInstitution_;
import it.aranciaict.jobmatch.domain.User_;
import it.aranciaict.jobmatch.repository.SponsoringInstitutionRepository;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionCriteria;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionDTO;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionItemDTO;
import it.aranciaict.jobmatch.service.mapper.SponsoringInstitutionItemMapper;

/**
 * Service for executing complex queries for SponsoringInstitution entities in
 * the database. The main input is a {@link SponsoringInstitutionCriteria} which
 * gets converted to {@link Specification}, in a way that all the filters must
 * apply. It returns a {@link List} of {@link SponsoringInstitutionDTO} or a
 * {@link Page} of {@link SponsoringInstitutionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SponsoringInstitutionQueryService extends QueryService<SponsoringInstitution> {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SponsoringInstitutionQueryService.class);

	/** The sponsoring institution repository. */
	private final SponsoringInstitutionRepository sponsoringInstitutionRepository;

	/** The sponsoring institution mapper. */
	private final SponsoringInstitutionItemMapper sponsoringInstitutionItemMapper;

	/**
	 * Instantiates a new sponsoring institution query service.
	 *
	 * @param sponsoringInstitutionRepository the sponsoring institution repository
	 * @param sponsoringInstitutionItemMapper the sponsoring institution item mapper
	 */
	public SponsoringInstitutionQueryService(SponsoringInstitutionRepository sponsoringInstitutionRepository,
			SponsoringInstitutionItemMapper sponsoringInstitutionItemMapper) {
		this.sponsoringInstitutionRepository = sponsoringInstitutionRepository;
		this.sponsoringInstitutionItemMapper = sponsoringInstitutionItemMapper;
	}

	/**
	 * Return a {@link List} of {@link SponsoringInstitutionDTO} which matches the
	 * criteria from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<SponsoringInstitutionItemDTO> findByCriteria(SponsoringInstitutionCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<SponsoringInstitution> specification = createSpecification(criteria);
		return sponsoringInstitutionItemMapper.toDto(sponsoringInstitutionRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link SponsoringInstitutionDTO} which matches the
	 * criteria from the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<SponsoringInstitutionItemDTO> findByCriteria(SponsoringInstitutionCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<SponsoringInstitution> specification = createSpecification(criteria);
		return sponsoringInstitutionRepository.findAll(specification, page).map(sponsoringInstitutionItemMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(SponsoringInstitutionCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<SponsoringInstitution> specification = createSpecification(criteria);
		return sponsoringInstitutionRepository.count(specification);
	}

	/**
	 * Function to convert SponsoringInstitutionCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	@SuppressWarnings("checkstyle:executablestatementcount")
	private Specification<SponsoringInstitution> createSpecification(SponsoringInstitutionCriteria criteria) {
		Specification<SponsoringInstitution> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildSpecification(criteria.getId(), SponsoringInstitution_.id));
			}
			if (criteria.getCreatedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCreatedBy(), SponsoringInstitution_.createdBy));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification.and(
						buildStringSpecification(criteria.getLastModifiedBy(), SponsoringInstitution_.lastModifiedBy));
			}
			if (criteria.getCreatedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCreatedDate(), SponsoringInstitution_.createdDate));
			}
			if (criteria.getLastModifiedDate() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(),
						SponsoringInstitution_.lastModifiedDate));
			}
			if (criteria.getIstituitionName() != null) {
				specification = specification.and(buildStringSpecification(criteria.getIstituitionName(),
						SponsoringInstitution_.istituitionName));
			}
			if (criteria.getIstituitionSector() != null) {
				specification = specification.and(
						buildSpecification(criteria.getIstituitionSector(), SponsoringInstitution_.istituitionSector));
			}
			if (criteria.getIstituitionType() != null) {
				specification = specification
						.and(buildSpecification(criteria.getIstituitionType(), SponsoringInstitution_.istituitionType));
			}
			if (criteria.getStreetAddress() != null) {
				specification = specification.and(
						buildStringSpecification(criteria.getStreetAddress(), SponsoringInstitution_.streetAddress));
			}
			if (criteria.getFoundationDate() != null) {
				specification = specification.and(
						buildRangeSpecification(criteria.getFoundationDate(), SponsoringInstitution_.foundationDate));
			}
			if (criteria.getVatNumber() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getVatNumber(), SponsoringInstitution_.vatNumber));
			}
			if (criteria.getEmail() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getEmail(), SponsoringInstitution_.email));
			}
			if (criteria.getPhone() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getPhone(), SponsoringInstitution_.phone));
			}
			if (criteria.getMobilePhone() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getMobilePhone(), SponsoringInstitution_.mobilePhone));
			}
			if (criteria.getCountry() != null) {
				specification = specification
						.and(buildSpecification(criteria.getCountry(), SponsoringInstitution_.country));
			}
			if (criteria.getRegion() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getRegion(), SponsoringInstitution_.region));
			}
			if (criteria.getProvince() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getProvince(), SponsoringInstitution_.province));
			}
			if (criteria.getCity() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCity(), SponsoringInstitution_.city));
			}
			if (criteria.getCap() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCap(), SponsoringInstitution_.cap));
			}
			if (criteria.getUrlSite() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getUrlSite(), SponsoringInstitution_.urlSite));
			}
			if (criteria.getEnabled() != null) {
				specification = specification
						.and(buildSpecification(criteria.getEnabled(), SponsoringInstitution_.enabled));
			}
			if (criteria.getUserId() != null) {
				specification = specification.and(buildSpecification(criteria.getUserId(),
						root -> root.join(SponsoringInstitution_.user, JoinType.LEFT).get(User_.id)));
			}
		}
		return specification;
	}
}
