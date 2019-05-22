package it.aranciaict.jobmatch.web.rest.vm;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import it.aranciaict.jobmatch.domain.enumeration.Country;

/**
 * The Class SponsoringInstitutionManagedUserVM.
 */
public class SponsoringInstitutionManagedUserVM extends ManagedUserVM {


	/** The instituition name. */
	@NotBlank
	private String istituitionName;

	/** The foundation date. */
	private LocalDate foundationDate;

	/** The instituition description. */
	private String istituitionDescription;
	
	/** The country. */
	private Country country;
	
	/** The vat number. */
	private String vatNumber;
	
	/** The phone. */
	private String phone;


	/**
	 * Gets the foundation date.
	 *
	 * @return the foundation date
	 */
	public LocalDate getFoundationDate() {
		return foundationDate;
	}

	/**
	 * Sets the foundation date.
	 *
	 * @param foundationDate the new foundation date
	 */
	public void setFoundationDate(LocalDate foundationDate) {
		this.foundationDate = foundationDate;
	}

	/**
	 * getIstituitionName.
	 *
	 * @return istituitionName
	 */
	public String getIstituitionName() {
		return istituitionName;
	}

	/**
	 * setIstituitionName.
	 *
	 * @param istituitionName the istituitionName
	 */
	public void setIstituitionName(String istituitionName) {
		this.istituitionName = istituitionName;
	}

	/**
	 * getIstituitionDescription.
	 *
	 * @return istituitionDescription
	 */
	public String getIstituitionDescription() {
		return istituitionDescription;
	}

	/**
	 * setIstituitionDescription.
	 *
	 * @param istituitionDescription the istituitionDescription
	 */
	public void setIstituitionDescription(String istituitionDescription) {
		this.istituitionDescription = istituitionDescription;
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
	 * Gets the vat number.
	 *
	 * @return the vat number
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * Sets the vat number.
	 *
	 * @param vatNumber the new vat number
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
