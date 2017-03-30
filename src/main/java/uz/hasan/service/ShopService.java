package uz.hasan.service;

import uz.hasan.service.dto.ShopDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Shop.
 */
public interface ShopService {

    /**
     * Save a shop.
     *
     * @param shopDTO the entity to save
     * @return the persisted entity
     */
    ShopDTO save(ShopDTO shopDTO);

    /**
     *  Get all the shops.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ShopDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" shop.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ShopDTO findOne(Long id);

    /**
     *  Delete the "id" shop.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
