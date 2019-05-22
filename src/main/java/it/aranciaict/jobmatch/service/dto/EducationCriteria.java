package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.EducationType;

// TODO: Auto-generated Javadoc
/**
 * Criteria class for the Education entity. This class is used in EducationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /educations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@SuppressWarnings("serial")
public class EducationCriteria implements Serializable {
    
    /**
     * Class for filtering EducationType.
     */
    public static class EducationTypeFilter extends Filter<EducationType> {
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

    /** The school name. */
    private StringFilter schoolName;

    /** The field of study. */
    private StringFilter fieldOfStudy;

    /** The start date. */
    private LocalDateFilter startDate;

    /** The end date. */
    private LocalDateFilter endDate;

    /** The current. */
    private BooleanFilter current;

    /** The expires. */
    private BooleanFilter expires;

    /** The education type. */
    private EducationTypeFilter educationType;

    /** The candidate id. */
    private LongFilter candidateId;

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
     * Gets the school name.
     *
     * @return the school name
     */
    public StringFilter getSchoolName() {
        return schoolName;
    }

    /**
     * Sets the school name.
     *
     * @param schoolName the new school name
     */
    public void setSchoolName(StringFilter schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * Gets the field of study.
     *
     * @return the field of study
     */
    public StringFilter getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * Sets the field of study.
     *
     * @param fieldOfStudy the new field of study
     */
    public void setFieldOfStudy(StringFilter fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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
     * Gets the current.
     *
     * @return the current
     */
    public BooleanFilter getCurrent() {
        return current;
    }

    /**
     * Sets the current.
     *
     * @param current the new current
     */
    public void setCurrent(BooleanFilter current) {
        this.current = current;
    }

    /**
     * Gets the expires.
     *
     * @return the expires
     */
    public BooleanFilter getExpires() {
        return expires;
    }

    /**
     * Sets the expires.
     *
     * @param expires the new expires
     */
    public void setExpires(BooleanFilter expires) {
        this.expires = expires;
    }

    /**
     * Gets the education type.
     *
     * @return the education type
     */
    public EducationTypeFilter getEducationType() {
        return educationType;
    }

    /**
     * Sets the education type.
     *
     * @param educationType the new education type
     */
    public void setEducationType(EducationTypeFilter educationType) {
        this.educationType = educationType;
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
        final EducationCriteria that = (EducationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(schoolName, that.schoolName) &&
            Objects.equals(fieldOfStudy, that.fieldOfStudy) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(current, that.current) &&
            Objects.equals(expires, that.expires) &&
            Objects.equals(educationType, that.educationType) &&
            Objects.equals(candidateId, that.candidateId);
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
        schoolName,
        fieldOfStudy,
        startDate,
        endDate,
        current,
        expires,
        educationType,
        candidateId
        );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EducationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (schoolName != null ? "schoolName=" + schoolName + ", " : "") +
                (fieldOfStudy != null ? "fieldOfStudy=" + fieldOfStudy + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (current != null ? "current=" + current + ", " : "") +
                (expires != null ? "expires=" + expires + ", " : "") +
                (educationType != null ? "educationType=" + educationType + ", " : "") +
                (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            "}";
    }

}
