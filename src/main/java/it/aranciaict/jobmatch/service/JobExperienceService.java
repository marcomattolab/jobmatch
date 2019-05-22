package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.JobExperienceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing JobExperience.
 */
public interface JobExperienceService {

    /**
     * Save a jobExperience.
     *
     * @param jobExperienceDTO the entity to save
     * @return the persisted entity
     */
    JobExperienceDTO save(JobExperienceDTO jobExperienceDTO);

    /**
     * Get all the jobExperiences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobExperienceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jobExperience.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<JobExperienceDTO> findOne(Long id);

    /**
     * Delete the "id" jobExperience.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	/**
	 * Find all by candidate id.
	 *
	 * @param candidateId the candidate id
	 * @return the list
	 */
	List<JobExperienceDTO> findAllByCandidateId(Long candidateId);
}
