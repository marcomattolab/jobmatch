package it.aranciaict.jobmatch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.Document;
import it.aranciaict.jobmatch.domain.enumeration.DocumentType;
import it.aranciaict.jobmatch.repository.DocumentRepository;
import it.aranciaict.jobmatch.repository.result.DocumentItem;
import it.aranciaict.jobmatch.service.DocumentService;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;
import it.aranciaict.jobmatch.service.mapper.DocumentItemMapper;
import it.aranciaict.jobmatch.service.mapper.DocumentMapper;

/**
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);

	/** The document repository. */
	private final DocumentRepository documentRepository;

	/** The document mapper. */
	private final DocumentMapper documentMapper;

	/** The document Item mapper. */
	private final DocumentItemMapper documentItemMapper;

	/**
	 * Instantiates a new document service impl.
	 *
	 * @param documentRepository the document repository
	 * @param documentMapper     the document mapper
	 * @param documentItemMapper the document item mapper
	 */
	public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper,
			DocumentItemMapper documentItemMapper) {
		this.documentRepository = documentRepository;
		this.documentMapper = documentMapper;
		this.documentItemMapper = documentItemMapper;
	}

	/**
	 * Save a document.
	 *
	 * @param documentDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public DocumentDTO save(DocumentDTO documentDTO) {
		log.debug("Request to save Document : {}", documentDTO);
		Document document = documentMapper.toEntity(documentDTO);
		document = documentRepository.save(document);
		return documentMapper.toDto(document);
	}

	/**
	 * Get all the documents.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DocumentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Documents");
		return documentRepository.findAll(pageable).map(documentMapper::toDto);
	}

	/**
	 * Get one document by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DocumentDTO> findOne(Long id) {
		log.debug("Request to get Document : {}", id);
		return documentRepository.findById(id).map(documentMapper::toDto);
	}

	/**
	 * Delete the document by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Document : {}", id);
		documentRepository.deleteById(id);
	}

	/**
	 * Get all the documents by candidate id.
	 *
	 * @param candidateId the candidate id
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DocumentDTO> findAllByCandidateId(Long candidateId) {
		log.debug("Request to get all Documents by candidate id: {}", candidateId);
		List<DocumentItem> documents = documentRepository.findAllByCandidateId(candidateId);
		return documents.stream().map(documentItemMapper::toDto)
				.collect(Collectors.toList());
	}
	
	/**
	 * Find cv by candidate id.
	 *
	 * @param candidateId the candidate id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DocumentDTO> findCvByCandidateId(Long candidateId) {
		log.debug("Request to get Document fro candiadte with id: {}", candidateId);
		return documentRepository.findByCandidateIdAndDocumentType(candidateId, DocumentType.CV).map(documentMapper::toDto);
	}

}
