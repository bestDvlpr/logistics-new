package uz.multimafe.service;

import uz.multimafe.service.dto.ReceiptStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ReceiptStatus.
 */
public interface ReceiptStatusService {

    /**
     * Save a receiptStatus.
     *
     * @param receiptStatusDTO the entity to save
     * @return the persisted entity
     */
    ReceiptStatusDTO save(ReceiptStatusDTO receiptStatusDTO);

    /**
     *  Get all the receiptStatuses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReceiptStatusDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" receiptStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReceiptStatusDTO findOne(Long id);

    /**
     *  Delete the "id" receiptStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
