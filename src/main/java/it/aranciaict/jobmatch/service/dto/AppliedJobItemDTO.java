package it.aranciaict.jobmatch.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.domain.enumeration.AppliedJobFeedback;
import it.aranciaict.jobmatch.domain.enumeration.Country;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Item Applied Job (Candidatura)")
public class AppliedJobItemDTO implements Serializable {

    /** The id. */
    private Long id;

    /** The created by. */
    private String createdBy;

    /** The last modified by. */
    private String lastModifiedBy;

    /** The created date. */
    private Instant createdDate;

    /** The last modified date. */
    private Instant lastModifiedDate;

    /** The applied job feedback. */
    private AppliedJobFeedback appliedJobFeedback;
    
    /** The appied job status. */
    private AppiedJobStatus appiedJobStatus;

    // CANDIADTE INFO
    
    /** The candidate id. */
    private Long candidateId;

    /** The last name. */
	private String lastName;

	/** The first name. */
	private String firstName;

	// JOB OFFER INFO
	
    /** The job offer id. */
    private Long jobOfferId;
    
    /** The job offer title. */
	private String jobOfferTitle;
	
	/** The job offer city. */
	private String jobOfferCity;
	
	/** The job offer country. */
	private Country jobOfferCountry;
    

	// COMPANY INFO
    
    /** The company id. */
    private Long companyId;
    
    /** The company name. */
    private String companyName;
    
    /** The image url. */
    private String imageUrl;
    
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
     * Gets the applied job feedback.
     *
     * @return the applied job feedback
     */
    public AppliedJobFeedback getAppliedJobFeedback() {
        return appliedJobFeedback;
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
     * Gets the job offer id.
     *
     * @return the job offer id
     */
    public Long getJobOfferId() {
        return jobOfferId;
    }

    /**
     * Sets the job offer id.
     *
     * @param jobOfferId the new job offer id
     */
    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }

    /**
     * Gets the last name.
     *
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first name.
     *
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	/**
	 * Gets the job offer title.
	 *
	 * @return the job offer title
	 */
	public String getJobOfferTitle() {
		return jobOfferTitle;
	}

	/**
	 * Sets the job offer title.
	 *
	 * @param jobOfferTitle the new job offer title
	 */
	public void setJobOfferTitle(String jobOfferTitle) {
		this.jobOfferTitle = jobOfferTitle;
	}

	/**
	 * Gets the job offer city.
	 *
	 * @return the job offer city
	 */
	public String getJobOfferCity() {
		return jobOfferCity;
	}

	/**
	 * Sets the job offer city.
	 *
	 * @param jobOfferCity the new job offer city
	 */
	public void setJobOfferCity(String jobOfferCity) {
		this.jobOfferCity = jobOfferCity;
	}

	/**
	 * Gets the job offer country.
	 *
	 * @return the job offer country
	 */
	public Country getJobOfferCountry() {
		return jobOfferCountry;
	}

	/**
	 * Sets the job offer country.
	 *
	 * @param jobOfferCountry the new job offer country
	 */
	public void setJobOfferCountry(Country jobOfferCountry) {
		this.jobOfferCountry = jobOfferCountry;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 *
	 * @param imageUrl the new image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

        AppliedJobDTO appliedJobDTO = (AppliedJobDTO) o;
        if (appliedJobDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appliedJobDTO.getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }




	
	
}
