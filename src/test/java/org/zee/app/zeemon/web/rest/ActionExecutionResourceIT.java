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
import org.zee.app.zeemon.domain.ActionExecution;
import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;
import org.zee.app.zeemon.repository.ActionExecutionRepository;
import org.zee.app.zeemon.service.ActionExecutionService;
import org.zee.app.zeemon.web.rest.ActionExecutionResource;
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
 * Integration tests for the {@link ActionExecutionResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ActionExecutionResourceIT {

    private static final Instant DEFAULT_EXECUTION_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXECUTION_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ExecutionStatus DEFAULT_EXECUTION_STATUS = ExecutionStatus.PENDING;
    private static final ExecutionStatus UPDATED_EXECUTION_STATUS = ExecutionStatus.EXECUTING;

    private static final String DEFAULT_ACTION_LOG = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_LOG = "BBBBBBBBBB";

    @Autowired
    private ActionExecutionRepository actionExecutionRepository;

    @Autowired
    private ActionExecutionService actionExecutionService;

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

    private MockMvc restActionExecutionMockMvc;

    private ActionExecution actionExecution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionExecutionResource actionExecutionResource = new ActionExecutionResource(actionExecutionService);
        this.restActionExecutionMockMvc = MockMvcBuilders.standaloneSetup(actionExecutionResource)
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
    public static ActionExecution createEntity(EntityManager em) {
        ActionExecution actionExecution = new ActionExecution()
            .executionStartTime(DEFAULT_EXECUTION_START_TIME)
            .executionEndTime(DEFAULT_EXECUTION_END_TIME)
            .executionStatus(DEFAULT_EXECUTION_STATUS)
            .actionLog(DEFAULT_ACTION_LOG);
        // Add required entity
        Action action;
        if (TestUtil.findAll(em, Action.class).isEmpty()) {
            action = ActionResourceIT.createEntity(em);
            em.persist(action);
            em.flush();
        } else {
            action = TestUtil.findAll(em, Action.class).get(0);
        }
        actionExecution.setAction(action);
        return actionExecution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionExecution createUpdatedEntity(EntityManager em) {
        ActionExecution actionExecution = new ActionExecution()
            .executionStartTime(UPDATED_EXECUTION_START_TIME)
            .executionEndTime(UPDATED_EXECUTION_END_TIME)
            .executionStatus(UPDATED_EXECUTION_STATUS)
            .actionLog(UPDATED_ACTION_LOG);
        // Add required entity
        Action action;
        if (TestUtil.findAll(em, Action.class).isEmpty()) {
            action = ActionResourceIT.createUpdatedEntity(em);
            em.persist(action);
            em.flush();
        } else {
            action = TestUtil.findAll(em, Action.class).get(0);
        }
        actionExecution.setAction(action);
        return actionExecution;
    }

    @BeforeEach
    public void initTest() {
        actionExecution = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionExecution() throws Exception {
        int databaseSizeBeforeCreate = actionExecutionRepository.findAll().size();

        // Create the ActionExecution
        restActionExecutionMockMvc.perform(post("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionExecution)))
            .andExpect(status().isCreated());

        // Validate the ActionExecution in the database
        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeCreate + 1);
        ActionExecution testActionExecution = actionExecutionList.get(actionExecutionList.size() - 1);
        assertThat(testActionExecution.getExecutionStartTime()).isEqualTo(DEFAULT_EXECUTION_START_TIME);
        assertThat(testActionExecution.getExecutionEndTime()).isEqualTo(DEFAULT_EXECUTION_END_TIME);
        assertThat(testActionExecution.getExecutionStatus()).isEqualTo(DEFAULT_EXECUTION_STATUS);
        assertThat(testActionExecution.getActionLog()).isEqualTo(DEFAULT_ACTION_LOG);
    }

    @Test
    @Transactional
    public void createActionExecutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionExecutionRepository.findAll().size();

        // Create the ActionExecution with an existing ID
        actionExecution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionExecutionMockMvc.perform(post("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionExecution)))
            .andExpect(status().isBadRequest());

        // Validate the ActionExecution in the database
        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExecutionStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionExecutionRepository.findAll().size();
        // set the field null
        actionExecution.setExecutionStartTime(null);

        // Create the ActionExecution, which fails.

        restActionExecutionMockMvc.perform(post("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionExecution)))
            .andExpect(status().isBadRequest());

        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionExecutionRepository.findAll().size();
        // set the field null
        actionExecution.setExecutionEndTime(null);

        // Create the ActionExecution, which fails.

        restActionExecutionMockMvc.perform(post("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionExecution)))
            .andExpect(status().isBadRequest());

        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionExecutionRepository.findAll().size();
        // set the field null
        actionExecution.setExecutionStatus(null);

        // Create the ActionExecution, which fails.

        restActionExecutionMockMvc.perform(post("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionExecution)))
            .andExpect(status().isBadRequest());

        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActionExecutions() throws Exception {
        // Initialize the database
        actionExecutionRepository.saveAndFlush(actionExecution);

        // Get all the actionExecutionList
        restActionExecutionMockMvc.perform(get("/api/action-executions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionExecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].executionStartTime").value(hasItem(DEFAULT_EXECUTION_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].executionEndTime").value(hasItem(DEFAULT_EXECUTION_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].executionStatus").value(hasItem(DEFAULT_EXECUTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].actionLog").value(hasItem(DEFAULT_ACTION_LOG)));
    }
    
    @Test
    @Transactional
    public void getActionExecution() throws Exception {
        // Initialize the database
        actionExecutionRepository.saveAndFlush(actionExecution);

        // Get the actionExecution
        restActionExecutionMockMvc.perform(get("/api/action-executions/{id}", actionExecution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actionExecution.getId().intValue()))
            .andExpect(jsonPath("$.executionStartTime").value(DEFAULT_EXECUTION_START_TIME.toString()))
            .andExpect(jsonPath("$.executionEndTime").value(DEFAULT_EXECUTION_END_TIME.toString()))
            .andExpect(jsonPath("$.executionStatus").value(DEFAULT_EXECUTION_STATUS.toString()))
            .andExpect(jsonPath("$.actionLog").value(DEFAULT_ACTION_LOG));
    }

    @Test
    @Transactional
    public void getNonExistingActionExecution() throws Exception {
        // Get the actionExecution
        restActionExecutionMockMvc.perform(get("/api/action-executions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionExecution() throws Exception {
        // Initialize the database
        actionExecutionService.save(actionExecution);

        int databaseSizeBeforeUpdate = actionExecutionRepository.findAll().size();

        // Update the actionExecution
        ActionExecution updatedActionExecution = actionExecutionRepository.findById(actionExecution.getId()).get();
        // Disconnect from session so that the updates on updatedActionExecution are not directly saved in db
        em.detach(updatedActionExecution);
        updatedActionExecution
            .executionStartTime(UPDATED_EXECUTION_START_TIME)
            .executionEndTime(UPDATED_EXECUTION_END_TIME)
            .executionStatus(UPDATED_EXECUTION_STATUS)
            .actionLog(UPDATED_ACTION_LOG);

        restActionExecutionMockMvc.perform(put("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActionExecution)))
            .andExpect(status().isOk());

        // Validate the ActionExecution in the database
        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeUpdate);
        ActionExecution testActionExecution = actionExecutionList.get(actionExecutionList.size() - 1);
        assertThat(testActionExecution.getExecutionStartTime()).isEqualTo(UPDATED_EXECUTION_START_TIME);
        assertThat(testActionExecution.getExecutionEndTime()).isEqualTo(UPDATED_EXECUTION_END_TIME);
        assertThat(testActionExecution.getExecutionStatus()).isEqualTo(UPDATED_EXECUTION_STATUS);
        assertThat(testActionExecution.getActionLog()).isEqualTo(UPDATED_ACTION_LOG);
    }

    @Test
    @Transactional
    public void updateNonExistingActionExecution() throws Exception {
        int databaseSizeBeforeUpdate = actionExecutionRepository.findAll().size();

        // Create the ActionExecution

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionExecutionMockMvc.perform(put("/api/action-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionExecution)))
            .andExpect(status().isBadRequest());

        // Validate the ActionExecution in the database
        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionExecution() throws Exception {
        // Initialize the database
        actionExecutionService.save(actionExecution);

        int databaseSizeBeforeDelete = actionExecutionRepository.findAll().size();

        // Delete the actionExecution
        restActionExecutionMockMvc.perform(delete("/api/action-executions/{id}", actionExecution.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActionExecution> actionExecutionList = actionExecutionRepository.findAll();
        assertThat(actionExecutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
