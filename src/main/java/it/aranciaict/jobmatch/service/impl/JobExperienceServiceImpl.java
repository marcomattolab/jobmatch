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

import it.aranciaict.jobmatch.domain.JobExperience;
import it.aranciaict.jobmatch.repository.JobExperienceRepository;
import it.aranciaict.jobmatch.service.JobExperienceService;
import it.aranciaict.jobmatch.service.dto.JobExperienceDTO;
import it.aranciaict.jobmatch.service.mapper.JobExperienceMapper;

/**
 * Service Implementation for managing JobExperience.
 */
@Service
@Transactional
public class JobExperienceServiceImpl implements JobExperienceService {

    /** The log. */
    private static final Logger LOG = LoggerFactory.getLogger(JobExperienceServiceImpl.class);

    /** The job experience repository. */
    private final JobExperienceRepository jobExperienceRepository;

    /** The job experience mapper. */
    private final JobExperienceMapper jobExperienceMapper;

    
    
    /**
     * Instantiates a new job experience service impl.
     *
     * @param jobExperienceRepository the job experience repository
     * @param jobExperienceMapper the job experience mapper
     */
    public JobExperienceServiceImpl(JobExperienceRepository jobExperienceRepository, JobExperienceMapper jobExperienceMapper) {
        this.jobExperienceRepository = jobExperienceRepository;
        this.jobExperienceMapper = jobExperienceMapper;
    }

    /**
     * Save a jobExperience.
     *
     * @param jobExperienceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JobExperienceDTO save(JobExperienceDTO jobExperienceDTO) {
        LOG.debug("Request to save JobExperience : {}", jobExperienceDTO);
        JobExperience jobExperience = jobExperienceMapper.toEntity(jobExperienceDTO);
        jobExperience = jobExperienceRepository.save(jobExperience);
        return jobExperienceMapper.toDto(jobExperience);
    }

    /**
     * Get all the jobExperiences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobExperienceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all JobExperiences");
        return jobExperienceRepository.findAll(pageable)
            .map(jobExperienceMapper::toDto);
    }


    /**
     * Get one jobExperience by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JobExperienceDTO> findOne(Long id) {
        LOG.debug("Request to get JobExperience : {}", id);
        return jobExperienceRepository.findById(id)
            .map(jobExperienceMapper::toDto);
    }

    /**
     * Delete the jobExperience by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete JobExperience : {}", id);
        jobExperienceRepository.deleteById(id);
    }
    
	@Override
	@Transactional(readOnly = true)
	public List<JobExperienceDTO> findAllByCandidateId(Long candidateId) {
		LOG.debug("Request to get All Job Experiences by candidate id : {}", candidateId);
		return jobExperienceRepository.findAllByCandidateId(candidateId).stream().map(jobExperienceMapper::toDto)
				.collect(Collectors.toList());
	}
}
