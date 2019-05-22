package it.aranciaict.jobmatch.service.dto.request;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
public class ApplyToJobOfferRequestDTO implements Serializable {
   
	
    /** The candidate id. */
	@NotNull
    private Long candidateId;

    /** The job offer id. */
	@NotNull
    private Long jobOfferId;
    
   
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppliedJobRequestDTO [candidateId=" + candidateId + ", jobOfferId=" + jobOfferId + "]";
	}


   
}
