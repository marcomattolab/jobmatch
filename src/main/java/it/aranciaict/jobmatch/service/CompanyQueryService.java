package it.aranciaict.jobmatch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.LongFilter;
import it.aranciaict.jobmatch.domain.Candidate;
// for static metamodels
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.CompanySector_;
import it.aranciaict.jobmatch.domain.CompanySkill;
import it.aranciaict.jobmatch.domain.CompanySkill_;
import it.aranciaict.jobmatch.domain.Company_;
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.domain.User_;
import it.aranciaict.jobmatch.domain.SponsoringInstitution_;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.service.dto.CompanyCriteria;
import it.aranciaict.jobmatch.service.dto.CompanyDTO;
import it.aranciaict.jobmatch.service.dto.CompanyItemDTO;
import it.aranciaict.jobmatch.service.mapper.CompanyItemMapper;

/**
 * Service for executing complex queries for Company entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to
 * {@link Specification}, in a way that all the filters must apply. It returns a
 * {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO}
 * which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

	private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

	private static final int NUMBER_OF_SUGGESTED_COMPANIES = 6;

	private final CompanyRepository companyRepository;

	private final CompanyItemMapper companyItemMapper;

	@Autowired
	private CandidateRepository candidateRepository;

	/**
	 * Instantiates a new company query service.
	 *
	 * @param companyRepository the company repository
	 * @param companyItemMapper the company item mapper
	 */
	public CompanyQueryService(CompanyRepository companyRepository, CompanyItemMapper companyItemMapper) {
		this.companyRepository = companyRepository;
		this.companyItemMapper = companyItemMapper;
	}

	/**
	 * Return a {@link List} of {@link CompanyDTO} which matches the criteria from
	 * the database
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<CompanyItemDTO> findByCriteria(CompanyCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<Company> specification = createSpecification(criteria);
		return companyItemMapper.toDto(companyRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from
	 * the database
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<CompanyItemDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<Company> specification = createSpecification(criteria);
		return companyRepository.findAll(specification, page).map(companyItemMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(CompanyCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<Company> specification = createSpecification(criteria);
		return companyRepository.count(specification);
	}

	/**
	 * Find suggested by candidate id.
	 *
	 * @param candidateId the candidate id
	 * @return the list
	 */
	public List<CompanyItemDTO> findSuggestedByCandidateId(Long candidateId) {
		log.debug("find suggested for candidate : {} ", candidateId);
		List<CompanyItemDTO> list = new ArrayList<CompanyItemDTO>();
		Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
		if (!CollectionUtils.isEmpty(candidate.getSkills())) {
			CompanyCriteria criteria = createCriteriaForForCandidateSuggestedCompanies(candidate);
			final Specification<Company> specification = createSpecification(criteria);
			PageRequest pageable = PageRequest.of(0, NUMBER_OF_SUGGESTED_COMPANIES,
					new Sort(Direction.DESC, "createdDate"));
			Page<CompanyItemDTO> page = companyRepository.findAll(specification, pageable)
					.map(companyItemMapper::toDto);
			list.addAll(page.getContent());
		}
		return list;
	}

	/**
	 * Creates the criteria for for candidate suggested offers.
	 *
	 * @param candidate the candidate
	 * @return the job offer criteria
	 */
	private CompanyCriteria createCriteriaForForCandidateSuggestedCompanies(Candidate candidate) {
		CompanyCriteria criteria = new CompanyCriteria();
		// Skill tag condition
		if (!CollectionUtils.isEmpty(candidate.getSkills())) {
			List<Long> tagIds = candidate.getSkills().stream().map(s -> s.getSkillTag().getId())
					.collect(Collectors.toList());
			LongFilter skillTagFilter = new LongFilter();
			skillTagFilter.setIn(tagIds);
			criteria.setSkillTagId(skillTagFilter);
		}
		return criteria;
	}

	/**
	 * Function to convert CompanyCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	@SuppressWarnings("checkstyle:executablestatementcount")
	private Specification<Company> createSpecification(CompanyCriteria criteria) {
		Specification<Company> specification = Specification.where(null);
		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildSpecification(criteria.getId(), Company_.id));
			}
			if (criteria.getCreatedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCreatedBy(), Company_.createdBy));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastModifiedBy(), Company_.lastModifiedBy));
			}
			if (criteria.getCreatedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCreatedDate(), Company_.createdDate));
			}
			if (criteria.getLastModifiedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModifiedDate(), Company_.lastModifiedDate));
			}
			if (criteria.getCompanyName() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCompanyName(), Company_.companyName));
			}
			if (criteria.getCompanySize() != null) {
				specification = specification.and(buildSpecification(criteria.getCompanySize(), Company_.companySize));
			}
			if (criteria.getCompanyType() != null) {
				specification = specification.and(buildSpecification(criteria.getCompanyType(), Company_.companyType));
			}
			if (criteria.getNumberOfEmployee() != null) {
				specification = specification
						.and(buildSpecification(criteria.getNumberOfEmployee(), Company_.numberOfEmployee));
			}
			if (criteria.getStreetAddress() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getStreetAddress(), Company_.streetAddress));
			}
			if (criteria.getFoundationDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getFoundationDate(), Company_.foundationDate));
			}
			if (criteria.getVatNumber() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getVatNumber(), Company_.vatNumber));
			}
			if (criteria.getEmail() != null) {
				specification = specification.and(buildStringSpecification(criteria.getEmail(), Company_.email));
			}
			if (criteria.getPhone() != null) {
				specification = specification.and(buildStringSpecification(criteria.getPhone(), Company_.phone));
			}
			if (criteria.getMobilePhone() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getMobilePhone(), Company_.mobilePhone));
			}
			if (criteria.getCountry() != null) {
				specification = specification.and(buildSpecification(criteria.getCountry(), Company_.country));
			}
			if (criteria.getRegion() != null) {
				specification = specification.and(buildStringSpecification(criteria.getRegion(), Company_.region));
			}
			if (criteria.getProvince() != null) {
				specification = specification.and(buildStringSpecification(criteria.getProvince(), Company_.province));
			}
			if (criteria.getCity() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCity(), Company_.city));
			}
			if (criteria.getCap() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCap(), Company_.cap));
			}
			if (criteria.getUrlSite() != null) {
				specification = specification.and(buildStringSpecification(criteria.getUrlSite(), Company_.urlSite));
			}
			if (criteria.getEnabled() != null) {
				specification = specification.and(buildSpecification(criteria.getEnabled(), Company_.enabled));
			}
			if (criteria.getUserId() != null) {
				specification = specification.and(buildSpecification(criteria.getUserId(),
						root -> root.join(Company_.user, JoinType.LEFT).get(User_.id)));
			}
			if (criteria.getSectorId() != null) {
				specification = specification.and(buildSpecification(criteria.getSectorId(),
						root -> root.join(Company_.sector, JoinType.LEFT).get(CompanySector_.id)));
//                specification = specification.and(buildSpecification(criteria.getSectorId(),
//                    root -> root.join(Company_.sectors, JoinType.LEFT).get(CompanySector_.id)));
			}
			if (criteria.getSkillTagId() != null) {
				specification = specification.and(getSkillTagSpecification(criteria.getSkillTagId()));
			}
		    if (criteria.getSponsoringInstitutionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSponsoringInstitutionId(),
                    root -> root.join(Company_.sponsoringInstitution, JoinType.LEFT).get(SponsoringInstitution_.id)));
            }
		}
		return specification;
	}

	/**
	 * Gets the skill tag specification.
	 *
	 * @param skillTagId the skill tag id
	 * @return the skill tag specification
	 */
	public Specification<Company> getSkillTagSpecification(LongFilter skillTagId) {
		return (root, query, criteriaBuilder) -> {
			Join<CompanySkill, SkillTag> skillTagJoin = root.join(Company_.skills, JoinType.LEFT)
					.join(CompanySkill_.skillTag);
			Predicate predicate = null;
			if (skillTagId.getEquals() != null) {
				predicate = criteriaBuilder.equal(skillTagJoin.get(SkillTag_.id), skillTagId.getEquals());
			} else if (skillTagId.getIn() != null) {
				predicate = skillTagJoin.get(SkillTag_.id).in(skillTagId.getIn());
			}
			query.distinct(true);
			return predicate;
		};
	}

}
