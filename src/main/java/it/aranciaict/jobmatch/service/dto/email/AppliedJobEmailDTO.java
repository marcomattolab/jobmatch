package it.aranciaict.jobmatch.service.dto.email;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Applied Job (Candidatura)")
public class AppliedJobEmailDTO extends AbstractEmailDTO implements Serializable {

    /** The id. */
    private Long id;

    /** The created date. */
    private Instant createdDate;

    /** The candidate id. */
    private Long candidateId;

    /** The last name. */
    @NotNull
    @Size(max = ValidationConstants.SIZE_50)
    private String lastName;

    /** The first name. */
    @NotNull
    @Size(max = ValidationConstants.SIZE_50)
    private String firstName;

    /** The job offer id. */
    private Long jobOfferId;

    /** The jobOfferTitle. */
    private String jobOfferTitle;

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


    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the jobOfferTitle
     */
    public String getJobOfferTitle() {
        return jobOfferTitle;
    }

    /**
     * @param jobOfferTitle the jobOfferTitle to set
     */
    public void setJobOfferTitle(String jobOfferTitle) {
        this.jobOfferTitle = jobOfferTitle;
    }
    
    @Override
    public String toString() {
        return "AppliedJobDTO{" + "id=" + getId() + ", createdDate='" + getCreatedDate() + "'" + ", candidate="
                + getCandidateId() + ", jobOffer=" + getJobOfferId() + "}";
    }
}
