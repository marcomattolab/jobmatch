package it.aranciaict.jobmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.item.CompanyInfo;

/**
 * Spring Data repository for the Company entity.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

//    /**
//     * Find all with eager relationships.
//     *
//     * @param pageable the pageable
//     * @return the page
//     */
//    @Query(value = "select distinct company from Company company left join fetch company.sectors",
//        countQuery = "select count(distinct company) from Company company")
//    Page<Company> findAllWithEagerRelationships(Pageable pageable);
//
//    /**
//     * Find all with eager relationships.
//     *
//     * @return the list
//     */
//    @Query(value = "select distinct company from Company company left join fetch company.sectors")
//    List<Company> findAllWithEagerRelationships();
//
//    /**
//     * Find one with eager relationships.
//     *
//     * @param id the id
//     * @return the optional
//     */
//    @Query("select company from Company company left join fetch company.sectors where company.id =:id")
//    Optional<Company> findOneWithEagerRelationships(@Param("id") Long id);

	/**
	 * Find by user is current user.
	 *
	 * @return the optional
	 */
	@Query("SELECT company FROM Company company WHERE company.user.login = ?#{principal.username}")
	Optional<Company> findByUserIsCurrentUser();

	/**
	 * Find by user.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	@Query("SELECT company FROM Company company WHERE company.user.login = :userName")
	Optional<Company> findByUser(@Param("userName") String userName);

	/**
	 * Find id by user.
	 *
	 * @param userName the user name
	 * @return the optional
	 */
	@Query("SELECT company.id FROM Company company WHERE company.user.login = :userName")
	Optional<Long> findIdByUser(@Param("userName") String userName);

	/**
	 * Find info by id.
	 *
	 * @param id the id
	 * @return the optional
	 */
	@Query(value = "SELECT c.id as id, c.companyName as companyName, c.sector.id as sectorId, "
			+ "c.city as city, c.country as country, c.companySize as companySize, c.companyType as companyType, "
			+ "c.numberOfEmployee as numberOfEmployee, c.urlSite as urlSite, c.user.imageUrl as imageUrl, c.sponsoringInstitution.id as sponsoringInstitutionId "
			+ "FROM Company c WHERE c.id=:id")
	Optional<CompanyInfo> findInfoById(@Param("id") Long id);

	/**
	 * Find info by current user.
	 *
	 * @return the optional
	 */
	@Query(value = "SELECT c.id as id, c.companyName as companyName, c.sector.id as sectorId, "
			+ "c.city as city, c.country as country, c.companySize as companySize, c.companyType as companyType, "
			+ "c.numberOfEmployee as numberOfEmployee, c.urlSite as urlSite, c.user.imageUrl as imageUrl, c.sponsoringInstitution.id as sponsoringInstitutionId "
			+ "FROM Company c WHERE c.user.login = ?#{principal.username}")
	Optional<CompanyInfo> findInfoByUserIsCurrentUser();

	/**
	 * Find info by sponsoring institution id.
	 *
	 * @param sponsoringInstitutionId the sponsoring institution id
	 * @return the optional
	 */
	@Query(value = "SELECT c.id as id, c.companyName as companyName, c.sector.id as sectorId, "
			+ "c.city as city, c.country as country, c.companySize as companySize, c.companyType as companyType, "
			+ "c.numberOfEmployee as numberOfEmployee, c.urlSite as urlSite, c.user.imageUrl as imageUrl FROM Company c "
			+ "WHERE c.sponsoringInstitution.id=:sponsoringInstitutionId")
	Optional<CompanyInfo> findInfoBySponsoringInstitutionId(
			@Param("sponsoringInstitutionId") Long sponsoringInstitutionId);

}
