package uz.hasan.service;

import uz.hasan.domain.ProductEntry;
import uz.hasan.service.dto.ProductEntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing ProductEntry.
 */
public interface ProductEntryService {

    /**
     * Save a productEntry.
     *
     * @param productEntryDTO the entity to save
     * @return the persisted entity
     */
    ProductEntryDTO save(ProductEntryDTO productEntryDTO);

    /**
     *  Get all the productEntries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProductEntryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" productEntry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductEntryDTO findOne(Long id);

    /**
     *  Delete the "id" productEntry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get all the productEntries by receiptId.
     *
     *  @param receiptId the receipt ID
     *  @return the list of entities
     */
    List<ProductEntryDTO> findAllByReceiptId(Long receiptId);

    /**
     *  Get new the productEntries by car number.
     *
     *  @param carNumber the car number
     *  @return the list of entities
     */
    List<ProductEntryDTO> findNewProductsByCarNumber(String carNumber);

    /**
     *  Get all the productEntries by car number.
     *
     *  @param carNumber the car number
     *  @return the list of entities
     */
    List<ProductEntryDTO> findLastProductsByCarNumber(String carNumber);

    /**
     * Deliver a productEntries.
     *
     * @param productEntryDTOs the entities to deliver
     * @return the persisted entities
     */
    void deliver(List<ProductEntryDTO> productEntryDTOs, HttpServletResponse response);

    /**
     * Make productEntries as delivered.
     *
     * @param productEntryDTOs the entities delivered
     * @return the persisted entities
     */
    List<ProductEntryDTO> delivered(List<ProductEntryDTO> productEntryDTOs);
}
