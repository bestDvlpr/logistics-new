package uz.hasan.service;

import uz.hasan.service.dto.PayMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PayMaster.
 */
public interface PayMasterService {

    /**
     * Save a payMaster.
     *
     * @param payMasterDTO the entity to save
     * @return the persisted entity
     */
    PayMasterDTO save(PayMasterDTO payMasterDTO);

    /**
     *  Get all the payMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PayMasterDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" payMaster.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PayMasterDTO findOne(Long id);

    /**
     *  Delete the "id" payMaster.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
