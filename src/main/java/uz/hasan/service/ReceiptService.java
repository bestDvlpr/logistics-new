package uz.hasan.service;

import uz.hasan.service.dto.ReceiptDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

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
    Page<ReceiptProductEntriesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" receipt.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReceiptProductEntriesDTO findOne(Long id);

    /**
     *  Delete the "id" receipt.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get all the new receipts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReceiptProductEntriesDTO> findAllNewReceipts(Pageable pageable);

    /**
     *  Get all the applied receipts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReceiptProductEntriesDTO> findAppliedReceipts(Pageable pageable);

    /**
     * Send a receipt.
     *
     * @param receiptDTO the entity to send
     * @return the persisted entity
     */
    ReceiptDTO sendOrder(ReceiptProductEntriesDTO receiptDTO);

    /**
     * Count new receipts.
     *
     * @return count of new receipts
     */
    Long countNewReceipts();

    /**
     * Count applied receipts.
     *
     * @return count of applied receipts
     */
    Long countAppliedReceipts();

    /**
     * Attach receipt products to car(s).
     *
     * @return receipt with attached to car products
     */
    ReceiptDTO attachOrder(ReceiptProductEntriesDTO receiptDTO);
}
