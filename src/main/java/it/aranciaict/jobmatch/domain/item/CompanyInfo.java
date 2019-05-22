package it.aranciaict.jobmatch.domain.item;

import java.io.Serializable;
import java.time.LocalDate;

import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;

/**
 * The Company entity Info.
 */
public interface CompanyInfo extends Serializable {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId();

	/**
	 * 
	 * /** Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName();

	/**
	 * Gets the company size.
	 *
	 * @return the company size
	 */
	public CompanySizeType getCompanySize();

	/**
	 * Gets the company type.
	 *
	 * @return the company type
	 */
	public CompanyType getCompanyType();

	/**
	 * Gets the number of employee.
	 *
	 * @return the number of employee
	 */
	public NumberOfEmployees getNumberOfEmployee();

	/**
	 * Gets the street address.
	 *
	 * @return the street address
	 */
	public String getStreetAddress();

	/**
	 * Gets the foundation date.
	 *
	 * @return the foundation date
	 */
	public LocalDate getFoundationDate();

	/**
	 * Gets the vat number.
	 *
	 * @return the vat number
	 */
	public String getVatNumber();

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail();

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * Gets the mobile phone.
	 *
	 * @return the mobile phone
	 */
	public String getMobilePhone();

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Country getCountry();

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion();

	/**
	 * Gets the province.
	 *
	 * @return the province
	 */
	public String getProvince();

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity();

	/**
	 * Gets the cap.
	 *
	 * @return the cap
	 */
	public String getCap();

	/**
	 * Gets the url site.
	 *
	 * @return the url site
	 */
	public String getUrlSite();

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageUrl();
	
	/**
	 * Gets the sector.
	 *
	 * @return the sector
	 */
	public String getSectorId();
	
	/**
	 * Gets the sponsoring institution id.
	 *
	 * @return the sponsoring institution id
	 */
	public String getSponsoringInstitutionId();

}
