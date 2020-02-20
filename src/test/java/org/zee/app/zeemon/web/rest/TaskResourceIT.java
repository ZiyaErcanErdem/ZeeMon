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
import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.enumeration.TaskState;
import org.zee.app.zeemon.repository.TaskRepository;
import org.zee.app.zeemon.service.TaskService;
import org.zee.app.zeemon.web.rest.TaskResource;
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
 * Integration tests for the {@link TaskResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class TaskResourceIT {

    private static final String DEFAULT_TASK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TASK_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_NEXT_EXECUTION_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_EXECUTION_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TaskState DEFAULT_TASK_STATE = TaskState.PENDING;
    private static final TaskState UPDATED_TASK_STATE = TaskState.PAUSED;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

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

    private MockMvc restTaskMockMvc;

    private Task task;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskResource taskResource = new TaskResource(taskService);
        this.restTaskMockMvc = MockMvcBuilders.standaloneSetup(taskResource)
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
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .taskName(DEFAULT_TASK_NAME)
            .taskDescription(DEFAULT_TASK_DESCRIPTION)
            .nextExecutionStartTime(DEFAULT_NEXT_EXECUTION_START_TIME)
            .taskState(DEFAULT_TASK_STATE);
        // Add required entity
        Agent agent;
        if (TestUtil.findAll(em, Agent.class).isEmpty()) {
            agent = AgentResourceIT.createEntity(em);
            em.persist(agent);
            em.flush();
        } else {
            agent = TestUtil.findAll(em, Agent.class).get(0);
        }
        task.setAgent(agent);
        // Add required entity
        CheckScript checkScript;
        if (TestUtil.findAll(em, CheckScript.class).isEmpty()) {
            checkScript = CheckScriptResourceIT.createEntity(em);
            em.persist(checkScript);
            em.flush();
        } else {
            checkScript = TestUtil.findAll(em, CheckScript.class).get(0);
        }
        task.setCheckScript(checkScript);
        return task;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .taskName(UPDATED_TASK_NAME)
            .taskDescription(UPDATED_TASK_DESCRIPTION)
            .nextExecutionStartTime(UPDATED_NEXT_EXECUTION_START_TIME)
            .taskState(UPDATED_TASK_STATE);
        // Add required entity
        Agent agent;
        if (TestUtil.findAll(em, Agent.class).isEmpty()) {
            agent = AgentResourceIT.createUpdatedEntity(em);
            em.persist(agent);
            em.flush();
        } else {
            agent = TestUtil.findAll(em, Agent.class).get(0);
        }
        task.setAgent(agent);
        // Add required entity
        CheckScript checkScript;
        if (TestUtil.findAll(em, CheckScript.class).isEmpty()) {
            checkScript = CheckScriptResourceIT.createUpdatedEntity(em);
            em.persist(checkScript);
            em.flush();
        } else {
            checkScript = TestUtil.findAll(em, CheckScript.class).get(0);
        }
        task.setCheckScript(checkScript);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskName()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(testTask.getTaskDescription()).isEqualTo(DEFAULT_TASK_DESCRIPTION);
        assertThat(testTask.getNextExecutionStartTime()).isEqualTo(DEFAULT_NEXT_EXECUTION_START_TIME);
        assertThat(testTask.getTaskState()).isEqualTo(DEFAULT_TASK_STATE);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTaskNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTaskName(null);

        // Create the Task, which fails.

        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaskStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskRepository.findAll().size();
        // set the field null
        task.setTaskState(null);

        // Create the Task, which fails.

        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskName").value(hasItem(DEFAULT_TASK_NAME)))
            .andExpect(jsonPath("$.[*].taskDescription").value(hasItem(DEFAULT_TASK_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nextExecutionStartTime").value(hasItem(DEFAULT_NEXT_EXECUTION_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].taskState").value(hasItem(DEFAULT_TASK_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.taskName").value(DEFAULT_TASK_NAME))
            .andExpect(jsonPath("$.taskDescription").value(DEFAULT_TASK_DESCRIPTION))
            .andExpect(jsonPath("$.nextExecutionStartTime").value(DEFAULT_NEXT_EXECUTION_START_TIME.toString()))
            .andExpect(jsonPath("$.taskState").value(DEFAULT_TASK_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskService.save(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .taskName(UPDATED_TASK_NAME)
            .taskDescription(UPDATED_TASK_DESCRIPTION)
            .nextExecutionStartTime(UPDATED_NEXT_EXECUTION_START_TIME)
            .taskState(UPDATED_TASK_STATE);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTask)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTaskName()).isEqualTo(UPDATED_TASK_NAME);
        assertThat(testTask.getTaskDescription()).isEqualTo(UPDATED_TASK_DESCRIPTION);
        assertThat(testTask.getNextExecutionStartTime()).isEqualTo(UPDATED_NEXT_EXECUTION_START_TIME);
        assertThat(testTask.getTaskState()).isEqualTo(UPDATED_TASK_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskService.save(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
