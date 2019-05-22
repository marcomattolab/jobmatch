package it.aranciaict.jobmatch.domain;


import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.domain.enumeration.AppliedJobFeedback;

/**
 * Entity Applied Job (Candidatura).
 */
@Entity
@Table(name = "applied_job")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppliedJob extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    /** The applied job feedback. */
    @Enumerated(EnumType.STRING)
    @Column(name = "applied_job_feedback")
    private AppliedJobFeedback appliedJobFeedback;

    /** The applied job iterations. */
    @OneToMany(mappedBy = "appliedJob")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AppliedJobIteration> appliedJobIterations = new HashSet<>();
    
    /** The candidate. */
    @ManyToOne
    @JsonIgnoreProperties("appliedJobs")
    private Candidate candidate;

    /** The job offer. */
    @ManyToOne
    @JsonIgnoreProperties("appliedJobs")
    private JobOffer jobOffer;
    
    /** The appied job status. */
    @Enumerated(EnumType.STRING)
    @Column(name = "appied_job_status")
    private AppiedJobStatus appiedJobStatus = AppiedJobStatus.NEW;


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
     * Gets the applied job feedback.
     *
     * @return the applied job feedback
     */
    public AppliedJobFeedback getAppliedJobFeedback() {
        return appliedJobFeedback;
    }

    /**
     * Applied job feedback.
     *
     * @param appliedJobFeedback the applied job feedback
     * @return the applied job
     */
    public AppliedJob appliedJobFeedback(AppliedJobFeedback appliedJobFeedback) {
        this.appliedJobFeedback = appliedJobFeedback;
        return this;
    }

    /**
     * Sets the applied job feedback.
     *
     * @param appliedJobFeedback the new applied job feedback
     */
    public void setAppliedJobFeedback(AppliedJobFeedback appliedJobFeedback) {
        this.appliedJobFeedback = appliedJobFeedback;
    }

    /**
     * Gets the applied job iterations.
     *
     * @return the applied job iterations
     */
    public Set<AppliedJobIteration> getAppliedJobIterations() {
        return appliedJobIterations;
    }

    /**
     * Applied job iterations.
     *
     * @param appliedJobIterations the applied job iterations
     * @return the applied job
     */
    public AppliedJob appliedJobIterations(Set<AppliedJobIteration> appliedJobIterations) {
        this.appliedJobIterations = appliedJobIterations;
        return this;
    }

    /**
     * Adds the applied job iteration.
     *
     * @param appliedJobIteration the applied job iteration
     * @return the applied job
     */
    public AppliedJob addAppliedJobIteration(AppliedJobIteration appliedJobIteration) {
        this.appliedJobIterations.add(appliedJobIteration);
        appliedJobIteration.setAppliedJob(this);
        return this;
    }

    /**
     * Removes the applied job iteration.
     *
     * @param appliedJobIteration the applied job iteration
     * @return the applied job
     */
    public AppliedJob removeAppliedJobIteration(AppliedJobIteration appliedJobIteration) {
        this.appliedJobIterations.remove(appliedJobIteration);
        appliedJobIteration.setAppliedJob(null);
        return this;
    }

    /**
     * Sets the applied job iterations.
     *
     * @param appliedJobIterations the new applied job iterations
     */
    public void setAppliedJobIterations(Set<AppliedJobIteration> appliedJobIterations) {
        this.appliedJobIterations = appliedJobIterations;
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
     * @return the applied job
     */
    public AppliedJob candidate(Candidate candidate) {
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

    /**
     * Gets the job offer.
     *
     * @return the job offer
     */
    public JobOffer getJobOffer() {
        return jobOffer;
    }

    /**
     * Job offer.
     *
     * @param jobOffer the job offer
     * @return the applied job
     */
    public AppliedJob jobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
        return this;
    }

    /**
     * Sets the job offer.
     *
     * @param jobOffer the new job offer
     */
    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
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
	 * Sets the appied job status.
	 *
	 * @param appiedJobStatus the new appied job status
	 */
	public void setAppiedJobStatus(AppiedJobStatus appiedJobStatus) {
		this.appiedJobStatus = appiedJobStatus;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppliedJob appliedJob = (AppliedJob) o;
        if (appliedJob.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appliedJob.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "AppliedJob [id=" + id + ", appliedJobFeedback=" + appliedJobFeedback + ", appliedJobIterations="
				+ appliedJobIterations + ", candidate=" + candidate + ", jobOffer=" + jobOffer + ", appiedJobStatus="
				+ appiedJobStatus + "]";
	}

   
}
