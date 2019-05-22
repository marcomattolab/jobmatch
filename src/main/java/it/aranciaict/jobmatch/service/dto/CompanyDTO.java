package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;

/**
 * A DTO for the Company entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Company")
public class CompanyDTO implements Serializable {

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

	/** The company name. */
	@NotNull
	@Size(max = ValidationConstants.SIZE_255)
	private String companyName;

	/** The company description. */
	@Lob
	private String companyDescription;

	/** The company size. */
	private CompanySizeType companySize;

	/** The company type. */
	private CompanyType companyType;

	/** The number of employee. */
	private NumberOfEmployees numberOfEmployee;

	/** The street address. */
	private String streetAddress;

	/** The foundation date. */
	@PastOrPresent
	private LocalDate foundationDate;

	/** The vat number. */
	@Pattern(regexp = ValidationConstants.VAT_CODE_REG_EXP)
	private String vatNumber;

	/** The email. */
	@NotNull
	@Email
	private String email;

	/** The phone. */
	@Pattern(regexp = ValidationConstants.PHONE_REG_EXP)
	private String phone;

	/** The mobile phone. */
	@Pattern(regexp = ValidationConstants.PHONE_REG_EXP)
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
	@Size(max = ValidationConstants.SIZE_20)
	private String cap;

	/** The url site. */
	@Pattern(regexp = ValidationConstants.URL_SITE_REG_EXP)
	private String urlSite;

	/** The enabled. */
	private Boolean enabled;

	/** The user id. */
	private Long userId;

	/** The sector id. */
	private Long sectorId;

//	/** The sectors. */
//	private Set<CompanySectorDTO> sectors = new HashSet<>();

	/** The image url. */
	private String imageUrl;

	/** The sponsoring institution id. */
	private Long sponsoringInstitutionId;

	/** The edit available. */
	private boolean editAvailable;

	/** The delete available. */
	private boolean deleteAvailable;

	/** The application available. */
	private boolean applicationAvailable;
	

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
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	/**
//	 * Gets the sectors.
//	 *
//	 * @return the sectors
//	 */
//	public Set<CompanySectorDTO> getSectors() {
//		return sectors;
//	}
//
//	/**
//	 * Sets the sectors.
//	 *
//	 * @param companySectors the new sectors
//	 */
//	public void setSectors(Set<CompanySectorDTO> companySectors) {
//		this.sectors = companySectors;
//	}

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
	 * @param sectorId the new sector id
	 */
	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
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
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
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

	/**
	 * Checks if is application available.
	 *
	 * @return true, if is application available
	 */
	public boolean isApplicationAvailable() {
		return applicationAvailable;
	}

	/**
	 * Sets the application available.
	 *
	 * @param applicationAvailable the new application available
	 */
	public void setApplicationAvailable(boolean applicationAvailable) {
		this.applicationAvailable = applicationAvailable;
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

		CompanyDTO companyDTO = (CompanyDTO) o;
		if (companyDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), companyDTO.getId());
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
		return "CompanyDTO{" + "id=" + getId() + ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", lastModifiedDate='"
				+ getLastModifiedDate() + "'" + ", companyName='" + getCompanyName() + "'" + ", companyDescription='"
				+ getCompanyDescription() + "'" + ", companySize='" + getCompanySize() + "'" + ", companyType='"
				+ getCompanyType() + "'" + ", numberOfEmployee='" + getNumberOfEmployee() + "'" + ", streetAddress='"
				+ getStreetAddress() + "'" + ", foundationDate='" + getFoundationDate() + "'" + ", vatNumber='"
				+ getVatNumber() + "'" + ", email='" + getEmail() + "'" + ", phone='" + getPhone() + "'"
				+ ", mobilePhone='" + getMobilePhone() + "'" + ", country='" + getCountry() + "'" + ", region='"
				+ getRegion() + "'" + ", province='" + getProvince() + "'" + ", city='" + getCity() + "'" + ", cap='"
				+ getCap() + "'" + ", urlSite='" + getUrlSite() + "'" + ", enabled='" + isEnabled() + "'" + ", user="
				+ getUserId() + ", sponsoringInstitutionId=" + getSponsoringInstitutionId() + "}";
	}
}
