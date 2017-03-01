package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.ReceiptStatus;
import uz.hasan.repository.ReceiptStatusRepository;
import uz.hasan.service.ReceiptStatusService;
import uz.hasan.service.dto.ReceiptStatusDTO;
import uz.hasan.service.mapper.ReceiptStatusMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReceiptStatusResource REST controller.
 *
 * @see ReceiptStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class ReceiptStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReceiptStatusRepository receiptStatusRepository;

    @Autowired
    private ReceiptStatusMapper receiptStatusMapper;

    @Autowired
    private ReceiptStatusService receiptStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restReceiptStatusMockMvc;

    private ReceiptStatus receiptStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceiptStatusResource receiptStatusResource = new ReceiptStatusResource(receiptStatusService);
        this.restReceiptStatusMockMvc = MockMvcBuilders.standaloneSetup(receiptStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiptStatus createEntity(EntityManager em) {
        ReceiptStatus receiptStatus = new ReceiptStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return receiptStatus;
    }

    @Before
    public void initTest() {
        receiptStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceiptStatus() throws Exception {
        int databaseSizeBeforeCreate = receiptStatusRepository.findAll().size();

        // Create the ReceiptStatus
        ReceiptStatusDTO receiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus);

        restReceiptStatusMockMvc.perform(post("/api/receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ReceiptStatus in the database
        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiptStatus testReceiptStatus = receiptStatusList.get(receiptStatusList.size() - 1);
        assertThat(testReceiptStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReceiptStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReceiptStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptStatusRepository.findAll().size();

        // Create the ReceiptStatus with an existing ID
        ReceiptStatus existingReceiptStatus = new ReceiptStatus();
        existingReceiptStatus.setId(1L);
        ReceiptStatusDTO existingReceiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(existingReceiptStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptStatusMockMvc.perform(post("/api/receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReceiptStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptStatusRepository.findAll().size();
        // set the field null
        receiptStatus.setName(null);

        // Create the ReceiptStatus, which fails.
        ReceiptStatusDTO receiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus);

        restReceiptStatusMockMvc.perform(post("/api/receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptStatusRepository.findAll().size();
        // set the field null
        receiptStatus.setDescription(null);

        // Create the ReceiptStatus, which fails.
        ReceiptStatusDTO receiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus);

        restReceiptStatusMockMvc.perform(post("/api/receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptStatusDTO)))
            .andExpect(status().isBadRequest());

        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceiptStatuses() throws Exception {
        // Initialize the database
        receiptStatusRepository.saveAndFlush(receiptStatus);

        // Get all the receiptStatusList
        restReceiptStatusMockMvc.perform(get("/api/receipt-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getReceiptStatus() throws Exception {
        // Initialize the database
        receiptStatusRepository.saveAndFlush(receiptStatus);

        // Get the receiptStatus
        restReceiptStatusMockMvc.perform(get("/api/receipt-statuses/{id}", receiptStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receiptStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceiptStatus() throws Exception {
        // Get the receiptStatus
        restReceiptStatusMockMvc.perform(get("/api/receipt-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceiptStatus() throws Exception {
        // Initialize the database
        receiptStatusRepository.saveAndFlush(receiptStatus);
        int databaseSizeBeforeUpdate = receiptStatusRepository.findAll().size();

        // Update the receiptStatus
        ReceiptStatus updatedReceiptStatus = receiptStatusRepository.findOne(receiptStatus.getId());
        updatedReceiptStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        ReceiptStatusDTO receiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(updatedReceiptStatus);

        restReceiptStatusMockMvc.perform(put("/api/receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptStatusDTO)))
            .andExpect(status().isOk());

        // Validate the ReceiptStatus in the database
        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeUpdate);
        ReceiptStatus testReceiptStatus = receiptStatusList.get(receiptStatusList.size() - 1);
        assertThat(testReceiptStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReceiptStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReceiptStatus() throws Exception {
        int databaseSizeBeforeUpdate = receiptStatusRepository.findAll().size();

        // Create the ReceiptStatus
        ReceiptStatusDTO receiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReceiptStatusMockMvc.perform(put("/api/receipt-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ReceiptStatus in the database
        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReceiptStatus() throws Exception {
        // Initialize the database
        receiptStatusRepository.saveAndFlush(receiptStatus);
        int databaseSizeBeforeDelete = receiptStatusRepository.findAll().size();

        // Get the receiptStatus
        restReceiptStatusMockMvc.perform(delete("/api/receipt-statuses/{id}", receiptStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReceiptStatus> receiptStatusList = receiptStatusRepository.findAll();
        assertThat(receiptStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptStatus.class);
    }
}
