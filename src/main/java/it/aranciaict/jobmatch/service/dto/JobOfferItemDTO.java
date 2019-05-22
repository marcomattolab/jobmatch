package it.aranciaict.jobmatch.service.dto;

import java.time.Instant;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.enumeration.Country;

/**
 * A DTO for the JobOffer entity.
 */
@ApiModel(description = "Job Offer Item DTO")
public class JobOfferItemDTO {

	/** The id. */
	private Long id;

	/** The created date. */
	private Instant createdDate;

	/** The start date. */
	private LocalDate startDate;

	/** The job offer title. */
	private String jobOfferTitle;

	/** The job title. */
	private String jobTitle;

	/** The job city. */
	private String jobCity;

	/** The job country. */
	private Country jobCountry;

	/** The company image url. */
    private String companyImageUrl;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the createdDate
     */
    public Instant getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the jobOfferTitle
     */
    public String getJobOfferTitle() {
        return jobOfferTitle;
    }

    /**
     * @param jobOfferTitle the jobOfferTitle to set
     */
    public void setJobOfferTitle(String jobOfferTitle) {
        this.jobOfferTitle = jobOfferTitle;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the jobCity
     */
    public String getJobCity() {
        return jobCity;
    }

    /**
     * @param jobCity the jobCity to set
     */
    public void setJobCity(String jobCity) {
        this.jobCity = jobCity;
    }

    /**
     * @return the jobCountry
     */
    public Country getJobCountry() {
        return jobCountry;
    }

    /**
     * @param jobCountry the jobCountry to set
     */
    public void setJobCountry(Country jobCountry) {
        this.jobCountry = jobCountry;
    }

    /**
     * @return the companyImageUrl
     */
    public String getCompanyImageUrl() {
        return companyImageUrl;
    }

    /**
     * @param companyImageUrl the companyImageUrl to set
     */
    public void setCompanyImageUrl(String companyImageUrl) {
        this.companyImageUrl = companyImageUrl;
    }
    
}
