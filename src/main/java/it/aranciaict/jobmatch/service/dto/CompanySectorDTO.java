package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.Size;

import it.aranciaict.jobmatch.domain.constants.ValidationConstants;


/**
 * A DTO for the CompanySector entity.
 */
@SuppressWarnings("serial")
public class CompanySectorDTO implements Serializable{

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

	/** The code. */
	private String code;

	/** The description IT. */
	private String descriptionIT;

	/** The description EN. */
	private String descriptionEN;

	/** The description EN. */
	private String description;

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
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description IT.
	 *
	 * @return the description IT
	 */
	public String getDescriptionIT() {
		return descriptionIT;
	}

	/**
	 * Sets the description IT.
	 *
	 * @param descriptionIT the new description IT
	 */
	public void setDescriptionIT(String descriptionIT) {
		this.descriptionIT = descriptionIT;
	}

	/**
	 * Gets the description EN.
	 *
	 * @return the description EN
	 */
	public String getDescriptionEN() {
		return descriptionEN;
	}

	/**
	 * Sets the description EN.
	 *
	 * @param descriptionEN the new description EN
	 */
	public void setDescriptionEN(String descriptionEN) {
		this.descriptionEN = descriptionEN;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
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

		CompanySectorDTO companySectorDTO = (CompanySectorDTO) o;
		if (companySectorDTO.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), companySectorDTO.getId());
	}

	/**
	 * Hash code.
	 *
	 * @return the int
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
		return "CompanySectorDTO [id=" + id + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", code=" + code
				+ ", descriptionIT=" + descriptionIT + ", descriptionEN=" + descriptionEN + ", description="
				+ description + "]";
	}

}
