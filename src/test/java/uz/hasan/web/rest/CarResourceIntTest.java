package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.Car;
import uz.hasan.domain.CarModel;
import uz.hasan.domain.CarType;
import uz.hasan.repository.CarRepository;
import uz.hasan.service.CarService;
import uz.hasan.service.dto.CarDTO;
import uz.hasan.service.mapper.CarMapper;
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

import uz.hasan.domain.enumeration.CarStatus;
/**
 * Test class for the CarResource REST controller.
 *
 * @see CarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
@Transactional
public class CarResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final CarStatus DEFAULT_STATUS = CarStatus.IDLE;
    private static final CarStatus UPDATED_STATUS = CarStatus.BUSY;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarMockMvc;

    private Car car;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarResource carResource = new CarResource(carService);
        this.restCarMockMvc = MockMvcBuilders.standaloneSetup(carResource)
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
    public static Car createEntity(EntityManager em) {
        Car car = new Car()
                .number(DEFAULT_NUMBER)
                .deleted(DEFAULT_DELETED)
                .status(DEFAULT_STATUS);
        // Add required entity
        CarModel carModel = CarModelResourceIntTest.createEntity(em);
        em.persist(carModel);
        em.flush();
        car.setCarModel(carModel);
        // Add required entity
        CarType type = CarTypeResourceIntTest.createEntity(em);
        em.persist(type);
        em.flush();
        car.setType(type);
        return car;
    }

    @Before
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car
        CarDTO carDTO = carMapper.carToCarDTO(car);

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate + 1);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCar.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testCar.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car with an existing ID
        Car existingCar = new Car();
        existingCar.setId(1L);
        CarDTO existingCarDTO = carMapper.carToCarDTO(existingCar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setNumber(null);

        // Create the Car, which fails.
        CarDTO carDTO = carMapper.carToCarDTO(car);

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setDeleted(null);

        // Create the Car, which fails.
        CarDTO carDTO = carMapper.carToCarDTO(car);

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCars() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get all the carList
        restCarMockMvc.perform(get("/api/cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);
        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Car updatedCar = carRepository.findOne(car.getId());
        updatedCar
                .number(UPDATED_NUMBER)
                .deleted(UPDATED_DELETED)
                .status(UPDATED_STATUS);
        CarDTO carDTO = carMapper.carToCarDTO(updatedCar);

        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCar.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testCar.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Create the Car
        CarDTO carDTO = carMapper.carToCarDTO(car);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carDTO)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);
        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Get the car
        restCarMockMvc.perform(delete("/api/cars/{id}", car.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car.class);
    }
}
