package it.aranciaict.jobmatch.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.domain.enumeration.DocumentType;

// TODO: Auto-generated Javadoc
/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The document type. */
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    /** The name. */
    @NotNull
    @Size(max = ValidationConstants.SIZE_50)
    @Column(name = "name", length = ValidationConstants.SIZE_50, nullable = false)
    private String name;

    
    /** The content. */
    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    /** The content content type. */
    @Column(name = "content_content_type", nullable = false)
    private String contentContentType;

    /** The description. */
    @Size(max = ValidationConstants.SIZE_255)
    @Column(name = "description", length = ValidationConstants.SIZE_255)
    private String description;

    /** The candidate. */
    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Candidate candidate;

    /**
     * Gets the id.
     *
     * @return the id
     */
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the document type.
     *
     * @return the document type
     */
    public DocumentType getDocumentType() {
        return documentType;
    }

    /**
     * Document type.
     *
     * @param documentType the document type
     * @return the document
     */
    public Document documentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    /**
     * Sets the document type.
     *
     * @param documentType the new document type
     */
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Name.
     *
     * @param name the name
     * @return the document
     */
    public Document name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Content.
     *
     * @param content the content
     * @return the document
     */
    public Document content(byte[] content) {
        this.content = content;
        return this;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * Gets the content content type.
     *
     * @return the content content type
     */
    public String getContentContentType() {
        return contentContentType;
    }

    /**
     * Content content type.
     *
     * @param contentContentType the content content type
     * @return the document
     */
    public Document contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    /**
     * Sets the content content type.
     *
     * @param contentContentType the new content content type
     */
    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description.
     *
     * @param description the description
     * @return the document
     */
    public Document description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the candidate.
     *
     * @return the candidate
     */
    public Candidate getCandidate() {
        return candidate;
    }

    /**
     * Candidate.
     *
     * @param candidate the candidate
     * @return the document
     */
    public Document candidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    /**
     * Sets the candidate.
     *
     * @param candidate the new candidate
     */
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        Document document = (Document) o;
        if (document.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), document.getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
