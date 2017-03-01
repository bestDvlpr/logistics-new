package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.CarColor;
import uz.hasan.repository.CarColorRepository;

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
 * Test class for the CarColorResource REST controller.
 *
 * @see CarColorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class CarColorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CarColorRepository carColorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCarColorMockMvc;

    private CarColor carColor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CarColorResource carColorResource = new CarColorResource(carColorRepository);
        this.restCarColorMockMvc = MockMvcBuilders.standaloneSetup(carColorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarColor createEntity(EntityManager em) {
        CarColor carColor = new CarColor()
                .name(DEFAULT_NAME);
        return carColor;
    }

    @Before
    public void initTest() {
        carColor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarColor() throws Exception {
        int databaseSizeBeforeCreate = carColorRepository.findAll().size();

        // Create the CarColor

        restCarColorMockMvc.perform(post("/api/car-colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carColor)))
            .andExpect(status().isCreated());

        // Validate the CarColor in the database
        List<CarColor> carColorList = carColorRepository.findAll();
        assertThat(carColorList).hasSize(databaseSizeBeforeCreate + 1);
        CarColor testCarColor = carColorList.get(carColorList.size() - 1);
        assertThat(testCarColor.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCarColorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carColorRepository.findAll().size();

        // Create the CarColor with an existing ID
        CarColor existingCarColor = new CarColor();
        existingCarColor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarColorMockMvc.perform(post("/api/car-colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCarColor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarColor> carColorList = carColorRepository.findAll();
        assertThat(carColorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carColorRepository.findAll().size();
        // set the field null
        carColor.setName(null);

        // Create the CarColor, which fails.

        restCarColorMockMvc.perform(post("/api/car-colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carColor)))
            .andExpect(status().isBadRequest());

        List<CarColor> carColorList = carColorRepository.findAll();
        assertThat(carColorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarColors() throws Exception {
        // Initialize the database
        carColorRepository.saveAndFlush(carColor);

        // Get all the carColorList
        restCarColorMockMvc.perform(get("/api/car-colors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carColor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCarColor() throws Exception {
        // Initialize the database
        carColorRepository.saveAndFlush(carColor);

        // Get the carColor
        restCarColorMockMvc.perform(get("/api/car-colors/{id}", carColor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carColor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarColor() throws Exception {
        // Get the carColor
        restCarColorMockMvc.perform(get("/api/car-colors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarColor() throws Exception {
        // Initialize the database
        carColorRepository.saveAndFlush(carColor);
        int databaseSizeBeforeUpdate = carColorRepository.findAll().size();

        // Update the carColor
        CarColor updatedCarColor = carColorRepository.findOne(carColor.getId());
        updatedCarColor
                .name(UPDATED_NAME);

        restCarColorMockMvc.perform(put("/api/car-colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarColor)))
            .andExpect(status().isOk());

        // Validate the CarColor in the database
        List<CarColor> carColorList = carColorRepository.findAll();
        assertThat(carColorList).hasSize(databaseSizeBeforeUpdate);
        CarColor testCarColor = carColorList.get(carColorList.size() - 1);
        assertThat(testCarColor.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCarColor() throws Exception {
        int databaseSizeBeforeUpdate = carColorRepository.findAll().size();

        // Create the CarColor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarColorMockMvc.perform(put("/api/car-colors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carColor)))
            .andExpect(status().isCreated());

        // Validate the CarColor in the database
        List<CarColor> carColorList = carColorRepository.findAll();
        assertThat(carColorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarColor() throws Exception {
        // Initialize the database
        carColorRepository.saveAndFlush(carColor);
        int databaseSizeBeforeDelete = carColorRepository.findAll().size();

        // Get the carColor
        restCarColorMockMvc.perform(delete("/api/car-colors/{id}", carColor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarColor> carColorList = carColorRepository.findAll();
        assertThat(carColorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarColor.class);
    }
}
