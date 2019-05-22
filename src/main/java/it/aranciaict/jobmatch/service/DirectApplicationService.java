package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.DirectApplicationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DirectApplication.
 */
public interface DirectApplicationService {

    /**
     * Save a directApplication.
     *
     * @param directApplicationDTO the entity to save
     * @return the persisted entity
     */
    DirectApplicationDTO save(DirectApplicationDTO directApplicationDTO);

    /**
     * Get all the directApplications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DirectApplicationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" directApplication.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DirectApplicationDTO> findOne(Long id);

    /**
     * Delete the "id" directApplication.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	/**
	 * Creates the applied job to company.
	 *
	 * @param directApplicationDTO the direct application DTO
	 * @return the direct application DTO
	 */
	DirectApplicationDTO createAppliedJobToCompany(DirectApplicationDTO directApplicationDTO);
}
