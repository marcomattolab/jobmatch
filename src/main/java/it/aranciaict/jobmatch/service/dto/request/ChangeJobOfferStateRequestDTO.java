package it.aranciaict.jobmatch.service.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
public class ChangeJobOfferStateRequestDTO implements Serializable {

	/** The job offer id. */
	@NotNull
	private Long jobOfferId;

	/** The status. */
	@NotNull
	private JobOfferStatus status;

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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public JobOfferStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(JobOfferStatus status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChangeJobOfferStateRequestDTO [" + (jobOfferId != null ? "jobOfferId=" + jobOfferId + ", " : "")
				+ (status != null ? "status=" + status : "") + "]";
	}

}
