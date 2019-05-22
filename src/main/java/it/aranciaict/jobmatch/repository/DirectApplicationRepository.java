package it.aranciaict.jobmatch.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.DirectApplication;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;

/**
 * Spring Data repository for the DirectApplication entity.
 */
@Repository
public interface DirectApplicationRepository
		extends JpaRepository<DirectApplication, Long>, JpaSpecificationExecutor<DirectApplication> {

	/**
	 * Find all by status equals and created date GreaterThan.
	 *
	 * @param status the status
	 * @param createdDate the created date
	 * @return the list
	 */
	List<DirectApplication> findAllByAppiedJobStatusEqualsAndCreatedDateGreaterThan(AppiedJobStatus status, Instant createdDate);

}
