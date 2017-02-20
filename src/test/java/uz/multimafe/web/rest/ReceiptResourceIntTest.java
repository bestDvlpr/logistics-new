package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.Receipt;
import uz.multimafe.repository.ReceiptRepository;
import uz.multimafe.service.ReceiptService;
import uz.multimafe.service.dto.ReceiptDTO;
import uz.multimafe.service.mapper.ReceiptMapper;

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
 * Test class for the ReceiptResource REST controller.
 *
 * @see ReceiptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class ReceiptResourceIntTest {

    private static final String DEFAULT_DOC_NUM = "AAAAAAAAAA";
    private static final String UPDATED_DOC_NUM = "BBBBBBBBBB";

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restReceiptMockMvc;

    private Receipt receipt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceiptResource receiptResource = new ReceiptResource(receiptService);
        this.restReceiptMockMvc = MockMvcBuilders.standaloneSetup(receiptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receipt createEntity(EntityManager em) {
        Receipt receipt = new Receipt()
                .docNum(DEFAULT_DOC_NUM);
        return receipt;
    }

    @Before
    public void initTest() {
        receipt = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceipt() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(receipt);

        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isCreated());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeCreate + 1);
        Receipt testReceipt = receiptList.get(receiptList.size() - 1);
        assertThat(testReceipt.getDocNum()).isEqualTo(DEFAULT_DOC_NUM);
    }

    @Test
    @Transactional
    public void createReceiptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt with an existing ID
        Receipt existingReceipt = new Receipt();
        existingReceipt.setId(1L);
        ReceiptDTO existingReceiptDTO = receiptMapper.receiptToReceiptDTO(existingReceipt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReceiptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setDocNum(null);

        // Create the Receipt, which fails.
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(receipt);

        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isBadRequest());

        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceipts() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get all the receiptList
        restReceiptMockMvc.perform(get("/api/receipts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receipt.getId().intValue())))
            .andExpect(jsonPath("$.[*].docNum").value(hasItem(DEFAULT_DOC_NUM.toString())));
    }

    @Test
    @Transactional
    public void getReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);

        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", receipt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(receipt.getId().intValue()))
            .andExpect(jsonPath("$.docNum").value(DEFAULT_DOC_NUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReceipt() throws Exception {
        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);
        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Update the receipt
        Receipt updatedReceipt = receiptRepository.findOne(receipt.getId());
        updatedReceipt
                .docNum(UPDATED_DOC_NUM);
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(updatedReceipt);

        restReceiptMockMvc.perform(put("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isOk());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeUpdate);
        Receipt testReceipt = receiptList.get(receiptList.size() - 1);
        assertThat(testReceipt.getDocNum()).isEqualTo(UPDATED_DOC_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingReceipt() throws Exception {
        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Create the Receipt
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(receipt);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReceiptMockMvc.perform(put("/api/receipts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isCreated());

        // Validate the Receipt in the database
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receipt);
        int databaseSizeBeforeDelete = receiptRepository.findAll().size();

        // Get the receipt
        restReceiptMockMvc.perform(delete("/api/receipts/{id}", receipt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Receipt> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receipt.class);
    }
}
