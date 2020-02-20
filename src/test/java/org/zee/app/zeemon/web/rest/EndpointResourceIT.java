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
import org.zee.app.zeemon.domain.enumeration.EndpointSpec;
import org.zee.app.zeemon.domain.enumeration.EndpointType;
import org.zee.app.zeemon.repository.EndpointRepository;
import org.zee.app.zeemon.service.EndpointService;
import org.zee.app.zeemon.web.rest.EndpointResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link EndpointResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class EndpointResourceIT {

    private static final String DEFAULT_ENDPOINT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENDPOINT_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT_INSTANCE_ID = "BBBBBBBBBB";

    private static final EndpointType DEFAULT_ENDPOINT_TYPE = EndpointType.DATABASE;
    private static final EndpointType UPDATED_ENDPOINT_TYPE = EndpointType.FILE_SYSTEM;

    private static final EndpointSpec DEFAULT_ENDPOINT_SPEC = EndpointSpec.ANY;
    private static final EndpointSpec UPDATED_ENDPOINT_SPEC = EndpointSpec.MSSQL;

    private static final String DEFAULT_ENDPOINT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ENDPOINT_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private EndpointService endpointService;

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

    private MockMvc restEndpointMockMvc;

    private Endpoint endpoint;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EndpointResource endpointResource = new EndpointResource(endpointService);
        this.restEndpointMockMvc = MockMvcBuilders.standaloneSetup(endpointResource)
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
    public static Endpoint createEntity(EntityManager em) {
        Endpoint endpoint = new Endpoint()
            .endpointName(DEFAULT_ENDPOINT_NAME)
            .endpointInstanceId(DEFAULT_ENDPOINT_INSTANCE_ID)
            .endpointType(DEFAULT_ENDPOINT_TYPE)
            .endpointSpec(DEFAULT_ENDPOINT_SPEC)
            .endpointDescription(DEFAULT_ENDPOINT_DESCRIPTION);
        return endpoint;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endpoint createUpdatedEntity(EntityManager em) {
        Endpoint endpoint = new Endpoint()
            .endpointName(UPDATED_ENDPOINT_NAME)
            .endpointInstanceId(UPDATED_ENDPOINT_INSTANCE_ID)
            .endpointType(UPDATED_ENDPOINT_TYPE)
            .endpointSpec(UPDATED_ENDPOINT_SPEC)
            .endpointDescription(UPDATED_ENDPOINT_DESCRIPTION);
        return endpoint;
    }

    @BeforeEach
    public void initTest() {
        endpoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndpoint() throws Exception {
        int databaseSizeBeforeCreate = endpointRepository.findAll().size();

        // Create the Endpoint
        restEndpointMockMvc.perform(post("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpoint)))
            .andExpect(status().isCreated());

        // Validate the Endpoint in the database
        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeCreate + 1);
        Endpoint testEndpoint = endpointList.get(endpointList.size() - 1);
        assertThat(testEndpoint.getEndpointName()).isEqualTo(DEFAULT_ENDPOINT_NAME);
        assertThat(testEndpoint.getEndpointInstanceId()).isEqualTo(DEFAULT_ENDPOINT_INSTANCE_ID);
        assertThat(testEndpoint.getEndpointType()).isEqualTo(DEFAULT_ENDPOINT_TYPE);
        assertThat(testEndpoint.getEndpointSpec()).isEqualTo(DEFAULT_ENDPOINT_SPEC);
        assertThat(testEndpoint.getEndpointDescription()).isEqualTo(DEFAULT_ENDPOINT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEndpointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = endpointRepository.findAll().size();

        // Create the Endpoint with an existing ID
        endpoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEndpointMockMvc.perform(post("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpoint)))
            .andExpect(status().isBadRequest());

        // Validate the Endpoint in the database
        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEndpointNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointRepository.findAll().size();
        // set the field null
        endpoint.setEndpointName(null);

        // Create the Endpoint, which fails.

        restEndpointMockMvc.perform(post("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpoint)))
            .andExpect(status().isBadRequest());

        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndpointTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointRepository.findAll().size();
        // set the field null
        endpoint.setEndpointType(null);

        // Create the Endpoint, which fails.

        restEndpointMockMvc.perform(post("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpoint)))
            .andExpect(status().isBadRequest());

        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndpointSpecIsRequired() throws Exception {
        int databaseSizeBeforeTest = endpointRepository.findAll().size();
        // set the field null
        endpoint.setEndpointSpec(null);

        // Create the Endpoint, which fails.

        restEndpointMockMvc.perform(post("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpoint)))
            .andExpect(status().isBadRequest());

        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEndpoints() throws Exception {
        // Initialize the database
        endpointRepository.saveAndFlush(endpoint);

        // Get all the endpointList
        restEndpointMockMvc.perform(get("/api/endpoints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endpoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].endpointName").value(hasItem(DEFAULT_ENDPOINT_NAME)))
            .andExpect(jsonPath("$.[*].endpointInstanceId").value(hasItem(DEFAULT_ENDPOINT_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].endpointType").value(hasItem(DEFAULT_ENDPOINT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].endpointSpec").value(hasItem(DEFAULT_ENDPOINT_SPEC.toString())))
            .andExpect(jsonPath("$.[*].endpointDescription").value(hasItem(DEFAULT_ENDPOINT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEndpoint() throws Exception {
        // Initialize the database
        endpointRepository.saveAndFlush(endpoint);

        // Get the endpoint
        restEndpointMockMvc.perform(get("/api/endpoints/{id}", endpoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endpoint.getId().intValue()))
            .andExpect(jsonPath("$.endpointName").value(DEFAULT_ENDPOINT_NAME))
            .andExpect(jsonPath("$.endpointInstanceId").value(DEFAULT_ENDPOINT_INSTANCE_ID))
            .andExpect(jsonPath("$.endpointType").value(DEFAULT_ENDPOINT_TYPE.toString()))
            .andExpect(jsonPath("$.endpointSpec").value(DEFAULT_ENDPOINT_SPEC.toString()))
            .andExpect(jsonPath("$.endpointDescription").value(DEFAULT_ENDPOINT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingEndpoint() throws Exception {
        // Get the endpoint
        restEndpointMockMvc.perform(get("/api/endpoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndpoint() throws Exception {
        // Initialize the database
        endpointService.save(endpoint);

        int databaseSizeBeforeUpdate = endpointRepository.findAll().size();

        // Update the endpoint
        Endpoint updatedEndpoint = endpointRepository.findById(endpoint.getId()).get();
        // Disconnect from session so that the updates on updatedEndpoint are not directly saved in db
        em.detach(updatedEndpoint);
        updatedEndpoint
            .endpointName(UPDATED_ENDPOINT_NAME)
            .endpointInstanceId(UPDATED_ENDPOINT_INSTANCE_ID)
            .endpointType(UPDATED_ENDPOINT_TYPE)
            .endpointSpec(UPDATED_ENDPOINT_SPEC)
            .endpointDescription(UPDATED_ENDPOINT_DESCRIPTION);

        restEndpointMockMvc.perform(put("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEndpoint)))
            .andExpect(status().isOk());

        // Validate the Endpoint in the database
        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeUpdate);
        Endpoint testEndpoint = endpointList.get(endpointList.size() - 1);
        assertThat(testEndpoint.getEndpointName()).isEqualTo(UPDATED_ENDPOINT_NAME);
        assertThat(testEndpoint.getEndpointInstanceId()).isEqualTo(UPDATED_ENDPOINT_INSTANCE_ID);
        assertThat(testEndpoint.getEndpointType()).isEqualTo(UPDATED_ENDPOINT_TYPE);
        assertThat(testEndpoint.getEndpointSpec()).isEqualTo(UPDATED_ENDPOINT_SPEC);
        assertThat(testEndpoint.getEndpointDescription()).isEqualTo(UPDATED_ENDPOINT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEndpoint() throws Exception {
        int databaseSizeBeforeUpdate = endpointRepository.findAll().size();

        // Create the Endpoint

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEndpointMockMvc.perform(put("/api/endpoints")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endpoint)))
            .andExpect(status().isBadRequest());

        // Validate the Endpoint in the database
        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndpoint() throws Exception {
        // Initialize the database
        endpointService.save(endpoint);

        int databaseSizeBeforeDelete = endpointRepository.findAll().size();

        // Delete the endpoint
        restEndpointMockMvc.perform(delete("/api/endpoints/{id}", endpoint.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Endpoint> endpointList = endpointRepository.findAll();
        assertThat(endpointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
