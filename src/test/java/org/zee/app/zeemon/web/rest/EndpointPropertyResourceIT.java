package org.zee.app.zeemon.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.zee.app.zeemon.ZeemonApp;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.EndpointProperty;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.domain.enumeration.PropKeyType;
import org.zee.app.zeemon.repository.EndpointPropertyRepository;
import org.zee.app.zeemon.service.EndpointPropertyService;
import org.zee.app.zeemon.web.rest.EndpointPropertyResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link EndpointPropertyResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class EndpointPropertyResourceIT {

    private static final String DEFAULT_PROP_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PROP_KEY = "BBBBBBBBBB";

    private static final PropKeyType DEFAULT_PROP_KEY_TYPE = PropKeyType.ANY;
    private static final PropKeyType UPDATED_PROP_KEY_TYPE = PropKeyType.SCHEMA;

    private static final String DEFAULT_PROP_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PROP_VALUE = "BBBBBBBBBB";

    private static final DataType DEFAULT_PROP_VALUE_TYPE = DataType.STRING;
    private static final DataType UPDATED_PROP_VALUE_TYPE = DataType.NUMBER;

    private static final String DEFAULT_PROP_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROP_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EndpointPropertyRepository endpointPropertyRepository;

    @Autowired
    private EndpointPropertyService endpointPropertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEndpointPropertyMockMvc;

    private EndpointProperty endpointProperty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EndpointPropertyResource endpointPropertyResource = new EndpointPropertyResource(endpointPropertyService);
        this.restEndpointPropertyMockMvc = MockMvcBuilders.standaloneSetup(endpointPropertyResource)
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
     */
    public static EndpointProperty createEntity(EntityManager em) {
        EndpointProperty endpointProperty = new EndpointProperty()
            .propKey(DEFAULT_PROP_KEY)
            .propKeyType(DEFAULT_PROP_KEY_TYPE)
            .propValue(DEFAULT_PROP_VALUE)
            .propValueType(DEFAULT_PROP_VALUE_TYPE)
            .propDescription(DEFAULT_PROP_DESCRIPTION);
        // Add required entity
        Endpoint endpoint;
        if (TestUtil.findAll(em, Endpoint.class).isEmpty()) {
            endpoint = EndpointResourceIT.createEntity(em);
            em.persist(endpoint);
            em.flush();
        } else {
            endpoint = TestUtil.findAll(em, Endpoint.class).get(0);
        }
        endpointProperty.setEndpoint(endpoint);
        return endpointProperty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EndpointProperty createUpdatedEntity(EntityManager em) {
        EndpointProperty endpointProperty = new EndpointProperty()
            .propKey(UPDATED_PROP_KEY)
            .propKeyType(UPDATED_PROP_KEY_TYPE)
            .propValue(UPDATED_PROP_VALUE)
            .propValueType(UPDATED_PROP_VALUE_TYPE)
            .propDescription(UPDATED_PROP_DESCRIPTION);
        // Add required entity
        Endpoint endpoint;
        if (TestUtil.findAll(em, Endpoint.class).isEmpty()) {
            endpoint = EndpointResourceIT.createUpdatedEntity(em);
            em.persist(endpoint);
            em.flush();
        } else {
            endpoint = TestUtil.findAll(em, Endpoint.class).get(0);
        }
        endpointProperty.setEndpoint(endpoint);
        return endpointProperty;
    }

    @BeforeEach
    public void initTest() {
        endpointProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndpointProperty() throws Exception {
        int databaseSizeBeforeCreate = endpointPropertyRepository.findAll().size();

        // Create the EndpointProperty
        restEndpointPropertyMockMvc.perform(post("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isCreated());

        // Validate the EndpointProperty in the database
        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeCreate + 1);
        EndpointProperty testEndpointProperty = endpointPropertyList.get(endpointPropertyList.size() - 1);
        assertThat(testEndpointProperty.getPropKey()).isEqualTo(DEFAULT_PROP_KEY);
        assertThat(testEndpointProperty.getPropKeyType()).isEqualTo(DEFAULT_PROP_KEY_TYPE);
        assertThat(testEndpointProperty.getPropValue()).isEqualTo(DEFAULT_PROP_VALUE);
        assertThat(testEndpointProperty.getPropValueType()).isEqualTo(DEFAULT_PROP_VALUE_TYPE);
        assertThat(testEndpointProperty.getPropDescription()).isEqualTo(DEFAULT_PROP_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEndpointPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endpointPropertyRepository.findAll().size();

        // Create the EndpointProperty with an existing ID
        endpointProperty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndpointPropertyMockMvc.perform(post("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isBadRequest());

        // Validate the EndpointProperty in the database
        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPropKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointPropertyRepository.findAll().size();
        // set the field null
        endpointProperty.setPropKey(null);

        // Create the EndpointProperty, which fails.

        restEndpointPropertyMockMvc.perform(post("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isBadRequest());

        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropKeyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointPropertyRepository.findAll().size();
        // set the field null
        endpointProperty.setPropKeyType(null);

        // Create the EndpointProperty, which fails.

        restEndpointPropertyMockMvc.perform(post("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isBadRequest());

        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointPropertyRepository.findAll().size();
        // set the field null
        endpointProperty.setPropValue(null);

        // Create the EndpointProperty, which fails.

        restEndpointPropertyMockMvc.perform(post("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isBadRequest());

        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointPropertyRepository.findAll().size();
        // set the field null
        endpointProperty.setPropValueType(null);

        // Create the EndpointProperty, which fails.

        restEndpointPropertyMockMvc.perform(post("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isBadRequest());

        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEndpointProperties() throws Exception {
        // Initialize the database
        endpointPropertyRepository.saveAndFlush(endpointProperty);

        // Get all the endpointPropertyList
        restEndpointPropertyMockMvc.perform(get("/api/endpoint-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endpointProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].propKey").value(hasItem(DEFAULT_PROP_KEY)))
            .andExpect(jsonPath("$.[*].propKeyType").value(hasItem(DEFAULT_PROP_KEY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].propValue").value(hasItem(DEFAULT_PROP_VALUE)))
            .andExpect(jsonPath("$.[*].propValueType").value(hasItem(DEFAULT_PROP_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].propDescription").value(hasItem(DEFAULT_PROP_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEndpointProperty() throws Exception {
        // Initialize the database
        endpointPropertyRepository.saveAndFlush(endpointProperty);

        // Get the endpointProperty
        restEndpointPropertyMockMvc.perform(get("/api/endpoint-properties/{id}", endpointProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endpointProperty.getId().intValue()))
            .andExpect(jsonPath("$.propKey").value(DEFAULT_PROP_KEY))
            .andExpect(jsonPath("$.propKeyType").value(DEFAULT_PROP_KEY_TYPE.toString()))
            .andExpect(jsonPath("$.propValue").value(DEFAULT_PROP_VALUE))
            .andExpect(jsonPath("$.propValueType").value(DEFAULT_PROP_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.propDescription").value(DEFAULT_PROP_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingEndpointProperty() throws Exception {
        // Get the endpointProperty
        restEndpointPropertyMockMvc.perform(get("/api/endpoint-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndpointProperty() throws Exception {
        // Initialize the database
        endpointPropertyService.save(endpointProperty);

        int databaseSizeBeforeUpdate = endpointPropertyRepository.findAll().size();

        // Update the endpointProperty
        EndpointProperty updatedEndpointProperty = endpointPropertyRepository.findById(endpointProperty.getId()).get();
        // Disconnect from session so that the updates on updatedEndpointProperty are not directly saved in db
        em.detach(updatedEndpointProperty);
        updatedEndpointProperty
            .propKey(UPDATED_PROP_KEY)
            .propKeyType(UPDATED_PROP_KEY_TYPE)
            .propValue(UPDATED_PROP_VALUE)
            .propValueType(UPDATED_PROP_VALUE_TYPE)
            .propDescription(UPDATED_PROP_DESCRIPTION);

        restEndpointPropertyMockMvc.perform(put("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEndpointProperty)))
            .andExpect(status().isOk());

        // Validate the EndpointProperty in the database
        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeUpdate);
        EndpointProperty testEndpointProperty = endpointPropertyList.get(endpointPropertyList.size() - 1);
        assertThat(testEndpointProperty.getPropKey()).isEqualTo(UPDATED_PROP_KEY);
        assertThat(testEndpointProperty.getPropKeyType()).isEqualTo(UPDATED_PROP_KEY_TYPE);
        assertThat(testEndpointProperty.getPropValue()).isEqualTo(UPDATED_PROP_VALUE);
        assertThat(testEndpointProperty.getPropValueType()).isEqualTo(UPDATED_PROP_VALUE_TYPE);
        assertThat(testEndpointProperty.getPropDescription()).isEqualTo(UPDATED_PROP_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEndpointProperty() throws Exception {
        int databaseSizeBeforeUpdate = endpointPropertyRepository.findAll().size();

        // Create the EndpointProperty

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndpointPropertyMockMvc.perform(put("/api/endpoint-properties")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpointProperty)))
            .andExpect(status().isBadRequest());

        // Validate the EndpointProperty in the database
        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndpointProperty() throws Exception {
        // Initialize the database
        endpointPropertyService.save(endpointProperty);

        int databaseSizeBeforeDelete = endpointPropertyRepository.findAll().size();

        // Delete the endpointProperty
        restEndpointPropertyMockMvc.perform(delete("/api/endpoint-properties/{id}", endpointProperty.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EndpointProperty> endpointPropertyList = endpointPropertyRepository.findAll();
        assertThat(endpointPropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
