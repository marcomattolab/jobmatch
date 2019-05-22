package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.AppliedJobIterationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AppliedJobIteration.
 */
public interface AppliedJobIterationService {

    /**
     * Save a appliedJobIteration.
     *
     * @param appliedJobIterationDTO the entity to save
     * @return the persisted entity
     */
    AppliedJobIterationDTO save(AppliedJobIterationDTO appliedJobIterationDTO);

    /**
     * Get all the appliedJobIterations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AppliedJobIterationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" appliedJobIteration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AppliedJobIterationDTO> findOne(Long id);

    /**
     * Delete the "id" appliedJobIteration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
