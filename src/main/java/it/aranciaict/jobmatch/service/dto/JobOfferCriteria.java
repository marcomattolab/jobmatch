/*
 * 
 */
package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.EmploymentType;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferType;
import it.aranciaict.jobmatch.domain.enumeration.SeniorityLevel;

// TODO: Auto-generated Javadoc
/**
 * Criteria class for the JobOffer entity. This class is used in
 * JobOfferResource to receive all the possible filtering options from the Http
 * GET request parameters. For example the following could be a valid requests:
 * <code> /job-offers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@SuppressWarnings("serial")
public class JobOfferCriteria implements Serializable {

	/**
	 * Class for filtering JobOfferType.
	 */
	public static class JobOfferTypeFilter extends Filter<JobOfferType> {
	}

	/**
	 * Class for filtering Country.
	 */
	public static class CountryFilter extends Filter<Country> {
	}

	/**
	 * Class for filtering EmploymentType.
	 */
	public static class EmploymentTypeFilter extends Filter<EmploymentType> {
	}

	/**
	 * Class for filtering SeniorityLevel.
	 */
	public static class SeniorityLevelFilter extends Filter<SeniorityLevel> {
	}

	/**
	 * Class for filtering JobOfferStatus.
	 */
	public static class JobOfferStatusFilter extends Filter<JobOfferStatus> {
	}

	/** The id. */
	private LongFilter id;

	/** The created by. */
	private StringFilter createdBy;

	/** The last modified by. */
	private StringFilter lastModifiedBy;

	/** The created date. */
	private InstantFilter createdDate;

	/** The last modified date. */
	private InstantFilter lastModifiedDate;

	/** The start date. */
	private LocalDateFilter startDate;

	/** The job title. */
	private StringFilter jobTitle;

	/** The job short description. */
	private StringFilter jobShortDescription;

	/** The job offer type. */
	private JobOfferTypeFilter jobOfferType;

	/** The job functions. */
	private StringFilter jobFunctions;

	/** The job city. */
	private StringFilter jobCity;

	/** The job country. */
	private CountryFilter jobCountry;

	/** The employment type. */
	private EmploymentTypeFilter employmentType;

	/** The seniority level. */
	private SeniorityLevelFilter seniorityLevel;

	/** The salary offered. */
	private StringFilter salaryOffered;

	/** The status. */
	private JobOfferStatusFilter status;

	/** The enabled. */
	private BooleanFilter enabled;

	/** The skill id. */
	private LongFilter skillId;

	/** The company id. */
	private LongFilter companyId;

	/** The sector id. */
	private LongFilter sectorId;

	/** The project id. */
	private LongFilter projectId;

	/** The skill tag name. */
	private LongFilter skillTagId;
	/** The macth all skill tag. */
	private boolean macthAllSkillTag = true;

	/** The key word. */
	private StringFilter keyWord;
	
	/** The include others companies. */
	private boolean searchMode = false;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public LongFilter getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(LongFilter id) {
		this.id = id;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public StringFilter getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(StringFilter createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public StringFilter getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy the new last modified by
	 */
	public void setLastModifiedBy(StringFilter lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public InstantFilter getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(InstantFilter createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last modified date.
	 *
	 * @return the last modified date
	 */
	public InstantFilter getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * Sets the last modified date.
	 *
	 * @param lastModifiedDate the new last modified date
	 */
	public void setLastModifiedDate(InstantFilter lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public LocalDateFilter getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(LocalDateFilter startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public StringFilter getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle the new job title
	 */
	public void setJobTitle(StringFilter jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the job short description.
	 *
	 * @return the job short description
	 */
	public StringFilter getJobShortDescription() {
		return jobShortDescription;
	}

	/**
	 * Sets the job short description.
	 *
	 * @param jobShortDescription the new job short description
	 */
	public void setJobShortDescription(StringFilter jobShortDescription) {
		this.jobShortDescription = jobShortDescription;
	}

	/**
	 * Gets the job functions.
	 *
	 * @return the job functions
	 */
	public StringFilter getJobFunctions() {
		return jobFunctions;
	}

	/**
	 * Sets the job functions.
	 *
	 * @param jobFunctions the new job functions
	 */
	public void setJobFunctions(StringFilter jobFunctions) {
		this.jobFunctions = jobFunctions;
	}

	/**
	 * Gets the job city.
	 *
	 * @return the job city
	 */
	public StringFilter getJobCity() {
		return jobCity;
	}

	/**
	 * Sets the job city.
	 *
	 * @param jobCity the new job city
	 */
	public void setJobCity(StringFilter jobCity) {
		this.jobCity = jobCity;
	}

	/**
	 * Gets the job country.
	 *
	 * @return the job country
	 */
	public CountryFilter getJobCountry() {
		return jobCountry;
	}

	/**
	 * Sets the job country.
	 *
	 * @param jobCountry the new job country
	 */
	public void setJobCountry(CountryFilter jobCountry) {
		this.jobCountry = jobCountry;
	}

	/**
	 * Gets the employment type.
	 *
	 * @return the employment type
	 */
	public EmploymentTypeFilter getEmploymentType() {
		return employmentType;
	}

	/**
	 * Sets the employment type.
	 *
	 * @param employmentType the new employment type
	 */
	public void setEmploymentType(EmploymentTypeFilter employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * Gets the seniority level.
	 *
	 * @return the seniority level
	 */
	public SeniorityLevelFilter getSeniorityLevel() {
		return seniorityLevel;
	}

	/**
	 * Sets the seniority level.
	 *
	 * @param seniorityLevel the new seniority level
	 */
	public void setSeniorityLevel(SeniorityLevelFilter seniorityLevel) {
		this.seniorityLevel = seniorityLevel;
	}

	/**
	 * Gets the salary offered.
	 *
	 * @return the salary offered
	 */
	public StringFilter getSalaryOffered() {
		return salaryOffered;
	}

	/**
	 * Sets the salary offered.
	 *
	 * @param salaryOffered the new salary offered
	 */
	public void setSalaryOffered(StringFilter salaryOffered) {
		this.salaryOffered = salaryOffered;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public JobOfferStatusFilter getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(JobOfferStatusFilter status) {
		this.status = status;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public BooleanFilter getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(BooleanFilter enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the skill id.
	 *
	 * @return the skill id
	 */
	public LongFilter getSkillId() {
		return skillId;
	}

	/**
	 * Sets the skill id.
	 *
	 * @param skillId the new skill id
	 */
	public void setSkillId(LongFilter skillId) {
		this.skillId = skillId;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public LongFilter getCompanyId() {
		return companyId;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(LongFilter companyId) {
		this.companyId = companyId;
	}

	/**
	 * Gets the sector id.
	 *
	 * @return the sector id
	 */
	public LongFilter getSectorId() {
		return sectorId;
	}

	/**
	 * Sets the sector id.
	 *
	 * @param sectorId the new sector id
	 */
	public void setSectorId(LongFilter sectorId) {
		this.sectorId = sectorId;
	}

	/**
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public LongFilter getProjectId() {
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId the new project id
	 */
	public void setProjectId(LongFilter projectId) {
		this.projectId = projectId;
	}

	/**
	 * Gets the skill tag id.
	 *
	 * @return the skill tag id
	 */
	public LongFilter getSkillTagId() {
		return skillTagId;
	}

	/**
	 * Sets the skill tag id.
	 *
	 * @param skillTagId the new skill tag id
	 */
	public void setSkillTagId(LongFilter skillTagId) {
		this.skillTagId = skillTagId;
	}

	/**
	 * Gets the key word.
	 *
	 * @return the key word
	 */
	public StringFilter getKeyWord() {
		return keyWord;
	}

	/**
	 * Sets the key word.
	 *
	 * @param keyWord the new key word
	 */
	public void setKeyWord(StringFilter keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * Checks if is macth all skill tag.
	 *
	 * @return true, if is macth all skill tag
	 */
	public boolean isMacthAllSkillTag() {
		return macthAllSkillTag;
	}

	/**
	 * Sets the macth all skill tag.
	 *
	 * @param macthAllSkillTag the new macth all skill tag
	 */
	public void setMacthAllSkillTag(boolean macthAllSkillTag) {
		this.macthAllSkillTag = macthAllSkillTag;
	}

	/**
	 * Gets the job offer type.
	 *
	 * @return the job offer type
	 */
	public JobOfferTypeFilter getJobOfferType() {
		return jobOfferType;
	}

	/**
	 * Sets the job offer type.
	 *
	 * @param jobOfferType the new job offer type
	 */
	public void setJobOfferType(JobOfferTypeFilter jobOfferType) {
		this.jobOfferType = jobOfferType;
	}


	/**
	 * Checks if is search mode.
	 *
	 * @return true, if is search mode
	 */
	public boolean isSearchMode() {
		return searchMode;
	}

	/**
	 * Sets the search mode.
	 *
	 * @param searchMode the new search mode
	 */
	public void setSearchMode(boolean searchMode) {
		this.searchMode = searchMode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final JobOfferCriteria that = (JobOfferCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(createdBy, that.createdBy)
				&& Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(createdDate, that.createdDate)
				&& Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(startDate, that.startDate)
				&& Objects.equals(jobTitle, that.jobTitle)
				&& Objects.equals(jobShortDescription, that.jobShortDescription)
				&& Objects.equals(jobFunctions, that.jobFunctions) && Objects.equals(jobCity, that.jobCity)
				&& Objects.equals(jobCountry, that.jobCountry) && Objects.equals(employmentType, that.employmentType)
				&& Objects.equals(seniorityLevel, that.seniorityLevel)
				&& Objects.equals(salaryOffered, that.salaryOffered) && Objects.equals(status, that.status)
				&& Objects.equals(enabled, that.enabled) && Objects.equals(skillId, that.skillId)
				&& Objects.equals(companyId, that.companyId) && Objects.equals(sectorId, that.sectorId)
				&& Objects.equals(projectId, that.projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, createdBy, lastModifiedBy, createdDate, lastModifiedDate, startDate, jobTitle,
				jobShortDescription, jobFunctions, jobCity, jobCountry, employmentType, seniorityLevel, salaryOffered,
				status, enabled, skillId, companyId, sectorId, projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobOfferCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", " : "")
				+ (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (jobTitle != null ? "jobTitle=" + jobTitle + ", " : "")
				+ (jobShortDescription != null ? "jobShortDescription=" + jobShortDescription + ", " : "")
				+ (jobFunctions != null ? "jobFunctions=" + jobFunctions + ", " : "")
				+ (jobCity != null ? "jobCity=" + jobCity + ", " : "")
				+ (jobCountry != null ? "jobCountry=" + jobCountry + ", " : "")
				+ (employmentType != null ? "employmentType=" + employmentType + ", " : "")
				+ (seniorityLevel != null ? "seniorityLevel=" + seniorityLevel + ", " : "")
				+ (salaryOffered != null ? "salaryOffered=" + salaryOffered + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (enabled != null ? "enabled=" + enabled + ", " : "")
				+ (skillId != null ? "skillId=" + skillId + ", " : "")
				+ (companyId != null ? "companyId=" + companyId + ", " : "")
				+ (sectorId != null ? "sectorId=" + sectorId + ", " : "")
				+ (projectId != null ? "projectId=" + projectId + ", " : "") + "}";
	}


}
