package it.aranciaict.jobmatch.service.dto;

import io.swagger.annotations.ApiModel;

/**
 * A DTO
 */
@SuppressWarnings("serial")
@ApiModel(description = "ApplyToCompanyDTO  (Iterazione Candidato-Azienda)")
public class ApplyToCompanyDTO {

    /** candidate id */
    private Long candidateId;

    /** company id */
    private Long companyId;

    /**
     * @return the candidateId
     */
    public Long getCandidateId() {
        return candidateId;
    }

    /**
     * @param candidateId the candidateId to set
     */
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    /**
     * @return the companyId
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}