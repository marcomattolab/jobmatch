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

import it.aranciaict.jobmatch.domain.Skill;
import it.aranciaict.jobmatch.repository.SkillRepository;
import it.aranciaict.jobmatch.service.SkillService;
import it.aranciaict.jobmatch.service.dto.SkillDTO;
import it.aranciaict.jobmatch.service.mapper.SkillMapper;

/**
 * Service Implementation for managing Skill.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(SkillServiceImpl.class);

	/** The skill repository. */
	private final SkillRepository skillRepository;

	/** The skill mapper. */
	private final SkillMapper skillMapper;

	/**
	 * Instantiates a new skill service impl.
	 *
	 * @param skillRepository the skill repository
	 * @param skillMapper     the skill mapper
	 */
	public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
		this.skillRepository = skillRepository;
		this.skillMapper = skillMapper;
	}

	/**
	 * Save a skill.
	 *
	 * @param skillDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public SkillDTO save(SkillDTO skillDTO) {
		LOG.debug("Request to save Skill : {}", skillDTO);
		Skill skill = skillMapper.toEntity(skillDTO);
		skill = skillRepository.save(skill);
		return skillMapper.toDto(skill);
	}

	/**
	 * Get all the skills.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SkillDTO> findAll(Pageable pageable) {
		LOG.debug("Request to get all Skills");
		return skillRepository.findAll(pageable).map(skillMapper::toDto);
	}

	/**
	 * Get one skill by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<SkillDTO> findOne(Long id) {
		LOG.debug("Request to get Skill : {}", id);
		return skillRepository.findById(id).map(skillMapper::toDto);
	}

	/**
	 * Delete the skill by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		LOG.debug("Request to delete Skill : {}", id);
		skillRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SkillDTO> findAllByCandidateId(Long candidateId) {
		LOG.debug("Request to get All Skills by candidate id : {}", candidateId);
		return skillRepository.findAllByCandidateId(candidateId).stream().map(skillMapper::toDto)
				.collect(Collectors.toList());
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<String> findAllSkillNamesByCandidateId(Long candidateId) {
//		LOG.debug("Request to get All Skills Names by candidate id : {}", candidateId);
//		return skillRepository.findAllSkillNamesByCandidateId(candidateId);
//	}
}
