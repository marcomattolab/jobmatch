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
import it.aranciaict.jobmatch.domain.Skill;
import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.domain.enumeration.ProficNumberOfYear;
import it.aranciaict.jobmatch.domain.enumeration.SkillLevelType;
import it.aranciaict.jobmatch.repository.SkillRepository;
import it.aranciaict.jobmatch.service.SkillQueryService;
import it.aranciaict.jobmatch.service.SkillService;
import it.aranciaict.jobmatch.service.SkillTagService;
import it.aranciaict.jobmatch.service.dto.SkillDTO;
import it.aranciaict.jobmatch.service.mapper.SkillMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the SkillResource REST controller.
 *
 * @see SkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class SkillResourceIntTest {

    /** The Constant DEFAULT_LEVEL. */
    private static final SkillLevelType DEFAULT_LEVEL = SkillLevelType.BEGINNER;
    
    /** The Constant UPDATED_LEVEL. */
    private static final SkillLevelType UPDATED_LEVEL = SkillLevelType.MEDIUM;
    
    private static final String DEFAULT_SKILL_TAG_NAME = "AAAAAAAAAAA";

    /** The Constant DEFAULT_PROFIC_NUMBER_OF_YEAR. */
    private static final ProficNumberOfYear DEFAULT_PROFIC_NUMBER_OF_YEAR = ProficNumberOfYear.ZERO_TO_ONE;
    
    /** The Constant UPDATED_PROFIC_NUMBER_OF_YEAR. */
    private static final ProficNumberOfYear UPDATED_PROFIC_NUMBER_OF_YEAR = ProficNumberOfYear.ONE_TO_THREE;

    /** The skill repository. */
    @Autowired
    private SkillRepository skillRepository;

    /** The skill mapper. */
    @Autowired
    private SkillMapper skillMapper;

    /** The skill service. */
    @Autowired
    private SkillService skillService;

    /** The skill tag service. */
    @Autowired
    private SkillTagService skillTagService;

    /** The skill query service. */
    @Autowired
    private SkillQueryService skillQueryService;

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

    /** The rest skill mock mvc. */
    private MockMvc restSkillMockMvc;

    /** The skill. */
    private Skill skill;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillResource skillResource = new SkillResource(skillService, skillQueryService, skillTagService);
        this.restSkillMockMvc = MockMvcBuilders.standaloneSetup(skillResource)
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
     * @return the skill
     */
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill()
            .level(DEFAULT_LEVEL)
            .proficNumberOfYear(DEFAULT_PROFIC_NUMBER_OF_YEAR);
        return skill;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        skill = createEntity(em);
    }

