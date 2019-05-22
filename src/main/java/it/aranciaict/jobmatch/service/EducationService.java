package it.aranciaict.jobmatch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.aranciaict.jobmatch.service.dto.EducationDTO;

/**
 * Service Interface for managing Education.
 */
public interface EducationService {

    /**
     * Save a education.
     *
     * @param educationDTO the entity to save
     * @return the persisted entity
     */
    EducationDTO save(EducationDTO educationDTO);

    /**
     * Get all the educations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EducationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" education.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EducationDTO> findOne(Long id);

    /**
     * Delete the "id" education.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	/**
	 * Find all by candidate id.
	 *
	 * @param idCandidate the id candidate
	 * @return the list
	 */
	List<EducationDTO> findAllByCandidateId(Long idCandidate);
}
