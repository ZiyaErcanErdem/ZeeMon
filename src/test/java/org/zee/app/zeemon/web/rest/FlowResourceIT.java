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
import org.zee.app.zeemon.domain.enumeration.FlowState;
import org.zee.app.zeemon.repository.FlowRepository;
import org.zee.app.zeemon.service.FlowService;
import org.zee.app.zeemon.web.rest.FlowResource;
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
 * Integration tests for the {@link FlowResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class FlowResourceIT {

    private static final String DEFAULT_FLOW_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FLOW_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLOW_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FLOW_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_NEXT_EXECUTION_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_EXECUTION_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final FlowState DEFAULT_FLOW_STATE = FlowState.PENDING;
    private static final FlowState UPDATED_FLOW_STATE = FlowState.PAUSED;

    @Autowired
    private FlowRepository flowRepository;

    @Autowired
    private FlowService flowService;

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

    private MockMvc restFlowMockMvc;

    private Flow flow;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FlowResource flowResource = new FlowResource(flowService);
        this.restFlowMockMvc = MockMvcBuilders.standaloneSetup(flowResource)
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
    public static Flow createEntity(EntityManager em) {
        Flow flow = new Flow()
            .flowName(DEFAULT_FLOW_NAME)
            .flowDescription(DEFAULT_FLOW_DESCRIPTION)
            .nextExecutionStartTime(DEFAULT_NEXT_EXECUTION_START_TIME)
            .flowState(DEFAULT_FLOW_STATE);
        return flow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flow createUpdatedEntity(EntityManager em) {
        Flow flow = new Flow()
            .flowName(UPDATED_FLOW_NAME)
            .flowDescription(UPDATED_FLOW_DESCRIPTION)
            .nextExecutionStartTime(UPDATED_NEXT_EXECUTION_START_TIME)
            .flowState(UPDATED_FLOW_STATE);
        return flow;
    }

    @BeforeEach
    public void initTest() {
        flow = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlow() throws Exception {
        int databaseSizeBeforeCreate = flowRepository.findAll().size();

        // Create the Flow
        restFlowMockMvc.perform(post("/api/flows")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flow)))
            .andExpect(status().isCreated());

        // Validate the Flow in the database
        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeCreate + 1);
        Flow testFlow = flowList.get(flowList.size() - 1);
        assertThat(testFlow.getFlowName()).isEqualTo(DEFAULT_FLOW_NAME);
        assertThat(testFlow.getFlowDescription()).isEqualTo(DEFAULT_FLOW_DESCRIPTION);
        assertThat(testFlow.getNextExecutionStartTime()).isEqualTo(DEFAULT_NEXT_EXECUTION_START_TIME);
        assertThat(testFlow.getFlowState()).isEqualTo(DEFAULT_FLOW_STATE);
    }

    @Test
    @Transactional
    public void createFlowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = flowRepository.findAll().size();

        // Create the Flow with an existing ID
        flow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlowMockMvc.perform(post("/api/flows")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flow)))
            .andExpect(status().isBadRequest());

        // Validate the Flow in the database
        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFlowNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = flowRepository.findAll().size();
        // set the field null
        flow.setFlowName(null);

        // Create the Flow, which fails.

        restFlowMockMvc.perform(post("/api/flows")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flow)))
            .andExpect(status().isBadRequest());

        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlowStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = flowRepository.findAll().size();
        // set the field null
        flow.setFlowState(null);

        // Create the Flow, which fails.

        restFlowMockMvc.perform(post("/api/flows")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flow)))
            .andExpect(status().isBadRequest());

        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFlows() throws Exception {
        // Initialize the database
        flowRepository.saveAndFlush(flow);

        // Get all the flowList
        restFlowMockMvc.perform(get("/api/flows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flow.getId().intValue())))
            .andExpect(jsonPath("$.[*].flowName").value(hasItem(DEFAULT_FLOW_NAME)))
            .andExpect(jsonPath("$.[*].flowDescription").value(hasItem(DEFAULT_FLOW_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nextExecutionStartTime").value(hasItem(DEFAULT_NEXT_EXECUTION_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].flowState").value(hasItem(DEFAULT_FLOW_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFlow() throws Exception {
        // Initialize the database
        flowRepository.saveAndFlush(flow);

        // Get the flow
        restFlowMockMvc.perform(get("/api/flows/{id}", flow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flow.getId().intValue()))
            .andExpect(jsonPath("$.flowName").value(DEFAULT_FLOW_NAME))
            .andExpect(jsonPath("$.flowDescription").value(DEFAULT_FLOW_DESCRIPTION))
            .andExpect(jsonPath("$.nextExecutionStartTime").value(DEFAULT_NEXT_EXECUTION_START_TIME.toString()))
            .andExpect(jsonPath("$.flowState").value(DEFAULT_FLOW_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFlow() throws Exception {
        // Get the flow
        restFlowMockMvc.perform(get("/api/flows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlow() throws Exception {
        // Initialize the database
        flowService.save(flow);

        int databaseSizeBeforeUpdate = flowRepository.findAll().size();

        // Update the flow
        Flow updatedFlow = flowRepository.findById(flow.getId()).get();
        // Disconnect from session so that the updates on updatedFlow are not directly saved in db
        em.detach(updatedFlow);
        updatedFlow
            .flowName(UPDATED_FLOW_NAME)
            .flowDescription(UPDATED_FLOW_DESCRIPTION)
            .nextExecutionStartTime(UPDATED_NEXT_EXECUTION_START_TIME)
            .flowState(UPDATED_FLOW_STATE);

        restFlowMockMvc.perform(put("/api/flows")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFlow)))
            .andExpect(status().isOk());

        // Validate the Flow in the database
        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeUpdate);
        Flow testFlow = flowList.get(flowList.size() - 1);
        assertThat(testFlow.getFlowName()).isEqualTo(UPDATED_FLOW_NAME);
        assertThat(testFlow.getFlowDescription()).isEqualTo(UPDATED_FLOW_DESCRIPTION);
        assertThat(testFlow.getNextExecutionStartTime()).isEqualTo(UPDATED_NEXT_EXECUTION_START_TIME);
        assertThat(testFlow.getFlowState()).isEqualTo(UPDATED_FLOW_STATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFlow() throws Exception {
        int databaseSizeBeforeUpdate = flowRepository.findAll().size();

        // Create the Flow

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlowMockMvc.perform(put("/api/flows")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(flow)))
            .andExpect(status().isBadRequest());

        // Validate the Flow in the database
        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFlow() throws Exception {
        // Initialize the database
        flowService.save(flow);

        int databaseSizeBeforeDelete = flowRepository.findAll().size();

        // Delete the flow
        restFlowMockMvc.perform(delete("/api/flows/{id}", flow.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Flow> flowList = flowRepository.findAll();
        assertThat(flowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
