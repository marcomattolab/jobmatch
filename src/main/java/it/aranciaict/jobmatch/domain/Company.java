package it.aranciaict.jobmatch.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company extends AbstractAuditingEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The company name. */
	@NotNull
	@Size(max = ValidationConstants.SIZE_255)
	@Column(name = "company_name", length = ValidationConstants.SIZE_255, nullable = false)
	private String companyName;

	/** The company description. */
	@Lob
	@Column(name = "company_description")
	private String companyDescription;

	/** The company size. */
	@Enumerated(EnumType.STRING)
	@Column(name = "company_size")
	private CompanySizeType companySize;

	/** The company type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "company_type")
	private CompanyType companyType;

	/** The number of employee. */
	@Enumerated(EnumType.STRING)
	@Column(name = "number_of_employee")
	private NumberOfEmployees numberOfEmployee;

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

	@OneToMany(mappedBy = "company")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<CompanySkill> skills = new HashSet<>();

	/** The principal sector. */
	@ManyToOne
	@JsonIgnoreProperties("companies")
	private CompanySector sector;

	@ManyToOne
	@JsonIgnoreProperties("companies")
	private SponsoringInstitution sponsoringInstitution;

	/**
	 * The secondary sectors.
	 * 
	 * @ManyToMany
	 * @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	 * @JoinTable(name = "comp_sector", joinColumns = @JoinColumn(name =
	 *                 "company_id", referencedColumnName = "id"),
	 *                 inverseJoinColumns = @JoinColumn(name = "sector_id",
	 *                 referencedColumnName = "id")) private Set<CompanySector>
	 *                 sectors = new HashSet<>();
	 */

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove

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
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Company name.
	 *
	 * @param companyName the company name
	 * @return the company
	 */
	public Company companyName(String companyName) {
		this.companyName = companyName;
		return this;
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
	 * Company description.
	 *
	 * @param companyDescription the company description
	 * @return the company
	 */
	public Company companyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
		return this;
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
	 * Company size.
	 *
	 * @param companySize the company size
	 * @return the company
	 */
	public Company companySize(CompanySizeType companySize) {
		this.companySize = companySize;
		return this;
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
	 * Company type.
	 *
	 * @param companyType the company type
	 * @return the company
	 */
	public Company companyType(CompanyType companyType) {
		this.companyType = companyType;
		return this;
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
	 * Number of employee.
	 *
	 * @param numberOfEmployee the number of employee
	 * @return the company
	 */
	public Company numberOfEmployee(NumberOfEmployees numberOfEmployee) {
		this.numberOfEmployee = numberOfEmployee;
		return this;
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
	 * Street address.
	 *
	 * @param streetAddress the street address
	 * @return the company
	 */
	public Company streetAddress(String streetAddress) {
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
	 * @return the company
	 */
	public Company foundationDate(LocalDate foundationDate) {
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
	 * @return the company
	 */
	public Company vatNumber(String vatNumber) {
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
	 * @return the company
	 */
	public Company email(String email) {
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
	 * @return the company
	 */
	public Company phone(String phone) {
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
	 * @return the company
	 */
	public Company mobilePhone(String mobilePhone) {
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
	 * @return the company
	 */
	public Company country(Country country) {
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
	 * @return the company
	 */
	public Company region(String region) {
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
	 * @return the company
	 */
	public Company province(String province) {
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
	 * @return the company
	 */
	public Company city(String city) {
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
	 * @return the company
	 */
	public Company cap(String cap) {
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
	 * @return the company
	 */
	public Company urlSite(String urlSite) {
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
	 * @return the company
	 */
	public Company enabled(Boolean enabled) {
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
	 * @return the company
	 */
	public Company user(User user) {
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

//    /**
//     * Gets the sectors.
//     *
//     * @return the sectors
//     */
//    public Set<CompanySector> getSectors() {
//        return sectors;
//    }
//
//    /**
//     * Sectors.
//     *
//     * @param companySectors the company sectors
//     * @return the company
//     */
//    public Company sectors(Set<CompanySector> companySectors) {
//        this.sectors = companySectors;
//        return this;
//    }
//
//    /**
//     * Adds the sector.
//     *
//     * @param companySector the company sector
//     * @return the company
//     */
//    public Company addSector(CompanySector companySector) {
//        this.sectors.add(companySector);
////        companySector.getCompanies().add(this);
//        return this;
//    }
//
//    /**
//     * Removes the sector.
//     *
//     * @param companySector the company sector
//     * @return the company
//     */
//    public Company removeSector(CompanySector companySector) {
//        this.sectors.remove(companySector);
////        companySector.getCompanies().remove(this);
//        return this;
//    }
//
//    /**
//     * Sets the sectors.
//     *
//     * @param companySectors the new sectors
//     */
//    public void setSectors(Set<CompanySector> companySectors) {
//        this.sectors = companySectors;
//    }

	/**
	 * Gets the sector.
	 *
	 * @return the sector
	 */
	public CompanySector getSector() {
		return sector;
	}

	/**
	 * Sets the sector.
	 *
	 * @param sector the new sector
	 */
	public void setSector(CompanySector sector) {
		this.sector = sector;
	}

	/**
	 * Gets the skills.
	 *
	 * @return the skills
	 */
	public Set<CompanySkill> getSkills() {
		return skills;
	}

	/**
	 * Sets the skills.
	 *
	 * @param skills the new skills
	 */
	public void setSkills(Set<CompanySkill> skills) {
		this.skills = skills;
	}

	/**
	 * Gets the sponsoring institution.
	 *
	 * @return the sponsoring institution
	 */
	public SponsoringInstitution getSponsoringInstitution() {
		return sponsoringInstitution;
	}

	/**
	 * Sets the sponsoring institution.
	 *
	 * @param sponsoringInstitution the new sponsoring institution
	 */
	public void setSponsoringInstitution(SponsoringInstitution sponsoringInstitution) {
		this.sponsoringInstitution = sponsoringInstitution;
	}

	/**
	 * Checks if is sponsoring institution.
	 *
	 * @return true, if is sponsoring institution
	 */
	public boolean isSponsoringInstitution() {
		return this.sponsoringInstitution != null;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Company company = (Company) o;
		if (company.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), company.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Company{" + "id=" + getId() + ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", lastModifiedDate='"
				+ getLastModifiedDate() + "'" + ", companyName='" + getCompanyName() + "'" + ", companyDescription='"
				+ getCompanyDescription() + "'" + ", companySize='" + getCompanySize() + "'" + ", companyType='"
				+ getCompanyType() + "'" + ", numberOfEmployee='" + getNumberOfEmployee() + "'" + ", streetAddress='"
				+ getStreetAddress() + "'" + ", foundationDate='" + getFoundationDate() + "'" + ", vatNumber='"
				+ getVatNumber() + "'" + ", email='" + getEmail() + "'" + ", phone='" + getPhone() + "'"
				+ ", mobilePhone='" + getMobilePhone() + "'" + ", country='" + getCountry() + "'" + ", region='"
				+ getRegion() + "'" + ", province='" + getProvince() + "'" + ", city='" + getCity() + "'" + ", cap='"
				+ getCap() + "'" + ", urlSite='" + getUrlSite() + "'" + ", enabled='" + isEnabled() + "'" + "}";
	}
}
