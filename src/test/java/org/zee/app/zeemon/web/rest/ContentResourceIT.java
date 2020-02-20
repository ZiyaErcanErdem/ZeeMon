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
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.repository.ContentRepository;
import org.zee.app.zeemon.service.ContentService;
import org.zee.app.zeemon.web.rest.ContentResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContentResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ContentResourceIT {

    private static final Integer DEFAULT_SOURCE_INDEX = 0;
    private static final Integer UPDATED_SOURCE_INDEX = 1;

    private static final String DEFAULT_TXT_1 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_2 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_3 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_4 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_4 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_5 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_5 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_6 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_6 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_7 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_7 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_8 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_8 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_9 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_9 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_10 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_10 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_11 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_11 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_12 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_12 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_13 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_13 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_14 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_14 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_15 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_15 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_16 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_16 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_17 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_17 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_18 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_18 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_19 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_19 = "BBBBBBBBBB";

    private static final String DEFAULT_TXT_20 = "AAAAAAAAAA";
    private static final String UPDATED_TXT_20 = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_NUM_1 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_1 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_2 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_3 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_3 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_4 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_4 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_5 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_5 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_6 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_6 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_7 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_7 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_8 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_8 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_9 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_9 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_10 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_10 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_11 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_11 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_12 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_12 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_13 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_13 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_14 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_14 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_15 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_15 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_16 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_16 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_17 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_17 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_18 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_18 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_19 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_19 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NUM_20 = new BigDecimal(1);
    private static final BigDecimal UPDATED_NUM_20 = new BigDecimal(2);

    private static final Instant DEFAULT_DATE_1 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_1 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_2 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_2 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_3 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_3 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_4 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_4 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_5 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_5 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_6 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_6 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_7 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_7 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_8 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_8 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_9 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_9 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_10 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_10 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOOL_1 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOL_1 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOOL_2 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOL_2 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOOL_3 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOL_3 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOOL_4 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOL_4 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOOL_5 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOL_5 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentService contentService;

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

    private MockMvc restContentMockMvc;

    private Content content;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentResource contentResource = new ContentResource(contentService);
        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(contentResource)
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
    public static Content createEntity(EntityManager em) {
        Content content = new Content()
            .sourceIndex(DEFAULT_SOURCE_INDEX)
            .txt1(DEFAULT_TXT_1)
            .txt2(DEFAULT_TXT_2)
            .txt3(DEFAULT_TXT_3)
            .txt4(DEFAULT_TXT_4)
            .txt5(DEFAULT_TXT_5)
            .txt6(DEFAULT_TXT_6)
            .txt7(DEFAULT_TXT_7)
            .txt8(DEFAULT_TXT_8)
            .txt9(DEFAULT_TXT_9)
            .txt10(DEFAULT_TXT_10)
            .txt11(DEFAULT_TXT_11)
            .txt12(DEFAULT_TXT_12)
            .txt13(DEFAULT_TXT_13)
            .txt14(DEFAULT_TXT_14)
            .txt15(DEFAULT_TXT_15)
            .txt16(DEFAULT_TXT_16)
            .txt17(DEFAULT_TXT_17)
            .txt18(DEFAULT_TXT_18)
            .txt19(DEFAULT_TXT_19)
            .txt20(DEFAULT_TXT_20)
            .num1(DEFAULT_NUM_1)
            .num2(DEFAULT_NUM_2)
            .num3(DEFAULT_NUM_3)
            .num4(DEFAULT_NUM_4)
            .num5(DEFAULT_NUM_5)
            .num6(DEFAULT_NUM_6)
            .num7(DEFAULT_NUM_7)
            .num8(DEFAULT_NUM_8)
            .num9(DEFAULT_NUM_9)
            .num10(DEFAULT_NUM_10)
            .num11(DEFAULT_NUM_11)
            .num12(DEFAULT_NUM_12)
            .num13(DEFAULT_NUM_13)
            .num14(DEFAULT_NUM_14)
            .num15(DEFAULT_NUM_15)
            .num16(DEFAULT_NUM_16)
            .num17(DEFAULT_NUM_17)
            .num18(DEFAULT_NUM_18)
            .num19(DEFAULT_NUM_19)
            .num20(DEFAULT_NUM_20)
            .date1(DEFAULT_DATE_1)
            .date2(DEFAULT_DATE_2)
            .date3(DEFAULT_DATE_3)
            .date4(DEFAULT_DATE_4)
            .date5(DEFAULT_DATE_5)
            .date6(DEFAULT_DATE_6)
            .date7(DEFAULT_DATE_7)
            .date8(DEFAULT_DATE_8)
            .date9(DEFAULT_DATE_9)
            .date10(DEFAULT_DATE_10)
            .bool1(DEFAULT_BOOL_1)
            .bool2(DEFAULT_BOOL_2)
            .bool3(DEFAULT_BOOL_3)
            .bool4(DEFAULT_BOOL_4)
            .bool5(DEFAULT_BOOL_5);
        // Add required entity
        CheckScript checkScript;
        if (TestUtil.findAll(em, CheckScript.class).isEmpty()) {
            checkScript = CheckScriptResourceIT.createEntity(em);
            em.persist(checkScript);
            em.flush();
        } else {
            checkScript = TestUtil.findAll(em, CheckScript.class).get(0);
        }
        content.setCheckScript(checkScript);
        // Add required entity
        Flow flow;
        if (TestUtil.findAll(em, Flow.class).isEmpty()) {
            flow = FlowResourceIT.createEntity(em);
            em.persist(flow);
            em.flush();
        } else {
            flow = TestUtil.findAll(em, Flow.class).get(0);
        }
        content.setFlow(flow);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        content.setTask(task);
        // Add required entity
        TaskExecution taskExecution;
        if (TestUtil.findAll(em, TaskExecution.class).isEmpty()) {
            taskExecution = TaskExecutionResourceIT.createEntity(em);
            em.persist(taskExecution);
            em.flush();
        } else {
            taskExecution = TestUtil.findAll(em, TaskExecution.class).get(0);
        }
        content.setTaskExecution(taskExecution);
        // Add required entity
        FlowExecution flowExecution;
        if (TestUtil.findAll(em, FlowExecution.class).isEmpty()) {
            flowExecution = FlowExecutionResourceIT.createEntity(em);
            em.persist(flowExecution);
            em.flush();
        } else {
            flowExecution = TestUtil.findAll(em, FlowExecution.class).get(0);
        }
        content.setFlowExecution(flowExecution);
        return content;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Content createUpdatedEntity(EntityManager em) {
        Content content = new Content()
            .sourceIndex(UPDATED_SOURCE_INDEX)
            .txt1(UPDATED_TXT_1)
            .txt2(UPDATED_TXT_2)
            .txt3(UPDATED_TXT_3)
            .txt4(UPDATED_TXT_4)
            .txt5(UPDATED_TXT_5)
            .txt6(UPDATED_TXT_6)
            .txt7(UPDATED_TXT_7)
            .txt8(UPDATED_TXT_8)
            .txt9(UPDATED_TXT_9)
            .txt10(UPDATED_TXT_10)
            .txt11(UPDATED_TXT_11)
            .txt12(UPDATED_TXT_12)
            .txt13(UPDATED_TXT_13)
            .txt14(UPDATED_TXT_14)
            .txt15(UPDATED_TXT_15)
            .txt16(UPDATED_TXT_16)
            .txt17(UPDATED_TXT_17)
            .txt18(UPDATED_TXT_18)
            .txt19(UPDATED_TXT_19)
            .txt20(UPDATED_TXT_20)
            .num1(UPDATED_NUM_1)
            .num2(UPDATED_NUM_2)
            .num3(UPDATED_NUM_3)
            .num4(UPDATED_NUM_4)
            .num5(UPDATED_NUM_5)
            .num6(UPDATED_NUM_6)
            .num7(UPDATED_NUM_7)
            .num8(UPDATED_NUM_8)
            .num9(UPDATED_NUM_9)
            .num10(UPDATED_NUM_10)
            .num11(UPDATED_NUM_11)
            .num12(UPDATED_NUM_12)
            .num13(UPDATED_NUM_13)
            .num14(UPDATED_NUM_14)
            .num15(UPDATED_NUM_15)
            .num16(UPDATED_NUM_16)
            .num17(UPDATED_NUM_17)
            .num18(UPDATED_NUM_18)
            .num19(UPDATED_NUM_19)
            .num20(UPDATED_NUM_20)
            .date1(UPDATED_DATE_1)
            .date2(UPDATED_DATE_2)
            .date3(UPDATED_DATE_3)
            .date4(UPDATED_DATE_4)
            .date5(UPDATED_DATE_5)
            .date6(UPDATED_DATE_6)
            .date7(UPDATED_DATE_7)
            .date8(UPDATED_DATE_8)
            .date9(UPDATED_DATE_9)
            .date10(UPDATED_DATE_10)
            .bool1(UPDATED_BOOL_1)
            .bool2(UPDATED_BOOL_2)
            .bool3(UPDATED_BOOL_3)
            .bool4(UPDATED_BOOL_4)
            .bool5(UPDATED_BOOL_5);
        // Add required entity
        CheckScript checkScript;
        if (TestUtil.findAll(em, CheckScript.class).isEmpty()) {
            checkScript = CheckScriptResourceIT.createUpdatedEntity(em);
            em.persist(checkScript);
            em.flush();
        } else {
            checkScript = TestUtil.findAll(em, CheckScript.class).get(0);
        }
        content.setCheckScript(checkScript);
        // Add required entity
        Flow flow;
        if (TestUtil.findAll(em, Flow.class).isEmpty()) {
            flow = FlowResourceIT.createUpdatedEntity(em);
            em.persist(flow);
            em.flush();
        } else {
            flow = TestUtil.findAll(em, Flow.class).get(0);
        }
        content.setFlow(flow);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createUpdatedEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        content.setTask(task);
        // Add required entity
        TaskExecution taskExecution;
        if (TestUtil.findAll(em, TaskExecution.class).isEmpty()) {
            taskExecution = TaskExecutionResourceIT.createUpdatedEntity(em);
            em.persist(taskExecution);
            em.flush();
        } else {
            taskExecution = TestUtil.findAll(em, TaskExecution.class).get(0);
        }
        content.setTaskExecution(taskExecution);
        // Add required entity
        FlowExecution flowExecution;
        if (TestUtil.findAll(em, FlowExecution.class).isEmpty()) {
            flowExecution = FlowExecutionResourceIT.createUpdatedEntity(em);
            em.persist(flowExecution);
            em.flush();
        } else {
            flowExecution = TestUtil.findAll(em, FlowExecution.class).get(0);
        }
        content.setFlowExecution(flowExecution);
        return content;
    }

    @BeforeEach
    public void initTest() {
        content = createEntity(em);
    }

    @Test
    @Transactional
    public void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getSourceIndex()).isEqualTo(DEFAULT_SOURCE_INDEX);
        assertThat(testContent.getTxt1()).isEqualTo(DEFAULT_TXT_1);
        assertThat(testContent.getTxt2()).isEqualTo(DEFAULT_TXT_2);
        assertThat(testContent.getTxt3()).isEqualTo(DEFAULT_TXT_3);
        assertThat(testContent.getTxt4()).isEqualTo(DEFAULT_TXT_4);
        assertThat(testContent.getTxt5()).isEqualTo(DEFAULT_TXT_5);
        assertThat(testContent.getTxt6()).isEqualTo(DEFAULT_TXT_6);
        assertThat(testContent.getTxt7()).isEqualTo(DEFAULT_TXT_7);
        assertThat(testContent.getTxt8()).isEqualTo(DEFAULT_TXT_8);
        assertThat(testContent.getTxt9()).isEqualTo(DEFAULT_TXT_9);
        assertThat(testContent.getTxt10()).isEqualTo(DEFAULT_TXT_10);
        assertThat(testContent.getTxt11()).isEqualTo(DEFAULT_TXT_11);
        assertThat(testContent.getTxt12()).isEqualTo(DEFAULT_TXT_12);
        assertThat(testContent.getTxt13()).isEqualTo(DEFAULT_TXT_13);
        assertThat(testContent.getTxt14()).isEqualTo(DEFAULT_TXT_14);
        assertThat(testContent.getTxt15()).isEqualTo(DEFAULT_TXT_15);
        assertThat(testContent.getTxt16()).isEqualTo(DEFAULT_TXT_16);
        assertThat(testContent.getTxt17()).isEqualTo(DEFAULT_TXT_17);
        assertThat(testContent.getTxt18()).isEqualTo(DEFAULT_TXT_18);
        assertThat(testContent.getTxt19()).isEqualTo(DEFAULT_TXT_19);
        assertThat(testContent.getTxt20()).isEqualTo(DEFAULT_TXT_20);
        assertThat(testContent.getNum1()).isEqualTo(DEFAULT_NUM_1);
        assertThat(testContent.getNum2()).isEqualTo(DEFAULT_NUM_2);
        assertThat(testContent.getNum3()).isEqualTo(DEFAULT_NUM_3);
        assertThat(testContent.getNum4()).isEqualTo(DEFAULT_NUM_4);
        assertThat(testContent.getNum5()).isEqualTo(DEFAULT_NUM_5);
        assertThat(testContent.getNum6()).isEqualTo(DEFAULT_NUM_6);
        assertThat(testContent.getNum7()).isEqualTo(DEFAULT_NUM_7);
        assertThat(testContent.getNum8()).isEqualTo(DEFAULT_NUM_8);
        assertThat(testContent.getNum9()).isEqualTo(DEFAULT_NUM_9);
        assertThat(testContent.getNum10()).isEqualTo(DEFAULT_NUM_10);
        assertThat(testContent.getNum11()).isEqualTo(DEFAULT_NUM_11);
        assertThat(testContent.getNum12()).isEqualTo(DEFAULT_NUM_12);
        assertThat(testContent.getNum13()).isEqualTo(DEFAULT_NUM_13);
        assertThat(testContent.getNum14()).isEqualTo(DEFAULT_NUM_14);
        assertThat(testContent.getNum15()).isEqualTo(DEFAULT_NUM_15);
        assertThat(testContent.getNum16()).isEqualTo(DEFAULT_NUM_16);
        assertThat(testContent.getNum17()).isEqualTo(DEFAULT_NUM_17);
        assertThat(testContent.getNum18()).isEqualTo(DEFAULT_NUM_18);
        assertThat(testContent.getNum19()).isEqualTo(DEFAULT_NUM_19);
        assertThat(testContent.getNum20()).isEqualTo(DEFAULT_NUM_20);
        assertThat(testContent.getDate1()).isEqualTo(DEFAULT_DATE_1);
        assertThat(testContent.getDate2()).isEqualTo(DEFAULT_DATE_2);
        assertThat(testContent.getDate3()).isEqualTo(DEFAULT_DATE_3);
        assertThat(testContent.getDate4()).isEqualTo(DEFAULT_DATE_4);
        assertThat(testContent.getDate5()).isEqualTo(DEFAULT_DATE_5);
        assertThat(testContent.getDate6()).isEqualTo(DEFAULT_DATE_6);
        assertThat(testContent.getDate7()).isEqualTo(DEFAULT_DATE_7);
        assertThat(testContent.getDate8()).isEqualTo(DEFAULT_DATE_8);
        assertThat(testContent.getDate9()).isEqualTo(DEFAULT_DATE_9);
        assertThat(testContent.getDate10()).isEqualTo(DEFAULT_DATE_10);
        assertThat(testContent.getBool1()).isEqualTo(DEFAULT_BOOL_1);
        assertThat(testContent.getBool2()).isEqualTo(DEFAULT_BOOL_2);
        assertThat(testContent.getBool3()).isEqualTo(DEFAULT_BOOL_3);
        assertThat(testContent.getBool4()).isEqualTo(DEFAULT_BOOL_4);
        assertThat(testContent.getBool5()).isEqualTo(DEFAULT_BOOL_5);
    }

    @Test
    @Transactional
    public void createContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content with an existing ID
        content.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSourceIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setSourceIndex(null);

        // Create the Content, which fails.

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contentList
        restContentMockMvc.perform(get("/api/contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceIndex").value(hasItem(DEFAULT_SOURCE_INDEX)))
            .andExpect(jsonPath("$.[*].txt1").value(hasItem(DEFAULT_TXT_1)))
            .andExpect(jsonPath("$.[*].txt2").value(hasItem(DEFAULT_TXT_2)))
            .andExpect(jsonPath("$.[*].txt3").value(hasItem(DEFAULT_TXT_3)))
            .andExpect(jsonPath("$.[*].txt4").value(hasItem(DEFAULT_TXT_4)))
            .andExpect(jsonPath("$.[*].txt5").value(hasItem(DEFAULT_TXT_5)))
            .andExpect(jsonPath("$.[*].txt6").value(hasItem(DEFAULT_TXT_6)))
            .andExpect(jsonPath("$.[*].txt7").value(hasItem(DEFAULT_TXT_7)))
            .andExpect(jsonPath("$.[*].txt8").value(hasItem(DEFAULT_TXT_8)))
            .andExpect(jsonPath("$.[*].txt9").value(hasItem(DEFAULT_TXT_9)))
            .andExpect(jsonPath("$.[*].txt10").value(hasItem(DEFAULT_TXT_10)))
            .andExpect(jsonPath("$.[*].txt11").value(hasItem(DEFAULT_TXT_11)))
            .andExpect(jsonPath("$.[*].txt12").value(hasItem(DEFAULT_TXT_12)))
            .andExpect(jsonPath("$.[*].txt13").value(hasItem(DEFAULT_TXT_13)))
            .andExpect(jsonPath("$.[*].txt14").value(hasItem(DEFAULT_TXT_14)))
            .andExpect(jsonPath("$.[*].txt15").value(hasItem(DEFAULT_TXT_15)))
            .andExpect(jsonPath("$.[*].txt16").value(hasItem(DEFAULT_TXT_16)))
            .andExpect(jsonPath("$.[*].txt17").value(hasItem(DEFAULT_TXT_17)))
            .andExpect(jsonPath("$.[*].txt18").value(hasItem(DEFAULT_TXT_18)))
            .andExpect(jsonPath("$.[*].txt19").value(hasItem(DEFAULT_TXT_19)))
            .andExpect(jsonPath("$.[*].txt20").value(hasItem(DEFAULT_TXT_20)))
            .andExpect(jsonPath("$.[*].num1").value(hasItem(DEFAULT_NUM_1.intValue())))
            .andExpect(jsonPath("$.[*].num2").value(hasItem(DEFAULT_NUM_2.intValue())))
            .andExpect(jsonPath("$.[*].num3").value(hasItem(DEFAULT_NUM_3.intValue())))
            .andExpect(jsonPath("$.[*].num4").value(hasItem(DEFAULT_NUM_4.intValue())))
            .andExpect(jsonPath("$.[*].num5").value(hasItem(DEFAULT_NUM_5.intValue())))
            .andExpect(jsonPath("$.[*].num6").value(hasItem(DEFAULT_NUM_6.intValue())))
            .andExpect(jsonPath("$.[*].num7").value(hasItem(DEFAULT_NUM_7.intValue())))
            .andExpect(jsonPath("$.[*].num8").value(hasItem(DEFAULT_NUM_8.intValue())))
            .andExpect(jsonPath("$.[*].num9").value(hasItem(DEFAULT_NUM_9.intValue())))
            .andExpect(jsonPath("$.[*].num10").value(hasItem(DEFAULT_NUM_10.intValue())))
            .andExpect(jsonPath("$.[*].num11").value(hasItem(DEFAULT_NUM_11.intValue())))
            .andExpect(jsonPath("$.[*].num12").value(hasItem(DEFAULT_NUM_12.intValue())))
            .andExpect(jsonPath("$.[*].num13").value(hasItem(DEFAULT_NUM_13.intValue())))
            .andExpect(jsonPath("$.[*].num14").value(hasItem(DEFAULT_NUM_14.intValue())))
            .andExpect(jsonPath("$.[*].num15").value(hasItem(DEFAULT_NUM_15.intValue())))
            .andExpect(jsonPath("$.[*].num16").value(hasItem(DEFAULT_NUM_16.intValue())))
            .andExpect(jsonPath("$.[*].num17").value(hasItem(DEFAULT_NUM_17.intValue())))
            .andExpect(jsonPath("$.[*].num18").value(hasItem(DEFAULT_NUM_18.intValue())))
            .andExpect(jsonPath("$.[*].num19").value(hasItem(DEFAULT_NUM_19.intValue())))
            .andExpect(jsonPath("$.[*].num20").value(hasItem(DEFAULT_NUM_20.intValue())))
            .andExpect(jsonPath("$.[*].date1").value(hasItem(DEFAULT_DATE_1.toString())))
            .andExpect(jsonPath("$.[*].date2").value(hasItem(DEFAULT_DATE_2.toString())))
            .andExpect(jsonPath("$.[*].date3").value(hasItem(DEFAULT_DATE_3.toString())))
            .andExpect(jsonPath("$.[*].date4").value(hasItem(DEFAULT_DATE_4.toString())))
            .andExpect(jsonPath("$.[*].date5").value(hasItem(DEFAULT_DATE_5.toString())))
            .andExpect(jsonPath("$.[*].date6").value(hasItem(DEFAULT_DATE_6.toString())))
            .andExpect(jsonPath("$.[*].date7").value(hasItem(DEFAULT_DATE_7.toString())))
            .andExpect(jsonPath("$.[*].date8").value(hasItem(DEFAULT_DATE_8.toString())))
            .andExpect(jsonPath("$.[*].date9").value(hasItem(DEFAULT_DATE_9.toString())))
            .andExpect(jsonPath("$.[*].date10").value(hasItem(DEFAULT_DATE_10.toString())))
            .andExpect(jsonPath("$.[*].bool1").value(hasItem(DEFAULT_BOOL_1.toString())))
            .andExpect(jsonPath("$.[*].bool2").value(hasItem(DEFAULT_BOOL_2.toString())))
            .andExpect(jsonPath("$.[*].bool3").value(hasItem(DEFAULT_BOOL_3.toString())))
            .andExpect(jsonPath("$.[*].bool4").value(hasItem(DEFAULT_BOOL_4.toString())))
            .andExpect(jsonPath("$.[*].bool5").value(hasItem(DEFAULT_BOOL_5.toString())));
    }
    
    @Test
    @Transactional
    public void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.sourceIndex").value(DEFAULT_SOURCE_INDEX))
            .andExpect(jsonPath("$.txt1").value(DEFAULT_TXT_1))
            .andExpect(jsonPath("$.txt2").value(DEFAULT_TXT_2))
            .andExpect(jsonPath("$.txt3").value(DEFAULT_TXT_3))
            .andExpect(jsonPath("$.txt4").value(DEFAULT_TXT_4))
            .andExpect(jsonPath("$.txt5").value(DEFAULT_TXT_5))
            .andExpect(jsonPath("$.txt6").value(DEFAULT_TXT_6))
            .andExpect(jsonPath("$.txt7").value(DEFAULT_TXT_7))
            .andExpect(jsonPath("$.txt8").value(DEFAULT_TXT_8))
            .andExpect(jsonPath("$.txt9").value(DEFAULT_TXT_9))
            .andExpect(jsonPath("$.txt10").value(DEFAULT_TXT_10))
            .andExpect(jsonPath("$.txt11").value(DEFAULT_TXT_11))
            .andExpect(jsonPath("$.txt12").value(DEFAULT_TXT_12))
            .andExpect(jsonPath("$.txt13").value(DEFAULT_TXT_13))
            .andExpect(jsonPath("$.txt14").value(DEFAULT_TXT_14))
            .andExpect(jsonPath("$.txt15").value(DEFAULT_TXT_15))
            .andExpect(jsonPath("$.txt16").value(DEFAULT_TXT_16))
            .andExpect(jsonPath("$.txt17").value(DEFAULT_TXT_17))
            .andExpect(jsonPath("$.txt18").value(DEFAULT_TXT_18))
            .andExpect(jsonPath("$.txt19").value(DEFAULT_TXT_19))
            .andExpect(jsonPath("$.txt20").value(DEFAULT_TXT_20))
            .andExpect(jsonPath("$.num1").value(DEFAULT_NUM_1.intValue()))
            .andExpect(jsonPath("$.num2").value(DEFAULT_NUM_2.intValue()))
            .andExpect(jsonPath("$.num3").value(DEFAULT_NUM_3.intValue()))
            .andExpect(jsonPath("$.num4").value(DEFAULT_NUM_4.intValue()))
            .andExpect(jsonPath("$.num5").value(DEFAULT_NUM_5.intValue()))
            .andExpect(jsonPath("$.num6").value(DEFAULT_NUM_6.intValue()))
            .andExpect(jsonPath("$.num7").value(DEFAULT_NUM_7.intValue()))
            .andExpect(jsonPath("$.num8").value(DEFAULT_NUM_8.intValue()))
            .andExpect(jsonPath("$.num9").value(DEFAULT_NUM_9.intValue()))
            .andExpect(jsonPath("$.num10").value(DEFAULT_NUM_10.intValue()))
            .andExpect(jsonPath("$.num11").value(DEFAULT_NUM_11.intValue()))
            .andExpect(jsonPath("$.num12").value(DEFAULT_NUM_12.intValue()))
            .andExpect(jsonPath("$.num13").value(DEFAULT_NUM_13.intValue()))
            .andExpect(jsonPath("$.num14").value(DEFAULT_NUM_14.intValue()))
            .andExpect(jsonPath("$.num15").value(DEFAULT_NUM_15.intValue()))
            .andExpect(jsonPath("$.num16").value(DEFAULT_NUM_16.intValue()))
            .andExpect(jsonPath("$.num17").value(DEFAULT_NUM_17.intValue()))
            .andExpect(jsonPath("$.num18").value(DEFAULT_NUM_18.intValue()))
            .andExpect(jsonPath("$.num19").value(DEFAULT_NUM_19.intValue()))
            .andExpect(jsonPath("$.num20").value(DEFAULT_NUM_20.intValue()))
            .andExpect(jsonPath("$.date1").value(DEFAULT_DATE_1.toString()))
            .andExpect(jsonPath("$.date2").value(DEFAULT_DATE_2.toString()))
            .andExpect(jsonPath("$.date3").value(DEFAULT_DATE_3.toString()))
            .andExpect(jsonPath("$.date4").value(DEFAULT_DATE_4.toString()))
            .andExpect(jsonPath("$.date5").value(DEFAULT_DATE_5.toString()))
            .andExpect(jsonPath("$.date6").value(DEFAULT_DATE_6.toString()))
            .andExpect(jsonPath("$.date7").value(DEFAULT_DATE_7.toString()))
            .andExpect(jsonPath("$.date8").value(DEFAULT_DATE_8.toString()))
            .andExpect(jsonPath("$.date9").value(DEFAULT_DATE_9.toString()))
            .andExpect(jsonPath("$.date10").value(DEFAULT_DATE_10.toString()))
            .andExpect(jsonPath("$.bool1").value(DEFAULT_BOOL_1.toString()))
            .andExpect(jsonPath("$.bool2").value(DEFAULT_BOOL_2.toString()))
            .andExpect(jsonPath("$.bool3").value(DEFAULT_BOOL_3.toString()))
            .andExpect(jsonPath("$.bool4").value(DEFAULT_BOOL_4.toString()))
            .andExpect(jsonPath("$.bool5").value(DEFAULT_BOOL_5.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContent() throws Exception {
        // Initialize the database
        contentService.save(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        Content updatedContent = contentRepository.findById(content.getId()).get();
        // Disconnect from session so that the updates on updatedContent are not directly saved in db
        em.detach(updatedContent);
        updatedContent
            .sourceIndex(UPDATED_SOURCE_INDEX)
            .txt1(UPDATED_TXT_1)
            .txt2(UPDATED_TXT_2)
            .txt3(UPDATED_TXT_3)
            .txt4(UPDATED_TXT_4)
            .txt5(UPDATED_TXT_5)
            .txt6(UPDATED_TXT_6)
            .txt7(UPDATED_TXT_7)
            .txt8(UPDATED_TXT_8)
            .txt9(UPDATED_TXT_9)
            .txt10(UPDATED_TXT_10)
            .txt11(UPDATED_TXT_11)
            .txt12(UPDATED_TXT_12)
            .txt13(UPDATED_TXT_13)
            .txt14(UPDATED_TXT_14)
            .txt15(UPDATED_TXT_15)
            .txt16(UPDATED_TXT_16)
            .txt17(UPDATED_TXT_17)
            .txt18(UPDATED_TXT_18)
            .txt19(UPDATED_TXT_19)
            .txt20(UPDATED_TXT_20)
            .num1(UPDATED_NUM_1)
            .num2(UPDATED_NUM_2)
            .num3(UPDATED_NUM_3)
            .num4(UPDATED_NUM_4)
            .num5(UPDATED_NUM_5)
            .num6(UPDATED_NUM_6)
            .num7(UPDATED_NUM_7)
            .num8(UPDATED_NUM_8)
            .num9(UPDATED_NUM_9)
            .num10(UPDATED_NUM_10)
            .num11(UPDATED_NUM_11)
            .num12(UPDATED_NUM_12)
            .num13(UPDATED_NUM_13)
            .num14(UPDATED_NUM_14)
            .num15(UPDATED_NUM_15)
            .num16(UPDATED_NUM_16)
            .num17(UPDATED_NUM_17)
            .num18(UPDATED_NUM_18)
            .num19(UPDATED_NUM_19)
            .num20(UPDATED_NUM_20)
            .date1(UPDATED_DATE_1)
            .date2(UPDATED_DATE_2)
            .date3(UPDATED_DATE_3)
            .date4(UPDATED_DATE_4)
            .date5(UPDATED_DATE_5)
            .date6(UPDATED_DATE_6)
            .date7(UPDATED_DATE_7)
            .date8(UPDATED_DATE_8)
            .date9(UPDATED_DATE_9)
            .date10(UPDATED_DATE_10)
            .bool1(UPDATED_BOOL_1)
            .bool2(UPDATED_BOOL_2)
            .bool3(UPDATED_BOOL_3)
            .bool4(UPDATED_BOOL_4)
            .bool5(UPDATED_BOOL_5);

        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContent)))
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getSourceIndex()).isEqualTo(UPDATED_SOURCE_INDEX);
        assertThat(testContent.getTxt1()).isEqualTo(UPDATED_TXT_1);
        assertThat(testContent.getTxt2()).isEqualTo(UPDATED_TXT_2);
        assertThat(testContent.getTxt3()).isEqualTo(UPDATED_TXT_3);
        assertThat(testContent.getTxt4()).isEqualTo(UPDATED_TXT_4);
        assertThat(testContent.getTxt5()).isEqualTo(UPDATED_TXT_5);
        assertThat(testContent.getTxt6()).isEqualTo(UPDATED_TXT_6);
        assertThat(testContent.getTxt7()).isEqualTo(UPDATED_TXT_7);
        assertThat(testContent.getTxt8()).isEqualTo(UPDATED_TXT_8);
        assertThat(testContent.getTxt9()).isEqualTo(UPDATED_TXT_9);
        assertThat(testContent.getTxt10()).isEqualTo(UPDATED_TXT_10);
        assertThat(testContent.getTxt11()).isEqualTo(UPDATED_TXT_11);
        assertThat(testContent.getTxt12()).isEqualTo(UPDATED_TXT_12);
        assertThat(testContent.getTxt13()).isEqualTo(UPDATED_TXT_13);
        assertThat(testContent.getTxt14()).isEqualTo(UPDATED_TXT_14);
        assertThat(testContent.getTxt15()).isEqualTo(UPDATED_TXT_15);
        assertThat(testContent.getTxt16()).isEqualTo(UPDATED_TXT_16);
        assertThat(testContent.getTxt17()).isEqualTo(UPDATED_TXT_17);
        assertThat(testContent.getTxt18()).isEqualTo(UPDATED_TXT_18);
        assertThat(testContent.getTxt19()).isEqualTo(UPDATED_TXT_19);
        assertThat(testContent.getTxt20()).isEqualTo(UPDATED_TXT_20);
        assertThat(testContent.getNum1()).isEqualTo(UPDATED_NUM_1);
        assertThat(testContent.getNum2()).isEqualTo(UPDATED_NUM_2);
        assertThat(testContent.getNum3()).isEqualTo(UPDATED_NUM_3);
        assertThat(testContent.getNum4()).isEqualTo(UPDATED_NUM_4);
        assertThat(testContent.getNum5()).isEqualTo(UPDATED_NUM_5);
        assertThat(testContent.getNum6()).isEqualTo(UPDATED_NUM_6);
        assertThat(testContent.getNum7()).isEqualTo(UPDATED_NUM_7);
        assertThat(testContent.getNum8()).isEqualTo(UPDATED_NUM_8);
        assertThat(testContent.getNum9()).isEqualTo(UPDATED_NUM_9);
        assertThat(testContent.getNum10()).isEqualTo(UPDATED_NUM_10);
        assertThat(testContent.getNum11()).isEqualTo(UPDATED_NUM_11);
        assertThat(testContent.getNum12()).isEqualTo(UPDATED_NUM_12);
        assertThat(testContent.getNum13()).isEqualTo(UPDATED_NUM_13);
        assertThat(testContent.getNum14()).isEqualTo(UPDATED_NUM_14);
        assertThat(testContent.getNum15()).isEqualTo(UPDATED_NUM_15);
        assertThat(testContent.getNum16()).isEqualTo(UPDATED_NUM_16);
        assertThat(testContent.getNum17()).isEqualTo(UPDATED_NUM_17);
        assertThat(testContent.getNum18()).isEqualTo(UPDATED_NUM_18);
        assertThat(testContent.getNum19()).isEqualTo(UPDATED_NUM_19);
        assertThat(testContent.getNum20()).isEqualTo(UPDATED_NUM_20);
        assertThat(testContent.getDate1()).isEqualTo(UPDATED_DATE_1);
        assertThat(testContent.getDate2()).isEqualTo(UPDATED_DATE_2);
        assertThat(testContent.getDate3()).isEqualTo(UPDATED_DATE_3);
        assertThat(testContent.getDate4()).isEqualTo(UPDATED_DATE_4);
        assertThat(testContent.getDate5()).isEqualTo(UPDATED_DATE_5);
        assertThat(testContent.getDate6()).isEqualTo(UPDATED_DATE_6);
        assertThat(testContent.getDate7()).isEqualTo(UPDATED_DATE_7);
        assertThat(testContent.getDate8()).isEqualTo(UPDATED_DATE_8);
        assertThat(testContent.getDate9()).isEqualTo(UPDATED_DATE_9);
        assertThat(testContent.getDate10()).isEqualTo(UPDATED_DATE_10);
        assertThat(testContent.getBool1()).isEqualTo(UPDATED_BOOL_1);
        assertThat(testContent.getBool2()).isEqualTo(UPDATED_BOOL_2);
        assertThat(testContent.getBool3()).isEqualTo(UPDATED_BOOL_3);
        assertThat(testContent.getBool4()).isEqualTo(UPDATED_BOOL_4);
        assertThat(testContent.getBool5()).isEqualTo(UPDATED_BOOL_5);
    }

    @Test
    @Transactional
    public void updateNonExistingContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Create the Content

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContent() throws Exception {
        // Initialize the database
        contentService.save(content);

        int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Delete the content
        restContentMockMvc.perform(delete("/api/contents/{id}", content.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
