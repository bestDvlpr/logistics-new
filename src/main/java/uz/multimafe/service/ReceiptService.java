package uz.multimafe.service;

import uz.multimafe.service.dto.ReceiptDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Receipt.
 */
public interface ReceiptService {

    /**
     * Save a receipt.
     *
     * @param receiptDTO the entity to save
     * @return the persisted entity
     */
    ReceiptDTO save(ReceiptDTO receiptDTO);

    /**
     *  Get all the receipts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReceiptDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" receipt.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReceiptDTO findOne(Long id);

    /**
     *  Delete the "id" receipt.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
