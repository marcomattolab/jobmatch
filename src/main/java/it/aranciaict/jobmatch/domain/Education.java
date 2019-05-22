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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.EducationType;

/**
 * Entity Education.
 */
@Entity
@Table(name = "education")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Education extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The school name. */
    @Column(name = "school_name", nullable = false)
    private String schoolName;


    /** The field of study. */
    @Column(name = "field_of_study", nullable = false)
    private String fieldOfStudy;

    /** The description. */
    @Lob
    @Column(name = "description")
    private String description;

    /** The start date. */
    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** The end date. */
    @Column(name = "end_date")
    private LocalDate endDate;

    /** The current. */
    @Column(name = "current_education")
    private Boolean current;

    /** If Certification expires. */
    @Column(name = "expires")
    private Boolean expires;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_type")
    private EducationType educationType;
    
    /** The candidate. */
    @ManyToOne
    @JsonIgnoreProperties("educations")
    private Candidate candidate;

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
     * Gets the school name.
     *
     * @return the school name
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * School name.
     *
     * @param schoolName the school name
     * @return the education
     */
    public Education schoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }

    /**
     * Sets the school name.
     *
     * @param schoolName the new school name
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * Gets the field of study.
     *
     * @return the field of study
     */
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * Field of study.
     *
     * @param fieldOfStudy the field of study
     * @return the education
     */
    public Education fieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        return this;
    }

    /**
     * Sets the field of study.
     *
     * @param fieldOfStudy the new field of study
     */
    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description.
     *
     * @param description the description
     * @return the education
     */
    public Education description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Start date.
     *
     * @param startDate the start date
     * @return the education
     */
    public Education startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * End date.
     *
     * @param endDate the end date
     * @return the education
     */
    public Education endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Checks if is current.
     *
     * @return the boolean
     */
    public Boolean isCurrent() {
        return current;
    }

    /**
     * Current.
     *
     * @param current the current
     * @return the education
     */
    public Education current(Boolean current) {
        this.current = current;
        return this;
    }

    /**
     * Sets the current.
     *
     * @param current the new current
     */
    public void setCurrent(Boolean current) {
        this.current = current;
    }

    /**
     * Gets the candidate.
     *
     * @return the candidate
     */
    public Candidate getCandidate() {
        return candidate;
    }

    /**
     * Candidate.
     *
     * @param candidate the candidate
     * @return the education
     */
    public Education candidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    /**
     * Sets the candidate.
     *
     * @param candidate the new candidate
     */
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    /**
     * Gets the expires.
     *
     * @return the expires
     */
    public Boolean getExpires() {
		return expires;
	}

	/**
	 * Sets the expires.
	 *
	 * @param expires the new expires
	 */
	public void setExpires(Boolean expires) {
		this.expires = expires;
	}

	/**
	 * Gets the education type.
	 *
	 * @return the education type
	 */
	public EducationType getEducationType() {
		return educationType;
	}

	/**
	 * Sets the education type.
	 *
	 * @param educationType the new education type
	 */
	public void setEducationType(EducationType educationType) {
		this.educationType = educationType;
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
        Education education = (Education) o;
        if (education.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), education.getId());
    }

  
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "Education [id=" + id + ", schoolName=" + schoolName + ", fieldOfStudy=" + fieldOfStudy
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", current="
				+ current + ", expires=" + expires + ", educationType=" + educationType + ", candidate=" + candidate
				+ "]";
	}

   
}
