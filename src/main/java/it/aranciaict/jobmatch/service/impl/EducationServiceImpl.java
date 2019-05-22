package it.aranciaict.jobmatch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.Education;
import it.aranciaict.jobmatch.repository.EducationRepository;
import it.aranciaict.jobmatch.service.EducationService;
import it.aranciaict.jobmatch.service.dto.EducationDTO;
import it.aranciaict.jobmatch.service.mapper.EducationMapper;

/**
 * Service Implementation for managing Education.
 */
@Service
@Transactional
public class EducationServiceImpl implements EducationService {

	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(EducationServiceImpl.class);

	/** The education repository. */
	private final EducationRepository educationRepository;

	/** The education mapper. */
	private final EducationMapper educationMapper;

	/**
	 * Instantiates a new education service impl.
	 *
	 * @param educationRepository the education repository
	 * @param educationMapper     the education mapper
	 */
	public EducationServiceImpl(EducationRepository educationRepository, EducationMapper educationMapper) {
		this.educationRepository = educationRepository;
		this.educationMapper = educationMapper;
	}

	/**
	 * Save a education.
	 *
	 * @param educationDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public EducationDTO save(EducationDTO educationDTO) {
		LOG.debug("Request to save Education : {}", educationDTO);
		Education education = educationMapper.toEntity(educationDTO);
		education = educationRepository.save(education);
		return educationMapper.toDto(education);
	}

	/**
	 * Get all the educations.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<EducationDTO> findAll(Pageable pageable) {
		LOG.debug("Request to get all Educations");
		return educationRepository.findAll(pageable).map(educationMapper::toDto);
	}

	/**
	 * Get one education by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<EducationDTO> findOne(Long id) {
		LOG.debug("Request to get Education : {}", id);
		return educationRepository.findById(id).map(educationMapper::toDto);
	}

	/**
	 * Delete the education by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete Education : {}", id);
		educationRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EducationDTO> findAllByCandidateId(Long candidateId) {
		LOG.debug("Request to get All Educations by candidate id : {}", candidateId);
		return educationRepository.findAllByCandidateId(candidateId).stream().map(educationMapper::toDto)
				.collect(Collectors.toList());
	}
}
