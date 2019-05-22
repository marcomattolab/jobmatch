package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;

/**
 * Criteria class for the Company entity. This class is used in CompanyResource
 * to receive all the possible filtering options from the Http GET request
 * parameters. For example the following could be a valid requests:
 * <code> /companies?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@SuppressWarnings("serial")
public class CompanyCriteria implements Serializable {

	/**
	 * Class for filtering CompanySizeType.
	 */
	public static class CompanySizeTypeFilter extends Filter<CompanySizeType> {
	}

	/**
	 * Class for filtering CompanyType.
	 */
	public static class CompanyTypeFilter extends Filter<CompanyType> {
	}

	/**
	 * Class for filtering NumberOfEmployees.
	 */
	public static class NumberOfEmployeesFilter extends Filter<NumberOfEmployees> {
	}

	/**
	 * Class for filtering Country.
	 */
	public static class CountryFilter extends Filter<Country> {
	}

	/** The id. */
	private LongFilter id;

	/** The created by. */
	private StringFilter createdBy;

	/** The last modified by. */
	private StringFilter lastModifiedBy;

	/** The created date. */
	private InstantFilter createdDate;

	/** The last modified date. */
	private InstantFilter lastModifiedDate;

	/** The company name. */
	private StringFilter companyName;

	/** The company size. */
	private CompanySizeTypeFilter companySize;

	/** The company type. */
	private CompanyTypeFilter companyType;

	/** The number of employee. */
	private NumberOfEmployeesFilter numberOfEmployee;

	/** The street address. */
	private StringFilter streetAddress;

	/** The foundation date. */
	private LocalDateFilter foundationDate;

	/** The vat number. */
	private StringFilter vatNumber;

	/** The email. */
	private StringFilter email;

	/** The phone. */
	private StringFilter phone;

	/** The mobile phone. */
	private StringFilter mobilePhone;

	/** The country. */
	private CountryFilter country;

	/** The region. */
	private StringFilter region;

	/** The province. */
	private StringFilter province;

	/** The city. */
	private StringFilter city;

	/** The cap. */
	private StringFilter cap;

	/** The url site. */
	private StringFilter urlSite;

	/** The enabled. */
	private BooleanFilter enabled;

	/** The user id. */
	private LongFilter userId;

	/** The sector id. */
	private LongFilter sectorId;

    /** The sponsoring institution id. */
    private LongFilter sponsoringInstitutionId;
	
	/** The skill tag name. */
	private LongFilter skillTagId;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public LongFilter getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(LongFilter id) {
		this.id = id;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public StringFilter getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(StringFilter createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public StringFilter getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy the new last modified by
	 */
	public void setLastModifiedBy(StringFilter lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public InstantFilter getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(InstantFilter createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last modified date.
	 *
	 * @return the last modified date
	 */
	public InstantFilter getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * Sets the last modified date.
	 *
	 * @param lastModifiedDate the new last modified date
	 */
	public void setLastModifiedDate(InstantFilter lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public StringFilter getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the new company name
	 */
	public void setCompanyName(StringFilter companyName) {
		this.companyName = companyName;
	}

	/**
	 * Gets the company size.
	 *
	 * @return the company size
	 */
	public CompanySizeTypeFilter getCompanySize() {
		return companySize;
	}

	/**
	 * Sets the company size.
	 *
	 * @param companySize the new company size
	 */
	public void setCompanySize(CompanySizeTypeFilter companySize) {
		this.companySize = companySize;
	}

	/**
	 * Gets the company type.
	 *
	 * @return the company type
	 */
	public CompanyTypeFilter getCompanyType() {
		return companyType;
	}

	/**
	 * Sets the company type.
	 *
	 * @param companyType the new company type
	 */
	public void setCompanyType(CompanyTypeFilter companyType) {
		this.companyType = companyType;
	}

	/**
	 * Gets the number of employee.
	 *
	 * @return the number of employee
	 */
	public NumberOfEmployeesFilter getNumberOfEmployee() {
		return numberOfEmployee;
	}

	/**
	 * Sets the number of employee.
	 *
	 * @param numberOfEmployee the new number of employee
	 */
	public void setNumberOfEmployee(NumberOfEmployeesFilter numberOfEmployee) {
		this.numberOfEmployee = numberOfEmployee;
	}

	/**
	 * Gets the street address.
	 *
	 * @return the street address
	 */
	public StringFilter getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Sets the street address.
	 *
	 * @param streetAddress the new street address
	 */
	public void setStreetAddress(StringFilter streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * Gets the foundation date.
	 *
	 * @return the foundation date
	 */
	public LocalDateFilter getFoundationDate() {
		return foundationDate;
	}

	/**
	 * Sets the foundation date.
	 *
	 * @param foundationDate the new foundation date
	 */
	public void setFoundationDate(LocalDateFilter foundationDate) {
		this.foundationDate = foundationDate;
	}

	/**
	 * Gets the vat number.
	 *
	 * @return the vat number
	 */
	public StringFilter getVatNumber() {
		return vatNumber;
	}

	/**
	 * Sets the vat number.
	 *
	 * @param vatNumber the new vat number
	 */
	public void setVatNumber(StringFilter vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public StringFilter getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(StringFilter email) {
		this.email = email;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public StringFilter getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(StringFilter phone) {
		this.phone = phone;
	}

	/**
	 * Gets the mobile phone.
	 *
	 * @return the mobile phone
	 */
	public StringFilter getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Sets the mobile phone.
	 *
	 * @param mobilePhone the new mobile phone
	 */
	public void setMobilePhone(StringFilter mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public CountryFilter getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(CountryFilter country) {
		this.country = country;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public StringFilter getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(StringFilter region) {
		this.region = region;
	}

	/**
	 * Gets the province.
	 *
	 * @return the province
	 */
	public StringFilter getProvince() {
		return province;
	}

	/**
	 * Sets the province.
	 *
	 * @param province the new province
	 */
	public void setProvince(StringFilter province) {
		this.province = province;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public StringFilter getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(StringFilter city) {
		this.city = city;
	}

	/**
	 * Gets the cap.
	 *
	 * @return the cap
	 */
	public StringFilter getCap() {
		return cap;
	}

	/**
	 * Sets the cap.
	 *
	 * @param cap the new cap
	 */
	public void setCap(StringFilter cap) {
		this.cap = cap;
	}

	/**
	 * Gets the url site.
	 *
	 * @return the url site
	 */
	public StringFilter getUrlSite() {
		return urlSite;
	}

	/**
	 * Sets the url site.
	 *
	 * @param urlSite the new url site
	 */
	public void setUrlSite(StringFilter urlSite) {
		this.urlSite = urlSite;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public BooleanFilter getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(BooleanFilter enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public LongFilter getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(LongFilter userId) {
		this.userId = userId;
	}

	/**
	 * Gets the sector id.
	 *
	 * @return the sector id
	 */
	public LongFilter getSectorId() {
		return sectorId;
	}

	/**
	 * Sets the sector id.
	 *
	 * @param sectorId the new sector id
	 */
	public void setSectorId(LongFilter sectorId) {
		this.sectorId = sectorId;
	}

	/**
	 * Gets the sponsoring institution id.
	 *
	 * @return the sponsoring institution id
	 */
	public LongFilter getSponsoringInstitutionId() {
		return sponsoringInstitutionId;
	}

	/**
	 * Sets the sponsoring institution id.
	 *
	 * @param sponsoringInstitutionId the new sponsoring institution id
	 */
	public void setSponsoringInstitutionId(LongFilter sponsoringInstitutionId) {
		this.sponsoringInstitutionId = sponsoringInstitutionId;
	}

	/**
	 * Gets the skill tag id.
	 *
	 * @return the skill tag id
	 */
	public LongFilter getSkillTagId() {
		return skillTagId;
	}

	/**
	 * Sets the skill tag id.
	 *
	 * @param skillTagId the new skill tag id
	 */
	public void setSkillTagId(LongFilter skillTagId) {
		this.skillTagId = skillTagId;
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
		final CompanyCriteria that = (CompanyCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(createdBy, that.createdBy)
				&& Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(createdDate, that.createdDate)
				&& Objects.equals(lastModifiedDate, that.lastModifiedDate)
				&& Objects.equals(companyName, that.companyName) && Objects.equals(companySize, that.companySize)
				&& Objects.equals(companyType, that.companyType)
				&& Objects.equals(numberOfEmployee, that.numberOfEmployee)
				&& Objects.equals(streetAddress, that.streetAddress)
				&& Objects.equals(foundationDate, that.foundationDate) && Objects.equals(vatNumber, that.vatNumber)
				&& Objects.equals(email, that.email) && Objects.equals(phone, that.phone)
				&& Objects.equals(mobilePhone, that.mobilePhone) && Objects.equals(country, that.country)
				&& Objects.equals(region, that.region) && Objects.equals(province, that.province)
				&& Objects.equals(city, that.city) && Objects.equals(cap, that.cap)
				&& Objects.equals(urlSite, that.urlSite) && Objects.equals(enabled, that.enabled)
				&& Objects.equals(userId, that.userId) && Objects.equals(sectorId, that.sectorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, createdBy, lastModifiedBy, createdDate, lastModifiedDate, companyName, companySize,
				companyType, numberOfEmployee, streetAddress, foundationDate, vatNumber, email, phone, mobilePhone,
				country, region, province, city, cap, urlSite, enabled, userId, sectorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanyCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", " : "")
				+ (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "")
				+ (companyName != null ? "companyName=" + companyName + ", " : "")
				+ (companySize != null ? "companySize=" + companySize + ", " : "")
				+ (companyType != null ? "companyType=" + companyType + ", " : "")
				+ (numberOfEmployee != null ? "numberOfEmployee=" + numberOfEmployee + ", " : "")
				+ (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "")
				+ (foundationDate != null ? "foundationDate=" + foundationDate + ", " : "")
				+ (vatNumber != null ? "vatNumber=" + vatNumber + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (phone != null ? "phone=" + phone + ", " : "")
				+ (mobilePhone != null ? "mobilePhone=" + mobilePhone + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (region != null ? "region=" + region + ", " : "")
				+ (province != null ? "province=" + province + ", " : "") + (city != null ? "city=" + city + ", " : "")
				+ (cap != null ? "cap=" + cap + ", " : "") + (urlSite != null ? "urlSite=" + urlSite + ", " : "")
				+ (enabled != null ? "enabled=" + enabled + ", " : "")
				+ (userId != null ? "userId=" + userId + ", " : "")
				+ (sectorId != null ? "sectorId=" + sectorId + ", " : "") + "}";
	}

}
