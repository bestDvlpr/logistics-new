package uz.hasan.service;

import uz.hasan.service.dto.CarTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CarType.
 */
public interface CarTypeService {

    /**
     * Save a carType.
     *
     * @param carTypeDTO the entity to save
     * @return the persisted entity
     */
    CarTypeDTO save(CarTypeDTO carTypeDTO);

    /**
     *  Get all the carTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CarTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" carType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarTypeDTO findOne(Long id);

    /**
     *  Delete the "id" carType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
