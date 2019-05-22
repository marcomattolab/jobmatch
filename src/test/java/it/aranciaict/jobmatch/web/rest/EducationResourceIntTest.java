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
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.Education;
import it.aranciaict.jobmatch.repository.EducationRepository;
import it.aranciaict.jobmatch.service.EducationQueryService;
import it.aranciaict.jobmatch.service.EducationService;
import it.aranciaict.jobmatch.service.dto.EducationDTO;
import it.aranciaict.jobmatch.service.mapper.EducationMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the EducationResource REST controller.
 *
 * @see EducationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class EducationResourceIntTest {

    /** The Constant DEFAULT_SCHOOL_NAME. */
    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    
    /** The Constant UPDATED_SCHOOL_NAME. */
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    /** The Constant DEFAULT_FIELD_OF_STUDY. */
    private static final String DEFAULT_FIELD_OF_STUDY = "AAAAAAAAAA";
    
    /** The Constant UPDATED_FIELD_OF_STUDY. */
    private static final String UPDATED_FIELD_OF_STUDY = "BBBBBBBBBB";

    /** The Constant DEFAULT_DESCRIPTION. */
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    
    /** The Constant UPDATED_DESCRIPTION. */
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

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

    /** The education repository. */
    @Autowired
    private EducationRepository educationRepository;

    /** The education mapper. */
    @Autowired
    private EducationMapper educationMapper;

    /** The education service. */
    @Autowired
    private EducationService educationService;

    /** The education query service. */
    @Autowired
    private EducationQueryService educationQueryService;

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

    /** The rest education mock mvc. */
    private MockMvc restEducationMockMvc;

    /** The education. */
    private Education education;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EducationResource educationResource = new EducationResource(educationService, educationQueryService);
        this.restEducationMockMvc = MockMvcBuilders.standaloneSetup(educationResource)
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
     * @return the education
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .schoolName(DEFAULT_SCHOOL_NAME)
            .fieldOfStudy(DEFAULT_FIELD_OF_STUDY)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .current(DEFAULT_CURRENT);
        return education;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        education = createEntity(em);
    }

    /**
     * Creates the education.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc.perform(post("/api/educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testEducation.getFieldOfStudy()).isEqualTo(DEFAULT_FIELD_OF_STUDY);
        assertThat(testEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEducation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.isCurrent()).isEqualTo(DEFAULT_CURRENT);
    }

    /**
     * Creates the education with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createEducationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // Create the Education with an existing ID
        education.setId(1L);
        EducationDTO educationDTO = educationMapper.toDto(education);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc.perform(post("/api/educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }


    /**
     * Gets the all educations.
     *
     *  the all educations
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldOfStudy").value(hasItem(DEFAULT_FIELD_OF_STUDY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT.booleanValue())));
    }
    
    /**
     * Gets the education.
     *
     *  the education
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME.toString()))
            .andExpect(jsonPath("$.fieldOfStudy").value(DEFAULT_FIELD_OF_STUDY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.current").value(DEFAULT_CURRENT.booleanValue()));
    }


    /**
     * Gets the all educations by school name is equal to something.
     *
     *  the all educations by school name is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsBySchoolNameIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName equals to DEFAULT_SCHOOL_NAME
        defaultEducationShouldBeFound("schoolName.equals=" + DEFAULT_SCHOOL_NAME);

        // Get all the educationList where schoolName equals to UPDATED_SCHOOL_NAME
        defaultEducationShouldNotBeFound("schoolName.equals=" + UPDATED_SCHOOL_NAME);
    }

    /**
     * Gets the all educations by school name is in should work.
     *
     *  the all educations by school name is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsBySchoolNameIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName in DEFAULT_SCHOOL_NAME or UPDATED_SCHOOL_NAME
        defaultEducationShouldBeFound("schoolName.in=" + DEFAULT_SCHOOL_NAME + "," + UPDATED_SCHOOL_NAME);

        // Get all the educationList where schoolName equals to UPDATED_SCHOOL_NAME
        defaultEducationShouldNotBeFound("schoolName.in=" + UPDATED_SCHOOL_NAME);
    }

    /**
     * Gets the all educations by school name is null or not null.
     *
     *  the all educations by school name is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsBySchoolNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName is not null
        defaultEducationShouldBeFound("schoolName.specified=true");

        // Get all the educationList where schoolName is null
        defaultEducationShouldNotBeFound("schoolName.specified=false");
    }


    /**
     * Gets the all educations by field of study is equal to something.
     *
     *  the all educations by field of study is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByFieldOfStudyIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where fieldOfStudy equals to DEFAULT_FIELD_OF_STUDY
        defaultEducationShouldBeFound("fieldOfStudy.equals=" + DEFAULT_FIELD_OF_STUDY);

        // Get all the educationList where fieldOfStudy equals to UPDATED_FIELD_OF_STUDY
        defaultEducationShouldNotBeFound("fieldOfStudy.equals=" + UPDATED_FIELD_OF_STUDY);
    }

    /**
     * Gets the all educations by field of study is in should work.
     *
     *  the all educations by field of study is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByFieldOfStudyIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where fieldOfStudy in DEFAULT_FIELD_OF_STUDY or UPDATED_FIELD_OF_STUDY
        defaultEducationShouldBeFound("fieldOfStudy.in=" + DEFAULT_FIELD_OF_STUDY + "," + UPDATED_FIELD_OF_STUDY);

        // Get all the educationList where fieldOfStudy equals to UPDATED_FIELD_OF_STUDY
        defaultEducationShouldNotBeFound("fieldOfStudy.in=" + UPDATED_FIELD_OF_STUDY);
    }

    /**
     * Gets the all educations by field of study is null or not null.
     *
     *  the all educations by field of study is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByFieldOfStudyIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where fieldOfStudy is not null
        defaultEducationShouldBeFound("fieldOfStudy.specified=true");

        // Get all the educationList where fieldOfStudy is null
        defaultEducationShouldNotBeFound("fieldOfStudy.specified=false");
    }

    /**
     * Gets the all educations by start date is equal to something.
     *
     *  the all educations by start date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate equals to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    /**
     * Gets the all educations by start date is in should work.
     *
     *  the all educations by start date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultEducationShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    /**
     * Gets the all educations by start date is null or not null.
     *
     *  the all educations by start date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is not null
        defaultEducationShouldBeFound("startDate.specified=true");

        // Get all the educationList where startDate is null
        defaultEducationShouldNotBeFound("startDate.specified=false");
    }

    /**
     * Gets the all educations by start date is greater than or equal to something.
     *
     *  the all educations by start date is greater than or equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate greater than or equals to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate greater than or equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    /**
     * Gets the all educations by start date is less than something.
     *
     *  the all educations by start date is less than something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate less than or equals to DEFAULT_START_DATE
        defaultEducationShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate less than or equals to UPDATED_START_DATE
        defaultEducationShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    /**
     * Gets the all educations by end date is equal to something.
     *
     *  the all educations by end date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate equals to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    /**
     * Gets the all educations by end date is in should work.
     *
     *  the all educations by end date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    /**
     * Gets the all educations by end date is null or not null.
     *
     *  the all educations by end date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is not null
        defaultEducationShouldBeFound("endDate.specified=true");

        // Get all the educationList where endDate is null
        defaultEducationShouldNotBeFound("endDate.specified=false");
    }

    /**
     * Gets the all educations by end date is greater than or equal to something.
     *
     *  the all educations by end date is greater than or equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate greater than or equals to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate greater than or equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    /**
     * Gets the all educations by end date is less than something.
     *
     *  the all educations by end date is less than something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate less than or equals to DEFAULT_END_DATE
        defaultEducationShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate less than or equals to UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }



    /**
     * Gets the all educations by current is equal to something.
     *
     *  the all educations by current is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByCurrentIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where current equals to DEFAULT_CURRENT
        defaultEducationShouldBeFound("current.equals=" + DEFAULT_CURRENT);

        // Get all the educationList where current equals to UPDATED_CURRENT
        defaultEducationShouldNotBeFound("current.equals=" + UPDATED_CURRENT);
    }

    /**
     * Gets the all educations by current is in should work.
     *
     *  the all educations by current is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByCurrentIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where current in DEFAULT_CURRENT or UPDATED_CURRENT
        defaultEducationShouldBeFound("current.in=" + DEFAULT_CURRENT + "," + UPDATED_CURRENT);

        // Get all the educationList where current equals to UPDATED_CURRENT
        defaultEducationShouldNotBeFound("current.in=" + UPDATED_CURRENT);
    }

    /**
     * Gets the all educations by current is null or not null.
     *
     *  the all educations by current is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByCurrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where current is not null
        defaultEducationShouldBeFound("current.specified=true");

        // Get all the educationList where current is null
        defaultEducationShouldNotBeFound("current.specified=false");
    }

    /**
     * Gets the all educations by candidate is equal to something.
     *
     *  the all educations by candidate is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllEducationsByCandidateIsEqualToSomething() throws Exception {
        // Initialize the database
        Candidate candidate = CandidateResourceIntTest.createEntity(em);
        em.persist(candidate);
        em.flush();
        education.setCandidate(candidate);
        educationRepository.saveAndFlush(education);
        Long candidateId = candidate.getId();

        // Get all the educationList where candidate equals to candidateId
        defaultEducationShouldBeFound("candidateId.equals=" + candidateId);

        // Get all the educationList where candidate equals to candidateId + 1
        defaultEducationShouldNotBeFound("candidateId.equals=" + (candidateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultEducationShouldBeFound(String filter) throws Exception {
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].fieldOfStudy").value(hasItem(DEFAULT_FIELD_OF_STUDY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].current").value(hasItem(DEFAULT_CURRENT.booleanValue())));

        // Check, that the count call also returns 1
        restEducationMockMvc.perform(get("/api/educations/count?sort=id,desc&" + filter))
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
    private void defaultEducationShouldNotBeFound(String filter) throws Exception {
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationMockMvc.perform(get("/api/educations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing education.
     *
     *  the non existing education
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update education.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .schoolName(UPDATED_SCHOOL_NAME)
            .fieldOfStudy(UPDATED_FIELD_OF_STUDY)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .current(UPDATED_CURRENT);
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc.perform(put("/api/educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testEducation.getFieldOfStudy()).isEqualTo(UPDATED_FIELD_OF_STUDY);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.isCurrent()).isEqualTo(UPDATED_CURRENT);
    }

    /**
     * Update non existing education.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc.perform(put("/api/educations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete education.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc.perform(delete("/api/educations/{id}", education.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(Education.class);
        Education education1 = new Education();
        education1.setId(1L);
        Education education2 = new Education();
        education2.setId(education1.getId());
        assertThat(education1).isEqualTo(education2);
        education2.setId(2L);
        assertThat(education1).isNotEqualTo(education2);
        education1.setId(null);
        assertThat(education1).isNotEqualTo(education2);
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
        TestUtil.equalsVerifier(EducationDTO.class);
        EducationDTO educationDTO1 = new EducationDTO();
        educationDTO1.setId(1L);
        EducationDTO educationDTO2 = new EducationDTO();
        assertThat(educationDTO1).isNotEqualTo(educationDTO2);
        educationDTO2.setId(educationDTO1.getId());
        assertThat(educationDTO1).isEqualTo(educationDTO2);
        educationDTO2.setId(2L);
        assertThat(educationDTO1).isNotEqualTo(educationDTO2);
        educationDTO1.setId(null);
        assertThat(educationDTO1).isNotEqualTo(educationDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(educationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(educationMapper.fromId(null)).isNull();
    }
}
