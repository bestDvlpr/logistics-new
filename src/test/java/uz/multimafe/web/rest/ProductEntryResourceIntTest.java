package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.ProductEntry;
import uz.multimafe.domain.Product;
import uz.multimafe.repository.ProductEntryRepository;
import uz.multimafe.service.ProductEntryService;
import uz.multimafe.service.dto.ProductEntryDTO;
import uz.multimafe.service.mapper.ProductEntryMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uz.multimafe.domain.enumeration.SalesType;
import uz.multimafe.domain.enumeration.SalesPlace;
import uz.multimafe.domain.enumeration.DefectFlag;
import uz.multimafe.domain.enumeration.VirtualFlag;
/**
 * Test class for the ProductEntryResource REST controller.
 *
 * @see ProductEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class ProductEntryResourceIntTest {

    private static final String DEFAULT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final SalesType DEFAULT_DELIVERY_FLAG = SalesType.TAKEOUT_IN_TIME;
    private static final SalesType UPDATED_DELIVERY_FLAG = SalesType.TAKEOUT;

    private static final SalesPlace DEFAULT_HALL_FLAG = SalesPlace.STORE;
    private static final SalesPlace UPDATED_HALL_FLAG = SalesPlace.WAREHOUSE;

    private static final DefectFlag DEFAULT_DEFECT_FLAG = DefectFlag.WELL;
    private static final DefectFlag UPDATED_DEFECT_FLAG = DefectFlag.DEFECTED;

    private static final VirtualFlag DEFAULT_VIRTUAL_FLAG = VirtualFlag.SOLD_PHISICALLY;
    private static final VirtualFlag UPDATED_VIRTUAL_FLAG = VirtualFlag.SOLD_VIRTUALLY;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private ProductEntryMapper productEntryMapper;

    @Autowired
    private ProductEntryService productEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restProductEntryMockMvc;

    private ProductEntry productEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductEntryResource productEntryResource = new ProductEntryResource(productEntryService);
        this.restProductEntryMockMvc = MockMvcBuilders.standaloneSetup(productEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductEntry createEntity(EntityManager em) {
        ProductEntry productEntry = new ProductEntry()
                .serial(DEFAULT_SERIAL)
                .price(DEFAULT_PRICE)
                .deliveryFlag(DEFAULT_DELIVERY_FLAG)
                .hallFlag(DEFAULT_HALL_FLAG)
                .defectFlag(DEFAULT_DEFECT_FLAG)
                .virtualFlag(DEFAULT_VIRTUAL_FLAG)
                .reason(DEFAULT_REASON)
                .comment(DEFAULT_COMMENT)
                .guid(DEFAULT_GUID);
        // Add required entity
        Product product = ProductResourceIntTest.createEntity(em);
        em.persist(product);
        em.flush();
        productEntry.setProduct(product);
        return productEntry;
    }

    @Before
    public void initTest() {
        productEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductEntry() throws Exception {
        int databaseSizeBeforeCreate = productEntryRepository.findAll().size();

        // Create the ProductEntry
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductEntry in the database
        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductEntry testProductEntry = productEntryList.get(productEntryList.size() - 1);
        assertThat(testProductEntry.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testProductEntry.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductEntry.getDeliveryFlag()).isEqualTo(DEFAULT_DELIVERY_FLAG);
        assertThat(testProductEntry.getHallFlag()).isEqualTo(DEFAULT_HALL_FLAG);
        assertThat(testProductEntry.getDefectFlag()).isEqualTo(DEFAULT_DEFECT_FLAG);
        assertThat(testProductEntry.getVirtualFlag()).isEqualTo(DEFAULT_VIRTUAL_FLAG);
        assertThat(testProductEntry.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testProductEntry.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testProductEntry.getGuid()).isEqualTo(DEFAULT_GUID);
    }

    @Test
    @Transactional
    public void createProductEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productEntryRepository.findAll().size();

        // Create the ProductEntry with an existing ID
        ProductEntry existingProductEntry = new ProductEntry();
        existingProductEntry.setId(1L);
        ProductEntryDTO existingProductEntryDTO = productEntryMapper.productEntryToProductEntryDTO(existingProductEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProductEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSerialIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setSerial(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setPrice(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setDeliveryFlag(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHallFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setHallFlag(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefectFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setDefectFlag(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVirtualFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setVirtualFlag(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = productEntryRepository.findAll().size();
        // set the field null
        productEntry.setGuid(null);

        // Create the ProductEntry, which fails.
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        restProductEntryMockMvc.perform(post("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductEntries() throws Exception {
        // Initialize the database
        productEntryRepository.saveAndFlush(productEntry);

        // Get all the productEntryList
        restProductEntryMockMvc.perform(get("/api/product-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].deliveryFlag").value(hasItem(DEFAULT_DELIVERY_FLAG.toString())))
            .andExpect(jsonPath("$.[*].hallFlag").value(hasItem(DEFAULT_HALL_FLAG.toString())))
            .andExpect(jsonPath("$.[*].defectFlag").value(hasItem(DEFAULT_DEFECT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].virtualFlag").value(hasItem(DEFAULT_VIRTUAL_FLAG.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())));
    }

    @Test
    @Transactional
    public void getProductEntry() throws Exception {
        // Initialize the database
        productEntryRepository.saveAndFlush(productEntry);

        // Get the productEntry
        restProductEntryMockMvc.perform(get("/api/product-entries/{id}", productEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productEntry.getId().intValue()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.deliveryFlag").value(DEFAULT_DELIVERY_FLAG.toString()))
            .andExpect(jsonPath("$.hallFlag").value(DEFAULT_HALL_FLAG.toString()))
            .andExpect(jsonPath("$.defectFlag").value(DEFAULT_DEFECT_FLAG.toString()))
            .andExpect(jsonPath("$.virtualFlag").value(DEFAULT_VIRTUAL_FLAG.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductEntry() throws Exception {
        // Get the productEntry
        restProductEntryMockMvc.perform(get("/api/product-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductEntry() throws Exception {
        // Initialize the database
        productEntryRepository.saveAndFlush(productEntry);
        int databaseSizeBeforeUpdate = productEntryRepository.findAll().size();

        // Update the productEntry
        ProductEntry updatedProductEntry = productEntryRepository.findOne(productEntry.getId());
        updatedProductEntry
                .serial(UPDATED_SERIAL)
                .price(UPDATED_PRICE)
                .deliveryFlag(UPDATED_DELIVERY_FLAG)
                .hallFlag(UPDATED_HALL_FLAG)
                .defectFlag(UPDATED_DEFECT_FLAG)
                .virtualFlag(UPDATED_VIRTUAL_FLAG)
                .reason(UPDATED_REASON)
                .comment(UPDATED_COMMENT)
                .guid(UPDATED_GUID);
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(updatedProductEntry);

        restProductEntryMockMvc.perform(put("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductEntry in the database
        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeUpdate);
        ProductEntry testProductEntry = productEntryList.get(productEntryList.size() - 1);
        assertThat(testProductEntry.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testProductEntry.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductEntry.getDeliveryFlag()).isEqualTo(UPDATED_DELIVERY_FLAG);
        assertThat(testProductEntry.getHallFlag()).isEqualTo(UPDATED_HALL_FLAG);
        assertThat(testProductEntry.getDefectFlag()).isEqualTo(UPDATED_DEFECT_FLAG);
        assertThat(testProductEntry.getVirtualFlag()).isEqualTo(UPDATED_VIRTUAL_FLAG);
        assertThat(testProductEntry.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testProductEntry.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testProductEntry.getGuid()).isEqualTo(UPDATED_GUID);
    }

    @Test
    @Transactional
    public void updateNonExistingProductEntry() throws Exception {
        int databaseSizeBeforeUpdate = productEntryRepository.findAll().size();

        // Create the ProductEntry
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductEntryMockMvc.perform(put("/api/product-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductEntry in the database
        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductEntry() throws Exception {
        // Initialize the database
        productEntryRepository.saveAndFlush(productEntry);
        int databaseSizeBeforeDelete = productEntryRepository.findAll().size();

        // Get the productEntry
        restProductEntryMockMvc.perform(delete("/api/product-entries/{id}", productEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductEntry> productEntryList = productEntryRepository.findAll();
        assertThat(productEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductEntry.class);
    }
}
