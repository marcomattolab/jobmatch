package it.aranciaict.jobmatch.service.dto;

import java.time.Instant;
import java.time.LocalDate;

import it.aranciaict.jobmatch.domain.enumeration.Country;

/**
 * A DTO for the JobOffer entity.
 */
public interface JobOfferItem {

	/**
	 * The id.
	 *
	 * @return the id
	 */
	Long getId();

	/**
	 * The created date.
	 *
	 * @return the created date
	 */
	Instant getCreatedDate();

	/**
	 * The start date.
	 *
	 * @return the start date
	 */
	LocalDate getStartDate();

	/**
	 * The job offer title.
	 *
	 * @return the job offer title
	 */
	String getJobOfferTitle();

	/**
	 * The job title.
	 *
	 * @return the job title
	 */
	String getJobTitle();

	/**
	 * The job city.
	 *
	 * @return the job city
	 */
	String getJobCity();

	/**
	 * The job country.
	 *
	 * @return the job country
	 */
	Country getJobCountry();

	/**
	 * The company image url.
	 *
	 * @return the company image url
	 */
	String getCompanyImageUrl();
}
