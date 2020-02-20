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
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.repository.ScriptParamRepository;
import org.zee.app.zeemon.service.ScriptParamService;
import org.zee.app.zeemon.web.rest.ScriptParamResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ScriptParamResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ScriptParamResourceIT {

    private static final String DEFAULT_PARAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_NAME = "BBBBBBBBBB";

    private static final DataType DEFAULT_PARAM_DATA_TYPE = DataType.STRING;
    private static final DataType UPDATED_PARAM_DATA_TYPE = DataType.NUMBER;

    private static final String DEFAULT_PARAM_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_EXPRESSION = "BBBBBBBBBB";

    @Autowired
    private ScriptParamRepository scriptParamRepository;

    @Autowired
    private ScriptParamService scriptParamService;

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

    private MockMvc restScriptParamMockMvc;

    private ScriptParam scriptParam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScriptParamResource scriptParamResource = new ScriptParamResource(scriptParamService);
        this.restScriptParamMockMvc = MockMvcBuilders.standaloneSetup(scriptParamResource)
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
    public static ScriptParam createEntity(EntityManager em) {
        ScriptParam scriptParam = new ScriptParam()
            .paramName(DEFAULT_PARAM_NAME)
            .paramDataType(DEFAULT_PARAM_DATA_TYPE)
            .paramValue(DEFAULT_PARAM_VALUE)
            .paramExpression(DEFAULT_PARAM_EXPRESSION);
        // Add required entity
        CheckScript checkScript;
        if (TestUtil.findAll(em, CheckScript.class).isEmpty()) {
            checkScript = CheckScriptResourceIT.createEntity(em);
            em.persist(checkScript);
            em.flush();
        } else {
            checkScript = TestUtil.findAll(em, CheckScript.class).get(0);
        }
        scriptParam.setCheckScript(checkScript);
        return scriptParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScriptParam createUpdatedEntity(EntityManager em) {
        ScriptParam scriptParam = new ScriptParam()
            .paramName(UPDATED_PARAM_NAME)
            .paramDataType(UPDATED_PARAM_DATA_TYPE)
            .paramValue(UPDATED_PARAM_VALUE)
            .paramExpression(UPDATED_PARAM_EXPRESSION);
        // Add required entity
        CheckScript checkScript;
        if (TestUtil.findAll(em, CheckScript.class).isEmpty()) {
            checkScript = CheckScriptResourceIT.createUpdatedEntity(em);
            em.persist(checkScript);
            em.flush();
        } else {
            checkScript = TestUtil.findAll(em, CheckScript.class).get(0);
        }
        scriptParam.setCheckScript(checkScript);
        return scriptParam;
    }

    @BeforeEach
    public void initTest() {
        scriptParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createScriptParam() throws Exception {
        int databaseSizeBeforeCreate = scriptParamRepository.findAll().size();

        // Create the ScriptParam
        restScriptParamMockMvc.perform(post("/api/script-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scriptParam)))
            .andExpect(status().isCreated());

        // Validate the ScriptParam in the database
        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeCreate + 1);
        ScriptParam testScriptParam = scriptParamList.get(scriptParamList.size() - 1);
        assertThat(testScriptParam.getParamName()).isEqualTo(DEFAULT_PARAM_NAME);
        assertThat(testScriptParam.getParamDataType()).isEqualTo(DEFAULT_PARAM_DATA_TYPE);
        assertThat(testScriptParam.getParamValue()).isEqualTo(DEFAULT_PARAM_VALUE);
        assertThat(testScriptParam.getParamExpression()).isEqualTo(DEFAULT_PARAM_EXPRESSION);
    }

    @Test
    @Transactional
    public void createScriptParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scriptParamRepository.findAll().size();

        // Create the ScriptParam with an existing ID
        scriptParam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScriptParamMockMvc.perform(post("/api/script-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scriptParam)))
            .andExpect(status().isBadRequest());

        // Validate the ScriptParam in the database
        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkParamNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scriptParamRepository.findAll().size();
        // set the field null
        scriptParam.setParamName(null);

        // Create the ScriptParam, which fails.

        restScriptParamMockMvc.perform(post("/api/script-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scriptParam)))
            .andExpect(status().isBadRequest());

        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParamDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = scriptParamRepository.findAll().size();
        // set the field null
        scriptParam.setParamDataType(null);

        // Create the ScriptParam, which fails.

        restScriptParamMockMvc.perform(post("/api/script-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scriptParam)))
            .andExpect(status().isBadRequest());

        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScriptParams() throws Exception {
        // Initialize the database
        scriptParamRepository.saveAndFlush(scriptParam);

        // Get all the scriptParamList
        restScriptParamMockMvc.perform(get("/api/script-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scriptParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramName").value(hasItem(DEFAULT_PARAM_NAME)))
            .andExpect(jsonPath("$.[*].paramDataType").value(hasItem(DEFAULT_PARAM_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)))
            .andExpect(jsonPath("$.[*].paramExpression").value(hasItem(DEFAULT_PARAM_EXPRESSION)));
    }
    
    @Test
    @Transactional
    public void getScriptParam() throws Exception {
        // Initialize the database
        scriptParamRepository.saveAndFlush(scriptParam);

        // Get the scriptParam
        restScriptParamMockMvc.perform(get("/api/script-params/{id}", scriptParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scriptParam.getId().intValue()))
            .andExpect(jsonPath("$.paramName").value(DEFAULT_PARAM_NAME))
            .andExpect(jsonPath("$.paramDataType").value(DEFAULT_PARAM_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.paramValue").value(DEFAULT_PARAM_VALUE))
            .andExpect(jsonPath("$.paramExpression").value(DEFAULT_PARAM_EXPRESSION));
    }

    @Test
    @Transactional
    public void getNonExistingScriptParam() throws Exception {
        // Get the scriptParam
        restScriptParamMockMvc.perform(get("/api/script-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScriptParam() throws Exception {
        // Initialize the database
        scriptParamService.save(scriptParam);

        int databaseSizeBeforeUpdate = scriptParamRepository.findAll().size();

        // Update the scriptParam
        ScriptParam updatedScriptParam = scriptParamRepository.findById(scriptParam.getId()).get();
        // Disconnect from session so that the updates on updatedScriptParam are not directly saved in db
        em.detach(updatedScriptParam);
        updatedScriptParam
            .paramName(UPDATED_PARAM_NAME)
            .paramDataType(UPDATED_PARAM_DATA_TYPE)
            .paramValue(UPDATED_PARAM_VALUE)
            .paramExpression(UPDATED_PARAM_EXPRESSION);

        restScriptParamMockMvc.perform(put("/api/script-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedScriptParam)))
            .andExpect(status().isOk());

        // Validate the ScriptParam in the database
        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeUpdate);
        ScriptParam testScriptParam = scriptParamList.get(scriptParamList.size() - 1);
        assertThat(testScriptParam.getParamName()).isEqualTo(UPDATED_PARAM_NAME);
        assertThat(testScriptParam.getParamDataType()).isEqualTo(UPDATED_PARAM_DATA_TYPE);
        assertThat(testScriptParam.getParamValue()).isEqualTo(UPDATED_PARAM_VALUE);
        assertThat(testScriptParam.getParamExpression()).isEqualTo(UPDATED_PARAM_EXPRESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingScriptParam() throws Exception {
        int databaseSizeBeforeUpdate = scriptParamRepository.findAll().size();

        // Create the ScriptParam

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScriptParamMockMvc.perform(put("/api/script-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scriptParam)))
            .andExpect(status().isBadRequest());

        // Validate the ScriptParam in the database
        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScriptParam() throws Exception {
        // Initialize the database
        scriptParamService.save(scriptParam);

        int databaseSizeBeforeDelete = scriptParamRepository.findAll().size();

        // Delete the scriptParam
        restScriptParamMockMvc.perform(delete("/api/script-params/{id}", scriptParam.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScriptParam> scriptParamList = scriptParamRepository.findAll();
        assertThat(scriptParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
