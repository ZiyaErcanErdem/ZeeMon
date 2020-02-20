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
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.enumeration.ActionType;
import org.zee.app.zeemon.domain.enumeration.ScriptType;
import org.zee.app.zeemon.repository.ActionScriptRepository;
import org.zee.app.zeemon.service.ActionScriptService;
import org.zee.app.zeemon.web.rest.ActionScriptResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ActionScriptResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ActionScriptResourceIT {

    private static final String DEFAULT_ACTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SCRIPT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCRIPT_NAME = "BBBBBBBBBB";

    private static final ScriptType DEFAULT_SCRIPT_TYPE = ScriptType.SQL_SCRIPT;
    private static final ScriptType UPDATED_SCRIPT_TYPE = ScriptType.SHELL_SCRIPT;

    private static final ActionType DEFAULT_ACTION_TYPE = ActionType.EMAIL;
    private static final ActionType UPDATED_ACTION_TYPE = ActionType.SMS;

    private static final String DEFAULT_ACTION_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_SOURCE = "BBBBBBBBBB";

    @Autowired
    private ActionScriptRepository actionScriptRepository;

    @Autowired
    private ActionScriptService actionScriptService;

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

    private MockMvc restActionScriptMockMvc;

    private ActionScript actionScript;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionScriptResource actionScriptResource = new ActionScriptResource(actionScriptService);
        this.restActionScriptMockMvc = MockMvcBuilders.standaloneSetup(actionScriptResource)
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
    public static ActionScript createEntity(EntityManager em) {
        ActionScript actionScript = new ActionScript()
            .actionCode(DEFAULT_ACTION_CODE)
            .scriptName(DEFAULT_SCRIPT_NAME)
            .scriptType(DEFAULT_SCRIPT_TYPE)
            .actionType(DEFAULT_ACTION_TYPE)
            .actionSource(DEFAULT_ACTION_SOURCE);
        // Add required entity
        Endpoint endpoint;
        if (TestUtil.findAll(em, Endpoint.class).isEmpty()) {
            endpoint = EndpointResourceIT.createEntity(em);
            em.persist(endpoint);
            em.flush();
        } else {
            endpoint = TestUtil.findAll(em, Endpoint.class).get(0);
        }
        actionScript.setEndpoint(endpoint);
        return actionScript;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionScript createUpdatedEntity(EntityManager em) {
        ActionScript actionScript = new ActionScript()
            .actionCode(UPDATED_ACTION_CODE)
            .scriptName(UPDATED_SCRIPT_NAME)
            .scriptType(UPDATED_SCRIPT_TYPE)
            .actionType(UPDATED_ACTION_TYPE)
            .actionSource(UPDATED_ACTION_SOURCE);
        // Add required entity
        Endpoint endpoint;
        if (TestUtil.findAll(em, Endpoint.class).isEmpty()) {
            endpoint = EndpointResourceIT.createUpdatedEntity(em);
            em.persist(endpoint);
            em.flush();
        } else {
            endpoint = TestUtil.findAll(em, Endpoint.class).get(0);
        }
        actionScript.setEndpoint(endpoint);
        return actionScript;
    }

    @BeforeEach
    public void initTest() {
        actionScript = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionScript() throws Exception {
        int databaseSizeBeforeCreate = actionScriptRepository.findAll().size();

        // Create the ActionScript
        restActionScriptMockMvc.perform(post("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isCreated());

        // Validate the ActionScript in the database
        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeCreate + 1);
        ActionScript testActionScript = actionScriptList.get(actionScriptList.size() - 1);
        assertThat(testActionScript.getActionCode()).isEqualTo(DEFAULT_ACTION_CODE);
        assertThat(testActionScript.getScriptName()).isEqualTo(DEFAULT_SCRIPT_NAME);
        assertThat(testActionScript.getScriptType()).isEqualTo(DEFAULT_SCRIPT_TYPE);
        assertThat(testActionScript.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testActionScript.getActionSource()).isEqualTo(DEFAULT_ACTION_SOURCE);
    }

    @Test
    @Transactional
    public void createActionScriptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionScriptRepository.findAll().size();

        // Create the ActionScript with an existing ID
        actionScript.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionScriptMockMvc.perform(post("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isBadRequest());

        // Validate the ActionScript in the database
        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionScriptRepository.findAll().size();
        // set the field null
        actionScript.setActionCode(null);

        // Create the ActionScript, which fails.

        restActionScriptMockMvc.perform(post("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isBadRequest());

        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScriptNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionScriptRepository.findAll().size();
        // set the field null
        actionScript.setScriptName(null);

        // Create the ActionScript, which fails.

        restActionScriptMockMvc.perform(post("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isBadRequest());

        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScriptTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionScriptRepository.findAll().size();
        // set the field null
        actionScript.setScriptType(null);

        // Create the ActionScript, which fails.

        restActionScriptMockMvc.perform(post("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isBadRequest());

        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionScriptRepository.findAll().size();
        // set the field null
        actionScript.setActionType(null);

        // Create the ActionScript, which fails.

        restActionScriptMockMvc.perform(post("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isBadRequest());

        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActionScripts() throws Exception {
        // Initialize the database
        actionScriptRepository.saveAndFlush(actionScript);

        // Get all the actionScriptList
        restActionScriptMockMvc.perform(get("/api/action-scripts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionScript.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionCode").value(hasItem(DEFAULT_ACTION_CODE)))
            .andExpect(jsonPath("$.[*].scriptName").value(hasItem(DEFAULT_SCRIPT_NAME)))
            .andExpect(jsonPath("$.[*].scriptType").value(hasItem(DEFAULT_SCRIPT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionSource").value(hasItem(DEFAULT_ACTION_SOURCE)));
    }
    
    @Test
    @Transactional
    public void getActionScript() throws Exception {
        // Initialize the database
        actionScriptRepository.saveAndFlush(actionScript);

        // Get the actionScript
        restActionScriptMockMvc.perform(get("/api/action-scripts/{id}", actionScript.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actionScript.getId().intValue()))
            .andExpect(jsonPath("$.actionCode").value(DEFAULT_ACTION_CODE))
            .andExpect(jsonPath("$.scriptName").value(DEFAULT_SCRIPT_NAME))
            .andExpect(jsonPath("$.scriptType").value(DEFAULT_SCRIPT_TYPE.toString()))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.actionSource").value(DEFAULT_ACTION_SOURCE));
    }

    @Test
    @Transactional
    public void getNonExistingActionScript() throws Exception {
        // Get the actionScript
        restActionScriptMockMvc.perform(get("/api/action-scripts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionScript() throws Exception {
        // Initialize the database
        actionScriptService.save(actionScript);

        int databaseSizeBeforeUpdate = actionScriptRepository.findAll().size();

        // Update the actionScript
        ActionScript updatedActionScript = actionScriptRepository.findById(actionScript.getId()).get();
        // Disconnect from session so that the updates on updatedActionScript are not directly saved in db
        em.detach(updatedActionScript);
        updatedActionScript
            .actionCode(UPDATED_ACTION_CODE)
            .scriptName(UPDATED_SCRIPT_NAME)
            .scriptType(UPDATED_SCRIPT_TYPE)
            .actionType(UPDATED_ACTION_TYPE)
            .actionSource(UPDATED_ACTION_SOURCE);

        restActionScriptMockMvc.perform(put("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActionScript)))
            .andExpect(status().isOk());

        // Validate the ActionScript in the database
        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeUpdate);
        ActionScript testActionScript = actionScriptList.get(actionScriptList.size() - 1);
        assertThat(testActionScript.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
        assertThat(testActionScript.getScriptName()).isEqualTo(UPDATED_SCRIPT_NAME);
        assertThat(testActionScript.getScriptType()).isEqualTo(UPDATED_SCRIPT_TYPE);
        assertThat(testActionScript.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testActionScript.getActionSource()).isEqualTo(UPDATED_ACTION_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingActionScript() throws Exception {
        int databaseSizeBeforeUpdate = actionScriptRepository.findAll().size();

        // Create the ActionScript

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionScriptMockMvc.perform(put("/api/action-scripts")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionScript)))
            .andExpect(status().isBadRequest());

        // Validate the ActionScript in the database
        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionScript() throws Exception {
        // Initialize the database
        actionScriptService.save(actionScript);

        int databaseSizeBeforeDelete = actionScriptRepository.findAll().size();

        // Delete the actionScript
        restActionScriptMockMvc.perform(delete("/api/action-scripts/{id}", actionScript.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActionScript> actionScriptList = actionScriptRepository.findAll();
        assertThat(actionScriptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
