package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.service.CandidateService;
import it.aranciaict.jobmatch.service.dto.CandidateDTO;
import it.aranciaict.jobmatch.service.mapper.CandidateMapper;

/**
 * Service Implementation for managing Candidate.
 */
@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

	/** The candidate repository. */
	private final CandidateRepository candidateRepository;

	/** The candidate mapper. */
	private final CandidateMapper candidateMapper;


	/**
	 * Instantiates a new candidate service impl.
	 *
	 * @param candidateRepository the candidate repository
	 * @param candidateMapper     the candidate mapper
	 */
	public CandidateServiceImpl(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
		this.candidateRepository = candidateRepository;
		this.candidateMapper = candidateMapper;
	}

	/**
	 * Save a candidate.
	 *
	 * @param candidateDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public CandidateDTO save(CandidateDTO candidateDTO) {
		log.debug("Request to save Candidate : {}", candidateDTO);
		Candidate candidate = candidateMapper.toEntity(candidateDTO);
		candidate = candidateRepository.save(candidate);
		return candidateMapper.toDto(candidate);
	}

	/**
	 * Get all the candidates.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<CandidateDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Candidates");
		return candidateRepository.findAll(pageable).map(candidateMapper::toDto);
	}

	/**
	 * Get one candidate by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<CandidateDTO> findOne(Long id) {
		log.debug("Request to get Candidate : {}", id);
		return candidateRepository.findById(id).map(candidateMapper::toDto);
	}


	@Override
	@Transactional(readOnly = true)
	public Optional<CandidateDTO> findByCurrentUser() {
		log.debug("Request to get Candidate by current User");
		return candidateRepository.findByUserIsCurrentUser().map(candidateMapper::toDto);
	}

	/**
	 * Delete the candidate by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Candidate : {}", id);
		candidateRepository.deleteById(id);
	}

	@Override
	public Optional<Long> findCandidateIdByCurrentUser() {
		log.debug("Request to get Candidate Id by current User");
		return candidateRepository.findCandidateIdByCurrentUser();
	}
}
