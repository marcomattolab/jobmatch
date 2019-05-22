package it.aranciaict.jobmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.item.JobOfferToPromote;
import it.aranciaict.jobmatch.repository.support.JpaSpecificationExecutorWithProjection;


/**
 * Spring Data  repository for the JobOffer entity.
 */
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>, JpaSpecificationExecutorWithProjection<JobOffer> {

	
//	@Override
//	@Query(value="SELECT jo.id as id, jo.startDate as startDate, jo.jobTitle as jobTitle, jo.jobShortDescription as jobShortDescription FROM JobOffer jo")
//	<R> Page<R> findAll(Specification<JobOffer> spec, Class<R> projectionClass, Pageable pageable);
    
    /** 
     * find all job offers to promote by id 
     * @param ids the ids
     * @return the job offers
     */
    @Query(value="SELECT jo.id AS id, jo.jobTitle AS jobTitle, jo.jobShortDescription AS jobShortDescription,"
        + " jo.company.companyName AS companyName, jo.company.id AS companyId FROM JobOffer jo WHERE jo.id IN ?1 ORDER BY jo.id ASC")
	List<JobOfferToPromote> findAllJobOfferToPromoteByIds(List<Long> ids);
	
}
