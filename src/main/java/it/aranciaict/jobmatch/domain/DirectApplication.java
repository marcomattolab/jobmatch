package it.aranciaict.jobmatch.domain;


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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;

/**
 * Entity Applied Job (Candidatura).
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "direct_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DirectApplication extends AbstractAuditingEntity {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The appied job status. */
    @Enumerated(EnumType.STRING)
    @Column(name = "appied_job_status")
    private AppiedJobStatus appiedJobStatus;

    /** The company. */
    @ManyToOne
    @JsonIgnoreProperties("directApplications")
    private Company company;

    /** The candidate. */
    @ManyToOne
    @JsonIgnoreProperties("directApplications")
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
     * Gets the appied job status.
     *
     * @return the appied job status
     */
    public AppiedJobStatus getAppiedJobStatus() {
        return appiedJobStatus;
    }

    /**
     * Appied job status.
     *
     * @param appiedJobStatus the appied job status
     * @return the direct application
     */
    public DirectApplication appiedJobStatus(AppiedJobStatus appiedJobStatus) {
        this.appiedJobStatus = appiedJobStatus;
        return this;
    }

    /**
     * Sets the appied job status.
     *
     * @param appiedJobStatus the new appied job status
     */
    public void setAppiedJobStatus(AppiedJobStatus appiedJobStatus) {
        this.appiedJobStatus = appiedJobStatus;
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
     * @return the direct application
     */
    public DirectApplication company(Company company) {
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
     * @return the direct application
     */
    public DirectApplication candidate(Candidate candidate) {
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
        DirectApplication directApplication = (DirectApplication) o;
        if (directApplication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), directApplication.getId());
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
        return "DirectApplication{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", appiedJobStatus='" + getAppiedJobStatus() + "'" +
            "}";
    }
}
