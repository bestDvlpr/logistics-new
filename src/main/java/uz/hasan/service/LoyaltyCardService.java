package uz.hasan.service;

import uz.hasan.service.dto.LoyaltyCardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing LoyaltyCard.
 */
public interface LoyaltyCardService {

    /**
     * Save a loyaltyCard.
     *
     * @param loyaltyCardDTO the entity to save
     * @return the persisted entity
     */
    LoyaltyCardDTO save(LoyaltyCardDTO loyaltyCardDTO);

    /**
     *  Get all the loyaltyCards.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LoyaltyCardDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" loyaltyCard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LoyaltyCardDTO findOne(Long id);

    /**
     *  Delete the "id" loyaltyCard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
