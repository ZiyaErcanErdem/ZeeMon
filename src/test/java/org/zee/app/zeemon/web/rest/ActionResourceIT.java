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
import org.zee.app.zeemon.domain.Action;
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.enumeration.ActionState;
import org.zee.app.zeemon.repository.ActionRepository;
import org.zee.app.zeemon.service.ActionService;
import org.zee.app.zeemon.web.rest.ActionResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ActionResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ActionResourceIT {

    private static final String DEFAULT_ACTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_NEXT_EXECUTION_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_EXECUTION_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ActionState DEFAULT_ACTION_STATE = ActionState.PENDING;
    private static final ActionState UPDATED_ACTION_STATE = ActionState.PAUSED;

    private static final String DEFAULT_RESOLUTION_RULE_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION_RULE_EXPRESSION = "BBBBBBBBBB";

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionService actionService;

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

    private MockMvc restActionMockMvc;

    private Action action;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionResource actionResource = new ActionResource(actionService);
        this.restActionMockMvc = MockMvcBuilders.standaloneSetup(actionResource)
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
    public static Action createEntity(EntityManager em) {
        Action action = new Action()
            .actionName(DEFAULT_ACTION_NAME)
            .actionDescription(DEFAULT_ACTION_DESCRIPTION)
            .nextExecutionStartTime(DEFAULT_NEXT_EXECUTION_START_TIME)
            .actionState(DEFAULT_ACTION_STATE)
            .resolutionRuleExpression(DEFAULT_RESOLUTION_RULE_EXPRESSION);
        // Add required entity
        Agent agent;
        if (TestUtil.findAll(em, Agent.class).isEmpty()) {
            agent = AgentResourceIT.createEntity(em);
            em.persist(agent);
            em.flush();
        } else {
            agent = TestUtil.findAll(em, Agent.class).get(0);
        }
        action.setAgent(agent);
        // Add required entity
        ActionScript actionScript;
        if (TestUtil.findAll(em, ActionScript.class).isEmpty()) {
            actionScript = ActionScriptResourceIT.createEntity(em);
            em.persist(actionScript);
            em.flush();
        } else {
            actionScript = TestUtil.findAll(em, ActionScript.class).get(0);
        }
        action.setActionScript(actionScript);
        return action;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Action createUpdatedEntity(EntityManager em) {
        Action action = new Action()
            .actionName(UPDATED_ACTION_NAME)
            .actionDescription(UPDATED_ACTION_DESCRIPTION)
            .nextExecutionStartTime(UPDATED_NEXT_EXECUTION_START_TIME)
            .actionState(UPDATED_ACTION_STATE)
            .resolutionRuleExpression(UPDATED_RESOLUTION_RULE_EXPRESSION);
        // Add required entity
        Agent agent;
        if (TestUtil.findAll(em, Agent.class).isEmpty()) {
            agent = AgentResourceIT.createUpdatedEntity(em);
            em.persist(agent);
            em.flush();
        } else {
            agent = TestUtil.findAll(em, Agent.class).get(0);
        }
        action.setAgent(agent);
        // Add required entity
        ActionScript actionScript;
        if (TestUtil.findAll(em, ActionScript.class).isEmpty()) {
            actionScript = ActionScriptResourceIT.createUpdatedEntity(em);
            em.persist(actionScript);
            em.flush();
        } else {
            actionScript = TestUtil.findAll(em, ActionScript.class).get(0);
        }
        action.setActionScript(actionScript);
        return action;
    }

    @BeforeEach
    public void initTest() {
        action = createEntity(em);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action
        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getActionName()).isEqualTo(DEFAULT_ACTION_NAME);
        assertThat(testAction.getActionDescription()).isEqualTo(DEFAULT_ACTION_DESCRIPTION);
        assertThat(testAction.getNextExecutionStartTime()).isEqualTo(DEFAULT_NEXT_EXECUTION_START_TIME);
        assertThat(testAction.getActionState()).isEqualTo(DEFAULT_ACTION_STATE);
        assertThat(testAction.getResolutionRuleExpression()).isEqualTo(DEFAULT_RESOLUTION_RULE_EXPRESSION);
    }

    @Test
    @Transactional
    public void createActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action with an existing ID
        action.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setActionName(null);

        // Create the Action, which fails.

        restActionMockMvc.perform(post("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actionList
        restActionMockMvc.perform(get("/api/actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionName").value(hasItem(DEFAULT_ACTION_NAME)))
            .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nextExecutionStartTime").value(hasItem(DEFAULT_NEXT_EXECUTION_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].actionState").value(hasItem(DEFAULT_ACTION_STATE.toString())))
            .andExpect(jsonPath("$.[*].resolutionRuleExpression").value(hasItem(DEFAULT_RESOLUTION_RULE_EXPRESSION)));
    }
    
    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.actionName").value(DEFAULT_ACTION_NAME))
            .andExpect(jsonPath("$.actionDescription").value(DEFAULT_ACTION_DESCRIPTION))
            .andExpect(jsonPath("$.nextExecutionStartTime").value(DEFAULT_NEXT_EXECUTION_START_TIME.toString()))
            .andExpect(jsonPath("$.actionState").value(DEFAULT_ACTION_STATE.toString()))
            .andExpect(jsonPath("$.resolutionRuleExpression").value(DEFAULT_RESOLUTION_RULE_EXPRESSION));
    }

    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionService.save(action);

        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        Action updatedAction = actionRepository.findById(action.getId()).get();
        // Disconnect from session so that the updates on updatedAction are not directly saved in db
        em.detach(updatedAction);
        updatedAction
            .actionName(UPDATED_ACTION_NAME)
            .actionDescription(UPDATED_ACTION_DESCRIPTION)
            .nextExecutionStartTime(UPDATED_NEXT_EXECUTION_START_TIME)
            .actionState(UPDATED_ACTION_STATE)
            .resolutionRuleExpression(UPDATED_RESOLUTION_RULE_EXPRESSION);

        restActionMockMvc.perform(put("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAction)))
            .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actionList.get(actionList.size() - 1);
        assertThat(testAction.getActionName()).isEqualTo(UPDATED_ACTION_NAME);
        assertThat(testAction.getActionDescription()).isEqualTo(UPDATED_ACTION_DESCRIPTION);
        assertThat(testAction.getNextExecutionStartTime()).isEqualTo(UPDATED_NEXT_EXECUTION_START_TIME);
        assertThat(testAction.getActionState()).isEqualTo(UPDATED_ACTION_STATE);
        assertThat(testAction.getResolutionRuleExpression()).isEqualTo(UPDATED_RESOLUTION_RULE_EXPRESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingAction() throws Exception {
        int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Create the Action

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionMockMvc.perform(put("/api/actions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(action)))
            .andExpect(status().isBadRequest());

        // Validate the Action in the database
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionService.save(action);

        int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Delete the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Action> actionList = actionRepository.findAll();
        assertThat(actionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
