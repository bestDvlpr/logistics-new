package uz.hasan.service.impl;

import uz.hasan.service.CarService;
import uz.hasan.domain.Car;
import uz.hasan.repository.CarRepository;
import uz.hasan.service.dto.CarDTO;
import uz.hasan.service.mapper.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Car.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService{

    private final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
    
    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    /**
     * Save a car.
     *
     * @param carDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarDTO save(CarDTO carDTO) {
        log.debug("Request to save Car : {}", carDTO);
        Car car = carMapper.carDTOToCar(carDTO);
        car = carRepository.save(car);
        CarDTO result = carMapper.carToCarDTO(car);
        return result;
    }

    /**
     *  Get all the cars.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cars");
        Page<Car> result = carRepository.findAll(pageable);
        return result.map(car -> carMapper.carToCarDTO(car));
    }

    /**
     *  Get one car by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarDTO findOne(Long id) {
        log.debug("Request to get Car : {}", id);
        Car car = carRepository.findOne(id);
        CarDTO carDTO = carMapper.carToCarDTO(car);
        return carDTO;
    }

    /**
     *  Delete the  car by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car : {}", id);
        carRepository.delete(id);
    }
}
