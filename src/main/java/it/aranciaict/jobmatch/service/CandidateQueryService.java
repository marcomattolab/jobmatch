package it.aranciaict.jobmatch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

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
import io.github.jhipster.service.filter.StringFilter;
// for static metamodels
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.Candidate_;
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.Document_;
import it.aranciaict.jobmatch.domain.Education_;
import it.aranciaict.jobmatch.domain.JobExperience;
import it.aranciaict.jobmatch.domain.JobExperience_;
import it.aranciaict.jobmatch.domain.Skill;
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.SkillTag_;
import it.aranciaict.jobmatch.domain.Skill_;
import it.aranciaict.jobmatch.domain.User_;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.service.dto.CandidateCriteria;
import it.aranciaict.jobmatch.service.dto.CandidateCriteria.CountryFilter;
import it.aranciaict.jobmatch.service.dto.CandidateDTO;
import it.aranciaict.jobmatch.service.dto.CandidateItemDTO;
import it.aranciaict.jobmatch.service.mapper.CandidateItemMapper;

/**
 * Service for executing complex queries for Candidate entities in the database.
 * The main input is a {@link CandidateCriteria} which gets converted to
 * {@link Specification}, in a way that all the filters must apply. It returns a
 * {@link List} of {@link CandidateDTO} or a {@link Page} of
 * {@link CandidateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CandidateQueryService extends QueryService<Candidate> {

	private static final int NUMBER_OF_SUGGESTED_CANDIDATES = 6;
	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CandidateQueryService.class);

	/** The candidate repository. */
	private final CandidateRepository candidateRepository;

	/** The candidate mapper. */
	private final CandidateItemMapper candidateItemMapper;

	/** The company repository. */
	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * Instantiates a new candidate query service.
	 *
	 * @param candidateRepository the candidate repository
	 * @param candidateItemMapper the candidate item mapper
	 */
	public CandidateQueryService(CandidateRepository candidateRepository, CandidateItemMapper candidateItemMapper) {
		this.candidateRepository = candidateRepository;
		this.candidateItemMapper = candidateItemMapper;
	}

	/**
	 * Return a {@link List} of {@link CandidateDTO} which matches the criteria from
	 * the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public List<CandidateItemDTO> findByCriteria(CandidateCriteria criteria) {
		log.debug("find by criteria : {}", criteria);
		final Specification<Candidate> specification = createSpecification(criteria);
		return candidateItemMapper.toDto(candidateRepository.findAll(specification));
	}

	/**
	 * Return a {@link Page} of {@link CandidateDTO} which matches the criteria from
	 * the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<CandidateItemDTO> findByCriteria(CandidateCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<Candidate> specification = createSpecification(criteria);
		return candidateRepository.findAll(specification, page).map(candidateItemMapper::toDto);
	}

	/**
	 * Find suggested by company id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<CandidateItemDTO> findSuggestedByCompanyId(Long companyId) {
		log.debug("find suggested candidates for company : {} ", companyId);
		List<CandidateItemDTO> list = new ArrayList<CandidateItemDTO>();
		Company company = companyRepository.findById(companyId).orElse(null);
		CandidateCriteria criteria = createCriteriaForCompanySuggestedCandidate(company);
		final Specification<Candidate> specification = createSpecification(criteria);
		PageRequest pageable = PageRequest.of(0, NUMBER_OF_SUGGESTED_CANDIDATES,
				new Sort(Direction.DESC, "createdDate"));
		Page<CandidateItemDTO> page = candidateRepository.findAll(specification, pageable)
				.map(candidateItemMapper::toDto);
		list.addAll(page.getContent());
		return list;
	}

	/**
	 * Creates the criteria for company suggested candidate.
	 *
	 * @param company the company
	 * @return the candidate criteria
	 */
	private CandidateCriteria createCriteriaForCompanySuggestedCandidate(Company company) {
		CandidateCriteria criteria = new CandidateCriteria();
		// Skill tag condition
		if (!CollectionUtils.isEmpty(company.getSkills())) {
			List<Long> tagIds = company.getSkills().stream().map(s -> s.getSkillTag().getId())
					.collect(Collectors.toList());
			LongFilter skillTagFilter = new LongFilter();
			skillTagFilter.setIn(tagIds);
			criteria.setSkillTagId(skillTagFilter);
		}
		// I Candidati suggeriti devono possedere almeno una delle skill dell'azienda
		criteria.setMacthAllSkillTag(false);
		return criteria;
	}

	/**
	 * Return the number of matching entities in the database.
	 *
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(CandidateCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<Candidate> specification = createSpecification(criteria);
		return candidateRepository.count(specification);
	}

	/**
	 * Function to convert CandidateCriteria to a {@link Specification}.
	 *
	 * @param criteria the criteria
	 * @return the specification
	 */
	@SuppressWarnings("checkstyle:executablestatementcount")
	private Specification<Candidate> createSpecification(CandidateCriteria criteria) {
		Specification<Candidate> specification = Specification.where(null);

		if (criteria != null) {
			if (criteria.getId() != null) {
				specification = specification.and(buildSpecification(criteria.getId(), Candidate_.id));
			}
			if (criteria.getCreatedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getCreatedBy(), Candidate_.createdBy));
			}
			if (criteria.getLastModifiedBy() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastModifiedBy(), Candidate_.lastModifiedBy));
			}
			if (criteria.getCreatedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getCreatedDate(), Candidate_.createdDate));
			}
			if (criteria.getLastModifiedDate() != null) {
				specification = specification
						.and(buildRangeSpecification(criteria.getLastModifiedDate(), Candidate_.lastModifiedDate));
			}
			if (criteria.getLastName() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getLastName(), Candidate_.lastName));
			}
			if (criteria.getFirstName() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getFirstName(), Candidate_.firstName));
			}
			if (criteria.getGender() != null) {
				specification = specification.and(buildSpecification(criteria.getGender(), Candidate_.gender));
			}
			if (criteria.getBirthday() != null) {
				specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Candidate_.birthday));
			}
			if (criteria.getStreetAddress() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getStreetAddress(), Candidate_.streetAddress));
			}
			if (criteria.getEmail() != null) {
				specification = specification.and(buildStringSpecification(criteria.getEmail(), Candidate_.email));
			}
			if (criteria.getPhone() != null) {
				specification = specification.and(buildStringSpecification(criteria.getPhone(), Candidate_.phone));
			}
			if (criteria.getMobilePhone() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getMobilePhone(), Candidate_.mobilePhone));
			}
			if (criteria.getCountry() != null) {
				specification = specification.and(buildSpecification(criteria.getCountry(), Candidate_.country));
			}
			if (criteria.getRegion() != null) {
				specification = specification.and(buildStringSpecification(criteria.getRegion(), Candidate_.region));
			}
			if (criteria.getProvince() != null) {
				specification = specification
						.and(buildStringSpecification(criteria.getProvince(), Candidate_.province));
			}
			if (criteria.getCity() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCity(), Candidate_.city));
			}
			if (criteria.getCap() != null) {
				specification = specification.and(buildStringSpecification(criteria.getCap(), Candidate_.cap));
			}
			if (criteria.getEnabled() != null) {
				specification = specification.and(buildSpecification(criteria.getEnabled(), Candidate_.enabled));
			}
			if (criteria.getUserId() != null) {
				specification = specification.and(buildSpecification(criteria.getUserId(),
						root -> root.join(Candidate_.user, JoinType.LEFT).get(User_.id)));
			}
			if (criteria.getDocumentId() != null) {
				specification = specification.and(buildSpecification(criteria.getDocumentId(),
						root -> root.join(Candidate_.documents, JoinType.LEFT).get(Document_.id)));
			}
			if (criteria.getJobExperienceId() != null) {
				specification = specification.and(buildSpecification(criteria.getJobExperienceId(),
						root -> root.join(Candidate_.jobExperiences, JoinType.LEFT).get(JobExperience_.id)));
			}
			if (criteria.getEducationId() != null) {
				specification = specification.and(buildSpecification(criteria.getEducationId(),
						root -> root.join(Candidate_.educations, JoinType.LEFT).get(Education_.id)));
			}
			if (criteria.getSkillId() != null) {
				specification = specification.and(buildSpecification(criteria.getSkillId(),
						root -> root.join(Candidate_.skills, JoinType.LEFT).get(Skill_.id)));
			}
			if (criteria.getCurrentJobTitle() != null) {
				specification = specification.and(
						getCurrentJobExperienceSpecification(JobExperience_.jobTitle, criteria.getCurrentJobTitle()));
			}
			if (criteria.getCurrentJobCountry() != null) {
				specification = specification
						.and(getCurrentJobExperienceCountrySpecification(criteria.getCurrentJobCountry()));
			}
			if (criteria.getCurrentCountry() != null) {
				Specification<Candidate> currentCountrySpecification = getCurrentJobExperienceCountrySpecification(
						criteria.getCurrentCountry())
								.or(buildSpecification(criteria.getCurrentCountry(), Candidate_.country));
				specification = specification.and(currentCountrySpecification);
			}
			if (criteria.getCurrentCity() != null) {
				Specification<Candidate> currentCitySpecification = getCurrentJobExperienceSpecification(
						JobExperience_.city, criteria.getCurrentCity())
								.or(buildStringSpecification(criteria.getCurrentCity(), Candidate_.city));
				specification = specification.and(currentCitySpecification);
			}
			if (criteria.getSkillTagId() != null) {
				if (!criteria.isMacthAllSkillTag()) {
					specification = specification.and(getSkillTagSpecification(criteria.getSkillTagId()));
				} else {
					if (criteria.getSkillTagId().getEquals() != null) {
						specification = specification.and(getSkillTagSpecification(criteria.getSkillTagId()));
					} else if (criteria.getSkillTagId().getIn() != null) {
						for (Long skillTagId : criteria.getSkillTagId().getIn()) {
							LongFilter skillTagIdEqualsFilter = new LongFilter();
							skillTagIdEqualsFilter.setEquals(skillTagId);
							specification = specification.and(getSkillTagSpecification(skillTagIdEqualsFilter));
						}
					}
				}
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
	public Specification<Candidate> getSkillTagSpecification(LongFilter skillTagId) {
		return (root, query, criteriaBuilder) -> {
			Join<Skill, SkillTag> skillTagJoin = root.join(Candidate_.skills, JoinType.LEFT).join(Skill_.skillTag);
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

	/**
	 * Gets the current job experience specification.
	 *
	 * @param countryFilter the country filter
	 * @return the current job experience specification
	 */
	public Specification<Candidate> getCurrentJobExperienceCountrySpecification(CountryFilter countryFilter) {
		return (root, query, criteriaBuilder) -> {
			Join<Candidate, JobExperience> jobExperienceJoin = root.join(Candidate_.jobExperiences, JoinType.LEFT);
			jobExperienceJoin = jobExperienceJoin
					.on(criteriaBuilder.isTrue(jobExperienceJoin.get(JobExperience_.current)));
			Predicate predicate = null;
			if (countryFilter.getEquals() != null) {
				predicate = criteriaBuilder.equal(jobExperienceJoin.get(JobExperience_.country),
						countryFilter.getEquals());
			}
			query.distinct(true);
			return predicate;
		};
	}

	/**
	 * Gets the current job experience specification.
	 *
	 * @param attribute    the attribute
	 * @param stringFilter the string filter
	 * @return the current job experience specification
	 */
	public Specification<Candidate> getCurrentJobExperienceSpecification(
			SingularAttribute<JobExperience, String> attribute, StringFilter stringFilter) {
		return (root, query, criteriaBuilder) -> {
			Join<Candidate, JobExperience> jobExperienceJoin = root.join(Candidate_.jobExperiences, JoinType.LEFT);
			jobExperienceJoin = jobExperienceJoin
					.on(criteriaBuilder.isTrue(jobExperienceJoin.get(JobExperience_.current)));
			Predicate predicate = null;
			if (stringFilter.getEquals() != null) {
				predicate = criteriaBuilder.equal(jobExperienceJoin.get(attribute), stringFilter.getEquals());
			} else if (stringFilter.getContains() != null) {
				predicate = criteriaBuilder.like(jobExperienceJoin.get(attribute),
						wrapLikeQuery(stringFilter.getContains()));
			}
			query.distinct(true);
			return predicate;
		};
	}

//	/**
//	 * Gets the current job experience specification.
//	 *
//	 * @param jobTitleFilter the job title filter
//	 * @return the current job experience specification
//	 */
//	public Specification<Candidate> getCurrentJobExperienceTitleSpecification(StringFilter jobTitleFilter) {
//		return (root, query, criteriaBuilder) -> {
//			Join<Candidate, JobExperience> jobExperienceJoin = root.join(Candidate_.jobExperiences, JoinType.LEFT);
//			jobExperienceJoin = jobExperienceJoin
//					.on(criteriaBuilder.isTrue(jobExperienceJoin.get(JobExperience_.current)));
//			Predicate predicate = null;
//			if (jobTitleFilter.getEquals() != null) {
//				predicate = criteriaBuilder.equal(jobExperienceJoin.get(JobExperience_.jobTitle),
//						jobTitleFilter.getEquals());
//			} else if (jobTitleFilter.getContains() != null) {
//				predicate = criteriaBuilder.like(jobExperienceJoin.get(JobExperience_.jobTitle),
//						wrapLikeQuery(jobTitleFilter.getContains()));
//			}
//			query.distinct(true);
//			return predicate;
//		};
//	}

}
