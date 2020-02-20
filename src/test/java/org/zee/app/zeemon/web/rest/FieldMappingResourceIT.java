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
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.repository.FieldMappingRepository;
import org.zee.app.zeemon.service.FieldMappingService;
import org.zee.app.zeemon.web.rest.FieldMappingResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link FieldMappingResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class FieldMappingResourceIT {

    private static final Integer DEFAULT_SOURCE_INDEX = 0;
    private static final Integer UPDATED_SOURCE_INDEX = 1;

    private static final String DEFAULT_SOURCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_FORMAT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOURCE_START_INDEX = 0;
    private static final Integer UPDATED_SOURCE_START_INDEX = 1;

    private static final Integer DEFAULT_SOURCE_END_INDEX = 0;
    private static final Integer UPDATED_SOURCE_END_INDEX = 1;

    private static final DataType DEFAULT_SOURCE_DATA_TYPE = DataType.STRING;
    private static final DataType UPDATED_SOURCE_DATA_TYPE = DataType.NUMBER;

    private static final String DEFAULT_TARGET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_COL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_COL_NAME = "BBBBBBBBBB";

    private static final DataType DEFAULT_TARGET_DATA_TYPE = DataType.STRING;
    private static final DataType UPDATED_TARGET_DATA_TYPE = DataType.NUMBER;

    private static final String DEFAULT_TRANSFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFORMATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REQUIRED_DATA = false;
    private static final Boolean UPDATED_REQUIRED_DATA = true;

    @Autowired
    private FieldMappingRepository fieldMappingRepository;

    @Autowired
    private FieldMappingService fieldMappingService;

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

    private MockMvc restFieldMappingMockMvc;

    private FieldMapping fieldMapping;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FieldMappingResource fieldMappingResource = new FieldMappingResource(fieldMappingService);
        this.restFieldMappingMockMvc = MockMvcBuilders.standaloneSetup(fieldMappingResource)
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
    public static FieldMapping createEntity(EntityManager em) {
        FieldMapping fieldMapping = new FieldMapping()
            .sourceIndex(DEFAULT_SOURCE_INDEX)
            .sourceName(DEFAULT_SOURCE_NAME)
            .sourceFormat(DEFAULT_SOURCE_FORMAT)
            .sourceStartIndex(DEFAULT_SOURCE_START_INDEX)
            .sourceEndIndex(DEFAULT_SOURCE_END_INDEX)
            .sourceDataType(DEFAULT_SOURCE_DATA_TYPE)
            .targetName(DEFAULT_TARGET_NAME)
            .targetColName(DEFAULT_TARGET_COL_NAME)
            .targetDataType(DEFAULT_TARGET_DATA_TYPE)
            .transformation(DEFAULT_TRANSFORMATION)
            .requiredData(DEFAULT_REQUIRED_DATA);
        // Add required entity
        ContentMapper contentMapper;
        if (TestUtil.findAll(em, ContentMapper.class).isEmpty()) {
            contentMapper = ContentMapperResourceIT.createEntity(em);
            em.persist(contentMapper);
            em.flush();
        } else {
            contentMapper = TestUtil.findAll(em, ContentMapper.class).get(0);
        }
        fieldMapping.setContentMapper(contentMapper);
        return fieldMapping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldMapping createUpdatedEntity(EntityManager em) {
        FieldMapping fieldMapping = new FieldMapping()
            .sourceIndex(UPDATED_SOURCE_INDEX)
            .sourceName(UPDATED_SOURCE_NAME)
            .sourceFormat(UPDATED_SOURCE_FORMAT)
            .sourceStartIndex(UPDATED_SOURCE_START_INDEX)
            .sourceEndIndex(UPDATED_SOURCE_END_INDEX)
            .sourceDataType(UPDATED_SOURCE_DATA_TYPE)
            .targetName(UPDATED_TARGET_NAME)
            .targetColName(UPDATED_TARGET_COL_NAME)
            .targetDataType(UPDATED_TARGET_DATA_TYPE)
            .transformation(UPDATED_TRANSFORMATION)
            .requiredData(UPDATED_REQUIRED_DATA);
        // Add required entity
        ContentMapper contentMapper;
        if (TestUtil.findAll(em, ContentMapper.class).isEmpty()) {
            contentMapper = ContentMapperResourceIT.createUpdatedEntity(em);
            em.persist(contentMapper);
            em.flush();
        } else {
            contentMapper = TestUtil.findAll(em, ContentMapper.class).get(0);
        }
        fieldMapping.setContentMapper(contentMapper);
        return fieldMapping;
    }

    @BeforeEach
    public void initTest() {
        fieldMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldMapping() throws Exception {
        int databaseSizeBeforeCreate = fieldMappingRepository.findAll().size();

        // Create the FieldMapping
        restFieldMappingMockMvc.perform(post("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isCreated());

        // Validate the FieldMapping in the database
        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeCreate + 1);
        FieldMapping testFieldMapping = fieldMappingList.get(fieldMappingList.size() - 1);
        assertThat(testFieldMapping.getSourceIndex()).isEqualTo(DEFAULT_SOURCE_INDEX);
        assertThat(testFieldMapping.getSourceName()).isEqualTo(DEFAULT_SOURCE_NAME);
        assertThat(testFieldMapping.getSourceFormat()).isEqualTo(DEFAULT_SOURCE_FORMAT);
        assertThat(testFieldMapping.getSourceStartIndex()).isEqualTo(DEFAULT_SOURCE_START_INDEX);
        assertThat(testFieldMapping.getSourceEndIndex()).isEqualTo(DEFAULT_SOURCE_END_INDEX);
        assertThat(testFieldMapping.getSourceDataType()).isEqualTo(DEFAULT_SOURCE_DATA_TYPE);
        assertThat(testFieldMapping.getTargetName()).isEqualTo(DEFAULT_TARGET_NAME);
        assertThat(testFieldMapping.getTargetColName()).isEqualTo(DEFAULT_TARGET_COL_NAME);
        assertThat(testFieldMapping.getTargetDataType()).isEqualTo(DEFAULT_TARGET_DATA_TYPE);
        assertThat(testFieldMapping.getTransformation()).isEqualTo(DEFAULT_TRANSFORMATION);
        assertThat(testFieldMapping.isRequiredData()).isEqualTo(DEFAULT_REQUIRED_DATA);
    }

    @Test
    @Transactional
    public void createFieldMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldMappingRepository.findAll().size();

        // Create the FieldMapping with an existing ID
        fieldMapping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldMappingMockMvc.perform(post("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isBadRequest());

        // Validate the FieldMapping in the database
        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSourceDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldMappingRepository.findAll().size();
        // set the field null
        fieldMapping.setSourceDataType(null);

        // Create the FieldMapping, which fails.

        restFieldMappingMockMvc.perform(post("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isBadRequest());

        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldMappingRepository.findAll().size();
        // set the field null
        fieldMapping.setTargetName(null);

        // Create the FieldMapping, which fails.

        restFieldMappingMockMvc.perform(post("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isBadRequest());

        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetColNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldMappingRepository.findAll().size();
        // set the field null
        fieldMapping.setTargetColName(null);

        // Create the FieldMapping, which fails.

        restFieldMappingMockMvc.perform(post("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isBadRequest());

        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldMappingRepository.findAll().size();
        // set the field null
        fieldMapping.setTargetDataType(null);

        // Create the FieldMapping, which fails.

        restFieldMappingMockMvc.perform(post("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isBadRequest());

        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFieldMappings() throws Exception {
        // Initialize the database
        fieldMappingRepository.saveAndFlush(fieldMapping);

        // Get all the fieldMappingList
        restFieldMappingMockMvc.perform(get("/api/field-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceIndex").value(hasItem(DEFAULT_SOURCE_INDEX)))
            .andExpect(jsonPath("$.[*].sourceName").value(hasItem(DEFAULT_SOURCE_NAME)))
            .andExpect(jsonPath("$.[*].sourceFormat").value(hasItem(DEFAULT_SOURCE_FORMAT)))
            .andExpect(jsonPath("$.[*].sourceStartIndex").value(hasItem(DEFAULT_SOURCE_START_INDEX)))
            .andExpect(jsonPath("$.[*].sourceEndIndex").value(hasItem(DEFAULT_SOURCE_END_INDEX)))
            .andExpect(jsonPath("$.[*].sourceDataType").value(hasItem(DEFAULT_SOURCE_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].targetName").value(hasItem(DEFAULT_TARGET_NAME)))
            .andExpect(jsonPath("$.[*].targetColName").value(hasItem(DEFAULT_TARGET_COL_NAME)))
            .andExpect(jsonPath("$.[*].targetDataType").value(hasItem(DEFAULT_TARGET_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transformation").value(hasItem(DEFAULT_TRANSFORMATION)))
            .andExpect(jsonPath("$.[*].requiredData").value(hasItem(DEFAULT_REQUIRED_DATA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFieldMapping() throws Exception {
        // Initialize the database
        fieldMappingRepository.saveAndFlush(fieldMapping);

        // Get the fieldMapping
        restFieldMappingMockMvc.perform(get("/api/field-mappings/{id}", fieldMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldMapping.getId().intValue()))
            .andExpect(jsonPath("$.sourceIndex").value(DEFAULT_SOURCE_INDEX))
            .andExpect(jsonPath("$.sourceName").value(DEFAULT_SOURCE_NAME))
            .andExpect(jsonPath("$.sourceFormat").value(DEFAULT_SOURCE_FORMAT))
            .andExpect(jsonPath("$.sourceStartIndex").value(DEFAULT_SOURCE_START_INDEX))
            .andExpect(jsonPath("$.sourceEndIndex").value(DEFAULT_SOURCE_END_INDEX))
            .andExpect(jsonPath("$.sourceDataType").value(DEFAULT_SOURCE_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.targetName").value(DEFAULT_TARGET_NAME))
            .andExpect(jsonPath("$.targetColName").value(DEFAULT_TARGET_COL_NAME))
            .andExpect(jsonPath("$.targetDataType").value(DEFAULT_TARGET_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.transformation").value(DEFAULT_TRANSFORMATION))
            .andExpect(jsonPath("$.requiredData").value(DEFAULT_REQUIRED_DATA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFieldMapping() throws Exception {
        // Get the fieldMapping
        restFieldMappingMockMvc.perform(get("/api/field-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldMapping() throws Exception {
        // Initialize the database
        fieldMappingService.save(fieldMapping);

        int databaseSizeBeforeUpdate = fieldMappingRepository.findAll().size();

        // Update the fieldMapping
        FieldMapping updatedFieldMapping = fieldMappingRepository.findById(fieldMapping.getId()).get();
        // Disconnect from session so that the updates on updatedFieldMapping are not directly saved in db
        em.detach(updatedFieldMapping);
        updatedFieldMapping
            .sourceIndex(UPDATED_SOURCE_INDEX)
            .sourceName(UPDATED_SOURCE_NAME)
            .sourceFormat(UPDATED_SOURCE_FORMAT)
            .sourceStartIndex(UPDATED_SOURCE_START_INDEX)
            .sourceEndIndex(UPDATED_SOURCE_END_INDEX)
            .sourceDataType(UPDATED_SOURCE_DATA_TYPE)
            .targetName(UPDATED_TARGET_NAME)
            .targetColName(UPDATED_TARGET_COL_NAME)
            .targetDataType(UPDATED_TARGET_DATA_TYPE)
            .transformation(UPDATED_TRANSFORMATION)
            .requiredData(UPDATED_REQUIRED_DATA);

        restFieldMappingMockMvc.perform(put("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFieldMapping)))
            .andExpect(status().isOk());

        // Validate the FieldMapping in the database
        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeUpdate);
        FieldMapping testFieldMapping = fieldMappingList.get(fieldMappingList.size() - 1);
        assertThat(testFieldMapping.getSourceIndex()).isEqualTo(UPDATED_SOURCE_INDEX);
        assertThat(testFieldMapping.getSourceName()).isEqualTo(UPDATED_SOURCE_NAME);
        assertThat(testFieldMapping.getSourceFormat()).isEqualTo(UPDATED_SOURCE_FORMAT);
        assertThat(testFieldMapping.getSourceStartIndex()).isEqualTo(UPDATED_SOURCE_START_INDEX);
        assertThat(testFieldMapping.getSourceEndIndex()).isEqualTo(UPDATED_SOURCE_END_INDEX);
        assertThat(testFieldMapping.getSourceDataType()).isEqualTo(UPDATED_SOURCE_DATA_TYPE);
        assertThat(testFieldMapping.getTargetName()).isEqualTo(UPDATED_TARGET_NAME);
        assertThat(testFieldMapping.getTargetColName()).isEqualTo(UPDATED_TARGET_COL_NAME);
        assertThat(testFieldMapping.getTargetDataType()).isEqualTo(UPDATED_TARGET_DATA_TYPE);
        assertThat(testFieldMapping.getTransformation()).isEqualTo(UPDATED_TRANSFORMATION);
        assertThat(testFieldMapping.isRequiredData()).isEqualTo(UPDATED_REQUIRED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldMapping() throws Exception {
        int databaseSizeBeforeUpdate = fieldMappingRepository.findAll().size();

        // Create the FieldMapping

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldMappingMockMvc.perform(put("/api/field-mappings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldMapping)))
            .andExpect(status().isBadRequest());

        // Validate the FieldMapping in the database
        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFieldMapping() throws Exception {
        // Initialize the database
        fieldMappingService.save(fieldMapping);

        int databaseSizeBeforeDelete = fieldMappingRepository.findAll().size();

        // Delete the fieldMapping
        restFieldMappingMockMvc.perform(delete("/api/field-mappings/{id}", fieldMapping.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldMapping> fieldMappingList = fieldMappingRepository.findAll();
        assertThat(fieldMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
