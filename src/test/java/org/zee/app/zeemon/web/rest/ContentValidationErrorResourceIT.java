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
import org.zee.app.zeemon.domain.ContentValidationError;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.repository.ContentValidationErrorRepository;
import org.zee.app.zeemon.service.ContentValidationErrorService;
import org.zee.app.zeemon.web.rest.ContentValidationErrorResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContentValidationErrorResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ContentValidationErrorResourceIT {

    private static final Integer DEFAULT_SOURCE_INDEX = 0;
    private static final Integer UPDATED_SOURCE_INDEX = 1;

    private static final String DEFAULT_SOURCE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_TEXT = "BBBBBBBBBB";

    @Autowired
    private ContentValidationErrorRepository contentValidationErrorRepository;

    @Autowired
    private ContentValidationErrorService contentValidationErrorService;

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

    private MockMvc restContentValidationErrorMockMvc;

    private ContentValidationError contentValidationError;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentValidationErrorResource contentValidationErrorResource = new ContentValidationErrorResource(contentValidationErrorService);
        this.restContentValidationErrorMockMvc = MockMvcBuilders.standaloneSetup(contentValidationErrorResource)
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
    public static ContentValidationError createEntity(EntityManager em) {
        ContentValidationError contentValidationError = new ContentValidationError()
            .sourceIndex(DEFAULT_SOURCE_INDEX)
            .sourceText(DEFAULT_SOURCE_TEXT)
            .errorText(DEFAULT_ERROR_TEXT);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        contentValidationError.setTask(task);
        // Add required entity
        TaskExecution taskExecution;
        if (TestUtil.findAll(em, TaskExecution.class).isEmpty()) {
            taskExecution = TaskExecutionResourceIT.createEntity(em);
            em.persist(taskExecution);
            em.flush();
        } else {
            taskExecution = TestUtil.findAll(em, TaskExecution.class).get(0);
        }
        contentValidationError.setTaskExecution(taskExecution);
        return contentValidationError;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentValidationError createUpdatedEntity(EntityManager em) {
        ContentValidationError contentValidationError = new ContentValidationError()
            .sourceIndex(UPDATED_SOURCE_INDEX)
            .sourceText(UPDATED_SOURCE_TEXT)
            .errorText(UPDATED_ERROR_TEXT);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createUpdatedEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        contentValidationError.setTask(task);
        // Add required entity
        TaskExecution taskExecution;
        if (TestUtil.findAll(em, TaskExecution.class).isEmpty()) {
            taskExecution = TaskExecutionResourceIT.createUpdatedEntity(em);
            em.persist(taskExecution);
            em.flush();
        } else {
            taskExecution = TestUtil.findAll(em, TaskExecution.class).get(0);
        }
        contentValidationError.setTaskExecution(taskExecution);
        return contentValidationError;
    }

    @BeforeEach
    public void initTest() {
        contentValidationError = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentValidationError() throws Exception {
        int databaseSizeBeforeCreate = contentValidationErrorRepository.findAll().size();

        // Create the ContentValidationError
        restContentValidationErrorMockMvc.perform(post("/api/content-validation-errors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentValidationError)))
            .andExpect(status().isCreated());

        // Validate the ContentValidationError in the database
        List<ContentValidationError> contentValidationErrorList = contentValidationErrorRepository.findAll();
        assertThat(contentValidationErrorList).hasSize(databaseSizeBeforeCreate + 1);
        ContentValidationError testContentValidationError = contentValidationErrorList.get(contentValidationErrorList.size() - 1);
        assertThat(testContentValidationError.getSourceIndex()).isEqualTo(DEFAULT_SOURCE_INDEX);
        assertThat(testContentValidationError.getSourceText()).isEqualTo(DEFAULT_SOURCE_TEXT);
        assertThat(testContentValidationError.getErrorText()).isEqualTo(DEFAULT_ERROR_TEXT);
    }

    @Test
    @Transactional
    public void createContentValidationErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentValidationErrorRepository.findAll().size();

        // Create the ContentValidationError with an existing ID
        contentValidationError.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentValidationErrorMockMvc.perform(post("/api/content-validation-errors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentValidationError)))
            .andExpect(status().isBadRequest());

        // Validate the ContentValidationError in the database
        List<ContentValidationError> contentValidationErrorList = contentValidationErrorRepository.findAll();
        assertThat(contentValidationErrorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSourceIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentValidationErrorRepository.findAll().size();
        // set the field null
        contentValidationError.setSourceIndex(null);

        // Create the ContentValidationError, which fails.

        restContentValidationErrorMockMvc.perform(post("/api/content-validation-errors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentValidationError)))
            .andExpect(status().isBadRequest());

        List<ContentValidationError> contentValidationErrorList = contentValidationErrorRepository.findAll();
        assertThat(contentValidationErrorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentValidationErrors() throws Exception {
        // Initialize the database
        contentValidationErrorRepository.saveAndFlush(contentValidationError);

        // Get all the contentValidationErrorList
        restContentValidationErrorMockMvc.perform(get("/api/content-validation-errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentValidationError.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceIndex").value(hasItem(DEFAULT_SOURCE_INDEX)))
            .andExpect(jsonPath("$.[*].sourceText").value(hasItem(DEFAULT_SOURCE_TEXT)))
            .andExpect(jsonPath("$.[*].errorText").value(hasItem(DEFAULT_ERROR_TEXT)));
    }
    
    @Test
    @Transactional
    public void getContentValidationError() throws Exception {
        // Initialize the database
        contentValidationErrorRepository.saveAndFlush(contentValidationError);

        // Get the contentValidationError
        restContentValidationErrorMockMvc.perform(get("/api/content-validation-errors/{id}", contentValidationError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentValidationError.getId().intValue()))
            .andExpect(jsonPath("$.sourceIndex").value(DEFAULT_SOURCE_INDEX))
            .andExpect(jsonPath("$.sourceText").value(DEFAULT_SOURCE_TEXT))
            .andExpect(jsonPath("$.errorText").value(DEFAULT_ERROR_TEXT));
    }

    @Test
    @Transactional
    public void getNonExistingContentValidationError() throws Exception {
        // Get the contentValidationError
        restContentValidationErrorMockMvc.perform(get("/api/content-validation-errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentValidationError() throws Exception {
        // Initialize the database
        contentValidationErrorService.save(contentValidationError);

        int databaseSizeBeforeUpdate = contentValidationErrorRepository.findAll().size();

        // Update the contentValidationError
        ContentValidationError updatedContentValidationError = contentValidationErrorRepository.findById(contentValidationError.getId()).get();
        // Disconnect from session so that the updates on updatedContentValidationError are not directly saved in db
        em.detach(updatedContentValidationError);
        updatedContentValidationError
            .sourceIndex(UPDATED_SOURCE_INDEX)
            .sourceText(UPDATED_SOURCE_TEXT)
            .errorText(UPDATED_ERROR_TEXT);

        restContentValidationErrorMockMvc.perform(put("/api/content-validation-errors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentValidationError)))
            .andExpect(status().isOk());

        // Validate the ContentValidationError in the database
        List<ContentValidationError> contentValidationErrorList = contentValidationErrorRepository.findAll();
        assertThat(contentValidationErrorList).hasSize(databaseSizeBeforeUpdate);
        ContentValidationError testContentValidationError = contentValidationErrorList.get(contentValidationErrorList.size() - 1);
        assertThat(testContentValidationError.getSourceIndex()).isEqualTo(UPDATED_SOURCE_INDEX);
        assertThat(testContentValidationError.getSourceText()).isEqualTo(UPDATED_SOURCE_TEXT);
        assertThat(testContentValidationError.getErrorText()).isEqualTo(UPDATED_ERROR_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingContentValidationError() throws Exception {
        int databaseSizeBeforeUpdate = contentValidationErrorRepository.findAll().size();

        // Create the ContentValidationError

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentValidationErrorMockMvc.perform(put("/api/content-validation-errors")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentValidationError)))
            .andExpect(status().isBadRequest());

        // Validate the ContentValidationError in the database
        List<ContentValidationError> contentValidationErrorList = contentValidationErrorRepository.findAll();
        assertThat(contentValidationErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentValidationError() throws Exception {
        // Initialize the database
        contentValidationErrorService.save(contentValidationError);

        int databaseSizeBeforeDelete = contentValidationErrorRepository.findAll().size();

        // Delete the contentValidationError
        restContentValidationErrorMockMvc.perform(delete("/api/content-validation-errors/{id}", contentValidationError.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentValidationError> contentValidationErrorList = contentValidationErrorRepository.findAll();
        assertThat(contentValidationErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
