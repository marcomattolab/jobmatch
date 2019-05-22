package it.aranciaict.jobmatch.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;

/**
 * A DTO for the DirectApplication entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Applied Job (Candidatura)")
public class DirectApplicationDTO implements Serializable {

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

    /** The appied job status. */
    private AppiedJobStatus appiedJobStatus;

    /** The company id. */
    private Long companyId;

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
     * Gets the appied job status.
     *
     * @return the appied job status
     */
    public AppiedJobStatus getAppiedJobStatus() {
        return appiedJobStatus;
    }

    /**
     * Sets the appied job status.
     *
     * @param appiedJobStatus the new appied job status
     */
    public void setAppiedJobStatus(AppiedJobStatus appiedJobStatus) {
        this.appiedJobStatus = appiedJobStatus;
    }

    /**
     * Gets the company id.
     *
     * @return the company id
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

        DirectApplicationDTO directApplicationDTO = (DirectApplicationDTO) o;
        if (directApplicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), directApplicationDTO.getId());
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
        return "DirectApplicationDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", appiedJobStatus='" + getAppiedJobStatus() + "'" +
            ", company=" + getCompanyId() +
            ", candidate=" + getCandidateId() +
            "}";
    }
}
