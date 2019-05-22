package it.aranciaict.jobmatch.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.aranciaict.jobmatch.service.dto.AppliedJobDTO;
import it.aranciaict.jobmatch.service.dto.request.ApplyToJobOfferRequestDTO;
import it.aranciaict.jobmatch.service.exceptions.AppliedJobAlreadyExistException;

/**
 * Service Interface for managing AppliedJob.
 */
public interface AppliedJobService {

	/**
	 * Save a appliedJob.
	 *
	 * @param appliedJobDTO the entity to save
	 * @return the persisted entity
	 */
	AppliedJobDTO save(AppliedJobDTO appliedJobDTO);

	/**
	 * Get all the appliedJobs.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	Page<AppliedJobDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" appliedJob.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	Optional<AppliedJobDTO> findOne(Long id);

	/**
	 * Delete the "id" appliedJob.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);

	/**
	 * Creates the applied job.
	 *
	 * @param applyToJobOfferRequestDTO the apply to job offer request DTO
	 * @return the applied job DTO
	 * @throws AppliedJobAlreadyExistException the applied job already exist exception
	 */
	AppliedJobDTO createAppliedJob(@Valid ApplyToJobOfferRequestDTO applyToJobOfferRequestDTO)
			throws AppliedJobAlreadyExistException;

//	/**
//	 * createAppliedJobToCompany.
//	 *
//	 * @param request the request
//	 */
//	void createAppliedJobToCompany(ApplyToCompanyDTO request);
}
