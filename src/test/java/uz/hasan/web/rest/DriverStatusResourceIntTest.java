package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.DriverStatus;
import uz.hasan.repository.DriverStatusRepository;
import uz.hasan.service.DriverStatusService;
import uz.hasan.service.dto.DriverStatusDTO;
import uz.hasan.service.mapper.DriverStatusMapper;

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
 * Test class for the DriverStatusResource REST controller.
 *
 * @see DriverStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class DriverStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DriverStatusRepository driverStatusRepository;

    @Autowired
    private DriverStatusMapper driverStatusMapper;

    @Autowired
    private DriverStatusService driverStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restDriverStatusMockMvc;

    private DriverStatus driverStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DriverStatusResource driverStatusResource = new DriverStatusResource(driverStatusService);
        this.restDriverStatusMockMvc = MockMvcBuilders.standaloneSetup(driverStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DriverStatus createEntity(EntityManager em) {
        DriverStatus driverStatus = new DriverStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return driverStatus;
    }

    @Before
    public void initTest() {
        driverStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriverStatus() throws Exception {
        int databaseSizeBeforeCreate = driverStatusRepository.findAll().size();

        // Create the DriverStatus
        DriverStatusDTO driverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus);

        restDriverStatusMockMvc.perform(post("/api/driver-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the DriverStatus in the database
        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DriverStatus testDriverStatus = driverStatusList.get(driverStatusList.size() - 1);
        assertThat(testDriverStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDriverStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDriverStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverStatusRepository.findAll().size();

        // Create the DriverStatus with an existing ID
        DriverStatus existingDriverStatus = new DriverStatus();
        existingDriverStatus.setId(1L);
        DriverStatusDTO existingDriverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(existingDriverStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverStatusMockMvc.perform(post("/api/driver-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDriverStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverStatusRepository.findAll().size();
        // set the field null
        driverStatus.setName(null);

        // Create the DriverStatus, which fails.
        DriverStatusDTO driverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus);

        restDriverStatusMockMvc.perform(post("/api/driver-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverStatusDTO)))
            .andExpect(status().isBadRequest());

        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverStatusRepository.findAll().size();
        // set the field null
        driverStatus.setDescription(null);

        // Create the DriverStatus, which fails.
        DriverStatusDTO driverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus);

        restDriverStatusMockMvc.perform(post("/api/driver-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverStatusDTO)))
            .andExpect(status().isBadRequest());

        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDriverStatuses() throws Exception {
        // Initialize the database
        driverStatusRepository.saveAndFlush(driverStatus);

        // Get all the driverStatusList
        restDriverStatusMockMvc.perform(get("/api/driver-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driverStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDriverStatus() throws Exception {
        // Initialize the database
        driverStatusRepository.saveAndFlush(driverStatus);

        // Get the driverStatus
        restDriverStatusMockMvc.perform(get("/api/driver-statuses/{id}", driverStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driverStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDriverStatus() throws Exception {
        // Get the driverStatus
        restDriverStatusMockMvc.perform(get("/api/driver-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriverStatus() throws Exception {
        // Initialize the database
        driverStatusRepository.saveAndFlush(driverStatus);
        int databaseSizeBeforeUpdate = driverStatusRepository.findAll().size();

        // Update the driverStatus
        DriverStatus updatedDriverStatus = driverStatusRepository.findOne(driverStatus.getId());
        updatedDriverStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        DriverStatusDTO driverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(updatedDriverStatus);

        restDriverStatusMockMvc.perform(put("/api/driver-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverStatusDTO)))
            .andExpect(status().isOk());

        // Validate the DriverStatus in the database
        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeUpdate);
        DriverStatus testDriverStatus = driverStatusList.get(driverStatusList.size() - 1);
        assertThat(testDriverStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDriverStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDriverStatus() throws Exception {
        int databaseSizeBeforeUpdate = driverStatusRepository.findAll().size();

        // Create the DriverStatus
        DriverStatusDTO driverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDriverStatusMockMvc.perform(put("/api/driver-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the DriverStatus in the database
        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDriverStatus() throws Exception {
        // Initialize the database
        driverStatusRepository.saveAndFlush(driverStatus);
        int databaseSizeBeforeDelete = driverStatusRepository.findAll().size();

        // Get the driverStatus
        restDriverStatusMockMvc.perform(delete("/api/driver-statuses/{id}", driverStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DriverStatus> driverStatusList = driverStatusRepository.findAll();
        assertThat(driverStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverStatus.class);
    }
}
