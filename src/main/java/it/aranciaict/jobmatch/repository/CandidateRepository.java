package it.aranciaict.jobmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.item.CandidateForOfferPromotion;

/**
 * Spring Data repository for the Candidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

	/**
	 * Find by user is current user.
	 *
	 * @return the optional
	 */
	@Query("SELECT candidate FROM Candidate candidate WHERE candidate.user.login = ?#{principal.username}")
	Optional<Candidate> findByUserIsCurrentUser();

	/**
	 * Find by user.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	@Query("SELECT candidate FROM Candidate candidate WHERE candidate.user.login = :userName")
	Optional<Candidate> findByUser(@Param("userName") String userName);

	/**
	 * Find candidate id by current user.
	 *
	 * @return the optional
	 */
	@Query("SELECT candidate.id FROM Candidate candidate WHERE candidate.user.login = ?#{principal.username}")
	Optional<Long> findCandidateIdByCurrentUser();

	/**
	 * Find id by user.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	@Query("SELECT candidate.id FROM Candidate candidate WHERE candidate.user.login = :userName")
	Optional<Long> findIdByUser(@Param("userName") String userName);

	/**
	 * Find candidates for promoting job offers.
	 *
	 * @param ids the ids
	 * @return the candidates
	 */
	@Query(value = "SELECT cand.id AS id, cand.user.langKey AS langKey, cand.user.email AS email FROM Candidate cand WHERE cand.id IN ?1 ORDER BY cand.id ASC")
	List<CandidateForOfferPromotion> findAllCandidatesForJobOffersPromotionByIds(List<Long> ids);

}
