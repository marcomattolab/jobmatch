package it.aranciaict.jobmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.CompanySkill;


/**
 * Spring Data  repository for the CompanySkill entity.
 */
@Repository
public interface CompanySkillRepository extends JpaRepository<CompanySkill, Long>, JpaSpecificationExecutor<CompanySkill> {
    
    /**
     * RETURN LIST OF COMPANY SKILL BY COMPANY ID.
     *
     * @param companyId the company id
     * @return list of companyskill
     */
    List<CompanySkill> findAllByCompanyId(Long companyId);
}
