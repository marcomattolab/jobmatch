package it.aranciaict.jobmatch.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.EmploymentType;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferType;
import it.aranciaict.jobmatch.domain.enumeration.SeniorityLevel;

// TODO: Auto-generated Javadoc
/**
 * Entity Job Offer.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "job_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobOffer extends AbstractAuditingEntity implements Serializable {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The start date. */
	@NotNull
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	/** The job title. */
	@NotNull
	@Column(name = "job_title", nullable = false)
	private String jobTitle;

	/** The job short description. */
	@NotNull
	@Column(name = "job_short_description", nullable = false)
	private String jobShortDescription;

	/** The job description. */
	@Lob
	@Column(name = "job_description")
	private String jobDescription;

	@Enumerated(EnumType.STRING)
	@Column(name = "job_offer_type")
	private JobOfferType jobOfferType;

	/** The responsibilities description. */
	@Lob
	@Column(name = "responsibilities_description")
	private String responsibilitiesDescription;

	/** The experiences description. */
	@Lob
	@Column(name = "experiences_description")
	private String experiencesDescription;

	/** The attributes description. */
	@Lob
	@Column(name = "attributes_description")
	private String attributesDescription;

	/** The job functions. */
	@Column(name = "job_functions")
	private String jobFunctions;

	/** The job city. */
	@Column(name = "job_city")
	private String jobCity;

	/** The job country. */
	@Enumerated(EnumType.STRING)
	@Column(name = "job_country")
	private Country jobCountry;

	/** The employment type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "employment_type")
	private EmploymentType employmentType;

	/** The seniority level. */
	@Enumerated(EnumType.STRING)
	@Column(name = "seniority_level")
	private SeniorityLevel seniorityLevel;

	/** The salary offered. */
	@Column(name = "salary_offered")
	private String salaryOffered;

	/** The status. */
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private JobOfferStatus status;

	/** The enabled. */
	@Column(name = "enabled")
	private Boolean enabled;

	/** The skills. */
	@OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<JobOfferSkill> skills = new HashSet<>();

	/** The company. */
	@ManyToOne
	@JsonIgnoreProperties("jobOffers")
	private Company company;

	/** The sector. */
	@ManyToOne
	@JsonIgnoreProperties("jobOffers")
	private CompanySector sector;

	/** The project. */
	@ManyToOne
	@JsonIgnoreProperties("jobOffers")
	private Project project;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
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
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Start date.
	 *
	 * @param startDate the start date
	 * @return the job offer
	 */
	public JobOffer startDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
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
	 * Job title.
	 *
	 * @param jobTitle the job title
	 * @return the job offer
	 */
	public JobOffer jobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
		return this;
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
	 * Job short description.
	 *
	 * @param jobShortDescription the job short description
	 * @return the job offer
	 */
	public JobOffer jobShortDescription(String jobShortDescription) {
		this.jobShortDescription = jobShortDescription;
		return this;
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
	 * Job description.
	 *
	 * @param jobDescription the job description
	 * @return the job offer
	 */
	public JobOffer jobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
		return this;
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
	 * Responsibilities description.
	 *
	 * @param responsibilitiesDescription the responsibilities description
	 * @return the job offer
	 */
	public JobOffer responsibilitiesDescription(String responsibilitiesDescription) {
		this.responsibilitiesDescription = responsibilitiesDescription;
		return this;
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
	 * Experiences description.
	 *
	 * @param experiencesDescription the experiences description
	 * @return the job offer
	 */
	public JobOffer experiencesDescription(String experiencesDescription) {
		this.experiencesDescription = experiencesDescription;
		return this;
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
	 * Attributes description.
	 *
	 * @param attributesDescription the attributes description
	 * @return the job offer
	 */
	public JobOffer attributesDescription(String attributesDescription) {
		this.attributesDescription = attributesDescription;
		return this;
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
	 * Job functions.
	 *
	 * @param jobFunctions the job functions
	 * @return the job offer
	 */
	public JobOffer jobFunctions(String jobFunctions) {
		this.jobFunctions = jobFunctions;
		return this;
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
	 * Job city.
	 *
	 * @param jobCity the job city
	 * @return the job offer
	 */
	public JobOffer jobCity(String jobCity) {
		this.jobCity = jobCity;
		return this;
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
	 * Job country.
	 *
	 * @param jobCountry the job country
	 * @return the job offer
	 */
	public JobOffer jobCountry(Country jobCountry) {
		this.jobCountry = jobCountry;
		return this;
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
	 * Employment type.
	 *
	 * @param employmentType the employment type
	 * @return the job offer
	 */
	public JobOffer employmentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
		return this;
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
	 * Seniority level.
	 *
	 * @param seniorityLevel the seniority level
	 * @return the job offer
	 */
	public JobOffer seniorityLevel(SeniorityLevel seniorityLevel) {
		this.seniorityLevel = seniorityLevel;
		return this;
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
	 * Salary offered.
	 *
	 * @param salaryOffered the salary offered
	 * @return the job offer
	 */
	public JobOffer salaryOffered(String salaryOffered) {
		this.salaryOffered = salaryOffered;
		return this;
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
	 * Status.
	 *
	 * @param status the status
	 * @return the job offer
	 */
	public JobOffer status(JobOfferStatus status) {
		this.status = status;
		return this;
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
	 * Enabled.
	 *
	 * @param enabled the enabled
	 * @return the job offer
	 */
	public JobOffer enabled(Boolean enabled) {
		this.enabled = enabled;
		return this;
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
	 * Gets the skills.
	 *
	 * @return the skills
	 */
	public Set<JobOfferSkill> getSkills() {
		return skills;
	}

	/**
	 * Skills.
	 *
	 * @param jobOfferSkills the job offer skills
	 * @return the job offer
	 */
	public JobOffer skills(Set<JobOfferSkill> jobOfferSkills) {
		this.skills = jobOfferSkills;
		return this;
	}

	/**
	 * Adds the skill.
	 *
	 * @param jobOfferSkill the job offer skill
	 * @return the job offer
	 */
	public JobOffer addSkill(JobOfferSkill jobOfferSkill) {
		this.skills.add(jobOfferSkill);
		jobOfferSkill.setJobOffer(this);
		return this;
	}

	/**
	 * Removes the skill.
	 *
	 * @param jobOfferSkill the job offer skill
	 * @return the job offer
	 */
	public JobOffer removeSkill(JobOfferSkill jobOfferSkill) {
		this.skills.remove(jobOfferSkill);
		jobOfferSkill.setJobOffer(null);
		return this;
	}

	/**
	 * Sets the skills.
	 *
	 * @param jobOfferSkills the new skills
	 */
	public void setSkills(Set<JobOfferSkill> jobOfferSkills) {
		this.skills = jobOfferSkills;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Company.
	 *
	 * @param company the company
	 * @return the job offer
	 */
	public JobOffer company(Company company) {
		this.company = company;
		return this;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the sector.
	 *
	 * @return the sector
	 */
	public CompanySector getSector() {
		return sector;
	}

	/**
	 * Sector.
	 *
	 * @param companySector the company sector
	 * @return the job offer
	 */
	public JobOffer sector(CompanySector companySector) {
		this.sector = companySector;
		return this;
	}

	/**
	 * Sets the sector.
	 *
	 * @param companySector the new sector
	 */
	public void setSector(CompanySector companySector) {
		this.sector = companySector;
	}

	/**
	 * Gets the project.
	 *
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Project.
	 *
	 * @param project the project
	 * @return the job offer
	 */
	public JobOffer project(Project project) {
		this.project = project;
		return this;
	}

	/**
	 * Sets the project.
	 *
	 * @param project the new project
	 */
	public void setProject(Project project) {
		this.project = project;
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

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

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
		JobOffer jobOffer = (JobOffer) o;
		if (jobOffer.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), jobOffer.getId());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobOffer{" + "id=" + getId() + ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", lastModifiedDate='"
				+ getLastModifiedDate() + "'" + ", startDate='" + getStartDate() + "'" + ", jobTitle='" + getJobTitle()
				+ "'" + ", jobShortDescription='" + getJobShortDescription() + "'" + ", jobDescription='"
				+ getJobDescription() + "'" + ", jobOfferType='" + getJobOfferType() + "'"
				+ ", responsibilitiesDescription='" + getResponsibilitiesDescription() + "'"
				+ ", experiencesDescription='" + getExperiencesDescription() + "'" + ", attributesDescription='"
				+ getAttributesDescription() + "'" + ", jobFunctions='" + getJobFunctions() + "'" + ", jobCity='"
				+ getJobCity() + "'" + ", jobCountry='" + getJobCountry() + "'" + ", employmentType='"
				+ getEmploymentType() + "'" + ", seniorityLevel='" + getSeniorityLevel() + "'" + ", salaryOffered='"
				+ getSalaryOffered() + "'" + ", status='" + getStatus() + "'" + ", enabled='" + isEnabled() + "'" + "}";
	}
}
