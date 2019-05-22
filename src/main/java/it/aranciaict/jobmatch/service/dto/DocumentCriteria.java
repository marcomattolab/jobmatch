package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import it.aranciaict.jobmatch.domain.enumeration.DocumentType;

/**
 * Criteria class for the Document entity. This class is used in DocumentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /documents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@SuppressWarnings("serial")
public class DocumentCriteria implements Serializable {
    
    /**
     * Class for filtering DocumentType.
     */
    public static class DocumentTypeFilter extends Filter<DocumentType> {
    }

    /** The id. */
    private LongFilter id;

    /** The created by. */
    private StringFilter createdBy;

    /** The last modified by. */
    private StringFilter lastModifiedBy;

    /** The created date. */
    private InstantFilter createdDate;

    /** The last modified date. */
    private InstantFilter lastModifiedDate;

    /** The document type. */
    private DocumentTypeFilter documentType;

    /** The name. */
    private StringFilter name;

    /** The description. */
    private StringFilter description;

    /** The candidate id. */
    private LongFilter candidateId;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public LongFilter getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(LongFilter id) {
        this.id = id;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public StringFilter getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last modified by.
     *
     * @return the last modified by
     */
    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the last modified by.
     *
     * @param lastModifiedBy the new last modified by
     */
    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date
     */
    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the last modified date.
     *
     * @param lastModifiedDate the new last modified date
     */
    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Gets the document type.
     *
     * @return the document type
     */
    public DocumentTypeFilter getDocumentType() {
        return documentType;
    }

    /**
     * Sets the document type.
     *
     * @param documentType the new document type
     */
    public void setDocumentType(DocumentTypeFilter documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public StringFilter getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(StringFilter name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public StringFilter getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(StringFilter description) {
        this.description = description;
    }

    /**
     * Gets the candidate id.
     *
     * @return the candidate id
     */
    public LongFilter getCandidateId() {
        return candidateId;
    }

    /**
     * Sets the candidate id.
     *
     * @param candidateId the new candidate id
     */
    public void setCandidateId(LongFilter candidateId) {
        this.candidateId = candidateId;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentCriteria that = (DocumentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(documentType, that.documentType) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(candidateId, that.candidateId);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        lastModifiedBy,
        createdDate,
        lastModifiedDate,
        documentType,
        name,
        description,
        candidateId
        );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DocumentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (documentType != null ? "documentType=" + documentType + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (candidateId != null ? "candidateId=" + candidateId + ", " : "") +
            "}";
    }

}
