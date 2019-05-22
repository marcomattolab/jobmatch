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
import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionSectorType;
import it.aranciaict.jobmatch.domain.enumeration.IstituitionType;
import it.aranciaict.jobmatch.repository.SponsoringInstitutionRepository;
import it.aranciaict.jobmatch.service.SponsoringInstitutionQueryService;
import it.aranciaict.jobmatch.service.SponsoringInstitutionService;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionDTO;
import it.aranciaict.jobmatch.service.mapper.SponsoringInstitutionMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the SponsoringInstitutionResource REST controller.
 *
 * @see SponsoringInstitutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class SponsoringInstitutionResourceIntTest {

    /** The Constant DEFAULT_ISTITUITION_NAME. */
    private static final String DEFAULT_ISTITUITION_NAME = "AAAAAAAAAA";
    
    /** The Constant UPDATED_ISTITUITION_NAME. */
    private static final String UPDATED_ISTITUITION_NAME = "BBBBBBBBBB";

    /** The Constant DEFAULT_ISTITUITION_DESCRIPTION. */
    private static final String DEFAULT_ISTITUITION_DESCRIPTION = "AAAAAAAAAA";
    
    /** The Constant UPDATED_ISTITUITION_DESCRIPTION. */
    private static final String UPDATED_ISTITUITION_DESCRIPTION = "BBBBBBBBBB";

    /** The Constant DEFAULT_ISTITUITION_SECTOR. */
    private static final IstituitionSectorType DEFAULT_ISTITUITION_SECTOR = IstituitionSectorType.ICT_IT;
    
    /** The Constant UPDATED_ISTITUITION_SECTOR. */
    private static final IstituitionSectorType UPDATED_ISTITUITION_SECTOR = IstituitionSectorType.CHARITY;

    /** The Constant DEFAULT_ISTITUITION_TYPE. */
    private static final IstituitionType DEFAULT_ISTITUITION_TYPE = IstituitionType.PUBLIC;
    
    /** The Constant UPDATED_ISTITUITION_TYPE. */
    private static final IstituitionType UPDATED_ISTITUITION_TYPE = IstituitionType.PRIVATE;

    /** The Constant DEFAULT_STREET_ADDRESS. */
    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    
    /** The Constant UPDATED_STREET_ADDRESS. */
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    /** The Constant DEFAULT_FOUNDATION_DATE. */
    private static final LocalDate DEFAULT_FOUNDATION_DATE = LocalDate.ofEpochDay(0L);
    
    /** The Constant UPDATED_FOUNDATION_DATE. */
    private static final LocalDate UPDATED_FOUNDATION_DATE = LocalDate.now(ZoneId.systemDefault());

    /** The Constant DEFAULT_VAT_NUMBER. */
    private static final String DEFAULT_VAT_NUMBER = "11111111111";
    
    /** The Constant UPDATED_VAT_NUMBER. */
    private static final String UPDATED_VAT_NUMBER = "22222222222";

    /** The Constant DEFAULT_EMAIL. */
    private static final String DEFAULT_EMAIL = "aaaa@aaa.it";
    
    /** The Constant UPDATED_EMAIL. */
    private static final String UPDATED_EMAIL = "bbbb@bbb.com";

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

    /** The Constant DEFAULT_URL_SITE. */
    private static final String DEFAULT_URL_SITE = "www.aaa.it";
    
    /** The Constant UPDATED_URL_SITE. */
    private static final String UPDATED_URL_SITE = "www.bbb.com";

    /** The Constant DEFAULT_ENABLED. */
    private static final Boolean DEFAULT_ENABLED = false;
    
    /** The Constant UPDATED_ENABLED. */
    private static final Boolean UPDATED_ENABLED = true;

    /** The sponsoring institution repository. */
    @Autowired
    private SponsoringInstitutionRepository sponsoringInstitutionRepository;

    /** The sponsoring institution mapper. */
    @Autowired
    private SponsoringInstitutionMapper sponsoringInstitutionMapper;

    /** The sponsoring institution service. */
    @Autowired
    private SponsoringInstitutionService sponsoringInstitutionService;

    /** The sponsoring institution query service. */
    @Autowired
    private SponsoringInstitutionQueryService sponsoringInstitutionQueryService;

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

    /** The rest sponsoring institution mock mvc. */
    private MockMvc restSponsoringInstitutionMockMvc;

    /** The sponsoring institution. */
    private SponsoringInstitution sponsoringInstitution;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SponsoringInstitutionResource sponsoringInstitutionResource = new SponsoringInstitutionResource(sponsoringInstitutionService, sponsoringInstitutionQueryService);
        this.restSponsoringInstitutionMockMvc = MockMvcBuilders.standaloneSetup(sponsoringInstitutionResource)
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
     * @return the sponsoring institution
     */
    public static SponsoringInstitution createEntity(EntityManager em) {
        SponsoringInstitution sponsoringInstitution = new SponsoringInstitution()
            .istituitionName(DEFAULT_ISTITUITION_NAME)
            .istituitionDescription(DEFAULT_ISTITUITION_DESCRIPTION)
            .istituitionSector(DEFAULT_ISTITUITION_SECTOR)
            .istituitionType(DEFAULT_ISTITUITION_TYPE)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .foundationDate(DEFAULT_FOUNDATION_DATE)
            .vatNumber(DEFAULT_VAT_NUMBER)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .country(DEFAULT_COUNTRY)
            .region(DEFAULT_REGION)
            .province(DEFAULT_PROVINCE)
            .city(DEFAULT_CITY)
            .cap(DEFAULT_CAP)
            .urlSite(DEFAULT_URL_SITE)
            .enabled(DEFAULT_ENABLED);
        return sponsoringInstitution;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        sponsoringInstitution = createEntity(em);
    }

    /**
     * Creates the sponsoring institution.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void createSponsoringInstitution() throws Exception {
        int databaseSizeBeforeCreate = sponsoringInstitutionRepository.findAll().size();

        // Create the SponsoringInstitution
        SponsoringInstitutionDTO sponsoringInstitutionDTO = sponsoringInstitutionMapper.toDto(sponsoringInstitution);
        restSponsoringInstitutionMockMvc.perform(post("/api/sponsoring-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsoringInstitutionDTO)))
            .andExpect(status().isCreated());

        // Validate the SponsoringInstitution in the database
        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeCreate + 1);
        SponsoringInstitution testSponsoringInstitution = sponsoringInstitutionList.get(sponsoringInstitutionList.size() - 1);
//        assertThat(testSponsoringInstitution.getIstituitionName()).isEqualTo(DEFAULT_ISTITUITION_NAME);
        assertThat(testSponsoringInstitution.getIstituitionDescription()).isEqualTo(DEFAULT_ISTITUITION_DESCRIPTION);
        assertThat(testSponsoringInstitution.getIstituitionSector()).isEqualTo(DEFAULT_ISTITUITION_SECTOR);
        assertThat(testSponsoringInstitution.getIstituitionType()).isEqualTo(DEFAULT_ISTITUITION_TYPE);
        assertThat(testSponsoringInstitution.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testSponsoringInstitution.getFoundationDate()).isEqualTo(DEFAULT_FOUNDATION_DATE);
        assertThat(testSponsoringInstitution.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
        assertThat(testSponsoringInstitution.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSponsoringInstitution.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSponsoringInstitution.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testSponsoringInstitution.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSponsoringInstitution.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testSponsoringInstitution.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testSponsoringInstitution.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSponsoringInstitution.getCap()).isEqualTo(DEFAULT_CAP);
        assertThat(testSponsoringInstitution.getUrlSite()).isEqualTo(DEFAULT_URL_SITE);
        assertThat(testSponsoringInstitution.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    /**
     * Creates the sponsoring institution with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createSponsoringInstitutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sponsoringInstitutionRepository.findAll().size();

        // Create the SponsoringInstitution with an existing ID
        sponsoringInstitution.setId(1L);
        SponsoringInstitutionDTO sponsoringInstitutionDTO = sponsoringInstitutionMapper.toDto(sponsoringInstitution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSponsoringInstitutionMockMvc.perform(post("/api/sponsoring-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsoringInstitutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SponsoringInstitution in the database
        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Check istituition name is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void checkIstituitionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsoringInstitutionRepository.findAll().size();
        // set the field null
        sponsoringInstitution.setIstituitionName(null);

        // Create the SponsoringInstitution, which fails.
        SponsoringInstitutionDTO sponsoringInstitutionDTO = sponsoringInstitutionMapper.toDto(sponsoringInstitution);

        restSponsoringInstitutionMockMvc.perform(post("/api/sponsoring-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsoringInstitutionDTO)))
            .andExpect(status().isBadRequest());

        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Check email is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsoringInstitutionRepository.findAll().size();
        // set the field null
        sponsoringInstitution.setEmail(null);

        // Create the SponsoringInstitution, which fails.
        SponsoringInstitutionDTO sponsoringInstitutionDTO = sponsoringInstitutionMapper.toDto(sponsoringInstitution);

        restSponsoringInstitutionMockMvc.perform(post("/api/sponsoring-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsoringInstitutionDTO)))
            .andExpect(status().isBadRequest());

        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Gets the all sponsoring institutions.
     *
     *  the all sponsoring institutions
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void getAllSponsoringInstitutions() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsoringInstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].istituitionName").value(hasItem(DEFAULT_ISTITUITION_NAME.toString())))
//            .andExpect(jsonPath("$.[*].istituitionDescription").value(hasItem(DEFAULT_ISTITUITION_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].istituitionSector").value(hasItem(DEFAULT_ISTITUITION_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].istituitionType").value(hasItem(DEFAULT_ISTITUITION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].foundationDate").value(hasItem(DEFAULT_FOUNDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].cap").value(hasItem(DEFAULT_CAP.toString())))
            .andExpect(jsonPath("$.[*].urlSite").value(hasItem(DEFAULT_URL_SITE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }
    
    /**
     * Gets the sponsoring institution.
     *
     *  the sponsoring institution
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getSponsoringInstitution() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get the sponsoringInstitution
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions/{id}", sponsoringInstitution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sponsoringInstitution.getId().intValue()))
            .andExpect(jsonPath("$.istituitionName").value(DEFAULT_ISTITUITION_NAME.toString()))
            .andExpect(jsonPath("$.istituitionDescription").value(DEFAULT_ISTITUITION_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.istituitionSector").value(DEFAULT_ISTITUITION_SECTOR.toString()))
            .andExpect(jsonPath("$.istituitionType").value(DEFAULT_ISTITUITION_TYPE.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.foundationDate").value(DEFAULT_FOUNDATION_DATE.toString()))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.cap").value(DEFAULT_CAP.toString()))
            .andExpect(jsonPath("$.urlSite").value(DEFAULT_URL_SITE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }


    /**
     * Gets the all sponsoring institutions by istituition name is equal to something.
     *
     *  the all sponsoring institutions by istituition name is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionName equals to DEFAULT_ISTITUITION_NAME
        defaultSponsoringInstitutionShouldBeFound("istituitionName.equals=" + DEFAULT_ISTITUITION_NAME);

        // Get all the sponsoringInstitutionList where istituitionName equals to UPDATED_ISTITUITION_NAME
        defaultSponsoringInstitutionShouldNotBeFound("istituitionName.equals=" + UPDATED_ISTITUITION_NAME);
    }

    /**
     * Gets the all sponsoring institutions by istituition name is in should work.
     *
     *  the all sponsoring institutions by istituition name is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionNameIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionName in DEFAULT_ISTITUITION_NAME or UPDATED_ISTITUITION_NAME
        defaultSponsoringInstitutionShouldBeFound("istituitionName.in=" + DEFAULT_ISTITUITION_NAME + "," + UPDATED_ISTITUITION_NAME);

        // Get all the sponsoringInstitutionList where istituitionName equals to UPDATED_ISTITUITION_NAME
        defaultSponsoringInstitutionShouldNotBeFound("istituitionName.in=" + UPDATED_ISTITUITION_NAME);
    }

    /**
     * Gets the all sponsoring institutions by istituition name is null or not null.
     *
     *  the all sponsoring institutions by istituition name is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionName is not null
        defaultSponsoringInstitutionShouldBeFound("istituitionName.specified=true");

        // Get all the sponsoringInstitutionList where istituitionName is null
        defaultSponsoringInstitutionShouldNotBeFound("istituitionName.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by istituition sector is equal to something.
     *
     *  the all sponsoring institutions by istituition sector is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionSectorIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionSector equals to DEFAULT_ISTITUITION_SECTOR
        defaultSponsoringInstitutionShouldBeFound("istituitionSector.equals=" + DEFAULT_ISTITUITION_SECTOR);

        // Get all the sponsoringInstitutionList where istituitionSector equals to UPDATED_ISTITUITION_SECTOR
        defaultSponsoringInstitutionShouldNotBeFound("istituitionSector.equals=" + UPDATED_ISTITUITION_SECTOR);
    }

    /**
     * Gets the all sponsoring institutions by istituition sector is in should work.
     *
     *  the all sponsoring institutions by istituition sector is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionSectorIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionSector in DEFAULT_ISTITUITION_SECTOR or UPDATED_ISTITUITION_SECTOR
        defaultSponsoringInstitutionShouldBeFound("istituitionSector.in=" + DEFAULT_ISTITUITION_SECTOR + "," + UPDATED_ISTITUITION_SECTOR);

        // Get all the sponsoringInstitutionList where istituitionSector equals to UPDATED_ISTITUITION_SECTOR
        defaultSponsoringInstitutionShouldNotBeFound("istituitionSector.in=" + UPDATED_ISTITUITION_SECTOR);
    }

    /**
     * Gets the all sponsoring institutions by istituition sector is null or not null.
     *
     *  the all sponsoring institutions by istituition sector is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionSectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionSector is not null
        defaultSponsoringInstitutionShouldBeFound("istituitionSector.specified=true");

        // Get all the sponsoringInstitutionList where istituitionSector is null
        defaultSponsoringInstitutionShouldNotBeFound("istituitionSector.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by istituition type is equal to something.
     *
     *  the all sponsoring institutions by istituition type is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionType equals to DEFAULT_ISTITUITION_TYPE
        defaultSponsoringInstitutionShouldBeFound("istituitionType.equals=" + DEFAULT_ISTITUITION_TYPE);

        // Get all the sponsoringInstitutionList where istituitionType equals to UPDATED_ISTITUITION_TYPE
        defaultSponsoringInstitutionShouldNotBeFound("istituitionType.equals=" + UPDATED_ISTITUITION_TYPE);
    }

    /**
     * Gets the all sponsoring institutions by istituition type is in should work.
     *
     *  the all sponsoring institutions by istituition type is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionType in DEFAULT_ISTITUITION_TYPE or UPDATED_ISTITUITION_TYPE
        defaultSponsoringInstitutionShouldBeFound("istituitionType.in=" + DEFAULT_ISTITUITION_TYPE + "," + UPDATED_ISTITUITION_TYPE);

        // Get all the sponsoringInstitutionList where istituitionType equals to UPDATED_ISTITUITION_TYPE
        defaultSponsoringInstitutionShouldNotBeFound("istituitionType.in=" + UPDATED_ISTITUITION_TYPE);
    }

    /**
     * Gets the all sponsoring institutions by istituition type is null or not null.
     *
     *  the all sponsoring institutions by istituition type is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByIstituitionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where istituitionType is not null
        defaultSponsoringInstitutionShouldBeFound("istituitionType.specified=true");

        // Get all the sponsoringInstitutionList where istituitionType is null
        defaultSponsoringInstitutionShouldNotBeFound("istituitionType.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by street address is equal to something.
     *
     *  the all sponsoring institutions by street address is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByStreetAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where streetAddress equals to DEFAULT_STREET_ADDRESS
        defaultSponsoringInstitutionShouldBeFound("streetAddress.equals=" + DEFAULT_STREET_ADDRESS);

        // Get all the sponsoringInstitutionList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultSponsoringInstitutionShouldNotBeFound("streetAddress.equals=" + UPDATED_STREET_ADDRESS);
    }

    /**
     * Gets the all sponsoring institutions by street address is in should work.
     *
     *  the all sponsoring institutions by street address is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByStreetAddressIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where streetAddress in DEFAULT_STREET_ADDRESS or UPDATED_STREET_ADDRESS
        defaultSponsoringInstitutionShouldBeFound("streetAddress.in=" + DEFAULT_STREET_ADDRESS + "," + UPDATED_STREET_ADDRESS);

        // Get all the sponsoringInstitutionList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultSponsoringInstitutionShouldNotBeFound("streetAddress.in=" + UPDATED_STREET_ADDRESS);
    }

    /**
     * Gets the all sponsoring institutions by street address is null or not null.
     *
     *  the all sponsoring institutions by street address is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByStreetAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where streetAddress is not null
        defaultSponsoringInstitutionShouldBeFound("streetAddress.specified=true");

        // Get all the sponsoringInstitutionList where streetAddress is null
        defaultSponsoringInstitutionShouldNotBeFound("streetAddress.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by foundation date is equal to something.
     *
     *  the all sponsoring institutions by foundation date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByFoundationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where foundationDate equals to DEFAULT_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldBeFound("foundationDate.equals=" + DEFAULT_FOUNDATION_DATE);

        // Get all the sponsoringInstitutionList where foundationDate equals to UPDATED_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldNotBeFound("foundationDate.equals=" + UPDATED_FOUNDATION_DATE);
    }

    /**
     * Gets the all sponsoring institutions by foundation date is in should work.
     *
     *  the all sponsoring institutions by foundation date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByFoundationDateIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where foundationDate in DEFAULT_FOUNDATION_DATE or UPDATED_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldBeFound("foundationDate.in=" + DEFAULT_FOUNDATION_DATE + "," + UPDATED_FOUNDATION_DATE);

        // Get all the sponsoringInstitutionList where foundationDate equals to UPDATED_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldNotBeFound("foundationDate.in=" + UPDATED_FOUNDATION_DATE);
    }

    /**
     * Gets the all sponsoring institutions by foundation date is null or not null.
     *
     *  the all sponsoring institutions by foundation date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByFoundationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where foundationDate is not null
        defaultSponsoringInstitutionShouldBeFound("foundationDate.specified=true");

        // Get all the sponsoringInstitutionList where foundationDate is null
        defaultSponsoringInstitutionShouldNotBeFound("foundationDate.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by foundation date is greater than or equal to something.
     *
     *  the all sponsoring institutions by foundation date is greater than or equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByFoundationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where foundationDate greater than or equals to DEFAULT_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldBeFound("foundationDate.greaterOrEqualThan=" + DEFAULT_FOUNDATION_DATE);

        // Get all the sponsoringInstitutionList where foundationDate greater than or equals to UPDATED_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldNotBeFound("foundationDate.greaterOrEqualThan=" + UPDATED_FOUNDATION_DATE);
    }

    /**
     * Gets the all sponsoring institutions by foundation date is less than something.
     *
     *  the all sponsoring institutions by foundation date is less than something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByFoundationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where foundationDate less than or equals to DEFAULT_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldNotBeFound("foundationDate.lessThan=" + DEFAULT_FOUNDATION_DATE);

        // Get all the sponsoringInstitutionList where foundationDate less than or equals to UPDATED_FOUNDATION_DATE
        defaultSponsoringInstitutionShouldBeFound("foundationDate.lessThan=" + UPDATED_FOUNDATION_DATE);
    }


    /**
     * Gets the all sponsoring institutions by vat number is equal to something.
     *
     *  the all sponsoring institutions by vat number is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByVatNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where vatNumber equals to DEFAULT_VAT_NUMBER
        defaultSponsoringInstitutionShouldBeFound("vatNumber.equals=" + DEFAULT_VAT_NUMBER);

        // Get all the sponsoringInstitutionList where vatNumber equals to UPDATED_VAT_NUMBER
        defaultSponsoringInstitutionShouldNotBeFound("vatNumber.equals=" + UPDATED_VAT_NUMBER);
    }

    /**
     * Gets the all sponsoring institutions by vat number is in should work.
     *
     *  the all sponsoring institutions by vat number is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByVatNumberIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where vatNumber in DEFAULT_VAT_NUMBER or UPDATED_VAT_NUMBER
        defaultSponsoringInstitutionShouldBeFound("vatNumber.in=" + DEFAULT_VAT_NUMBER + "," + UPDATED_VAT_NUMBER);

        // Get all the sponsoringInstitutionList where vatNumber equals to UPDATED_VAT_NUMBER
        defaultSponsoringInstitutionShouldNotBeFound("vatNumber.in=" + UPDATED_VAT_NUMBER);
    }

    /**
     * Gets the all sponsoring institutions by vat number is null or not null.
     *
     *  the all sponsoring institutions by vat number is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByVatNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where vatNumber is not null
        defaultSponsoringInstitutionShouldBeFound("vatNumber.specified=true");

        // Get all the sponsoringInstitutionList where vatNumber is null
        defaultSponsoringInstitutionShouldNotBeFound("vatNumber.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by email is equal to something.
     *
     *  the all sponsoring institutions by email is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where email equals to DEFAULT_EMAIL
        defaultSponsoringInstitutionShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the sponsoringInstitutionList where email equals to UPDATED_EMAIL
        defaultSponsoringInstitutionShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    /**
     * Gets the all sponsoring institutions by email is in should work.
     *
     *  the all sponsoring institutions by email is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSponsoringInstitutionShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the sponsoringInstitutionList where email equals to UPDATED_EMAIL
        defaultSponsoringInstitutionShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    /**
     * Gets the all sponsoring institutions by email is null or not null.
     *
     *  the all sponsoring institutions by email is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where email is not null
        defaultSponsoringInstitutionShouldBeFound("email.specified=true");

        // Get all the sponsoringInstitutionList where email is null
        defaultSponsoringInstitutionShouldNotBeFound("email.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by phone is equal to something.
     *
     *  the all sponsoring institutions by phone is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where phone equals to DEFAULT_PHONE
        defaultSponsoringInstitutionShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the sponsoringInstitutionList where phone equals to UPDATED_PHONE
        defaultSponsoringInstitutionShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    /**
     * Gets the all sponsoring institutions by phone is in should work.
     *
     *  the all sponsoring institutions by phone is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultSponsoringInstitutionShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the sponsoringInstitutionList where phone equals to UPDATED_PHONE
        defaultSponsoringInstitutionShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    /**
     * Gets the all sponsoring institutions by phone is null or not null.
     *
     *  the all sponsoring institutions by phone is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where phone is not null
        defaultSponsoringInstitutionShouldBeFound("phone.specified=true");

        // Get all the sponsoringInstitutionList where phone is null
        defaultSponsoringInstitutionShouldNotBeFound("phone.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by mobile phone is equal to something.
     *
     *  the all sponsoring institutions by mobile phone is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByMobilePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where mobilePhone equals to DEFAULT_MOBILE_PHONE
        defaultSponsoringInstitutionShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

        // Get all the sponsoringInstitutionList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultSponsoringInstitutionShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
    }

    /**
     * Gets the all sponsoring institutions by mobile phone is in should work.
     *
     *  the all sponsoring institutions by mobile phone is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByMobilePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where mobilePhone in DEFAULT_MOBILE_PHONE or UPDATED_MOBILE_PHONE
        defaultSponsoringInstitutionShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

        // Get all the sponsoringInstitutionList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultSponsoringInstitutionShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
    }

    /**
     * Gets the all sponsoring institutions by mobile phone is null or not null.
     *
     *  the all sponsoring institutions by mobile phone is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByMobilePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where mobilePhone is not null
        defaultSponsoringInstitutionShouldBeFound("mobilePhone.specified=true");

        // Get all the sponsoringInstitutionList where mobilePhone is null
        defaultSponsoringInstitutionShouldNotBeFound("mobilePhone.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by country is equal to something.
     *
     *  the all sponsoring institutions by country is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where country equals to DEFAULT_COUNTRY
        defaultSponsoringInstitutionShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the sponsoringInstitutionList where country equals to UPDATED_COUNTRY
        defaultSponsoringInstitutionShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    /**
     * Gets the all sponsoring institutions by country is in should work.
     *
     *  the all sponsoring institutions by country is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultSponsoringInstitutionShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the sponsoringInstitutionList where country equals to UPDATED_COUNTRY
        defaultSponsoringInstitutionShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    /**
     * Gets the all sponsoring institutions by country is null or not null.
     *
     *  the all sponsoring institutions by country is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where country is not null
        defaultSponsoringInstitutionShouldBeFound("country.specified=true");

        // Get all the sponsoringInstitutionList where country is null
        defaultSponsoringInstitutionShouldNotBeFound("country.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by region is equal to something.
     *
     *  the all sponsoring institutions by region is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where region equals to DEFAULT_REGION
        defaultSponsoringInstitutionShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the sponsoringInstitutionList where region equals to UPDATED_REGION
        defaultSponsoringInstitutionShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    /**
     * Gets the all sponsoring institutions by region is in should work.
     *
     *  the all sponsoring institutions by region is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where region in DEFAULT_REGION or UPDATED_REGION
        defaultSponsoringInstitutionShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the sponsoringInstitutionList where region equals to UPDATED_REGION
        defaultSponsoringInstitutionShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    /**
     * Gets the all sponsoring institutions by region is null or not null.
     *
     *  the all sponsoring institutions by region is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where region is not null
        defaultSponsoringInstitutionShouldBeFound("region.specified=true");

        // Get all the sponsoringInstitutionList where region is null
        defaultSponsoringInstitutionShouldNotBeFound("region.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by province is equal to something.
     *
     *  the all sponsoring institutions by province is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where province equals to DEFAULT_PROVINCE
        defaultSponsoringInstitutionShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

        // Get all the sponsoringInstitutionList where province equals to UPDATED_PROVINCE
        defaultSponsoringInstitutionShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
    }

    /**
     * Gets the all sponsoring institutions by province is in should work.
     *
     *  the all sponsoring institutions by province is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
        defaultSponsoringInstitutionShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

        // Get all the sponsoringInstitutionList where province equals to UPDATED_PROVINCE
        defaultSponsoringInstitutionShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
    }

    /**
     * Gets the all sponsoring institutions by province is null or not null.
     *
     *  the all sponsoring institutions by province is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where province is not null
        defaultSponsoringInstitutionShouldBeFound("province.specified=true");

        // Get all the sponsoringInstitutionList where province is null
        defaultSponsoringInstitutionShouldNotBeFound("province.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by city is equal to something.
     *
     *  the all sponsoring institutions by city is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where city equals to DEFAULT_CITY
        defaultSponsoringInstitutionShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the sponsoringInstitutionList where city equals to UPDATED_CITY
        defaultSponsoringInstitutionShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    /**
     * Gets the all sponsoring institutions by city is in should work.
     *
     *  the all sponsoring institutions by city is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where city in DEFAULT_CITY or UPDATED_CITY
        defaultSponsoringInstitutionShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the sponsoringInstitutionList where city equals to UPDATED_CITY
        defaultSponsoringInstitutionShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    /**
     * Gets the all sponsoring institutions by city is null or not null.
     *
     *  the all sponsoring institutions by city is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where city is not null
        defaultSponsoringInstitutionShouldBeFound("city.specified=true");

        // Get all the sponsoringInstitutionList where city is null
        defaultSponsoringInstitutionShouldNotBeFound("city.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by cap is equal to something.
     *
     *  the all sponsoring institutions by cap is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCapIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where cap equals to DEFAULT_CAP
        defaultSponsoringInstitutionShouldBeFound("cap.equals=" + DEFAULT_CAP);

        // Get all the sponsoringInstitutionList where cap equals to UPDATED_CAP
        defaultSponsoringInstitutionShouldNotBeFound("cap.equals=" + UPDATED_CAP);
    }

    /**
     * Gets the all sponsoring institutions by cap is in should work.
     *
     *  the all sponsoring institutions by cap is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCapIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where cap in DEFAULT_CAP or UPDATED_CAP
        defaultSponsoringInstitutionShouldBeFound("cap.in=" + DEFAULT_CAP + "," + UPDATED_CAP);

        // Get all the sponsoringInstitutionList where cap equals to UPDATED_CAP
        defaultSponsoringInstitutionShouldNotBeFound("cap.in=" + UPDATED_CAP);
    }

    /**
     * Gets the all sponsoring institutions by cap is null or not null.
     *
     *  the all sponsoring institutions by cap is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByCapIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where cap is not null
        defaultSponsoringInstitutionShouldBeFound("cap.specified=true");

        // Get all the sponsoringInstitutionList where cap is null
        defaultSponsoringInstitutionShouldNotBeFound("cap.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by url site is equal to something.
     *
     *  the all sponsoring institutions by url site is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByUrlSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where urlSite equals to DEFAULT_URL_SITE
        defaultSponsoringInstitutionShouldBeFound("urlSite.equals=" + DEFAULT_URL_SITE);

        // Get all the sponsoringInstitutionList where urlSite equals to UPDATED_URL_SITE
        defaultSponsoringInstitutionShouldNotBeFound("urlSite.equals=" + UPDATED_URL_SITE);
    }

    /**
     * Gets the all sponsoring institutions by url site is in should work.
     *
     *  the all sponsoring institutions by url site is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByUrlSiteIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where urlSite in DEFAULT_URL_SITE or UPDATED_URL_SITE
        defaultSponsoringInstitutionShouldBeFound("urlSite.in=" + DEFAULT_URL_SITE + "," + UPDATED_URL_SITE);

        // Get all the sponsoringInstitutionList where urlSite equals to UPDATED_URL_SITE
        defaultSponsoringInstitutionShouldNotBeFound("urlSite.in=" + UPDATED_URL_SITE);
    }

    /**
     * Gets the all sponsoring institutions by url site is null or not null.
     *
     *  the all sponsoring institutions by url site is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByUrlSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where urlSite is not null
        defaultSponsoringInstitutionShouldBeFound("urlSite.specified=true");

        // Get all the sponsoringInstitutionList where urlSite is null
        defaultSponsoringInstitutionShouldNotBeFound("urlSite.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by enabled is equal to something.
     *
     *  the all sponsoring institutions by enabled is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where enabled equals to DEFAULT_ENABLED
        defaultSponsoringInstitutionShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the sponsoringInstitutionList where enabled equals to UPDATED_ENABLED
        defaultSponsoringInstitutionShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    /**
     * Gets the all sponsoring institutions by enabled is in should work.
     *
     *  the all sponsoring institutions by enabled is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultSponsoringInstitutionShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the sponsoringInstitutionList where enabled equals to UPDATED_ENABLED
        defaultSponsoringInstitutionShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    /**
     * Gets the all sponsoring institutions by enabled is null or not null.
     *
     *  the all sponsoring institutions by enabled is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        // Get all the sponsoringInstitutionList where enabled is not null
        defaultSponsoringInstitutionShouldBeFound("enabled.specified=true");

        // Get all the sponsoringInstitutionList where enabled is null
        defaultSponsoringInstitutionShouldNotBeFound("enabled.specified=false");
    }

    /**
     * Gets the all sponsoring institutions by user is equal to something.
     *
     *  the all sponsoring institutions by user is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllSponsoringInstitutionsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        sponsoringInstitution.setUser(user);
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);
        Long userId = user.getId();

        // Get all the sponsoringInstitutionList where user equals to userId
        defaultSponsoringInstitutionShouldBeFound("userId.equals=" + userId);

        // Get all the sponsoringInstitutionList where user equals to userId + 1
        defaultSponsoringInstitutionShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultSponsoringInstitutionShouldBeFound(String filter) throws Exception {
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsoringInstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].istituitionName").value(hasItem(DEFAULT_ISTITUITION_NAME)))
//            .andExpect(jsonPath("$.[*].istituitionDescription").value(hasItem(DEFAULT_ISTITUITION_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].istituitionSector").value(hasItem(DEFAULT_ISTITUITION_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].istituitionType").value(hasItem(DEFAULT_ISTITUITION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].foundationDate").value(hasItem(DEFAULT_FOUNDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].cap").value(hasItem(DEFAULT_CAP)))
            .andExpect(jsonPath("$.[*].urlSite").value(hasItem(DEFAULT_URL_SITE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));

        // Check, that the count call also returns 1
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions/count?sort=id,desc&" + filter))
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
    private void defaultSponsoringInstitutionShouldNotBeFound(String filter) throws Exception {
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing sponsoring institution.
     *
     *  the non existing sponsoring institution
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingSponsoringInstitution() throws Exception {
        // Get the sponsoringInstitution
        restSponsoringInstitutionMockMvc.perform(get("/api/sponsoring-institutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update sponsoring institution.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void updateSponsoringInstitution() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        int databaseSizeBeforeUpdate = sponsoringInstitutionRepository.findAll().size();

        // Update the sponsoringInstitution
        SponsoringInstitution updatedSponsoringInstitution = sponsoringInstitutionRepository.findById(sponsoringInstitution.getId()).get();
        // Disconnect from session so that the updates on updatedSponsoringInstitution are not directly saved in db
        em.detach(updatedSponsoringInstitution);
        updatedSponsoringInstitution
            .istituitionName(UPDATED_ISTITUITION_NAME)
            .istituitionDescription(UPDATED_ISTITUITION_DESCRIPTION)
            .istituitionSector(UPDATED_ISTITUITION_SECTOR)
            .istituitionType(UPDATED_ISTITUITION_TYPE)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .foundationDate(UPDATED_FOUNDATION_DATE)
            .vatNumber(UPDATED_VAT_NUMBER)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .country(UPDATED_COUNTRY)
            .region(UPDATED_REGION)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .cap(UPDATED_CAP)
            .urlSite(UPDATED_URL_SITE)
            .enabled(UPDATED_ENABLED);
        SponsoringInstitutionDTO sponsoringInstitutionDTO = sponsoringInstitutionMapper.toDto(updatedSponsoringInstitution);

        restSponsoringInstitutionMockMvc.perform(put("/api/sponsoring-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsoringInstitutionDTO)))
            .andExpect(status().isOk());

        // Validate the SponsoringInstitution in the database
        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeUpdate);
        SponsoringInstitution testSponsoringInstitution = sponsoringInstitutionList.get(sponsoringInstitutionList.size() - 1);
        assertThat(testSponsoringInstitution.getIstituitionName()).isEqualTo(UPDATED_ISTITUITION_NAME);
        assertThat(testSponsoringInstitution.getIstituitionDescription()).isEqualTo(UPDATED_ISTITUITION_DESCRIPTION);
        assertThat(testSponsoringInstitution.getIstituitionSector()).isEqualTo(UPDATED_ISTITUITION_SECTOR);
        assertThat(testSponsoringInstitution.getIstituitionType()).isEqualTo(UPDATED_ISTITUITION_TYPE);
        assertThat(testSponsoringInstitution.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testSponsoringInstitution.getFoundationDate()).isEqualTo(UPDATED_FOUNDATION_DATE);
        assertThat(testSponsoringInstitution.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testSponsoringInstitution.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSponsoringInstitution.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSponsoringInstitution.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testSponsoringInstitution.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSponsoringInstitution.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testSponsoringInstitution.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testSponsoringInstitution.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSponsoringInstitution.getCap()).isEqualTo(UPDATED_CAP);
        assertThat(testSponsoringInstitution.getUrlSite()).isEqualTo(UPDATED_URL_SITE);
        assertThat(testSponsoringInstitution.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    /**
     * Update non existing sponsoring institution.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingSponsoringInstitution() throws Exception {
        int databaseSizeBeforeUpdate = sponsoringInstitutionRepository.findAll().size();

        // Create the SponsoringInstitution
        SponsoringInstitutionDTO sponsoringInstitutionDTO = sponsoringInstitutionMapper.toDto(sponsoringInstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsoringInstitutionMockMvc.perform(put("/api/sponsoring-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sponsoringInstitutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SponsoringInstitution in the database
        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete sponsoring institution.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void deleteSponsoringInstitution() throws Exception {
        // Initialize the database
        sponsoringInstitutionRepository.saveAndFlush(sponsoringInstitution);

        int databaseSizeBeforeDelete = sponsoringInstitutionRepository.findAll().size();

        // Delete the sponsoringInstitution
        restSponsoringInstitutionMockMvc.perform(delete("/api/sponsoring-institutions/{id}", sponsoringInstitution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SponsoringInstitution> sponsoringInstitutionList = sponsoringInstitutionRepository.findAll();
        assertThat(sponsoringInstitutionList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(SponsoringInstitution.class);
        SponsoringInstitution sponsoringInstitution1 = new SponsoringInstitution();
        sponsoringInstitution1.setId(1L);
        SponsoringInstitution sponsoringInstitution2 = new SponsoringInstitution();
        sponsoringInstitution2.setId(sponsoringInstitution1.getId());
        assertThat(sponsoringInstitution1).isEqualTo(sponsoringInstitution2);
        sponsoringInstitution2.setId(2L);
        assertThat(sponsoringInstitution1).isNotEqualTo(sponsoringInstitution2);
        sponsoringInstitution1.setId(null);
        assertThat(sponsoringInstitution1).isNotEqualTo(sponsoringInstitution2);
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
        TestUtil.equalsVerifier(SponsoringInstitutionDTO.class);
        SponsoringInstitutionDTO sponsoringInstitutionDTO1 = new SponsoringInstitutionDTO();
        sponsoringInstitutionDTO1.setId(1L);
        SponsoringInstitutionDTO sponsoringInstitutionDTO2 = new SponsoringInstitutionDTO();
        assertThat(sponsoringInstitutionDTO1).isNotEqualTo(sponsoringInstitutionDTO2);
        sponsoringInstitutionDTO2.setId(sponsoringInstitutionDTO1.getId());
        assertThat(sponsoringInstitutionDTO1).isEqualTo(sponsoringInstitutionDTO2);
        sponsoringInstitutionDTO2.setId(2L);
        assertThat(sponsoringInstitutionDTO1).isNotEqualTo(sponsoringInstitutionDTO2);
        sponsoringInstitutionDTO1.setId(null);
        assertThat(sponsoringInstitutionDTO1).isNotEqualTo(sponsoringInstitutionDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(sponsoringInstitutionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sponsoringInstitutionMapper.fromId(null)).isNull();
    }
}
