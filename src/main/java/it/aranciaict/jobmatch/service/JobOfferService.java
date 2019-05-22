package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.JobOfferDTO;

import it.aranciaict.jobmatch.service.dto.request.ChangeJobOfferStateRequestDTO;
import it.aranciaict.jobmatch.service.dto.request.PromoteJobOffersRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing JobOffer.
 */
public interface JobOfferService {

    /**
     * Save a jobOffer.
     *
     * @param jobOfferDTO the entity to save
     * @return the persisted entity
     */
    JobOfferDTO save(JobOfferDTO jobOfferDTO);
    
    /**
     * Get all the jobOffers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<JobOfferDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jobOffer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<JobOfferDTO> findOne(Long id);

    /**
     * Delete the "id" jobOffer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    
    /**
     * Change state.
     *
     * @param changeJobOfferStateRequestDTO the change job offer state request DTO
     * @return the job offer DTO
     */
    JobOfferDTO changeStatus(ChangeJobOfferStateRequestDTO changeJobOfferStateRequestDTO);

    /**
     * Promote Job Offers.
     *
     * @param promoteRequest the promote request detail
     * @return true if every ids was valid, false if some ids were invalid and not sent in the email
     */
    boolean promoteJobOffers(PromoteJobOffersRequestDTO promoteRequest);
}
