package it.aranciaict.jobmatch.service.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

/**
 * A DTO for the AppliedJob entity.
 */
@SuppressWarnings("serial")
@ApiModel(description = "Request DTO to promote some job offers")
public class PromoteJobOffersCustomMessageDTO implements Serializable {

    /** Language key */
    @NotNull
    private String langKey;
    
    /** Custom message */
    @NotNull
    private String customMessage;

    /** Empty Constructor */
    public PromoteJobOffersCustomMessageDTO() { }

    /**
     * Constructor With Parameter
     * @param langKey the language key
     * @param customMessage the custom message
     */
    public PromoteJobOffersCustomMessageDTO(String langKey, String customMessage) {
        this.langKey = langKey;
        this.customMessage = customMessage;
    }

    /**
     * @return the langKey
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * @param langKey the langKey to set
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     * @return the customMessage
     */
    public String getCustomMessage() {
        return customMessage;
    }

    /**
     * @param customMessage the customMessage to set
     */
    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
    
}