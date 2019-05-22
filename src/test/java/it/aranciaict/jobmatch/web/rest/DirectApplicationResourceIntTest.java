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
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.DirectApplication;
import it.aranciaict.jobmatch.domain.enumeration.AppiedJobStatus;
import it.aranciaict.jobmatch.repository.DirectApplicationRepository;
import it.aranciaict.jobmatch.service.DirectApplicationQueryService;
import it.aranciaict.jobmatch.service.DirectApplicationService;
import it.aranciaict.jobmatch.service.dto.DirectApplicationDTO;
import it.aranciaict.jobmatch.service.mapper.DirectApplicationMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the DirectApplicationResource REST controller.
 *
 * @see DirectApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class DirectApplicationResourceIntTest {

	/** The Constant DEFAULT_APPIED_JOB_STATUS. */
	private static final AppiedJobStatus DEFAULT_APPIED_JOB_STATUS = AppiedJobStatus.NEW;

	/** The Constant UPDATED_APPIED_JOB_STATUS. */
	private static final AppiedJobStatus UPDATED_APPIED_JOB_STATUS = AppiedJobStatus.EVALUATION_IN_PROGRESS;

	/** The direct application repository. */
	@Autowired
	private DirectApplicationRepository directApplicationRepository;

	/** The direct application mapper. */
	@Autowired
	private DirectApplicationMapper directApplicationMapper;

	/** The direct application service. */
	@Autowired
	private DirectApplicationService directApplicationService;

	/** The direct application query service. */
	@Autowired
	private DirectApplicationQueryService directApplicationQueryService;

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

	/** The rest direct application mock mvc. */
	private MockMvc restDirectApplicationMockMvc;

	/** The direct application. */
	private DirectApplication directApplication;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final DirectApplicationResource directApplicationResource = new DirectApplicationResource(
				directApplicationService, directApplicationQueryService);
		this.restDirectApplicationMockMvc = MockMvcBuilders.standaloneSetup(directApplicationResource)
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
	 * @param em the em the direct application
	 * @return the direct application
	 */
	public static DirectApplication createEntity(EntityManager em) {
		DirectApplication directApplication = new DirectApplication().appiedJobStatus(DEFAULT_APPIED_JOB_STATUS);
		return directApplication;
	}

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		directApplication = createEntity(em);
	}

	/**
	 * Creates the direct application.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createDirectApplication() throws Exception {
		int databaseSizeBeforeCreate = directApplicationRepository.findAll().size();

		// Create the DirectApplication
		DirectApplicationDTO directApplicationDTO = directApplicationMapper.toDto(directApplication);
		restDirectApplicationMockMvc
				.perform(post("/api/direct-applications").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(directApplicationDTO)))
				.andExpect(status().isCreated());

		// Validate the DirectApplication in the database
		List<DirectApplication> directApplicationList = directApplicationRepository.findAll();
		assertThat(directApplicationList).hasSize(databaseSizeBeforeCreate + 1);
		DirectApplication testDirectApplication = directApplicationList.get(directApplicationList.size() - 1);
		assertThat(testDirectApplication.getAppiedJobStatus()).isEqualTo(DEFAULT_APPIED_JOB_STATUS);
	}

	/**
	 * Creates the direct application with existing id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createDirectApplicationWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = directApplicationRepository.findAll().size();

		// Create the DirectApplication with an existing ID
		directApplication.setId(1L);
		DirectApplicationDTO directApplicationDTO = directApplicationMapper.toDto(directApplication);

		// An entity with an existing ID cannot be created, so this API call must fail
		restDirectApplicationMockMvc
				.perform(post("/api/direct-applications").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(directApplicationDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DirectApplication in the database
		List<DirectApplication> directApplicationList = directApplicationRepository.findAll();
		assertThat(directApplicationList).hasSize(databaseSizeBeforeCreate);
	}

	/**
	 * Gets the all direct applications.
	 *
	 * the all direct applications
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllDirectApplications() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		// Get all the directApplicationList
		restDirectApplicationMockMvc.perform(get("/api/direct-applications?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(directApplication.getId().intValue())))
				.andExpect(jsonPath("$.[*].appiedJobStatus").value(hasItem(DEFAULT_APPIED_JOB_STATUS.toString())));
	}

	/**
	 * Gets the direct application.
	 *
	 * the direct application
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getDirectApplication() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		// Get the directApplication
		restDirectApplicationMockMvc.perform(get("/api/direct-applications/{id}", directApplication.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(directApplication.getId().intValue()))
				.andExpect(jsonPath("$.appiedJobStatus").value(DEFAULT_APPIED_JOB_STATUS.toString()));
	}

	/**
	 * Gets the all direct applications by appied job status is equal to something.
	 *
	 * the all direct applications by appied job status is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllDirectApplicationsByAppiedJobStatusIsEqualToSomething() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		// Get all the directApplicationList where appiedJobStatus equals to
		// DEFAULT_APPIED_JOB_STATUS
		defaultDirectApplicationShouldBeFound("appiedJobStatus.equals=" + DEFAULT_APPIED_JOB_STATUS);

		// Get all the directApplicationList where appiedJobStatus equals to
		// UPDATED_APPIED_JOB_STATUS
		defaultDirectApplicationShouldNotBeFound("appiedJobStatus.equals=" + UPDATED_APPIED_JOB_STATUS);
	}

	/**
	 * Gets the all direct applications by appied job status is in should work.
	 *
	 * the all direct applications by appied job status is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllDirectApplicationsByAppiedJobStatusIsInShouldWork() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		// Get all the directApplicationList where appiedJobStatus in
		// DEFAULT_APPIED_JOB_STATUS or UPDATED_APPIED_JOB_STATUS
		defaultDirectApplicationShouldBeFound(
				"appiedJobStatus.in=" + DEFAULT_APPIED_JOB_STATUS + "," + UPDATED_APPIED_JOB_STATUS);

		// Get all the directApplicationList where appiedJobStatus equals to
		// UPDATED_APPIED_JOB_STATUS
		defaultDirectApplicationShouldNotBeFound("appiedJobStatus.in=" + UPDATED_APPIED_JOB_STATUS);
	}

	/**
	 * Gets the all direct applications by appied job status is null or not null.
	 *
	 * the all direct applications by appied job status is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllDirectApplicationsByAppiedJobStatusIsNullOrNotNull() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		// Get all the directApplicationList where appiedJobStatus is not null
		defaultDirectApplicationShouldBeFound("appiedJobStatus.specified=true");

		// Get all the directApplicationList where appiedJobStatus is null
		defaultDirectApplicationShouldNotBeFound("appiedJobStatus.specified=false");
	}

	/**
	 * Gets the all direct applications by company is equal to something.
	 *
	 * the all direct applications by company is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllDirectApplicationsByCompanyIsEqualToSomething() throws Exception {
		// Initialize the database
		Company company = CompanyResourceIntTest.createEntity(em);
		em.persist(company);
		em.flush();
		directApplication.setCompany(company);
		directApplicationRepository.saveAndFlush(directApplication);
		Long companyId = company.getId();

		// Get all the directApplicationList where company equals to companyId
		defaultDirectApplicationShouldBeFound("companyId.equals=" + companyId);

		// Get all the directApplicationList where company equals to companyId + 1
		defaultDirectApplicationShouldNotBeFound("companyId.equals=" + (companyId + 1));
	}

	/**
	 * Gets the all direct applications by candidate is equal to something.
	 *
	 * the all direct applications by candidate is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllDirectApplicationsByCandidateIsEqualToSomething() throws Exception {
		// Initialize the database
		Candidate candidate = CandidateResourceIntTest.createEntity(em);
		em.persist(candidate);
		em.flush();
		directApplication.setCandidate(candidate);
		directApplicationRepository.saveAndFlush(directApplication);
		Long candidateId = candidate.getId();

		// Get all the directApplicationList where candidate equals to candidateId
		defaultDirectApplicationShouldBeFound("candidateId.equals=" + candidateId);

		// Get all the directApplicationList where candidate equals to candidateId + 1
		defaultDirectApplicationShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultDirectApplicationShouldBeFound(String filter) throws Exception {
		restDirectApplicationMockMvc.perform(get("/api/direct-applications?sort=id,desc&" + filter))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(directApplication.getId().intValue())))
				.andExpect(jsonPath("$.[*].appiedJobStatus").value(hasItem(DEFAULT_APPIED_JOB_STATUS.toString())));

		// Check, that the count call also returns 1
		restDirectApplicationMockMvc.perform(get("/api/direct-applications/count?sort=id,desc&" + filter))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultDirectApplicationShouldNotBeFound(String filter) throws Exception {
		restDirectApplicationMockMvc.perform(get("/api/direct-applications?sort=id,desc&" + filter))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restDirectApplicationMockMvc.perform(get("/api/direct-applications/count?sort=id,desc&" + filter))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("0"));
	}

	/**
	 * Gets the non existing direct application.
	 *
	 * the non existing direct application
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getNonExistingDirectApplication() throws Exception {
		// Get the directApplication
		restDirectApplicationMockMvc.perform(get("/api/direct-applications/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	/**
	 * Update direct application.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateDirectApplication() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		int databaseSizeBeforeUpdate = directApplicationRepository.findAll().size();

		// Update the directApplication
		DirectApplication updatedDirectApplication = directApplicationRepository.findById(directApplication.getId())
				.get();
		// Disconnect from session so that the updates on updatedDirectApplication are
		// not directly saved in db
		em.detach(updatedDirectApplication);
		updatedDirectApplication.appiedJobStatus(UPDATED_APPIED_JOB_STATUS);
		DirectApplicationDTO directApplicationDTO = directApplicationMapper.toDto(updatedDirectApplication);

		restDirectApplicationMockMvc.perform(put("/api/direct-applications").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(directApplicationDTO))).andExpect(status().isOk());

		// Validate the DirectApplication in the database
		List<DirectApplication> directApplicationList = directApplicationRepository.findAll();
		assertThat(directApplicationList).hasSize(databaseSizeBeforeUpdate);
		DirectApplication testDirectApplication = directApplicationList.get(directApplicationList.size() - 1);
		assertThat(testDirectApplication.getAppiedJobStatus()).isEqualTo(UPDATED_APPIED_JOB_STATUS);
	}

	/**
	 * Update non existing direct application.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateNonExistingDirectApplication() throws Exception {
		int databaseSizeBeforeUpdate = directApplicationRepository.findAll().size();

		// Create the DirectApplication
		DirectApplicationDTO directApplicationDTO = directApplicationMapper.toDto(directApplication);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restDirectApplicationMockMvc
				.perform(put("/api/direct-applications").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(directApplicationDTO)))
				.andExpect(status().isBadRequest());

		// Validate the DirectApplication in the database
		List<DirectApplication> directApplicationList = directApplicationRepository.findAll();
		assertThat(directApplicationList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Delete direct application.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void deleteDirectApplication() throws Exception {
		// Initialize the database
		directApplicationRepository.saveAndFlush(directApplication);

		int databaseSizeBeforeDelete = directApplicationRepository.findAll().size();

		// Delete the directApplication
		restDirectApplicationMockMvc.perform(delete("/api/direct-applications/{id}", directApplication.getId())
				.accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

		// Validate the database is empty
		List<DirectApplication> directApplicationList = directApplicationRepository.findAll();
		assertThat(directApplicationList).hasSize(databaseSizeBeforeDelete - 1);
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
		TestUtil.equalsVerifier(DirectApplication.class);
		DirectApplication directApplication1 = new DirectApplication();
		directApplication1.setId(1L);
		DirectApplication directApplication2 = new DirectApplication();
		directApplication2.setId(directApplication1.getId());
		assertThat(directApplication1).isEqualTo(directApplication2);
		directApplication2.setId(2L);
		assertThat(directApplication1).isNotEqualTo(directApplication2);
		directApplication1.setId(null);
		assertThat(directApplication1).isNotEqualTo(directApplication2);
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
		TestUtil.equalsVerifier(DirectApplicationDTO.class);
		DirectApplicationDTO directApplicationDTO1 = new DirectApplicationDTO();
		directApplicationDTO1.setId(1L);
		DirectApplicationDTO directApplicationDTO2 = new DirectApplicationDTO();
		assertThat(directApplicationDTO1).isNotEqualTo(directApplicationDTO2);
		directApplicationDTO2.setId(directApplicationDTO1.getId());
		assertThat(directApplicationDTO1).isEqualTo(directApplicationDTO2);
		directApplicationDTO2.setId(2L);
		assertThat(directApplicationDTO1).isNotEqualTo(directApplicationDTO2);
		directApplicationDTO1.setId(null);
		assertThat(directApplicationDTO1).isNotEqualTo(directApplicationDTO2);
	}

	/**
	 * Test entity from id.
	 */
	@Test
	@Transactional
	@SuppressWarnings("checkstyle:magicNumber")
	public void testEntityFromId() {
		assertThat(directApplicationMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(directApplicationMapper.fromId(null)).isNull();
	}
}
