package uz.hasan.service.impl;

import uz.hasan.service.PayTypeService;
import uz.hasan.domain.PayType;
import uz.hasan.repository.PayTypeRepository;
import uz.hasan.service.dto.PayTypeDTO;
import uz.hasan.service.mapper.PayTypeMapper;
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
 * Service Implementation for managing PayType.
 */
@Service
@Transactional
public class PayTypeServiceImpl implements PayTypeService{

    private final Logger log = LoggerFactory.getLogger(PayTypeServiceImpl.class);
    
    private final PayTypeRepository payTypeRepository;

    private final PayTypeMapper payTypeMapper;

    public PayTypeServiceImpl(PayTypeRepository payTypeRepository, PayTypeMapper payTypeMapper) {
        this.payTypeRepository = payTypeRepository;
        this.payTypeMapper = payTypeMapper;
    }

    /**
     * Save a payType.
     *
     * @param payTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PayTypeDTO save(PayTypeDTO payTypeDTO) {
        log.debug("Request to save PayType : {}", payTypeDTO);
        PayType payType = payTypeMapper.payTypeDTOToPayType(payTypeDTO);
        payType = payTypeRepository.save(payType);
        PayTypeDTO result = payTypeMapper.payTypeToPayTypeDTO(payType);
        return result;
    }

    /**
     *  Get all the payTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PayTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PayTypes");
        Page<PayType> result = payTypeRepository.findAll(pageable);
        return result.map(payType -> payTypeMapper.payTypeToPayTypeDTO(payType));
    }

    /**
     *  Get one payType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PayTypeDTO findOne(Long id) {
        log.debug("Request to get PayType : {}", id);
        PayType payType = payTypeRepository.findOne(id);
        PayTypeDTO payTypeDTO = payTypeMapper.payTypeToPayTypeDTO(payType);
        return payTypeDTO;
    }

    /**
     *  Delete the  payType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayType : {}", id);
        payTypeRepository.delete(id);
    }
}
