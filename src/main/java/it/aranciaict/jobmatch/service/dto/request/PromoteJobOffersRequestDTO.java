package it.aranciaict.jobmatch.service.dto.request;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Request DTO to promote some job offers")
public class PromoteJobOffersRequestDTO implements Serializable {

    /** Job Offers Id to promote */
    private List<Long> jobOffersId;

    /** Candidates to send email to */
    private List<Long> candidatesId;
    
    /** The Custom Messages */
    private List<PromoteJobOffersCustomMessageDTO> customMessages;

    /**
     * @return the jobOffersId
     */
    public List<Long> getJobOffersId() {
        return jobOffersId;
    }

    /**
     * @param jobOffersId the jobOffersId to set
     */
    public void setJobOffersId(List<Long> jobOffersId) {
        this.jobOffersId = jobOffersId;
    }

    /**
     * @return the candidatesId
     */
    public List<Long> getCandidatesId() {
        return candidatesId;
    }

    /**
     * @param candidatesId the candidatesId to set
     */
    public void setCandidatesId(List<Long> candidatesId) {
        this.candidatesId = candidatesId;
    }

    /**
     * @return the customMessages
     */
    public List<PromoteJobOffersCustomMessageDTO> getCustomMessages() {
        return customMessages;
    }

    /**
     * @param customMessages the customMessages to set
     */
    public void setCustomMessages(List<PromoteJobOffersCustomMessageDTO> customMessages) {
        this.customMessages = customMessages;
    }
    
}