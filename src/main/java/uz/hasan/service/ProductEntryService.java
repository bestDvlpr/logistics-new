package uz.hasan.service;

import uz.hasan.service.dto.ProductEntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

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
}
