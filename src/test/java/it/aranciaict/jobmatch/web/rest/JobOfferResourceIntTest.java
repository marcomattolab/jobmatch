package it.aranciaict.jobmatch.web.rest;

import static it.aranciaict.jobmatch.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.context.MessageSource;
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
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.EmploymentType;
import it.aranciaict.jobmatch.domain.enumeration.JobOfferStatus;
import it.aranciaict.jobmatch.domain.enumeration.SeniorityLevel;
import it.aranciaict.jobmatch.repository.JobOfferRepository;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.JobOfferQueryService;
import it.aranciaict.jobmatch.service.JobOfferService;
import it.aranciaict.jobmatch.service.dto.JobOfferDTO;
import it.aranciaict.jobmatch.service.mapper.JobOfferMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the JobOfferResource REST controller.
 *
 * @see JobOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class JobOfferResourceIntTest {

	/** The Constant DEFAULT_START_DATE. */
	private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);

	/** The Constant UPDATED_START_DATE. */
	private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

	/** The Constant DEFAULT_JOB_TITLE. */
	private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";

	/** The Constant UPDATED_JOB_TITLE. */
	private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

	/** The Constant DEFAULT_JOB_SHORT_DESCRIPTION. */
	private static final String DEFAULT_JOB_SHORT_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_JOB_SHORT_DESCRIPTION. */
	private static final String UPDATED_JOB_SHORT_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_JOB_DESCRIPTION. */
	private static final String DEFAULT_JOB_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_JOB_DESCRIPTION. */
	private static final String UPDATED_JOB_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_RESPONSIBILITIES_DESCRIPTION. */
	private static final String DEFAULT_RESPONSIBILITIES_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_RESPONSIBILITIES_DESCRIPTION. */
	private static final String UPDATED_RESPONSIBILITIES_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_EXPERIENCES_DESCRIPTION. */
	private static final String DEFAULT_EXPERIENCES_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_EXPERIENCES_DESCRIPTION. */
	private static final String UPDATED_EXPERIENCES_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_ATTRIBUTES_DESCRIPTION. */
	private static final String DEFAULT_ATTRIBUTES_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_ATTRIBUTES_DESCRIPTION. */
	private static final String UPDATED_ATTRIBUTES_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_JOB_FUNCTIONS. */
	private static final String DEFAULT_JOB_FUNCTIONS = "AAAAAAAAAA";

	/** The Constant UPDATED_JOB_FUNCTIONS. */
	private static final String UPDATED_JOB_FUNCTIONS = "BBBBBBBBBB";

	/** The Constant DEFAULT_JOB_CITY. */
	private static final String DEFAULT_JOB_CITY = "AAAAAAAAAA";

	/** The Constant UPDATED_JOB_CITY. */
	private static final String UPDATED_JOB_CITY = "BBBBBBBBBB";

	/** The Constant DEFAULT_JOB_COUNTRY. */
	private static final Country DEFAULT_JOB_COUNTRY = Country.IT;

	/** The Constant UPDATED_JOB_COUNTRY. */
	private static final Country UPDATED_JOB_COUNTRY = Country.AF;

	/** The Constant DEFAULT_EMPLOYMENT_TYPE. */
	private static final EmploymentType DEFAULT_EMPLOYMENT_TYPE = EmploymentType.FULL_TIME;

	/** The Constant UPDATED_EMPLOYMENT_TYPE. */
	private static final EmploymentType UPDATED_EMPLOYMENT_TYPE = EmploymentType.PART_TIME;

	/** The Constant DEFAULT_SENIORITY_LEVEL. */
	private static final SeniorityLevel DEFAULT_SENIORITY_LEVEL = SeniorityLevel.BEGINNER;

	/** The Constant UPDATED_SENIORITY_LEVEL. */
	private static final SeniorityLevel UPDATED_SENIORITY_LEVEL = SeniorityLevel.MEDIUM;

	/** The Constant DEFAULT_SALARY_OFFERED. */
	private static final String DEFAULT_SALARY_OFFERED = "AAAAAAAAAA";

	/** The Constant UPDATED_SALARY_OFFERED. */
	private static final String UPDATED_SALARY_OFFERED = "BBBBBBBBBB";

	/** The Constant DEFAULT_STATUS. */
	private static final JobOfferStatus DEFAULT_STATUS = JobOfferStatus.ACTIVE;

	/** The Constant UPDATED_STATUS. */
	private static final JobOfferStatus UPDATED_STATUS = JobOfferStatus.ENDED;

	/** The Constant DEFAULT_ENABLED. */
	private static final Boolean DEFAULT_ENABLED = false;

	/** The Constant UPDATED_ENABLED. */
	private static final Boolean UPDATED_ENABLED = true;

	/** The job offer repository. */
	@Autowired
	private JobOfferRepository jobOfferRepository;

	/** The job offer mapper. */
	@Autowired
	private JobOfferMapper jobOfferMapper;

	/** The job offer service. */
	@Autowired
	private JobOfferService jobOfferService;

	/** The company service. */
	@Autowired
	private CompanyService companyService;

	/** The job offer query service. */
	@Autowired
	private JobOfferQueryService jobOfferQueryService;

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

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The validator. */
	@Autowired
	private Validator validator;

	/** The rest job offer mock mvc. */
	private MockMvc restJobOfferMockMvc;

	/** The job offer. */
	private JobOffer jobOffer;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final JobOfferResource jobOfferResource = new JobOfferResource(jobOfferService, jobOfferQueryService,
				companyService, messageSource);
		this.restJobOfferMockMvc = MockMvcBuilders.standaloneSetup(jobOfferResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.setValidator(validator).build();
	}

	/**
	 * Create an entity for this test.
	 * 
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 *
	 * @param em the em
	 * @return the job offer
	 */
	public static JobOffer createEntity(EntityManager em) {
		JobOffer jobOffer = new JobOffer().startDate(DEFAULT_START_DATE).jobTitle(DEFAULT_JOB_TITLE)
				.jobShortDescription(DEFAULT_JOB_SHORT_DESCRIPTION).jobDescription(DEFAULT_JOB_DESCRIPTION)
				.responsibilitiesDescription(DEFAULT_RESPONSIBILITIES_DESCRIPTION)
				.experiencesDescription(DEFAULT_EXPERIENCES_DESCRIPTION)
				.attributesDescription(DEFAULT_ATTRIBUTES_DESCRIPTION).jobFunctions(DEFAULT_JOB_FUNCTIONS)
				.jobCity(DEFAULT_JOB_CITY).jobCountry(DEFAULT_JOB_COUNTRY).employmentType(DEFAULT_EMPLOYMENT_TYPE)
				.seniorityLevel(DEFAULT_SENIORITY_LEVEL).salaryOffered(DEFAULT_SALARY_OFFERED).status(DEFAULT_STATUS)
				.enabled(DEFAULT_ENABLED);
		return jobOffer;
	}

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		jobOffer = createEntity(em);
	}

	/**
	 * Creates the job offer.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createJobOffer() throws Exception {
		int databaseSizeBeforeCreate = jobOfferRepository.findAll().size();

		// Create the JobOffer
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(jobOffer);
		restJobOfferMockMvc.perform(post("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isCreated());

		// Validate the JobOffer in the database
		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeCreate + 1);
		JobOffer testJobOffer = jobOfferList.get(jobOfferList.size() - 1);
		assertThat(testJobOffer.getStartDate()).isEqualTo(DEFAULT_START_DATE);
		assertThat(testJobOffer.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
		assertThat(testJobOffer.getJobShortDescription()).isEqualTo(DEFAULT_JOB_SHORT_DESCRIPTION);
		assertThat(testJobOffer.getJobDescription()).isEqualTo(DEFAULT_JOB_DESCRIPTION);
		assertThat(testJobOffer.getResponsibilitiesDescription()).isEqualTo(DEFAULT_RESPONSIBILITIES_DESCRIPTION);
		assertThat(testJobOffer.getExperiencesDescription()).isEqualTo(DEFAULT_EXPERIENCES_DESCRIPTION);
		assertThat(testJobOffer.getAttributesDescription()).isEqualTo(DEFAULT_ATTRIBUTES_DESCRIPTION);
		assertThat(testJobOffer.getJobFunctions()).isEqualTo(DEFAULT_JOB_FUNCTIONS);
		assertThat(testJobOffer.getJobCity()).isEqualTo(DEFAULT_JOB_CITY);
		assertThat(testJobOffer.getJobCountry()).isEqualTo(DEFAULT_JOB_COUNTRY);
		assertThat(testJobOffer.getEmploymentType()).isEqualTo(DEFAULT_EMPLOYMENT_TYPE);
		assertThat(testJobOffer.getSeniorityLevel()).isEqualTo(DEFAULT_SENIORITY_LEVEL);
		assertThat(testJobOffer.getSalaryOffered()).isEqualTo(DEFAULT_SALARY_OFFERED);
		assertThat(testJobOffer.getStatus()).isEqualTo(DEFAULT_STATUS);
		assertThat(testJobOffer.isEnabled()).isEqualTo(DEFAULT_ENABLED);
	}

	/**
	 * Creates the job offer with existing id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createJobOfferWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = jobOfferRepository.findAll().size();

		// Create the JobOffer with an existing ID
		jobOffer.setId(1L);
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(jobOffer);

		// An entity with an existing ID cannot be created, so this API call must fail
		restJobOfferMockMvc.perform(post("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isBadRequest());

		// Validate the JobOffer in the database
		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeCreate);
	}

	/**
	 * Check start date is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkStartDateIsRequired() throws Exception {
		int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
		// set the field null
		jobOffer.setStartDate(null);

		// Create the JobOffer, which fails.
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(jobOffer);

		restJobOfferMockMvc.perform(post("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isBadRequest());

		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check job title is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkJobTitleIsRequired() throws Exception {
		int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
		// set the field null
		jobOffer.setJobTitle(null);

		// Create the JobOffer, which fails.
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(jobOffer);

		restJobOfferMockMvc.perform(post("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isBadRequest());

		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check job short description is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkJobShortDescriptionIsRequired() throws Exception {
		int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
		// set the field null
		jobOffer.setJobShortDescription(null);

		// Create the JobOffer, which fails.
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(jobOffer);

		restJobOfferMockMvc.perform(post("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isBadRequest());

		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
	}

//	/**
//	 * Gets the all job offers.
//	 *
//	 * the all job offers
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffers() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList
//		restJobOfferMockMvc.perform(get("/api/job-offers?sort=id,desc")).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$.[*].id").value(hasItem(jobOffer.getId().intValue())))
//				.andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
//				.andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
//				.andExpect(
//						jsonPath("$.[*].jobShortDescription").value(hasItem(DEFAULT_JOB_SHORT_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].responsibilitiesDescription")
//						.value(hasItem(DEFAULT_RESPONSIBILITIES_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].experiencesDescription")
//						.value(hasItem(DEFAULT_EXPERIENCES_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].attributesDescription")
//						.value(hasItem(DEFAULT_ATTRIBUTES_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].jobFunctions").value(hasItem(DEFAULT_JOB_FUNCTIONS.toString())))
//				.andExpect(jsonPath("$.[*].jobCity").value(hasItem(DEFAULT_JOB_CITY.toString())))
//				.andExpect(jsonPath("$.[*].jobCountry").value(hasItem(DEFAULT_JOB_COUNTRY.toString())))
//				.andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
//				.andExpect(jsonPath("$.[*].seniorityLevel").value(hasItem(DEFAULT_SENIORITY_LEVEL.toString())))
//				.andExpect(jsonPath("$.[*].salaryOffered").value(hasItem(DEFAULT_SALARY_OFFERED.toString())))
//				.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
//				.andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
//	}

	/**
	 * Gets the job offer.
	 *
	 * the job offer
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getJobOffer() throws Exception {
		// Initialize the database
		jobOfferRepository.saveAndFlush(jobOffer);

		// Get the jobOffer
		restJobOfferMockMvc.perform(get("/api/job-offers/{id}", jobOffer.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(jobOffer.getId().intValue()))
				.andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
				.andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
				.andExpect(jsonPath("$.jobShortDescription").value(DEFAULT_JOB_SHORT_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.jobDescription").value(DEFAULT_JOB_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.responsibilitiesDescription")
						.value(DEFAULT_RESPONSIBILITIES_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.experiencesDescription").value(DEFAULT_EXPERIENCES_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.attributesDescription").value(DEFAULT_ATTRIBUTES_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.jobFunctions").value(DEFAULT_JOB_FUNCTIONS.toString()))
				.andExpect(jsonPath("$.jobCity").value(DEFAULT_JOB_CITY.toString()))
				.andExpect(jsonPath("$.jobCountry").value(DEFAULT_JOB_COUNTRY.toString()))
				.andExpect(jsonPath("$.employmentType").value(DEFAULT_EMPLOYMENT_TYPE.toString()))
				.andExpect(jsonPath("$.seniorityLevel").value(DEFAULT_SENIORITY_LEVEL.toString()))
				.andExpect(jsonPath("$.salaryOffered").value(DEFAULT_SALARY_OFFERED.toString()))
				.andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
				.andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
	}

//	/**
//	 * Gets the all job offers by start date is equal to something.
//	 *
//	 * the all job offers by start date is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStartDateIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where startDate equals to DEFAULT_START_DATE
//		defaultJobOfferShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);
//
//		// Get all the jobOfferList where startDate equals to UPDATED_START_DATE
//		defaultJobOfferShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
//	}
//
//	/**
//	 * Gets the all job offers by start date is in should work.
//	 *
//	 * the all job offers by start date is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStartDateIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where startDate in DEFAULT_START_DATE or
//		// UPDATED_START_DATE
//		defaultJobOfferShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);
//
//		// Get all the jobOfferList where startDate equals to UPDATED_START_DATE
//		defaultJobOfferShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
//	}
//
//	/**
//	 * Gets the all job offers by start date is null or not null.
//	 *
//	 * the all job offers by start date is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStartDateIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where startDate is not null
//		defaultJobOfferShouldBeFound("startDate.specified=true");
//
//		// Get all the jobOfferList where startDate is null
//		defaultJobOfferShouldNotBeFound("startDate.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by start date is greater than or equal to something.
//	 *
//	 * the all job offers by start date is greater than or equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where startDate greater than or equals to
//		// DEFAULT_START_DATE
//		defaultJobOfferShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);
//
//		// Get all the jobOfferList where startDate greater than or equals to
//		// UPDATED_START_DATE
//		defaultJobOfferShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
//	}
//
//	/**
//	 * Gets the all job offers by start date is less than something.
//	 *
//	 * the all job offers by start date is less than something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStartDateIsLessThanSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where startDate less than or equals to
//		// DEFAULT_START_DATE
//		defaultJobOfferShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);
//
//		// Get all the jobOfferList where startDate less than or equals to
//		// UPDATED_START_DATE
//		defaultJobOfferShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
//	}
//
//	/**
//	 * Gets the all job offers by job title is equal to something.
//	 *
//	 * the all job offers by job title is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobTitleIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobTitle equals to DEFAULT_JOB_TITLE
//		defaultJobOfferShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);
//
//		// Get all the jobOfferList where jobTitle equals to UPDATED_JOB_TITLE
//		defaultJobOfferShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
//	}
//
//	/**
//	 * Gets the all job offers by job title is in should work.
//	 *
//	 * the all job offers by job title is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobTitleIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobTitle in DEFAULT_JOB_TITLE or
//		// UPDATED_JOB_TITLE
//		defaultJobOfferShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);
//
//		// Get all the jobOfferList where jobTitle equals to UPDATED_JOB_TITLE
//		defaultJobOfferShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
//	}
//
//	/**
//	 * Gets the all job offers by job title is null or not null.
//	 *
//	 * the all job offers by job title is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobTitleIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobTitle is not null
//		defaultJobOfferShouldBeFound("jobTitle.specified=true");
//
//		// Get all the jobOfferList where jobTitle is null
//		defaultJobOfferShouldNotBeFound("jobTitle.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by job short description is equal to something.
//	 *
//	 * the all job offers by job short description is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobShortDescriptionIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobShortDescription equals to
//		// DEFAULT_JOB_SHORT_DESCRIPTION
//		defaultJobOfferShouldBeFound("jobShortDescription.equals=" + DEFAULT_JOB_SHORT_DESCRIPTION);
//
//		// Get all the jobOfferList where jobShortDescription equals to
//		// UPDATED_JOB_SHORT_DESCRIPTION
//		defaultJobOfferShouldNotBeFound("jobShortDescription.equals=" + UPDATED_JOB_SHORT_DESCRIPTION);
//	}
//
//	/**
//	 * Gets the all job offers by job short description is in should work.
//	 *
//	 * the all job offers by job short description is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobShortDescriptionIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobShortDescription in
//		// DEFAULT_JOB_SHORT_DESCRIPTION or UPDATED_JOB_SHORT_DESCRIPTION
//		defaultJobOfferShouldBeFound(
//				"jobShortDescription.in=" + DEFAULT_JOB_SHORT_DESCRIPTION + "," + UPDATED_JOB_SHORT_DESCRIPTION);
//
//		// Get all the jobOfferList where jobShortDescription equals to
//		// UPDATED_JOB_SHORT_DESCRIPTION
//		defaultJobOfferShouldNotBeFound("jobShortDescription.in=" + UPDATED_JOB_SHORT_DESCRIPTION);
//	}
//
//	/**
//	 * Gets the all job offers by job short description is null or not null.
//	 *
//	 * the all job offers by job short description is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobShortDescriptionIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobShortDescription is not null
//		defaultJobOfferShouldBeFound("jobShortDescription.specified=true");
//
//		// Get all the jobOfferList where jobShortDescription is null
//		defaultJobOfferShouldNotBeFound("jobShortDescription.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by job functions is equal to something.
//	 *
//	 * the all job offers by job functions is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobFunctionsIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobFunctions equals to DEFAULT_JOB_FUNCTIONS
//		defaultJobOfferShouldBeFound("jobFunctions.equals=" + DEFAULT_JOB_FUNCTIONS);
//
//		// Get all the jobOfferList where jobFunctions equals to UPDATED_JOB_FUNCTIONS
//		defaultJobOfferShouldNotBeFound("jobFunctions.equals=" + UPDATED_JOB_FUNCTIONS);
//	}
//
//	/**
//	 * Gets the all job offers by job functions is in should work.
//	 *
//	 * the all job offers by job functions is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobFunctionsIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobFunctions in DEFAULT_JOB_FUNCTIONS or
//		// UPDATED_JOB_FUNCTIONS
//		defaultJobOfferShouldBeFound("jobFunctions.in=" + DEFAULT_JOB_FUNCTIONS + "," + UPDATED_JOB_FUNCTIONS);
//
//		// Get all the jobOfferList where jobFunctions equals to UPDATED_JOB_FUNCTIONS
//		defaultJobOfferShouldNotBeFound("jobFunctions.in=" + UPDATED_JOB_FUNCTIONS);
//	}
//
//	/**
//	 * Gets the all job offers by job functions is null or not null.
//	 *
//	 * the all job offers by job functions is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobFunctionsIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobFunctions is not null
//		defaultJobOfferShouldBeFound("jobFunctions.specified=true");
//
//		// Get all the jobOfferList where jobFunctions is null
//		defaultJobOfferShouldNotBeFound("jobFunctions.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by job city is equal to something.
//	 *
//	 * the all job offers by job city is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobCityIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobCity equals to DEFAULT_JOB_CITY
//		defaultJobOfferShouldBeFound("jobCity.equals=" + DEFAULT_JOB_CITY);
//
//		// Get all the jobOfferList where jobCity equals to UPDATED_JOB_CITY
//		defaultJobOfferShouldNotBeFound("jobCity.equals=" + UPDATED_JOB_CITY);
//	}
//
//	/**
//	 * Gets the all job offers by job city is in should work.
//	 *
//	 * the all job offers by job city is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobCityIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobCity in DEFAULT_JOB_CITY or
//		// UPDATED_JOB_CITY
//		defaultJobOfferShouldBeFound("jobCity.in=" + DEFAULT_JOB_CITY + "," + UPDATED_JOB_CITY);
//
//		// Get all the jobOfferList where jobCity equals to UPDATED_JOB_CITY
//		defaultJobOfferShouldNotBeFound("jobCity.in=" + UPDATED_JOB_CITY);
//	}
//
//	/**
//	 * Gets the all job offers by job city is null or not null.
//	 *
//	 * the all job offers by job city is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobCityIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobCity is not null
//		defaultJobOfferShouldBeFound("jobCity.specified=true");
//
//		// Get all the jobOfferList where jobCity is null
//		defaultJobOfferShouldNotBeFound("jobCity.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by job country is equal to something.
//	 *
//	 * the all job offers by job country is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobCountryIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobCountry equals to DEFAULT_JOB_COUNTRY
//		defaultJobOfferShouldBeFound("jobCountry.equals=" + DEFAULT_JOB_COUNTRY);
//
//		// Get all the jobOfferList where jobCountry equals to UPDATED_JOB_COUNTRY
//		defaultJobOfferShouldNotBeFound("jobCountry.equals=" + UPDATED_JOB_COUNTRY);
//	}
//
//	/**
//	 * Gets the all job offers by job country is in should work.
//	 *
//	 * the all job offers by job country is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobCountryIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobCountry in DEFAULT_JOB_COUNTRY or
//		// UPDATED_JOB_COUNTRY
//		defaultJobOfferShouldBeFound("jobCountry.in=" + DEFAULT_JOB_COUNTRY + "," + UPDATED_JOB_COUNTRY);
//
//		// Get all the jobOfferList where jobCountry equals to UPDATED_JOB_COUNTRY
//		defaultJobOfferShouldNotBeFound("jobCountry.in=" + UPDATED_JOB_COUNTRY);
//	}
//
//	/**
//	 * Gets the all job offers by job country is null or not null.
//	 *
//	 * the all job offers by job country is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByJobCountryIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where jobCountry is not null
//		defaultJobOfferShouldBeFound("jobCountry.specified=true");
//
//		// Get all the jobOfferList where jobCountry is null
//		defaultJobOfferShouldNotBeFound("jobCountry.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by employment type is equal to something.
//	 *
//	 * the all job offers by employment type is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByEmploymentTypeIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where employmentType equals to
//		// DEFAULT_EMPLOYMENT_TYPE
//		defaultJobOfferShouldBeFound("employmentType.equals=" + DEFAULT_EMPLOYMENT_TYPE);
//
//		// Get all the jobOfferList where employmentType equals to
//		// UPDATED_EMPLOYMENT_TYPE
//		defaultJobOfferShouldNotBeFound("employmentType.equals=" + UPDATED_EMPLOYMENT_TYPE);
//	}
//
//	/**
//	 * Gets the all job offers by employment type is in should work.
//	 *
//	 * the all job offers by employment type is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByEmploymentTypeIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where employmentType in DEFAULT_EMPLOYMENT_TYPE or
//		// UPDATED_EMPLOYMENT_TYPE
//		defaultJobOfferShouldBeFound("employmentType.in=" + DEFAULT_EMPLOYMENT_TYPE + "," + UPDATED_EMPLOYMENT_TYPE);
//
//		// Get all the jobOfferList where employmentType equals to
//		// UPDATED_EMPLOYMENT_TYPE
//		defaultJobOfferShouldNotBeFound("employmentType.in=" + UPDATED_EMPLOYMENT_TYPE);
//	}
//
//	/**
//	 * Gets the all job offers by employment type is null or not null.
//	 *
//	 * the all job offers by employment type is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByEmploymentTypeIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where employmentType is not null
//		defaultJobOfferShouldBeFound("employmentType.specified=true");
//
//		// Get all the jobOfferList where employmentType is null
//		defaultJobOfferShouldNotBeFound("employmentType.specified=false");
//	}

//	/**
//	 * Gets the all job offers by seniority level is equal to something.
//	 *
//	 * the all job offers by seniority level is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySeniorityLevelIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where seniorityLevel equals to
//		// DEFAULT_SENIORITY_LEVEL
//		defaultJobOfferShouldBeFound("seniorityLevel.equals=" + DEFAULT_SENIORITY_LEVEL);
//
//		// Get all the jobOfferList where seniorityLevel equals to
//		// UPDATED_SENIORITY_LEVEL
//		defaultJobOfferShouldNotBeFound("seniorityLevel.equals=" + UPDATED_SENIORITY_LEVEL);
//	}

//	/**
//	 * Gets the all job offers by seniority level is in should work.
//	 *
//	 * the all job offers by seniority level is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySeniorityLevelIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where seniorityLevel in DEFAULT_SENIORITY_LEVEL or
//		// UPDATED_SENIORITY_LEVEL
//		defaultJobOfferShouldBeFound("seniorityLevel.in=" + DEFAULT_SENIORITY_LEVEL + "," + UPDATED_SENIORITY_LEVEL);
//
//		// Get all the jobOfferList where seniorityLevel equals to
//		// UPDATED_SENIORITY_LEVEL
//		defaultJobOfferShouldNotBeFound("seniorityLevel.in=" + UPDATED_SENIORITY_LEVEL);
//	}
//
//	/**
//	 * Gets the all job offers by seniority level is null or not null.
//	 *
//	 * the all job offers by seniority level is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySeniorityLevelIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where seniorityLevel is not null
//		defaultJobOfferShouldBeFound("seniorityLevel.specified=true");
//
//		// Get all the jobOfferList where seniorityLevel is null
//		defaultJobOfferShouldNotBeFound("seniorityLevel.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by salary offered is equal to something.
//	 *
//	 * the all job offers by salary offered is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySalaryOfferedIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where salaryOffered equals to DEFAULT_SALARY_OFFERED
//		defaultJobOfferShouldBeFound("salaryOffered.equals=" + DEFAULT_SALARY_OFFERED);
//
//		// Get all the jobOfferList where salaryOffered equals to UPDATED_SALARY_OFFERED
//		defaultJobOfferShouldNotBeFound("salaryOffered.equals=" + UPDATED_SALARY_OFFERED);
//	}
//
//	/**
//	 * Gets the all job offers by salary offered is in should work.
//	 *
//	 * the all job offers by salary offered is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySalaryOfferedIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where salaryOffered in DEFAULT_SALARY_OFFERED or
//		// UPDATED_SALARY_OFFERED
//		defaultJobOfferShouldBeFound("salaryOffered.in=" + DEFAULT_SALARY_OFFERED + "," + UPDATED_SALARY_OFFERED);
//
//		// Get all the jobOfferList where salaryOffered equals to UPDATED_SALARY_OFFERED
//		defaultJobOfferShouldNotBeFound("salaryOffered.in=" + UPDATED_SALARY_OFFERED);
//	}
//
//	/**
//	 * Gets the all job offers by salary offered is null or not null.
//	 *
//	 * the all job offers by salary offered is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySalaryOfferedIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where salaryOffered is not null
//		defaultJobOfferShouldBeFound("salaryOffered.specified=true");
//
//		// Get all the jobOfferList where salaryOffered is null
//		defaultJobOfferShouldNotBeFound("salaryOffered.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by status is equal to something.
//	 *
//	 * the all job offers by status is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStatusIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where status equals to DEFAULT_STATUS
//		defaultJobOfferShouldBeFound("status.equals=" + DEFAULT_STATUS);
//
//		// Get all the jobOfferList where status equals to UPDATED_STATUS
//		defaultJobOfferShouldNotBeFound("status.equals=" + UPDATED_STATUS);
//	}
//
//	/**
//	 * Gets the all job offers by status is in should work.
//	 *
//	 * the all job offers by status is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStatusIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where status in DEFAULT_STATUS or UPDATED_STATUS
//		defaultJobOfferShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);
//
//		// Get all the jobOfferList where status equals to UPDATED_STATUS
//		defaultJobOfferShouldNotBeFound("status.in=" + UPDATED_STATUS);
//	}
//
//	/**
//	 * Gets the all job offers by status is null or not null.
//	 *
//	 * the all job offers by status is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByStatusIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where status is not null
//		defaultJobOfferShouldBeFound("status.specified=true");
//
//		// Get all the jobOfferList where status is null
//		defaultJobOfferShouldNotBeFound("status.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by enabled is equal to something.
//	 *
//	 * the all job offers by enabled is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByEnabledIsEqualToSomething() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where enabled equals to DEFAULT_ENABLED
//		defaultJobOfferShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);
//
//		// Get all the jobOfferList where enabled equals to UPDATED_ENABLED
//		defaultJobOfferShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
//	}
//
//	/**
//	 * Gets the all job offers by enabled is in should work.
//	 *
//	 * the all job offers by enabled is in should work
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByEnabledIsInShouldWork() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
//		defaultJobOfferShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);
//
//		// Get all the jobOfferList where enabled equals to UPDATED_ENABLED
//		defaultJobOfferShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
//	}
//
//	/**
//	 * Gets the all job offers by enabled is null or not null.
//	 *
//	 * the all job offers by enabled is null or not null
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByEnabledIsNullOrNotNull() throws Exception {
//		// Initialize the database
//		jobOfferRepository.saveAndFlush(jobOffer);
//
//		// Get all the jobOfferList where enabled is not null
//		defaultJobOfferShouldBeFound("enabled.specified=true");
//
//		// Get all the jobOfferList where enabled is null
//		defaultJobOfferShouldNotBeFound("enabled.specified=false");
//	}
//
//	/**
//	 * Gets the all job offers by skill is equal to something.
//	 *
//	 * the all job offers by skill is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySkillIsEqualToSomething() throws Exception {
//		// Initialize the database
//		JobOfferSkill skill = JobOfferSkillResourceIntTest.createEntity(em);
//		em.persist(skill);
//		em.flush();
//		jobOffer.addSkill(skill);
//		jobOfferRepository.saveAndFlush(jobOffer);
//		Long skillId = skill.getId();
//
//		// Get all the jobOfferList where skill equals to skillId
//		defaultJobOfferShouldBeFound("skillId.equals=" + skillId);
//
//		// Get all the jobOfferList where skill equals to skillId + 1
//		defaultJobOfferShouldNotBeFound("skillId.equals=" + (skillId + 1));
//	}
//
//	/**
//	 * Gets the all job offers by company is equal to something.
//	 *
//	 * the all job offers by company is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByCompanyIsEqualToSomething() throws Exception {
//		// Initialize the database
//		Company company = CompanyResourceIntTest.createEntity(em);
//		em.persist(company);
//		em.flush();
//		jobOffer.setCompany(company);
//		jobOfferRepository.saveAndFlush(jobOffer);
//		Long companyId = company.getId();
//
//		// Get all the jobOfferList where company equals to companyId
//		defaultJobOfferShouldBeFound("companyId.equals=" + companyId);
//
//		// Get all the jobOfferList where company equals to companyId + 1
//		defaultJobOfferShouldNotBeFound("companyId.equals=" + (companyId + 1));
//	}
//
//	/**
//	 * Gets the all job offers by sector is equal to something.
//	 *
//	 * the all job offers by sector is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersBySectorIsEqualToSomething() throws Exception {
//		// Initialize the database
//		CompanySector sector = CompanySectorResourceIntTest.createEntity(em);
//		em.persist(sector);
//		em.flush();
//		jobOffer.setSector(sector);
//		jobOfferRepository.saveAndFlush(jobOffer);
//		Long sectorId = sector.getId();
//
//		// Get all the jobOfferList where sector equals to sectorId
//		defaultJobOfferShouldBeFound("sectorId.equals=" + sectorId);
//
//		// Get all the jobOfferList where sector equals to sectorId + 1
//		defaultJobOfferShouldNotBeFound("sectorId.equals=" + (sectorId + 1));
//	}
//
//	/**
//	 * Gets the all job offers by project is equal to something.
//	 *
//	 * the all job offers by project is equal to something
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getAllJobOffersByProjectIsEqualToSomething() throws Exception {
//		// Initialize the database
//		Project project = ProjectResourceIntTest.createEntity(em);
//		em.persist(project);
//		em.flush();
//		jobOffer.setProject(project);
//		jobOfferRepository.saveAndFlush(jobOffer);
//		Long projectId = project.getId();
//
//		// Get all the jobOfferList where project equals to projectId
//		defaultJobOfferShouldBeFound("projectId.equals=" + projectId);
//
//		// Get all the jobOfferList where project equals to projectId + 1
//		defaultJobOfferShouldNotBeFound("projectId.equals=" + (projectId + 1));
//	}
//
//	/**
//	 * Executes the search, and checks that the default entity is returned.
//	 *
//	 * @param filter the filter
//	 * @throws Exception the exception
//	 */
//	private void defaultJobOfferShouldBeFound(String filter) throws Exception {
//		restJobOfferMockMvc.perform(get("/api/job-offers?sort=id,desc&searchMode=true&" + filter)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$.[*].id").value(hasItem(jobOffer.getId().intValue())))
//				.andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
//				.andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
//				.andExpect(jsonPath("$.[*].jobShortDescription").value(hasItem(DEFAULT_JOB_SHORT_DESCRIPTION)))
//				.andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].responsibilitiesDescription")
//						.value(hasItem(DEFAULT_RESPONSIBILITIES_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].experiencesDescription")
//						.value(hasItem(DEFAULT_EXPERIENCES_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].attributesDescription")
//						.value(hasItem(DEFAULT_ATTRIBUTES_DESCRIPTION.toString())))
//				.andExpect(jsonPath("$.[*].jobFunctions").value(hasItem(DEFAULT_JOB_FUNCTIONS)))
//				.andExpect(jsonPath("$.[*].jobCity").value(hasItem(DEFAULT_JOB_CITY)))
//				.andExpect(jsonPath("$.[*].jobCountry").value(hasItem(DEFAULT_JOB_COUNTRY.toString())))
//				.andExpect(jsonPath("$.[*].employmentType").value(hasItem(DEFAULT_EMPLOYMENT_TYPE.toString())))
//				.andExpect(jsonPath("$.[*].seniorityLevel").value(hasItem(DEFAULT_SENIORITY_LEVEL.toString())))
//				.andExpect(jsonPath("$.[*].salaryOffered").value(hasItem(DEFAULT_SALARY_OFFERED)))
//				.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
//				.andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
//
//		// Check, that the count call also returns 1
//		restJobOfferMockMvc.perform(get("/api/job-offers/count?sort=id,desc&searchMode=true&" + filter)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(content().string("1"));
//	}
//
//	/**
//	 * Executes the search, and checks that the default entity is not returned.
//	 *
//	 * @param filter the filter
//	 * @throws Exception the exception
//	 */
//	private void defaultJobOfferShouldNotBeFound(String filter) throws Exception {
//		restJobOfferMockMvc.perform(get("/api/job-offers?sort=id,desc&searchMode=true&" + filter)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());
//
//		// Check, that the count call also returns 0
//		restJobOfferMockMvc.perform(get("/api/job-offers/count?sort=id,desc&searchMode=true&" + filter)).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(content().string("0"));
//	}
//
//	/**
//	 * Gets the non existing job offer.
//	 *
//	 * the non existing job offer
//	 * 
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void getNonExistingJobOffer() throws Exception {
//		// Get the jobOffer
//		restJobOfferMockMvc.perform(get("/api/job-offers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
//	}

	/**
	 * Update job offer.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateJobOffer() throws Exception {
		// Initialize the database
		jobOfferRepository.saveAndFlush(jobOffer);

		int databaseSizeBeforeUpdate = jobOfferRepository.findAll().size();

		// Update the jobOffer
		JobOffer updatedJobOffer = jobOfferRepository.findById(jobOffer.getId()).get();
		// Disconnect from session so that the updates on updatedJobOffer are not
		// directly saved in db
		em.detach(updatedJobOffer);
		updatedJobOffer.startDate(UPDATED_START_DATE).jobTitle(UPDATED_JOB_TITLE)
				.jobShortDescription(UPDATED_JOB_SHORT_DESCRIPTION).jobDescription(UPDATED_JOB_DESCRIPTION)
				.responsibilitiesDescription(UPDATED_RESPONSIBILITIES_DESCRIPTION)
				.experiencesDescription(UPDATED_EXPERIENCES_DESCRIPTION)
				.attributesDescription(UPDATED_ATTRIBUTES_DESCRIPTION).jobFunctions(UPDATED_JOB_FUNCTIONS)
				.jobCity(UPDATED_JOB_CITY).jobCountry(UPDATED_JOB_COUNTRY).employmentType(UPDATED_EMPLOYMENT_TYPE)
				.seniorityLevel(UPDATED_SENIORITY_LEVEL).salaryOffered(UPDATED_SALARY_OFFERED).status(UPDATED_STATUS)
				.enabled(UPDATED_ENABLED);
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(updatedJobOffer);

		restJobOfferMockMvc.perform(put("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isOk());

		// Validate the JobOffer in the database
		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeUpdate);
		JobOffer testJobOffer = jobOfferList.get(jobOfferList.size() - 1);
		assertThat(testJobOffer.getStartDate()).isEqualTo(UPDATED_START_DATE);
		assertThat(testJobOffer.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
		assertThat(testJobOffer.getJobShortDescription()).isEqualTo(UPDATED_JOB_SHORT_DESCRIPTION);
		assertThat(testJobOffer.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
		assertThat(testJobOffer.getResponsibilitiesDescription()).isEqualTo(UPDATED_RESPONSIBILITIES_DESCRIPTION);
		assertThat(testJobOffer.getExperiencesDescription()).isEqualTo(UPDATED_EXPERIENCES_DESCRIPTION);
		assertThat(testJobOffer.getAttributesDescription()).isEqualTo(UPDATED_ATTRIBUTES_DESCRIPTION);
		assertThat(testJobOffer.getJobFunctions()).isEqualTo(UPDATED_JOB_FUNCTIONS);
		assertThat(testJobOffer.getJobCity()).isEqualTo(UPDATED_JOB_CITY);
		assertThat(testJobOffer.getJobCountry()).isEqualTo(UPDATED_JOB_COUNTRY);
		assertThat(testJobOffer.getEmploymentType()).isEqualTo(UPDATED_EMPLOYMENT_TYPE);
		assertThat(testJobOffer.getSeniorityLevel()).isEqualTo(UPDATED_SENIORITY_LEVEL);
		assertThat(testJobOffer.getSalaryOffered()).isEqualTo(UPDATED_SALARY_OFFERED);
		assertThat(testJobOffer.getStatus()).isEqualTo(UPDATED_STATUS);
		assertThat(testJobOffer.isEnabled()).isEqualTo(UPDATED_ENABLED);
	}

	/**
	 * Update non existing job offer.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateNonExistingJobOffer() throws Exception {
		int databaseSizeBeforeUpdate = jobOfferRepository.findAll().size();

		// Create the JobOffer
		JobOfferDTO jobOfferDTO = jobOfferMapper.toDto(jobOffer);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restJobOfferMockMvc.perform(put("/api/job-offers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(jobOfferDTO))).andExpect(status().isBadRequest());

		// Validate the JobOffer in the database
		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Delete job offer.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void deleteJobOffer() throws Exception {
		// Initialize the database
		jobOfferRepository.saveAndFlush(jobOffer);

		int databaseSizeBeforeDelete = jobOfferRepository.findAll().size();

		// Delete the jobOffer
		restJobOfferMockMvc
				.perform(delete("/api/job-offers/{id}", jobOffer.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<JobOffer> jobOfferList = jobOfferRepository.findAll();
		assertThat(jobOfferList).hasSize(databaseSizeBeforeDelete - 1);
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
		TestUtil.equalsVerifier(JobOffer.class);
		JobOffer jobOffer1 = new JobOffer();
		jobOffer1.setId(1L);
		JobOffer jobOffer2 = new JobOffer();
		jobOffer2.setId(jobOffer1.getId());
		assertThat(jobOffer1).isEqualTo(jobOffer2);
		jobOffer2.setId(2L);
		assertThat(jobOffer1).isNotEqualTo(jobOffer2);
		jobOffer1.setId(null);
		assertThat(jobOffer1).isNotEqualTo(jobOffer2);
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
		TestUtil.equalsVerifier(JobOfferDTO.class);
		JobOfferDTO jobOfferDTO1 = new JobOfferDTO();
		jobOfferDTO1.setId(1L);
		JobOfferDTO jobOfferDTO2 = new JobOfferDTO();
		assertThat(jobOfferDTO1).isNotEqualTo(jobOfferDTO2);
		jobOfferDTO2.setId(jobOfferDTO1.getId());
		assertThat(jobOfferDTO1).isEqualTo(jobOfferDTO2);
		jobOfferDTO2.setId(2L);
		assertThat(jobOfferDTO1).isNotEqualTo(jobOfferDTO2);
		jobOfferDTO1.setId(null);
		assertThat(jobOfferDTO1).isNotEqualTo(jobOfferDTO2);
	}

	/**
	 * Test entity from id.
	 */
	@Test
	@Transactional
	@SuppressWarnings("checkstyle:magicNumber")
	public void testEntityFromId() {
		assertThat(jobOfferMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(jobOfferMapper.fromId(null)).isNull();
	}
}
