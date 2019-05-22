package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

// TODO: Auto-generated Javadoc
/**
 * Criteria class for the AppliedJobIteration entity. This class is used in AppliedJobIterationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /applied-job-iterations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppliedJobIterationCriteria implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

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

    /** The iteration date. */
    private InstantFilter iterationDate;

    /** The iteration type. */
    private StringFilter iterationType;

    /** The applied job id. */
    private LongFilter appliedJobId;

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
     * Gets the iteration date.
     *
     * @return the iteration date
     */
    public InstantFilter getIterationDate() {
        return iterationDate;
    }

    /**
     * Sets the iteration date.
     *
     * @param iterationDate the new iteration date
     */
    public void setIterationDate(InstantFilter iterationDate) {
        this.iterationDate = iterationDate;
    }

    /**
     * Gets the iteration type.
     *
     * @return the iteration type
     */
    public StringFilter getIterationType() {
        return iterationType;
    }

    /**
     * Sets the iteration type.
     *
     * @param iterationType the new iteration type
     */
    public void setIterationType(StringFilter iterationType) {
        this.iterationType = iterationType;
    }

    /**
     * Gets the applied job id.
     *
     * @return the applied job id
     */
    public LongFilter getAppliedJobId() {
        return appliedJobId;
    }

    /**
     * Sets the applied job id.
     *
     * @param appliedJobId the new applied job id
     */
    public void setAppliedJobId(LongFilter appliedJobId) {
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
        final AppliedJobIterationCriteria that = (AppliedJobIterationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(iterationDate, that.iterationDate) &&
            Objects.equals(iterationType, that.iterationType) &&
            Objects.equals(appliedJobId, that.appliedJobId);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        lastModifiedBy,
        createdDate,
        lastModifiedDate,
        iterationDate,
        iterationType,
        appliedJobId
        );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AppliedJobIterationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (iterationDate != null ? "iterationDate=" + iterationDate + ", " : "") +
                (iterationType != null ? "iterationType=" + iterationType + ", " : "") +
                (appliedJobId != null ? "appliedJobId=" + appliedJobId + ", " : "") +
            "}";
    }

}
