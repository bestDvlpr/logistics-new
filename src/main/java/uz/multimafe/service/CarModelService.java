package uz.multimafe.service;

import uz.multimafe.service.dto.CarModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CarModel.
 */
public interface CarModelService {

    /**
     * Save a carModel.
     *
     * @param carModelDTO the entity to save
     * @return the persisted entity
     */
    CarModelDTO save(CarModelDTO carModelDTO);

    /**
     *  Get all the carModels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CarModelDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" carModel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarModelDTO findOne(Long id);

    /**
     *  Delete the "id" carModel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
