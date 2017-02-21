package uz.multimafe.service;

import uz.multimafe.service.dto.SellerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Seller.
 */
public interface SellerService {

    /**
     * Save a seller.
     *
     * @param sellerDTO the entity to save
     * @return the persisted entity
     */
    SellerDTO save(SellerDTO sellerDTO);

    /**
     *  Get all the sellers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SellerDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" seller.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SellerDTO findOne(Long id);

    /**
     *  Delete the "id" seller.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
