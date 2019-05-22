package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.JobOfferSkill;
import it.aranciaict.jobmatch.repository.JobOfferSkillRepository;
import it.aranciaict.jobmatch.service.JobOfferSkillService;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;
import it.aranciaict.jobmatch.service.mapper.JobOfferSkillMapper;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing JobOfferSkill.
 */
@Service
@Transactional
public class JobOfferSkillServiceImpl implements JobOfferSkillService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(JobOfferSkillServiceImpl.class);

    /** The job offer skill repository. */
    private final JobOfferSkillRepository jobOfferSkillRepository;

    /** The job offer skill mapper. */
    private final JobOfferSkillMapper jobOfferSkillMapper;

    /**
     * Instantiates a new job offer skill service impl.
     *
     * @param jobOfferSkillRepository the job offer skill repository
     * @param jobOfferSkillMapper the job offer skill mapper
     */
    public JobOfferSkillServiceImpl(JobOfferSkillRepository jobOfferSkillRepository, JobOfferSkillMapper jobOfferSkillMapper) {
        this.jobOfferSkillRepository = jobOfferSkillRepository;
        this.jobOfferSkillMapper = jobOfferSkillMapper;
    }

    /**
     * Save a jobOfferSkill.
     *
     * @param jobOfferSkillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JobOfferSkillDTO save(JobOfferSkillDTO jobOfferSkillDTO) {
        log.debug("Request to save JobOfferSkill : {}", jobOfferSkillDTO);
        JobOfferSkill jobOfferSkill = jobOfferSkillMapper.toEntity(jobOfferSkillDTO);
        jobOfferSkill = jobOfferSkillRepository.save(jobOfferSkill);
        return jobOfferSkillMapper.toDto(jobOfferSkill);
    }

    /**
     * Get all the jobOfferSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobOfferSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobOfferSkills");
        return jobOfferSkillRepository.findAll(pageable)
            .map(jobOfferSkillMapper::toDto);
    }


    /**
     * Get one jobOfferSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JobOfferSkillDTO> findOne(Long id) {
        log.debug("Request to get JobOfferSkill : {}", id);
        return jobOfferSkillRepository.findById(id)
            .map(jobOfferSkillMapper::toDto);
    }

    /**
     * Delete the jobOfferSkill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobOfferSkill : {}", id);
        jobOfferSkillRepository.deleteById(id);
    }
}
