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
import org.springframework.validation.Validator;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.domain.CompanySector;
import it.aranciaict.jobmatch.repository.CompanySectorRepository;
import it.aranciaict.jobmatch.service.CompanySectorQueryService;
import it.aranciaict.jobmatch.service.CompanySectorService;
import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;
import it.aranciaict.jobmatch.service.mapper.CompanySectorMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

// TODO: Auto-generated Javadoc
/**
 * Test class for the CompanySectorResource REST controller.
 *
 * @see CompanySectorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class CompanySectorResourceIntTest {

	/** The Constant DEFAULT_CODE. */
	private static final String DEFAULT_CODE = "AAAAAAAAAA";
    
    /** The Constant UPDATED_CODE. */
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    /** The Constant DEFAULT_descriptionEN. */
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    
    /** The Constant UPDATED_descriptionEN. */
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    /** The company sector repository. */
    @Autowired
    private CompanySectorRepository companySectorRepository;

    /** The company sector mapper. */
    @Autowired
    private CompanySectorMapper companySectorMapper;

    /** The company sector service. */
    @Autowired
    private CompanySectorService companySectorService;

    /** The company sector query service. */
    @Autowired
    private CompanySectorQueryService companySectorQueryService;

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

    /** The rest company sector mock mvc. */
    private MockMvc restCompanySectorMockMvc;

    /** The company sector. */
    private CompanySector companySector;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanySectorResource companySectorResource = new CompanySectorResource(companySectorService, companySectorQueryService);
        this.restCompanySectorMockMvc = MockMvcBuilders.standaloneSetup(companySectorResource)
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
     * @return the company sector
     */
    public static CompanySector createEntity(EntityManager em) {
        CompanySector companySector = new CompanySector()
            .code(DEFAULT_CODE)
            .descriptionEN(DEFAULT_DESCRIPTION)
        	.descriptionIT(DEFAULT_DESCRIPTION);
        return companySector;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        companySector = createEntity(em);
    }

    /**
     * Creates the company sector.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createCompanySector() throws Exception {
        int databaseSizeBeforeCreate = companySectorRepository.findAll().size();

        // Create the CompanySector
        CompanySectorDTO companySectorDTO = companySectorMapper.toDto(companySector);
        restCompanySectorMockMvc.perform(post("/api/company-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySectorDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanySector in the database
        List<CompanySector> companySectorList = companySectorRepository.findAll();
        assertThat(companySectorList).hasSize(databaseSizeBeforeCreate + 1);
        CompanySector testCompanySector = companySectorList.get(companySectorList.size() - 1);
        assertThat(testCompanySector.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCompanySector.getDescriptionEN()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    /**
     * Creates the company sector with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createCompanySectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companySectorRepository.findAll().size();

        // Create the CompanySector with an existing ID
        companySector.setId(1L);
        CompanySectorDTO companySectorDTO = companySectorMapper.toDto(companySector);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanySectorMockMvc.perform(post("/api/company-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanySector in the database
        List<CompanySector> companySectorList = companySectorRepository.findAll();
        assertThat(companySectorList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Gets the all company sectors.
     *
     *  the all company sectors
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectors() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList
        restCompanySectorMockMvc.perform(get("/api/company-sectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companySector.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION.toString())))
        	.andExpect(jsonPath("$.[*].descriptionIT").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    /**
     * Gets the company sector.
     *
     *  the company sector
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getCompanySector() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get the companySector
        restCompanySectorMockMvc.perform(get("/api/company-sectors/{languageKey}/{id}", "EN",  companySector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companySector.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.descriptionEN").value(DEFAULT_DESCRIPTION.toString()))
        	.andExpect(jsonPath("$.descriptionIT").value(DEFAULT_DESCRIPTION.toString()));
    }


    /**
     * Gets the all company sectors by code is equal to something.
     *
     *  the all company sectors by code is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList where code equals to DEFAULT_CODE
        defaultCompanySectorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the companySectorList where code equals to UPDATED_CODE
        defaultCompanySectorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    /**
     * Gets the all company sectors by code is in should work.
     *
     *  the all company sectors by code is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCompanySectorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the companySectorList where code equals to UPDATED_CODE
        defaultCompanySectorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    /**
     * Gets the all company sectors by code is null or not null.
     *
     *  the all company sectors by code is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList where code is not null
        defaultCompanySectorShouldBeFound("code.specified=true");

        // Get all the companySectorList where code is null
        defaultCompanySectorShouldNotBeFound("code.specified=false");
    }

    /**
     * Gets the all company sectors by description is equal to something.
     *
     *  the all company sectors by description is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectorsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList where description equals to DEFAULT_DESCRIPTION
        defaultCompanySectorShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the companySectorList where description equals to UPDATED_DESCRIPTION
        defaultCompanySectorShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    /**
     * Gets the all company sectors by description is in should work.
     *
     *  the all company sectors by description is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectorsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCompanySectorShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the companySectorList where description equals to UPDATED_DESCRIPTION
        defaultCompanySectorShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    /**
     * Gets the all company sectors by description is null or not null.
     *
     *  the all company sectors by description is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanySectorsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        // Get all the companySectorList where description is not null
        defaultCompanySectorShouldBeFound("description.specified=true");

        // Get all the companySectorList where description is null
        defaultCompanySectorShouldNotBeFound("description.specified=false");
    }
    
    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultCompanySectorShouldBeFound(String filter) throws Exception {
        restCompanySectorMockMvc.perform(get("/api/company-sectors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companySector.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].descriptionEN").value(hasItem(DEFAULT_DESCRIPTION)))
        	.andExpect(jsonPath("$.[*].descriptionIT").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restCompanySectorMockMvc.perform(get("/api/company-sectors/count?sort=id,desc&" + filter))
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
    private void defaultCompanySectorShouldNotBeFound(String filter) throws Exception {
        restCompanySectorMockMvc.perform(get("/api/company-sectors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanySectorMockMvc.perform(get("/api/company-sectors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing company sector.
     *
     *  the non existing company sector
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingCompanySector() throws Exception {
        // Get the companySector
        restCompanySectorMockMvc.perform(get("/api/company-sectors/{languageKey}/{id}", "EN" , Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update company sector.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateCompanySector() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        int databaseSizeBeforeUpdate = companySectorRepository.findAll().size();

        // Update the companySector
        CompanySector updatedCompanySector = companySectorRepository.findById(companySector.getId()).get();
        // Disconnect from session so that the updates on updatedCompanySector are not directly saved in db
        em.detach(updatedCompanySector);
        updatedCompanySector
            .code(UPDATED_CODE)
            .descriptionEN(UPDATED_DESCRIPTION)
            .descriptionIT(UPDATED_DESCRIPTION);
        
        CompanySectorDTO companySectorDTO = companySectorMapper.toDto(updatedCompanySector);

        restCompanySectorMockMvc.perform(put("/api/company-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySectorDTO)))
            .andExpect(status().isOk());

        // Validate the CompanySector in the database
        List<CompanySector> companySectorList = companySectorRepository.findAll();
        assertThat(companySectorList).hasSize(databaseSizeBeforeUpdate);
        CompanySector testCompanySector = companySectorList.get(companySectorList.size() - 1);
        assertThat(testCompanySector.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCompanySector.getDescriptionEN()).isEqualTo(UPDATED_DESCRIPTION);
    }

    /**
     * Update non existing company sector.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingCompanySector() throws Exception {
        int databaseSizeBeforeUpdate = companySectorRepository.findAll().size();

        // Create the CompanySector
        CompanySectorDTO companySectorDTO = companySectorMapper.toDto(companySector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanySectorMockMvc.perform(put("/api/company-sectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companySectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanySector in the database
        List<CompanySector> companySectorList = companySectorRepository.findAll();
        assertThat(companySectorList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete company sector.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteCompanySector() throws Exception {
        // Initialize the database
        companySectorRepository.saveAndFlush(companySector);

        int databaseSizeBeforeDelete = companySectorRepository.findAll().size();

        // Delete the companySector
        restCompanySectorMockMvc.perform(delete("/api/company-sectors/{id}", companySector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanySector> companySectorList = companySectorRepository.findAll();
        assertThat(companySectorList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(CompanySector.class);
        CompanySector companySector1 = new CompanySector();
        companySector1.setId(1L);
        CompanySector companySector2 = new CompanySector();
        companySector2.setId(companySector1.getId());
        assertThat(companySector1).isEqualTo(companySector2);
        companySector2.setId(2L);
        assertThat(companySector1).isNotEqualTo(companySector2);
        companySector1.setId(null);
        assertThat(companySector1).isNotEqualTo(companySector2);
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
        TestUtil.equalsVerifier(CompanySectorDTO.class);
        CompanySectorDTO companySectorDTO1 = new CompanySectorDTO();
        companySectorDTO1.setId(1L);
        CompanySectorDTO companySectorDTO2 = new CompanySectorDTO();
        assertThat(companySectorDTO1).isNotEqualTo(companySectorDTO2);
        companySectorDTO2.setId(companySectorDTO1.getId());
        assertThat(companySectorDTO1).isEqualTo(companySectorDTO2);
        companySectorDTO2.setId(2L);
        assertThat(companySectorDTO1).isNotEqualTo(companySectorDTO2);
        companySectorDTO1.setId(null);
        assertThat(companySectorDTO1).isNotEqualTo(companySectorDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(companySectorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companySectorMapper.fromId(null)).isNull();
    }
}
