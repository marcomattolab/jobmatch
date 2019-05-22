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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.GenderType;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidate extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The last name. */
    @NotNull
    @Size(max = ValidationConstants.SIZE_50)
    @Column(name = "last_name", length = ValidationConstants.SIZE_50, nullable = false)
    private String lastName;

    /** The first name. */
    @NotNull
    @Size(max = ValidationConstants.SIZE_50)
    @Column(name = "first_name", length = ValidationConstants.SIZE_50, nullable = false)
    private String firstName;

    /** The gender. */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    /** The birthday. */
    @Column(name = "birthday")
    private LocalDate birthday;

    /** The street address. */
    @Column(name = "street_address")
    private String streetAddress;

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
    @Column(name = "cap")
    private String cap;

    /** The note. */
    @Lob
    @Column(name = "note")
    private String note;
    
    /** The note EN. */
    @Lob
    @Column(name = "note_en")
    private String noteEN;

    /** The enabled. */
    @Column(name = "enabled")
    private Boolean enabled;

    /** The user. */
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    /** The documents. */
    @OneToMany(mappedBy = "candidate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Document> documents = new HashSet<>();
    
    /** The job experiences. */
    @OneToMany(mappedBy = "candidate")
    @OrderBy("startDate desc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobExperience> jobExperiences = new HashSet<>();
    
    /** The educations. */
    @OneToMany(mappedBy = "candidate")
    @OrderBy("startDate desc")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Education> educations = new HashSet<>();
    
    /** The skills. */
    @OneToMany(mappedBy = "candidate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Skill> skills = new HashSet<>();
    
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
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Last name.
     *
     * @param lastName the last name
     * @return the candidate
     */
    public Candidate lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * First name.
     *
     * @param firstName the first name
     * @return the candidate
     */
    public Candidate firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * Gender.
     *
     * @param gender the gender
     * @return the candidate
     */
    public Candidate gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    /**
     * Gets the birthday.
     *
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Birthday.
     *
     * @param birthday the birthday
     * @return the candidate
     */
    public Candidate birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday the new birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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
     * @return the candidate
     */
    public Candidate streetAddress(String streetAddress) {
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
     * @return the candidate
     */
    public Candidate email(String email) {
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
     * @return the candidate
     */
    public Candidate phone(String phone) {
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
     * @return the candidate
     */
    public Candidate mobilePhone(String mobilePhone) {
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
     * @return the candidate
     */
    public Candidate country(Country country) {
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
     * @return the candidate
     */
    public Candidate region(String region) {
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
     * @return the candidate
     */
    public Candidate province(String province) {
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
     * @return the candidate
     */
    public Candidate city(String city) {
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
     * @return the candidate
     */
    public Candidate cap(String cap) {
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
     * Gets the note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Note.
     *
     * @param note the note
     * @return the candidate
     */
    public Candidate note(String note) {
        this.note = note;
        return this;
    }

    /**
     * Sets the note.
     *
     * @param note the new note
     */
    public void setNote(String note) {
        this.note = note;
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
     * @return the candidate
     */
    public Candidate enabled(Boolean enabled) {
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
     * @return the candidate
     */
    public Candidate user(User user) {
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
    
    /**
     * Associa user.
     *
     * @param user the user
     */
    public void associaUser(User user) {
    	setUser(user);
    	setLastName(user.getLastName());
    	setFirstName(user.getFirstName());
    	setEmail(user.getEmail());
    }

    /**
     * Gets the documents.
     *
     * @return the documents
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * Documents.
     *
     * @param documents the documents
     * @return the candidate
     */
    public Candidate documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    /**
     * Adds the document.
     *
     * @param document the document
     * @return the candidate
     */
    public Candidate addDocument(Document document) {
        this.documents.add(document);
        document.setCandidate(this);
        return this;
    }

    /**
     * Removes the document.
     *
     * @param document the document
     * @return the candidate
     */
    public Candidate removeDocument(Document document) {
        this.documents.remove(document);
        document.setCandidate(null);
        return this;
    }

    /**
     * Sets the documents.
     *
     * @param documents the new documents
     */
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    /**
     * Gets the job experiences.
     *
     * @return the job experiences
     */
    public Set<JobExperience> getJobExperiences() {
        return jobExperiences;
    }

    /**
     * Job experiences.
     *
     * @param jobExperiences the job experiences
     * @return the candidate
     */
    public Candidate jobExperiences(Set<JobExperience> jobExperiences) {
        this.jobExperiences = jobExperiences;
        return this;
    }

    /**
     * Adds the job experience.
     *
     * @param jobExperience the job experience
     * @return the candidate
     */
    public Candidate addJobExperience(JobExperience jobExperience) {
        this.jobExperiences.add(jobExperience);
        jobExperience.setCandidate(this);
        return this;
    }

    /**
     * Removes the job experience.
     *
     * @param jobExperience the job experience
     * @return the candidate
     */
    public Candidate removeJobExperience(JobExperience jobExperience) {
        this.jobExperiences.remove(jobExperience);
        jobExperience.setCandidate(null);
        return this;
    }

    /**
     * Sets the job experiences.
     *
     * @param jobExperiences the new job experiences
     */
    public void setJobExperiences(Set<JobExperience> jobExperiences) {
        this.jobExperiences = jobExperiences;
    }

    /**
     * Gets the educations.
     *
     * @return the educations
     */
    public Set<Education> getEducations() {
        return educations;
    }

    /**
     * Educations.
     *
     * @param educations the educations
     * @return the candidate
     */
    public Candidate educations(Set<Education> educations) {
        this.educations = educations;
        return this;
    }

    /**
     * Adds the education.
     *
     * @param education the education
     * @return the candidate
     */
    public Candidate addEducation(Education education) {
        this.educations.add(education);
        education.setCandidate(this);
        return this;
    }

    /**
     * Removes the education.
     *
     * @param education the education
     * @return the candidate
     */
    public Candidate removeEducation(Education education) {
        this.educations.remove(education);
        education.setCandidate(null);
        return this;
    }

    /**
     * Sets the educations.
     *
     * @param educations the new educations
     */
    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    /**
     * Gets the skills.
     *
     * @return the skills
     */
    public Set<Skill> getSkills() {
        return skills;
    }

    /**
     * Skills.
     *
     * @param skills the skills
     * @return the candidate
     */
    public Candidate skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    /**
     * Adds the skill.
     *
     * @param skill the skill
     * @return the candidate
     */
    public Candidate addSkill(Skill skill) {
        this.skills.add(skill);
        skill.setCandidate(this);
        return this;
    }

    /**
     * Removes the skill.
     *
     * @param skill the skill
     * @return the candidate
     */
    public Candidate removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.setCandidate(null);
        return this;
    }

    /**
     * Sets the skills.
     *
     * @param skills the new skills
     */
    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
    
    /**
     * Gets the note EN.
     *
     * @return the note EN
     */
    public String getNoteEN() {
		return noteEN;
	}

	/**
	 * Sets the note EN.
	 *
	 * @param noteEN the new note EN
	 */
	public void setNoteEN(String noteEN) {
		this.noteEN = noteEN;
	}

	/**
     * Gets the current job experience.
     *
     * @return the current job experience
     */
    public JobExperience getCurrentJobExperience() {
    	JobExperience current = null;
    	if(!CollectionUtils.isEmpty(jobExperiences)) {
    		for (JobExperience job : jobExperiences) {
				if(job.isCurrent()) {
					current = job;
					break;
				}
			}
    	}
    	return current;
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
        Candidate candidate = (Candidate) o;
        if (candidate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidate.getId());
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
        return "Candidate{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", country='" + getCountry() + "'" +
            ", region='" + getRegion() + "'" +
            ", province='" + getProvince() + "'" +
            ", city='" + getCity() + "'" +
            ", cap='" + getCap() + "'" +
            ", enabled='" + isEnabled() + "'" +
            "}";
    }
}
