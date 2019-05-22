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

import it.aranciaict.jobmatch.domain.CompanySkill;
import it.aranciaict.jobmatch.repository.CompanySkillRepository;
import it.aranciaict.jobmatch.service.CompanySkillService;
import it.aranciaict.jobmatch.service.dto.CompanySkillDTO;
import it.aranciaict.jobmatch.service.mapper.CompanySkillMapper;

/**
 * Service Implementation for managing CompanySkill.
 */
@Service
@Transactional
public class CompanySkillServiceImpl implements CompanySkillService {

    private final Logger log = LoggerFactory.getLogger(CompanySkillServiceImpl.class);

    private final CompanySkillRepository companySkillRepository;

    private final CompanySkillMapper companySkillMapper;

    /**
     * Instantiates a new company skill service impl.
     *
     * @param companySkillRepository the company skill repository
     * @param companySkillMapper     the company skill mapper
     */
    public CompanySkillServiceImpl(CompanySkillRepository companySkillRepository,
            CompanySkillMapper companySkillMapper) {
        this.companySkillRepository = companySkillRepository;
        this.companySkillMapper = companySkillMapper;
    }

    /**
     * Save a companySkill.
     *
     * @param companySkillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanySkillDTO save(CompanySkillDTO companySkillDTO) {
        log.debug("Request to save CompanySkill : {}", companySkillDTO);
        CompanySkill companySkill = companySkillMapper.toEntity(companySkillDTO);
        companySkill = companySkillRepository.save(companySkill);
        return companySkillMapper.toDto(companySkill);
    }

    /**
     * Get all the companySkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanySkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanySkills");
        return companySkillRepository.findAll(pageable).map(companySkillMapper::toDto);
    }

    /**
     * Get one companySkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanySkillDTO> findOne(Long id) {
        log.debug("Request to get CompanySkill : {}", id);
        return companySkillRepository.findById(id).map(companySkillMapper::toDto);
    }

    @Override
    public List<CompanySkillDTO> findAllByIdCompany(Long idCompany) {
        return this.companySkillRepository.findAllByCompanyId(idCompany)
            .stream()
            .map(companySkillMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Delete the companySkill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanySkill : {}", id);
        companySkillRepository.deleteById(id);
    }

}
