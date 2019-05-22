package it.aranciaict.jobmatch.domain;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.ProjectStatus;

// TODO: Auto-generated Javadoc
/**
 * Entity Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The title. */
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    /** The description. */
    @Lob
    @Column(name = "description")
    private String description;

    /** The start date. */
    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** The status. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus status;

    /** The end date. */
    @Column(name = "end_date")
    private LocalDate endDate;

    /** The job offers. */
    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobOffer> jobOffers = new HashSet<>();
    
    /** The company. */
    @ManyToOne
    @JsonIgnoreProperties("projects")
    private Company company;

    /** The sector. */
    @ManyToOne
    @JsonIgnoreProperties("projects")
    private CompanySector sector;

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
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title.
     *
     * @param title the title
     * @return the project
     */
    public Project title(String title) {
        this.title = title;
        return this;
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
     * Description.
     *
     * @param description the description
     * @return the project
     */
    public Project description(String description) {
        this.description = description;
        return this;
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
     * Start date.
     *
     * @param startDate the start date
     * @return the project
     */
    public Project startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
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
     * Status.
     *
     * @param status the status
     * @return the project
     */
    public Project status(ProjectStatus status) {
        this.status = status;
        return this;
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
     * End date.
     *
     * @param endDate the end date
     * @return the project
     */
    public Project endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
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
     * Gets the job offers.
     *
     * @return the job offers
     */
    public Set<JobOffer> getJobOffers() {
        return jobOffers;
    }

    /**
     * Job offers.
     *
     * @param jobOffers the job offers
     * @return the project
     */
    public Project jobOffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
        return this;
    }

    /**
     * Adds the job offer.
     *
     * @param jobOffer the job offer
     * @return the project
     */
    public Project addJobOffer(JobOffer jobOffer) {
        this.jobOffers.add(jobOffer);
        jobOffer.setProject(this);
        return this;
    }

    /**
     * Removes the job offer.
     *
     * @param jobOffer the job offer
     * @return the project
     */
    public Project removeJobOffer(JobOffer jobOffer) {
        this.jobOffers.remove(jobOffer);
        jobOffer.setProject(null);
        return this;
    }

    /**
     * Sets the job offers.
     *
     * @param jobOffers the new job offers
     */
    public void setJobOffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Company.
     *
     * @param company the company
     * @return the project
     */
    public Project company(Company company) {
        this.company = company;
        return this;
    }

    /**
     * Sets the company.
     *
     * @param company the new company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Gets the sector.
     *
     * @return the sector
     */
    public CompanySector getSector() {
        return sector;
    }

    /**
     * Sector.
     *
     * @param companySector the company sector
     * @return the project
     */
    public Project sector(CompanySector companySector) {
        this.sector = companySector;
        return this;
    }

    /**
     * Sets the sector.
     *
     * @param companySector the new sector
     */
    public void setSector(CompanySector companySector) {
        this.sector = companySector;
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
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
        return "Project{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
