package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.DocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Document.
 */
public interface DocumentService {

    /**
     * Save a document.
     *
     * @param documentDTO the entity to save
     * @return the persisted entity
     */
    DocumentDTO save(DocumentDTO documentDTO);

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" document.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DocumentDTO> findOne(Long id);

    /**
     * Delete the "id" document.
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
	List<DocumentDTO> findAllByCandidateId(Long candidateId);

	/**
	 * Find cv by candidate id.
	 *
	 * @param candidateId the candidate id
	 * @return the optional
	 */
	Optional<DocumentDTO> findCvByCandidateId(Long candidateId);
}
