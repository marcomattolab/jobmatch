/*
 * 
 */
package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.EmploymentType;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferType;
import it.aranciaict.jobmatch.domain.enumeration.SeniorityLevel;

/**
 * A DTO for the JobOffer entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Job Offer")
public class JobOfferDTO implements Serializable {

	/** The id. */
	private Long id;

	/** The created by. */
	@Size(max = ValidationConstants.SIZE_50)
	private String createdBy;

	/** The last modified by. */
	@Size(max = ValidationConstants.SIZE_50)
	private String lastModifiedBy;

	/** The created date. */
	private Instant createdDate;

	/** The last modified date. */
	private Instant lastModifiedDate;

	/** The start date. */
	@NotNull
	private LocalDate startDate;

	/** The job title. */
	@NotNull
	private String jobTitle;

	/** The job short description. */
	@NotNull
	private String jobShortDescription;

	/** The job description. */
	@Lob
	private String jobDescription;

	/** The job offer type. */
	private JobOfferType jobOfferType;

	/** The responsibilities description. */
	@Lob
	private String responsibilitiesDescription;

	/** The experiences description. */
	@Lob
	private String experiencesDescription;

	/** The attributes description. */
	@Lob
	private String attributesDescription;

	/** The job functions. */
	private String jobFunctions;

	/** The job city. */
	private String jobCity;

	/** The job country. */
	private Country jobCountry;

	/** The employment type. */
	private EmploymentType employmentType;

	/** The seniority level. */
	private SeniorityLevel seniorityLevel;

	/** The salary offered. */
	private String salaryOffered;

	/** The status. */
	private JobOfferStatus status;

	/** The enabled. */
	private Boolean enabled;

	/** The company image url. */
	private String imageUrl;

	/** The job offer skills. */
	private Set<JobOfferSkillDTO> skills;

	/** The company id. */
	private Long companyId;
	
	/** The sponsoring institution id. */
	private Long sponsoringInstitutionId;

	/** The company name. */
	private String companyName;

	/** The sector id. */
	private Long sectorId;

	/** The project id. */
	private Long projectId;

	/** The edit available. */
	private boolean editAvailable;

	/** The delete available. */
	private boolean deleteAvailable;

	/** The applied job available. */
	private boolean appliedJobAvailable;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy the new last modified by
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Instant getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last modified date.
	 *
	 * @return the last modified date
	 */
	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * Sets the last modified date.
	 *
	 * @param lastModifiedDate the new last modified date
	 */
	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the job short description.
	 *
	 * @return the job short description
	 */
	public String getJobShortDescription() {
		return jobShortDescription;
	}

	/**
	 * Sets the job short description.
	 *
	 * @param jobShortDescription the new job short description
	 */
	public void setJobShortDescription(String jobShortDescription) {
		this.jobShortDescription = jobShortDescription;
	}

	/**
	 * Gets the job description.
	 *
	 * @return the job description
	 */
	public String getJobDescription() {
		return jobDescription;
	}

	/**
	 * Sets the job description.
	 *
	 * @param jobDescription the new job description
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/**
	 * Gets the responsibilities description.
	 *
	 * @return the responsibilities description
	 */
	public String getResponsibilitiesDescription() {
		return responsibilitiesDescription;
	}

	/**
	 * Sets the responsibilities description.
	 *
	 * @param responsibilitiesDescription the new responsibilities description
	 */
	public void setResponsibilitiesDescription(String responsibilitiesDescription) {
		this.responsibilitiesDescription = responsibilitiesDescription;
	}

	/**
	 * Gets the experiences description.
	 *
	 * @return the experiences description
	 */
	public String getExperiencesDescription() {
		return experiencesDescription;
	}

	/**
	 * Sets the experiences description.
	 *
	 * @param experiencesDescription the new experiences description
	 */
	public void setExperiencesDescription(String experiencesDescription) {
		this.experiencesDescription = experiencesDescription;
	}

	/**
	 * Gets the attributes description.
	 *
	 * @return the attributes description
	 */
	public String getAttributesDescription() {
		return attributesDescription;
	}

	/**
	 * Sets the attributes description.
	 *
	 * @param attributesDescription the new attributes description
	 */
	public void setAttributesDescription(String attributesDescription) {
		this.attributesDescription = attributesDescription;
	}

	/**
	 * Gets the job functions.
	 *
	 * @return the job functions
	 */
	public String getJobFunctions() {
		return jobFunctions;
	}

	/**
	 * Sets the job functions.
	 *
	 * @param jobFunctions the new job functions
	 */
	public void setJobFunctions(String jobFunctions) {
		this.jobFunctions = jobFunctions;
	}

	/**
	 * Gets the job city.
	 *
	 * @return the job city
	 */
	public String getJobCity() {
		return jobCity;
	}

	/**
	 * Sets the job city.
	 *
	 * @param jobCity the new job city
	 */
	public void setJobCity(String jobCity) {
		this.jobCity = jobCity;
	}

	/**
	 * Gets the job country.
	 *
	 * @return the job country
	 */
	public Country getJobCountry() {
		return jobCountry;
	}

	/**
	 * Sets the job country.
	 *
	 * @param jobCountry the new job country
	 */
	public void setJobCountry(Country jobCountry) {
		this.jobCountry = jobCountry;
	}

	/**
	 * Gets the employment type.
	 *
	 * @return the employment type
	 */
	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	/**
	 * Sets the employment type.
	 *
	 * @param employmentType the new employment type
	 */
	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * Gets the seniority level.
	 *
	 * @return the seniority level
	 */
	public SeniorityLevel getSeniorityLevel() {
		return seniorityLevel;
	}

	/**
	 * Sets the seniority level.
	 *
	 * @param seniorityLevel the new seniority level
	 */
	public void setSeniorityLevel(SeniorityLevel seniorityLevel) {
		this.seniorityLevel = seniorityLevel;
	}

	/**
	 * Gets the salary offered.
	 *
	 * @return the salary offered
	 */
	public String getSalaryOffered() {
		return salaryOffered;
	}

	/**
	 * Sets the salary offered.
	 *
	 * @param salaryOffered the new salary offered
	 */
	public void setSalaryOffered(String salaryOffered) {
		this.salaryOffered = salaryOffered;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public JobOfferStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(JobOfferStatus status) {
		this.status = status;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return the boolean
	 */
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * Gets the sector id.
	 *
	 * @return the sector id
	 */
	public Long getSectorId() {
		return sectorId;
	}

	/**
	 * Sets the sector id.
	 *
	 * @param companySectorId the new sector id
	 */
	public void setSectorId(Long companySectorId) {
		this.sectorId = companySectorId;
	}

	/**
	 * Gets the project id.
	 *
	 * @return the project id
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * Sets the project id.
	 *
	 * @param projectId the new project id
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

		JobOfferDTO jobOfferDTO = (JobOfferDTO) o;
		if (jobOfferDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), jobOfferDTO.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	/**
	 * Checks if is edits the available.
	 *
	 * @return true, if is edits the available
	 */
	public boolean isEditAvailable() {
		return editAvailable;
	}

	/**
	 * Sets the edits the available.
	 *
	 * @param editAvailable the new edits the available
	 */
	public void setEditAvailable(boolean editAvailable) {
		this.editAvailable = editAvailable;
	}

	/**
	 * Checks if is delete available.
	 *
	 * @return true, if is delete available
	 */
	public boolean isDeleteAvailable() {
		return deleteAvailable;
	}

	/**
	 * Sets the delete available.
	 *
	 * @param deleteAvailable the new delete available
	 */
	public void setDeleteAvailable(boolean deleteAvailable) {
		this.deleteAvailable = deleteAvailable;
	}

	/**
	 * Checks if is applied job available.
	 *
	 * @return true, if is applied job available
	 */
	public boolean isAppliedJobAvailable() {
		return appliedJobAvailable;
	}

	/**
	 * Sets the applied job available.
	 *
	 * @param appliedJobAvailable the new applied job available
	 */
	public void setAppliedJobAvailable(boolean appliedJobAvailable) {
		this.appliedJobAvailable = appliedJobAvailable;
	}

	/**
	 * Gets the job offer type.
	 *
	 * @return the job offer type
	 */
	public JobOfferType getJobOfferType() {
		return jobOfferType;
	}

	/**
	 * Sets the job offer type.
	 *
	 * @param jobOfferType the new job offer type
	 */
	public void setJobOfferType(JobOfferType jobOfferType) {
		this.jobOfferType = jobOfferType;
	}
	
	/**
	 * Gets the sponsoring institution id.
	 *
	 * @return the sponsoring institution id
	 */
	public Long getSponsoringInstitutionId() {
		return sponsoringInstitutionId;
	}

	/**
	 * Sets the sponsoring institution id.
	 *
	 * @param sponsoringInstitutionId the new sponsoring institution id
	 */
	public void setSponsoringInstitutionId(Long sponsoringInstitutionId) {
		this.sponsoringInstitutionId = sponsoringInstitutionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobOfferDTO [id=" + id + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", startDate=" + startDate
				+ ", jobTitle=" + jobTitle + ", jobShortDescription=" + jobShortDescription + ", jobOfferType="
				+ jobOfferType + ", jobCity=" + jobCity + ", jobCountry=" + jobCountry + ", employmentType="
				+ employmentType + ", seniorityLevel=" + seniorityLevel + ", salaryOffered=" + salaryOffered
				+ ", status=" + status + ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", companyId=" + companyId
				+ ", sectorId=" + sectorId + ", projectId=" + projectId + ", editAvailable=" + editAvailable
				+ ", deleteAvailable=" + deleteAvailable + ", appliedJobAvailable=" + appliedJobAvailable + "]";
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 *
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Gets the skills.
	 *
	 * @return the skills
	 */
	public Set<JobOfferSkillDTO> getSkills() {
		return skills;
	}

	/**
	 * Sets the skills.
	 *
	 * @param skills the skills to set
	 */
	public void setSkills(Set<JobOfferSkillDTO> skills) {
		this.skills = skills;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
