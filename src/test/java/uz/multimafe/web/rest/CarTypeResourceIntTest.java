package uz.multimafe.web.rest;

import uz.multimafe.LogisticsApp;

import uz.multimafe.domain.CarType;
import uz.multimafe.repository.CarTypeRepository;
import uz.multimafe.service.CarTypeService;
import uz.multimafe.service.dto.CarTypeDTO;
import uz.multimafe.service.mapper.CarTypeMapper;

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
 * Test class for the CarTypeResource REST controller.
 *
 * @see CarTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class CarTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CarTypeRepository carTypeRepository;

    @Autowired
    private CarTypeMapper carTypeMapper;

    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCarTypeMockMvc;

    private CarType carType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarTypeResource carTypeResource = new CarTypeResource(carTypeService);
        this.restCarTypeMockMvc = MockMvcBuilders.standaloneSetup(carTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarType createEntity(EntityManager em) {
        CarType carType = new CarType()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return carType;
    }

    @Before
    public void initTest() {
        carType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarType() throws Exception {
        int databaseSizeBeforeCreate = carTypeRepository.findAll().size();

        // Create the CarType
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(carType);

        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CarType testCarType = carTypeList.get(carTypeList.size() - 1);
        assertThat(testCarType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCarTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carTypeRepository.findAll().size();

        // Create the CarType with an existing ID
        CarType existingCarType = new CarType();
        existingCarType.setId(1L);
        CarTypeDTO existingCarTypeDTO = carTypeMapper.carTypeToCarTypeDTO(existingCarType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCarTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = carTypeRepository.findAll().size();
        // set the field null
        carType.setName(null);

        // Create the CarType, which fails.
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(carType);

        restCarTypeMockMvc.perform(post("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarTypes() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);

        // Get all the carTypeList
        restCarTypeMockMvc.perform(get("/api/car-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);

        // Get the carType
        restCarTypeMockMvc.perform(get("/api/car-types/{id}", carType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarType() throws Exception {
        // Get the carType
        restCarTypeMockMvc.perform(get("/api/car-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);
        int databaseSizeBeforeUpdate = carTypeRepository.findAll().size();

        // Update the carType
        CarType updatedCarType = carTypeRepository.findOne(carType.getId());
        updatedCarType
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(updatedCarType);

        restCarTypeMockMvc.perform(put("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeUpdate);
        CarType testCarType = carTypeList.get(carTypeList.size() - 1);
        assertThat(testCarType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCarType() throws Exception {
        int databaseSizeBeforeUpdate = carTypeRepository.findAll().size();

        // Create the CarType
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(carType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarTypeMockMvc.perform(put("/api/car-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CarType in the database
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarType() throws Exception {
        // Initialize the database
        carTypeRepository.saveAndFlush(carType);
        int databaseSizeBeforeDelete = carTypeRepository.findAll().size();

        // Get the carType
        restCarTypeMockMvc.perform(delete("/api/car-types/{id}", carType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarType> carTypeList = carTypeRepository.findAll();
        assertThat(carTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarType.class);
    }
}
