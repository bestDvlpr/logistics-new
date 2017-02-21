package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.CarModel;
import uz.multimafe.repository.CarModelRepository;
import uz.multimafe.service.CarModelService;
import uz.multimafe.service.dto.CarModelDTO;
import uz.multimafe.service.mapper.CarModelMapper;

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
 * Test class for the CarModelResource REST controller.
 *
 * @see CarModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class CarModelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarModelMapper carModelMapper;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCarModelMockMvc;

    private CarModel carModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarModelResource carModelResource = new CarModelResource(carModelService);
        this.restCarModelMockMvc = MockMvcBuilders.standaloneSetup(carModelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarModel createEntity(EntityManager em) {
        CarModel carModel = new CarModel()
                .name(DEFAULT_NAME);
        return carModel;
    }

    @Before
    public void initTest() {
        carModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarModel() throws Exception {
        int databaseSizeBeforeCreate = carModelRepository.findAll().size();

        // Create the CarModel
        CarModelDTO carModelDTO = carModelMapper.carModelToCarModelDTO(carModel);

        restCarModelMockMvc.perform(post("/api/car-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carModelDTO)))
            .andExpect(status().isCreated());

        // Validate the CarModel in the database
        List<CarModel> carModelList = carModelRepository.findAll();
        assertThat(carModelList).hasSize(databaseSizeBeforeCreate + 1);
        CarModel testCarModel = carModelList.get(carModelList.size() - 1);
        assertThat(testCarModel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCarModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carModelRepository.findAll().size();

        // Create the CarModel with an existing ID
        CarModel existingCarModel = new CarModel();
        existingCarModel.setId(1L);
        CarModelDTO existingCarModelDTO = carModelMapper.carModelToCarModelDTO(existingCarModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarModelMockMvc.perform(post("/api/car-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCarModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarModel> carModelList = carModelRepository.findAll();
        assertThat(carModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carModelRepository.findAll().size();
        // set the field null
        carModel.setName(null);

        // Create the CarModel, which fails.
        CarModelDTO carModelDTO = carModelMapper.carModelToCarModelDTO(carModel);

        restCarModelMockMvc.perform(post("/api/car-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carModelDTO)))
            .andExpect(status().isBadRequest());

        List<CarModel> carModelList = carModelRepository.findAll();
        assertThat(carModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarModels() throws Exception {
        // Initialize the database
        carModelRepository.saveAndFlush(carModel);

        // Get all the carModelList
        restCarModelMockMvc.perform(get("/api/car-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCarModel() throws Exception {
        // Initialize the database
        carModelRepository.saveAndFlush(carModel);

        // Get the carModel
        restCarModelMockMvc.perform(get("/api/car-models/{id}", carModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarModel() throws Exception {
        // Get the carModel
        restCarModelMockMvc.perform(get("/api/car-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarModel() throws Exception {
        // Initialize the database
        carModelRepository.saveAndFlush(carModel);
        int databaseSizeBeforeUpdate = carModelRepository.findAll().size();

        // Update the carModel
        CarModel updatedCarModel = carModelRepository.findOne(carModel.getId());
        updatedCarModel
                .name(UPDATED_NAME);
        CarModelDTO carModelDTO = carModelMapper.carModelToCarModelDTO(updatedCarModel);

        restCarModelMockMvc.perform(put("/api/car-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carModelDTO)))
            .andExpect(status().isOk());

        // Validate the CarModel in the database
        List<CarModel> carModelList = carModelRepository.findAll();
        assertThat(carModelList).hasSize(databaseSizeBeforeUpdate);
        CarModel testCarModel = carModelList.get(carModelList.size() - 1);
        assertThat(testCarModel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCarModel() throws Exception {
        int databaseSizeBeforeUpdate = carModelRepository.findAll().size();

        // Create the CarModel
        CarModelDTO carModelDTO = carModelMapper.carModelToCarModelDTO(carModel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarModelMockMvc.perform(put("/api/car-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carModelDTO)))
            .andExpect(status().isCreated());

        // Validate the CarModel in the database
        List<CarModel> carModelList = carModelRepository.findAll();
        assertThat(carModelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarModel() throws Exception {
        // Initialize the database
        carModelRepository.saveAndFlush(carModel);
        int databaseSizeBeforeDelete = carModelRepository.findAll().size();

        // Get the carModel
        restCarModelMockMvc.perform(delete("/api/car-models/{id}", carModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarModel> carModelList = carModelRepository.findAll();
        assertThat(carModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarModel.class);
    }
}
