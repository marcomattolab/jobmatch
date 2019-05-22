package it.aranciaict.jobmatch.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.CompanySector;
import it.aranciaict.jobmatch.repository.CompanySectorRepository;
import it.aranciaict.jobmatch.service.CompanySectorService;
import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;
import it.aranciaict.jobmatch.service.mapper.CompanySectorMapper;

/**
 * Service Implementation for managing CompanySector.
 */
@Service
@Transactional
public class CompanySectorServiceImpl implements CompanySectorService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CompanySectorServiceImpl.class);

	/** The company sector repository. */
	private final CompanySectorRepository companySectorRepository;

	/** The company sector mapper. */
	private final CompanySectorMapper companySectorMapper;

	/**
	 * Instantiates a new company sector service impl.
	 *
	 * @param companySectorRepository the company sector repository
	 * @param companySectorMapper     the company sector mapper
	 */
	public CompanySectorServiceImpl(CompanySectorRepository companySectorRepository,
			CompanySectorMapper companySectorMapper) {
		this.companySectorRepository = companySectorRepository;
		this.companySectorMapper = companySectorMapper;
	}

	/**
	 * Save a companySector.
	 *
	 * @param companySectorDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public CompanySectorDTO save(CompanySectorDTO companySectorDTO) {
		log.debug("Request to save CompanySector : {}", companySectorDTO);
		CompanySector companySector = companySectorMapper.toEntity(companySectorDTO);
		companySector = companySectorRepository.save(companySector);
		return companySectorMapper.toDto(companySector);
	}

	/**
	 * Get all the companySectors.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<CompanySectorDTO> findAll(Pageable pageable) {
		log.debug("Request to get all CompanySectors");
		return companySectorRepository.findAll(pageable).map(companySectorMapper::toDto);
	}

	/**
	 * Get all the companySectors.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CompanySectorDTO> findAll(Locale locale) {
		log.debug("Request to get all CompanySectors");
		List<CompanySector> companySectors = companySectorRepository.findAll();
		return companySectors.stream().map(companySector -> companySectorMapper.toDto(companySector, locale))
				.collect(Collectors.toList());
	}

	/**
	 * Get one companySector by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<CompanySectorDTO> findOne(Locale locale, Long id) {
		log.debug("Request to get CompanySector : {}", id);
		return companySectorRepository.findById(id).map(companySector -> companySectorMapper.toDto(companySector, locale));
	}

	/**
	 * Delete the companySector by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete CompanySector : {}", id);
		companySectorRepository.deleteById(id);
	}
}
