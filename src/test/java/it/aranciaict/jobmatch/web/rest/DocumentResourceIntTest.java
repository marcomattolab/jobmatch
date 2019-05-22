package it.aranciaict.jobmatch.web.rest;

import static it.aranciaict.jobmatch.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.Document;
import it.aranciaict.jobmatch.domain.enumeration.DocumentType;
import it.aranciaict.jobmatch.repository.DocumentRepository;
import it.aranciaict.jobmatch.service.DocumentQueryService;
import it.aranciaict.jobmatch.service.DocumentService;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;
import it.aranciaict.jobmatch.service.mapper.DocumentMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the DocumentResource REST controller.
 *
 * @see DocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class DocumentResourceIntTest {

    /** The Constant DEFAULT_DOCUMENT_TYPE. */
    private static final DocumentType DEFAULT_DOCUMENT_TYPE = DocumentType.CV;
    
    /** The Constant UPDATED_DOCUMENT_TYPE. */
    private static final DocumentType UPDATED_DOCUMENT_TYPE = DocumentType.OTHER;

    /** The Constant DEFAULT_NAME. */
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    
    /** The Constant UPDATED_NAME. */
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    /** The Constant DEFAULT_CONTENT. */
    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    
    /** The Constant UPDATED_CONTENT. */
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    
    /** The Constant DEFAULT_CONTENT_CONTENT_TYPE. */
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    
    /** The Constant UPDATED_CONTENT_CONTENT_TYPE. */
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    /** The Constant DEFAULT_DESCRIPTION. */
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    
    /** The Constant UPDATED_DESCRIPTION. */
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    /** The document repository. */
    @Autowired
    private DocumentRepository documentRepository;

    /** The document mapper. */
    @Autowired
    private DocumentMapper documentMapper;

    /** The document service. */
    @Autowired
    private DocumentService documentService;

    /** The document query service. */
    @Autowired
    private DocumentQueryService documentQueryService;

    /** The jackson message converter. */
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    /** The pageable argument resolver. */
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    /** The exception translator. */
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    /** The em. */
    @Autowired
    private EntityManager em;

    /** The validator. */
    @Autowired
    private Validator validator;

    /** The rest document mock mvc. */
    private MockMvc restDocumentMockMvc;

    /** The document. */
    private Document document;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentResource documentResource = new DocumentResource(documentService, documentQueryService);
        this.restDocumentMockMvc = MockMvcBuilders.standaloneSetup(documentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * 
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *
     * @param em the em
     * @return the document
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return document;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        document = createEntity(em);
    }

    /**
     * Creates the document.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDocument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocument.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDocument.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    /**
     * Creates the document with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // Create the Document with an existing ID
        document.setId(1L);
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Check name is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentRepository.findAll().size();
        // set the field null
        document.setName(null);

        // Create the Document, which fails.
        DocumentDTO documentDTO = documentMapper.toDto(document);

        restDocumentMockMvc.perform(post("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Gets the all documents.
     *
     *  the all documents
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    /**
     * Gets the document.
     *
     *  the document
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

   
    /**
     * Gets the all documents by document type is equal to something.
     *
     *  the all documents by document type is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentType equals to DEFAULT_DOCUMENT_TYPE
        defaultDocumentShouldBeFound("documentType.equals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the documentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultDocumentShouldNotBeFound("documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    /**
     * Gets the all documents by document type is in should work.
     *
     *  the all documents by document type is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentType in DEFAULT_DOCUMENT_TYPE or UPDATED_DOCUMENT_TYPE
        defaultDocumentShouldBeFound("documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE);

        // Get all the documentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultDocumentShouldNotBeFound("documentType.in=" + UPDATED_DOCUMENT_TYPE);
    }

    /**
     * Gets the all documents by document type is null or not null.
     *
     *  the all documents by document type is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where documentType is not null
        defaultDocumentShouldBeFound("documentType.specified=true");

        // Get all the documentList where documentType is null
        defaultDocumentShouldNotBeFound("documentType.specified=false");
    }

    /**
     * Gets the all documents by name is equal to something.
     *
     *  the all documents by name is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where name equals to DEFAULT_NAME
        defaultDocumentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the documentList where name equals to UPDATED_NAME
        defaultDocumentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    /**
     * Gets the all documents by name is in should work.
     *
     *  the all documents by name is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDocumentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the documentList where name equals to UPDATED_NAME
        defaultDocumentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    /**
     * Gets the all documents by name is null or not null.
     *
     *  the all documents by name is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where name is not null
        defaultDocumentShouldBeFound("name.specified=true");

        // Get all the documentList where name is null
        defaultDocumentShouldNotBeFound("name.specified=false");
    }

    /**
     * Gets the all documents by description is equal to something.
     *
     *  the all documents by description is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where description equals to DEFAULT_DESCRIPTION
        defaultDocumentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the documentList where description equals to UPDATED_DESCRIPTION
        defaultDocumentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    /**
     * Gets the all documents by description is in should work.
     *
     *  the all documents by description is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDocumentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the documentList where description equals to UPDATED_DESCRIPTION
        defaultDocumentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    /**
     * Gets the all documents by description is null or not null.
     *
     *  the all documents by description is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList where description is not null
        defaultDocumentShouldBeFound("description.specified=true");

        // Get all the documentList where description is null
        defaultDocumentShouldNotBeFound("description.specified=false");
    }

    /**
     * Gets the all documents by candidate is equal to something.
     *
     *  the all documents by candidate is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllDocumentsByCandidateIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidate candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
        document.setCandidate(candidate);
        documentRepository.saveAndFlush(document);
        Long candidateId = candidate.getId();

        // Get all the documentList where candidate equals to candidateId
        defaultDocumentShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the documentList where candidate equals to candidateId + 1
        defaultDocumentShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultDocumentShouldBeFound(String filter) throws Exception {
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restDocumentMockMvc.perform(get("/api/documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultDocumentShouldNotBeFound(String filter) throws Exception {
        restDocumentMockMvc.perform(get("/api/documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentMockMvc.perform(get("/api/documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing document.
     *
     *  the non existing document
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get("/api/documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update document.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        DocumentDTO documentDTO = documentMapper.toDto(updatedDocument);

        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocument.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocument.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    /**
     * Update non existing document.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Create the Document
        DocumentDTO documentDTO = documentMapper.toDto(document);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc.perform(put("/api/documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete document.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc.perform(delete("/api/documents/{id}", document.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    /**
     * Equals verifier.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = new Document();
        document1.setId(1L);
        Document document2 = new Document();
        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);
        document2.setId(2L);
        assertThat(document1).isNotEqualTo(document2);
        document1.setId(null);
        assertThat(document1).isNotEqualTo(document2);
    }

    /**
     * Dto equals verifier.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentDTO.class);
        DocumentDTO documentDTO1 = new DocumentDTO();
        documentDTO1.setId(1L);
        DocumentDTO documentDTO2 = new DocumentDTO();
        assertThat(documentDTO1).isNotEqualTo(documentDTO2);
        documentDTO2.setId(documentDTO1.getId());
        assertThat(documentDTO1).isEqualTo(documentDTO2);
        documentDTO2.setId(2L);
        assertThat(documentDTO1).isNotEqualTo(documentDTO2);
        documentDTO1.setId(null);
        assertThat(documentDTO1).isNotEqualTo(documentDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(documentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentMapper.fromId(null)).isNull();
    }
}
