package it.aranciaict.jobmatch.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.Country;

// TODO: Auto-generated Javadoc
/**
 * A JobExperience.
 */
@Entity
@Table(name = "job_experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobExperience extends AbstractAuditingEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The job title. */
	@NotNull
	@Column(name = "job_title", nullable = false)
	private String jobTitle;

	/** The job description. */
	@Lob
	@Column(name = "job_description")
	private String jobDescription;

	/** The company name. */
	@NotNull
	@Column(name = "company_name")
	private String companyName;

	/** The start date. */
	@NotNull
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	/** The end date. */
	@Column(name = "end_date")
	private LocalDate endDate;

	/** The current. */
	@Column(name = "current_job")
	private Boolean current;

	/** The city. */
	@Column(name = "city")
	private String city;
	
	/** The country. */
	@Enumerated(EnumType.STRING)
	@Column(name = "country")
	private Country country;

	/** The enabled. */
	@Column(name = "enabled")
	private Boolean enabled;

	/** The candidate. */
	@ManyToOne
	@JsonIgnoreProperties("jobExperiences")
	private Candidate candidate;

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
	 * @return the job experience
	 */
	public JobExperience jobTitle(String jobTitle) {
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
	 * @return the job experience
	 */
	public JobExperience jobDescription(String jobDescription) {
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
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Company name.
	 *
	 * @param companyName the company name
	 * @return the job experience
	 */
	public JobExperience companyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	 * @return the job experience
	 */
	public JobExperience startDate(LocalDate startDate) {
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
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * End date.
	 *
	 * @param endDate the end date
	 * @return the job experience
	 */
	public JobExperience endDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 * Checks if is current.
	 *
	 * @return the boolean
	 */
	public Boolean isCurrent() {
		return current;
	}

	/**
	 * Current.
	 *
	 * @param current the current
	 * @return the job experience
	 */
	public JobExperience current(Boolean current) {
		this.current = current;
		return this;
	}

	/**
	 * Sets the current.
	 *
	 * @param current the new current
	 */
	public void setCurrent(Boolean current) {
		this.current = current;
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
	 * @return the job experience
	 */
	public JobExperience enabled(Boolean enabled) {
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
	 * Gets the candidate.
	 *
	 * @return the candidate
	 */
	public Candidate getCandidate() {
		return candidate;
	}

	/**
	 * Candidate.
	 *
	 * @param candidate the candidate
	 * @return the job experience
	 */
	public JobExperience candidate(Candidate candidate) {
		this.candidate = candidate;
		return this;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 * @return the job experience
	 */
	public JobExperience city(String city) {
		this.city = city;
		return this;
	}
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 * @return the job experience
	 */
	public JobExperience country(Country country) {
		this.country = country;
		return this;
	}

	/**
	 * Gets the current.
	 *
	 * @return the current
	 */
	public Boolean getCurrent() {
		return current;
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
	 * Sets the candidate.
	 *
	 * @param candidate the new candidate
	 */
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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
		JobExperience jobExperience = (JobExperience) o;
		if (jobExperience.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), jobExperience.getId());
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

	@Override
	public String toString() {
		return "JobExperience [getId()=" + getId() + ", getJobTitle()=" + getJobTitle() + ", getJobDescription()="
				+ getJobDescription() + ", getCompanyName()=" + getCompanyName() + ", getStartDate()=" + getStartDate()
				+ ", getEndDate()=" + getEndDate() + ", isCurrent()=" + isCurrent() + ", isEnabled()=" + isEnabled()
				+ ", getCandidate()=" + getCandidate() + ", getCity()=" + getCity() + ", getCountry()=" + getCountry()
				+ ", getCurrent()=" + getCurrent() + ", getEnabled()=" + getEnabled() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
