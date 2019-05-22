package it.aranciaict.jobmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.Education;

/**
 * Spring Data repository for the Education entity.
 */
@Repository
public interface EducationRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {

	/**
	 * Find all by candidate id.
	 *
	 * @param candidateId the id candidate
	 * @return the list
	 */
	List<Education> findAllByCandidateId(Long candidateId);

}
