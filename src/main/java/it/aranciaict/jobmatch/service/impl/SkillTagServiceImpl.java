package it.aranciaict.jobmatch.service.impl;

import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.repository.SkillTagRepository;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.service.mapper.SkillTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * Service Implementation for managing SkillTag.
 */
@Service
@Transactional
public class SkillTagServiceImpl implements SkillTagService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(SkillTagServiceImpl.class);

    /** The skill tag repository. */
    private final SkillTagRepository skillTagRepository;

    /** The skill tag mapper. */
    private final SkillTagMapper skillTagMapper;

    /**
     * Instantiates a new skill tag service impl.
     *
     * @param skillTagRepository the skill tag repository
     * @param skillTagMapper the skill tag mapper
     */
    public SkillTagServiceImpl(SkillTagRepository skillTagRepository, SkillTagMapper skillTagMapper) {
        this.skillTagRepository = skillTagRepository;
        this.skillTagMapper = skillTagMapper;
    }

    /**
     * Save a skillTag.
     *
     * @param skillTagDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SkillTagDTO save(SkillTagDTO skillTagDTO) {
        log.debug("Request to save SkillTag : {}", skillTagDTO);
        SkillTag skillTag = skillTagMapper.toEntity(skillTagDTO);
        skillTag = skillTagRepository.save(skillTag);
        return skillTagMapper.toDto(skillTag);
    }

    /**
     * Get all the skillTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SkillTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkillTags");
        return skillTagRepository.findAll(pageable)
            .map(skillTagMapper::toDto);
    }


    /**
     * Get one skillTag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillTagDTO> findOne(Long id) {
        log.debug("Request to get SkillTag : {}", id);
        return skillTagRepository.findById(id)
            .map(skillTagMapper::toDto);
    }

    /**
     * Delete the skillTag by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillTag : {}", id);
        skillTagRepository.deleteById(id);
    }
}
