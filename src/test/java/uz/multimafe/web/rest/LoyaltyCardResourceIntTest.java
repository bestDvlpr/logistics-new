package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.LoyaltyCard;
import uz.multimafe.repository.LoyaltyCardRepository;
import uz.multimafe.service.LoyaltyCardService;
import uz.multimafe.service.dto.LoyaltyCardDTO;
import uz.multimafe.service.mapper.LoyaltyCardMapper;

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
 * Test class for the LoyaltyCardResource REST controller.
 *
 * @see LoyaltyCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class LoyaltyCardResourceIntTest {

    private static final String DEFAULT_LOYALTY_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOYALTY_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LOYALTY_CARD_BONUS = "AAAAAAAAAA";
    private static final String UPDATED_LOYALTY_CARD_BONUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_LOYALTY_CARD_AMOUNT = 1;
    private static final Integer UPDATED_LOYALTY_CARD_AMOUNT = 2;

    @Autowired
    private LoyaltyCardRepository loyaltyCardRepository;

    @Autowired
    private LoyaltyCardMapper loyaltyCardMapper;

    @Autowired
    private LoyaltyCardService loyaltyCardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLoyaltyCardMockMvc;

    private LoyaltyCard loyaltyCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoyaltyCardResource loyaltyCardResource = new LoyaltyCardResource(loyaltyCardService);
        this.restLoyaltyCardMockMvc = MockMvcBuilders.standaloneSetup(loyaltyCardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoyaltyCard createEntity(EntityManager em) {
        LoyaltyCard loyaltyCard = new LoyaltyCard()
                .loyaltyCardID(DEFAULT_LOYALTY_CARD_ID)
                .loyaltyCardBonus(DEFAULT_LOYALTY_CARD_BONUS)
                .loyaltyCardAmount(DEFAULT_LOYALTY_CARD_AMOUNT);
        return loyaltyCard;
    }

    @Before
    public void initTest() {
        loyaltyCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoyaltyCard() throws Exception {
        int databaseSizeBeforeCreate = loyaltyCardRepository.findAll().size();

        // Create the LoyaltyCard
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);

        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isCreated());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeCreate + 1);
        LoyaltyCard testLoyaltyCard = loyaltyCardList.get(loyaltyCardList.size() - 1);
        assertThat(testLoyaltyCard.getLoyaltyCardID()).isEqualTo(DEFAULT_LOYALTY_CARD_ID);
        assertThat(testLoyaltyCard.getLoyaltyCardBonus()).isEqualTo(DEFAULT_LOYALTY_CARD_BONUS);
        assertThat(testLoyaltyCard.getLoyaltyCardAmount()).isEqualTo(DEFAULT_LOYALTY_CARD_AMOUNT);
    }

    @Test
    @Transactional
    public void createLoyaltyCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loyaltyCardRepository.findAll().size();

        // Create the LoyaltyCard with an existing ID
        LoyaltyCard existingLoyaltyCard = new LoyaltyCard();
        existingLoyaltyCard.setId(1L);
        LoyaltyCardDTO existingLoyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(existingLoyaltyCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLoyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoyaltyCardIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyaltyCardRepository.findAll().size();
        // set the field null
        loyaltyCard.setLoyaltyCardID(null);

        // Create the LoyaltyCard, which fails.
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);

        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoyaltyCardBonusIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyaltyCardRepository.findAll().size();
        // set the field null
        loyaltyCard.setLoyaltyCardBonus(null);

        // Create the LoyaltyCard, which fails.
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);

        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoyaltyCardAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyaltyCardRepository.findAll().size();
        // set the field null
        loyaltyCard.setLoyaltyCardAmount(null);

        // Create the LoyaltyCard, which fails.
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);

        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoyaltyCards() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);

        // Get all the loyaltyCardList
        restLoyaltyCardMockMvc.perform(get("/api/loyalty-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loyaltyCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].loyaltyCardID").value(hasItem(DEFAULT_LOYALTY_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].loyaltyCardBonus").value(hasItem(DEFAULT_LOYALTY_CARD_BONUS.toString())))
            .andExpect(jsonPath("$.[*].loyaltyCardAmount").value(hasItem(DEFAULT_LOYALTY_CARD_AMOUNT)));
    }

    @Test
    @Transactional
    public void getLoyaltyCard() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);

        // Get the loyaltyCard
        restLoyaltyCardMockMvc.perform(get("/api/loyalty-cards/{id}", loyaltyCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loyaltyCard.getId().intValue()))
            .andExpect(jsonPath("$.loyaltyCardID").value(DEFAULT_LOYALTY_CARD_ID.toString()))
            .andExpect(jsonPath("$.loyaltyCardBonus").value(DEFAULT_LOYALTY_CARD_BONUS.toString()))
            .andExpect(jsonPath("$.loyaltyCardAmount").value(DEFAULT_LOYALTY_CARD_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingLoyaltyCard() throws Exception {
        // Get the loyaltyCard
        restLoyaltyCardMockMvc.perform(get("/api/loyalty-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoyaltyCard() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);
        int databaseSizeBeforeUpdate = loyaltyCardRepository.findAll().size();

        // Update the loyaltyCard
        LoyaltyCard updatedLoyaltyCard = loyaltyCardRepository.findOne(loyaltyCard.getId());
        updatedLoyaltyCard
                .loyaltyCardID(UPDATED_LOYALTY_CARD_ID)
                .loyaltyCardBonus(UPDATED_LOYALTY_CARD_BONUS)
                .loyaltyCardAmount(UPDATED_LOYALTY_CARD_AMOUNT);
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(updatedLoyaltyCard);

        restLoyaltyCardMockMvc.perform(put("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isOk());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeUpdate);
        LoyaltyCard testLoyaltyCard = loyaltyCardList.get(loyaltyCardList.size() - 1);
        assertThat(testLoyaltyCard.getLoyaltyCardID()).isEqualTo(UPDATED_LOYALTY_CARD_ID);
        assertThat(testLoyaltyCard.getLoyaltyCardBonus()).isEqualTo(UPDATED_LOYALTY_CARD_BONUS);
        assertThat(testLoyaltyCard.getLoyaltyCardAmount()).isEqualTo(UPDATED_LOYALTY_CARD_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingLoyaltyCard() throws Exception {
        int databaseSizeBeforeUpdate = loyaltyCardRepository.findAll().size();

        // Create the LoyaltyCard
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLoyaltyCardMockMvc.perform(put("/api/loyalty-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isCreated());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLoyaltyCard() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);
        int databaseSizeBeforeDelete = loyaltyCardRepository.findAll().size();

        // Get the loyaltyCard
        restLoyaltyCardMockMvc.perform(delete("/api/loyalty-cards/{id}", loyaltyCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoyaltyCard.class);
    }
}
