package it.aranciaict.jobmatch.service.dto.email;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import it.aranciaict.jobmatch.domain.item.JobOfferToPromote;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Email Promote Job Offers")
public class PromoteJobOffersEmailDTO extends AbstractEmailDTO implements Serializable {
    
    /** Custom message */
    private String messageFromSender;

    /** Job Offer Details */
    private List<JobOfferToPromote> jobOffersDetail;

    /** Empty Constructor */
    public PromoteJobOffersEmailDTO() { }
    
    /**
     * Constructor with parameters
     * @param messageFromSender the message from sender
     * @param jobOffersDetail the job offers detail
     */
    public PromoteJobOffersEmailDTO(String messageFromSender, List<JobOfferToPromote> jobOffersDetail) {
        this.messageFromSender = messageFromSender;
        this.jobOffersDetail = jobOffersDetail;
    }

    /**
     * @return the messageFromSender
     */
    public String getMessageFromSender() {
        return messageFromSender;
    }

    /**
     * @param messageFromSender the messageFromSender to set
     */
    public void setMessageFromSender(String messageFromSender) {
        this.messageFromSender = messageFromSender;
    }

    /**
     * @return the jobOffersDetail
     */
    public List<JobOfferToPromote> getJobOffersDetail() {
        return jobOffersDetail;
    }

    /**
     * @param jobOffersDetail the jobOffersDetail to set
     */
    public void setJobOffersDetail(List<JobOfferToPromote> jobOffersDetail) {
        this.jobOffersDetail = jobOffersDetail;
    }
    
}