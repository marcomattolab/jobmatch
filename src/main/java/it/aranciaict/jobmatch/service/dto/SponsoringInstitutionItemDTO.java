package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionSectorType;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionType;

/**
 * A DTO for the SponsoringInstitution entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Sponsoring Institution")
public class SponsoringInstitutionItemDTO implements Serializable {

	/** The id. */
	private Long id;

	/** The created by. */
	private String createdBy;

	/** The last modified by. */
	private String lastModifiedBy;

	/** The created date. */
	private Instant createdDate;

	/** The last modified date. */
	private Instant lastModifiedDate;

	/** The istituition name. */
	private String istituitionName;

	/** The istituition sector. */
	private IstituitionSectorType istituitionSector;

	/** The istituition type. */
	private IstituitionType istituitionType;

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

	/** The enabled. */
	private Boolean enabled;

	/** The user id. */
	private Long userId;

	/** The user id. */
	private String userName;

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
	 * Gets the istituition name.
	 *
	 * @return the istituition name
	 */
	public String getIstituitionName() {
		return istituitionName;
	}

	/**
	 * Sets the istituition name.
	 *
	 * @param istituitionName the new istituition name
	 */
	public void setIstituitionName(String istituitionName) {
		this.istituitionName = istituitionName;
	}

	/**
	 * Gets the istituition sector.
	 *
	 * @return the istituition sector
	 */
	public IstituitionSectorType getIstituitionSector() {
		return istituitionSector;
	}

	/**
	 * Sets the istituition sector.
	 *
	 * @param istituitionSector the new istituition sector
	 */
	public void setIstituitionSector(IstituitionSectorType istituitionSector) {
		this.istituitionSector = istituitionSector;
	}

	/**
	 * Gets the istituition type.
	 *
	 * @return the istituition type
	 */
	public IstituitionType getIstituitionType() {
		return istituitionType;
	}

	/**
	 * Sets the istituition type.
	 *
	 * @param istituitionType the new istituition type
	 */
	public void setIstituitionType(IstituitionType istituitionType) {
		this.istituitionType = istituitionType;
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

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
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

		SponsoringInstitutionItemDTO sponsoringInstitutionDTO = (SponsoringInstitutionItemDTO) o;
		if (sponsoringInstitutionDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), sponsoringInstitutionDTO.getId());
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
		return "SponsoringInstitutionItemDTO [id=" + id + ", createdBy=" + createdBy + ", lastModifiedBy="
				+ lastModifiedBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
				+ ", istituitionName=" + istituitionName + ", istituitionSector=" + istituitionSector
				+ ", istituitionType=" + istituitionType + ", streetAddress=" + streetAddress + ", foundationDate="
				+ foundationDate + ", vatNumber=" + vatNumber + ", email=" + email + ", phone=" + phone
				+ ", mobilePhone=" + mobilePhone + ", country=" + country + ", region=" + region + ", province="
				+ province + ", city=" + city + ", cap=" + cap + ", urlSite=" + urlSite + ", enabled=" + enabled
				+ ", userId=" + userId + ", userName=" + userName + "]";
	}

}
