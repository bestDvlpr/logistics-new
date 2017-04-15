package uz.hasan.service;

import uz.hasan.service.dto.ReceiptDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

import javax.servlet.http.HttpServletResponse;
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
    ReceiptProductEntriesDTO save(ReceiptProductEntriesDTO receiptDTO);

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
     *  Get all the new receipts by shop id.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReceiptProductEntriesDTO> findAllNewReceiptsByShopId(Pageable pageable);


    /**
     *  Get all receipts by shop id.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ReceiptProductEntriesDTO> findAllReceiptsByShopId(Pageable pageable);

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
    List<ReceiptProductEntriesDTO> sendOrder(ReceiptProductEntriesDTO receiptDTO);

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
    ReceiptProductEntriesDTO attachOrder(ReceiptProductEntriesDTO receiptDTO);

    /**
     * Download receipt as ms-word document.
     */
    void download(Long receiptId, HttpServletResponse response);

    /**
     * Mark receipt and its products as delivered.
     *
     * @return receipt with attached to car products
     */
    ReceiptProductEntriesDTO delivered(ReceiptProductEntriesDTO receiptDTO);

    /**
     * Mark receipt and its products as taken out.
     *
     * @return receipt with attached to car products
     */
    ReceiptProductEntriesDTO takenOut(ReceiptProductEntriesDTO receiptDTO);
}
