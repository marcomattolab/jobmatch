/*
 * 
 */
package it.aranciaict.jobmatch.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.ProjectStatus;

// TODO: Auto-generated Javadoc
/**
 * A DTO for the Project entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Entity Project")
public class ProjectDTO implements Serializable {

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

    /** The title. */
    @NotNull
    private String title;

    /** The description. */
    @Lob
    private String description;

    /** The start date. */
    @NotNull
    private LocalDate startDate;

    /** The status. */
    private ProjectStatus status;

    /** The end date. */
    private LocalDate endDate;


    /** The company id. */
    private Long companyId;

    /** The sector id. */
    private Long sectorId;

    private boolean editAvailable;
    
    private boolean deleteAvailable;
    
    
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
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Gets the start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the company id.
     *
     * @return the company id
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets the sector id.
     *
     * @return the sector id
     */
    public Long getSectorId() {
        return sectorId;
    }

    /**
     * Sets the sector id.
     *
     * @param companySectorId the new sector id
     */
    public void setSectorId(Long companySectorId) {
        this.sectorId = companySectorId;
    }
    

    /**
     * Checks if is edits the available.
     *
     * @return true, if is edits the available
     */
    public boolean isEditAvailable() {
		return editAvailable;
	}

	/**
	 * Sets the edits the available.
	 *
	 * @param editAvailable the new edits the available
	 */
	public void setEditAvailable(boolean editAvailable) {
		this.editAvailable = editAvailable;
	}

	/**
	 * Checks if is delete available.
	 *
	 * @return true, if is delete available
	 */
	public boolean isDeleteAvailable() {
		return deleteAvailable;
	}

	/**
	 * Sets the delete available.
	 *
	 * @param deleteAvailable the new delete available
	 */
	public void setDeleteAvailable(boolean deleteAvailable) {
		this.deleteAvailable = deleteAvailable;
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

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (projectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectDTO.getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ProjectDTO [id=" + id + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", status=" + status + ", endDate="
				+ endDate + ", companyId=" + companyId + ", sectorId=" + sectorId + ", editAvailable=" + editAvailable
				+ ", deleteAvailable=" + deleteAvailable + "]";
	}

   
}
