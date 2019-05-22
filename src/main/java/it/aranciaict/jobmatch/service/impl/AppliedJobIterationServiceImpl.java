package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.AppliedJobIteration;
import it.aranciaict.jobmatch.repository.AppliedJobIterationRepository;
import it.aranciaict.jobmatch.service.AppliedJobIterationService;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationDTO;
import it.aranciaict.jobmatch.service.mapper.AppliedJobIterationMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing AppliedJobIteration.
 */
@Service
@Transactional
public class AppliedJobIterationServiceImpl implements AppliedJobIterationService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(AppliedJobIterationServiceImpl.class);

    /** The applied job iteration repository. */
    private final AppliedJobIterationRepository appliedJobIterationRepository;

    /** The applied job iteration mapper. */
    private final AppliedJobIterationMapper appliedJobIterationMapper;

    /**
     * Instantiates a new applied job iteration service impl.
     *
     * @param appliedJobIterationRepository the applied job iteration repository
     * @param appliedJobIterationMapper the applied job iteration mapper
     */
    public AppliedJobIterationServiceImpl(AppliedJobIterationRepository appliedJobIterationRepository, AppliedJobIterationMapper appliedJobIterationMapper) {
        this.appliedJobIterationRepository = appliedJobIterationRepository;
        this.appliedJobIterationMapper = appliedJobIterationMapper;
    }

    /**
     * Save a appliedJobIteration.
     *
     * @param appliedJobIterationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AppliedJobIterationDTO save(AppliedJobIterationDTO appliedJobIterationDTO) {
        log.debug("Request to save AppliedJobIteration : {}", appliedJobIterationDTO);
        AppliedJobIteration appliedJobIteration = appliedJobIterationMapper.toEntity(appliedJobIterationDTO);
        appliedJobIteration = appliedJobIterationRepository.save(appliedJobIteration);
        return appliedJobIterationMapper.toDto(appliedJobIteration);
    }

    /**
     * Get all the appliedJobIterations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppliedJobIterationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppliedJobIterations");
        return appliedJobIterationRepository.findAll(pageable)
            .map(appliedJobIterationMapper::toDto);
    }


    /**
     * Get one appliedJobIteration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppliedJobIterationDTO> findOne(Long id) {
        log.debug("Request to get AppliedJobIteration : {}", id);
        return appliedJobIterationRepository.findById(id)
            .map(appliedJobIterationMapper::toDto);
    }

    /**
     * Delete the appliedJobIteration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppliedJobIteration : {}", id);
        appliedJobIterationRepository.deleteById(id);
    }
}
