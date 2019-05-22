package it.aranciaict.jobmatch.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;

/**
 * Service Interface for managing CompanySector.
 */
public interface CompanySectorService {

    /**
     * Save a companySector.
     *
     * @param companySectorDTO the entity to save
     * @return the persisted entity
     */
    CompanySectorDTO save(CompanySectorDTO companySectorDTO);

    /**
     * Get all the companySectors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompanySectorDTO> findAll(Pageable pageable);

    /**
     * Find all.
     *
     * @param locale the locale
     * @return the list
     */
    List<CompanySectorDTO> findAll(Locale locale);
    
    /**
     * Get the "id" companySector.
     * @param locale 
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompanySectorDTO> findOne(Locale locale, Long id);

    /**
     * Delete the "id" companySector.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	
}
