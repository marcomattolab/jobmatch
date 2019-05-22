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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.CompanySector;
import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.Project;
import it.aranciaict.jobmatch.domain.enumeration.ProjectStatus;
import it.aranciaict.jobmatch.repository.ProjectRepository;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.ProjectQueryService;
import it.aranciaict.jobmatch.service.ProjectService;
import it.aranciaict.jobmatch.service.dto.ProjectDTO;
import it.aranciaict.jobmatch.service.mapper.ProjectMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

// TODO: Auto-generated Javadoc
/**
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class ProjectResourceIntTest {

	/** The Constant DEFAULT_TITLE. */
	private static final String DEFAULT_TITLE = "AAAAAAAAAA";

	/** The Constant UPDATED_TITLE. */
	private static final String UPDATED_TITLE = "BBBBBBBBBB";

	/** The Constant DEFAULT_DESCRIPTION. */
	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";

	/** The Constant UPDATED_DESCRIPTION. */
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	/** The Constant DEFAULT_START_DATE. */
	private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);

	/** The Constant UPDATED_START_DATE. */
	private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

	/** The Constant DEFAULT_STATUS. */
	private static final ProjectStatus DEFAULT_STATUS = ProjectStatus.ACTIVE;

	/** The Constant UPDATED_STATUS. */
	private static final ProjectStatus UPDATED_STATUS = ProjectStatus.ENDED;

	/** The Constant DEFAULT_END_DATE. */
	private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);

	/** The Constant UPDATED_END_DATE. */
	private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

	/** The project repository. */
	@Autowired
	private ProjectRepository projectRepository;

	/** The project mapper. */
	@Autowired
	private ProjectMapper projectMapper;

	/** The project service. */
	@Autowired
	private ProjectService projectService;

	/** The project query service. */
	@Autowired
	private ProjectQueryService projectQueryService;

	/** The company service. */
	@Autowired
	private CompanyService companyService;

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

	/** The rest project mock mvc. */
	private MockMvc restProjectMockMvc;

	/** The project. */
	private Project project;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final ProjectResource projectResource = new ProjectResource(projectService, projectQueryService,
				companyService);
		this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
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
	 * @return the project
	 */
	public static Project createEntity(EntityManager em) {
		Project project = new Project().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION)
				.startDate(DEFAULT_START_DATE).status(DEFAULT_STATUS).endDate(DEFAULT_END_DATE);
		return project;
	}

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		project = createEntity(em);
	}

	/**
	 * Creates the project.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createProject() throws Exception {
		int databaseSizeBeforeCreate = projectRepository.findAll().size();

		// Create the Project
		ProjectDTO projectDTO = projectMapper.toDto(project);
		restProjectMockMvc.perform(post("/api/projects").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(projectDTO))).andExpect(status().isCreated());

		// Validate the Project in the database
		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
		Project testProject = projectList.get(projectList.size() - 1);
		assertThat(testProject.getTitle()).isEqualTo(DEFAULT_TITLE);
		assertThat(testProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
		assertThat(testProject.getStatus()).isEqualTo(DEFAULT_STATUS);
		assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
	}

	/**
	 * Creates the project with existing id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createProjectWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = projectRepository.findAll().size();

		// Create the Project with an existing ID
		project.setId(1L);
		ProjectDTO projectDTO = projectMapper.toDto(project);

		// An entity with an existing ID cannot be created, so this API call must fail
		restProjectMockMvc.perform(post("/api/projects").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(projectDTO))).andExpect(status().isBadRequest());

		// Validate the Project in the database
		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeCreate);
	}

	/**
	 * Check title is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkTitleIsRequired() throws Exception {
		int databaseSizeBeforeTest = projectRepository.findAll().size();
		// set the field null
		project.setTitle(null);

		// Create the Project, which fails.
		ProjectDTO projectDTO = projectMapper.toDto(project);

		restProjectMockMvc.perform(post("/api/projects").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(projectDTO))).andExpect(status().isBadRequest());

		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check start date is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkStartDateIsRequired() throws Exception {
		int databaseSizeBeforeTest = projectRepository.findAll().size();
		// set the field null
		project.setStartDate(null);

		// Create the Project, which fails.
		ProjectDTO projectDTO = projectMapper.toDto(project);

		restProjectMockMvc.perform(post("/api/projects").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(projectDTO))).andExpect(status().isBadRequest());

		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Gets the all projects.
	 *
	 * the all projects
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjects() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList
		restProjectMockMvc.perform(get("/api/projects?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
				.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
				.andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
				.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
				.andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
	}

	/**
	 * Gets the project.
	 *
	 * the project
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getProject() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get the project
		restProjectMockMvc.perform(get("/api/projects/{id}", project.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(project.getId().intValue()))
				.andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
				.andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
				.andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
				.andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
				.andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
	}

	/**
	 * Gets the all projects by title is equal to something.
	 *
	 * the all projects by title is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByTitleIsEqualToSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where title equals to DEFAULT_TITLE
		defaultProjectShouldBeFound("title.equals=" + DEFAULT_TITLE);

		// Get all the projectList where title equals to UPDATED_TITLE
		defaultProjectShouldNotBeFound("title.equals=" + UPDATED_TITLE);
	}

	/**
	 * Gets the all projects by title is in should work.
	 *
	 * the all projects by title is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByTitleIsInShouldWork() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where title in DEFAULT_TITLE or UPDATED_TITLE
		defaultProjectShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

		// Get all the projectList where title equals to UPDATED_TITLE
		defaultProjectShouldNotBeFound("title.in=" + UPDATED_TITLE);
	}

	/**
	 * Gets the all projects by title is null or not null.
	 *
	 * the all projects by title is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByTitleIsNullOrNotNull() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where title is not null
		defaultProjectShouldBeFound("title.specified=true");

		// Get all the projectList where title is null
		defaultProjectShouldNotBeFound("title.specified=false");
	}

	/**
	 * Gets the all projects by start date is equal to something.
	 *
	 * the all projects by start date is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStartDateIsEqualToSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where startDate equals to DEFAULT_START_DATE
		defaultProjectShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

		// Get all the projectList where startDate equals to UPDATED_START_DATE
		defaultProjectShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
	}

	/**
	 * Gets the all projects by start date is in should work.
	 *
	 * the all projects by start date is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStartDateIsInShouldWork() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where startDate in DEFAULT_START_DATE or
		// UPDATED_START_DATE
		defaultProjectShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

		// Get all the projectList where startDate equals to UPDATED_START_DATE
		defaultProjectShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
	}

	/**
	 * Gets the all projects by start date is null or not null.
	 *
	 * the all projects by start date is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStartDateIsNullOrNotNull() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where startDate is not null
		defaultProjectShouldBeFound("startDate.specified=true");

		// Get all the projectList where startDate is null
		defaultProjectShouldNotBeFound("startDate.specified=false");
	}

	/**
	 * Gets the all projects by start date is greater than or equal to something.
	 *
	 * the all projects by start date is greater than or equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where startDate greater than or equals to
		// DEFAULT_START_DATE
		defaultProjectShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

		// Get all the projectList where startDate greater than or equals to
		// UPDATED_START_DATE
		defaultProjectShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
	}

	/**
	 * Gets the all projects by start date is less than something.
	 *
	 * the all projects by start date is less than something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStartDateIsLessThanSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where startDate less than or equals to
		// DEFAULT_START_DATE
		defaultProjectShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

		// Get all the projectList where startDate less than or equals to
		// UPDATED_START_DATE
		defaultProjectShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
	}

	/**
	 * Gets the all projects by status is equal to something.
	 *
	 * the all projects by status is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStatusIsEqualToSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where status equals to DEFAULT_STATUS
		defaultProjectShouldBeFound("status.equals=" + DEFAULT_STATUS);

		// Get all the projectList where status equals to UPDATED_STATUS
		defaultProjectShouldNotBeFound("status.equals=" + UPDATED_STATUS);
	}

	/**
	 * Gets the all projects by status is in should work.
	 *
	 * the all projects by status is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStatusIsInShouldWork() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where status in DEFAULT_STATUS or UPDATED_STATUS
		defaultProjectShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

		// Get all the projectList where status equals to UPDATED_STATUS
		defaultProjectShouldNotBeFound("status.in=" + UPDATED_STATUS);
	}

	/**
	 * Gets the all projects by status is null or not null.
	 *
	 * the all projects by status is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByStatusIsNullOrNotNull() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where status is not null
		defaultProjectShouldBeFound("status.specified=true");

		// Get all the projectList where status is null
		defaultProjectShouldNotBeFound("status.specified=false");
	}

	/**
	 * Gets the all projects by end date is equal to something.
	 *
	 * the all projects by end date is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByEndDateIsEqualToSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where endDate equals to DEFAULT_END_DATE
		defaultProjectShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

		// Get all the projectList where endDate equals to UPDATED_END_DATE
		defaultProjectShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
	}

	/**
	 * Gets the all projects by end date is in should work.
	 *
	 * the all projects by end date is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByEndDateIsInShouldWork() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
		defaultProjectShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

		// Get all the projectList where endDate equals to UPDATED_END_DATE
		defaultProjectShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
	}

	/**
	 * Gets the all projects by end date is null or not null.
	 *
	 * the all projects by end date is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByEndDateIsNullOrNotNull() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where endDate is not null
		defaultProjectShouldBeFound("endDate.specified=true");

		// Get all the projectList where endDate is null
		defaultProjectShouldNotBeFound("endDate.specified=false");
	}

	/**
	 * Gets the all projects by end date is greater than or equal to something.
	 *
	 * the all projects by end date is greater than or equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where endDate greater than or equals to
		// DEFAULT_END_DATE
		defaultProjectShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

		// Get all the projectList where endDate greater than or equals to
		// UPDATED_END_DATE
		defaultProjectShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
	}

	/**
	 * Gets the all projects by end date is less than something.
	 *
	 * the all projects by end date is less than something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByEndDateIsLessThanSomething() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		// Get all the projectList where endDate less than or equals to DEFAULT_END_DATE
		defaultProjectShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

		// Get all the projectList where endDate less than or equals to UPDATED_END_DATE
		defaultProjectShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
	}

	/**
	 * Gets the all projects by job offer is equal to something.
	 *
	 * the all projects by job offer is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByJobOfferIsEqualToSomething() throws Exception {
		// Initialize the database
		JobOffer jobOffer = JobOfferResourceIntTest.createEntity(em);
		em.persist(jobOffer);
		em.flush();
		project.addJobOffer(jobOffer);
		projectRepository.saveAndFlush(project);
		Long jobOfferId = jobOffer.getId();

		// Get all the projectList where jobOffer equals to jobOfferId
		defaultProjectShouldBeFound("jobOfferId.equals=" + jobOfferId);

		// Get all the projectList where jobOffer equals to jobOfferId + 1
		defaultProjectShouldNotBeFound("jobOfferId.equals=" + (jobOfferId + 1));
	}

	/**
	 * Gets the all projects by company is equal to something.
	 *
	 * the all projects by company is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsByCompanyIsEqualToSomething() throws Exception {
		// Initialize the database
		Company company = CompanyResourceIntTest.createEntity(em);
		em.persist(company);
		em.flush();
		project.setCompany(company);
		projectRepository.saveAndFlush(project);
		Long companyId = company.getId();

		// Get all the projectList where company equals to companyId
		defaultProjectShouldBeFound("companyId.equals=" + companyId);

		// Get all the projectList where company equals to companyId + 1
		defaultProjectShouldNotBeFound("companyId.equals=" + (companyId + 1));
	}

	/**
	 * Gets the all projects by sector is equal to something.
	 *
	 * the all projects by sector is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllProjectsBySectorIsEqualToSomething() throws Exception {
		// Initialize the database
		CompanySector sector = CompanySectorResourceIntTest.createEntity(em);
		em.persist(sector);
		em.flush();
		project.setSector(sector);
		projectRepository.saveAndFlush(project);
		Long sectorId = sector.getId();

		// Get all the projectList where sector equals to sectorId
		defaultProjectShouldBeFound("sectorId.equals=" + sectorId);

		// Get all the projectList where sector equals to sectorId + 1
		defaultProjectShouldNotBeFound("sectorId.equals=" + (sectorId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultProjectShouldBeFound(String filter) throws Exception {
		restProjectMockMvc.perform(get("/api/projects?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
				.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
				.andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
				.andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
				.andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

		// Check, that the count call also returns 1
		restProjectMockMvc.perform(get("/api/projects/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultProjectShouldNotBeFound(String filter) throws Exception {
		restProjectMockMvc.perform(get("/api/projects?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restProjectMockMvc.perform(get("/api/projects/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("0"));
	}

	/**
	 * Gets the non existing project.
	 *
	 * the non existing project
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getNonExistingProject() throws Exception {
		// Get the project
		restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	/**
	 * Update project.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateProject() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		int databaseSizeBeforeUpdate = projectRepository.findAll().size();

		// Update the project
		Project updatedProject = projectRepository.findById(project.getId()).get();
		// Disconnect from session so that the updates on updatedProject are not
		// directly saved in db
		em.detach(updatedProject);
		updatedProject.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE)
				.status(UPDATED_STATUS).endDate(UPDATED_END_DATE);
		ProjectDTO projectDTO = projectMapper.toDto(updatedProject);

		restProjectMockMvc.perform(put("/api/projects").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(projectDTO))).andExpect(status().isOk());

		// Validate the Project in the database
		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
		Project testProject = projectList.get(projectList.size() - 1);
		assertThat(testProject.getTitle()).isEqualTo(UPDATED_TITLE);
		assertThat(testProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
		assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
		assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
	}

	/**
	 * Update non existing project.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateNonExistingProject() throws Exception {
		int databaseSizeBeforeUpdate = projectRepository.findAll().size();

		// Create the Project
		ProjectDTO projectDTO = projectMapper.toDto(project);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restProjectMockMvc.perform(put("/api/projects").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(projectDTO))).andExpect(status().isBadRequest());

		// Validate the Project in the database
		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Delete project.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void deleteProject() throws Exception {
		// Initialize the database
		projectRepository.saveAndFlush(project);

		int databaseSizeBeforeDelete = projectRepository.findAll().size();

		// Delete the project
		restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Project> projectList = projectRepository.findAll();
		assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
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
		TestUtil.equalsVerifier(Project.class);
		Project project1 = new Project();
		project1.setId(1L);
		Project project2 = new Project();
		project2.setId(project1.getId());
		assertThat(project1).isEqualTo(project2);
		project2.setId(2L);
		assertThat(project1).isNotEqualTo(project2);
		project1.setId(null);
		assertThat(project1).isNotEqualTo(project2);
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
		TestUtil.equalsVerifier(ProjectDTO.class);
		ProjectDTO projectDTO1 = new ProjectDTO();
		projectDTO1.setId(1L);
		ProjectDTO projectDTO2 = new ProjectDTO();
		assertThat(projectDTO1).isNotEqualTo(projectDTO2);
		projectDTO2.setId(projectDTO1.getId());
		assertThat(projectDTO1).isEqualTo(projectDTO2);
		projectDTO2.setId(2L);
		assertThat(projectDTO1).isNotEqualTo(projectDTO2);
		projectDTO1.setId(null);
		assertThat(projectDTO1).isNotEqualTo(projectDTO2);
	}

	/**
	 * Test entity from id.
	 */
	@Test
	@Transactional
	@SuppressWarnings("checkstyle:magicNumber")
	public void testEntityFromId() {
		assertThat(projectMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(projectMapper.fromId(null)).isNull();
	}
}
