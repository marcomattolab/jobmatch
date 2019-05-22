/*
 * 
 */
package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.ProjectStatus;

/**
 * Criteria class for the Project entity. This class is used in ProjectResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /projects?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@SuppressWarnings("serial")
public class ProjectCriteria implements Serializable {
    
    /**
     * Class for filtering ProjectStatus.
     */
    public static class ProjectStatusFilter extends Filter<ProjectStatus> {
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

    /** The title. */
    private StringFilter title;

    /** The start date. */
    private LocalDateFilter startDate;

    /** The status. */
    private ProjectStatusFilter status;

    /** The end date. */
    private LocalDateFilter endDate;

    /** The job offer id. */
    private LongFilter jobOfferId;

    /** The company id. */
    private LongFilter companyId;

    /** The sector id. */
    private LongFilter sectorId;

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
     * Gets the title.
     *
     * @return the title
     */
    public StringFilter getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(StringFilter title) {
        this.title = title;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public LocalDateFilter getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public ProjectStatusFilter getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(ProjectStatusFilter status) {
        this.status = status;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public LocalDateFilter getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
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
     * Sets the job offer id.
     *
     * @param jobOfferId the new job offer id
     */
    public void setJobOfferId(LongFilter jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    /**
     * Gets the company id.
     *
     * @return the company id
     */
    public LongFilter getCompanyId() {
        return companyId;
    }

    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets the sector id.
     *
     * @return the sector id
     */
    public LongFilter getSectorId() {
        return sectorId;
    }

    /**
     * Sets the sector id.
     *
     * @param sectorId the new sector id
     */
    public void setSectorId(LongFilter sectorId) {
        this.sectorId = sectorId;
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
        final ProjectCriteria that = (ProjectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(title, that.title) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(jobOfferId, that.jobOfferId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(sectorId, that.sectorId);
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
        title,
        startDate,
        status,
        endDate,
        jobOfferId,
        companyId,
        sectorId
        );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ProjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (jobOfferId != null ? "jobOfferId=" + jobOfferId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (sectorId != null ? "sectorId=" + sectorId + ", " : "") +
            "}";
    }

}
