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
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;
import org.zee.app.zeemon.repository.FlowExecutionRepository;
import org.zee.app.zeemon.service.FlowExecutionService;
import org.zee.app.zeemon.web.rest.FlowExecutionResource;
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
 * Integration tests for the {@link FlowExecutionResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class FlowExecutionResourceIT {

    private static final Instant DEFAULT_EXECUTION_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXECUTION_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TOTAL_TASK_COUNT = 0;
    private static final Integer UPDATED_TOTAL_TASK_COUNT = 1;

    private static final Integer DEFAULT_RUNNING_TASK_COUNT = 0;
    private static final Integer UPDATED_RUNNING_TASK_COUNT = 1;

    private static final Integer DEFAULT_ERROR_TASK_COUNT = 0;
    private static final Integer UPDATED_ERROR_TASK_COUNT = 1;

    private static final ExecutionStatus DEFAULT_EXECUTION_STATUS = ExecutionStatus.PENDING;
    private static final ExecutionStatus UPDATED_EXECUTION_STATUS = ExecutionStatus.EXECUTING;

    @Autowired
    private FlowExecutionRepository flowExecutionRepository;

    @Autowired
    private FlowExecutionService flowExecutionService;

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

    private MockMvc restFlowExecutionMockMvc;

    private FlowExecution flowExecution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FlowExecutionResource flowExecutionResource = new FlowExecutionResource(flowExecutionService);
        this.restFlowExecutionMockMvc = MockMvcBuilders.standaloneSetup(flowExecutionResource)
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
    public static FlowExecution createEntity(EntityManager em) {
        FlowExecution flowExecution = new FlowExecution()
            .executionStartTime(DEFAULT_EXECUTION_START_TIME)
            .executionEndTime(DEFAULT_EXECUTION_END_TIME)
            .totalTaskCount(DEFAULT_TOTAL_TASK_COUNT)
            .runningTaskCount(DEFAULT_RUNNING_TASK_COUNT)
            .errorTaskCount(DEFAULT_ERROR_TASK_COUNT)
            .executionStatus(DEFAULT_EXECUTION_STATUS);
        // Add required entity
        Flow flow;
        if (TestUtil.findAll(em, Flow.class).isEmpty()) {
            flow = FlowResourceIT.createEntity(em);
            em.persist(flow);
            em.flush();
        } else {
            flow = TestUtil.findAll(em, Flow.class).get(0);
        }
        flowExecution.setFlow(flow);
        return flowExecution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FlowExecution createUpdatedEntity(EntityManager em) {
        FlowExecution flowExecution = new FlowExecution()
            .executionStartTime(UPDATED_EXECUTION_START_TIME)
            .executionEndTime(UPDATED_EXECUTION_END_TIME)
            .totalTaskCount(UPDATED_TOTAL_TASK_COUNT)
            .runningTaskCount(UPDATED_RUNNING_TASK_COUNT)
            .errorTaskCount(UPDATED_ERROR_TASK_COUNT)
            .executionStatus(UPDATED_EXECUTION_STATUS);
        // Add required entity
        Flow flow;
        if (TestUtil.findAll(em, Flow.class).isEmpty()) {
            flow = FlowResourceIT.createUpdatedEntity(em);
            em.persist(flow);
            em.flush();
        } else {
            flow = TestUtil.findAll(em, Flow.class).get(0);
        }
        flowExecution.setFlow(flow);
        return flowExecution;
    }

    @BeforeEach
    public void initTest() {
        flowExecution = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlowExecution() throws Exception {
        int databaseSizeBeforeCreate = flowExecutionRepository.findAll().size();

        // Create the FlowExecution
        restFlowExecutionMockMvc.perform(post("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flowExecution)))
            .andExpect(status().isCreated());

        // Validate the FlowExecution in the database
        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeCreate + 1);
        FlowExecution testFlowExecution = flowExecutionList.get(flowExecutionList.size() - 1);
        assertThat(testFlowExecution.getExecutionStartTime()).isEqualTo(DEFAULT_EXECUTION_START_TIME);
        assertThat(testFlowExecution.getExecutionEndTime()).isEqualTo(DEFAULT_EXECUTION_END_TIME);
        assertThat(testFlowExecution.getTotalTaskCount()).isEqualTo(DEFAULT_TOTAL_TASK_COUNT);
        assertThat(testFlowExecution.getRunningTaskCount()).isEqualTo(DEFAULT_RUNNING_TASK_COUNT);
        assertThat(testFlowExecution.getErrorTaskCount()).isEqualTo(DEFAULT_ERROR_TASK_COUNT);
        assertThat(testFlowExecution.getExecutionStatus()).isEqualTo(DEFAULT_EXECUTION_STATUS);
    }

    @Test
    @Transactional
    public void createFlowExecutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = flowExecutionRepository.findAll().size();

        // Create the FlowExecution with an existing ID
        flowExecution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlowExecutionMockMvc.perform(post("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flowExecution)))
            .andExpect(status().isBadRequest());

        // Validate the FlowExecution in the database
        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExecutionStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flowExecutionRepository.findAll().size();
        // set the field null
        flowExecution.setExecutionStartTime(null);

        // Create the FlowExecution, which fails.

        restFlowExecutionMockMvc.perform(post("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flowExecution)))
            .andExpect(status().isBadRequest());

        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flowExecutionRepository.findAll().size();
        // set the field null
        flowExecution.setExecutionEndTime(null);

        // Create the FlowExecution, which fails.

        restFlowExecutionMockMvc.perform(post("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flowExecution)))
            .andExpect(status().isBadRequest());

        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = flowExecutionRepository.findAll().size();
        // set the field null
        flowExecution.setExecutionStatus(null);

        // Create the FlowExecution, which fails.

        restFlowExecutionMockMvc.perform(post("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flowExecution)))
            .andExpect(status().isBadRequest());

        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFlowExecutions() throws Exception {
        // Initialize the database
        flowExecutionRepository.saveAndFlush(flowExecution);

        // Get all the flowExecutionList
        restFlowExecutionMockMvc.perform(get("/api/flow-executions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flowExecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].executionStartTime").value(hasItem(DEFAULT_EXECUTION_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].executionEndTime").value(hasItem(DEFAULT_EXECUTION_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].totalTaskCount").value(hasItem(DEFAULT_TOTAL_TASK_COUNT)))
            .andExpect(jsonPath("$.[*].runningTaskCount").value(hasItem(DEFAULT_RUNNING_TASK_COUNT)))
            .andExpect(jsonPath("$.[*].errorTaskCount").value(hasItem(DEFAULT_ERROR_TASK_COUNT)))
            .andExpect(jsonPath("$.[*].executionStatus").value(hasItem(DEFAULT_EXECUTION_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFlowExecution() throws Exception {
        // Initialize the database
        flowExecutionRepository.saveAndFlush(flowExecution);

        // Get the flowExecution
        restFlowExecutionMockMvc.perform(get("/api/flow-executions/{id}", flowExecution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flowExecution.getId().intValue()))
            .andExpect(jsonPath("$.executionStartTime").value(DEFAULT_EXECUTION_START_TIME.toString()))
            .andExpect(jsonPath("$.executionEndTime").value(DEFAULT_EXECUTION_END_TIME.toString()))
            .andExpect(jsonPath("$.totalTaskCount").value(DEFAULT_TOTAL_TASK_COUNT))
            .andExpect(jsonPath("$.runningTaskCount").value(DEFAULT_RUNNING_TASK_COUNT))
            .andExpect(jsonPath("$.errorTaskCount").value(DEFAULT_ERROR_TASK_COUNT))
            .andExpect(jsonPath("$.executionStatus").value(DEFAULT_EXECUTION_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFlowExecution() throws Exception {
        // Get the flowExecution
        restFlowExecutionMockMvc.perform(get("/api/flow-executions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlowExecution() throws Exception {
        // Initialize the database
        flowExecutionService.save(flowExecution);

        int databaseSizeBeforeUpdate = flowExecutionRepository.findAll().size();

        // Update the flowExecution
        FlowExecution updatedFlowExecution = flowExecutionRepository.findById(flowExecution.getId()).get();
        // Disconnect from session so that the updates on updatedFlowExecution are not directly saved in db
        em.detach(updatedFlowExecution);
        updatedFlowExecution
            .executionStartTime(UPDATED_EXECUTION_START_TIME)
            .executionEndTime(UPDATED_EXECUTION_END_TIME)
            .totalTaskCount(UPDATED_TOTAL_TASK_COUNT)
            .runningTaskCount(UPDATED_RUNNING_TASK_COUNT)
            .errorTaskCount(UPDATED_ERROR_TASK_COUNT)
            .executionStatus(UPDATED_EXECUTION_STATUS);

        restFlowExecutionMockMvc.perform(put("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFlowExecution)))
            .andExpect(status().isOk());

        // Validate the FlowExecution in the database
        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeUpdate);
        FlowExecution testFlowExecution = flowExecutionList.get(flowExecutionList.size() - 1);
        assertThat(testFlowExecution.getExecutionStartTime()).isEqualTo(UPDATED_EXECUTION_START_TIME);
        assertThat(testFlowExecution.getExecutionEndTime()).isEqualTo(UPDATED_EXECUTION_END_TIME);
        assertThat(testFlowExecution.getTotalTaskCount()).isEqualTo(UPDATED_TOTAL_TASK_COUNT);
        assertThat(testFlowExecution.getRunningTaskCount()).isEqualTo(UPDATED_RUNNING_TASK_COUNT);
        assertThat(testFlowExecution.getErrorTaskCount()).isEqualTo(UPDATED_ERROR_TASK_COUNT);
        assertThat(testFlowExecution.getExecutionStatus()).isEqualTo(UPDATED_EXECUTION_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFlowExecution() throws Exception {
        int databaseSizeBeforeUpdate = flowExecutionRepository.findAll().size();

        // Create the FlowExecution

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlowExecutionMockMvc.perform(put("/api/flow-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flowExecution)))
            .andExpect(status().isBadRequest());

        // Validate the FlowExecution in the database
        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFlowExecution() throws Exception {
        // Initialize the database
        flowExecutionService.save(flowExecution);

        int databaseSizeBeforeDelete = flowExecutionRepository.findAll().size();

        // Delete the flowExecution
        restFlowExecutionMockMvc.perform(delete("/api/flow-executions/{id}", flowExecution.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FlowExecution> flowExecutionList = flowExecutionRepository.findAll();
        assertThat(flowExecutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
