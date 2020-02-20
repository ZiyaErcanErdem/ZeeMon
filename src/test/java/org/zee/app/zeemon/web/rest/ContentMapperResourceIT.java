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
import org.zee.app.zeemon.domain.enumeration.ItemFormat;
import org.zee.app.zeemon.repository.ContentMapperRepository;
import org.zee.app.zeemon.service.ContentMapperService;
import org.zee.app.zeemon.web.rest.ContentMapperResource;
import org.zee.app.zeemon.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.zee.app.zeemon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ContentMapperResource} REST controller.
 */
@SpringBootTest(classes = ZeemonApp.class)
public class ContentMapperResourceIT {

    private static final String DEFAULT_MAPPER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAPPER_NAME = "BBBBBBBBBB";

    private static final ItemFormat DEFAULT_ITEM_FORMAT = ItemFormat.SQL_RESULTSET;
    private static final ItemFormat UPDATED_ITEM_FORMAT = ItemFormat.JSON;

    private static final Boolean DEFAULT_CONTAINS_HEADER = false;
    private static final Boolean UPDATED_CONTAINS_HEADER = true;

    private static final String DEFAULT_FIELD_DELIMITER = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_DELIMITER = "BBBBBBBBBB";

    @Autowired
    private ContentMapperRepository contentMapperRepository;

    @Autowired
    private ContentMapperService contentMapperService;

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

    private MockMvc restContentMapperMockMvc;

