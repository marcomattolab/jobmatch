package it.aranciaict.jobmatch.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.ProficNumberOfYear;
import it.aranciaict.jobmatch.domain.enumeration.SkillLevelType;

/**
 * Candidate Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skill extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The level. */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level", nullable = true)
    private SkillLevelType level;

    /** The profic number of year. */
    @Enumerated(EnumType.STRING)
    @Column(name = "profic_number_of_year")
    private ProficNumberOfYear proficNumberOfYear;

    /** The skill tag. */
    @ManyToOne
    @JsonIgnoreProperties("skills")
    private SkillTag skillTag;

    /** The candidate. */
    @ManyToOne
    @JsonIgnoreProperties("skills")
    private Candidate candidate;

    /**
     * Gets the id.
     *
     * @return the id
     */
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
     * Gets the level.
     *
     * @return the level
     */
    public SkillLevelType getLevel() {
        return level;
    }

    /**
     * Level.
     *
     * @param level the level
     * @return the skill
     */
    public Skill level(SkillLevelType level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the level.
     *
     * @param level the new level
     */
    public void setLevel(SkillLevelType level) {
        this.level = level;
    }

    /**
     * Gets the profic number of year.
     *
     * @return the profic number of year
     */
    public ProficNumberOfYear getProficNumberOfYear() {
        return proficNumberOfYear;
    }

    /**
     * Profic number of year.
     *
     * @param proficNumberOfYear the profic number of year
     * @return the skill
     */
    public Skill proficNumberOfYear(ProficNumberOfYear proficNumberOfYear) {
        this.proficNumberOfYear = proficNumberOfYear;
        return this;
    }

    /**
     * Sets the profic number of year.
     *
     * @param proficNumberOfYear the new profic number of year
     */
    public void setProficNumberOfYear(ProficNumberOfYear proficNumberOfYear) {
        this.proficNumberOfYear = proficNumberOfYear;
    }

    /**
     * Gets the skill tag.
     *
     * @return the skill tag
     */
    public SkillTag getSkillTag() {
        return skillTag;
    }

    /**
     * Skill tag.
     *
     * @param skillTag the skill tag
     * @return the skill
     */
    public Skill skillTag(SkillTag skillTag) {
        this.skillTag = skillTag;
        return this;
    }

    /**
     * Sets the skill tag.
     *
     * @param skillTag the new skill tag
     */
    public void setSkillTag(SkillTag skillTag) {
        this.skillTag = skillTag;
    }

    /**
     * Gets the candidate.
     *
     * @return the candidate
     */
    public Candidate getCandidate() {
        return candidate;
    }

    /**
     * Candidate.
     *
     * @param candidate the candidate
     * @return the skill
     */
    public Skill candidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    /**
     * Sets the candidate.
     *
     * @param candidate the new candidate
     */
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        Skill skill = (Skill) o;
        if (skill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skill.getId());
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
        return "Skill{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", level='" + getLevel() + "'" +
            ", proficNumberOfYear='" + getProficNumberOfYear() + "'" +
            "}";
    }
}
