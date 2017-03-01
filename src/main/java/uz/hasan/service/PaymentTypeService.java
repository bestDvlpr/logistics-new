package uz.hasan.service;

import uz.hasan.service.dto.PaymentTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PaymentType.
 */
public interface PaymentTypeService {

    /**
     * Save a paymentType.
     *
     * @param paymentTypeDTO the entity to save
     * @return the persisted entity
     */
    PaymentTypeDTO save(PaymentTypeDTO paymentTypeDTO);

    /**
     *  Get all the paymentTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaymentTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" paymentType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentTypeDTO findOne(Long id);

    /**
     *  Delete the "id" paymentType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
