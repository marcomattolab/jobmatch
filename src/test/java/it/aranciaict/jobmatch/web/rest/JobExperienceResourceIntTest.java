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

import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.JobExperience;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.repository.JobExperienceRepository;
import it.aranciaict.jobmatch.service.JobExperienceQueryService;
import it.aranciaict.jobmatch.service.JobExperienceService;
import it.aranciaict.jobmatch.service.dto.JobExperienceDTO;
import it.aranciaict.jobmatch.service.mapper.JobExperienceMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the JobExperienceResource REST controller.
 *
 * @see JobExperienceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class JobExperienceResourceIntTest {

    /** The Constant DEFAULT_JOB_TITLE. */
    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    
    /** The Constant UPDATED_JOB_TITLE. */
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    /** The Constant DEFAULT_JOB_DESCRIPTION. */
    private static final String DEFAULT_JOB_DESCRIPTION = "AAAAAAAAAA";
    
    /** The Constant UPDATED_JOB_DESCRIPTION. */
    private static final String UPDATED_JOB_DESCRIPTION = "BBBBBBBBBB";

    /** The Constant DEFAULT_COMPANY_NAME. */
    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    
    /** The Constant UPDATED_COMPANY_NAME. */
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    /** The Constant DEFAULT_START_DATE. */
    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    
    /** The Constant UPDATED_START_DATE. */
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    /** The Constant DEFAULT_END_DATE. */
    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    
    /** The Constant UPDATED_END_DATE. */
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    /** The Constant DEFAULT_CURRENT. */
    private static final Boolean DEFAULT_CURRENT = false;
    
    /** The Constant UPDATED_CURRENT. */
    private static final Boolean UPDATED_CURRENT = true;

    /** The Constant DEFAULT_ENABLED. */
    private static final Boolean DEFAULT_ENABLED = false;
    
    /** The Constant UPDATED_ENABLED. */
    private static final Boolean UPDATED_ENABLED = true;
    
    /** The Constant DEFAULT_COUNTRY. */
    private static final Country DEFAULT_COUNTRY = Country.IT;
    
    /** The Constant DEFAULT_CITY. */
    private static final String DEFAULT_CITY = "AAAAAAAAA";

    /** The job experience repository. */
    @Autowired
    private JobExperienceRepository jobExperienceRepository;

    /** The job experience mapper. */
    @Autowired
    private JobExperienceMapper jobExperienceMapper;

    /** The job experience service. */
    @Autowired
    private JobExperienceService jobExperienceService;

    /** The job experience query service. */
    @Autowired
    private JobExperienceQueryService jobExperienceQueryService;

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

    /** The rest job experience mock mvc. */
    private MockMvc restJobExperienceMockMvc;

    /** The job experience. */
    private JobExperience jobExperience;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobExperienceResource jobExperienceResource = new JobExperienceResource(jobExperienceService, jobExperienceQueryService);
        this.restJobExperienceMockMvc = MockMvcBuilders.standaloneSetup(jobExperienceResource)
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
     * @return the job experience
     */
    public static JobExperience createEntity(EntityManager em) {
        JobExperience jobExperience = new JobExperience()
            .jobTitle(DEFAULT_JOB_TITLE)
            .jobDescription(DEFAULT_JOB_DESCRIPTION)
            .companyName(DEFAULT_COMPANY_NAME)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .current(DEFAULT_CURRENT)
            .enabled(DEFAULT_ENABLED);
        return jobExperience;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        jobExperience = createEntity(em);
    }

    /**
     * Creates the job experience.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void createJobExperience() throws Exception {
        int databaseSizeBeforeCreate = jobExperienceRepository.findAll().size();

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);
        restJobExperienceMockMvc.perform(post("/api/job-experiences")
//        	.with(user("test1").roles("CANDIDATE"))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO)))
            .andExpect(status().isCreated());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testJobExperience.getJobDescription()).isEqualTo(DEFAULT_JOB_DESCRIPTION);
        assertThat(testJobExperience.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testJobExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testJobExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testJobExperience.isCurrent()).isEqualTo(DEFAULT_CURRENT);
        assertThat(testJobExperience.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    /**
     * Creates the job experience with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void createJobExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobExperienceRepository.findAll().size();

        // Create the JobExperience with an existing ID
        jobExperience.setId(1L);
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Check job title is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void checkJobTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobExperienceRepository.findAll().size();
        // set the field null
        jobExperience.setJobTitle(null);

        // Create the JobExperience, which fails.
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO)))
            .andExpect(status().isBadRequest());

        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Check start date is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobExperienceRepository.findAll().size();
        // set the field null
        jobExperience.setStartDate(null);

        // Create the JobExperience, which fails.
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        restJobExperienceMockMvc.perform(post("/api/job-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO)))
            .andExpect(status().isBadRequest());

        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Gets the all job experiences.
     *
     *  the all job experiences
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiences() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList
        restJobExperienceMockMvc.perform(get("/api/job-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT.booleanValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    /**
     * Gets the job experience.
     *
     *  the job experience
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get the jobExperience
        restJobExperienceMockMvc.perform(get("/api/job-experiences/{id}", jobExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobExperience.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.jobDescription").value(DEFAULT_JOB_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.current").value(DEFAULT_CURRENT.booleanValue()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }


    /**
     * Gets the all job experiences by job title is equal to something.
     *
     *  the all job experiences by job title is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultJobExperienceShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the jobExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultJobExperienceShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    /**
     * Gets the all job experiences by job title is in should work.
     *
     *  the all job experiences by job title is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultJobExperienceShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the jobExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultJobExperienceShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    /**
     * Gets the all job experiences by job title is null or not null.
     *
     *  the all job experiences by job title is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where jobTitle is not null
        defaultJobExperienceShouldBeFound("jobTitle.specified=true");

        // Get all the jobExperienceList where jobTitle is null
        defaultJobExperienceShouldNotBeFound("jobTitle.specified=false");
    }

    /**
     * Gets the all job experiences by company name is equal to something.
     *
     *  the all job experiences by company name is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where companyName equals to DEFAULT_COMPANY_NAME
        defaultJobExperienceShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the jobExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultJobExperienceShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    /**
     * Gets the all job experiences by company name is in should work.
     *
     *  the all job experiences by company name is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultJobExperienceShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the jobExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultJobExperienceShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    /**
     * Gets the all job experiences by company name is null or not null.
     *
     *  the all job experiences by company name is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where companyName is not null
        defaultJobExperienceShouldBeFound("companyName.specified=true");

        // Get all the jobExperienceList where companyName is null
        defaultJobExperienceShouldNotBeFound("companyName.specified=false");
    }

    /**
     * Gets the all job experiences by start date is equal to something.
     *
     *  the all job experiences by start date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where startDate equals to DEFAULT_START_DATE
        defaultJobExperienceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the jobExperienceList where startDate equals to UPDATED_START_DATE
        defaultJobExperienceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    /**
     * Gets the all job experiences by start date is in should work.
     *
     *  the all job experiences by start date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultJobExperienceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the jobExperienceList where startDate equals to UPDATED_START_DATE
        defaultJobExperienceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    /**
     * Gets the all job experiences by start date is null or not null.
     *
     *  the all job experiences by start date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where startDate is not null
        defaultJobExperienceShouldBeFound("startDate.specified=true");

        // Get all the jobExperienceList where startDate is null
        defaultJobExperienceShouldNotBeFound("startDate.specified=false");
    }

    /**
     * Gets the all job experiences by start date is greater than or equal to something.
     *
     *  the all job experiences by start date is greater than or equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where startDate greater than or equals to DEFAULT_START_DATE
        defaultJobExperienceShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the jobExperienceList where startDate greater than or equals to UPDATED_START_DATE
        defaultJobExperienceShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    /**
     * Gets the all job experiences by start date is less than something.
     *
     *  the all job experiences by start date is less than something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where startDate less than or equals to DEFAULT_START_DATE
        defaultJobExperienceShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the jobExperienceList where startDate less than or equals to UPDATED_START_DATE
        defaultJobExperienceShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    /**
     * Gets the all job experiences by end date is equal to something.
     *
     *  the all job experiences by end date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where endDate equals to DEFAULT_END_DATE
        defaultJobExperienceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the jobExperienceList where endDate equals to UPDATED_END_DATE
        defaultJobExperienceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    /**
     * Gets the all job experiences by end date is in should work.
     *
     *  the all job experiences by end date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultJobExperienceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the jobExperienceList where endDate equals to UPDATED_END_DATE
        defaultJobExperienceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    /**
     * Gets the all job experiences by end date is null or not null.
     *
     *  the all job experiences by end date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where endDate is not null
        defaultJobExperienceShouldBeFound("endDate.specified=true");

        // Get all the jobExperienceList where endDate is null
        defaultJobExperienceShouldNotBeFound("endDate.specified=false");
    }

    /**
     * Gets the all job experiences by end date is greater than or equal to something.
     *
     *  the all job experiences by end date is greater than or equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where endDate greater than or equals to DEFAULT_END_DATE
        defaultJobExperienceShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the jobExperienceList where endDate greater than or equals to UPDATED_END_DATE
        defaultJobExperienceShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    /**
     * Gets the all job experiences by end date is less than something.
     *
     *  the all job experiences by end date is less than something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where endDate less than or equals to DEFAULT_END_DATE
        defaultJobExperienceShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the jobExperienceList where endDate less than or equals to UPDATED_END_DATE
        defaultJobExperienceShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    /**
     * Gets the all job experiences by current is equal to something.
     *
     *  the all job experiences by current is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where current equals to DEFAULT_CURRENT
        defaultJobExperienceShouldBeFound("current.equals=" + DEFAULT_CURRENT);

        // Get all the jobExperienceList where current equals to UPDATED_CURRENT
        defaultJobExperienceShouldNotBeFound("current.equals=" + UPDATED_CURRENT);
    }

    /**
     * Gets the all job experiences by current is in should work.
     *
     *  the all job experiences by current is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where current in DEFAULT_CURRENT or UPDATED_CURRENT
        defaultJobExperienceShouldBeFound("current.in=" + DEFAULT_CURRENT + "," + UPDATED_CURRENT);

        // Get all the jobExperienceList where current equals to UPDATED_CURRENT
        defaultJobExperienceShouldNotBeFound("current.in=" + UPDATED_CURRENT);
    }

    /**
     * Gets the all job experiences by current is null or not null.
     *
     *  the all job experiences by current is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCurrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where current is not null
        defaultJobExperienceShouldBeFound("current.specified=true");

        // Get all the jobExperienceList where current is null
        defaultJobExperienceShouldNotBeFound("current.specified=false");
    }

    /**
     * Gets the all job experiences by enabled is equal to something.
     *
     *  the all job experiences by enabled is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where enabled equals to DEFAULT_ENABLED
        defaultJobExperienceShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the jobExperienceList where enabled equals to UPDATED_ENABLED
        defaultJobExperienceShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    /**
     * Gets the all job experiences by enabled is in should work.
     *
     *  the all job experiences by enabled is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultJobExperienceShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the jobExperienceList where enabled equals to UPDATED_ENABLED
        defaultJobExperienceShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    /**
     * Gets the all job experiences by enabled is null or not null.
     *
     *  the all job experiences by enabled is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList where enabled is not null
        defaultJobExperienceShouldBeFound("enabled.specified=true");

        // Get all the jobExperienceList where enabled is null
        defaultJobExperienceShouldNotBeFound("enabled.specified=false");
    }

    /**
     * Gets the all job experiences by candidate is equal to something.
     *
     *  the all job experiences by candidate is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllJobExperiencesByCandidateIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidate candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
        jobExperience.setCandidate(candidate);
        jobExperienceRepository.saveAndFlush(jobExperience);
        Long candidateId = candidate.getId();

        // Get all the jobExperienceList where candidate equals to candidateId
        defaultJobExperienceShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the jobExperienceList where candidate equals to candidateId + 1
        defaultJobExperienceShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultJobExperienceShouldBeFound(String filter) throws Exception {
        restJobExperienceMockMvc.perform(get("/api/job-experiences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT.booleanValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restJobExperienceMockMvc.perform(get("/api/job-experiences/count?sort=id,desc&" + filter))
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
    private void defaultJobExperienceShouldNotBeFound(String filter) throws Exception {
        restJobExperienceMockMvc.perform(get("/api/job-experiences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobExperienceMockMvc.perform(get("/api/job-experiences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing job experience.
     *
     *  the non existing job experience
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingJobExperience() throws Exception {
        // Get the jobExperience
        restJobExperienceMockMvc.perform(get("/api/job-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update job experience.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void updateJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // Update the jobExperience
        JobExperience updatedJobExperience = jobExperienceRepository.findById(jobExperience.getId()).get();
        // Disconnect from session so that the updates on updatedJobExperience are not directly saved in db
        em.detach(updatedJobExperience);
        updatedJobExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .current(UPDATED_CURRENT)
            .enabled(UPDATED_ENABLED);
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(updatedJobExperience);

        restJobExperienceMockMvc.perform(put("/api/job-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO)))
            .andExpect(status().isOk());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testJobExperience.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
        assertThat(testJobExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testJobExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testJobExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testJobExperience.isCurrent()).isEqualTo(UPDATED_CURRENT);
        assertThat(testJobExperience.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    /**
     * Update non existing job experience.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void updateNonExistingJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc.perform(put("/api/job-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete job experience.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void deleteJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        int databaseSizeBeforeDelete = jobExperienceRepository.findAll().size();

        // Delete the jobExperience
        restJobExperienceMockMvc.perform(delete("/api/job-experiences/{id}", jobExperience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(JobExperience.class);
        JobExperience jobExperience1 = new JobExperience();
        jobExperience1.setId(1L);
        JobExperience jobExperience2 = new JobExperience();
        jobExperience2.setId(jobExperience1.getId());
        assertThat(jobExperience1).isEqualTo(jobExperience2);
        jobExperience2.setId(2L);
        assertThat(jobExperience1).isNotEqualTo(jobExperience2);
        jobExperience1.setId(null);
        assertThat(jobExperience1).isNotEqualTo(jobExperience2);
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
        TestUtil.equalsVerifier(JobExperienceDTO.class);
        JobExperienceDTO jobExperienceDTO1 = new JobExperienceDTO();
        jobExperienceDTO1.setId(1L);
        JobExperienceDTO jobExperienceDTO2 = new JobExperienceDTO();
        assertThat(jobExperienceDTO1).isNotEqualTo(jobExperienceDTO2);
        jobExperienceDTO2.setId(jobExperienceDTO1.getId());
        assertThat(jobExperienceDTO1).isEqualTo(jobExperienceDTO2);
        jobExperienceDTO2.setId(2L);
        assertThat(jobExperienceDTO1).isNotEqualTo(jobExperienceDTO2);
        jobExperienceDTO1.setId(null);
        assertThat(jobExperienceDTO1).isNotEqualTo(jobExperienceDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(jobExperienceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobExperienceMapper.fromId(null)).isNull();
    }
}
