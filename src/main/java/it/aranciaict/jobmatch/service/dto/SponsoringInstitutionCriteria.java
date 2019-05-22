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
import it.aranciaict.jobmatch.domain.enumeration.IstituitionSectorType;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionType;

/**
 * Criteria class for the SponsoringInstitution entity. This class is used in SponsoringInstitutionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /sponsoring-institutions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@SuppressWarnings("serial")
public class SponsoringInstitutionCriteria implements Serializable {
    
    /**
     * Class for filtering IstituitionSectorType.
     */
    public static class IstituitionSectorTypeFilter extends Filter<IstituitionSectorType> {
    }
    
    /**
     * Class for filtering IstituitionType.
     */
    public static class IstituitionTypeFilter extends Filter<IstituitionType> {
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

    /** The istituition name. */
    private StringFilter istituitionName;

    /** The istituition sector. */
    private IstituitionSectorTypeFilter istituitionSector;

    /** The istituition type. */
    private IstituitionTypeFilter istituitionType;

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
     * Gets the istituition name.
     *
     * @return the istituition name
     */
    public StringFilter getIstituitionName() {
        return istituitionName;
    }

    /**
     * Sets the istituition name.
     *
     * @param istituitionName the new istituition name
     */
    public void setIstituitionName(StringFilter istituitionName) {
        this.istituitionName = istituitionName;
    }

    /**
     * Gets the istituition sector.
     *
     * @return the istituition sector
     */
    public IstituitionSectorTypeFilter getIstituitionSector() {
        return istituitionSector;
    }

    /**
     * Sets the istituition sector.
     *
     * @param istituitionSector the new istituition sector
     */
    public void setIstituitionSector(IstituitionSectorTypeFilter istituitionSector) {
        this.istituitionSector = istituitionSector;
    }

    /**
     * Gets the istituition type.
     *
     * @return the istituition type
     */
    public IstituitionTypeFilter getIstituitionType() {
        return istituitionType;
    }

    /**
     * Sets the istituition type.
     *
     * @param istituitionType the new istituition type
     */
    public void setIstituitionType(IstituitionTypeFilter istituitionType) {
        this.istituitionType = istituitionType;
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


    /* (non-Javadoc)
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
        final SponsoringInstitutionCriteria that = (SponsoringInstitutionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(istituitionName, that.istituitionName) &&
            Objects.equals(istituitionSector, that.istituitionSector) &&
            Objects.equals(istituitionType, that.istituitionType) &&
            Objects.equals(streetAddress, that.streetAddress) &&
            Objects.equals(foundationDate, that.foundationDate) &&
            Objects.equals(vatNumber, that.vatNumber) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(mobilePhone, that.mobilePhone) &&
            Objects.equals(country, that.country) &&
            Objects.equals(region, that.region) &&
            Objects.equals(province, that.province) &&
            Objects.equals(city, that.city) &&
            Objects.equals(cap, that.cap) &&
            Objects.equals(urlSite, that.urlSite) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(userId, that.userId);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        lastModifiedBy,
        createdDate,
        lastModifiedDate,
        istituitionName,
        istituitionSector,
        istituitionType,
        streetAddress,
        foundationDate,
        vatNumber,
        email,
        phone,
        mobilePhone,
        country,
        region,
        province,
        city,
        cap,
        urlSite,
        enabled,
        userId
        );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SponsoringInstitutionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (istituitionName != null ? "istituitionName=" + istituitionName + ", " : "") +
                (istituitionSector != null ? "istituitionSector=" + istituitionSector + ", " : "") +
                (istituitionType != null ? "istituitionType=" + istituitionType + ", " : "") +
                (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "") +
                (foundationDate != null ? "foundationDate=" + foundationDate + ", " : "") +
                (vatNumber != null ? "vatNumber=" + vatNumber + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (mobilePhone != null ? "mobilePhone=" + mobilePhone + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (region != null ? "region=" + region + ", " : "") +
                (province != null ? "province=" + province + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (cap != null ? "cap=" + cap + ", " : "") +
                (urlSite != null ? "urlSite=" + urlSite + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
