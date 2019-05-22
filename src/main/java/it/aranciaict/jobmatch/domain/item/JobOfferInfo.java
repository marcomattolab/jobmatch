package it.aranciaict.jobmatch.domain.item;

import java.io.Serializable;
import java.time.LocalDate;

import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.EmploymentType;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferType;
import it.aranciaict.jobmatch.domain.enumeration.SeniorityLevel;

/**
 * Item Job Offer Info.
 */

@SuppressWarnings("serial")
public class JobOfferInfo implements Serializable {

	/** The id. */
	private Long id;

	/** The start date. */
	private LocalDate startDate;

	/** The job title. */
	private String jobTitle;

	/** The job short description. */
	private String jobShortDescription;

	/** The job offer type. */
	private JobOfferType jobOfferType;

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

	/** The Company */
	private Company company;

	/**
	 * Instantiates a new job offer info.
	 *
	 * @param id                  the id
	 * @param startDate           the start date
	 * @param jobTitle            the job title
	 * @param jobShortDescription the job short description
	 * @param jobOfferType        the job offer type
	 * @param jobFunctions        the job functions
	 * @param jobCity             the job city
	 * @param jobCountry          the job country
	 * @param employmentType      the employment type
	 * @param seniorityLevel      the seniority level
	 * @param salaryOffered       the salary offered
	 * @param status              the status
	 * @param company             the company
	 */
	@SuppressWarnings("checkstyle:parameternumber")
	public JobOfferInfo(Long id, LocalDate startDate, String jobTitle, String jobShortDescription,
			JobOfferType jobOfferType, String jobFunctions, String jobCity, Country jobCountry,
			EmploymentType employmentType, SeniorityLevel seniorityLevel, String salaryOffered, JobOfferStatus status,
			Company company) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.jobTitle = jobTitle;
		this.jobShortDescription = jobShortDescription;
		this.jobOfferType = jobOfferType;
		this.jobFunctions = jobFunctions;
		this.jobCity = jobCity;
		this.jobCountry = jobCountry;
		this.employmentType = employmentType;
		this.seniorityLevel = seniorityLevel;
		this.salaryOffered = salaryOffered;
		this.status = status;
		this.company = company;
	}

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
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
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

}
