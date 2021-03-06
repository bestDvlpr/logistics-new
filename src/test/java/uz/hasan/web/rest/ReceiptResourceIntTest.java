package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.Receipt;
import uz.hasan.domain.Company;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UploadService;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static uz.hasan.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uz.hasan.domain.enumeration.DocType;
import uz.hasan.domain.enumeration.WholeSaleFlag;
import uz.hasan.domain.enumeration.ReceiptStatus;
/**
 * Test class for the ReceiptResource REST controller.
 *
 * @see ReceiptResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
@Transactional
public class ReceiptResourceIntTest {

    private static final String DEFAULT_DOC_NUM = "AAAAAAAAAA";
    private static final String UPDATED_DOC_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_ID = "AAAAAAAAAA";
    private static final String UPDATED_DOC_ID = "BBBBBBBBBB";

    private static final DocType DEFAULT_DOC_TYPE = DocType.RETURN;
    private static final DocType UPDATED_DOC_TYPE = DocType.SALES;

    private static final String DEFAULT_PREVIOUS_DOC_ID = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_DOC_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_DOC_DATE = 1L;
    private static final Long UPDATED_DOC_DATE = 2L;

    private static final WholeSaleFlag DEFAULT_WHOLE_SALE_FLAG = WholeSaleFlag.RETAIL;
    private static final WholeSaleFlag UPDATED_WHOLE_SALE_FLAG = WholeSaleFlag.WHOLESALE;

    private static final ReceiptStatus DEFAULT_STATUS = ReceiptStatus.NEW;
    private static final ReceiptStatus UPDATED_STATUS = ReceiptStatus.APPLICATION_SENT;

    private static final ZonedDateTime DEFAULT_SENT_TO_DC_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SENT_TO_DC_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DELIVERED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELIVERED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FROM_TIME = "AAAAAAAAAA";
    private static final String UPDATED_FROM_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_TO_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TO_TIME = "BBBBBBBBBB";

    private static final Long DEFAULT_DELIVERY_DATE = 1L;
    private static final Long UPDATED_DELIVERY_DATE = 2L;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReceiptMockMvc;

    private Receipt receipt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReceiptResource receiptResource = new ReceiptResource(receiptService, uploadService);
        this.restReceiptMockMvc = MockMvcBuilders.standaloneSetup(receiptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
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
                .docNum(DEFAULT_DOC_NUM)
                .docID(DEFAULT_DOC_ID)
                .docType(DEFAULT_DOC_TYPE)
                .previousDocID(DEFAULT_PREVIOUS_DOC_ID)
                .docDate(DEFAULT_DOC_DATE)
                .wholeSaleFlag(DEFAULT_WHOLE_SALE_FLAG)
                .status(DEFAULT_STATUS)
                .sentToDCTime(DEFAULT_SENT_TO_DC_TIME)
                .deliveredTime(DEFAULT_DELIVERED_TIME)
                .fromTime(DEFAULT_FROM_TIME)
                .toTime(DEFAULT_TO_TIME)
                .deliveryDate(DEFAULT_DELIVERY_DATE);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        receipt.setCompany(company);
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
        assertThat(testReceipt.getDocID()).isEqualTo(DEFAULT_DOC_ID);
        assertThat(testReceipt.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testReceipt.getPreviousDocID()).isEqualTo(DEFAULT_PREVIOUS_DOC_ID);
        assertThat(testReceipt.getDocDate()).isEqualTo(DEFAULT_DOC_DATE);
        assertThat(testReceipt.getWholeSaleFlag()).isEqualTo(DEFAULT_WHOLE_SALE_FLAG);
        assertThat(testReceipt.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReceipt.getSentToDCTime()).isEqualTo(DEFAULT_SENT_TO_DC_TIME);
//        assertThat(testReceipt.getDeliveredTime()).isEqualTo(DEFAULT_DELIVERED_TIME);
        assertThat(testReceipt.getFromTime()).isEqualTo(DEFAULT_FROM_TIME);
        assertThat(testReceipt.getToTime()).isEqualTo(DEFAULT_TO_TIME);
//        assertThat(testReceipt.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
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
    public void checkDocIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setDocID(null);

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
    public void checkDocTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setDocType(null);

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
    public void checkDocDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setDocDate(null);

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
    public void checkWholeSaleFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setWholeSaleFlag(null);

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
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receipt.setStatus(null);

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
            .andExpect(jsonPath("$.[*].docNum").value(hasItem(DEFAULT_DOC_NUM.toString())))
            .andExpect(jsonPath("$.[*].docID").value(hasItem(DEFAULT_DOC_ID.toString())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].previousDocID").value(hasItem(DEFAULT_PREVIOUS_DOC_ID.toString())))
            .andExpect(jsonPath("$.[*].docDate").value(hasItem(DEFAULT_DOC_DATE.intValue())))
            .andExpect(jsonPath("$.[*].wholeSaleFlag").value(hasItem(DEFAULT_WHOLE_SALE_FLAG.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].sentToDCTime").value(hasItem(sameInstant(DEFAULT_SENT_TO_DC_TIME))))
            .andExpect(jsonPath("$.[*].deliveredTime").value(hasItem(sameInstant(DEFAULT_DELIVERED_TIME))))
            .andExpect(jsonPath("$.[*].fromTime").value(hasItem(DEFAULT_FROM_TIME.toString())))
            .andExpect(jsonPath("$.[*].toTime").value(hasItem(DEFAULT_TO_TIME.toString())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.intValue())));
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
            .andExpect(jsonPath("$.docNum").value(DEFAULT_DOC_NUM.toString()))
            .andExpect(jsonPath("$.docID").value(DEFAULT_DOC_ID.toString()))
            .andExpect(jsonPath("$.docType").value(DEFAULT_DOC_TYPE.toString()))
            .andExpect(jsonPath("$.previousDocID").value(DEFAULT_PREVIOUS_DOC_ID.toString()))
            .andExpect(jsonPath("$.docDate").value(DEFAULT_DOC_DATE.intValue()))
            .andExpect(jsonPath("$.wholeSaleFlag").value(DEFAULT_WHOLE_SALE_FLAG.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.sentToDCTime").value(sameInstant(DEFAULT_SENT_TO_DC_TIME)))
            .andExpect(jsonPath("$.deliveredTime").value(sameInstant(DEFAULT_DELIVERED_TIME)))
            .andExpect(jsonPath("$.fromTime").value(DEFAULT_FROM_TIME.toString()))
            .andExpect(jsonPath("$.toTime").value(DEFAULT_TO_TIME.toString()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.intValue()));
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
                .docNum(UPDATED_DOC_NUM)
                .docID(UPDATED_DOC_ID)
                .docType(UPDATED_DOC_TYPE)
                .previousDocID(UPDATED_PREVIOUS_DOC_ID)
                .docDate(UPDATED_DOC_DATE)
                .wholeSaleFlag(UPDATED_WHOLE_SALE_FLAG)
                .status(UPDATED_STATUS)
                .sentToDCTime(UPDATED_SENT_TO_DC_TIME)
                .deliveredTime(UPDATED_DELIVERED_TIME)
                .fromTime(UPDATED_FROM_TIME)
                .toTime(UPDATED_TO_TIME)
                .deliveryDate(UPDATED_DELIVERY_DATE);
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
        assertThat(testReceipt.getDocID()).isEqualTo(UPDATED_DOC_ID);
        assertThat(testReceipt.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testReceipt.getPreviousDocID()).isEqualTo(UPDATED_PREVIOUS_DOC_ID);
        assertThat(testReceipt.getDocDate()).isEqualTo(UPDATED_DOC_DATE);
        assertThat(testReceipt.getWholeSaleFlag()).isEqualTo(UPDATED_WHOLE_SALE_FLAG);
        assertThat(testReceipt.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReceipt.getSentToDCTime()).isEqualTo(UPDATED_SENT_TO_DC_TIME);
//        assertThat(testReceipt.getDeliveredTime()).isEqualTo(UPDATED_DELIVERED_TIME);
        assertThat(testReceipt.getFromTime()).isEqualTo(UPDATED_FROM_TIME);
        assertThat(testReceipt.getToTime()).isEqualTo(UPDATED_TO_TIME);
        assertThat(testReceipt.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
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
