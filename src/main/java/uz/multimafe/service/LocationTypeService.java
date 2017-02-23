package uz.multimafe.service;

import uz.multimafe.domain.LocationType;
import uz.multimafe.repository.LocationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing LocationType.
 */
@Service
@Transactional
public class LocationTypeService {

    private final Logger log = LoggerFactory.getLogger(LocationTypeService.class);
    
    private final LocationTypeRepository locationTypeRepository;

    public LocationTypeService(LocationTypeRepository locationTypeRepository) {
        this.locationTypeRepository = locationTypeRepository;
    }

    /**
     * Save a locationType.
     *
     * @param locationType the entity to save
     * @return the persisted entity
     */
    public LocationType save(LocationType locationType) {
        log.debug("Request to save LocationType : {}", locationType);
        LocationType result = locationTypeRepository.save(locationType);
        return result;
    }

    /**
     *  Get all the locationTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LocationType> findAll(Pageable pageable) {
        log.debug("Request to get all LocationTypes");
        Page<LocationType> result = locationTypeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one locationType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LocationType findOne(Long id) {
        log.debug("Request to get LocationType : {}", id);
        LocationType locationType = locationTypeRepository.findOne(id);
        return locationType;
    }

    /**
     *  Delete the  locationType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LocationType : {}", id);
        locationTypeRepository.delete(id);
    }
}