//    /**
//     * Creates the skill.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    @Transactional
//    @WithMockUser(username = "username", roles={"ADMIN"})
//    public void createSkill() throws Exception {
//        int databaseSizeBeforeCreate = skillRepository.findAll().size();
//
//        // Create the Skill
//        SkillDTO skillDTO = skillMapper.toDto(skill);
//        skillDTO.setSkillTagName(DEFAULT_SKILL_TAG_NAME);
//        restSkillMockMvc.perform(post("/api/skills")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the Skill in the database
//        List<Skill> skillList = skillRepository.findAll();
//        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
//        Skill testSkill = skillList.get(skillList.size() - 1);
//        assertThat(testSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
//        assertThat(testSkill.getProficNumberOfYear()).isEqualTo(DEFAULT_PROFIC_NUMBER_OF_YEAR);
//    }

    /**
     * Creates the skill with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill with an existing ID
        skill.setId(1L);
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Check level is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setLevel(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Gets the all skills.
     *
     *  the all skills
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].proficNumberOfYear").value(hasItem(DEFAULT_PROFIC_NUMBER_OF_YEAR.toString())));
    }
    
    /**
     * Gets the skill.
     *
     *  the skill
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.proficNumberOfYear").value(DEFAULT_PROFIC_NUMBER_OF_YEAR.toString()));
    }

    /**
     * Gets the all skills by level is equal to something.
     *
     *  the all skills by level is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where level equals to DEFAULT_LEVEL
        defaultSkillShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the skillList where level equals to UPDATED_LEVEL
        defaultSkillShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    /**
     * Gets the all skills by level is in should work.
     *
     *  the all skills by level is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultSkillShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the skillList where level equals to UPDATED_LEVEL
        defaultSkillShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    /**
     * Gets the all skills by level is null or not null.
     *
     *  the all skills by level is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where level is not null
        defaultSkillShouldBeFound("level.specified=true");

        // Get all the skillList where level is null
        defaultSkillShouldNotBeFound("level.specified=false");
    }

    /**
     * Gets the all skills by profic number of year is equal to something.
     *
     *  the all skills by profic number of year is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByProficNumberOfYearIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where proficNumberOfYear equals to DEFAULT_PROFIC_NUMBER_OF_YEAR
        defaultSkillShouldBeFound("proficNumberOfYear.equals=" + DEFAULT_PROFIC_NUMBER_OF_YEAR);

        // Get all the skillList where proficNumberOfYear equals to UPDATED_PROFIC_NUMBER_OF_YEAR
        defaultSkillShouldNotBeFound("proficNumberOfYear.equals=" + UPDATED_PROFIC_NUMBER_OF_YEAR);
    }

    /**
     * Gets the all skills by profic number of year is in should work.
     *
     *  the all skills by profic number of year is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByProficNumberOfYearIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where proficNumberOfYear in DEFAULT_PROFIC_NUMBER_OF_YEAR or UPDATED_PROFIC_NUMBER_OF_YEAR
        defaultSkillShouldBeFound("proficNumberOfYear.in=" + DEFAULT_PROFIC_NUMBER_OF_YEAR + "," + UPDATED_PROFIC_NUMBER_OF_YEAR);

        // Get all the skillList where proficNumberOfYear equals to UPDATED_PROFIC_NUMBER_OF_YEAR
        defaultSkillShouldNotBeFound("proficNumberOfYear.in=" + UPDATED_PROFIC_NUMBER_OF_YEAR);
    }

    /**
     * Gets the all skills by profic number of year is null or not null.
     *
     *  the all skills by profic number of year is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByProficNumberOfYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where proficNumberOfYear is not null
        defaultSkillShouldBeFound("proficNumberOfYear.specified=true");

        // Get all the skillList where proficNumberOfYear is null
        defaultSkillShouldNotBeFound("proficNumberOfYear.specified=false");
    }

    /**
     * Gets the all skills by skill tag is equal to something.
     *
     *  the all skills by skill tag is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsBySkillTagIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillTag skillTag = SkillTagResourceIntTest.createEntity(em);
        em.persist(skillTag);
        em.flush();
        skill.setSkillTag(skillTag);
        skillRepository.saveAndFlush(skill);
        Long skillTagId = skillTag.getId();

        // Get all the skillList where skillTag equals to skillTagId
        defaultSkillShouldBeFound("skillTagId.equals=" + skillTagId);

        // Get all the skillList where skillTag equals to skillTagId + 1
        defaultSkillShouldNotBeFound("skillTagId.equals=" + (skillTagId + 1));
    }


    /**
     * Gets the all skills by candidate is equal to something.
     *
     *  the all skills by candidate is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSkillsByCandidateIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidate candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
        skill.setCandidate(candidate);
        skillRepository.saveAndFlush(skill);
        Long candidateId = candidate.getId();

        // Get all the skillList where candidate equals to candidateId
        defaultSkillShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the skillList where candidate equals to candidateId + 1
        defaultSkillShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultSkillShouldBeFound(String filter) throws Exception {
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].proficNumberOfYear").value(hasItem(DEFAULT_PROFIC_NUMBER_OF_YEAR.toString())));

        // Check, that the count call also returns 1
        restSkillMockMvc.perform(get("/api/skills/count?sort=id,desc&" + filter))
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
    private void defaultSkillShouldNotBeFound(String filter) throws Exception {
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillMockMvc.perform(get("/api/skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing skill.
     *
     *  the non existing skill
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

//    /**
//     * Update skill.
//     *
//     * @throws Exception the exception
//     */
//    @Test
//    @Transactional
//    @WithMockUser(username = "username", roles={"ADMIN"})
//    public void updateSkill() throws Exception {
//        // Initialize the database
//        skillRepository.saveAndFlush(skill);
//
//        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
//
//        // Update the skill
//        Skill updatedSkill = skillRepository.findById(skill.getId()).get();
//        // Disconnect from session so that the updates on updatedSkill are not directly saved in db
//        em.detach(updatedSkill);
//        updatedSkill
//            .level(UPDATED_LEVEL)
//            .proficNumberOfYear(UPDATED_PROFIC_NUMBER_OF_YEAR);
//        SkillDTO skillDTO = skillMapper.toDto(updatedSkill);
//
//        restSkillMockMvc.perform(put("/api/skills")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the Skill in the database
//        List<Skill> skillList = skillRepository.findAll();
//        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
//        Skill testSkill = skillList.get(skillList.size() - 1);
//        assertThat(testSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
//        assertThat(testSkill.getProficNumberOfYear()).isEqualTo(UPDATED_PROFIC_NUMBER_OF_YEAR);
//    }

    /**
     * Update non existing skill.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete skill.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Delete the skill
        restSkillMockMvc.perform(delete("/api/skills/{id}", skill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        Skill skill2 = new Skill();
        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);
        skill2.setId(2L);
        assertThat(skill1).isNotEqualTo(skill2);
        skill1.setId(null);
        assertThat(skill1).isNotEqualTo(skill2);
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
        TestUtil.equalsVerifier(SkillDTO.class);
        SkillDTO skillDTO1 = new SkillDTO();
        skillDTO1.setId(1L);
        SkillDTO skillDTO2 = new SkillDTO();
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO2.setId(skillDTO1.getId());
        assertThat(skillDTO1).isEqualTo(skillDTO2);
        skillDTO2.setId(2L);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO1.setId(null);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
	@SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(skillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skillMapper.fromId(null)).isNull();
    }
}
