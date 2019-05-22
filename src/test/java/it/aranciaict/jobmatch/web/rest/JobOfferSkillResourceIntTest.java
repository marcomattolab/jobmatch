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
import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.JobOfferSkill;
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.enumeration.ProficNumberOfYear;
import it.aranciaict.jobmatch.domain.enumeration.SkillLevelType;
import it.aranciaict.jobmatch.repository.JobOfferSkillRepository;
import it.aranciaict.jobmatch.service.JobOfferSkillQueryService;
import it.aranciaict.jobmatch.service.JobOfferSkillService;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;
import it.aranciaict.jobmatch.service.mapper.JobOfferSkillMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the JobOfferSkillResource REST controller.
 *
 * @see JobOfferSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class JobOfferSkillResourceIntTest {

    /** The Constant DEFAULT_LEVEL. */
    private static final SkillLevelType DEFAULT_LEVEL = SkillLevelType.BEGINNER;
    
    /** The Constant UPDATED_LEVEL. */
    private static final SkillLevelType UPDATED_LEVEL = SkillLevelType.MEDIUM;

    /** The Constant DEFAULT_PROFIC_NUMBER_OF_YEAR. */
    private static final ProficNumberOfYear DEFAULT_PROFIC_NUMBER_OF_YEAR = ProficNumberOfYear.ZERO_TO_ONE;
    
    /** The Constant UPDATED_PROFIC_NUMBER_OF_YEAR. */
    private static final ProficNumberOfYear UPDATED_PROFIC_NUMBER_OF_YEAR = ProficNumberOfYear.ONE_TO_THREE;

    /** The job offer skill repository. */
    @Autowired
    private JobOfferSkillRepository jobOfferSkillRepository;

    /** The job offer skill mapper. */
    @Autowired
    private JobOfferSkillMapper jobOfferSkillMapper;

    /** The job offer skill service. */
    @Autowired
    private JobOfferSkillService jobOfferSkillService;

    /** The job offer skill query service. */
    @Autowired
    private JobOfferSkillQueryService jobOfferSkillQueryService;

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

    /** The rest job offer skill mock mvc. */
    private MockMvc restJobOfferSkillMockMvc;

    /** The job offer skill. */
    private JobOfferSkill jobOfferSkill;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobOfferSkillResource jobOfferSkillResource = new JobOfferSkillResource(jobOfferSkillService, jobOfferSkillQueryService);
        this.restJobOfferSkillMockMvc = MockMvcBuilders.standaloneSetup(jobOfferSkillResource)
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
     * @return the job offer skill
     */
    public static JobOfferSkill createEntity(EntityManager em) {
        JobOfferSkill jobOfferSkill = new JobOfferSkill()
            .level(DEFAULT_LEVEL)
            .proficNumberOfYear(DEFAULT_PROFIC_NUMBER_OF_YEAR);
        return jobOfferSkill;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        jobOfferSkill = createEntity(em);
    }

    /**
     * Creates the job offer skill.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createJobOfferSkill() throws Exception {
        int databaseSizeBeforeCreate = jobOfferSkillRepository.findAll().size();

        // Create the JobOfferSkill
        JobOfferSkillDTO jobOfferSkillDTO = jobOfferSkillMapper.toDto(jobOfferSkill);
        restJobOfferSkillMockMvc.perform(post("/api/job-offer-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOfferSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the JobOfferSkill in the database
        List<JobOfferSkill> jobOfferSkillList = jobOfferSkillRepository.findAll();
        assertThat(jobOfferSkillList).hasSize(databaseSizeBeforeCreate + 1);
        JobOfferSkill testJobOfferSkill = jobOfferSkillList.get(jobOfferSkillList.size() - 1);
        assertThat(testJobOfferSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testJobOfferSkill.getProficNumberOfYear()).isEqualTo(DEFAULT_PROFIC_NUMBER_OF_YEAR);
    }

    /**
     * Creates the job offer skill with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createJobOfferSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobOfferSkillRepository.findAll().size();

        // Create the JobOfferSkill with an existing ID
        jobOfferSkill.setId(1L);
        JobOfferSkillDTO jobOfferSkillDTO = jobOfferSkillMapper.toDto(jobOfferSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOfferSkillMockMvc.perform(post("/api/job-offer-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOfferSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobOfferSkill in the database
        List<JobOfferSkill> jobOfferSkillList = jobOfferSkillRepository.findAll();
        assertThat(jobOfferSkillList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Gets the all job offer skills.
     *
     *  the all job offer skills
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkills() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOfferSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].proficNumberOfYear").value(hasItem(DEFAULT_PROFIC_NUMBER_OF_YEAR.toString())));
    }
    
    /**
     * Gets the job offer skill.
     *
     *  the job offer skill
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getJobOfferSkill() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get the jobOfferSkill
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills/{id}", jobOfferSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobOfferSkill.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.proficNumberOfYear").value(DEFAULT_PROFIC_NUMBER_OF_YEAR.toString()));
    }


    /**
     * Gets the all job offer skills by level is equal to something.
     *
     *  the all job offer skills by level is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList where level equals to DEFAULT_LEVEL
        defaultJobOfferSkillShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the jobOfferSkillList where level equals to UPDATED_LEVEL
        defaultJobOfferSkillShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    /**
     * Gets the all job offer skills by level is in should work.
     *
     *  the all job offer skills by level is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultJobOfferSkillShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the jobOfferSkillList where level equals to UPDATED_LEVEL
        defaultJobOfferSkillShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    /**
     * Gets the all job offer skills by level is null or not null.
     *
     *  the all job offer skills by level is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList where level is not null
        defaultJobOfferSkillShouldBeFound("level.specified=true");

        // Get all the jobOfferSkillList where level is null
        defaultJobOfferSkillShouldNotBeFound("level.specified=false");
    }

    /**
     * Gets the all job offer skills by profic number of year is equal to something.
     *
     *  the all job offer skills by profic number of year is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByProficNumberOfYearIsEqualToSomething() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList where proficNumberOfYear equals to DEFAULT_PROFIC_NUMBER_OF_YEAR
        defaultJobOfferSkillShouldBeFound("proficNumberOfYear.equals=" + DEFAULT_PROFIC_NUMBER_OF_YEAR);

        // Get all the jobOfferSkillList where proficNumberOfYear equals to UPDATED_PROFIC_NUMBER_OF_YEAR
        defaultJobOfferSkillShouldNotBeFound("proficNumberOfYear.equals=" + UPDATED_PROFIC_NUMBER_OF_YEAR);
    }

    /**
     * Gets the all job offer skills by profic number of year is in should work.
     *
     *  the all job offer skills by profic number of year is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByProficNumberOfYearIsInShouldWork() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList where proficNumberOfYear in DEFAULT_PROFIC_NUMBER_OF_YEAR or UPDATED_PROFIC_NUMBER_OF_YEAR
        defaultJobOfferSkillShouldBeFound("proficNumberOfYear.in=" + DEFAULT_PROFIC_NUMBER_OF_YEAR + "," + UPDATED_PROFIC_NUMBER_OF_YEAR);

        // Get all the jobOfferSkillList where proficNumberOfYear equals to UPDATED_PROFIC_NUMBER_OF_YEAR
        defaultJobOfferSkillShouldNotBeFound("proficNumberOfYear.in=" + UPDATED_PROFIC_NUMBER_OF_YEAR);
    }

    /**
     * Gets the all job offer skills by profic number of year is null or not null.
     *
     *  the all job offer skills by profic number of year is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByProficNumberOfYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        // Get all the jobOfferSkillList where proficNumberOfYear is not null
        defaultJobOfferSkillShouldBeFound("proficNumberOfYear.specified=true");

        // Get all the jobOfferSkillList where proficNumberOfYear is null
        defaultJobOfferSkillShouldNotBeFound("proficNumberOfYear.specified=false");
    }

    /**
     * Gets the all job offer skills by skill tag is equal to something.
     *
     *  the all job offer skills by skill tag is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsBySkillTagIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillTag skillTag = SkillTagResourceIntTest.createEntity(em);
        em.persist(skillTag);
        em.flush();
        jobOfferSkill.setSkillTag(skillTag);
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);
        Long skillTagId = skillTag.getId();

        // Get all the jobOfferSkillList where skillTag equals to skillTagId
        defaultJobOfferSkillShouldBeFound("skillTagId.equals=" + skillTagId);

        // Get all the jobOfferSkillList where skillTag equals to skillTagId + 1
        defaultJobOfferSkillShouldNotBeFound("skillTagId.equals=" + (skillTagId + 1));
    }


    /**
     * Gets the all job offer skills by job offer is equal to something.
     *
     *  the all job offer skills by job offer is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobOfferSkillsByJobOfferIsEqualToSomething() throws Exception {
        // Initialize the database
        JobOffer jobOffer = JobOfferResourceIntTest.createEntity(em);
        em.persist(jobOffer);
        em.flush();
        jobOfferSkill.setJobOffer(jobOffer);
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);
        Long jobOfferId = jobOffer.getId();

        // Get all the jobOfferSkillList where jobOffer equals to jobOfferId
        defaultJobOfferSkillShouldBeFound("jobOfferId.equals=" + jobOfferId);

        // Get all the jobOfferSkillList where jobOffer equals to jobOfferId + 1
        defaultJobOfferSkillShouldNotBeFound("jobOfferId.equals=" + (jobOfferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultJobOfferSkillShouldBeFound(String filter) throws Exception {
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOfferSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].proficNumberOfYear").value(hasItem(DEFAULT_PROFIC_NUMBER_OF_YEAR.toString())));

        // Check, that the count call also returns 1
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills/count?sort=id,desc&" + filter))
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
    private void defaultJobOfferSkillShouldNotBeFound(String filter) throws Exception {
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing job offer skill.
     *
     * the non existing job offer skill
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingJobOfferSkill() throws Exception {
        // Get the jobOfferSkill
        restJobOfferSkillMockMvc.perform(get("/api/job-offer-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update job offer skill.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateJobOfferSkill() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        int databaseSizeBeforeUpdate = jobOfferSkillRepository.findAll().size();

        // Update the jobOfferSkill
        JobOfferSkill updatedJobOfferSkill = jobOfferSkillRepository.findById(jobOfferSkill.getId()).get();
        // Disconnect from session so that the updates on updatedJobOfferSkill are not directly saved in db
        em.detach(updatedJobOfferSkill);
        updatedJobOfferSkill
            .level(UPDATED_LEVEL)
            .proficNumberOfYear(UPDATED_PROFIC_NUMBER_OF_YEAR);
        JobOfferSkillDTO jobOfferSkillDTO = jobOfferSkillMapper.toDto(updatedJobOfferSkill);

        restJobOfferSkillMockMvc.perform(put("/api/job-offer-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOfferSkillDTO)))
            .andExpect(status().isOk());

        // Validate the JobOfferSkill in the database
        List<JobOfferSkill> jobOfferSkillList = jobOfferSkillRepository.findAll();
        assertThat(jobOfferSkillList).hasSize(databaseSizeBeforeUpdate);
        JobOfferSkill testJobOfferSkill = jobOfferSkillList.get(jobOfferSkillList.size() - 1);
        assertThat(testJobOfferSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testJobOfferSkill.getProficNumberOfYear()).isEqualTo(UPDATED_PROFIC_NUMBER_OF_YEAR);
    }

    /**
     * Update non existing job offer skill.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingJobOfferSkill() throws Exception {
        int databaseSizeBeforeUpdate = jobOfferSkillRepository.findAll().size();

        // Create the JobOfferSkill
        JobOfferSkillDTO jobOfferSkillDTO = jobOfferSkillMapper.toDto(jobOfferSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobOfferSkillMockMvc.perform(put("/api/job-offer-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOfferSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobOfferSkill in the database
        List<JobOfferSkill> jobOfferSkillList = jobOfferSkillRepository.findAll();
        assertThat(jobOfferSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete job offer skill.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteJobOfferSkill() throws Exception {
        // Initialize the database
        jobOfferSkillRepository.saveAndFlush(jobOfferSkill);

        int databaseSizeBeforeDelete = jobOfferSkillRepository.findAll().size();

        // Delete the jobOfferSkill
        restJobOfferSkillMockMvc.perform(delete("/api/job-offer-skills/{id}", jobOfferSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobOfferSkill> jobOfferSkillList = jobOfferSkillRepository.findAll();
        assertThat(jobOfferSkillList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(JobOfferSkill.class);
        JobOfferSkill jobOfferSkill1 = new JobOfferSkill();
        jobOfferSkill1.setId(1L);
        JobOfferSkill jobOfferSkill2 = new JobOfferSkill();
        jobOfferSkill2.setId(jobOfferSkill1.getId());
        assertThat(jobOfferSkill1).isEqualTo(jobOfferSkill2);
        jobOfferSkill2.setId(2L);
        assertThat(jobOfferSkill1).isNotEqualTo(jobOfferSkill2);
        jobOfferSkill1.setId(null);
        assertThat(jobOfferSkill1).isNotEqualTo(jobOfferSkill2);
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
        TestUtil.equalsVerifier(JobOfferSkillDTO.class);
        JobOfferSkillDTO jobOfferSkillDTO1 = new JobOfferSkillDTO();
        jobOfferSkillDTO1.setId(1L);
        JobOfferSkillDTO jobOfferSkillDTO2 = new JobOfferSkillDTO();
        assertThat(jobOfferSkillDTO1).isNotEqualTo(jobOfferSkillDTO2);
        jobOfferSkillDTO2.setId(jobOfferSkillDTO1.getId());
        assertThat(jobOfferSkillDTO1).isEqualTo(jobOfferSkillDTO2);
        jobOfferSkillDTO2.setId(2L);
        assertThat(jobOfferSkillDTO1).isNotEqualTo(jobOfferSkillDTO2);
        jobOfferSkillDTO1.setId(null);
        assertThat(jobOfferSkillDTO1).isNotEqualTo(jobOfferSkillDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(jobOfferSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobOfferSkillMapper.fromId(null)).isNull();
    }
}
