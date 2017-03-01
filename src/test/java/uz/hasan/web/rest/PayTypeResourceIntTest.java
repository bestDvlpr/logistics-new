package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.PayType;
import uz.hasan.domain.PaymentType;
import uz.hasan.domain.Receipt;
import uz.hasan.repository.PayTypeRepository;
import uz.hasan.service.PayTypeService;
import uz.hasan.service.dto.PayTypeDTO;
import uz.hasan.service.mapper.PayTypeMapper;

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
 * Test class for the PayTypeResource REST controller.
 *
 * @see PayTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class PayTypeResourceIntTest {

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final String DEFAULT_SAP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SAP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBB";

    @Autowired
    private PayTypeRepository payTypeRepository;

    @Autowired
    private PayTypeMapper payTypeMapper;

    @Autowired
    private PayTypeService payTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPayTypeMockMvc;

    private PayType payType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayTypeResource payTypeResource = new PayTypeResource(payTypeService);
        this.restPayTypeMockMvc = MockMvcBuilders.standaloneSetup(payTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayType createEntity(EntityManager em) {
        PayType payType = new PayType()
                .amount(DEFAULT_AMOUNT)
                .sapCode(DEFAULT_SAP_CODE)
                .serial(DEFAULT_SERIAL);
        // Add required entity
        PaymentType paymentType = PaymentTypeResourceIntTest.createEntity(em);
        em.persist(paymentType);
        em.flush();
        payType.setPaymentType(paymentType);
        // Add required entity
        Receipt receipt = ReceiptResourceIntTest.createEntity(em);
        em.persist(receipt);
        em.flush();
        payType.setReceipt(receipt);
        return payType;
    }

    @Before
    public void initTest() {
        payType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayType() throws Exception {
        int databaseSizeBeforeCreate = payTypeRepository.findAll().size();

        // Create the PayType
        PayTypeDTO payTypeDTO = payTypeMapper.payTypeToPayTypeDTO(payType);

        restPayTypeMockMvc.perform(post("/api/pay-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PayType in the database
        List<PayType> payTypeList = payTypeRepository.findAll();
        assertThat(payTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PayType testPayType = payTypeList.get(payTypeList.size() - 1);
        assertThat(testPayType.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayType.getSapCode()).isEqualTo(DEFAULT_SAP_CODE);
        assertThat(testPayType.getSerial()).isEqualTo(DEFAULT_SERIAL);
    }

    @Test
    @Transactional
    public void createPayTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payTypeRepository.findAll().size();

        // Create the PayType with an existing ID
        PayType existingPayType = new PayType();
        existingPayType.setId(1L);
        PayTypeDTO existingPayTypeDTO = payTypeMapper.payTypeToPayTypeDTO(existingPayType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayTypeMockMvc.perform(post("/api/pay-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPayTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PayType> payTypeList = payTypeRepository.findAll();
        assertThat(payTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = payTypeRepository.findAll().size();
        // set the field null
        payType.setAmount(null);

        // Create the PayType, which fails.
        PayTypeDTO payTypeDTO = payTypeMapper.payTypeToPayTypeDTO(payType);

        restPayTypeMockMvc.perform(post("/api/pay-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PayType> payTypeList = payTypeRepository.findAll();
        assertThat(payTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayTypes() throws Exception {
        // Initialize the database
        payTypeRepository.saveAndFlush(payType);

        // Get all the payTypeList
        restPayTypeMockMvc.perform(get("/api/pay-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payType.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].sapCode").value(hasItem(DEFAULT_SAP_CODE.toString())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())));
    }

    @Test
    @Transactional
    public void getPayType() throws Exception {
        // Initialize the database
        payTypeRepository.saveAndFlush(payType);

        // Get the payType
        restPayTypeMockMvc.perform(get("/api/pay-types/{id}", payType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payType.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.sapCode").value(DEFAULT_SAP_CODE.toString()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayType() throws Exception {
        // Get the payType
        restPayTypeMockMvc.perform(get("/api/pay-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayType() throws Exception {
        // Initialize the database
        payTypeRepository.saveAndFlush(payType);
        int databaseSizeBeforeUpdate = payTypeRepository.findAll().size();

        // Update the payType
        PayType updatedPayType = payTypeRepository.findOne(payType.getId());
        updatedPayType
                .amount(UPDATED_AMOUNT)
                .sapCode(UPDATED_SAP_CODE)
                .serial(UPDATED_SERIAL);
        PayTypeDTO payTypeDTO = payTypeMapper.payTypeToPayTypeDTO(updatedPayType);

        restPayTypeMockMvc.perform(put("/api/pay-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PayType in the database
        List<PayType> payTypeList = payTypeRepository.findAll();
        assertThat(payTypeList).hasSize(databaseSizeBeforeUpdate);
        PayType testPayType = payTypeList.get(payTypeList.size() - 1);
        assertThat(testPayType.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayType.getSapCode()).isEqualTo(UPDATED_SAP_CODE);
        assertThat(testPayType.getSerial()).isEqualTo(UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingPayType() throws Exception {
        int databaseSizeBeforeUpdate = payTypeRepository.findAll().size();

        // Create the PayType
        PayTypeDTO payTypeDTO = payTypeMapper.payTypeToPayTypeDTO(payType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayTypeMockMvc.perform(put("/api/pay-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PayType in the database
        List<PayType> payTypeList = payTypeRepository.findAll();
        assertThat(payTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayType() throws Exception {
        // Initialize the database
        payTypeRepository.saveAndFlush(payType);
        int databaseSizeBeforeDelete = payTypeRepository.findAll().size();

        // Get the payType
        restPayTypeMockMvc.perform(delete("/api/pay-types/{id}", payType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayType> payTypeList = payTypeRepository.findAll();
        assertThat(payTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayType.class);
    }
}
