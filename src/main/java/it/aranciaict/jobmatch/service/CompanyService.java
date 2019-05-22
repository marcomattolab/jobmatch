package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.CompanyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompanyDTO> findAll(Pageable pageable);

//    /**
//     * Get all the Company with eager load of many-to-many relationships.
//     *
//     * @param pageable the pageable
//     * @return the list of entities
//     */
//    Page<CompanyDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" company.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompanyDTO> findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	/**
	 * Find by current user.
	 *
	 * @return the optional
	 */
	Optional<CompanyDTO> findByCurrentUser();

	/**
	 * Find company id by current user.
	 *
	 * @return the optional
	 */
	Optional<Long> findCompanyIdByCurrentUser();
}
