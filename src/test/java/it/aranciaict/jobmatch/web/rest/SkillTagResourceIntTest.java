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
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.enumeration.SkillType;
import it.aranciaict.jobmatch.repository.SkillTagRepository;
import it.aranciaict.jobmatch.service.SkillTagQueryService;
import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;
import it.aranciaict.jobmatch.service.mapper.SkillTagMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;

// TODO: Auto-generated Javadoc
/**
 * Test class for the SkillTagResource REST controller.
 *
 * @see SkillTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class SkillTagResourceIntTest {

	/** The Constant DEFAULT_NAME. */
	private static final String DEFAULT_NAME = "AAAAAAAAAA";

	/** The Constant UPDATED_NAME. */
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	/** The Constant DEFAULT_TYPE. */
	private static final SkillType DEFAULT_TYPE = SkillType.LANGUAGE;

	/** The Constant UPDATED_TYPE. */
	private static final SkillType UPDATED_TYPE = SkillType.OTHER;

	/** The skill tag repository. */
	@Autowired
	private SkillTagRepository skillTagRepository;

	/** The skill tag mapper. */
	@Autowired
	private SkillTagMapper skillTagMapper;

	/** The skill tag service. */
	@Autowired
	private SkillTagService skillTagService;

	/** The skill tag query service. */
	@Autowired
	private SkillTagQueryService skillTagQueryService;

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

	/** The rest skill tag mock mvc. */
	private MockMvc restSkillTagMockMvc;

	/** The skill tag. */
	private SkillTag skillTag;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final SkillTagResource skillTagResource = new SkillTagResource(skillTagService, skillTagQueryService);
		this.restSkillTagMockMvc = MockMvcBuilders.standaloneSetup(skillTagResource)
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
	 * @return the skill tag
	 */
	public static SkillTag createEntity(EntityManager em) {
		SkillTag skillTag = new SkillTag().name(DEFAULT_NAME).type(DEFAULT_TYPE);
		return skillTag;
	}

	/**
	 * Inits the test.
	 */
	@Before
	public void initTest() {
		skillTag = createEntity(em);
	}

	/**
	 * Creates the skill tag.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createSkillTag() throws Exception {
		int databaseSizeBeforeCreate = skillTagRepository.findAll().size();

		// Create the SkillTag
		SkillTagDTO skillTagDTO = skillTagMapper.toDto(skillTag);
		restSkillTagMockMvc.perform(post("/api/skill-tags").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(skillTagDTO))).andExpect(status().isCreated());

		// Validate the SkillTag in the database
		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeCreate + 1);
		SkillTag testSkillTag = skillTagList.get(skillTagList.size() - 1);
		assertThat(testSkillTag.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testSkillTag.getType()).isEqualTo(DEFAULT_TYPE);
	}

	/**
	 * Creates the skill tag with existing id.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createSkillTagWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = skillTagRepository.findAll().size();

		// Create the SkillTag with an existing ID
		skillTag.setId(1L);
		SkillTagDTO skillTagDTO = skillTagMapper.toDto(skillTag);

		// An entity with an existing ID cannot be created, so this API call must fail
		restSkillTagMockMvc.perform(post("/api/skill-tags").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(skillTagDTO))).andExpect(status().isBadRequest());

		// Validate the SkillTag in the database
		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeCreate);
	}

	/**
	 * Check name is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = skillTagRepository.findAll().size();
		// set the field null
		skillTag.setName(null);

		// Create the SkillTag, which fails.
		SkillTagDTO skillTagDTO = skillTagMapper.toDto(skillTag);

		restSkillTagMockMvc.perform(post("/api/skill-tags").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(skillTagDTO))).andExpect(status().isBadRequest());

		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Check type is required.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void checkTypeIsRequired() throws Exception {
		int databaseSizeBeforeTest = skillTagRepository.findAll().size();
		// set the field null
		skillTag.setType(null);

		// Create the SkillTag, which fails.
		SkillTagDTO skillTagDTO = skillTagMapper.toDto(skillTag);

		restSkillTagMockMvc.perform(post("/api/skill-tags").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(skillTagDTO))).andExpect(status().isBadRequest());

		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeTest);
	}

	/**
	 * Gets the all skill tags.
	 *
	 * the all skill tags
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTags() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList
		restSkillTagMockMvc.perform(get("/api/skill-tags?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(skillTag.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
				.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
	}

	/**
	 * Gets the skill tag.
	 *
	 * the skill tag
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getSkillTag() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get the skillTag
		restSkillTagMockMvc.perform(get("/api/skill-tags/{id}", skillTag.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(skillTag.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
				.andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
	}

	/**
	 * Gets the all skill tags by created by is equal to something.
	 *
	 * the all skill tags by created by is equal to something
	 * 
	 * @throws Exception the exception
	 */

	/**
	 * Gets the all skill tags by name is equal to something.
	 *
	 * the all skill tags by name is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTagsByNameIsEqualToSomething() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList where name equals to DEFAULT_NAME
		defaultSkillTagShouldBeFound("name.equals=" + DEFAULT_NAME);

		// Get all the skillTagList where name equals to UPDATED_NAME
		defaultSkillTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
	}

	/**
	 * Gets the all skill tags by name is in should work.
	 *
	 * the all skill tags by name is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTagsByNameIsInShouldWork() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList where name in DEFAULT_NAME or UPDATED_NAME
		defaultSkillTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

		// Get all the skillTagList where name equals to UPDATED_NAME
		defaultSkillTagShouldNotBeFound("name.in=" + UPDATED_NAME);
	}

	/**
	 * Gets the all skill tags by name is null or not null.
	 *
	 * the all skill tags by name is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTagsByNameIsNullOrNotNull() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList where name is not null
		defaultSkillTagShouldBeFound("name.specified=true");

		// Get all the skillTagList where name is null
		defaultSkillTagShouldNotBeFound("name.specified=false");
	}

	/**
	 * Gets the all skill tags by type is equal to something.
	 *
	 * the all skill tags by type is equal to something
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTagsByTypeIsEqualToSomething() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList where type equals to DEFAULT_TYPE
		defaultSkillTagShouldBeFound("type.equals=" + DEFAULT_TYPE);

		// Get all the skillTagList where type equals to UPDATED_TYPE
		defaultSkillTagShouldNotBeFound("type.equals=" + UPDATED_TYPE);
	}

	/**
	 * Gets the all skill tags by type is in should work.
	 *
	 * the all skill tags by type is in should work
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTagsByTypeIsInShouldWork() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList where type in DEFAULT_TYPE or UPDATED_TYPE
		defaultSkillTagShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

		// Get all the skillTagList where type equals to UPDATED_TYPE
		defaultSkillTagShouldNotBeFound("type.in=" + UPDATED_TYPE);
	}

	/**
	 * Gets the all skill tags by type is null or not null.
	 *
	 * the all skill tags by type is null or not null
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getAllSkillTagsByTypeIsNullOrNotNull() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		// Get all the skillTagList where type is not null
		defaultSkillTagShouldBeFound("type.specified=true");

		// Get all the skillTagList where type is null
		defaultSkillTagShouldNotBeFound("type.specified=false");
	}

	/**
	 * Executes the search, and checks that the default entity is returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultSkillTagShouldBeFound(String filter) throws Exception {
		restSkillTagMockMvc.perform(get("/api/skill-tags?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(skillTag.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
				.andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

		// Check, that the count call also returns 1
		restSkillTagMockMvc.perform(get("/api/skill-tags/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("1"));
	}

	/**
	 * Executes the search, and checks that the default entity is not returned.
	 *
	 * @param filter the filter
	 * @throws Exception the exception
	 */
	private void defaultSkillTagShouldNotBeFound(String filter) throws Exception {
		restSkillTagMockMvc.perform(get("/api/skill-tags?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());

		// Check, that the count call also returns 0
		restSkillTagMockMvc.perform(get("/api/skill-tags/count?sort=id,desc&" + filter)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().string("0"));
	}

	/**
	 * Gets the non existing skill tag.
	 *
	 * the non existing skill tag
	 * 
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void getNonExistingSkillTag() throws Exception {
		// Get the skillTag
		restSkillTagMockMvc.perform(get("/api/skill-tags/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	/**
	 * Update skill tag.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateSkillTag() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		int databaseSizeBeforeUpdate = skillTagRepository.findAll().size();

		// Update the skillTag
		SkillTag updatedSkillTag = skillTagRepository.findById(skillTag.getId()).get();
		// Disconnect from session so that the updates on updatedSkillTag are not
		// directly saved in db
		em.detach(updatedSkillTag);
		updatedSkillTag.name(UPDATED_NAME).type(UPDATED_TYPE);
		SkillTagDTO skillTagDTO = skillTagMapper.toDto(updatedSkillTag);

		restSkillTagMockMvc.perform(put("/api/skill-tags").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(skillTagDTO))).andExpect(status().isOk());

		// Validate the SkillTag in the database
		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeUpdate);
		SkillTag testSkillTag = skillTagList.get(skillTagList.size() - 1);
		assertThat(testSkillTag.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testSkillTag.getType()).isEqualTo(UPDATED_TYPE);
	}

	/**
	 * Update non existing skill tag.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateNonExistingSkillTag() throws Exception {
		int databaseSizeBeforeUpdate = skillTagRepository.findAll().size();

		// Create the SkillTag
		SkillTagDTO skillTagDTO = skillTagMapper.toDto(skillTag);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restSkillTagMockMvc.perform(put("/api/skill-tags").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(skillTagDTO))).andExpect(status().isBadRequest());

		// Validate the SkillTag in the database
		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeUpdate);
	}

	/**
	 * Delete skill tag.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void deleteSkillTag() throws Exception {
		// Initialize the database
		skillTagRepository.saveAndFlush(skillTag);

		int databaseSizeBeforeDelete = skillTagRepository.findAll().size();

		// Delete the skillTag
		restSkillTagMockMvc
				.perform(delete("/api/skill-tags/{id}", skillTag.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<SkillTag> skillTagList = skillTagRepository.findAll();
		assertThat(skillTagList).hasSize(databaseSizeBeforeDelete - 1);
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
		TestUtil.equalsVerifier(SkillTag.class);
		SkillTag skillTag1 = new SkillTag();
		skillTag1.setId(1L);
		SkillTag skillTag2 = new SkillTag();
		skillTag2.setId(skillTag1.getId());
		assertThat(skillTag1).isEqualTo(skillTag2);
		skillTag2.setId(2L);
		assertThat(skillTag1).isNotEqualTo(skillTag2);
		skillTag1.setId(null);
		assertThat(skillTag1).isNotEqualTo(skillTag2);
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
		TestUtil.equalsVerifier(SkillTagDTO.class);
		SkillTagDTO skillTagDTO1 = new SkillTagDTO();
		skillTagDTO1.setId(1L);
		SkillTagDTO skillTagDTO2 = new SkillTagDTO();
		assertThat(skillTagDTO1).isNotEqualTo(skillTagDTO2);
		skillTagDTO2.setId(skillTagDTO1.getId());
		assertThat(skillTagDTO1).isEqualTo(skillTagDTO2);
		skillTagDTO2.setId(2L);
		assertThat(skillTagDTO1).isNotEqualTo(skillTagDTO2);
		skillTagDTO1.setId(null);
		assertThat(skillTagDTO1).isNotEqualTo(skillTagDTO2);
	}

	/**
	 * Test entity from id.
	 */
	@Test
	@Transactional
	@SuppressWarnings("checkstyle:magicNumber")
	public void testEntityFromId() {
		assertThat(skillTagMapper.fromId(42L).getId()).isEqualTo(42);
		assertThat(skillTagMapper.fromId(null)).isNull();
	}
}
