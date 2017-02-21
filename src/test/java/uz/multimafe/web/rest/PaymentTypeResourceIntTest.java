package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.PaymentType;
import uz.multimafe.repository.PaymentTypeRepository;
import uz.multimafe.service.PaymentTypeService;
import uz.multimafe.service.dto.PaymentTypeDTO;
import uz.multimafe.service.mapper.PaymentTypeMapper;

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
 * Test class for the PaymentTypeResource REST controller.
 *
 * @see PaymentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class PaymentTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentTypeMockMvc;

    private PaymentType paymentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentTypeResource paymentTypeResource = new PaymentTypeResource(paymentTypeService);
        this.restPaymentTypeMockMvc = MockMvcBuilders.standaloneSetup(paymentTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentType createEntity(EntityManager em) {
        PaymentType paymentType = new PaymentType()
                .name(DEFAULT_NAME);
        return paymentType;
    }

    @Before
    public void initTest() {
        paymentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentType() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentType);

        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPaymentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentTypeRepository.findAll().size();

        // Create the PaymentType with an existing ID
        PaymentType existingPaymentType = new PaymentType();
        existingPaymentType.setId(1L);
        PaymentTypeDTO existingPaymentTypeDTO = paymentTypeMapper.paymentTypeToPaymentTypeDTO(existingPaymentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPaymentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentTypeRepository.findAll().size();
        // set the field null
        paymentType.setName(null);

        // Create the PaymentType, which fails.
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentType);

        restPaymentTypeMockMvc.perform(post("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentTypes() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get all the paymentTypeList
        restPaymentTypeMockMvc.perform(get("/api/payment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);

        // Get the paymentType
        restPaymentTypeMockMvc.perform(get("/api/payment-types/{id}", paymentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentType() throws Exception {
        // Get the paymentType
        restPaymentTypeMockMvc.perform(get("/api/payment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Update the paymentType
        PaymentType updatedPaymentType = paymentTypeRepository.findOne(paymentType.getId());
        updatedPaymentType
                .name(UPDATED_NAME);
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.paymentTypeToPaymentTypeDTO(updatedPaymentType);

        restPaymentTypeMockMvc.perform(put("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate);
        PaymentType testPaymentType = paymentTypeList.get(paymentTypeList.size() - 1);
        assertThat(testPaymentType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentType() throws Exception {
        int databaseSizeBeforeUpdate = paymentTypeRepository.findAll().size();

        // Create the PaymentType
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentTypeMockMvc.perform(put("/api/payment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentType in the database
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentType() throws Exception {
        // Initialize the database
        paymentTypeRepository.saveAndFlush(paymentType);
        int databaseSizeBeforeDelete = paymentTypeRepository.findAll().size();

        // Get the paymentType
        restPaymentTypeMockMvc.perform(delete("/api/payment-types/{id}", paymentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentType> paymentTypeList = paymentTypeRepository.findAll();
        assertThat(paymentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentType.class);
    }
}
