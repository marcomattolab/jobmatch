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
import it.aranciaict.jobmatch.domain.Document;
import it.aranciaict.jobmatch.domain.Education;
import it.aranciaict.jobmatch.domain.JobExperience;
import it.aranciaict.jobmatch.domain.Skill;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.GenderType;
import it.aranciaict.jobmatch.repository.CandidateRepository;
import it.aranciaict.jobmatch.service.CandidateQueryService;
import it.aranciaict.jobmatch.service.CandidateService;
import it.aranciaict.jobmatch.service.CompanySkillService;
import it.aranciaict.jobmatch.service.dto.CandidateDTO;
import it.aranciaict.jobmatch.service.mapper.CandidateMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the CandidateResource REST controller.
 *
 * @see CandidateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class CandidateResourceIntTest {

	/** The Constant DEFAULT_LAST_NAME. */
	private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";

	/** The Constant UPDATED_LAST_NAME. */
	private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

	/** The Constant DEFAULT_FIRST_NAME. */
	private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";

	/** The Constant UPDATED_FIRST_NAME. */
	private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

	/** The Constant DEFAULT_GENDER. */
	private static final GenderType DEFAULT_GENDER = GenderType.MALE;

	/** The Constant UPDATED_GENDER. */
	private static final GenderType UPDATED_GENDER = GenderType.FEMALE;

	/** The Constant DEFAULT_BIRTHDAY. */
	private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);

	/** The Constant UPDATED_BIRTHDAY. */
	private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

	/** The Constant DEFAULT_STREET_ADDRESS. */
	private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";

	/** The Constant UPDATED_STREET_ADDRESS. */
	private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

	/** The Constant DEFAULT_EMAIL. */
	private static final String DEFAULT_EMAIL = "aaaaa@aaaa.it";

	/** The Constant UPDATED_EMAIL. */
	private static final String UPDATED_EMAIL = "bbbbb@bbbb.com";

	/** The Constant DEFAULT_PHONE. */
	private static final String DEFAULT_PHONE = "11111111111";

	/** The Constant UPDATED_PHONE. */
	private static final String UPDATED_PHONE = "22222222222";

	/** The Constant DEFAULT_MOBILE_PHONE. */
	private static final String DEFAULT_MOBILE_PHONE = "11111111111";

	/** The Constant UPDATED_MOBILE_PHONE. */
	private static final String UPDATED_MOBILE_PHONE = "22222222222";

	/** The Constant DEFAULT_COUNTRY. */
	private static final Country DEFAULT_COUNTRY = Country.IT;

	/** The Constant UPDATED_COUNTRY. */
	private static final Country UPDATED_COUNTRY = Country.AF;

	/** The Constant DEFAULT_REGION. */
	private static final String DEFAULT_REGION = "AAAAAAAAAA";

	/** The Constant UPDATED_REGION. */
	private static final String UPDATED_REGION = "BBBBBBBBBB";

	/** The Constant DEFAULT_PROVINCE. */
	private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";

	/** The Constant UPDATED_PROVINCE. */
	private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

	/** The Constant DEFAULT_CITY. */
	private static final String DEFAULT_CITY = "AAAAAAAAAA";

	/** The Constant UPDATED_CITY. */
	private static final String UPDATED_CITY = "BBBBBBBBBB";

	/** The Constant DEFAULT_CAP. */
	private static final String DEFAULT_CAP = "AAAAAAAAAA";

	/** The Constant UPDATED_CAP. */
	private static final String UPDATED_CAP = "BBBBBBBBBB";

	/** The Constant DEFAULT_NOTE. */
	private static final String DEFAULT_NOTE = "AAAAAAAAAA";

	/** The Constant UPDATED_NOTE. */
	private static final String UPDATED_NOTE = "BBBBBBBBBB";

	/** The Constant DEFAULT_ENABLED. */
	private static final Boolean DEFAULT_ENABLED = false;

	/** The Constant UPDATED_ENABLED. */
	private static final Boolean UPDATED_ENABLED = true;

	/** The candidate repository. */
	@Autowired
	private CandidateRepository candidateRepository;

	/** The candidate mapper. */
	@Autowired
	private CandidateMapper candidateMapper;

	/** The candidate service. */
	@Autowired
	private CandidateService candidateService;

	/** The candidate query service. */
	@Autowired
	private CandidateQueryService candidateQueryService;

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

	/** The company skill service. */
	@Autowired
	private CompanySkillService companySkillService;

	/** The validator. */
	@Autowired
	private Validator validator;

	/** The rest candidate mock mvc. */
	private MockMvc restCandidateMockMvc;

	/** The candidate. */
	private Candidate candidate;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final CandidateResource candidateResource = new CandidateResource(candidateService, candidateQueryService, companySkillService);
		this.restCandidateMockMvc = MockMvcBuilders.standaloneSetup(candidateResource)
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
	 * @return the candidate
	 */
	public static Candidate createEntity(EntityManager em) {
		Candidate candidate = new Candidate().lastName(DEFAULT_LAST_NAME).firstName(DEFAULT_FIRST_NAME)
				.gender(DEFAULT_GENDER).birthday(DEFAULT_BIRTHDAY).streetAddress(DEFAULT_STREET_ADDRESS)
				.email(DEFAULT_EMAIL).phone(DEFAULT_PHONE).mobilePhone(DEFAULT_MOBILE_PHONE).country(DEFAULT_COUNTRY)
				.region(DEFAULT_REGION).province(DEFAULT_PROVINCE).city(DEFAULT_CITY).cap(DEFAULT_CAP)
				.note(DEFAULT_NOTE).enabled(DEFAULT_ENABLED);
		return candidate;
	}

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		candidate = createEntity(em);
	}

	/**
	 * Creates the candidate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	@WithMockUser(username = "username", roles={"ADMIN"})
	public void createCandidate() throws Exception {
		int databaseSizeBeforeCreate = candidateRepository.findAll().size();

		// Create the Candidate
		CandidateDTO candidateDTO = candidateMapper.toDto(candidate);
		restCandidateMockMvc.perform(post("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isCreated());

		// Validate the Candidate in the database
		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeCreate + 1);
		Candidate testCandidate = candidateList.get(candidateList.size() - 1);
		assertThat(testCandidate.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
		assertThat(testCandidate.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
		assertThat(testCandidate.getGender()).isEqualTo(DEFAULT_GENDER);
		assertThat(testCandidate.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
		assertThat(testCandidate.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
		assertThat(testCandidate.getEmail()).isEqualTo(DEFAULT_EMAIL);
		assertThat(testCandidate.getPhone()).isEqualTo(DEFAULT_PHONE);
		assertThat(testCandidate.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
		assertThat(testCandidate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
		assertThat(testCandidate.getRegion()).isEqualTo(DEFAULT_REGION);
		assertThat(testCandidate.getProvince()).isEqualTo(DEFAULT_PROVINCE);
		assertThat(testCandidate.getCity()).isEqualTo(DEFAULT_CITY);
		assertThat(testCandidate.getCap()).isEqualTo(DEFAULT_CAP);
		assertThat(testCandidate.getNote()).isEqualTo(DEFAULT_NOTE);
		assertThat(testCandidate.isEnabled()).isEqualTo(DEFAULT_ENABLED);
	}

	/**
	 * Creates the candidate with existing id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createCandidateWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = candidateRepository.findAll().size();

		// Create the Candidate with an existing ID
		candidate.setId(1L);
		CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

		// An entity with an existing ID cannot be created, so this API call must fail
		restCandidateMockMvc.perform(post("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isBadRequest());

		// Validate the Candidate in the database
		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeCreate);
	}

	/**
	 * Check last name is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkLastNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = candidateRepository.findAll().size();
		// set the field null
		candidate.setLastName(null);

		// Create the Candidate, which fails.
		CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

		restCandidateMockMvc.perform(post("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isBadRequest());

		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check first name is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkFirstNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = candidateRepository.findAll().size();
		// set the field null
		candidate.setFirstName(null);

		// Create the Candidate, which fails.
		CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

		restCandidateMockMvc.perform(post("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isBadRequest());

		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeTest);
	}

//	/**
//	 * Check email is required.
//	 *
//	 * @throws Exception the exception
//	 */
//	@Test
//	@Transactional
//	public void checkEmailIsRequired() throws Exception {
//		int databaseSizeBeforeTest = candidateRepository.findAll().size();
//		// set the field null
//		candidate.setEmail(null);
//
//		// Create the Candidate, which fails.
//		CandidateDTO candidateDTO = candidateMapper.toDto(candidate);
//
//		restCandidateMockMvc.perform(post("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
//				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isBadRequest());
//
//		List<Candidate> candidateList = candidateRepository.findAll();
//		assertThat(candidateList).hasSize(databaseSizeBeforeTest);
//	}

	/**
	 * Gets the all candidates.
	 *
	 * the all candidates
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidates() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList
		restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
				.andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
				.andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
				.andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
				.andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
				.andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
				.andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
				.andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
				.andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
				.andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
				.andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
				.andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
				.andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
				.andExpect(jsonPath("$.[*].cap").value(hasItem(DEFAULT_CAP.toString())))
//				.andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
				.andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
	}

	/**
	 * Gets the candidate.
	 *
	 * the candidate
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getCandidate() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get the candidate
		restCandidateMockMvc.perform(get("/api/candidates/{id}", candidate.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
				.andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
				.andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
				.andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
				.andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
				.andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
				.andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
				.andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
				.andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
				.andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
				.andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
				.andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
				.andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
				.andExpect(jsonPath("$.cap").value(DEFAULT_CAP.toString()))
				.andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
				.andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
	}

	/**
	 * Gets the all candidates by last name is equal to something.
	 *
	 * the all candidates by last name is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByLastNameIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where lastName equals to DEFAULT_LAST_NAME
		defaultCandidateShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

		// Get all the candidateList where lastName equals to UPDATED_LAST_NAME
		defaultCandidateShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
	}

	/**
	 * Gets the all candidates by last name is in should work.
	 *
	 * the all candidates by last name is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByLastNameIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where lastName in DEFAULT_LAST_NAME or
		// UPDATED_LAST_NAME
		defaultCandidateShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

		// Get all the candidateList where lastName equals to UPDATED_LAST_NAME
		defaultCandidateShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
	}

	/**
	 * Gets the all candidates by last name is null or not null.
	 *
	 * the all candidates by last name is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByLastNameIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where lastName is not null
		defaultCandidateShouldBeFound("lastName.specified=true");

		// Get all the candidateList where lastName is null
		defaultCandidateShouldNotBeFound("lastName.specified=false");
	}

	/**
	 * Gets the all candidates by first name is equal to something.
	 *
	 * the all candidates by first name is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByFirstNameIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where firstName equals to DEFAULT_FIRST_NAME
		defaultCandidateShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

		// Get all the candidateList where firstName equals to UPDATED_FIRST_NAME
		defaultCandidateShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
	}

	/**
	 * Gets the all candidates by first name is in should work.
	 *
	 * the all candidates by first name is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByFirstNameIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where firstName in DEFAULT_FIRST_NAME or
		// UPDATED_FIRST_NAME
		defaultCandidateShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

		// Get all the candidateList where firstName equals to UPDATED_FIRST_NAME
		defaultCandidateShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
	}

	/**
	 * Gets the all candidates by first name is null or not null.
	 *
	 * the all candidates by first name is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByFirstNameIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where firstName is not null
		defaultCandidateShouldBeFound("firstName.specified=true");

		// Get all the candidateList where firstName is null
		defaultCandidateShouldNotBeFound("firstName.specified=false");
	}

	/**
	 * Gets the all candidates by gender is equal to something.
	 *
	 * the all candidates by gender is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByGenderIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where gender equals to DEFAULT_GENDER
		defaultCandidateShouldBeFound("gender.equals=" + DEFAULT_GENDER);

		// Get all the candidateList where gender equals to UPDATED_GENDER
		defaultCandidateShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
	}

	/**
	 * Gets the all candidates by gender is in should work.
	 *
	 * the all candidates by gender is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByGenderIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where gender in DEFAULT_GENDER or UPDATED_GENDER
		defaultCandidateShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

		// Get all the candidateList where gender equals to UPDATED_GENDER
		defaultCandidateShouldNotBeFound("gender.in=" + UPDATED_GENDER);
	}

	/**
	 * Gets the all candidates by gender is null or not null.
	 *
	 * the all candidates by gender is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByGenderIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where gender is not null
		defaultCandidateShouldBeFound("gender.specified=true");

		// Get all the candidateList where gender is null
		defaultCandidateShouldNotBeFound("gender.specified=false");
	}

	/**
	 * Gets the all candidates by birthday is equal to something.
	 *
	 * the all candidates by birthday is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByBirthdayIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where birthday equals to DEFAULT_BIRTHDAY
		defaultCandidateShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

		// Get all the candidateList where birthday equals to UPDATED_BIRTHDAY
		defaultCandidateShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
	}

	/**
	 * Gets the all candidates by birthday is in should work.
	 *
	 * the all candidates by birthday is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByBirthdayIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where birthday in DEFAULT_BIRTHDAY or
		// UPDATED_BIRTHDAY
		defaultCandidateShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

		// Get all the candidateList where birthday equals to UPDATED_BIRTHDAY
		defaultCandidateShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
	}

	/**
	 * Gets the all candidates by birthday is null or not null.
	 *
	 * the all candidates by birthday is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByBirthdayIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where birthday is not null
		defaultCandidateShouldBeFound("birthday.specified=true");

		// Get all the candidateList where birthday is null
		defaultCandidateShouldNotBeFound("birthday.specified=false");
	}

	/**
	 * Gets the all candidates by birthday is greater than or equal to something.
	 *
	 * the all candidates by birthday is greater than or equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where birthday greater than or equals to
		// DEFAULT_BIRTHDAY
		defaultCandidateShouldBeFound("birthday.greaterOrEqualThan=" + DEFAULT_BIRTHDAY);

		// Get all the candidateList where birthday greater than or equals to
		// UPDATED_BIRTHDAY
		defaultCandidateShouldNotBeFound("birthday.greaterOrEqualThan=" + UPDATED_BIRTHDAY);
	}

	/**
	 * Gets the all candidates by birthday is less than something.
	 *
	 * the all candidates by birthday is less than something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByBirthdayIsLessThanSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where birthday less than or equals to
		// DEFAULT_BIRTHDAY
		defaultCandidateShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

		// Get all the candidateList where birthday less than or equals to
		// UPDATED_BIRTHDAY
		defaultCandidateShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
	}

	/**
	 * Gets the all candidates by street address is equal to something.
	 *
	 * the all candidates by street address is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByStreetAddressIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where streetAddress equals to
		// DEFAULT_STREET_ADDRESS
		defaultCandidateShouldBeFound("streetAddress.equals=" + DEFAULT_STREET_ADDRESS);

		// Get all the candidateList where streetAddress equals to
		// UPDATED_STREET_ADDRESS
		defaultCandidateShouldNotBeFound("streetAddress.equals=" + UPDATED_STREET_ADDRESS);
	}

	/**
	 * Gets the all candidates by street address is in should work.
	 *
	 * the all candidates by street address is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByStreetAddressIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where streetAddress in DEFAULT_STREET_ADDRESS or
		// UPDATED_STREET_ADDRESS
		defaultCandidateShouldBeFound("streetAddress.in=" + DEFAULT_STREET_ADDRESS + "," + UPDATED_STREET_ADDRESS);

		// Get all the candidateList where streetAddress equals to
		// UPDATED_STREET_ADDRESS
		defaultCandidateShouldNotBeFound("streetAddress.in=" + UPDATED_STREET_ADDRESS);
	}

	/**
	 * Gets the all candidates by street address is null or not null.
	 *
	 * the all candidates by street address is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByStreetAddressIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where streetAddress is not null
		defaultCandidateShouldBeFound("streetAddress.specified=true");

		// Get all the candidateList where streetAddress is null
		defaultCandidateShouldNotBeFound("streetAddress.specified=false");
	}

	/**
	 * Gets the all candidates by email is equal to something.
	 *
	 * the all candidates by email is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEmailIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where email equals to DEFAULT_EMAIL
		defaultCandidateShouldBeFound("email.equals=" + DEFAULT_EMAIL);

		// Get all the candidateList where email equals to UPDATED_EMAIL
		defaultCandidateShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
	}

	/**
	 * Gets the all candidates by email is in should work.
	 *
	 * the all candidates by email is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEmailIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where email in DEFAULT_EMAIL or UPDATED_EMAIL
		defaultCandidateShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

		// Get all the candidateList where email equals to UPDATED_EMAIL
		defaultCandidateShouldNotBeFound("email.in=" + UPDATED_EMAIL);
	}

	/**
	 * Gets the all candidates by email is null or not null.
	 *
	 * the all candidates by email is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEmailIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where email is not null
		defaultCandidateShouldBeFound("email.specified=true");

		// Get all the candidateList where email is null
		defaultCandidateShouldNotBeFound("email.specified=false");
	}

	/**
	 * Gets the all candidates by phone is equal to something.
	 *
	 * the all candidates by phone is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByPhoneIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where phone equals to DEFAULT_PHONE
		defaultCandidateShouldBeFound("phone.equals=" + DEFAULT_PHONE);

		// Get all the candidateList where phone equals to UPDATED_PHONE
		defaultCandidateShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
	}

	/**
	 * Gets the all candidates by phone is in should work.
	 *
	 * the all candidates by phone is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByPhoneIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where phone in DEFAULT_PHONE or UPDATED_PHONE
		defaultCandidateShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

		// Get all the candidateList where phone equals to UPDATED_PHONE
		defaultCandidateShouldNotBeFound("phone.in=" + UPDATED_PHONE);
	}

	/**
	 * Gets the all candidates by phone is null or not null.
	 *
	 * the all candidates by phone is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByPhoneIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where phone is not null
		defaultCandidateShouldBeFound("phone.specified=true");

		// Get all the candidateList where phone is null
		defaultCandidateShouldNotBeFound("phone.specified=false");
	}

	/**
	 * Gets the all candidates by mobile phone is equal to something.
	 *
	 * the all candidates by mobile phone is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByMobilePhoneIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where mobilePhone equals to DEFAULT_MOBILE_PHONE
		defaultCandidateShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

		// Get all the candidateList where mobilePhone equals to UPDATED_MOBILE_PHONE
		defaultCandidateShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
	}

	/**
	 * Gets the all candidates by mobile phone is in should work.
	 *
	 * the all candidates by mobile phone is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByMobilePhoneIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where mobilePhone in DEFAULT_MOBILE_PHONE or
		// UPDATED_MOBILE_PHONE
		defaultCandidateShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

		// Get all the candidateList where mobilePhone equals to UPDATED_MOBILE_PHONE
		defaultCandidateShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
	}

	/**
	 * Gets the all candidates by mobile phone is null or not null.
	 *
	 * the all candidates by mobile phone is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByMobilePhoneIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where mobilePhone is not null
		defaultCandidateShouldBeFound("mobilePhone.specified=true");

		// Get all the candidateList where mobilePhone is null
		defaultCandidateShouldNotBeFound("mobilePhone.specified=false");
	}

	/**
	 * Gets the all candidates by country is equal to something.
	 *
	 * the all candidates by country is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCountryIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where country equals to DEFAULT_COUNTRY
		defaultCandidateShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

		// Get all the candidateList where country equals to UPDATED_COUNTRY
		defaultCandidateShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
	}

	/**
	 * Gets the all candidates by country is in should work.
	 *
	 * the all candidates by country is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCountryIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
		defaultCandidateShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

		// Get all the candidateList where country equals to UPDATED_COUNTRY
		defaultCandidateShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
	}

	/**
	 * Gets the all candidates by country is null or not null.
	 *
	 * the all candidates by country is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCountryIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where country is not null
		defaultCandidateShouldBeFound("country.specified=true");

		// Get all the candidateList where country is null
		defaultCandidateShouldNotBeFound("country.specified=false");
	}

	/**
	 * Gets the all candidates by region is equal to something.
	 *
	 * the all candidates by region is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByRegionIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where region equals to DEFAULT_REGION
		defaultCandidateShouldBeFound("region.equals=" + DEFAULT_REGION);

		// Get all the candidateList where region equals to UPDATED_REGION
		defaultCandidateShouldNotBeFound("region.equals=" + UPDATED_REGION);
	}

	/**
	 * Gets the all candidates by region is in should work.
	 *
	 * the all candidates by region is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByRegionIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where region in DEFAULT_REGION or UPDATED_REGION
		defaultCandidateShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

		// Get all the candidateList where region equals to UPDATED_REGION
		defaultCandidateShouldNotBeFound("region.in=" + UPDATED_REGION);
	}

	/**
	 * Gets the all candidates by region is null or not null.
	 *
	 * the all candidates by region is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByRegionIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where region is not null
		defaultCandidateShouldBeFound("region.specified=true");

		// Get all the candidateList where region is null
		defaultCandidateShouldNotBeFound("region.specified=false");
	}

	/**
	 * Gets the all candidates by province is equal to something.
	 *
	 * the all candidates by province is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByProvinceIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where province equals to DEFAULT_PROVINCE
		defaultCandidateShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

		// Get all the candidateList where province equals to UPDATED_PROVINCE
		defaultCandidateShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
	}

	/**
	 * Gets the all candidates by province is in should work.
	 *
	 * the all candidates by province is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByProvinceIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where province in DEFAULT_PROVINCE or
		// UPDATED_PROVINCE
		defaultCandidateShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

		// Get all the candidateList where province equals to UPDATED_PROVINCE
		defaultCandidateShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
	}

	/**
	 * Gets the all candidates by province is null or not null.
	 *
	 * the all candidates by province is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByProvinceIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where province is not null
		defaultCandidateShouldBeFound("province.specified=true");

		// Get all the candidateList where province is null
		defaultCandidateShouldNotBeFound("province.specified=false");
	}

	/**
	 * Gets the all candidates by city is equal to something.
	 *
	 * the all candidates by city is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCityIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where city equals to DEFAULT_CITY
		defaultCandidateShouldBeFound("city.equals=" + DEFAULT_CITY);

		// Get all the candidateList where city equals to UPDATED_CITY
		defaultCandidateShouldNotBeFound("city.equals=" + UPDATED_CITY);
	}

	/**
	 * Gets the all candidates by city is in should work.
	 *
	 * the all candidates by city is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCityIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where city in DEFAULT_CITY or UPDATED_CITY
		defaultCandidateShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

		// Get all the candidateList where city equals to UPDATED_CITY
		defaultCandidateShouldNotBeFound("city.in=" + UPDATED_CITY);
	}

	/**
	 * Gets the all candidates by city is null or not null.
	 *
	 * the all candidates by city is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCityIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where city is not null
		defaultCandidateShouldBeFound("city.specified=true");

		// Get all the candidateList where city is null
		defaultCandidateShouldNotBeFound("city.specified=false");
	}

	/**
	 * Gets the all candidates by cap is equal to something.
	 *
	 * the all candidates by cap is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCapIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where cap equals to DEFAULT_CAP
		defaultCandidateShouldBeFound("cap.equals=" + DEFAULT_CAP);

		// Get all the candidateList where cap equals to UPDATED_CAP
		defaultCandidateShouldNotBeFound("cap.equals=" + UPDATED_CAP);
	}

	/**
	 * Gets the all candidates by cap is in should work.
	 *
	 * the all candidates by cap is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCapIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where cap in DEFAULT_CAP or UPDATED_CAP
		defaultCandidateShouldBeFound("cap.in=" + DEFAULT_CAP + "," + UPDATED_CAP);

		// Get all the candidateList where cap equals to UPDATED_CAP
		defaultCandidateShouldNotBeFound("cap.in=" + UPDATED_CAP);
	}

	/**
	 * Gets the all candidates by cap is null or not null.
	 *
	 * the all candidates by cap is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByCapIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where cap is not null
		defaultCandidateShouldBeFound("cap.specified=true");

		// Get all the candidateList where cap is null
		defaultCandidateShouldNotBeFound("cap.specified=false");
	}

	/**
	 * Gets the all candidates by enabled is equal to something.
	 *
	 * the all candidates by enabled is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEnabledIsEqualToSomething() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where enabled equals to DEFAULT_ENABLED
		defaultCandidateShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

		// Get all the candidateList where enabled equals to UPDATED_ENABLED
		defaultCandidateShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
	}

	/**
	 * Gets the all candidates by enabled is in should work.
	 *
	 * the all candidates by enabled is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEnabledIsInShouldWork() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
		defaultCandidateShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

		// Get all the candidateList where enabled equals to UPDATED_ENABLED
		defaultCandidateShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
	}

	/**
	 * Gets the all candidates by enabled is null or not null.
	 *
	 * the all candidates by enabled is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEnabledIsNullOrNotNull() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		// Get all the candidateList where enabled is not null
		defaultCandidateShouldBeFound("enabled.specified=true");

		// Get all the candidateList where enabled is null
		defaultCandidateShouldNotBeFound("enabled.specified=false");
	}

	/**
	 * Gets the all candidates by user is equal to something.
	 *
	 * the all candidates by user is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByUserIsEqualToSomething() throws Exception {
		// Initialize the database
		User user = UserResourceIntTest.createEntity(em);
		em.persist(user);
		em.flush();
		candidate.setUser(user);
		candidateRepository.saveAndFlush(candidate);
		Long userId = user.getId();

		// Get all the candidateList where user equals to userId
		defaultCandidateShouldBeFound("userId.equals=" + userId);

		// Get all the candidateList where user equals to userId + 1
		defaultCandidateShouldNotBeFound("userId.equals=" + (userId + 1));
	}

	/**
	 * Gets the all candidates by document is equal to something.
	 *
	 * the all candidates by document is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByDocumentIsEqualToSomething() throws Exception {
		// Initialize the database
		Document document = DocumentResourceIntTest.createEntity(em);
		em.persist(document);
		em.flush();
		candidate.addDocument(document);
		candidateRepository.saveAndFlush(candidate);
		Long documentId = document.getId();

		// Get all the candidateList where document equals to documentId
		defaultCandidateShouldBeFound("documentId.equals=" + documentId);

		// Get all the candidateList where document equals to documentId + 1
		defaultCandidateShouldNotBeFound("documentId.equals=" + (documentId + 1));
	}

	/**
	 * Gets the all candidates by job experience is equal to something.
	 *
	 * the all candidates by job experience is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByJobExperienceIsEqualToSomething() throws Exception {
		// Initialize the database
		JobExperience jobExperience = JobExperienceResourceIntTest.createEntity(em);
		em.persist(jobExperience);
		em.flush();
		candidate.addJobExperience(jobExperience);
		candidateRepository.saveAndFlush(candidate);
		Long jobExperienceId = jobExperience.getId();

		// Get all the candidateList where jobExperience equals to jobExperienceId
		defaultCandidateShouldBeFound("jobExperienceId.equals=" + jobExperienceId);

		// Get all the candidateList where jobExperience equals to jobExperienceId + 1
		defaultCandidateShouldNotBeFound("jobExperienceId.equals=" + (jobExperienceId + 1));
	}

	/**
	 * Gets the all candidates by education is equal to something.
	 *
	 * the all candidates by education is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesByEducationIsEqualToSomething() throws Exception {
		// Initialize the database
		Education education = EducationResourceIntTest.createEntity(em);
		em.persist(education);
		em.flush();
		candidate.addEducation(education);
		candidateRepository.saveAndFlush(candidate);
		Long educationId = education.getId();

		// Get all the candidateList where education equals to educationId
		defaultCandidateShouldBeFound("educationId.equals=" + educationId);

		// Get all the candidateList where education equals to educationId + 1
		defaultCandidateShouldNotBeFound("educationId.equals=" + (educationId + 1));
	}

	/**
	 * Gets the all candidates by skill is equal to something.
	 *
	 * the all candidates by skill is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllCandidatesBySkillIsEqualToSomething() throws Exception {
		// Initialize the database
		Skill skill = SkillResourceIntTest.createEntity(em);
		em.persist(skill);
		em.flush();
		candidate.addSkill(skill);
		candidateRepository.saveAndFlush(candidate);
		Long skillId = skill.getId();

		// Get all the candidateList where skill equals to skillId
		defaultCandidateShouldBeFound("skillId.equals=" + skillId);

		// Get all the candidateList where skill equals to skillId + 1
		defaultCandidateShouldNotBeFound("skillId.equals=" + (skillId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultCandidateShouldBeFound(String filter) throws Exception {
		restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
				.andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
				.andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
				.andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
				.andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
				.andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
				.andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
				.andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
				.andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
				.andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
				.andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
				.andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
				.andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
				.andExpect(jsonPath("$.[*].cap").value(hasItem(DEFAULT_CAP)))
//				.andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
				.andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

		// Check, that the count call also returns 1
		restCandidateMockMvc.perform(get("/api/candidates/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultCandidateShouldNotBeFound(String filter) throws Exception {
		restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restCandidateMockMvc.perform(get("/api/candidates/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("0"));
	}

	/**
	 * Gets the non existing candidate.
	 *
	 * the non existing candidate
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getNonExistingCandidate() throws Exception {
		// Get the candidate
		restCandidateMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	/**
	 * Update candidate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	@WithMockUser(username = "username", roles={"ADMIN"})
	public void updateCandidate() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

		// Update the candidate
		Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).get();
		// Disconnect from session so that the updates on updatedCandidate are not
		// directly saved in db
		em.detach(updatedCandidate);
		updatedCandidate.lastName(UPDATED_LAST_NAME).firstName(UPDATED_FIRST_NAME).gender(UPDATED_GENDER)
				.birthday(UPDATED_BIRTHDAY).streetAddress(UPDATED_STREET_ADDRESS).email(UPDATED_EMAIL)
				.phone(UPDATED_PHONE).mobilePhone(UPDATED_MOBILE_PHONE).country(UPDATED_COUNTRY).region(UPDATED_REGION)
				.province(UPDATED_PROVINCE).city(UPDATED_CITY).cap(UPDATED_CAP).note(UPDATED_NOTE)
				.enabled(UPDATED_ENABLED);
		CandidateDTO candidateDTO = candidateMapper.toDto(updatedCandidate);

		restCandidateMockMvc.perform(put("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isOk());

		// Validate the Candidate in the database
		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
		Candidate testCandidate = candidateList.get(candidateList.size() - 1);
		assertThat(testCandidate.getLastName()).isEqualTo(UPDATED_LAST_NAME);
		assertThat(testCandidate.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
		assertThat(testCandidate.getGender()).isEqualTo(UPDATED_GENDER);
		assertThat(testCandidate.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
		assertThat(testCandidate.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
		assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
		assertThat(testCandidate.getPhone()).isEqualTo(UPDATED_PHONE);
		assertThat(testCandidate.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
		assertThat(testCandidate.getCountry()).isEqualTo(UPDATED_COUNTRY);
		assertThat(testCandidate.getRegion()).isEqualTo(UPDATED_REGION);
		assertThat(testCandidate.getProvince()).isEqualTo(UPDATED_PROVINCE);
		assertThat(testCandidate.getCity()).isEqualTo(UPDATED_CITY);
		assertThat(testCandidate.getCap()).isEqualTo(UPDATED_CAP);
		assertThat(testCandidate.getNote()).isEqualTo(UPDATED_NOTE);
		assertThat(testCandidate.isEnabled()).isEqualTo(UPDATED_ENABLED);
	}

	/**
	 * Update non existing candidate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateNonExistingCandidate() throws Exception {
		int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

		// Create the Candidate
		CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restCandidateMockMvc.perform(put("/api/candidates").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(candidateDTO))).andExpect(status().isBadRequest());

		// Validate the Candidate in the database
		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Delete candidate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void deleteCandidate() throws Exception {
		// Initialize the database
		candidateRepository.saveAndFlush(candidate);

		int databaseSizeBeforeDelete = candidateRepository.findAll().size();

		// Delete the candidate
		restCandidateMockMvc
				.perform(delete("/api/candidates/{id}", candidate.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Candidate> candidateList = candidateRepository.findAll();
		assertThat(candidateList).hasSize(databaseSizeBeforeDelete - 1);
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
		TestUtil.equalsVerifier(Candidate.class);
		Candidate candidate1 = new Candidate();
		candidate1.setId(1L);
		Candidate candidate2 = new Candidate();
		candidate2.setId(candidate1.getId());
		assertThat(candidate1).isEqualTo(candidate2);
		candidate2.setId(2L);
		assertThat(candidate1).isNotEqualTo(candidate2);
		candidate1.setId(null);
		assertThat(candidate1).isNotEqualTo(candidate2);
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
		TestUtil.equalsVerifier(CandidateDTO.class);
		CandidateDTO candidateDTO1 = new CandidateDTO();
		candidateDTO1.setId(1L);
		CandidateDTO candidateDTO2 = new CandidateDTO();
		assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
		candidateDTO2.setId(candidateDTO1.getId());
		assertThat(candidateDTO1).isEqualTo(candidateDTO2);
		candidateDTO2.setId(2L);
		assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
		candidateDTO1.setId(null);
		assertThat(candidateDTO1).isNotEqualTo(candidateDTO2);
	}

	/**
	 * Test entity from id.
	 */
	@Test
	@Transactional
	@SuppressWarnings("checkstyle:magicNumber")
	public void testEntityFromId() {
		assertThat(candidateMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(candidateMapper.fromId(null)).isNull();
	}
}
