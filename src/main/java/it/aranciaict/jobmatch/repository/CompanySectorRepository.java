package it.aranciaict.jobmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.CompanySector;


/**
 * Spring Data  repository for the CompanySector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanySectorRepository extends JpaRepository<CompanySector, Long>, JpaSpecificationExecutor<CompanySector> {

}
