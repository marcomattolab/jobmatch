package it.aranciaict.jobmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.JobOfferSkill;


/**
 * Spring Data  repository for the JobOfferSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobOfferSkillRepository extends JpaRepository<JobOfferSkill, Long>, JpaSpecificationExecutor<JobOfferSkill> {

}
