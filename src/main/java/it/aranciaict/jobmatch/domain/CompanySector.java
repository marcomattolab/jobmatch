package it.aranciaict.jobmatch.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CompanySector.
 */
@Entity
@Table(name = "company_sector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanySector extends AbstractAuditingEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The code. */
	@Column(name = "code")
	private String code;

	/** The description IT. */
	@Column(name = "description_IT")
	private String descriptionIT;

	/** The description EN. */
	@Column(name = "description_EN")
	private String descriptionEN;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove

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
	 * Code.
	 *
	 * @param code the code
	 * @return the company sector
	 */
	public CompanySector code(String code) {
		this.code = code;
		return this;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

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
	 * Description EN.
	 *
	 * @param descriptionEN the description EN
	 * @return the company sector
	 */
	public CompanySector descriptionEN(String descriptionEN) {
		this.descriptionEN = descriptionEN;
		return this;
	}

	
	/**
	 * Description IT.
	 *
	 * @param descriptionIT the description IT
	 * @return the company sector
	 */
	public CompanySector descriptionIT(String descriptionIT) {
		this.descriptionIT = descriptionIT;
		return this;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
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
		CompanySector companySector = (CompanySector) o;
		if (companySector.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), companySector.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanySector{" + "id=" + getId() + ", createdBy='" + getCreatedBy() + "'" + ", lastModifiedBy='"
				+ getLastModifiedBy() + "'" + ", createdDate='" + getCreatedDate() + "'" + ", lastModifiedDate='"
				+ getLastModifiedDate() + "'" + ", code='" + getCode() + "'" + ", descriptionIT='" + getDescriptionIT()
				+ "'" + ", descriptionEN='" + getDescriptionEN()
				+ "'"+ "}";
	}

}
