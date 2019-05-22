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
import org.mockito.Mock;
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
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.domain.enumeration.CompanySizeType;
import it.aranciaict.jobmatch.domain.enumeration.CompanyType;
import it.aranciaict.jobmatch.domain.enumeration.Country;
import it.aranciaict.jobmatch.domain.enumeration.NumberOfEmployees;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.service.CompanyQueryService;
import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.service.dto.CompanyDTO;
import it.aranciaict.jobmatch.service.mapper.CompanyMapper;
import it.aranciaict.jobmatch.web.rest.errors.ExceptionTranslator;
// TODO: Auto-generated Javadoc
/**
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class CompanyResourceIntTest {

    /** The Constant DEFAULT_COMPANY_NAME. */
    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    
    /** The Constant UPDATED_COMPANY_NAME. */
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    /** The Constant DEFAULT_COMPANY_DESCRIPTION. */
    private static final String DEFAULT_COMPANY_DESCRIPTION = "AAAAAAAAAA";
    
    /** The Constant UPDATED_COMPANY_DESCRIPTION. */
    private static final String UPDATED_COMPANY_DESCRIPTION = "BBBBBBBBBB";

    /** The Constant DEFAULT_COMPANY_SIZE. */
    private static final CompanySizeType DEFAULT_COMPANY_SIZE = CompanySizeType.SMALL;
    
    /** The Constant UPDATED_COMPANY_SIZE. */
    private static final CompanySizeType UPDATED_COMPANY_SIZE = CompanySizeType.MEDIUM;

    /** The Constant DEFAULT_COMPANY_TYPE. */
    private static final CompanyType DEFAULT_COMPANY_TYPE = CompanyType.PRIVATE;
    
    /** The Constant UPDATED_COMPANY_TYPE. */
    private static final CompanyType UPDATED_COMPANY_TYPE = CompanyType.PUBLIC;

    /** The Constant DEFAULT_NUMBER_OF_EMPLOYEE. */
    private static final NumberOfEmployees DEFAULT_NUMBER_OF_EMPLOYEE = NumberOfEmployees.FROM_1_TO_9;
    
    /** The Constant UPDATED_NUMBER_OF_EMPLOYEE. */
    private static final NumberOfEmployees UPDATED_NUMBER_OF_EMPLOYEE = NumberOfEmployees.FROM_10_TO_19;

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
    private static final String DEFAULT_PHONE = "111111111111";
    
    /** The Constant UPDATED_PHONE. */
    private static final String UPDATED_PHONE = "222222222222";

    /** The Constant DEFAULT_MOBILE_PHONE. */
    private static final String DEFAULT_MOBILE_PHONE = "111111111111";
    
    /** The Constant UPDATED_MOBILE_PHONE. */
    private static final String UPDATED_MOBILE_PHONE = "222222222222";

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
    private static final String DEFAULT_URL_SITE = "www.aaaa.it";
    
    /** The Constant UPDATED_URL_SITE. */
    private static final String UPDATED_URL_SITE = "www.bbbb.com";

    /** The Constant DEFAULT_ENABLED. */
    private static final Boolean DEFAULT_ENABLED = false;
    
    /** The Constant UPDATED_ENABLED. */
    private static final Boolean UPDATED_ENABLED = true;

    /** The company repository. */
    @Autowired
    private CompanyRepository companyRepository;

    /** The company repository mock. */
    @Mock
    private CompanyRepository companyRepositoryMock;

    /** The company mapper. */
    @Autowired
    private CompanyMapper companyMapper;

    /** The company service mock. */
    @Mock
    private CompanyService companyServiceMock;

    /** The company service. */
    @Autowired
    private CompanyService companyService;

    /** The company query service. */
    @Autowired
    private CompanyQueryService companyQueryService;

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

    /** The rest company mock mvc. */
    private MockMvc restCompanyMockMvc;

    /** The company. */
    private Company company;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyResource companyResource = new CompanyResource(companyService, companyQueryService);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
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
     * @return the company
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyName(DEFAULT_COMPANY_NAME)
            .companyDescription(DEFAULT_COMPANY_DESCRIPTION)
            .companySize(DEFAULT_COMPANY_SIZE)
            .companyType(DEFAULT_COMPANY_TYPE)
            .numberOfEmployee(DEFAULT_NUMBER_OF_EMPLOYEE)
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
        return company;
    }

    /**
     * Inits the test.
     */
    @Before
    public void initTest() {
        company = createEntity(em);
    }

    /**
     * Creates the company.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getCompanyDescription()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION);
        assertThat(testCompany.getCompanySize()).isEqualTo(DEFAULT_COMPANY_SIZE);
        assertThat(testCompany.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);
        assertThat(testCompany.getNumberOfEmployee()).isEqualTo(DEFAULT_NUMBER_OF_EMPLOYEE);
        assertThat(testCompany.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testCompany.getFoundationDate()).isEqualTo(DEFAULT_FOUNDATION_DATE);
        assertThat(testCompany.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCompany.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testCompany.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCompany.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCompany.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testCompany.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCompany.getCap()).isEqualTo(DEFAULT_CAP);
        assertThat(testCompany.getUrlSite()).isEqualTo(DEFAULT_URL_SITE);
        assertThat(testCompany.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    /**
     * Creates the company with existing id.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    /**
     * Check company name is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCompanyName(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Check email is required.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setEmail(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    /**
     * Gets the all companies.
     *
     *  the all companies
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
//            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE.toString())))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numberOfEmployee").value(hasItem(DEFAULT_NUMBER_OF_EMPLOYEE.toString())))
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
    
//    /**
//     * Gets the all companies with eager relationships is enabled.
//     *
//     *  the all companies with eager relationships is enabled
//     * @throws Exception the exception
//     */
//    @SuppressWarnings({"unchecked"})
//    public void getAllCompaniesWithEagerRelationshipsIsEnabled() throws Exception {
//        CompanyResource companyResource = new CompanyResource(companyServiceMock, companyQueryService);
//        when(companyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
//
//        MockMvc restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter).build();
//
//        restCompanyMockMvc.perform(get("/api/companies?eagerload=true"))
//        .andExpect(status().isOk());
//
//        verify(companyServiceMock, times(1)).findAllWithEagerRelationships(any());
//    }

