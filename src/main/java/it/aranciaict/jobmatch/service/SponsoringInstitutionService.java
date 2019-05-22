package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SponsoringInstitution.
 */
public interface SponsoringInstitutionService {

    /**
     * Save a sponsoringInstitution.
     *
     * @param sponsoringInstitutionDTO the entity to save
     * @return the persisted entity
     */
    SponsoringInstitutionDTO save(SponsoringInstitutionDTO sponsoringInstitutionDTO);

    /**
     * Get all the sponsoringInstitutions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SponsoringInstitutionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sponsoringInstitution.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SponsoringInstitutionDTO> findOne(Long id);
    
    
    /**
     * Find by current user.
     *
     * @return the optional
     */
    Optional<SponsoringInstitutionDTO> findByCurrentUser();

    /**
     * Delete the "id" sponsoringInstitution.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	
}
