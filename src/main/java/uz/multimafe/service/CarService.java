package uz.multimafe.service;

import uz.multimafe.service.dto.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Car.
 */
public interface CarService {

    /**
     * Save a car.
     *
     * @param carDTO the entity to save
     * @return the persisted entity
     */
    CarDTO save(CarDTO carDTO);

    /**
     *  Get all the cars.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CarDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarDTO findOne(Long id);

    /**
     *  Delete the "id" car.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
