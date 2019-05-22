package it.aranciaict.jobmatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.Document;
import it.aranciaict.jobmatch.domain.enumeration.DocumentType;
import it.aranciaict.jobmatch.repository.result.DocumentItem;

/**
 * Spring Data repository for the Document entity.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

	/**
	 * Find all by candidate id.
	 *
	 * @param candidateId the candidate id
	 * @return the list
	 */
	@Query("select d.id as id, d.createdBy as createdBy, d.createdDate as createdDate, d.lastModifiedBy as lastModifiedBy, d.lastModifiedDate as lastModifiedDate, "
			+ "d.documentType as documentType, d.name as name, d.contentContentType as contentContentType, d.description as description, d.candidate.id as candidateId "
			+ " from Document d where d.candidate.id = ?1")
	List<DocumentItem> findAllByCandidateId(Long candidateId);

	/**
	 * find one by candidate id and document type.
	 *
	 * @param candidateId the candidate id
	 * @param documentType the documentType
	 * @return the document
	 */
	Optional<Document> findByCandidateIdAndDocumentType(Long candidateId, DocumentType documentType);

}
