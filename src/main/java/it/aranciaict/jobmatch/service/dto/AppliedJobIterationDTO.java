package it.aranciaict.jobmatch.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;

/**
 * A DTO for the AppliedJobIteration entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Applied Job Iteration  (Iterazione Candidato-Azienda)")
public class AppliedJobIterationDTO implements Serializable {

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

    /** The iteration date. */
    private Instant iterationDate;

    /** The iteration type. */
    private String iterationType;

    /** The iteration note. */
    @Lob
    private String iterationNote;


    /** The applied job id. */
    private Long appliedJobId;

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
     * Gets the iteration date.
     *
     * @return the iteration date
     */
    public Instant getIterationDate() {
        return iterationDate;
    }

    /**
     * Sets the iteration date.
     *
     * @param iterationDate the new iteration date
     */
    public void setIterationDate(Instant iterationDate) {
        this.iterationDate = iterationDate;
    }

    /**
     * Gets the iteration type.
     *
     * @return the iteration type
     */
    public String getIterationType() {
        return iterationType;
    }

    /**
     * Sets the iteration type.
     *
     * @param iterationType the new iteration type
     */
    public void setIterationType(String iterationType) {
        this.iterationType = iterationType;
    }

    /**
     * Gets the iteration note.
     *
     * @return the iteration note
     */
    public String getIterationNote() {
        return iterationNote;
    }

    /**
     * Sets the iteration note.
     *
     * @param iterationNote the new iteration note
     */
    public void setIterationNote(String iterationNote) {
        this.iterationNote = iterationNote;
    }

    /**
     * Gets the applied job id.
     *
     * @return the applied job id
     */
    public Long getAppliedJobId() {
        return appliedJobId;
    }

    /**
     * Sets the applied job id.
     *
     * @param appliedJobId the new applied job id
     */
    public void setAppliedJobId(Long appliedJobId) {
        this.appliedJobId = appliedJobId;
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

        AppliedJobIterationDTO appliedJobIterationDTO = (AppliedJobIterationDTO) o;
        if (appliedJobIterationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appliedJobIterationDTO.getId());
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
        return "AppliedJobIterationDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", iterationDate='" + getIterationDate() + "'" +
            ", iterationType='" + getIterationType() + "'" +
            ", iterationNote='" + getIterationNote() + "'" +
            ", appliedJob=" + getAppliedJobId() +
            "}";
    }
}
