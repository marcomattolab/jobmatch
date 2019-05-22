package it.aranciaict.jobmatch.web.rest.vm;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;

/**
 * The Class CompanyManagedUserVM.
 */
public class CompanyManagedUserVM extends ManagedUserVM {

	/**  dati Azienda. */
	@NotBlank
	private String companyName;
	
    /** The foundation date. */
    private LocalDate foundationDate;
    
	/** The company description. */
	private String companyDescription;
	
	/** The country. */
	private Country country;

	/** The vat number. */
	private String vatNumber;
	
	/** The phone. */
	private String phone;
	
	/** The company sector id. */
	private Long sectorId;
	
	/** The company type. */
	private CompanyType companyType;

	/** The company size. */
	private CompanySizeType companySize;

	
	/** The number of employee. */
	private NumberOfEmployees numberOfEmployee;
	
	
	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
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
	 * Gets the company description.
	 *
	 * @return the company description
	 */
	public String getCompanyDescription() {
		return companyDescription;
	}

	/**
	 * Sets the company description.
	 *
	 * @param companyDescription the new company description
	 */
	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	/**
	 * Gets the company size.
	 *
	 * @return the company size
	 */
	public CompanySizeType getCompanySize() {
		return companySize;
	}

	/**
	 * Sets the company size.
	 *
	 * @param companySize the new company size
	 */
	public void setCompanySize(CompanySizeType companySize) {
		this.companySize = companySize;
	}

	/**
	 * Gets the company type.
	 *
	 * @return the company type
	 */
	public CompanyType getCompanyType() {
		return companyType;
	}

	/**
	 * Sets the company type.
	 *
	 * @param companyType the new company type
	 */
	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	/**
	 * Gets the number of employee.
	 *
	 * @return the number of employee
	 */
	public NumberOfEmployees getNumberOfEmployee() {
		return numberOfEmployee;
	}

	/**
	 * Sets the number of employee.
	 *
	 * @param numberOfEmployee the new number of employee
	 */
	public void setNumberOfEmployee(NumberOfEmployees numberOfEmployee) {
		this.numberOfEmployee = numberOfEmployee;
	}

	/**
	 * getSectorId.
	 *
	 * @return the sectorId
	 */
	public Long getSectorId() {
		return sectorId;
	}

	/**
	 * setSectorId.
	 *
	 * @param sectorId the sectorId
	 */
	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

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

	

}
