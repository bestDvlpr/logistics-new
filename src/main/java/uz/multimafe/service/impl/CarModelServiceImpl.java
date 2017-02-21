package uz.multimafe.service.impl;

import uz.multimafe.service.CarModelService;
import uz.multimafe.domain.CarModel;
import uz.multimafe.repository.CarModelRepository;
import uz.multimafe.service.dto.CarModelDTO;
import uz.multimafe.service.mapper.CarModelMapper;
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
 * Service Implementation for managing CarModel.
 */
@Service
@Transactional
public class CarModelServiceImpl implements CarModelService{

    private final Logger log = LoggerFactory.getLogger(CarModelServiceImpl.class);
    
    private final CarModelRepository carModelRepository;

    private final CarModelMapper carModelMapper;

    public CarModelServiceImpl(CarModelRepository carModelRepository, CarModelMapper carModelMapper) {
        this.carModelRepository = carModelRepository;
        this.carModelMapper = carModelMapper;
    }

    /**
     * Save a carModel.
     *
     * @param carModelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarModelDTO save(CarModelDTO carModelDTO) {
        log.debug("Request to save CarModel : {}", carModelDTO);
        CarModel carModel = carModelMapper.carModelDTOToCarModel(carModelDTO);
        carModel = carModelRepository.save(carModel);
        CarModelDTO result = carModelMapper.carModelToCarModelDTO(carModel);
        return result;
    }

    /**
     *  Get all the carModels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarModels");
        Page<CarModel> result = carModelRepository.findAll(pageable);
        return result.map(carModel -> carModelMapper.carModelToCarModelDTO(carModel));
    }

    /**
     *  Get one carModel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarModelDTO findOne(Long id) {
        log.debug("Request to get CarModel : {}", id);
        CarModel carModel = carModelRepository.findOne(id);
        CarModelDTO carModelDTO = carModelMapper.carModelToCarModelDTO(carModel);
        return carModelDTO;
    }

    /**
     *  Delete the  carModel by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarModel : {}", id);
        carModelRepository.delete(id);
    }
}
