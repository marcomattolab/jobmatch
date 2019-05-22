package it.aranciaict.jobmatch.repository.result;

import java.time.Instant;

import it.aranciaict.jobmatch.domain.enumeration.DocumentType;

public interface DocumentItem {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId();

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy();

	/**
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public String getLastModifiedBy();

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Instant getCreatedDate();

	/**
	 * Gets the last modified date.
	 *
	 * @return the last modified date
	 */
	public Instant getLastModifiedDate();

	/**
	 * Gets the document type.
	 *
	 * @return the document type
	 */
	public DocumentType getDocumentType();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Gets the content content type.
	 *
	 * @return the content content type
	 */
	public String getContentContentType();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();

	/**
	 * Gets the candidate id.
	 *
	 * @return the candidate id
	 */
	public Long getCandidateId();

}
