package it.aranciaict.jobmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.SkillTag;


/**
 * Spring Data  repository for the SkillTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillTagRepository extends JpaRepository<SkillTag, Long>, JpaSpecificationExecutor<SkillTag> {

}
