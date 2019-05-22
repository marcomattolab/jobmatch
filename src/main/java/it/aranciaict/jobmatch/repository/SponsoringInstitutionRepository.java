package it.aranciaict.jobmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.SponsoringInstitution;

/**
 * Spring Data repository for the SponsoringInstitution entity.
 */
@Repository
public interface SponsoringInstitutionRepository
		extends JpaRepository<SponsoringInstitution, Long>, JpaSpecificationExecutor<SponsoringInstitution> {

	/**
	 * Find by user is current user.
	 *
	 * @return the optional
	 */
	@Query("SELECT institution FROM SponsoringInstitution institution WHERE institution.user.login = ?#{principal.username}")
	Optional<SponsoringInstitution> findByUserIsCurrentUser();

	/**
	 * Find by user.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	@Query("SELECT institution FROM SponsoringInstitution institution WHERE institution.user.login = :userName")
	Optional<SponsoringInstitution> findByUser(@Param("userName") String userName);

	/**
	 * Find id by user.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	@Query("SELECT institution.id FROM SponsoringInstitution institution WHERE institution.user.login = :userName")
	Optional<Long> findIdByUser(@Param("userName") String userName);

}
