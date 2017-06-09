package uz.hasan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Location;
import uz.hasan.domain.enumeration.LocationType;
import uz.hasan.repository.LocationRepository;

import java.util.List;

/**
 * Service Implementation for managing Location.
 */
@Service
@Transactional
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Save a location.
     *
     * @param location the entity to save
     * @return the persisted entity
     */
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        Location result = locationRepository.save(location);
        return result;
    }

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Location> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        Page<Location> result = locationRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one location by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Location findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        return location;
    }

    /**
     * Delete the  location by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }

    /**
     * Get location children by parent ID.
     *
     * @param parentId the id of the parent entity
     * @return the entity list
     */
    public Page<Location> findChildren(Pageable pageable, Long parentId) {
        if (parentId == null) {
            return null;
        }
        log.debug("Request to get children of Location: {}", parentId);
        return locationRepository.findByParentId(pageable, parentId);
    }

    /**
     * Get location children by parent ID.
     *
     * @param parentId the id of the parent entity
     * @return the entity list
     */
    public List<Location> findChildren(Long parentId) {
        if (parentId == null) {
            return null;
        }
        log.debug("Request to get children of Location: {}", parentId);
        return locationRepository.findByParentId(parentId);
    }

    /**
     * Get country locations.
     *
     * @return the entity list
     */
    public Page<Location> findByParentIdIsNull(Pageable pageable) {
        log.debug("Request to get country Locations");
        return locationRepository.findByParentIdIsNull(pageable);
    }

    /**
     * Get country locations.
     *
     * @return the entity list
     */
    public List<Location> findByParentIdIsNull() {
        log.debug("Request to get country Locations");
        return locationRepository.findByParentIdIsNull();
    }

    /**
     * Get all the locations.
     *
     * @return the list of entities
     */
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    /**
     * Get location children by parent ID.
     *
     * @param type the id of the parent entity
     * @return the entity list
     */
    public List<Location> findByType(LocationType type) {
        if (type == null) {
            return null;
        }
        return locationRepository.findByType(type);
    }
}
