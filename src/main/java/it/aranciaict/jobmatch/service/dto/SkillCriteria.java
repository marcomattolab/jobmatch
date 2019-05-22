package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.ProficNumberOfYear;
import it.aranciaict.jobmatch.domain.enumeration.SkillLevelType;

/**
 * Criteria class for the Skill entity. This class is used in SkillResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /skills?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@SuppressWarnings("serial")
public class SkillCriteria implements Serializable {
    
    /**
     * Class for filtering SkillLevelType.
     */
    public static class SkillLevelTypeFilter extends Filter<SkillLevelType> {
    }
    
    /**
     * Class for filtering ProficNumberOfYear.
     */
    public static class ProficNumberOfYearFilter extends Filter<ProficNumberOfYear> {
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

    /** The level. */
    private SkillLevelTypeFilter level;

    /** The profic number of year. */
    private ProficNumberOfYearFilter proficNumberOfYear;

    /** The skill tag id. */
    private LongFilter skillTagId;

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
     * Gets the level.
     *
     * @return the level
     */
    public SkillLevelTypeFilter getLevel() {
        return level;
    }

    /**
     * Sets the level.
     *
     * @param level the new level
     */
    public void setLevel(SkillLevelTypeFilter level) {
        this.level = level;
    }

    /**
     * Gets the profic number of year.
     *
     * @return the profic number of year
     */
    public ProficNumberOfYearFilter getProficNumberOfYear() {
        return proficNumberOfYear;
    }

    /**
     * Sets the profic number of year.
     *
     * @param proficNumberOfYear the new profic number of year
     */
    public void setProficNumberOfYear(ProficNumberOfYearFilter proficNumberOfYear) {
        this.proficNumberOfYear = proficNumberOfYear;
    }

    /**
     * Gets the skill tag id.
     *
     * @return the skill tag id
     */
    public LongFilter getSkillTagId() {
        return skillTagId;
    }

    /**
     * Sets the skill tag id.
     *
     * @param skillTagId the new skill tag id
     */
    public void setSkillTagId(LongFilter skillTagId) {
        this.skillTagId = skillTagId;
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
        final SkillCriteria that = (SkillCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(level, that.level) &&
            Objects.equals(proficNumberOfYear, that.proficNumberOfYear) &&
            Objects.equals(skillTagId, that.skillTagId) &&
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
        level,
        proficNumberOfYear,
        skillTagId,
        candidateId
        );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (proficNumberOfYear != null ? "proficNumberOfYear=" + proficNumberOfYear + ", " : "") +
                (skillTagId != null ? "skillTagId=" + skillTagId + ", " : "") +
                (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            "}";
    }

}
