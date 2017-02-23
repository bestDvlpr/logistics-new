package uz.multimafe.service.impl;

import uz.multimafe.service.CarTypeService;
import uz.multimafe.domain.CarType;
import uz.multimafe.repository.CarTypeRepository;
import uz.multimafe.service.dto.CarTypeDTO;
import uz.multimafe.service.mapper.CarTypeMapper;
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
 * Service Implementation for managing CarType.
 */
@Service
@Transactional
public class CarTypeServiceImpl implements CarTypeService{

    private final Logger log = LoggerFactory.getLogger(CarTypeServiceImpl.class);
    
    private final CarTypeRepository carTypeRepository;

    private final CarTypeMapper carTypeMapper;

    public CarTypeServiceImpl(CarTypeRepository carTypeRepository, CarTypeMapper carTypeMapper) {
        this.carTypeRepository = carTypeRepository;
        this.carTypeMapper = carTypeMapper;
    }

    /**
     * Save a carType.
     *
     * @param carTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarTypeDTO save(CarTypeDTO carTypeDTO) {
        log.debug("Request to save CarType : {}", carTypeDTO);
        CarType carType = carTypeMapper.carTypeDTOToCarType(carTypeDTO);
        carType = carTypeRepository.save(carType);
        CarTypeDTO result = carTypeMapper.carTypeToCarTypeDTO(carType);
        return result;
    }

    /**
     *  Get all the carTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarTypes");
        Page<CarType> result = carTypeRepository.findAll(pageable);
        return result.map(carType -> carTypeMapper.carTypeToCarTypeDTO(carType));
    }

    /**
     *  Get one carType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarTypeDTO findOne(Long id) {
        log.debug("Request to get CarType : {}", id);
        CarType carType = carTypeRepository.findOne(id);
        CarTypeDTO carTypeDTO = carTypeMapper.carTypeToCarTypeDTO(carType);
        return carTypeDTO;
    }

    /**
     *  Delete the  carType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarType : {}", id);
        carTypeRepository.delete(id);
    }
}
