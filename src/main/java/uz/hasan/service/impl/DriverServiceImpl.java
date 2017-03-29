package uz.hasan.service.impl;

import uz.hasan.service.DriverService;
import uz.hasan.domain.Driver;
import uz.hasan.repository.DriverRepository;
import uz.hasan.service.dto.DriverDTO;
import uz.hasan.service.mapper.DriverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Driver.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService{

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);
    
    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.driverDTOToDriver(driverDTO);
        driver = driverRepository.save(driver);
        DriverDTO result = driverMapper.driverToDriverDTO(driver);
        return result;
    }

    /**
     *  Get all the drivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DriverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        Page<Driver> result = driverRepository.findAll(pageable);
        return result.map(driver -> driverMapper.driverToDriverDTO(driver));
    }

    /**
     *  Get one driver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DriverDTO findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        Driver driver = driverRepository.findOneWithEagerRelationships(id);
        DriverDTO driverDTO = driverMapper.driverToDriverDTO(driver);
        return driverDTO;
    }

    /**
     *  Delete the  driver by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.delete(id);
    }
}
