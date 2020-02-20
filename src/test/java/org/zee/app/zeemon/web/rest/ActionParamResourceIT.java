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
import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.repository.ActionParamRepository;
import org.zee.app.zeemon.service.ActionParamService;
import org.zee.app.zeemon.web.rest.ActionParamResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ActionParamResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ActionParamResourceIT {

    private static final String DEFAULT_PARAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_NAME = "BBBBBBBBBB";

    private static final DataType DEFAULT_PARAM_DATA_TYPE = DataType.STRING;
    private static final DataType UPDATED_PARAM_DATA_TYPE = DataType.NUMBER;

    private static final String DEFAULT_PARAM_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_EXPRESSION = "BBBBBBBBBB";

    @Autowired
    private ActionParamRepository actionParamRepository;

    @Autowired
    private ActionParamService actionParamService;

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

    private MockMvc restActionParamMockMvc;

    private ActionParam actionParam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActionParamResource actionParamResource = new ActionParamResource(actionParamService);
        this.restActionParamMockMvc = MockMvcBuilders.standaloneSetup(actionParamResource)
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
    public static ActionParam createEntity(EntityManager em) {
        ActionParam actionParam = new ActionParam()
            .paramName(DEFAULT_PARAM_NAME)
            .paramDataType(DEFAULT_PARAM_DATA_TYPE)
            .paramValue(DEFAULT_PARAM_VALUE)
            .paramExpression(DEFAULT_PARAM_EXPRESSION);
        // Add required entity
        ActionScript actionScript;
        if (TestUtil.findAll(em, ActionScript.class).isEmpty()) {
            actionScript = ActionScriptResourceIT.createEntity(em);
            em.persist(actionScript);
            em.flush();
        } else {
            actionScript = TestUtil.findAll(em, ActionScript.class).get(0);
        }
        actionParam.setActionScript(actionScript);
        return actionParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActionParam createUpdatedEntity(EntityManager em) {
        ActionParam actionParam = new ActionParam()
            .paramName(UPDATED_PARAM_NAME)
            .paramDataType(UPDATED_PARAM_DATA_TYPE)
            .paramValue(UPDATED_PARAM_VALUE)
            .paramExpression(UPDATED_PARAM_EXPRESSION);
        // Add required entity
        ActionScript actionScript;
        if (TestUtil.findAll(em, ActionScript.class).isEmpty()) {
            actionScript = ActionScriptResourceIT.createUpdatedEntity(em);
            em.persist(actionScript);
            em.flush();
        } else {
            actionScript = TestUtil.findAll(em, ActionScript.class).get(0);
        }
        actionParam.setActionScript(actionScript);
        return actionParam;
    }

    @BeforeEach
    public void initTest() {
        actionParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createActionParam() throws Exception {
        int databaseSizeBeforeCreate = actionParamRepository.findAll().size();

        // Create the ActionParam
        restActionParamMockMvc.perform(post("/api/action-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionParam)))
            .andExpect(status().isCreated());

        // Validate the ActionParam in the database
        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeCreate + 1);
        ActionParam testActionParam = actionParamList.get(actionParamList.size() - 1);
        assertThat(testActionParam.getParamName()).isEqualTo(DEFAULT_PARAM_NAME);
        assertThat(testActionParam.getParamDataType()).isEqualTo(DEFAULT_PARAM_DATA_TYPE);
        assertThat(testActionParam.getParamValue()).isEqualTo(DEFAULT_PARAM_VALUE);
        assertThat(testActionParam.getParamExpression()).isEqualTo(DEFAULT_PARAM_EXPRESSION);
    }

    @Test
    @Transactional
    public void createActionParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actionParamRepository.findAll().size();

        // Create the ActionParam with an existing ID
        actionParam.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActionParamMockMvc.perform(post("/api/action-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionParam)))
            .andExpect(status().isBadRequest());

        // Validate the ActionParam in the database
        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkParamNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionParamRepository.findAll().size();
        // set the field null
        actionParam.setParamName(null);

        // Create the ActionParam, which fails.

        restActionParamMockMvc.perform(post("/api/action-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionParam)))
            .andExpect(status().isBadRequest());

        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParamDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionParamRepository.findAll().size();
        // set the field null
        actionParam.setParamDataType(null);

        // Create the ActionParam, which fails.

        restActionParamMockMvc.perform(post("/api/action-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionParam)))
            .andExpect(status().isBadRequest());

        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActionParams() throws Exception {
        // Initialize the database
        actionParamRepository.saveAndFlush(actionParam);

        // Get all the actionParamList
        restActionParamMockMvc.perform(get("/api/action-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actionParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramName").value(hasItem(DEFAULT_PARAM_NAME)))
            .andExpect(jsonPath("$.[*].paramDataType").value(hasItem(DEFAULT_PARAM_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paramValue").value(hasItem(DEFAULT_PARAM_VALUE)))
            .andExpect(jsonPath("$.[*].paramExpression").value(hasItem(DEFAULT_PARAM_EXPRESSION)));
    }
    
    @Test
    @Transactional
    public void getActionParam() throws Exception {
        // Initialize the database
        actionParamRepository.saveAndFlush(actionParam);

        // Get the actionParam
        restActionParamMockMvc.perform(get("/api/action-params/{id}", actionParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actionParam.getId().intValue()))
            .andExpect(jsonPath("$.paramName").value(DEFAULT_PARAM_NAME))
            .andExpect(jsonPath("$.paramDataType").value(DEFAULT_PARAM_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.paramValue").value(DEFAULT_PARAM_VALUE))
            .andExpect(jsonPath("$.paramExpression").value(DEFAULT_PARAM_EXPRESSION));
    }

    @Test
    @Transactional
    public void getNonExistingActionParam() throws Exception {
        // Get the actionParam
        restActionParamMockMvc.perform(get("/api/action-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionParam() throws Exception {
        // Initialize the database
        actionParamService.save(actionParam);

        int databaseSizeBeforeUpdate = actionParamRepository.findAll().size();

        // Update the actionParam
        ActionParam updatedActionParam = actionParamRepository.findById(actionParam.getId()).get();
        // Disconnect from session so that the updates on updatedActionParam are not directly saved in db
        em.detach(updatedActionParam);
        updatedActionParam
            .paramName(UPDATED_PARAM_NAME)
            .paramDataType(UPDATED_PARAM_DATA_TYPE)
            .paramValue(UPDATED_PARAM_VALUE)
            .paramExpression(UPDATED_PARAM_EXPRESSION);

        restActionParamMockMvc.perform(put("/api/action-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActionParam)))
            .andExpect(status().isOk());

        // Validate the ActionParam in the database
        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeUpdate);
        ActionParam testActionParam = actionParamList.get(actionParamList.size() - 1);
        assertThat(testActionParam.getParamName()).isEqualTo(UPDATED_PARAM_NAME);
        assertThat(testActionParam.getParamDataType()).isEqualTo(UPDATED_PARAM_DATA_TYPE);
        assertThat(testActionParam.getParamValue()).isEqualTo(UPDATED_PARAM_VALUE);
        assertThat(testActionParam.getParamExpression()).isEqualTo(UPDATED_PARAM_EXPRESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingActionParam() throws Exception {
        int databaseSizeBeforeUpdate = actionParamRepository.findAll().size();

        // Create the ActionParam

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActionParamMockMvc.perform(put("/api/action-params")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(actionParam)))
            .andExpect(status().isBadRequest());

        // Validate the ActionParam in the database
        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActionParam() throws Exception {
        // Initialize the database
        actionParamService.save(actionParam);

        int databaseSizeBeforeDelete = actionParamRepository.findAll().size();

        // Delete the actionParam
        restActionParamMockMvc.perform(delete("/api/action-params/{id}", actionParam.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActionParam> actionParamList = actionParamRepository.findAll();
        assertThat(actionParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
