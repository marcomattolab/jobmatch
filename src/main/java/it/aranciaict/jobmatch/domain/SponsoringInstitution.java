package it.aranciaict.jobmatch.domain;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionSectorType;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionType;

/**
 * A SponsoringInstitution.
 */
@Entity
@Table(name = "sponsoring_institution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SponsoringInstitution extends AbstractAuditingEntity  implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The istituition name. */
    @NotNull
    @Size(max = ValidationConstants.SIZE_255)
    @Column(name = "istituition_name", length = ValidationConstants.SIZE_255, nullable = false)
    private String istituitionName;

    /** The istituition description. */
    @Lob
    @Column(name = "istituition_description")
    private String istituitionDescription;

    /** The istituition sector. */
    @Enumerated(EnumType.STRING)
    @Column(name = "istituition_sector")
    private IstituitionSectorType istituitionSector;

    /** The istituition type. */
    @Enumerated(EnumType.STRING)
    @Column(name = "istituition_type")
    private IstituitionType istituitionType;

    /** The street address. */
    @Column(name = "street_address")
    private String streetAddress;

    /** The foundation date. */
    @Column(name = "foundation_date")
    private LocalDate foundationDate;

    /** The vat number. */
    @Column(name = "vat_number")
    private String vatNumber;

    /** The email. */
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    /** The phone. */
    @Column(name = "phone")
    private String phone;

    /** The mobile phone. */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /** The country. */
    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    /** The region. */
    @Column(name = "region")
    private String region;

    /** The province. */
    @Column(name = "province")
    private String province;

    /** The city. */
    @Column(name = "city")
    private String city;

    /** The cap. */
    @Size(max = ValidationConstants.SIZE_20)
    @Column(name = "cap", length = ValidationConstants.SIZE_20)
    private String cap;

    /** The url site. */
    @Pattern(regexp = "^www.[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "url_site")
    private String urlSite;

    /** The enabled. */
    @Column(name = "enabled")
    private Boolean enabled;

    /** The user. */
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    /**
     * Gets the id.
     *
     * @return the id
     */
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
     * Gets the istituition name.
     *
     * @return the istituition name
     */
    public String getIstituitionName() {
        return istituitionName;
    }

    /**
     * Istituition name.
     *
     * @param istituitionName the istituition name
     * @return the sponsoring institution
     */
    public SponsoringInstitution istituitionName(String istituitionName) {
        this.istituitionName = istituitionName;
        return this;
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
     * Gets the istituition description.
     *
     * @return the istituition description
     */
    public String getIstituitionDescription() {
        return istituitionDescription;
    }

    /**
     * Istituition description.
     *
     * @param istituitionDescription the istituition description
     * @return the sponsoring institution
     */
    public SponsoringInstitution istituitionDescription(String istituitionDescription) {
        this.istituitionDescription = istituitionDescription;
        return this;
    }

    /**
     * Sets the istituition description.
     *
     * @param istituitionDescription the new istituition description
     */
    public void setIstituitionDescription(String istituitionDescription) {
        this.istituitionDescription = istituitionDescription;
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
     * Istituition sector.
     *
     * @param istituitionSector the istituition sector
     * @return the sponsoring institution
     */
    public SponsoringInstitution istituitionSector(IstituitionSectorType istituitionSector) {
        this.istituitionSector = istituitionSector;
        return this;
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
     * Istituition type.
     *
     * @param istituitionType the istituition type
     * @return the sponsoring institution
     */
    public SponsoringInstitution istituitionType(IstituitionType istituitionType) {
        this.istituitionType = istituitionType;
        return this;
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
     * Street address.
     *
     * @param streetAddress the street address
     * @return the sponsoring institution
     */
    public SponsoringInstitution streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
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
     * Foundation date.
     *
     * @param foundationDate the foundation date
     * @return the sponsoring institution
     */
    public SponsoringInstitution foundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
        return this;
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
     * Vat number.
     *
     * @param vatNumber the vat number
     * @return the sponsoring institution
     */
    public SponsoringInstitution vatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
        return this;
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
     * Email.
     *
     * @param email the email
     * @return the sponsoring institution
     */
    public SponsoringInstitution email(String email) {
        this.email = email;
        return this;
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
     * Phone.
     *
     * @param phone the phone
     * @return the sponsoring institution
     */
    public SponsoringInstitution phone(String phone) {
        this.phone = phone;
        return this;
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
     * Mobile phone.
     *
     * @param mobilePhone the mobile phone
     * @return the sponsoring institution
     */
    public SponsoringInstitution mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
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
     * Country.
     *
     * @param country the country
     * @return the sponsoring institution
     */
    public SponsoringInstitution country(Country country) {
        this.country = country;
        return this;
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
     * Region.
     *
     * @param region the region
     * @return the sponsoring institution
     */
    public SponsoringInstitution region(String region) {
        this.region = region;
        return this;
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
     * Province.
     *
     * @param province the province
     * @return the sponsoring institution
     */
    public SponsoringInstitution province(String province) {
        this.province = province;
        return this;
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
     * City.
     *
     * @param city the city
     * @return the sponsoring institution
     */
    public SponsoringInstitution city(String city) {
        this.city = city;
        return this;
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
     * Cap.
     *
     * @param cap the cap
     * @return the sponsoring institution
     */
    public SponsoringInstitution cap(String cap) {
        this.cap = cap;
        return this;
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
     * Url site.
     *
     * @param urlSite the url site
     * @return the sponsoring institution
     */
    public SponsoringInstitution urlSite(String urlSite) {
        this.urlSite = urlSite;
        return this;
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
     * Enabled.
     *
     * @param enabled the enabled
     * @return the sponsoring institution
     */
    public SponsoringInstitution enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
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
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * User.
     *
     * @param user the user
     * @return the sponsoring institution
     */
    public SponsoringInstitution user(User user) {
        this.user = user;
        return this;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        SponsoringInstitution sponsoringInstitution = (SponsoringInstitution) o;
        if (sponsoringInstitution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sponsoringInstitution.getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SponsoringInstitution{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", istituitionName='" + getIstituitionName() + "'" +
            ", istituitionDescription='" + getIstituitionDescription() + "'" +
            ", istituitionSector='" + getIstituitionSector() + "'" +
            ", istituitionType='" + getIstituitionType() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", foundationDate='" + getFoundationDate() + "'" +
            ", vatNumber='" + getVatNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", country='" + getCountry() + "'" +
            ", region='" + getRegion() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", cap='" + getCap() + "'" +
            ", urlSite='" + getUrlSite() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
