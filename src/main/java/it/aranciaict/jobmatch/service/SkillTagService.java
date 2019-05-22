package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.SkillTagDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * Service Interface for managing SkillTag.
 */
public interface SkillTagService {

    /**
     * Save a skillTag.
     *
     * @param skillTagDTO the entity to save
     * @return the persisted entity
     */
    SkillTagDTO save(SkillTagDTO skillTagDTO);

    /**
     * Get all the skillTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SkillTagDTO> findAll(Pageable pageable);


    /**
     * Get the "id" skillTag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SkillTagDTO> findOne(Long id);

    /**
     * Delete the "id" skillTag.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
