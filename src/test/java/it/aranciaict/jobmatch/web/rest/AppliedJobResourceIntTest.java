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
import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.domain.AppliedJobIteration;
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.enumeration.AppliedJobFeedback;
import it.aranciaict.jobmatch.repository.AppliedJobRepository;
import it.aranciaict.jobmatch.service.AppliedJobQueryService;
import it.aranciaict.jobmatch.service.AppliedJobService;
import it.aranciaict.jobmatch.service.dto.AppliedJobDTO;
import it.aranciaict.jobmatch.service.mapper.AppliedJobMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the AppliedJobResource REST controller.
 *
 * @see AppliedJobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class AppliedJobResourceIntTest {

    /** The Constant DEFAULT_APPLIED_JOB_FEEDBACK. */
    private static final AppliedJobFeedback DEFAULT_APPLIED_JOB_FEEDBACK = AppliedJobFeedback.INSUFFICIENT;
    
    /** The Constant UPDATED_APPLIED_JOB_FEEDBACK. */
    private static final AppliedJobFeedback UPDATED_APPLIED_JOB_FEEDBACK = AppliedJobFeedback.FAIRLY_GOOD;

    /** The applied job repository. */
    @Autowired
    private AppliedJobRepository appliedJobRepository;

    /** The applied job mapper. */
    @Autowired
    private AppliedJobMapper appliedJobMapper;

    /** The applied job service. */
    @Autowired
    private AppliedJobService appliedJobService;

    /** The applied job query service. */
    @Autowired
    private AppliedJobQueryService appliedJobQueryService;

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

    /** The rest applied job mock mvc. */
    private MockMvc restAppliedJobMockMvc;

    /** The applied job. */
    private AppliedJob appliedJob;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppliedJobResource appliedJobResource = new AppliedJobResource(appliedJobService, appliedJobQueryService);
        this.restAppliedJobMockMvc = MockMvcBuilders.standaloneSetup(appliedJobResource)
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
     * @return the applied job
     */
    public static AppliedJob createEntity(EntityManager em) {
        AppliedJob appliedJob = new AppliedJob()
            .appliedJobFeedback(DEFAULT_APPLIED_JOB_FEEDBACK);
        return appliedJob;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        appliedJob = createEntity(em);
    }

    /**
     * Creates the applied job.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createAppliedJob() throws Exception {
        int databaseSizeBeforeCreate = appliedJobRepository.findAll().size();

        // Create the AppliedJob
        AppliedJobDTO appliedJobDTO = appliedJobMapper.toDto(appliedJob);
        restAppliedJobMockMvc.perform(post("/api/applied-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobDTO)))
            .andExpect(status().isCreated());

        // Validate the AppliedJob in the database
        List<AppliedJob> appliedJobList = appliedJobRepository.findAll();
        assertThat(appliedJobList).hasSize(databaseSizeBeforeCreate + 1);
        AppliedJob testAppliedJob = appliedJobList.get(appliedJobList.size() - 1);
        assertThat(testAppliedJob.getAppliedJobFeedback()).isEqualTo(DEFAULT_APPLIED_JOB_FEEDBACK);
    }

    /**
     * Creates the applied job with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createAppliedJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appliedJobRepository.findAll().size();

        // Create the AppliedJob with an existing ID
        appliedJob.setId(1L);
        AppliedJobDTO appliedJobDTO = appliedJobMapper.toDto(appliedJob);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppliedJobMockMvc.perform(post("/api/applied-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppliedJob in the database
        List<AppliedJob> appliedJobList = appliedJobRepository.findAll();
        assertThat(appliedJobList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Gets the all applied jobs.
     *
     *  the all applied jobs
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobs() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        // Get all the appliedJobList
        restAppliedJobMockMvc.perform(get("/api/applied-jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appliedJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].appliedJobFeedback").value(hasItem(DEFAULT_APPLIED_JOB_FEEDBACK.toString())));
    }
    
    /**
     * Gets the applied job.
     *
     *  the applied job
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAppliedJob() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        // Get the appliedJob
        restAppliedJobMockMvc.perform(get("/api/applied-jobs/{id}", appliedJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appliedJob.getId().intValue()))
            .andExpect(jsonPath("$.appliedJobFeedback").value(DEFAULT_APPLIED_JOB_FEEDBACK.toString()));
    }

    /**
     * Gets the all applied jobs by applied job feedback is equal to something.
     *
     *  the all applied jobs by applied job feedback is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobsByAppliedJobFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        // Get all the appliedJobList where appliedJobFeedback equals to DEFAULT_APPLIED_JOB_FEEDBACK
        defaultAppliedJobShouldBeFound("appliedJobFeedback.equals=" + DEFAULT_APPLIED_JOB_FEEDBACK);

        // Get all the appliedJobList where appliedJobFeedback equals to UPDATED_APPLIED_JOB_FEEDBACK
        defaultAppliedJobShouldNotBeFound("appliedJobFeedback.equals=" + UPDATED_APPLIED_JOB_FEEDBACK);
    }

    /**
     * Gets the all applied jobs by applied job feedback is in should work.
     *
     *  the all applied jobs by applied job feedback is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobsByAppliedJobFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        // Get all the appliedJobList where appliedJobFeedback in DEFAULT_APPLIED_JOB_FEEDBACK or UPDATED_APPLIED_JOB_FEEDBACK
        defaultAppliedJobShouldBeFound("appliedJobFeedback.in=" + DEFAULT_APPLIED_JOB_FEEDBACK + "," + UPDATED_APPLIED_JOB_FEEDBACK);

        // Get all the appliedJobList where appliedJobFeedback equals to UPDATED_APPLIED_JOB_FEEDBACK
        defaultAppliedJobShouldNotBeFound("appliedJobFeedback.in=" + UPDATED_APPLIED_JOB_FEEDBACK);
    }

    /**
     * Gets the all applied jobs by applied job feedback is null or not null.
     *
     *  the all applied jobs by applied job feedback is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobsByAppliedJobFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        // Get all the appliedJobList where appliedJobFeedback is not null
        defaultAppliedJobShouldBeFound("appliedJobFeedback.specified=true");

        // Get all the appliedJobList where appliedJobFeedback is null
        defaultAppliedJobShouldNotBeFound("appliedJobFeedback.specified=false");
    }

    /**
     * Gets the all applied jobs by applied job iteration is equal to something.
     *
     *  the all applied jobs by applied job iteration is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobsByAppliedJobIterationIsEqualToSomething() throws Exception {
        // Initialize the database
        AppliedJobIteration appliedJobIteration = AppliedJobIterationResourceIntTest.createEntity(em);
        em.persist(appliedJobIteration);
        em.flush();
        appliedJob.addAppliedJobIteration(appliedJobIteration);
        appliedJobRepository.saveAndFlush(appliedJob);
        Long appliedJobIterationId = appliedJobIteration.getId();

        // Get all the appliedJobList where appliedJobIteration equals to appliedJobIterationId
        defaultAppliedJobShouldBeFound("appliedJobIterationId.equals=" + appliedJobIterationId);

        // Get all the appliedJobList where appliedJobIteration equals to appliedJobIterationId + 1
        defaultAppliedJobShouldNotBeFound("appliedJobIterationId.equals=" + (appliedJobIterationId + 1));
    }


    /**
     * Gets the all applied jobs by candidate is equal to something.
     *
     *  the all applied jobs by candidate is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobsByCandidateIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidate candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
        appliedJob.setCandidate(candidate);
        appliedJobRepository.saveAndFlush(appliedJob);
        Long candidateId = candidate.getId();

        // Get all the appliedJobList where candidate equals to candidateId
        defaultAppliedJobShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the appliedJobList where candidate equals to candidateId + 1
        defaultAppliedJobShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }


    /**
     * Gets the all applied jobs by job offer is equal to something.
     *
     *  the all applied jobs by job offer is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllAppliedJobsByJobOfferIsEqualToSomething() throws Exception {
        // Initialize the database
        JobOffer jobOffer = JobOfferResourceIntTest.createEntity(em);
        em.persist(jobOffer);
        em.flush();
        appliedJob.setJobOffer(jobOffer);
        appliedJobRepository.saveAndFlush(appliedJob);
        Long jobOfferId = jobOffer.getId();

        // Get all the appliedJobList where jobOffer equals to jobOfferId
        defaultAppliedJobShouldBeFound("jobOfferId.equals=" + jobOfferId);

        // Get all the appliedJobList where jobOffer equals to jobOfferId + 1
        defaultAppliedJobShouldNotBeFound("jobOfferId.equals=" + (jobOfferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultAppliedJobShouldBeFound(String filter) throws Exception {
        restAppliedJobMockMvc.perform(get("/api/applied-jobs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appliedJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].appliedJobFeedback").value(hasItem(DEFAULT_APPLIED_JOB_FEEDBACK.toString())));

        // Check, that the count call also returns 1
        restAppliedJobMockMvc.perform(get("/api/applied-jobs/count?sort=id,desc&" + filter))
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
    private void defaultAppliedJobShouldNotBeFound(String filter) throws Exception {
        restAppliedJobMockMvc.perform(get("/api/applied-jobs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppliedJobMockMvc.perform(get("/api/applied-jobs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing applied job.
     *
     *  the non existing applied job
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingAppliedJob() throws Exception {
        // Get the appliedJob
        restAppliedJobMockMvc.perform(get("/api/applied-jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update applied job.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateAppliedJob() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        int databaseSizeBeforeUpdate = appliedJobRepository.findAll().size();

        // Update the appliedJob
        AppliedJob updatedAppliedJob = appliedJobRepository.findById(appliedJob.getId()).get();
        // Disconnect from session so that the updates on updatedAppliedJob are not directly saved in db
        em.detach(updatedAppliedJob);
        updatedAppliedJob
            .appliedJobFeedback(UPDATED_APPLIED_JOB_FEEDBACK);
        AppliedJobDTO appliedJobDTO = appliedJobMapper.toDto(updatedAppliedJob);

        restAppliedJobMockMvc.perform(put("/api/applied-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobDTO)))
            .andExpect(status().isOk());

        // Validate the AppliedJob in the database
        List<AppliedJob> appliedJobList = appliedJobRepository.findAll();
        assertThat(appliedJobList).hasSize(databaseSizeBeforeUpdate);
        AppliedJob testAppliedJob = appliedJobList.get(appliedJobList.size() - 1);
        assertThat(testAppliedJob.getAppliedJobFeedback()).isEqualTo(UPDATED_APPLIED_JOB_FEEDBACK);
    }

    /**
     * Update non existing applied job.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingAppliedJob() throws Exception {
        int databaseSizeBeforeUpdate = appliedJobRepository.findAll().size();

        // Create the AppliedJob
        AppliedJobDTO appliedJobDTO = appliedJobMapper.toDto(appliedJob);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppliedJobMockMvc.perform(put("/api/applied-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appliedJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppliedJob in the database
        List<AppliedJob> appliedJobList = appliedJobRepository.findAll();
        assertThat(appliedJobList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete applied job.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteAppliedJob() throws Exception {
        // Initialize the database
        appliedJobRepository.saveAndFlush(appliedJob);

        int databaseSizeBeforeDelete = appliedJobRepository.findAll().size();

        // Delete the appliedJob
        restAppliedJobMockMvc.perform(delete("/api/applied-jobs/{id}", appliedJob.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppliedJob> appliedJobList = appliedJobRepository.findAll();
        assertThat(appliedJobList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(AppliedJob.class);
        AppliedJob appliedJob1 = new AppliedJob();
        appliedJob1.setId(1L);
        AppliedJob appliedJob2 = new AppliedJob();
        appliedJob2.setId(appliedJob1.getId());
        assertThat(appliedJob1).isEqualTo(appliedJob2);
        appliedJob2.setId(2L);
        assertThat(appliedJob1).isNotEqualTo(appliedJob2);
        appliedJob1.setId(null);
        assertThat(appliedJob1).isNotEqualTo(appliedJob2);
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
        TestUtil.equalsVerifier(AppliedJobDTO.class);
        AppliedJobDTO appliedJobDTO1 = new AppliedJobDTO();
        appliedJobDTO1.setId(1L);
        AppliedJobDTO appliedJobDTO2 = new AppliedJobDTO();
        assertThat(appliedJobDTO1).isNotEqualTo(appliedJobDTO2);
        appliedJobDTO2.setId(appliedJobDTO1.getId());
        assertThat(appliedJobDTO1).isEqualTo(appliedJobDTO2);
        appliedJobDTO2.setId(2L);
        assertThat(appliedJobDTO1).isNotEqualTo(appliedJobDTO2);
        appliedJobDTO1.setId(null);
        assertThat(appliedJobDTO1).isNotEqualTo(appliedJobDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(appliedJobMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appliedJobMapper.fromId(null)).isNull();
    }
}
