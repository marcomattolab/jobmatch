package it.aranciaict.jobmatch.service;

import it.aranciaict.jobmatch.service.dto.CompanySkillDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CompanySkill.
 */
public interface CompanySkillService {

    /**
     * Save a companySkill.
     *
     * @param companySkillDTO the entity to save
     * @return the persisted entity
     */
    CompanySkillDTO save(CompanySkillDTO companySkillDTO);

    /**
     * Get all the companySkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompanySkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companySkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompanySkillDTO> findOne(Long id);

    /**
     * Get the "id" company.
     *
     * @param idCompany the id of the company
     * @return the entity
     */
    List<CompanySkillDTO> findAllByIdCompany(Long idCompany);

    /**
     * Delete the "id" companySkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
