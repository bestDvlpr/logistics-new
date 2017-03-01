package uz.hasan.service.impl;

import uz.hasan.service.DriverStatusService;
import uz.hasan.domain.DriverStatus;
import uz.hasan.repository.DriverStatusRepository;
import uz.hasan.service.dto.DriverStatusDTO;
import uz.hasan.service.mapper.DriverStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing DriverStatus.
 */
@Service
@Transactional
public class DriverStatusServiceImpl implements DriverStatusService{

    private final Logger log = LoggerFactory.getLogger(DriverStatusServiceImpl.class);

    private final DriverStatusRepository driverStatusRepository;

    private final DriverStatusMapper driverStatusMapper;

    public DriverStatusServiceImpl(DriverStatusRepository driverStatusRepository, DriverStatusMapper driverStatusMapper) {
        this.driverStatusRepository = driverStatusRepository;
        this.driverStatusMapper = driverStatusMapper;
    }

    /**
     * Save a driverStatus.
     *
     * @param driverStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DriverStatusDTO save(DriverStatusDTO driverStatusDTO) {
        log.debug("Request to save DriverStatus : {}", driverStatusDTO);
        DriverStatus driverStatus = driverStatusMapper.driverStatusDTOToDriverStatus(driverStatusDTO);
        driverStatus = driverStatusRepository.save(driverStatus);
        DriverStatusDTO result = driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus);
        return result;
    }

    /**
     *  Get all the driverStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DriverStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DriverStatuses");
        Page<DriverStatus> result = driverStatusRepository.findAll(pageable);
        return result.map(driverStatus -> driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus));
    }

    /**
     *  Get one driverStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DriverStatusDTO findOne(Long id) {
        log.debug("Request to get DriverStatus : {}", id);
        DriverStatus driverStatus = driverStatusRepository.findOne(id);
        DriverStatusDTO driverStatusDTO = driverStatusMapper.driverStatusToDriverStatusDTO(driverStatus);
        return driverStatusDTO;
    }

    /**
     *  Delete the  driverStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DriverStatus : {}", id);
        driverStatusRepository.delete(id);
    }
}
