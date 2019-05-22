package it.aranciaict.jobmatch.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.domain.enumeration.AppliedJobFeedback;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Applied Job (Candidatura)")
public class AppliedJobDTO implements Serializable {

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

    /** The applied job feedback. */
    private AppliedJobFeedback appliedJobFeedback;

    /** The feedback note. */
    private String feedbackNote;

    /** The appied job status. */
    private AppiedJobStatus appiedJobStatus;


    /** The candidate id. */
    private Long candidateId;

    /** The job offer id. */
    private Long jobOfferId;

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
     * Gets the applied job feedback.
     *
     * @return the applied job feedback
     */
    public AppliedJobFeedback getAppliedJobFeedback() {
        return appliedJobFeedback;
    }

    /**
     * Sets the applied job feedback.
     *
     * @param appliedJobFeedback the new applied job feedback
     */
    public void setAppliedJobFeedback(AppliedJobFeedback appliedJobFeedback) {
        this.appliedJobFeedback = appliedJobFeedback;
    }

    /**
     * Gets the feedback note.
     *
     * @return the feedback note
     */
    public String getFeedbackNote() {
        return feedbackNote;
    }

    /**
     * Sets the feedback note.
     *
     * @param feedbackNote the new feedback note
     */
    public void setFeedbackNote(String feedbackNote) {
        this.feedbackNote = feedbackNote;
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

    /**
     * Gets the job offer id.
     *
     * @return the job offer id
     */
    public Long getJobOfferId() {
        return jobOfferId;
    }

    /**
     * Sets the job offer id.
     *
     * @param jobOfferId the new job offer id
     */
    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
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

        AppliedJobDTO appliedJobDTO = (AppliedJobDTO) o;
        if (appliedJobDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appliedJobDTO.getId());
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
        return "AppliedJobDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", appliedJobFeedback='" + getAppliedJobFeedback() + "'" +
            ", feedbackNote='" + getFeedbackNote() + "'" +
            ", appiedJobStatus='" + getAppiedJobStatus() + "'" +
            ", candidate=" + getCandidateId() +
            ", jobOffer=" + getJobOfferId() +
            "}";
    }
}
