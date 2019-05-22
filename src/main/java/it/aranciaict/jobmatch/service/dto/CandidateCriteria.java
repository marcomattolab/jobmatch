package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.GenderType;

// TODO: Auto-generated Javadoc
/**
 * Criteria class for the Candidate entity. This class is used in
 * CandidateResource to receive all the possible filtering options from the Http
 * GET request parameters. For example the following could be a valid requests:
 * <code> /candidates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@SuppressWarnings("serial")
public class CandidateCriteria implements Serializable {

	/**
	 * Class for filtering GenderType.
	 */
	public static class GenderTypeFilter extends Filter<GenderType> {
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

	/** The last name. */
	private StringFilter lastName;

	/** The first name. */
	private StringFilter firstName;

	/** The gender. */
	private GenderTypeFilter gender;

	/** The birthday. */
	private LocalDateFilter birthday;

	/** The street address. */
	private StringFilter streetAddress;

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

	/** The enabled. */
	private BooleanFilter enabled;

	/** The user id. */
	private LongFilter userId;

	/** The document id. */
	private LongFilter documentId;

	/** The job experience id. */
	private LongFilter jobExperienceId;

	/** The education id. */
	private LongFilter educationId;

	/** The skill id. */
	private LongFilter skillId;
	
	/** The country. */
	private CountryFilter currentJobCountry;
	
	/** The skill tag name. */
	private StringFilter currentJobTitle;
	
	/** The skill tag name. */
	private StringFilter skillTagName;
	
	/** The current country. */
	private CountryFilter currentCountry;
	
	/** The current city. */
	private StringFilter currentCity;
	
	/** The skill tag name. */
	private LongFilter skillTagId;
	
	/** The macth all skill tag. */
	private boolean macthAllSkillTag = true;

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
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public StringFilter getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(StringFilter lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public StringFilter getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(StringFilter firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public GenderTypeFilter getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(GenderTypeFilter gender) {
		this.gender = gender;
	}

	/**
	 * Gets the birthday.
	 *
	 * @return the birthday
	 */
	public LocalDateFilter getBirthday() {
		return birthday;
	}

	/**
	 * Sets the birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(LocalDateFilter birthday) {
		this.birthday = birthday;
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
	 * Gets the document id.
	 *
	 * @return the document id
	 */
	public LongFilter getDocumentId() {
		return documentId;
	}

	/**
	 * Sets the document id.
	 *
	 * @param documentId the new document id
	 */
	public void setDocumentId(LongFilter documentId) {
		this.documentId = documentId;
	}

	/**
	 * Gets the job experience id.
	 *
	 * @return the job experience id
	 */
	public LongFilter getJobExperienceId() {
		return jobExperienceId;
	}

	/**
	 * Sets the job experience id.
	 *
	 * @param jobExperienceId the new job experience id
	 */
	public void setJobExperienceId(LongFilter jobExperienceId) {
		this.jobExperienceId = jobExperienceId;
	}

	/**
	 * Gets the education id.
	 *
	 * @return the education id
	 */
	public LongFilter getEducationId() {
		return educationId;
	}

	/**
	 * Sets the education id.
	 *
	 * @param educationId the new education id
	 */
	public void setEducationId(LongFilter educationId) {
		this.educationId = educationId;
	}

	/**
	 * Gets the skill id.
	 *
	 * @return the skill id
	 */
	public LongFilter getSkillId() {
		return skillId;
	}

	/**
	 * Sets the skill id.
	 *
	 * @param skillId the new skill id
	 */
	public void setSkillId(LongFilter skillId) {
		this.skillId = skillId;
	}
	
	/**
	 * Gets the current job country.
	 *
	 * @return the current job country
	 */
	public CountryFilter getCurrentJobCountry() {
		return currentJobCountry;
	}

	/**
	 * Sets the current job country.
	 *
	 * @param currentJobCountry the new current job country
	 */
	public void setCurrentJobCountry(CountryFilter currentJobCountry) {
		this.currentJobCountry = currentJobCountry;
	}

	/**
	 * Gets the skill tag name.
	 *
	 * @return the skill tag name
	 */
	public StringFilter getSkillTagName() {
		return skillTagName;
	}

	/**
	 * Sets the skill tag name.
	 *
	 * @param skillTagName the new skill tag name
	 */
	public void setSkillTagName(StringFilter skillTagName) {
		this.skillTagName = skillTagName;
	}

	
	/**
	 * Gets the current job title.
	 *
	 * @return the current job title
	 */
	public StringFilter getCurrentJobTitle() {
		return currentJobTitle;
	}

	/**
	 * Sets the current job title.
	 *
	 * @param currentJobTitle the new current job title
	 */
	public void setCurrentJobTitle(StringFilter currentJobTitle) {
		this.currentJobTitle = currentJobTitle;
	}
	
	/**
	 * Gets the current country.
	 *
	 * @return the current country
	 */
	public CountryFilter getCurrentCountry() {
		return currentCountry;
	}

	/**
	 * Sets the current country.
	 *
	 * @param currentCountry the new current country
	 */
	public void setCurrentCountry(CountryFilter currentCountry) {
		this.currentCountry = currentCountry;
	}
	
	/**
	 * Gets the current city.
	 *
	 * @return the current city
	 */
	public StringFilter getCurrentCity() {
		return currentCity;
	}

	/**
	 * Sets the current city.
	 *
	 * @param currentCity the new current city
	 */
	public void setCurrentCity(StringFilter currentCity) {
		this.currentCity = currentCity;
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
	
	/**
	 * Checks if is macth all skill tag.
	 *
	 * @return true, if is macth all skill tag
	 */
	public boolean isMacthAllSkillTag() {
		return macthAllSkillTag;
	}

	/**
	 * Sets the macth all skill tag.
	 *
	 * @param macthAllSkillTag the new macth all skill tag
	 */
	public void setMacthAllSkillTag(boolean macthAllSkillTag) {
		this.macthAllSkillTag = macthAllSkillTag;
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
		final CandidateCriteria that = (CandidateCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(createdBy, that.createdBy)
				&& Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(createdDate, that.createdDate)
				&& Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(lastName, that.lastName)
				&& Objects.equals(firstName, that.firstName) && Objects.equals(gender, that.gender)
				&& Objects.equals(birthday, that.birthday) && Objects.equals(streetAddress, that.streetAddress)
				&& Objects.equals(email, that.email) && Objects.equals(phone, that.phone)
				&& Objects.equals(mobilePhone, that.mobilePhone) && Objects.equals(country, that.country)
				&& Objects.equals(region, that.region) && Objects.equals(province, that.province)
				&& Objects.equals(city, that.city) && Objects.equals(cap, that.cap)
				&& Objects.equals(enabled, that.enabled) && Objects.equals(userId, that.userId)
				&& Objects.equals(documentId, that.documentId) && Objects.equals(jobExperienceId, that.jobExperienceId)
				&& Objects.equals(educationId, that.educationId) && Objects.equals(skillId, that.skillId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, createdBy, lastModifiedBy, createdDate, lastModifiedDate, lastName, firstName, gender,
				birthday, streetAddress, email, phone, mobilePhone, country, region, province, city, cap, enabled,
				userId, documentId, jobExperienceId, educationId, skillId);
	}

	@Override
	public String toString() {
		return "CandidateCriteria [" + (id != null ? "id=" + id + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", " : "")
				+ (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "")
				+ (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (gender != null ? "gender=" + gender + ", " : "")
				+ (birthday != null ? "birthday=" + birthday + ", " : "")
				+ (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (phone != null ? "phone=" + phone + ", " : "")
				+ (mobilePhone != null ? "mobilePhone=" + mobilePhone + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (region != null ? "region=" + region + ", " : "")
				+ (province != null ? "province=" + province + ", " : "") + (city != null ? "city=" + city + ", " : "")
				+ (cap != null ? "cap=" + cap + ", " : "") + (enabled != null ? "enabled=" + enabled + ", " : "")
				+ (userId != null ? "userId=" + userId + ", " : "")
				+ (documentId != null ? "documentId=" + documentId + ", " : "")
				+ (jobExperienceId != null ? "jobExperienceId=" + jobExperienceId + ", " : "")
				+ (educationId != null ? "educationId=" + educationId + ", " : "")
				+ (skillId != null ? "skillId=" + skillId + ", " : "")
				+ (currentJobCountry != null ? "currentJobCountry=" + currentJobCountry + ", " : "")
				+ (currentJobTitle != null ? "currentJobTitle=" + currentJobTitle + ", " : "")
				+ (skillTagName != null ? "skillTagName=" + skillTagName + ", " : "")
				+ (currentCountry != null ? "currentCountry=" + currentCountry + ", " : "")
				+ (currentCity != null ? "currentCity=" + currentCity + ", " : "")
				+ (skillTagId != null ? "skillTagId=" + skillTagId : "") + "]";
	}

	
}
