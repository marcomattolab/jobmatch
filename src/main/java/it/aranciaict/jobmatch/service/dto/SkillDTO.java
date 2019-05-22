package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.ProficNumberOfYear;
import it.aranciaict.jobmatch.domain.enumeration.SkillLevelType;
import it.aranciaict.jobmatch.domain.enumeration.SkillType;

/**
 * A DTO for the Skill entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Candidate Skill")
public class SkillDTO implements Serializable {

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

	/** The level. */
	@NotNull
	private SkillLevelType level;

	/** The profic number of year. */
	private ProficNumberOfYear proficNumberOfYear;

	/** The skill tag id. */
	private Long skillTagId;

	/** The skill tag name. */
	private String skillTagName;

	/** The skill tag type. */
	private SkillType skillTagType;

	/** The candidate id. */
	private Long candidateId;

	/**
	 * check if SkillTag is valid
	 * @return is valid
	 */
	public boolean isSkillTagValid() {
		return skillTagId != null || !StringUtils.isBlank(skillTagName) && skillTagType != null;
	}

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
	 * Gets the level.
	 *
	 * @return the level
	 */
	public SkillLevelType getLevel() {
		return level;
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
	 * Sets the profic number of year.
	 *
	 * @param proficNumberOfYear the new profic number of year
	 */
	public void setProficNumberOfYear(ProficNumberOfYear proficNumberOfYear) {
		this.proficNumberOfYear = proficNumberOfYear;
	}

	/**
	 * Gets the skill tag id.
	 *
	 * @return the skill tag id
	 */
	public Long getSkillTagId() {
		return skillTagId;
	}

	/**
	 * Sets the skill tag id.
	 *
	 * @param skillTagId the new skill tag id
	 */
	public void setSkillTagId(Long skillTagId) {
		this.skillTagId = skillTagId;
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
	 * Gets the skill tag name.
	 *
	 * @return the skill tag name
	 */
	public String getSkillTagName() {
		return skillTagName;
	}

	/**
	 * Sets the skill tag name.
	 *
	 * @param skillTagName the new skill tag name
	 */
	public void setSkillTagName(String skillTagName) {
		this.skillTagName = skillTagName;
	}

		/**
	 * @return the skillTagType
	 */
	public SkillType getSkillTagType() {
		return skillTagType;
	}

	/**
	 * @param skillTagType the skillTagType to set
	 */
	public void setSkillTagType(SkillType skillTagType) {
		this.skillTagType = skillTagType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SkillDTO skillDTO = (SkillDTO) o;
		if (skillDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), skillDTO.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "SkillDTO [id=" + id + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", level=" + level
				+ ", proficNumberOfYear=" + proficNumberOfYear + ", skillTagId=" + skillTagId + ", skillTagName="
				+ skillTagName + ", candidateId=" + candidateId + "]";
	}

}
