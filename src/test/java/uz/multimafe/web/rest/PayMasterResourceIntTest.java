package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.PayMaster;
import uz.multimafe.repository.PayMasterRepository;
import uz.multimafe.service.PayMasterService;
import uz.multimafe.service.dto.PayMasterDTO;
import uz.multimafe.service.mapper.PayMasterMapper;

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
 * Test class for the PayMasterResource REST controller.
 *
 * @see PayMasterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class PayMasterResourceIntTest {

    private static final String DEFAULT_PAYMASTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMASTER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_MASTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAY_MASTER_NAME = "BBBBBBBBBB";

    @Autowired
    private PayMasterRepository payMasterRepository;

    @Autowired
    private PayMasterMapper payMasterMapper;

    @Autowired
    private PayMasterService payMasterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPayMasterMockMvc;

    private PayMaster payMaster;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayMasterResource payMasterResource = new PayMasterResource(payMasterService);
        this.restPayMasterMockMvc = MockMvcBuilders.standaloneSetup(payMasterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayMaster createEntity(EntityManager em) {
        PayMaster payMaster = new PayMaster()
                .paymasterID(DEFAULT_PAYMASTER_ID)
                .payMasterName(DEFAULT_PAY_MASTER_NAME);
        return payMaster;
    }

    @Before
    public void initTest() {
        payMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayMaster() throws Exception {
        int databaseSizeBeforeCreate = payMasterRepository.findAll().size();

        // Create the PayMaster
        PayMasterDTO payMasterDTO = payMasterMapper.payMasterToPayMasterDTO(payMaster);

        restPayMasterMockMvc.perform(post("/api/pay-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the PayMaster in the database
        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeCreate + 1);
        PayMaster testPayMaster = payMasterList.get(payMasterList.size() - 1);
        assertThat(testPayMaster.getPaymasterID()).isEqualTo(DEFAULT_PAYMASTER_ID);
        assertThat(testPayMaster.getPayMasterName()).isEqualTo(DEFAULT_PAY_MASTER_NAME);
    }

    @Test
    @Transactional
    public void createPayMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payMasterRepository.findAll().size();

        // Create the PayMaster with an existing ID
        PayMaster existingPayMaster = new PayMaster();
        existingPayMaster.setId(1L);
        PayMasterDTO existingPayMasterDTO = payMasterMapper.payMasterToPayMasterDTO(existingPayMaster);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayMasterMockMvc.perform(post("/api/pay-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPayMasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaymasterIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = payMasterRepository.findAll().size();
        // set the field null
        payMaster.setPaymasterID(null);

        // Create the PayMaster, which fails.
        PayMasterDTO payMasterDTO = payMasterMapper.payMasterToPayMasterDTO(payMaster);

        restPayMasterMockMvc.perform(post("/api/pay-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payMasterDTO)))
            .andExpect(status().isBadRequest());

        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayMasterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = payMasterRepository.findAll().size();
        // set the field null
        payMaster.setPayMasterName(null);

        // Create the PayMaster, which fails.
        PayMasterDTO payMasterDTO = payMasterMapper.payMasterToPayMasterDTO(payMaster);

        restPayMasterMockMvc.perform(post("/api/pay-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payMasterDTO)))
            .andExpect(status().isBadRequest());

        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayMasters() throws Exception {
        // Initialize the database
        payMasterRepository.saveAndFlush(payMaster);

        // Get all the payMasterList
        restPayMasterMockMvc.perform(get("/api/pay-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymasterID").value(hasItem(DEFAULT_PAYMASTER_ID.toString())))
            .andExpect(jsonPath("$.[*].payMasterName").value(hasItem(DEFAULT_PAY_MASTER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPayMaster() throws Exception {
        // Initialize the database
        payMasterRepository.saveAndFlush(payMaster);

        // Get the payMaster
        restPayMasterMockMvc.perform(get("/api/pay-masters/{id}", payMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payMaster.getId().intValue()))
            .andExpect(jsonPath("$.paymasterID").value(DEFAULT_PAYMASTER_ID.toString()))
            .andExpect(jsonPath("$.payMasterName").value(DEFAULT_PAY_MASTER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayMaster() throws Exception {
        // Get the payMaster
        restPayMasterMockMvc.perform(get("/api/pay-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayMaster() throws Exception {
        // Initialize the database
        payMasterRepository.saveAndFlush(payMaster);
        int databaseSizeBeforeUpdate = payMasterRepository.findAll().size();

        // Update the payMaster
        PayMaster updatedPayMaster = payMasterRepository.findOne(payMaster.getId());
        updatedPayMaster
                .paymasterID(UPDATED_PAYMASTER_ID)
                .payMasterName(UPDATED_PAY_MASTER_NAME);
        PayMasterDTO payMasterDTO = payMasterMapper.payMasterToPayMasterDTO(updatedPayMaster);

        restPayMasterMockMvc.perform(put("/api/pay-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payMasterDTO)))
            .andExpect(status().isOk());

        // Validate the PayMaster in the database
        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeUpdate);
        PayMaster testPayMaster = payMasterList.get(payMasterList.size() - 1);
        assertThat(testPayMaster.getPaymasterID()).isEqualTo(UPDATED_PAYMASTER_ID);
        assertThat(testPayMaster.getPayMasterName()).isEqualTo(UPDATED_PAY_MASTER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPayMaster() throws Exception {
        int databaseSizeBeforeUpdate = payMasterRepository.findAll().size();

        // Create the PayMaster
        PayMasterDTO payMasterDTO = payMasterMapper.payMasterToPayMasterDTO(payMaster);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayMasterMockMvc.perform(put("/api/pay-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payMasterDTO)))
            .andExpect(status().isCreated());

        // Validate the PayMaster in the database
        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayMaster() throws Exception {
        // Initialize the database
        payMasterRepository.saveAndFlush(payMaster);
        int databaseSizeBeforeDelete = payMasterRepository.findAll().size();

        // Get the payMaster
        restPayMasterMockMvc.perform(delete("/api/pay-masters/{id}", payMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayMaster> payMasterList = payMasterRepository.findAll();
        assertThat(payMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayMaster.class);
    }
}
