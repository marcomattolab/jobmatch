package it.aranciaict.jobmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.AppliedJob;


/**
 * Spring Data  repository for the AppliedJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long>, JpaSpecificationExecutor<AppliedJob> {

	/**
	 * Count by job offer id and candidate id.
	 *
	 * @param jobOfferId the job offer id
	 * @param candidateId the candidate id
	 * @return the long
	 */
	long countByJobOfferIdAndCandidateId(Long jobOfferId, Long candidateId);
}
