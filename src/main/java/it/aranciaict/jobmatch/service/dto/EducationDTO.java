package it.aranciaict.jobmatch.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.EducationType;

/**
 * A DTO for the Education entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Education")
public class EducationDTO implements Serializable {

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

    /** The school name. */
    @NotNull
    private String schoolName;

    /** The field of study. */
    @NotNull
    private String fieldOfStudy;

    /** The description. */
    @Lob
    private String description;

    /** The start date. */
    @NotNull
    private LocalDate startDate;

    /** The end date. */
    private LocalDate endDate;

    /** The current. */
    private Boolean current;

    /** The expires. */
    private Boolean expires;

    /** The education type. */
    private EducationType educationType;


    /** The candidate id. */
    private Long candidateId;

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
     * Gets the school name.
     *
     * @return the school name
     */
    public String getSchoolName() {
        return schoolName;
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
     * Sets the current.
     *
     * @param current the new current
     */
    public void setCurrent(Boolean current) {
        this.current = current;
    }

    /**
     * Checks if is expires.
     *
     * @return the boolean
     */
    public Boolean isExpires() {
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

    /**
     * Gets the candidate id.
     *
     * @return the candidate id
     */
    public Long getCandidateId() {
        return candidateId;
    }

    /**
     * Sets the candidate id.
     *
     * @param candidateId the new candidate id
     */
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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

        EducationDTO educationDTO = (EducationDTO) o;
        if (educationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), educationDTO.getId());
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
        return "EducationDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", schoolName='" + getSchoolName() + "'" +
            ", fieldOfStudy='" + getFieldOfStudy() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", current='" + isCurrent() + "'" +
            ", expires='" + isExpires() + "'" +
            ", educationType='" + getEducationType() + "'" +
            ", candidate=" + getCandidateId() +
            "}";
    }
}
