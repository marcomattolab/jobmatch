package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.domain.enumeration.AppliedJobFeedback;

/**
 * Criteria class for the AppliedJob entity. This class is used in
 * AppliedJobResource to receive all the possible filtering options from the
 * Http GET request parameters. For example the following could be a valid
 * requests:
 * <code> /applied-jobs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@SuppressWarnings("serial")
public class AppliedJobCriteria implements Serializable {

	/**
	 * Class for filtering AppliedJobFeedback.
	 */
	public static class AppliedJobFeedbackFilter extends Filter<AppliedJobFeedback> {
	}

	/**
	 * Class for filtering AppiedJobStatus.
	 */
	public static class AppiedJobStatusFilter extends Filter<AppiedJobStatus> {
	}

	/** The id. */
	private LongFilter id;

	/** The created by. */
	private StringFilter createdBy;

	/** The last modified by. */
	private StringFilter lastModifiedBy;

	/** The created date. */
	private InstantFilter createdDate;

	/** The last modified date. */
	private InstantFilter lastModifiedDate;

	/** The applied job feedback. */
	private AppliedJobFeedbackFilter appliedJobFeedback;

	/** The feedback note. */
	private StringFilter feedbackNote;

	/** The appied job status. */
	private AppiedJobStatusFilter appliedJobStatus;

	/** The applied job iteration id. */
	private LongFilter appliedJobIterationId;

	/** The candidate id. */
	private LongFilter candidateId;

	/** The job offer id. */
	private LongFilter jobOfferId;
	
	/** The created by. */
	private StringFilter jobOffer;

	/** The company id. */
	private LongFilter companyId;
	
	/** The search mode. */
	private boolean searchMode = false;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public LongFilter getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(LongFilter id) {
		this.id = id;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public StringFilter getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(StringFilter createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public StringFilter getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy the new last modified by
	 */
	public void setLastModifiedBy(StringFilter lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public InstantFilter getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(InstantFilter createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the last modified date.
	 *
	 * @return the last modified date
	 */
	public InstantFilter getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * Sets the last modified date.
	 *
	 * @param lastModifiedDate the new last modified date
	 */
	public void setLastModifiedDate(InstantFilter lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Gets the applied job feedback.
	 *
	 * @return the applied job feedback
	 */
	public AppliedJobFeedbackFilter getAppliedJobFeedback() {
		return appliedJobFeedback;
	}

	/**
	 * Sets the applied job feedback.
	 *
	 * @param appliedJobFeedback the new applied job feedback
	 */
	public void setAppliedJobFeedback(AppliedJobFeedbackFilter appliedJobFeedback) {
		this.appliedJobFeedback = appliedJobFeedback;
	}

	/**
	 * Gets the feedback note.
	 *
	 * @return the feedback note
	 */
	public StringFilter getFeedbackNote() {
		return feedbackNote;
	}

	/**
	 * Sets the feedback note.
	 *
	 * @param feedbackNote the new feedback note
	 */
	public void setFeedbackNote(StringFilter feedbackNote) {
		this.feedbackNote = feedbackNote;
	}

	/**
	 * Gets the applied job status.
	 *
	 * @return the applied job status
	 */
	public AppiedJobStatusFilter getAppliedJobStatus() {
		return appliedJobStatus;
	}

	/**
	 * Sets the applied job status.
	 *
	 * @param appliedJobStatus the new applied job status
	 */
	public void setAppliedJobStatus(AppiedJobStatusFilter appliedJobStatus) {
		this.appliedJobStatus = appliedJobStatus;
	}

	/**
	 * Gets the applied job iteration id.
	 *
	 * @return the applied job iteration id
	 */
	public LongFilter getAppliedJobIterationId() {
		return appliedJobIterationId;
	}

	/**
	 * Sets the applied job iteration id.
	 *
	 * @param appliedJobIterationId the new applied job iteration id
	 */
	public void setAppliedJobIterationId(LongFilter appliedJobIterationId) {
		this.appliedJobIterationId = appliedJobIterationId;
	}

	/**
	 * Gets the candidate id.
	 *
	 * @return the candidate id
	 */
	public LongFilter getCandidateId() {
		return candidateId;
	}

	/**
	 * Sets the candidate id.
	 *
	 * @param candidateId the new candidate id
	 */
	public void setCandidateId(LongFilter candidateId) {
		this.candidateId = candidateId;
	}

	/**
	 * Gets the job offer id.
	 *
	 * @return the job offer id
	 */
	public LongFilter getJobOfferId() {
		return jobOfferId;
	}

	/**
	 * @return the companyId
	 */
	public LongFilter getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(LongFilter companyId) {
		this.companyId = companyId;
	}

	/**
	 * Sets the job offer id.
	 *
	 * @param jobOfferId the new job offer id
	 */
	public void setJobOfferId(LongFilter jobOfferId) {
		this.jobOfferId = jobOfferId;
	}


	/**
	 * Gets the job offer.
	 *
	 * @return the job offer
	 */
	public StringFilter getJobOffer() {
		return jobOffer;
	}

	/**
	 * Sets the job offer.
	 *
	 * @param jobOffer the new job offer
	 */
	public void setJobOffer(StringFilter jobOffer) {
		this.jobOffer = jobOffer;
	}
	
	/**
	 * Checks if is search mode.
	 *
	 * @return true, if is search mode
	 */
	public boolean isSearchMode() {
		return searchMode;
	}

	/**
	 * Sets the search mode.
	 *
	 * @param searchMode the new search mode
	 */
	public void setSearchMode(boolean searchMode) {
		this.searchMode = searchMode;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		final AppliedJobCriteria that = (AppliedJobCriteria) o;
		return Objects.equals(id, that.id) && Objects.equals(createdBy, that.createdBy)
				&& Objects.equals(lastModifiedBy, that.lastModifiedBy) && Objects.equals(createdDate, that.createdDate)
				&& Objects.equals(lastModifiedDate, that.lastModifiedDate)
				&& Objects.equals(appliedJobFeedback, that.appliedJobFeedback)
				&& Objects.equals(feedbackNote, that.feedbackNote)
				&& Objects.equals(appliedJobStatus, that.appliedJobStatus)
				&& Objects.equals(appliedJobIterationId, that.appliedJobIterationId)
				&& Objects.equals(candidateId, that.candidateId) && Objects.equals(jobOfferId, that.jobOfferId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, createdBy, lastModifiedBy, createdDate, lastModifiedDate, appliedJobFeedback,
				feedbackNote, appliedJobStatus, appliedJobIterationId, candidateId, jobOfferId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppliedJobCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", " : "")
				+ (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "")
				+ (appliedJobFeedback != null ? "appliedJobFeedback=" + appliedJobFeedback + ", " : "")
				+ (feedbackNote != null ? "feedbackNote=" + feedbackNote + ", " : "")
				+ (appliedJobStatus != null ? "appiedJobStatus=" + appliedJobStatus + ", " : "")
				+ (appliedJobIterationId != null ? "appliedJobIterationId=" + appliedJobIterationId + ", " : "")
				+ (candidateId != null ? "candidateId=" + candidateId + ", " : "")
				+ (jobOfferId != null ? "jobOfferId=" + jobOfferId + ", " : "") + "}";
	}


}
