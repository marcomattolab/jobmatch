package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the Company entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Company")
public class CompanyInfoDTO implements Serializable {

	/** The id. */
	private Long id;

	/** The company name. */
	private String companyName;

	/** The company size. */
	private CompanySizeType companySize;

	/** The company type. */
	private CompanyType companyType;

	/** The number of employee. */
	private NumberOfEmployees numberOfEmployee;

	/** The street address. */
	private String streetAddress;

	/** The foundation date. */
	private LocalDate foundationDate;

	/** The vat number. */
	private String vatNumber;

	/** The email. */
	private String email;

	/** The phone. */
	private String phone;

	/** The mobile phone. */
	private String mobilePhone;

	/** The country. */
	private Country country;

	/** The region. */
	private String region;

	/** The province. */
	private String province;

	/** The city. */
	private String city;

	/** The cap. */
	private String cap;

	/** The url site. */
	private String urlSite;

	/** The picture url. */
	private String imageUrl;

	/** The sector. */
	private String sectorId;
	
	/** The sponsoring institution */
	private Long sponsoringInstitutionId;

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
	 * 
	 * /** Gets the company name.
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
	 * Gets the street address.
	 *
	 * @return the street address
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Sets the street address.
	 *
	 * @param streetAddress the new street address
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * Gets the mobile phone.
	 *
	 * @return the mobile phone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Sets the mobile phone.
	 *
	 * @param mobilePhone the new mobile phone
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the province.
	 *
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Sets the province.
	 *
	 * @param province the new province
	 */
	public void setProvince(String province) {
		this.province = province;
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
	 * Gets the cap.
	 *
	 * @return the cap
	 */
	public String getCap() {
		return cap;
	}

	/**
	 * Sets the cap.
	 *
	 * @param cap the new cap
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}

	/**
	 * Gets the url site.
	 *
	 * @return the url site
	 */
	public String getUrlSite() {
		return urlSite;
	}

	/**
	 * Sets the url site.
	 *
	 * @param urlSite the new url site
	 */
	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 *
	 * @param imageUrl the new image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Gets the sector id.
	 *
	 * @return the sector id
	 */
	public String getSectorId() {
		return sectorId;
	}

	/**
	 * Sets the sector id.
	 *
	 * @param sectorId the new sector id
	 */
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CompanyInfoDTO companyDTO = (CompanyInfoDTO) o;
		if (companyDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), companyDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "CompanyInfoDTO [id=" + id + ", companyName=" + companyName + ", companySize=" + companySize
				+ ", companyType=" + companyType + ", numberOfEmployee=" + numberOfEmployee + ", streetAddress="
				+ streetAddress + ", foundationDate=" + foundationDate + ", vatNumber=" + vatNumber + ", email=" + email
				+ ", phone=" + phone + ", mobilePhone=" + mobilePhone + ", country=" + country + ", region=" + region
				+ ", province=" + province + ", city=" + city + ", cap=" + cap + ", urlSite=" + urlSite + ", imageUrl="
				+ imageUrl + ", sectorId=" + sectorId + "]";
	}

}
