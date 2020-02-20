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
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.enumeration.ScriptType;
import org.zee.app.zeemon.repository.CheckScriptRepository;
import org.zee.app.zeemon.service.CheckScriptService;
import org.zee.app.zeemon.web.rest.CheckScriptResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link CheckScriptResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class CheckScriptResourceIT {

    private static final String DEFAULT_SCRIPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT_NAME = "BBBBBBBBBB";

    private static final ScriptType DEFAULT_SCRIPT_TYPE = ScriptType.SQL_SCRIPT;
    private static final ScriptType UPDATED_SCRIPT_TYPE = ScriptType.SHELL_SCRIPT;

    private static final String DEFAULT_SCRIPT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_RULE_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_RULE_EXPRESSION = "BBBBBBBBBB";

    @Autowired
    private CheckScriptRepository checkScriptRepository;

    @Autowired
    private CheckScriptService checkScriptService;

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

    private MockMvc restCheckScriptMockMvc;

    private CheckScript checkScript;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckScriptResource checkScriptResource = new CheckScriptResource(checkScriptService);
        this.restCheckScriptMockMvc = MockMvcBuilders.standaloneSetup(checkScriptResource)
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
    public static CheckScript createEntity(EntityManager em) {
        CheckScript checkScript = new CheckScript()
            .scriptName(DEFAULT_SCRIPT_NAME)
            .scriptType(DEFAULT_SCRIPT_TYPE)
            .scriptSource(DEFAULT_SCRIPT_SOURCE)
            .actionRuleExpression(DEFAULT_ACTION_RULE_EXPRESSION);
        // Add required entity
        Endpoint endpoint;
        if (TestUtil.findAll(em, Endpoint.class).isEmpty()) {
            endpoint = EndpointResourceIT.createEntity(em);
            em.persist(endpoint);
            em.flush();
        } else {
            endpoint = TestUtil.findAll(em, Endpoint.class).get(0);
        }
        checkScript.setEndpoint(endpoint);
        // Add required entity
        ContentMapper contentMapper;
        if (TestUtil.findAll(em, ContentMapper.class).isEmpty()) {
            contentMapper = ContentMapperResourceIT.createEntity(em);
            em.persist(contentMapper);
            em.flush();
        } else {
            contentMapper = TestUtil.findAll(em, ContentMapper.class).get(0);
        }
        checkScript.setContentMapper(contentMapper);
        return checkScript;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckScript createUpdatedEntity(EntityManager em) {
        CheckScript checkScript = new CheckScript()
            .scriptName(UPDATED_SCRIPT_NAME)
            .scriptType(UPDATED_SCRIPT_TYPE)
            .scriptSource(UPDATED_SCRIPT_SOURCE)
            .actionRuleExpression(UPDATED_ACTION_RULE_EXPRESSION);
        // Add required entity
        Endpoint endpoint;
        if (TestUtil.findAll(em, Endpoint.class).isEmpty()) {
            endpoint = EndpointResourceIT.createUpdatedEntity(em);
            em.persist(endpoint);
            em.flush();
        } else {
            endpoint = TestUtil.findAll(em, Endpoint.class).get(0);
        }
        checkScript.setEndpoint(endpoint);
        // Add required entity
        ContentMapper contentMapper;
        if (TestUtil.findAll(em, ContentMapper.class).isEmpty()) {
            contentMapper = ContentMapperResourceIT.createUpdatedEntity(em);
            em.persist(contentMapper);
            em.flush();
        } else {
            contentMapper = TestUtil.findAll(em, ContentMapper.class).get(0);
        }
        checkScript.setContentMapper(contentMapper);
        return checkScript;
    }

    @BeforeEach
    public void initTest() {
        checkScript = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckScript() throws Exception {
        int databaseSizeBeforeCreate = checkScriptRepository.findAll().size();

        // Create the CheckScript
        restCheckScriptMockMvc.perform(post("/api/check-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkScript)))
            .andExpect(status().isCreated());

        // Validate the CheckScript in the database
        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeCreate + 1);
        CheckScript testCheckScript = checkScriptList.get(checkScriptList.size() - 1);
        assertThat(testCheckScript.getScriptName()).isEqualTo(DEFAULT_SCRIPT_NAME);
        assertThat(testCheckScript.getScriptType()).isEqualTo(DEFAULT_SCRIPT_TYPE);
        assertThat(testCheckScript.getScriptSource()).isEqualTo(DEFAULT_SCRIPT_SOURCE);
        assertThat(testCheckScript.getActionRuleExpression()).isEqualTo(DEFAULT_ACTION_RULE_EXPRESSION);
    }

    @Test
    @Transactional
    public void createCheckScriptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkScriptRepository.findAll().size();

        // Create the CheckScript with an existing ID
        checkScript.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckScriptMockMvc.perform(post("/api/check-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkScript)))
            .andExpect(status().isBadRequest());

        // Validate the CheckScript in the database
        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkScriptNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkScriptRepository.findAll().size();
        // set the field null
        checkScript.setScriptName(null);

        // Create the CheckScript, which fails.

        restCheckScriptMockMvc.perform(post("/api/check-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkScript)))
            .andExpect(status().isBadRequest());

        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScriptTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkScriptRepository.findAll().size();
        // set the field null
        checkScript.setScriptType(null);

        // Create the CheckScript, which fails.

        restCheckScriptMockMvc.perform(post("/api/check-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkScript)))
            .andExpect(status().isBadRequest());

        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCheckScripts() throws Exception {
        // Initialize the database
        checkScriptRepository.saveAndFlush(checkScript);

        // Get all the checkScriptList
        restCheckScriptMockMvc.perform(get("/api/check-scripts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkScript.getId().intValue())))
            .andExpect(jsonPath("$.[*].scriptName").value(hasItem(DEFAULT_SCRIPT_NAME)))
            .andExpect(jsonPath("$.[*].scriptType").value(hasItem(DEFAULT_SCRIPT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].scriptSource").value(hasItem(DEFAULT_SCRIPT_SOURCE)))
            .andExpect(jsonPath("$.[*].actionRuleExpression").value(hasItem(DEFAULT_ACTION_RULE_EXPRESSION)));
    }
    
    @Test
    @Transactional
    public void getCheckScript() throws Exception {
        // Initialize the database
        checkScriptRepository.saveAndFlush(checkScript);

        // Get the checkScript
        restCheckScriptMockMvc.perform(get("/api/check-scripts/{id}", checkScript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkScript.getId().intValue()))
            .andExpect(jsonPath("$.scriptName").value(DEFAULT_SCRIPT_NAME))
            .andExpect(jsonPath("$.scriptType").value(DEFAULT_SCRIPT_TYPE.toString()))
            .andExpect(jsonPath("$.scriptSource").value(DEFAULT_SCRIPT_SOURCE))
            .andExpect(jsonPath("$.actionRuleExpression").value(DEFAULT_ACTION_RULE_EXPRESSION));
    }

    @Test
    @Transactional
    public void getNonExistingCheckScript() throws Exception {
        // Get the checkScript
        restCheckScriptMockMvc.perform(get("/api/check-scripts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckScript() throws Exception {
        // Initialize the database
        checkScriptService.save(checkScript);

        int databaseSizeBeforeUpdate = checkScriptRepository.findAll().size();

        // Update the checkScript
        CheckScript updatedCheckScript = checkScriptRepository.findById(checkScript.getId()).get();
        // Disconnect from session so that the updates on updatedCheckScript are not directly saved in db
        em.detach(updatedCheckScript);
        updatedCheckScript
            .scriptName(UPDATED_SCRIPT_NAME)
            .scriptType(UPDATED_SCRIPT_TYPE)
            .scriptSource(UPDATED_SCRIPT_SOURCE)
            .actionRuleExpression(UPDATED_ACTION_RULE_EXPRESSION);

        restCheckScriptMockMvc.perform(put("/api/check-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCheckScript)))
            .andExpect(status().isOk());

        // Validate the CheckScript in the database
        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeUpdate);
        CheckScript testCheckScript = checkScriptList.get(checkScriptList.size() - 1);
        assertThat(testCheckScript.getScriptName()).isEqualTo(UPDATED_SCRIPT_NAME);
        assertThat(testCheckScript.getScriptType()).isEqualTo(UPDATED_SCRIPT_TYPE);
        assertThat(testCheckScript.getScriptSource()).isEqualTo(UPDATED_SCRIPT_SOURCE);
        assertThat(testCheckScript.getActionRuleExpression()).isEqualTo(UPDATED_ACTION_RULE_EXPRESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckScript() throws Exception {
        int databaseSizeBeforeUpdate = checkScriptRepository.findAll().size();

        // Create the CheckScript

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckScriptMockMvc.perform(put("/api/check-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkScript)))
            .andExpect(status().isBadRequest());

        // Validate the CheckScript in the database
        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckScript() throws Exception {
        // Initialize the database
        checkScriptService.save(checkScript);

        int databaseSizeBeforeDelete = checkScriptRepository.findAll().size();

        // Delete the checkScript
        restCheckScriptMockMvc.perform(delete("/api/check-scripts/{id}", checkScript.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckScript> checkScriptList = checkScriptRepository.findAll();
        assertThat(checkScriptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
