package it.aranciaict.jobmatch.domain;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// TODO: Auto-generated Javadoc
/**
 * Entity Applied Job Iteration  (Iterazione Candidato-Azienda).
 */
@Entity
@Table(name = "applied_job_iteration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppliedJobIteration extends AbstractAuditingEntity  implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The iteration date. */
    @Column(name = "iteration_date")
    private Instant iterationDate;

    /** The iteration type. */
    @Column(name = "iteration_type")
    private String iterationType;

    /** The iteration note. */
    @Lob
    @Column(name = "iteration_note")
    private String iterationNote;

    /** The applied job. */
    @ManyToOne
    @JsonIgnoreProperties("appliedJobIterations")
    private AppliedJob appliedJob;

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
     * Gets the iteration date.
     *
     * @return the iteration date
     */
    public Instant getIterationDate() {
        return iterationDate;
    }

    /**
     * Iteration date.
     *
     * @param iterationDate the iteration date
     * @return the applied job iteration
     */
    public AppliedJobIteration iterationDate(Instant iterationDate) {
        this.iterationDate = iterationDate;
        return this;
    }

    /**
     * Sets the iteration date.
     *
     * @param iterationDate the new iteration date
     */
    public void setIterationDate(Instant iterationDate) {
        this.iterationDate = iterationDate;
    }

    /**
     * Gets the iteration type.
     *
     * @return the iteration type
     */
    public String getIterationType() {
        return iterationType;
    }

    /**
     * Iteration type.
     *
     * @param iterationType the iteration type
     * @return the applied job iteration
     */
    public AppliedJobIteration iterationType(String iterationType) {
        this.iterationType = iterationType;
        return this;
    }

    /**
     * Sets the iteration type.
     *
     * @param iterationType the new iteration type
     */
    public void setIterationType(String iterationType) {
        this.iterationType = iterationType;
    }

    /**
     * Gets the iteration note.
     *
     * @return the iteration note
     */
    public String getIterationNote() {
        return iterationNote;
    }

    /**
     * Iteration note.
     *
     * @param iterationNote the iteration note
     * @return the applied job iteration
     */
    public AppliedJobIteration iterationNote(String iterationNote) {
        this.iterationNote = iterationNote;
        return this;
    }

    /**
     * Sets the iteration note.
     *
     * @param iterationNote the new iteration note
     */
    public void setIterationNote(String iterationNote) {
        this.iterationNote = iterationNote;
    }

    /**
     * Gets the applied job.
     *
     * @return the applied job
     */
    public AppliedJob getAppliedJob() {
        return appliedJob;
    }

    /**
     * Applied job.
     *
     * @param appliedJob the applied job
     * @return the applied job iteration
     */
    public AppliedJobIteration appliedJob(AppliedJob appliedJob) {
        this.appliedJob = appliedJob;
        return this;
    }

    /**
     * Sets the applied job.
     *
     * @param appliedJob the new applied job
     */
    public void setAppliedJob(AppliedJob appliedJob) {
        this.appliedJob = appliedJob;
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
        AppliedJobIteration appliedJobIteration = (AppliedJobIteration) o;
        if (appliedJobIteration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appliedJobIteration.getId());
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
        return "AppliedJobIteration{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", iterationDate='" + getIterationDate() + "'" +
            ", iterationType='" + getIterationType() + "'" +
            ", iterationNote='" + getIterationNote() + "'" +
            "}";
    }
}
