package uz.hasan.service;

import uz.hasan.domain.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PhoneNumber.
 */
public interface PhoneNumberService {

    /**
     * Save a phoneNumber.
     *
     * @param phoneNumber the entity to save
     * @return the persisted entity
     */
    PhoneNumber save(PhoneNumber phoneNumber);

    /**
     *  Get all the phoneNumbers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PhoneNumber> findAll(Pageable pageable);

    /**
     *  Get the "id" phoneNumber.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PhoneNumber findOne(Long id);

    /**
     *  Delete the "id" phoneNumber.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