//    /**
//     * Gets the all companies with eager relationships is not enabled.
//     *
//     *  the all companies with eager relationships is not enabled
//     * @throws Exception the exception
//     */
//    @SuppressWarnings({"unchecked"})
//    public void getAllCompaniesWithEagerRelationshipsIsNotEnabled() throws Exception {
//        CompanyResource companyResource = new CompanyResource(companyServiceMock, companyQueryService);
//            when(companyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
//            MockMvc restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setConversionService(createFormattingConversionService())
//            .setMessageConverters(jacksonMessageConverter).build();
//
//        restCompanyMockMvc.perform(get("/api/companies?eagerload=true"))
//        .andExpect(status().isOk());
//
//            verify(companyServiceMock, times(1)).findAllWithEagerRelationships(any());
//    }

    /**
     * Gets the company.
     *
     *  the company
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.companyDescription").value(DEFAULT_COMPANY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.companySize").value(DEFAULT_COMPANY_SIZE.toString()))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE.toString()))
            .andExpect(jsonPath("$.numberOfEmployee").value(DEFAULT_NUMBER_OF_EMPLOYEE.toString()))
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
     * Gets the all companies by company name is equal to something.
     *
     *  the all companies by company name is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the companyList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    /**
     * Gets the all companies by company name is in should work.
     *
     *  the all companies by company name is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCompanyShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the companyList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    /**
     * Gets the all companies by company name is null or not null.
     *
     *  the all companies by company name is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyName is not null
        defaultCompanyShouldBeFound("companyName.specified=true");

        // Get all the companyList where companyName is null
        defaultCompanyShouldNotBeFound("companyName.specified=false");
    }

    /**
     * Gets the all companies by company size is equal to something.
     *
     *  the all companies by company size is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companySize equals to DEFAULT_COMPANY_SIZE
        defaultCompanyShouldBeFound("companySize.equals=" + DEFAULT_COMPANY_SIZE);

        // Get all the companyList where companySize equals to UPDATED_COMPANY_SIZE
        defaultCompanyShouldNotBeFound("companySize.equals=" + UPDATED_COMPANY_SIZE);
    }

    /**
     * Gets the all companies by company size is in should work.
     *
     *  the all companies by company size is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanySizeIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companySize in DEFAULT_COMPANY_SIZE or UPDATED_COMPANY_SIZE
        defaultCompanyShouldBeFound("companySize.in=" + DEFAULT_COMPANY_SIZE + "," + UPDATED_COMPANY_SIZE);

        // Get all the companyList where companySize equals to UPDATED_COMPANY_SIZE
        defaultCompanyShouldNotBeFound("companySize.in=" + UPDATED_COMPANY_SIZE);
    }

    /**
     * Gets the all companies by company size is null or not null.
     *
     *  the all companies by company size is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companySize is not null
        defaultCompanyShouldBeFound("companySize.specified=true");

        // Get all the companyList where companySize is null
        defaultCompanyShouldNotBeFound("companySize.specified=false");
    }

    /**
     * Gets the all companies by company type is equal to something.
     *
     *  the all companies by company type is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyType equals to DEFAULT_COMPANY_TYPE
        defaultCompanyShouldBeFound("companyType.equals=" + DEFAULT_COMPANY_TYPE);

        // Get all the companyList where companyType equals to UPDATED_COMPANY_TYPE
        defaultCompanyShouldNotBeFound("companyType.equals=" + UPDATED_COMPANY_TYPE);
    }

    /**
     * Gets the all companies by company type is in should work.
     *
     *  the all companies by company type is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyType in DEFAULT_COMPANY_TYPE or UPDATED_COMPANY_TYPE
        defaultCompanyShouldBeFound("companyType.in=" + DEFAULT_COMPANY_TYPE + "," + UPDATED_COMPANY_TYPE);

        // Get all the companyList where companyType equals to UPDATED_COMPANY_TYPE
        defaultCompanyShouldNotBeFound("companyType.in=" + UPDATED_COMPANY_TYPE);
    }

    /**
     * Gets the all companies by company type is null or not null.
     *
     *  the all companies by company type is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCompanyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where companyType is not null
        defaultCompanyShouldBeFound("companyType.specified=true");

        // Get all the companyList where companyType is null
        defaultCompanyShouldNotBeFound("companyType.specified=false");
    }

    /**
     * Gets the all companies by number of employee is equal to something.
     *
     *  the all companies by number of employee is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByNumberOfEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numberOfEmployee equals to DEFAULT_NUMBER_OF_EMPLOYEE
        defaultCompanyShouldBeFound("numberOfEmployee.equals=" + DEFAULT_NUMBER_OF_EMPLOYEE);

        // Get all the companyList where numberOfEmployee equals to UPDATED_NUMBER_OF_EMPLOYEE
        defaultCompanyShouldNotBeFound("numberOfEmployee.equals=" + UPDATED_NUMBER_OF_EMPLOYEE);
    }

    /**
     * Gets the all companies by number of employee is in should work.
     *
     *  the all companies by number of employee is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByNumberOfEmployeeIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numberOfEmployee in DEFAULT_NUMBER_OF_EMPLOYEE or UPDATED_NUMBER_OF_EMPLOYEE
        defaultCompanyShouldBeFound("numberOfEmployee.in=" + DEFAULT_NUMBER_OF_EMPLOYEE + "," + UPDATED_NUMBER_OF_EMPLOYEE);

        // Get all the companyList where numberOfEmployee equals to UPDATED_NUMBER_OF_EMPLOYEE
        defaultCompanyShouldNotBeFound("numberOfEmployee.in=" + UPDATED_NUMBER_OF_EMPLOYEE);
    }

    /**
     * Gets the all companies by number of employee is null or not null.
     *
     *  the all companies by number of employee is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByNumberOfEmployeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where numberOfEmployee is not null
        defaultCompanyShouldBeFound("numberOfEmployee.specified=true");

        // Get all the companyList where numberOfEmployee is null
        defaultCompanyShouldNotBeFound("numberOfEmployee.specified=false");
    }

    /**
     * Gets the all companies by street address is equal to something.
     *
     *  the all companies by street address is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByStreetAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where streetAddress equals to DEFAULT_STREET_ADDRESS
        defaultCompanyShouldBeFound("streetAddress.equals=" + DEFAULT_STREET_ADDRESS);

        // Get all the companyList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultCompanyShouldNotBeFound("streetAddress.equals=" + UPDATED_STREET_ADDRESS);
    }

    /**
     * Gets the all companies by street address is in should work.
     *
     *  the all companies by street address is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByStreetAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where streetAddress in DEFAULT_STREET_ADDRESS or UPDATED_STREET_ADDRESS
        defaultCompanyShouldBeFound("streetAddress.in=" + DEFAULT_STREET_ADDRESS + "," + UPDATED_STREET_ADDRESS);

        // Get all the companyList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultCompanyShouldNotBeFound("streetAddress.in=" + UPDATED_STREET_ADDRESS);
    }

    /**
     * Gets the all companies by street address is null or not null.
     *
     *  the all companies by street address is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByStreetAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where streetAddress is not null
        defaultCompanyShouldBeFound("streetAddress.specified=true");

        // Get all the companyList where streetAddress is null
        defaultCompanyShouldNotBeFound("streetAddress.specified=false");
    }

    /**
     * Gets the all companies by foundation date is equal to something.
     *
     *  the all companies by foundation date is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByFoundationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where foundationDate equals to DEFAULT_FOUNDATION_DATE
        defaultCompanyShouldBeFound("foundationDate.equals=" + DEFAULT_FOUNDATION_DATE);

        // Get all the companyList where foundationDate equals to UPDATED_FOUNDATION_DATE
        defaultCompanyShouldNotBeFound("foundationDate.equals=" + UPDATED_FOUNDATION_DATE);
    }

    /**
     * Gets the all companies by foundation date is in should work.
     *
     *  the all companies by foundation date is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByFoundationDateIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where foundationDate in DEFAULT_FOUNDATION_DATE or UPDATED_FOUNDATION_DATE
        defaultCompanyShouldBeFound("foundationDate.in=" + DEFAULT_FOUNDATION_DATE + "," + UPDATED_FOUNDATION_DATE);

        // Get all the companyList where foundationDate equals to UPDATED_FOUNDATION_DATE
        defaultCompanyShouldNotBeFound("foundationDate.in=" + UPDATED_FOUNDATION_DATE);
    }

    /**
     * Gets the all companies by foundation date is null or not null.
     *
     *  the all companies by foundation date is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByFoundationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where foundationDate is not null
        defaultCompanyShouldBeFound("foundationDate.specified=true");

        // Get all the companyList where foundationDate is null
        defaultCompanyShouldNotBeFound("foundationDate.specified=false");
    }

    /**
     * Gets the all companies by foundation date is greater than or equal to something.
     *
     *  the all companies by foundation date is greater than or equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByFoundationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where foundationDate greater than or equals to DEFAULT_FOUNDATION_DATE
        defaultCompanyShouldBeFound("foundationDate.greaterOrEqualThan=" + DEFAULT_FOUNDATION_DATE);

        // Get all the companyList where foundationDate greater than or equals to UPDATED_FOUNDATION_DATE
        defaultCompanyShouldNotBeFound("foundationDate.greaterOrEqualThan=" + UPDATED_FOUNDATION_DATE);
    }

    /**
     * Gets the all companies by foundation date is less than something.
     *
     *  the all companies by foundation date is less than something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByFoundationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where foundationDate less than or equals to DEFAULT_FOUNDATION_DATE
        defaultCompanyShouldNotBeFound("foundationDate.lessThan=" + DEFAULT_FOUNDATION_DATE);

        // Get all the companyList where foundationDate less than or equals to UPDATED_FOUNDATION_DATE
        defaultCompanyShouldBeFound("foundationDate.lessThan=" + UPDATED_FOUNDATION_DATE);
    }


    /**
     * Gets the all companies by vat number is equal to something.
     *
     *  the all companies by vat number is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByVatNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where vatNumber equals to DEFAULT_VAT_NUMBER
        defaultCompanyShouldBeFound("vatNumber.equals=" + DEFAULT_VAT_NUMBER);

        // Get all the companyList where vatNumber equals to UPDATED_VAT_NUMBER
        defaultCompanyShouldNotBeFound("vatNumber.equals=" + UPDATED_VAT_NUMBER);
    }

    /**
     * Gets the all companies by vat number is in should work.
     *
     *  the all companies by vat number is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByVatNumberIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where vatNumber in DEFAULT_VAT_NUMBER or UPDATED_VAT_NUMBER
        defaultCompanyShouldBeFound("vatNumber.in=" + DEFAULT_VAT_NUMBER + "," + UPDATED_VAT_NUMBER);

        // Get all the companyList where vatNumber equals to UPDATED_VAT_NUMBER
        defaultCompanyShouldNotBeFound("vatNumber.in=" + UPDATED_VAT_NUMBER);
    }

    /**
     * Gets the all companies by vat number is null or not null.
     *
     *  the all companies by vat number is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByVatNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where vatNumber is not null
        defaultCompanyShouldBeFound("vatNumber.specified=true");

        // Get all the companyList where vatNumber is null
        defaultCompanyShouldNotBeFound("vatNumber.specified=false");
    }

    /**
     * Gets the all companies by email is equal to something.
     *
     *  the all companies by email is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email equals to DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    /**
     * Gets the all companies by email is in should work.
     *
     *  the all companies by email is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    /**
     * Gets the all companies by email is null or not null.
     *
     *  the all companies by email is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email is not null
        defaultCompanyShouldBeFound("email.specified=true");

        // Get all the companyList where email is null
        defaultCompanyShouldNotBeFound("email.specified=false");
    }

    /**
     * Gets the all companies by phone is equal to something.
     *
     *  the all companies by phone is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone equals to DEFAULT_PHONE
        defaultCompanyShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the companyList where phone equals to UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    /**
     * Gets the all companies by phone is in should work.
     *
     *  the all companies by phone is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCompanyShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the companyList where phone equals to UPDATED_PHONE
        defaultCompanyShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    /**
     * Gets the all companies by phone is null or not null.
     *
     *  the all companies by phone is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where phone is not null
        defaultCompanyShouldBeFound("phone.specified=true");

        // Get all the companyList where phone is null
        defaultCompanyShouldNotBeFound("phone.specified=false");
    }

    /**
     * Gets the all companies by mobile phone is equal to something.
     *
     *  the all companies by mobile phone is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByMobilePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobilePhone equals to DEFAULT_MOBILE_PHONE
        defaultCompanyShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

        // Get all the companyList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultCompanyShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
    }

    /**
     * Gets the all companies by mobile phone is in should work.
     *
     *  the all companies by mobile phone is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByMobilePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobilePhone in DEFAULT_MOBILE_PHONE or UPDATED_MOBILE_PHONE
        defaultCompanyShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

        // Get all the companyList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultCompanyShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
    }

    /**
     * Gets the all companies by mobile phone is null or not null.
     *
     *  the all companies by mobile phone is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByMobilePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where mobilePhone is not null
        defaultCompanyShouldBeFound("mobilePhone.specified=true");

        // Get all the companyList where mobilePhone is null
        defaultCompanyShouldNotBeFound("mobilePhone.specified=false");
    }

    /**
     * Gets the all companies by country is equal to something.
     *
     *  the all companies by country is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where country equals to DEFAULT_COUNTRY
        defaultCompanyShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the companyList where country equals to UPDATED_COUNTRY
        defaultCompanyShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    /**
     * Gets the all companies by country is in should work.
     *
     *  the all companies by country is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultCompanyShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the companyList where country equals to UPDATED_COUNTRY
        defaultCompanyShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    /**
     * Gets the all companies by country is null or not null.
     *
     *  the all companies by country is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where country is not null
        defaultCompanyShouldBeFound("country.specified=true");

        // Get all the companyList where country is null
        defaultCompanyShouldNotBeFound("country.specified=false");
    }

    /**
     * Gets the all companies by region is equal to something.
     *
     *  the all companies by region is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where region equals to DEFAULT_REGION
        defaultCompanyShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the companyList where region equals to UPDATED_REGION
        defaultCompanyShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    /**
     * Gets the all companies by region is in should work.
     *
     *  the all companies by region is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where region in DEFAULT_REGION or UPDATED_REGION
        defaultCompanyShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the companyList where region equals to UPDATED_REGION
        defaultCompanyShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    /**
     * Gets the all companies by region is null or not null.
     *
     *  the all companies by region is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where region is not null
        defaultCompanyShouldBeFound("region.specified=true");

        // Get all the companyList where region is null
        defaultCompanyShouldNotBeFound("region.specified=false");
    }

    /**
     * Gets the all companies by province is equal to something.
     *
     *  the all companies by province is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where province equals to DEFAULT_PROVINCE
        defaultCompanyShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

        // Get all the companyList where province equals to UPDATED_PROVINCE
        defaultCompanyShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
    }

    /**
     * Gets the all companies by province is in should work.
     *
     *  the all companies by province is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
        defaultCompanyShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

        // Get all the companyList where province equals to UPDATED_PROVINCE
        defaultCompanyShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
    }

    /**
     * Gets the all companies by province is null or not null.
     *
     *  the all companies by province is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where province is not null
        defaultCompanyShouldBeFound("province.specified=true");

        // Get all the companyList where province is null
        defaultCompanyShouldNotBeFound("province.specified=false");
    }

    /**
     * Gets the all companies by city is equal to something.
     *
     *  the all companies by city is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where city equals to DEFAULT_CITY
        defaultCompanyShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the companyList where city equals to UPDATED_CITY
        defaultCompanyShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    /**
     * Gets the all companies by city is in should work.
     *
     *  the all companies by city is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where city in DEFAULT_CITY or UPDATED_CITY
        defaultCompanyShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the companyList where city equals to UPDATED_CITY
        defaultCompanyShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    /**
     * Gets the all companies by city is null or not null.
     *
     *  the all companies by city is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where city is not null
        defaultCompanyShouldBeFound("city.specified=true");

        // Get all the companyList where city is null
        defaultCompanyShouldNotBeFound("city.specified=false");
    }

    /**
     * Gets the all companies by cap is equal to something.
     *
     *  the all companies by cap is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCapIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where cap equals to DEFAULT_CAP
        defaultCompanyShouldBeFound("cap.equals=" + DEFAULT_CAP);

        // Get all the companyList where cap equals to UPDATED_CAP
        defaultCompanyShouldNotBeFound("cap.equals=" + UPDATED_CAP);
    }

    /**
     * Gets the all companies by cap is in should work.
     *
     *  the all companies by cap is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCapIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where cap in DEFAULT_CAP or UPDATED_CAP
        defaultCompanyShouldBeFound("cap.in=" + DEFAULT_CAP + "," + UPDATED_CAP);

        // Get all the companyList where cap equals to UPDATED_CAP
        defaultCompanyShouldNotBeFound("cap.in=" + UPDATED_CAP);
    }

    /**
     * Gets the all companies by cap is null or not null.
     *
     *  the all companies by cap is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByCapIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where cap is not null
        defaultCompanyShouldBeFound("cap.specified=true");

        // Get all the companyList where cap is null
        defaultCompanyShouldNotBeFound("cap.specified=false");
    }

    /**
     * Gets the all companies by url site is equal to something.
     *
     *  the all companies by url site is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByUrlSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where urlSite equals to DEFAULT_URL_SITE
        defaultCompanyShouldBeFound("urlSite.equals=" + DEFAULT_URL_SITE);

        // Get all the companyList where urlSite equals to UPDATED_URL_SITE
        defaultCompanyShouldNotBeFound("urlSite.equals=" + UPDATED_URL_SITE);
    }

    /**
     * Gets the all companies by url site is in should work.
     *
     *  the all companies by url site is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByUrlSiteIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where urlSite in DEFAULT_URL_SITE or UPDATED_URL_SITE
        defaultCompanyShouldBeFound("urlSite.in=" + DEFAULT_URL_SITE + "," + UPDATED_URL_SITE);

        // Get all the companyList where urlSite equals to UPDATED_URL_SITE
        defaultCompanyShouldNotBeFound("urlSite.in=" + UPDATED_URL_SITE);
    }

    /**
     * Gets the all companies by url site is null or not null.
     *
     *  the all companies by url site is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByUrlSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where urlSite is not null
        defaultCompanyShouldBeFound("urlSite.specified=true");

        // Get all the companyList where urlSite is null
        defaultCompanyShouldNotBeFound("urlSite.specified=false");
    }

    /**
     * Gets the all companies by enabled is equal to something.
     *
     *  the all companies by enabled is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where enabled equals to DEFAULT_ENABLED
        defaultCompanyShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the companyList where enabled equals to UPDATED_ENABLED
        defaultCompanyShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    /**
     * Gets the all companies by enabled is in should work.
     *
     *  the all companies by enabled is in should work
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultCompanyShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the companyList where enabled equals to UPDATED_ENABLED
        defaultCompanyShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    /**
     * Gets the all companies by enabled is null or not null.
     *
     *  the all companies by enabled is null or not null
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where enabled is not null
        defaultCompanyShouldBeFound("enabled.specified=true");

        // Get all the companyList where enabled is null
        defaultCompanyShouldNotBeFound("enabled.specified=false");
    }

    /**
     * Gets the all companies by user is equal to something.
     *
     *  the all companies by user is equal to something
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getAllCompaniesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        company.setUser(user);
        companyRepository.saveAndFlush(company);
        Long userId = user.getId();

        // Get all the companyList where user equals to userId
        defaultCompanyShouldBeFound("userId.equals=" + userId);

        // Get all the companyList where user equals to userId + 1
        defaultCompanyShouldNotBeFound("userId.equals=" + (userId + 1));
    }


//    /**
//     * Gets the all companies by sector is equal to something.
//     *
//     *  the all companies by sector is equal to something
//     * @throws Exception the exception
//     */
//    @Test
//    @Transactional
//    public void getAllCompaniesBySectorIsEqualToSomething() throws Exception {
//        // Initialize the database
//        CompanySector sector = CompanySectorResourceIntTest.createEntity(em);
//        em.persist(sector);
//        em.flush();
//        company.addSector(sector);
//        companyRepository.saveAndFlush(company);
//        Long sectorId = sector.getId();
//
//        // Get all the companyList where sector equals to sectorId
//        defaultCompanyShouldBeFound("sectorId.equals=" + sectorId);
//
//        // Get all the companyList where sector equals to sectorId + 1
//        defaultCompanyShouldNotBeFound("sectorId.equals=" + (sectorId + 1));
//    }

    /**
     * Executes the search, and checks that the default entity is returned.
     *
     * @param filter the filter
     * @throws Exception the exception
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
//            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE.toString())))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numberOfEmployee").value(hasItem(DEFAULT_NUMBER_OF_EMPLOYEE.toString())))
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
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
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
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    /**
     * Gets the non existing company.
     *
     *  the non existing company
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /**
     * Update company.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .companySize(UPDATED_COMPANY_SIZE)
            .companyType(UPDATED_COMPANY_TYPE)
            .numberOfEmployee(UPDATED_NUMBER_OF_EMPLOYEE)
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
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testCompany.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testCompany.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testCompany.getNumberOfEmployee()).isEqualTo(UPDATED_NUMBER_OF_EMPLOYEE);
        assertThat(testCompany.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testCompany.getFoundationDate()).isEqualTo(UPDATED_FOUNDATION_DATE);
        assertThat(testCompany.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompany.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testCompany.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCompany.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCompany.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testCompany.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCompany.getCap()).isEqualTo(UPDATED_CAP);
        assertThat(testCompany.getUrlSite()).isEqualTo(UPDATED_URL_SITE);
        assertThat(testCompany.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    /**
     * Update non existing company.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    /**
     * Delete company.
     *
     * @throws Exception the exception
     */
    @Test
    @Transactional
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
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
        TestUtil.equalsVerifier(Company.class);
        Company company1 = new Company();
        company1.setId(1L);
        Company company2 = new Company();
        company2.setId(company1.getId());
        assertThat(company1).isEqualTo(company2);
        company2.setId(2L);
        assertThat(company1).isNotEqualTo(company2);
        company1.setId(null);
        assertThat(company1).isNotEqualTo(company2);
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
        TestUtil.equalsVerifier(CompanyDTO.class);
        CompanyDTO companyDTO1 = new CompanyDTO();
        companyDTO1.setId(1L);
        CompanyDTO companyDTO2 = new CompanyDTO();
        assertThat(companyDTO1).isNotEqualTo(companyDTO2);
        companyDTO2.setId(companyDTO1.getId());
        assertThat(companyDTO1).isEqualTo(companyDTO2);
        companyDTO2.setId(2L);
        assertThat(companyDTO1).isNotEqualTo(companyDTO2);
        companyDTO1.setId(null);
        assertThat(companyDTO1).isNotEqualTo(companyDTO2);
    }

    /**
     * Test entity from id.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void testEntityFromId() {
        assertThat(companyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyMapper.fromId(null)).isNull();
    }
}
