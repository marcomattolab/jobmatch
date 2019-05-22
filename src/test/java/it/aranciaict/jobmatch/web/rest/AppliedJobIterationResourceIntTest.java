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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.domain.AppliedJobIteration;
import it.aranciaict.jobmatch.repository.AppliedJobIterationRepository;
import it.aranciaict.jobmatch.service.AppliedJobIterationQueryService;
import it.aranciaict.jobmatch.service.AppliedJobIterationService;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationDTO;
import it.aranciaict.jobmatch.service.mapper.AppliedJobIterationMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

// TODO: Auto-generated Javadoc
/**
 * Test class for the AppliedJobIterationResource REST controller.
 *
 * @see AppliedJobIterationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class AppliedJobIterationResourceIntTest {


    /** The Constant DEFAULT_ITERATION_DATE. */
    private static final Instant DEFAULT_ITERATION_DATE = Instant.ofEpochMilli(0L);
    
    /** The Constant UPDATED_ITERATION_DATE. */
    private static final Instant UPDATED_ITERATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    /** The Constant DEFAULT_ITERATION_TYPE. */
    private static final String DEFAULT_ITERATION_TYPE = "AAAAAAAAAA";
    
    /** The Constant UPDATED_ITERATION_TYPE. */
    private static final String UPDATED_ITERATION_TYPE = "BBBBBBBBBB";

    /** The Constant DEFAULT_ITERATION_NOTE. */
    private static final String DEFAULT_ITERATION_NOTE = "AAAAAAAAAA";
    
    /** The Constant UPDATED_ITERATION_NOTE. */
    private static final String UPDATED_ITERATION_NOTE = "BBBBBBBBBB";

    /** The applied job iteration repository. */
    @Autowired
    private AppliedJobIterationRepository appliedJobIterationRepository;

    /** The applied job iteration mapper. */
    @Autowired
    private AppliedJobIterationMapper appliedJobIterationMapper;

    /** The applied job iteration service. */
    @Autowired
    private AppliedJobIterationService appliedJobIterationService;

    /** The applied job iteration query service. */
    @Autowired
    private AppliedJobIterationQueryService appliedJobIterationQueryService;

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

    /** The rest applied job iteration mock mvc. */
    private MockMvc restAppliedJobIterationMockMvc;

    /** The applied job iteration. */
    private AppliedJobIteration appliedJobIteration;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppliedJobIterationResource appliedJobIterationResource = new AppliedJobIterationResource(appliedJobIterationService, appliedJobIterationQueryService);
        this.restAppliedJobIterationMockMvc = MockMvcBuilders.standaloneSetup(appliedJobIterationResource)
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
     * @return the applied job iteration
     */
    public static AppliedJobIteration createEntity(EntityManager em) {
        AppliedJobIteration appliedJobIteration = new AppliedJobIteration()
            .iterationDate(DEFAULT_ITERATION_DATE)
            .iterationType(DEFAULT_ITERATION_TYPE)
            .iterationNote(DEFAULT_ITERATION_NOTE);
        return appliedJobIteration;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        appliedJobIteration = createEntity(em);
    }

    /**
     * Creates the applied job iteration.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createAppliedJobIteration() throws Exception {
        int databaseSizeBeforeCreate = appliedJobIterationRepository.findAll().size();

        // Create the AppliedJobIteration
        AppliedJobIterationDTO appliedJobIterationDTO = appliedJobIterationMapper.toDto(appliedJobIteration);
        restAppliedJobIterationMockMvc.perform(post("/api/applied-job-iterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobIterationDTO)))
            .andExpect(status().isCreated());

        // Validate the AppliedJobIteration in the database
        List<AppliedJobIteration> appliedJobIterationList = appliedJobIterationRepository.findAll();
        assertThat(appliedJobIterationList).hasSize(databaseSizeBeforeCreate + 1);
        AppliedJobIteration testAppliedJobIteration = appliedJobIterationList.get(appliedJobIterationList.size() - 1);
        assertThat(testAppliedJobIteration.getIterationDate()).isEqualTo(DEFAULT_ITERATION_DATE);
        assertThat(testAppliedJobIteration.getIterationType()).isEqualTo(DEFAULT_ITERATION_TYPE);
        assertThat(testAppliedJobIteration.getIterationNote()).isEqualTo(DEFAULT_ITERATION_NOTE);
    }

    /**
     * Creates the applied job iteration with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createAppliedJobIterationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appliedJobIterationRepository.findAll().size();

        // Create the AppliedJobIteration with an existing ID
        appliedJobIteration.setId(1L);
        AppliedJobIterationDTO appliedJobIterationDTO = appliedJobIterationMapper.toDto(appliedJobIteration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppliedJobIterationMockMvc.perform(post("/api/applied-job-iterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobIterationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppliedJobIteration in the database
        List<AppliedJobIteration> appliedJobIterationList = appliedJobIterationRepository.findAll();
        assertThat(appliedJobIterationList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Gets the all applied job iterations.
     *
     *  the all applied job iterations
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterations() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appliedJobIteration.getId().intValue())))
            .andExpect(jsonPath("$.[*].iterationDate").value(hasItem(DEFAULT_ITERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].iterationType").value(hasItem(DEFAULT_ITERATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].iterationNote").value(hasItem(DEFAULT_ITERATION_NOTE.toString())));
    }
    
    /**
     * Gets the applied job iteration.
     *
     *  the applied job iteration
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAppliedJobIteration() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get the appliedJobIteration
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations/{id}", appliedJobIteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appliedJobIteration.getId().intValue()))
            .andExpect(jsonPath("$.iterationDate").value(DEFAULT_ITERATION_DATE.toString()))
            .andExpect(jsonPath("$.iterationType").value(DEFAULT_ITERATION_TYPE.toString()))
            .andExpect(jsonPath("$.iterationNote").value(DEFAULT_ITERATION_NOTE.toString()));
    }


    /**
     * Gets the all applied job iterations by iteration date is equal to something.
     *
     *  the all applied job iterations by iteration date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByIterationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList where iterationDate equals to DEFAULT_ITERATION_DATE
        defaultAppliedJobIterationShouldBeFound("iterationDate.equals=" + DEFAULT_ITERATION_DATE);

        // Get all the appliedJobIterationList where iterationDate equals to UPDATED_ITERATION_DATE
        defaultAppliedJobIterationShouldNotBeFound("iterationDate.equals=" + UPDATED_ITERATION_DATE);
    }

    /**
     * Gets the all applied job iterations by iteration date is in should work.
     *
     *  the all applied job iterations by iteration date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByIterationDateIsInShouldWork() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList where iterationDate in DEFAULT_ITERATION_DATE or UPDATED_ITERATION_DATE
        defaultAppliedJobIterationShouldBeFound("iterationDate.in=" + DEFAULT_ITERATION_DATE + "," + UPDATED_ITERATION_DATE);

        // Get all the appliedJobIterationList where iterationDate equals to UPDATED_ITERATION_DATE
        defaultAppliedJobIterationShouldNotBeFound("iterationDate.in=" + UPDATED_ITERATION_DATE);
    }

    /**
     * Gets the all applied job iterations by iteration date is null or not null.
     *
     *  the all applied job iterations by iteration date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByIterationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList where iterationDate is not null
        defaultAppliedJobIterationShouldBeFound("iterationDate.specified=true");

        // Get all the appliedJobIterationList where iterationDate is null
        defaultAppliedJobIterationShouldNotBeFound("iterationDate.specified=false");
    }

    /**
     * Gets the all applied job iterations by iteration type is equal to something.
     *
     *  the all applied job iterations by iteration type is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByIterationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList where iterationType equals to DEFAULT_ITERATION_TYPE
        defaultAppliedJobIterationShouldBeFound("iterationType.equals=" + DEFAULT_ITERATION_TYPE);

        // Get all the appliedJobIterationList where iterationType equals to UPDATED_ITERATION_TYPE
        defaultAppliedJobIterationShouldNotBeFound("iterationType.equals=" + UPDATED_ITERATION_TYPE);
    }

    /**
     * Gets the all applied job iterations by iteration type is in should work.
     *
     *  the all applied job iterations by iteration type is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByIterationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList where iterationType in DEFAULT_ITERATION_TYPE or UPDATED_ITERATION_TYPE
        defaultAppliedJobIterationShouldBeFound("iterationType.in=" + DEFAULT_ITERATION_TYPE + "," + UPDATED_ITERATION_TYPE);

        // Get all the appliedJobIterationList where iterationType equals to UPDATED_ITERATION_TYPE
        defaultAppliedJobIterationShouldNotBeFound("iterationType.in=" + UPDATED_ITERATION_TYPE);
    }

    /**
     * Gets the all applied job iterations by iteration type is null or not null.
     *
     *  the all applied job iterations by iteration type is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByIterationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        // Get all the appliedJobIterationList where iterationType is not null
        defaultAppliedJobIterationShouldBeFound("iterationType.specified=true");

        // Get all the appliedJobIterationList where iterationType is null
        defaultAppliedJobIterationShouldNotBeFound("iterationType.specified=false");
    }

    /**
     * Gets the all applied job iterations by applied job is equal to something.
     *
     *  the all applied job iterations by applied job is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobIterationsByAppliedJobIsEqualToSomething() throws Exception {
        // Initialize the database
        AppliedJob appliedJob = AppliedJobResourceIntTest.createEntity(em);
        em.persist(appliedJob);
        em.flush();
        appliedJobIteration.setAppliedJob(appliedJob);
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);
        Long appliedJobId = appliedJob.getId();

        // Get all the appliedJobIterationList where appliedJob equals to appliedJobId
        defaultAppliedJobIterationShouldBeFound("appliedJobId.equals=" + appliedJobId);

        // Get all the appliedJobIterationList where appliedJob equals to appliedJobId + 1
        defaultAppliedJobIterationShouldNotBeFound("appliedJobId.equals=" + (appliedJobId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultAppliedJobIterationShouldBeFound(String filter) throws Exception {
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appliedJobIteration.getId().intValue())))
            .andExpect(jsonPath("$.[*].iterationDate").value(hasItem(DEFAULT_ITERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].iterationType").value(hasItem(DEFAULT_ITERATION_TYPE)))
            .andExpect(jsonPath("$.[*].iterationNote").value(hasItem(DEFAULT_ITERATION_NOTE.toString())));

        // Check, that the count call also returns 1
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations/count?sort=id,desc&" + filter))
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
    private void defaultAppliedJobIterationShouldNotBeFound(String filter) throws Exception {
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing applied job iteration.
     *
     *  the non existing applied job iteration
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingAppliedJobIteration() throws Exception {
        // Get the appliedJobIteration
        restAppliedJobIterationMockMvc.perform(get("/api/applied-job-iterations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update applied job iteration.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateAppliedJobIteration() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        int databaseSizeBeforeUpdate = appliedJobIterationRepository.findAll().size();

        // Update the appliedJobIteration
        AppliedJobIteration updatedAppliedJobIteration = appliedJobIterationRepository.findById(appliedJobIteration.getId()).get();
        // Disconnect from session so that the updates on updatedAppliedJobIteration are not directly saved in db
        em.detach(updatedAppliedJobIteration);
        updatedAppliedJobIteration
            .iterationDate(UPDATED_ITERATION_DATE)
            .iterationType(UPDATED_ITERATION_TYPE)
            .iterationNote(UPDATED_ITERATION_NOTE);
        AppliedJobIterationDTO appliedJobIterationDTO = appliedJobIterationMapper.toDto(updatedAppliedJobIteration);

        restAppliedJobIterationMockMvc.perform(put("/api/applied-job-iterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobIterationDTO)))
            .andExpect(status().isOk());

        // Validate the AppliedJobIteration in the database
        List<AppliedJobIteration> appliedJobIterationList = appliedJobIterationRepository.findAll();
        assertThat(appliedJobIterationList).hasSize(databaseSizeBeforeUpdate);
        AppliedJobIteration testAppliedJobIteration = appliedJobIterationList.get(appliedJobIterationList.size() - 1);
        assertThat(testAppliedJobIteration.getIterationDate()).isEqualTo(UPDATED_ITERATION_DATE);
        assertThat(testAppliedJobIteration.getIterationType()).isEqualTo(UPDATED_ITERATION_TYPE);
        assertThat(testAppliedJobIteration.getIterationNote()).isEqualTo(UPDATED_ITERATION_NOTE);
    }

    /**
     * Update non existing applied job iteration.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingAppliedJobIteration() throws Exception {
        int databaseSizeBeforeUpdate = appliedJobIterationRepository.findAll().size();

        // Create the AppliedJobIteration
        AppliedJobIterationDTO appliedJobIterationDTO = appliedJobIterationMapper.toDto(appliedJobIteration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppliedJobIterationMockMvc.perform(put("/api/applied-job-iterations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobIterationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppliedJobIteration in the database
        List<AppliedJobIteration> appliedJobIterationList = appliedJobIterationRepository.findAll();
        assertThat(appliedJobIterationList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete applied job iteration.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteAppliedJobIteration() throws Exception {
        // Initialize the database
        appliedJobIterationRepository.saveAndFlush(appliedJobIteration);

        int databaseSizeBeforeDelete = appliedJobIterationRepository.findAll().size();

        // Delete the appliedJobIteration
        restAppliedJobIterationMockMvc.perform(delete("/api/applied-job-iterations/{id}", appliedJobIteration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppliedJobIteration> appliedJobIterationList = appliedJobIterationRepository.findAll();
        assertThat(appliedJobIterationList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(AppliedJobIteration.class);
        AppliedJobIteration appliedJobIteration1 = new AppliedJobIteration();
        appliedJobIteration1.setId(1L);
        AppliedJobIteration appliedJobIteration2 = new AppliedJobIteration();
        appliedJobIteration2.setId(appliedJobIteration1.getId());
        assertThat(appliedJobIteration1).isEqualTo(appliedJobIteration2);
        appliedJobIteration2.setId(2L);
        assertThat(appliedJobIteration1).isNotEqualTo(appliedJobIteration2);
        appliedJobIteration1.setId(null);
        assertThat(appliedJobIteration1).isNotEqualTo(appliedJobIteration2);
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
        TestUtil.equalsVerifier(AppliedJobIterationDTO.class);
        AppliedJobIterationDTO appliedJobIterationDTO1 = new AppliedJobIterationDTO();
        appliedJobIterationDTO1.setId(1L);
        AppliedJobIterationDTO appliedJobIterationDTO2 = new AppliedJobIterationDTO();
        assertThat(appliedJobIterationDTO1).isNotEqualTo(appliedJobIterationDTO2);
        appliedJobIterationDTO2.setId(appliedJobIterationDTO1.getId());
        assertThat(appliedJobIterationDTO1).isEqualTo(appliedJobIterationDTO2);
        appliedJobIterationDTO2.setId(2L);
        assertThat(appliedJobIterationDTO1).isNotEqualTo(appliedJobIterationDTO2);
        appliedJobIterationDTO1.setId(null);
        assertThat(appliedJobIterationDTO1).isNotEqualTo(appliedJobIterationDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(appliedJobIterationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appliedJobIterationMapper.fromId(null)).isNull();
    }
}
