package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing JobOfferSkill.
 */
public interface JobOfferSkillService {

    /**
     * Save a jobOfferSkill.
     *
     * @param jobOfferSkillDTO the entity to save
     * @return the persisted entity
     */
    JobOfferSkillDTO save(JobOfferSkillDTO jobOfferSkillDTO);

    /**
     * Get all the jobOfferSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobOfferSkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jobOfferSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<JobOfferSkillDTO> findOne(Long id);

    /**
     * Delete the "id" jobOfferSkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
