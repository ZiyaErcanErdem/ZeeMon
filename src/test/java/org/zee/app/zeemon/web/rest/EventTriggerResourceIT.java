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
import org.zee.app.zeemon.domain.EventTrigger;
import org.zee.app.zeemon.domain.enumeration.TimeUnit;
import org.zee.app.zeemon.domain.enumeration.TriggerType;
import org.zee.app.zeemon.repository.EventTriggerRepository;
import org.zee.app.zeemon.service.EventTriggerService;
import org.zee.app.zeemon.web.rest.EventTriggerResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link EventTriggerResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class EventTriggerResourceIT {

    private static final String DEFAULT_TRIGGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRIGGER_NAME = "BBBBBBBBBB";

    private static final TriggerType DEFAULT_TRIGGER_TYPE = TriggerType.MANUAL;
    private static final TriggerType UPDATED_TRIGGER_TYPE = TriggerType.PERIODIC;

    private static final Long DEFAULT_TRIGGER_PERIOD = 1L;
    private static final Long UPDATED_TRIGGER_PERIOD = 2L;

    private static final TimeUnit DEFAULT_TRIGGER_TIME_UNIT = TimeUnit.SECOND;
    private static final TimeUnit UPDATED_TRIGGER_TIME_UNIT = TimeUnit.MINUTE;

    private static final String DEFAULT_TRIGGER_CRON_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_TRIGGER_CRON_EXPRESSION = "BBBBBBBBBB";

    private static final String DEFAULT_TRIGGER_CRON_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TRIGGER_CRON_TIME_ZONE = "BBBBBBBBBB";

    @Autowired
    private EventTriggerRepository eventTriggerRepository;

    @Autowired
    private EventTriggerService eventTriggerService;

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

    private MockMvc restEventTriggerMockMvc;

    private EventTrigger eventTrigger;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventTriggerResource eventTriggerResource = new EventTriggerResource(eventTriggerService);
        this.restEventTriggerMockMvc = MockMvcBuilders.standaloneSetup(eventTriggerResource)
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
    public static EventTrigger createEntity(EntityManager em) {
        EventTrigger eventTrigger = new EventTrigger()
            .triggerName(DEFAULT_TRIGGER_NAME)
            .triggerType(DEFAULT_TRIGGER_TYPE)
            .triggerPeriod(DEFAULT_TRIGGER_PERIOD)
            .triggerTimeUnit(DEFAULT_TRIGGER_TIME_UNIT)
            .triggerCronExpression(DEFAULT_TRIGGER_CRON_EXPRESSION)
            .triggerCronTimeZone(DEFAULT_TRIGGER_CRON_TIME_ZONE);
        return eventTrigger;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventTrigger createUpdatedEntity(EntityManager em) {
        EventTrigger eventTrigger = new EventTrigger()
            .triggerName(UPDATED_TRIGGER_NAME)
            .triggerType(UPDATED_TRIGGER_TYPE)
            .triggerPeriod(UPDATED_TRIGGER_PERIOD)
            .triggerTimeUnit(UPDATED_TRIGGER_TIME_UNIT)
            .triggerCronExpression(UPDATED_TRIGGER_CRON_EXPRESSION)
            .triggerCronTimeZone(UPDATED_TRIGGER_CRON_TIME_ZONE);
        return eventTrigger;
    }

    @BeforeEach
    public void initTest() {
        eventTrigger = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventTrigger() throws Exception {
        int databaseSizeBeforeCreate = eventTriggerRepository.findAll().size();

        // Create the EventTrigger
        restEventTriggerMockMvc.perform(post("/api/event-triggers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventTrigger)))
            .andExpect(status().isCreated());

        // Validate the EventTrigger in the database
        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeCreate + 1);
        EventTrigger testEventTrigger = eventTriggerList.get(eventTriggerList.size() - 1);
        assertThat(testEventTrigger.getTriggerName()).isEqualTo(DEFAULT_TRIGGER_NAME);
        assertThat(testEventTrigger.getTriggerType()).isEqualTo(DEFAULT_TRIGGER_TYPE);
        assertThat(testEventTrigger.getTriggerPeriod()).isEqualTo(DEFAULT_TRIGGER_PERIOD);
        assertThat(testEventTrigger.getTriggerTimeUnit()).isEqualTo(DEFAULT_TRIGGER_TIME_UNIT);
        assertThat(testEventTrigger.getTriggerCronExpression()).isEqualTo(DEFAULT_TRIGGER_CRON_EXPRESSION);
        assertThat(testEventTrigger.getTriggerCronTimeZone()).isEqualTo(DEFAULT_TRIGGER_CRON_TIME_ZONE);
    }

    @Test
    @Transactional
    public void createEventTriggerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventTriggerRepository.findAll().size();

        // Create the EventTrigger with an existing ID
        eventTrigger.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventTriggerMockMvc.perform(post("/api/event-triggers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventTrigger)))
            .andExpect(status().isBadRequest());

        // Validate the EventTrigger in the database
        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTriggerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventTriggerRepository.findAll().size();
        // set the field null
        eventTrigger.setTriggerName(null);

        // Create the EventTrigger, which fails.

        restEventTriggerMockMvc.perform(post("/api/event-triggers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventTrigger)))
            .andExpect(status().isBadRequest());

        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTriggerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventTriggerRepository.findAll().size();
        // set the field null
        eventTrigger.setTriggerType(null);

        // Create the EventTrigger, which fails.

        restEventTriggerMockMvc.perform(post("/api/event-triggers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventTrigger)))
            .andExpect(status().isBadRequest());

        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventTriggers() throws Exception {
        // Initialize the database
        eventTriggerRepository.saveAndFlush(eventTrigger);

        // Get all the eventTriggerList
        restEventTriggerMockMvc.perform(get("/api/event-triggers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventTrigger.getId().intValue())))
            .andExpect(jsonPath("$.[*].triggerName").value(hasItem(DEFAULT_TRIGGER_NAME)))
            .andExpect(jsonPath("$.[*].triggerType").value(hasItem(DEFAULT_TRIGGER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].triggerPeriod").value(hasItem(DEFAULT_TRIGGER_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].triggerTimeUnit").value(hasItem(DEFAULT_TRIGGER_TIME_UNIT.toString())))
            .andExpect(jsonPath("$.[*].triggerCronExpression").value(hasItem(DEFAULT_TRIGGER_CRON_EXPRESSION)))
            .andExpect(jsonPath("$.[*].triggerCronTimeZone").value(hasItem(DEFAULT_TRIGGER_CRON_TIME_ZONE)));
    }
    
    @Test
    @Transactional
    public void getEventTrigger() throws Exception {
        // Initialize the database
        eventTriggerRepository.saveAndFlush(eventTrigger);

        // Get the eventTrigger
        restEventTriggerMockMvc.perform(get("/api/event-triggers/{id}", eventTrigger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventTrigger.getId().intValue()))
            .andExpect(jsonPath("$.triggerName").value(DEFAULT_TRIGGER_NAME))
            .andExpect(jsonPath("$.triggerType").value(DEFAULT_TRIGGER_TYPE.toString()))
            .andExpect(jsonPath("$.triggerPeriod").value(DEFAULT_TRIGGER_PERIOD.intValue()))
            .andExpect(jsonPath("$.triggerTimeUnit").value(DEFAULT_TRIGGER_TIME_UNIT.toString()))
            .andExpect(jsonPath("$.triggerCronExpression").value(DEFAULT_TRIGGER_CRON_EXPRESSION))
            .andExpect(jsonPath("$.triggerCronTimeZone").value(DEFAULT_TRIGGER_CRON_TIME_ZONE));
    }

    @Test
    @Transactional
    public void getNonExistingEventTrigger() throws Exception {
        // Get the eventTrigger
        restEventTriggerMockMvc.perform(get("/api/event-triggers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventTrigger() throws Exception {
        // Initialize the database
        eventTriggerService.save(eventTrigger);

        int databaseSizeBeforeUpdate = eventTriggerRepository.findAll().size();

        // Update the eventTrigger
        EventTrigger updatedEventTrigger = eventTriggerRepository.findById(eventTrigger.getId()).get();
        // Disconnect from session so that the updates on updatedEventTrigger are not directly saved in db
        em.detach(updatedEventTrigger);
        updatedEventTrigger
            .triggerName(UPDATED_TRIGGER_NAME)
            .triggerType(UPDATED_TRIGGER_TYPE)
            .triggerPeriod(UPDATED_TRIGGER_PERIOD)
            .triggerTimeUnit(UPDATED_TRIGGER_TIME_UNIT)
            .triggerCronExpression(UPDATED_TRIGGER_CRON_EXPRESSION)
            .triggerCronTimeZone(UPDATED_TRIGGER_CRON_TIME_ZONE);

        restEventTriggerMockMvc.perform(put("/api/event-triggers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventTrigger)))
            .andExpect(status().isOk());

        // Validate the EventTrigger in the database
        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeUpdate);
        EventTrigger testEventTrigger = eventTriggerList.get(eventTriggerList.size() - 1);
        assertThat(testEventTrigger.getTriggerName()).isEqualTo(UPDATED_TRIGGER_NAME);
        assertThat(testEventTrigger.getTriggerType()).isEqualTo(UPDATED_TRIGGER_TYPE);
        assertThat(testEventTrigger.getTriggerPeriod()).isEqualTo(UPDATED_TRIGGER_PERIOD);
        assertThat(testEventTrigger.getTriggerTimeUnit()).isEqualTo(UPDATED_TRIGGER_TIME_UNIT);
        assertThat(testEventTrigger.getTriggerCronExpression()).isEqualTo(UPDATED_TRIGGER_CRON_EXPRESSION);
        assertThat(testEventTrigger.getTriggerCronTimeZone()).isEqualTo(UPDATED_TRIGGER_CRON_TIME_ZONE);
    }

    @Test
    @Transactional
    public void updateNonExistingEventTrigger() throws Exception {
        int databaseSizeBeforeUpdate = eventTriggerRepository.findAll().size();

        // Create the EventTrigger

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventTriggerMockMvc.perform(put("/api/event-triggers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventTrigger)))
            .andExpect(status().isBadRequest());

        // Validate the EventTrigger in the database
        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventTrigger() throws Exception {
        // Initialize the database
        eventTriggerService.save(eventTrigger);

        int databaseSizeBeforeDelete = eventTriggerRepository.findAll().size();

        // Delete the eventTrigger
        restEventTriggerMockMvc.perform(delete("/api/event-triggers/{id}", eventTrigger.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventTrigger> eventTriggerList = eventTriggerRepository.findAll();
        assertThat(eventTriggerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
