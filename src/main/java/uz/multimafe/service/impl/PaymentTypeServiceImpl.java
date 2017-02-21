package uz.multimafe.service.impl;

import uz.multimafe.service.PaymentTypeService;
import uz.multimafe.domain.PaymentType;
import uz.multimafe.repository.PaymentTypeRepository;
import uz.multimafe.service.dto.PaymentTypeDTO;
import uz.multimafe.service.mapper.PaymentTypeMapper;
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
 * Service Implementation for managing PaymentType.
 */
@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService{

    private final Logger log = LoggerFactory.getLogger(PaymentTypeServiceImpl.class);
    
    private final PaymentTypeRepository paymentTypeRepository;

    private final PaymentTypeMapper paymentTypeMapper;

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository, PaymentTypeMapper paymentTypeMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeMapper = paymentTypeMapper;
    }

    /**
     * Save a paymentType.
     *
     * @param paymentTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentTypeDTO save(PaymentTypeDTO paymentTypeDTO) {
        log.debug("Request to save PaymentType : {}", paymentTypeDTO);
        PaymentType paymentType = paymentTypeMapper.paymentTypeDTOToPaymentType(paymentTypeDTO);
        paymentType = paymentTypeRepository.save(paymentType);
        PaymentTypeDTO result = paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentType);
        return result;
    }

    /**
     *  Get all the paymentTypes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTypes");
        Page<PaymentType> result = paymentTypeRepository.findAll(pageable);
        return result.map(paymentType -> paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentType));
    }

    /**
     *  Get one paymentType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentTypeDTO findOne(Long id) {
        log.debug("Request to get PaymentType : {}", id);
        PaymentType paymentType = paymentTypeRepository.findOne(id);
        PaymentTypeDTO paymentTypeDTO = paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentType);
        return paymentTypeDTO;
    }

    /**
     *  Delete the  paymentType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentType : {}", id);
        paymentTypeRepository.delete(id);
    }
}
