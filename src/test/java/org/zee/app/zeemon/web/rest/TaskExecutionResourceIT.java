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
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;
import org.zee.app.zeemon.repository.TaskExecutionRepository;
import org.zee.app.zeemon.service.TaskExecutionService;
import org.zee.app.zeemon.web.rest.TaskExecutionResource;
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
 * Integration tests for the {@link TaskExecutionResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class TaskExecutionResourceIT {

    private static final Instant DEFAULT_EXECUTION_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXECUTION_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTION_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ExecutionStatus DEFAULT_EXECUTION_STATUS = ExecutionStatus.PENDING;
    private static final ExecutionStatus UPDATED_EXECUTION_STATUS = ExecutionStatus.EXECUTING;

    @Autowired
    private TaskExecutionRepository taskExecutionRepository;

    @Autowired
    private TaskExecutionService taskExecutionService;

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

    private MockMvc restTaskExecutionMockMvc;

    private TaskExecution taskExecution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskExecutionResource taskExecutionResource = new TaskExecutionResource(taskExecutionService);
        this.restTaskExecutionMockMvc = MockMvcBuilders.standaloneSetup(taskExecutionResource)
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
    public static TaskExecution createEntity(EntityManager em) {
        TaskExecution taskExecution = new TaskExecution()
            .executionStartTime(DEFAULT_EXECUTION_START_TIME)
            .executionEndTime(DEFAULT_EXECUTION_END_TIME)
            .executionStatus(DEFAULT_EXECUTION_STATUS);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        taskExecution.setTask(task);
        // Add required entity
        FlowExecution flowExecution;
        if (TestUtil.findAll(em, FlowExecution.class).isEmpty()) {
            flowExecution = FlowExecutionResourceIT.createEntity(em);
            em.persist(flowExecution);
            em.flush();
        } else {
            flowExecution = TestUtil.findAll(em, FlowExecution.class).get(0);
        }
        taskExecution.setFlowExecution(flowExecution);
        return taskExecution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskExecution createUpdatedEntity(EntityManager em) {
        TaskExecution taskExecution = new TaskExecution()
            .executionStartTime(UPDATED_EXECUTION_START_TIME)
            .executionEndTime(UPDATED_EXECUTION_END_TIME)
            .executionStatus(UPDATED_EXECUTION_STATUS);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createUpdatedEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        taskExecution.setTask(task);
        // Add required entity
        FlowExecution flowExecution;
        if (TestUtil.findAll(em, FlowExecution.class).isEmpty()) {
            flowExecution = FlowExecutionResourceIT.createUpdatedEntity(em);
            em.persist(flowExecution);
            em.flush();
        } else {
            flowExecution = TestUtil.findAll(em, FlowExecution.class).get(0);
        }
        taskExecution.setFlowExecution(flowExecution);
        return taskExecution;
    }

    @BeforeEach
    public void initTest() {
        taskExecution = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskExecution() throws Exception {
        int databaseSizeBeforeCreate = taskExecutionRepository.findAll().size();

        // Create the TaskExecution
        restTaskExecutionMockMvc.perform(post("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecution)))
            .andExpect(status().isCreated());

        // Validate the TaskExecution in the database
        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeCreate + 1);
        TaskExecution testTaskExecution = taskExecutionList.get(taskExecutionList.size() - 1);
        assertThat(testTaskExecution.getExecutionStartTime()).isEqualTo(DEFAULT_EXECUTION_START_TIME);
        assertThat(testTaskExecution.getExecutionEndTime()).isEqualTo(DEFAULT_EXECUTION_END_TIME);
        assertThat(testTaskExecution.getExecutionStatus()).isEqualTo(DEFAULT_EXECUTION_STATUS);
    }

    @Test
    @Transactional
    public void createTaskExecutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskExecutionRepository.findAll().size();

        // Create the TaskExecution with an existing ID
        taskExecution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskExecutionMockMvc.perform(post("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecution)))
            .andExpect(status().isBadRequest());

        // Validate the TaskExecution in the database
        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExecutionStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecutionRepository.findAll().size();
        // set the field null
        taskExecution.setExecutionStartTime(null);

        // Create the TaskExecution, which fails.

        restTaskExecutionMockMvc.perform(post("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecution)))
            .andExpect(status().isBadRequest());

        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecutionRepository.findAll().size();
        // set the field null
        taskExecution.setExecutionEndTime(null);

        // Create the TaskExecution, which fails.

        restTaskExecutionMockMvc.perform(post("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecution)))
            .andExpect(status().isBadRequest());

        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecutionRepository.findAll().size();
        // set the field null
        taskExecution.setExecutionStatus(null);

        // Create the TaskExecution, which fails.

        restTaskExecutionMockMvc.perform(post("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecution)))
            .andExpect(status().isBadRequest());

        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskExecutions() throws Exception {
        // Initialize the database
        taskExecutionRepository.saveAndFlush(taskExecution);

        // Get all the taskExecutionList
        restTaskExecutionMockMvc.perform(get("/api/task-executions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskExecution.getId().intValue())))
            .andExpect(jsonPath("$.[*].executionStartTime").value(hasItem(DEFAULT_EXECUTION_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].executionEndTime").value(hasItem(DEFAULT_EXECUTION_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].executionStatus").value(hasItem(DEFAULT_EXECUTION_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getTaskExecution() throws Exception {
        // Initialize the database
        taskExecutionRepository.saveAndFlush(taskExecution);

        // Get the taskExecution
        restTaskExecutionMockMvc.perform(get("/api/task-executions/{id}", taskExecution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskExecution.getId().intValue()))
            .andExpect(jsonPath("$.executionStartTime").value(DEFAULT_EXECUTION_START_TIME.toString()))
            .andExpect(jsonPath("$.executionEndTime").value(DEFAULT_EXECUTION_END_TIME.toString()))
            .andExpect(jsonPath("$.executionStatus").value(DEFAULT_EXECUTION_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaskExecution() throws Exception {
        // Get the taskExecution
        restTaskExecutionMockMvc.perform(get("/api/task-executions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskExecution() throws Exception {
        // Initialize the database
        taskExecutionService.save(taskExecution);

        int databaseSizeBeforeUpdate = taskExecutionRepository.findAll().size();

        // Update the taskExecution
        TaskExecution updatedTaskExecution = taskExecutionRepository.findById(taskExecution.getId()).get();
        // Disconnect from session so that the updates on updatedTaskExecution are not directly saved in db
        em.detach(updatedTaskExecution);
        updatedTaskExecution
            .executionStartTime(UPDATED_EXECUTION_START_TIME)
            .executionEndTime(UPDATED_EXECUTION_END_TIME)
            .executionStatus(UPDATED_EXECUTION_STATUS);

        restTaskExecutionMockMvc.perform(put("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskExecution)))
            .andExpect(status().isOk());

        // Validate the TaskExecution in the database
        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeUpdate);
        TaskExecution testTaskExecution = taskExecutionList.get(taskExecutionList.size() - 1);
        assertThat(testTaskExecution.getExecutionStartTime()).isEqualTo(UPDATED_EXECUTION_START_TIME);
        assertThat(testTaskExecution.getExecutionEndTime()).isEqualTo(UPDATED_EXECUTION_END_TIME);
        assertThat(testTaskExecution.getExecutionStatus()).isEqualTo(UPDATED_EXECUTION_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskExecution() throws Exception {
        int databaseSizeBeforeUpdate = taskExecutionRepository.findAll().size();

        // Create the TaskExecution

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskExecutionMockMvc.perform(put("/api/task-executions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecution)))
            .andExpect(status().isBadRequest());

        // Validate the TaskExecution in the database
        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskExecution() throws Exception {
        // Initialize the database
        taskExecutionService.save(taskExecution);

        int databaseSizeBeforeDelete = taskExecutionRepository.findAll().size();

        // Delete the taskExecution
        restTaskExecutionMockMvc.perform(delete("/api/task-executions/{id}", taskExecution.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskExecution> taskExecutionList = taskExecutionRepository.findAll();
        assertThat(taskExecutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
