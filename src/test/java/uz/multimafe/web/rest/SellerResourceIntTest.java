package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.Seller;
import uz.multimafe.repository.SellerRepository;
import uz.multimafe.service.SellerService;
import uz.multimafe.service.dto.SellerDTO;
import uz.multimafe.service.mapper.SellerMapper;

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
 * Test class for the SellerResource REST controller.
 *
 * @see SellerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class SellerResourceIntTest {

    private static final String DEFAULT_SELLER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SELLER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_NAME = "BBBBBBBBBB";

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSellerMockMvc;

    private Seller seller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SellerResource sellerResource = new SellerResource(sellerService);
        this.restSellerMockMvc = MockMvcBuilders.standaloneSetup(sellerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seller createEntity(EntityManager em) {
        Seller seller = new Seller()
                .sellerID(DEFAULT_SELLER_ID)
                .sellerName(DEFAULT_SELLER_NAME);
        return seller;
    }

    @Before
    public void initTest() {
        seller = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeller() throws Exception {
        int databaseSizeBeforeCreate = sellerRepository.findAll().size();

        // Create the Seller
        SellerDTO sellerDTO = sellerMapper.sellerToSellerDTO(seller);

        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isCreated());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeCreate + 1);
        Seller testSeller = sellerList.get(sellerList.size() - 1);
        assertThat(testSeller.getSellerID()).isEqualTo(DEFAULT_SELLER_ID);
        assertThat(testSeller.getSellerName()).isEqualTo(DEFAULT_SELLER_NAME);
    }

    @Test
    @Transactional
    public void createSellerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sellerRepository.findAll().size();

        // Create the Seller with an existing ID
        Seller existingSeller = new Seller();
        existingSeller.setId(1L);
        SellerDTO existingSellerDTO = sellerMapper.sellerToSellerDTO(existingSeller);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSellerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSellerIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = sellerRepository.findAll().size();
        // set the field null
        seller.setSellerID(null);

        // Create the Seller, which fails.
        SellerDTO sellerDTO = sellerMapper.sellerToSellerDTO(seller);

        restSellerMockMvc.perform(post("/api/sellers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isBadRequest());

        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSellers() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);

        // Get all the sellerList
        restSellerMockMvc.perform(get("/api/sellers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seller.getId().intValue())))
            .andExpect(jsonPath("$.[*].sellerID").value(hasItem(DEFAULT_SELLER_ID.toString())))
            .andExpect(jsonPath("$.[*].sellerName").value(hasItem(DEFAULT_SELLER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getSeller() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);

        // Get the seller
        restSellerMockMvc.perform(get("/api/sellers/{id}", seller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seller.getId().intValue()))
            .andExpect(jsonPath("$.sellerID").value(DEFAULT_SELLER_ID.toString()))
            .andExpect(jsonPath("$.sellerName").value(DEFAULT_SELLER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeller() throws Exception {
        // Get the seller
        restSellerMockMvc.perform(get("/api/sellers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeller() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);
        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();

        // Update the seller
        Seller updatedSeller = sellerRepository.findOne(seller.getId());
        updatedSeller
                .sellerID(UPDATED_SELLER_ID)
                .sellerName(UPDATED_SELLER_NAME);
        SellerDTO sellerDTO = sellerMapper.sellerToSellerDTO(updatedSeller);

        restSellerMockMvc.perform(put("/api/sellers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isOk());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate);
        Seller testSeller = sellerList.get(sellerList.size() - 1);
        assertThat(testSeller.getSellerID()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testSeller.getSellerName()).isEqualTo(UPDATED_SELLER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSeller() throws Exception {
        int databaseSizeBeforeUpdate = sellerRepository.findAll().size();

        // Create the Seller
        SellerDTO sellerDTO = sellerMapper.sellerToSellerDTO(seller);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSellerMockMvc.perform(put("/api/sellers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellerDTO)))
            .andExpect(status().isCreated());

        // Validate the Seller in the database
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSeller() throws Exception {
        // Initialize the database
        sellerRepository.saveAndFlush(seller);
        int databaseSizeBeforeDelete = sellerRepository.findAll().size();

        // Get the seller
        restSellerMockMvc.perform(delete("/api/sellers/{id}", seller.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seller> sellerList = sellerRepository.findAll();
        assertThat(sellerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seller.class);
    }
}
