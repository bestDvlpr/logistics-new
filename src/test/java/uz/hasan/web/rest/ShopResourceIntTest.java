package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.Shop;
import uz.hasan.repository.ShopRepository;
import uz.hasan.service.ShopService;
import uz.hasan.service.dto.ShopDTO;
import uz.hasan.service.mapper.ShopMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShopResource REST controller.
 *
 * @see ShopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class ShopResourceIntTest {

    private static final String DEFAULT_SHOP_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShopMockMvc;

    private Shop shop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShopResource shopResource = new ShopResource(shopService);
        this.restShopMockMvc = MockMvcBuilders.standaloneSetup(shopResource)
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
    public static Shop createEntity(EntityManager em) {
        Shop shop = new Shop()
                .shopId(DEFAULT_SHOP_ID)
                .name(DEFAULT_NAME);
        return shop;
    }

    @Before
    public void initTest() {
        shop = createEntity(em);
    }

    @Test
    @Transactional
    public void createShop() throws Exception {
        int databaseSizeBeforeCreate = shopRepository.findAll().size();

        // Create the Shop
        ShopDTO shopDTO = shopMapper.shopToShopDTO(shop);

        restShopMockMvc.perform(post("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopDTO)))
            .andExpect(status().isCreated());

        // Validate the Shop in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeCreate + 1);
        Shop testShop = shopList.get(shopList.size() - 1);
        assertThat(testShop.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testShop.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createShopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shopRepository.findAll().size();

        // Create the Shop with an existing ID
        Shop existingShop = new Shop();
        existingShop.setId(1L);
        ShopDTO existingShopDTO = shopMapper.shopToShopDTO(existingShop);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopMockMvc.perform(post("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingShopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShopIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopRepository.findAll().size();
        // set the field null
        shop.setShopId(null);

        // Create the Shop, which fails.
        ShopDTO shopDTO = shopMapper.shopToShopDTO(shop);

        restShopMockMvc.perform(post("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopDTO)))
            .andExpect(status().isBadRequest());

        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopRepository.findAll().size();
        // set the field null
        shop.setName(null);

        // Create the Shop, which fails.
        ShopDTO shopDTO = shopMapper.shopToShopDTO(shop);

        restShopMockMvc.perform(post("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopDTO)))
            .andExpect(status().isBadRequest());

        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShops() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);

        // Get all the shopList
        restShopMockMvc.perform(get("/api/shops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shop.getId().intValue())))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getShop() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);

        // Get the shop
        restShopMockMvc.perform(get("/api/shops/{id}", shop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shop.getId().intValue()))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShop() throws Exception {
        // Get the shop
        restShopMockMvc.perform(get("/api/shops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShop() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);
        int databaseSizeBeforeUpdate = shopRepository.findAll().size();

        // Update the shop
        Shop updatedShop = shopRepository.findOne(shop.getId());
        updatedShop
                .shopId(UPDATED_SHOP_ID)
                .name(UPDATED_NAME);
        ShopDTO shopDTO = shopMapper.shopToShopDTO(updatedShop);

        restShopMockMvc.perform(put("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopDTO)))
            .andExpect(status().isOk());

        // Validate the Shop in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeUpdate);
        Shop testShop = shopList.get(shopList.size() - 1);
        assertThat(testShop.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testShop.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingShop() throws Exception {
        int databaseSizeBeforeUpdate = shopRepository.findAll().size();

        // Create the Shop
        ShopDTO shopDTO = shopMapper.shopToShopDTO(shop);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShopMockMvc.perform(put("/api/shops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopDTO)))
            .andExpect(status().isCreated());

        // Validate the Shop in the database
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShop() throws Exception {
        // Initialize the database
        shopRepository.saveAndFlush(shop);
        int databaseSizeBeforeDelete = shopRepository.findAll().size();

        // Get the shop
        restShopMockMvc.perform(delete("/api/shops/{id}", shop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shop> shopList = shopRepository.findAll();
        assertThat(shopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shop.class);
    }
}
