package it.aranciaict.jobmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.AppliedJobIteration;


/**
 * Spring Data  repository for the AppliedJobIteration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppliedJobIterationRepository extends JpaRepository<AppliedJobIteration, Long>, JpaSpecificationExecutor<AppliedJobIteration> {

}
