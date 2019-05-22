package it.aranciaict.jobmatch.service;

import java.util.Optional;

import it.aranciaict.jobmatch.service.dto.CompanyInfoDTO;

/**
 * Service Interface for managing Company Info.
 */
public interface CompanyInfoService {

    /**
     * Get the "id" company.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompanyInfoDTO> findOne(Long id);
    
	/**
	 * Find by user is current user.
	 *
	 * @return the optional
	 */
    Optional<CompanyInfoDTO> findByUserIsCurrentUser();

	/**
	 * Find by sponsoring institution id.
	 *
	 * @param sponsoringInstitutionId the sponsoring institution id
	 * @return the optional
	 */
	Optional<CompanyInfoDTO> findBySponsoringInstitutionId(Long sponsoringInstitutionId);

    
}
