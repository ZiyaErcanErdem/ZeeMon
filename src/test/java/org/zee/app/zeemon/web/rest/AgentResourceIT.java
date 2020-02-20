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
import org.zee.app.zeemon.domain.enumeration.AgentStatus;
import org.zee.app.zeemon.domain.enumeration.AgentType;
import org.zee.app.zeemon.repository.AgentRepository;
import org.zee.app.zeemon.service.AgentService;
import org.zee.app.zeemon.web.rest.AgentResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link AgentResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class AgentResourceIT {

    private static final String DEFAULT_AGENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AGENT_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_INSTANCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LISTEN_URI = "AAAAAAAAAA";
    private static final String UPDATED_LISTEN_URI = "BBBBBBBBBB";

    private static final AgentType DEFAULT_AGENT_TYPE = AgentType.SQL_AGENT;
    private static final AgentType UPDATED_AGENT_TYPE = AgentType.SHELL_AGENT;

    private static final AgentStatus DEFAULT_AGENT_STATUS = AgentStatus.ACTIVE;
    private static final AgentStatus UPDATED_AGENT_STATUS = AgentStatus.PASSIVE;

    private static final String DEFAULT_AGENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentService agentService;

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

    private MockMvc restAgentMockMvc;

    private Agent agent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgentResource agentResource = new AgentResource(agentService);
        this.restAgentMockMvc = MockMvcBuilders.standaloneSetup(agentResource)
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
    public static Agent createEntity(EntityManager em) {
        Agent agent = new Agent()
            .agentName(DEFAULT_AGENT_NAME)
            .agentInstanceId(DEFAULT_AGENT_INSTANCE_ID)
            .listenURI(DEFAULT_LISTEN_URI)
            .agentType(DEFAULT_AGENT_TYPE)
            .agentStatus(DEFAULT_AGENT_STATUS)
            .agentDescription(DEFAULT_AGENT_DESCRIPTION);
        return agent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createUpdatedEntity(EntityManager em) {
        Agent agent = new Agent()
            .agentName(UPDATED_AGENT_NAME)
            .agentInstanceId(UPDATED_AGENT_INSTANCE_ID)
            .listenURI(UPDATED_LISTEN_URI)
            .agentType(UPDATED_AGENT_TYPE)
            .agentStatus(UPDATED_AGENT_STATUS)
            .agentDescription(UPDATED_AGENT_DESCRIPTION);
        return agent;
    }

    @BeforeEach
    public void initTest() {
        agent = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgent() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // Create the Agent
        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate + 1);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getAgentName()).isEqualTo(DEFAULT_AGENT_NAME);
        assertThat(testAgent.getAgentInstanceId()).isEqualTo(DEFAULT_AGENT_INSTANCE_ID);
        assertThat(testAgent.getListenURI()).isEqualTo(DEFAULT_LISTEN_URI);
        assertThat(testAgent.getAgentType()).isEqualTo(DEFAULT_AGENT_TYPE);
        assertThat(testAgent.getAgentStatus()).isEqualTo(DEFAULT_AGENT_STATUS);
        assertThat(testAgent.getAgentDescription()).isEqualTo(DEFAULT_AGENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAgentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // Create the Agent with an existing ID
        agent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAgentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setAgentName(null);

        // Create the Agent, which fails.

        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setAgentType(null);

        // Create the Agent, which fails.

        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentRepository.findAll().size();
        // set the field null
        agent.setAgentStatus(null);

        // Create the Agent, which fails.

        restAgentMockMvc.perform(post("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgents() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get all the agentList
        restAgentMockMvc.perform(get("/api/agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent.getId().intValue())))
            .andExpect(jsonPath("$.[*].agentName").value(hasItem(DEFAULT_AGENT_NAME)))
            .andExpect(jsonPath("$.[*].agentInstanceId").value(hasItem(DEFAULT_AGENT_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].listenURI").value(hasItem(DEFAULT_LISTEN_URI)))
            .andExpect(jsonPath("$.[*].agentType").value(hasItem(DEFAULT_AGENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].agentStatus").value(hasItem(DEFAULT_AGENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].agentDescription").value(hasItem(DEFAULT_AGENT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get the agent
        restAgentMockMvc.perform(get("/api/agents/{id}", agent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agent.getId().intValue()))
            .andExpect(jsonPath("$.agentName").value(DEFAULT_AGENT_NAME))
            .andExpect(jsonPath("$.agentInstanceId").value(DEFAULT_AGENT_INSTANCE_ID))
            .andExpect(jsonPath("$.listenURI").value(DEFAULT_LISTEN_URI))
            .andExpect(jsonPath("$.agentType").value(DEFAULT_AGENT_TYPE.toString()))
            .andExpect(jsonPath("$.agentStatus").value(DEFAULT_AGENT_STATUS.toString()))
            .andExpect(jsonPath("$.agentDescription").value(DEFAULT_AGENT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingAgent() throws Exception {
        // Get the agent
        restAgentMockMvc.perform(get("/api/agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgent() throws Exception {
        // Initialize the database
        agentService.save(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent
        Agent updatedAgent = agentRepository.findById(agent.getId()).get();
        // Disconnect from session so that the updates on updatedAgent are not directly saved in db
        em.detach(updatedAgent);
        updatedAgent
            .agentName(UPDATED_AGENT_NAME)
            .agentInstanceId(UPDATED_AGENT_INSTANCE_ID)
            .listenURI(UPDATED_LISTEN_URI)
            .agentType(UPDATED_AGENT_TYPE)
            .agentStatus(UPDATED_AGENT_STATUS)
            .agentDescription(UPDATED_AGENT_DESCRIPTION);

        restAgentMockMvc.perform(put("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgent)))
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getAgentName()).isEqualTo(UPDATED_AGENT_NAME);
        assertThat(testAgent.getAgentInstanceId()).isEqualTo(UPDATED_AGENT_INSTANCE_ID);
        assertThat(testAgent.getListenURI()).isEqualTo(UPDATED_LISTEN_URI);
        assertThat(testAgent.getAgentType()).isEqualTo(UPDATED_AGENT_TYPE);
        assertThat(testAgent.getAgentStatus()).isEqualTo(UPDATED_AGENT_STATUS);
        assertThat(testAgent.getAgentDescription()).isEqualTo(UPDATED_AGENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Create the Agent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc.perform(put("/api/agents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agent)))
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgent() throws Exception {
        // Initialize the database
        agentService.save(agent);

        int databaseSizeBeforeDelete = agentRepository.findAll().size();

        // Delete the agent
        restAgentMockMvc.perform(delete("/api/agents/{id}", agent.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
