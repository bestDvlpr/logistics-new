package uz.hasan.service;

import uz.hasan.service.dto.DriverStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DriverStatus.
 */
public interface DriverStatusService {

    /**
     * Save a driverStatus.
     *
     * @param driverStatusDTO the entity to save
     * @return the persisted entity
     */
    DriverStatusDTO save(DriverStatusDTO driverStatusDTO);

    /**
     *  Get all the driverStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DriverStatusDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" driverStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DriverStatusDTO findOne(Long id);

    /**
     *  Delete the "id" driverStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
