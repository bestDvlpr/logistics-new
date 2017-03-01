package uz.hasan.service;

import uz.hasan.service.dto.PayTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PayType.
 */
public interface PayTypeService {

    /**
     * Save a payType.
     *
     * @param payTypeDTO the entity to save
     * @return the persisted entity
     */
    PayTypeDTO save(PayTypeDTO payTypeDTO);

    /**
     *  Get all the payTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PayTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" payType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PayTypeDTO findOne(Long id);

    /**
     *  Delete the "id" payType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