    private ContentMapper contentMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentMapperResource contentMapperResource = new ContentMapperResource(contentMapperService);
        this.restContentMapperMockMvc = MockMvcBuilders.standaloneSetup(contentMapperResource)
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
    public static ContentMapper createEntity(EntityManager em) {
        ContentMapper contentMapper = new ContentMapper()
            .mapperName(DEFAULT_MAPPER_NAME)
            .itemFormat(DEFAULT_ITEM_FORMAT)
            .containsHeader(DEFAULT_CONTAINS_HEADER)
            .fieldDelimiter(DEFAULT_FIELD_DELIMITER);
        return contentMapper;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentMapper createUpdatedEntity(EntityManager em) {
        ContentMapper contentMapper = new ContentMapper()
            .mapperName(UPDATED_MAPPER_NAME)
            .itemFormat(UPDATED_ITEM_FORMAT)
            .containsHeader(UPDATED_CONTAINS_HEADER)
            .fieldDelimiter(UPDATED_FIELD_DELIMITER);
        return contentMapper;
    }

    @BeforeEach
    public void initTest() {
        contentMapper = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentMapper() throws Exception {
        int databaseSizeBeforeCreate = contentMapperRepository.findAll().size();

        // Create the ContentMapper
        restContentMapperMockMvc.perform(post("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentMapper)))
            .andExpect(status().isCreated());

        // Validate the ContentMapper in the database
        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeCreate + 1);
        ContentMapper testContentMapper = contentMapperList.get(contentMapperList.size() - 1);
        assertThat(testContentMapper.getMapperName()).isEqualTo(DEFAULT_MAPPER_NAME);
        assertThat(testContentMapper.getItemFormat()).isEqualTo(DEFAULT_ITEM_FORMAT);
        assertThat(testContentMapper.isContainsHeader()).isEqualTo(DEFAULT_CONTAINS_HEADER);
        assertThat(testContentMapper.getFieldDelimiter()).isEqualTo(DEFAULT_FIELD_DELIMITER);
    }

    @Test
    @Transactional
    public void createContentMapperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentMapperRepository.findAll().size();

        // Create the ContentMapper with an existing ID
        contentMapper.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMapperMockMvc.perform(post("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentMapper)))
            .andExpect(status().isBadRequest());

        // Validate the ContentMapper in the database
        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMapperNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentMapperRepository.findAll().size();
        // set the field null
        contentMapper.setMapperName(null);

        // Create the ContentMapper, which fails.

        restContentMapperMockMvc.perform(post("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentMapper)))
            .andExpect(status().isBadRequest());

        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentMapperRepository.findAll().size();
        // set the field null
        contentMapper.setItemFormat(null);

        // Create the ContentMapper, which fails.

        restContentMapperMockMvc.perform(post("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentMapper)))
            .andExpect(status().isBadRequest());

        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldDelimiterIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentMapperRepository.findAll().size();
        // set the field null
        contentMapper.setFieldDelimiter(null);

        // Create the ContentMapper, which fails.

        restContentMapperMockMvc.perform(post("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentMapper)))
            .andExpect(status().isBadRequest());

        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentMappers() throws Exception {
        // Initialize the database
        contentMapperRepository.saveAndFlush(contentMapper);

        // Get all the contentMapperList
        restContentMapperMockMvc.perform(get("/api/content-mappers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentMapper.getId().intValue())))
            .andExpect(jsonPath("$.[*].mapperName").value(hasItem(DEFAULT_MAPPER_NAME)))
            .andExpect(jsonPath("$.[*].itemFormat").value(hasItem(DEFAULT_ITEM_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].containsHeader").value(hasItem(DEFAULT_CONTAINS_HEADER.booleanValue())))
            .andExpect(jsonPath("$.[*].fieldDelimiter").value(hasItem(DEFAULT_FIELD_DELIMITER)));
    }
    
    @Test
    @Transactional
    public void getContentMapper() throws Exception {
        // Initialize the database
        contentMapperRepository.saveAndFlush(contentMapper);

        // Get the contentMapper
        restContentMapperMockMvc.perform(get("/api/content-mappers/{id}", contentMapper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentMapper.getId().intValue()))
            .andExpect(jsonPath("$.mapperName").value(DEFAULT_MAPPER_NAME))
            .andExpect(jsonPath("$.itemFormat").value(DEFAULT_ITEM_FORMAT.toString()))
            .andExpect(jsonPath("$.containsHeader").value(DEFAULT_CONTAINS_HEADER.booleanValue()))
            .andExpect(jsonPath("$.fieldDelimiter").value(DEFAULT_FIELD_DELIMITER));
    }

    @Test
    @Transactional
    public void getNonExistingContentMapper() throws Exception {
        // Get the contentMapper
        restContentMapperMockMvc.perform(get("/api/content-mappers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentMapper() throws Exception {
        // Initialize the database
        contentMapperService.save(contentMapper);

        int databaseSizeBeforeUpdate = contentMapperRepository.findAll().size();

        // Update the contentMapper
        ContentMapper updatedContentMapper = contentMapperRepository.findById(contentMapper.getId()).get();
        // Disconnect from session so that the updates on updatedContentMapper are not directly saved in db
        em.detach(updatedContentMapper);
        updatedContentMapper
            .mapperName(UPDATED_MAPPER_NAME)
            .itemFormat(UPDATED_ITEM_FORMAT)
            .containsHeader(UPDATED_CONTAINS_HEADER)
            .fieldDelimiter(UPDATED_FIELD_DELIMITER);

        restContentMapperMockMvc.perform(put("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentMapper)))
            .andExpect(status().isOk());

        // Validate the ContentMapper in the database
        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeUpdate);
        ContentMapper testContentMapper = contentMapperList.get(contentMapperList.size() - 1);
        assertThat(testContentMapper.getMapperName()).isEqualTo(UPDATED_MAPPER_NAME);
        assertThat(testContentMapper.getItemFormat()).isEqualTo(UPDATED_ITEM_FORMAT);
        assertThat(testContentMapper.isContainsHeader()).isEqualTo(UPDATED_CONTAINS_HEADER);
        assertThat(testContentMapper.getFieldDelimiter()).isEqualTo(UPDATED_FIELD_DELIMITER);
    }

    @Test
    @Transactional
    public void updateNonExistingContentMapper() throws Exception {
        int databaseSizeBeforeUpdate = contentMapperRepository.findAll().size();

        // Create the ContentMapper

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMapperMockMvc.perform(put("/api/content-mappers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentMapper)))
            .andExpect(status().isBadRequest());

        // Validate the ContentMapper in the database
        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentMapper() throws Exception {
        // Initialize the database
        contentMapperService.save(contentMapper);

        int databaseSizeBeforeDelete = contentMapperRepository.findAll().size();

        // Delete the contentMapper
        restContentMapperMockMvc.perform(delete("/api/content-mappers/{id}", contentMapper.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentMapper> contentMapperList = contentMapperRepository.findAll();
        assertThat(contentMapperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
