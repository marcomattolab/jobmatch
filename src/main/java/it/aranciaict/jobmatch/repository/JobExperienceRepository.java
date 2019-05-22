package it.aranciaict.jobmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.JobExperience;


/**
 * Spring Data  repository for the JobExperience entity.
 */
@Repository
public interface JobExperienceRepository extends JpaRepository<JobExperience, Long>, JpaSpecificationExecutor<JobExperience> {

	/**
	 * Find all by candidate id.
	 *
	 * @param candidateId the id candidate
	 * @return the list
	 */
	List<JobExperience> findAllByCandidateId(Long candidateId);
	
}
