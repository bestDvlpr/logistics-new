package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.Product;
import uz.multimafe.repository.ProductRepository;
import uz.multimafe.service.ProductService;
import uz.multimafe.service.dto.ProductDTO;
import uz.multimafe.service.mapper.ProductMapper;

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
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_SAP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SAP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SAP_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SAP_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final String DEFAULT_UOM = "AAAAAAAAAA";
    private static final String UPDATED_UOM = "BBBBBBBBBB";

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
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
                .sapCode(DEFAULT_SAP_CODE)
                .sapType(DEFAULT_SAP_TYPE)
                .name(DEFAULT_NAME)
                .serial(DEFAULT_SERIAL)
                .qty(DEFAULT_QTY)
                .uom(DEFAULT_UOM)
                .price(DEFAULT_PRICE)
                .deliveryFlag(DEFAULT_DELIVERY_FLAG)
                .hallFlag(DEFAULT_HALL_FLAG)
                .defectFlag(DEFAULT_DEFECT_FLAG)
                .virtualFlag(DEFAULT_VIRTUAL_FLAG)
                .reason(DEFAULT_REASON)
                .comment(DEFAULT_COMMENT)
                .guid(DEFAULT_GUID);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getSapCode()).isEqualTo(DEFAULT_SAP_CODE);
        assertThat(testProduct.getSapType()).isEqualTo(DEFAULT_SAP_TYPE);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testProduct.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testProduct.getUom()).isEqualTo(DEFAULT_UOM);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getDeliveryFlag()).isEqualTo(DEFAULT_DELIVERY_FLAG);
        assertThat(testProduct.getHallFlag()).isEqualTo(DEFAULT_HALL_FLAG);
        assertThat(testProduct.getDefectFlag()).isEqualTo(DEFAULT_DEFECT_FLAG);
        assertThat(testProduct.getVirtualFlag()).isEqualTo(DEFAULT_VIRTUAL_FLAG);
        assertThat(testProduct.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testProduct.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testProduct.getGuid()).isEqualTo(DEFAULT_GUID);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        ProductDTO existingProductDTO = productMapper.productToProductDTO(existingProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSapCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSapCode(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSapTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSapType(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setQty(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUomIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setUom(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setPrice(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setDeliveryFlag(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHallFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setHallFlag(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDefectFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setDefectFlag(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVirtualFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setVirtualFlag(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setGuid(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].sapCode").value(hasItem(DEFAULT_SAP_CODE.toString())))
            .andExpect(jsonPath("$.[*].sapType").value(hasItem(DEFAULT_SAP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM.toString())))
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
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.sapCode").value(DEFAULT_SAP_CODE.toString()))
            .andExpect(jsonPath("$.sapType").value(DEFAULT_SAP_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.uom").value(DEFAULT_UOM.toString()))
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
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct
                .sapCode(UPDATED_SAP_CODE)
                .sapType(UPDATED_SAP_TYPE)
                .name(UPDATED_NAME)
                .serial(UPDATED_SERIAL)
                .qty(UPDATED_QTY)
                .uom(UPDATED_UOM)
                .price(UPDATED_PRICE)
                .deliveryFlag(UPDATED_DELIVERY_FLAG)
                .hallFlag(UPDATED_HALL_FLAG)
                .defectFlag(UPDATED_DEFECT_FLAG)
                .virtualFlag(UPDATED_VIRTUAL_FLAG)
                .reason(UPDATED_REASON)
                .comment(UPDATED_COMMENT)
                .guid(UPDATED_GUID);
        ProductDTO productDTO = productMapper.productToProductDTO(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getSapCode()).isEqualTo(UPDATED_SAP_CODE);
        assertThat(testProduct.getSapType()).isEqualTo(UPDATED_SAP_TYPE);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testProduct.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testProduct.getUom()).isEqualTo(UPDATED_UOM);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getDeliveryFlag()).isEqualTo(UPDATED_DELIVERY_FLAG);
        assertThat(testProduct.getHallFlag()).isEqualTo(UPDATED_HALL_FLAG);
        assertThat(testProduct.getDefectFlag()).isEqualTo(UPDATED_DEFECT_FLAG);
        assertThat(testProduct.getVirtualFlag()).isEqualTo(UPDATED_VIRTUAL_FLAG);
        assertThat(testProduct.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testProduct.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testProduct.getGuid()).isEqualTo(UPDATED_GUID);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
    }
}
