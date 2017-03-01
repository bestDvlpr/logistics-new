package uz.hasan.service.impl;

import uz.hasan.service.PayMasterService;
import uz.hasan.domain.PayMaster;
import uz.hasan.repository.PayMasterRepository;
import uz.hasan.service.dto.PayMasterDTO;
import uz.hasan.service.mapper.PayMasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing PayMaster.
 */
@Service
@Transactional
public class PayMasterServiceImpl implements PayMasterService{

    private final Logger log = LoggerFactory.getLogger(PayMasterServiceImpl.class);

    private final PayMasterRepository payMasterRepository;

    private final PayMasterMapper payMasterMapper;

    public PayMasterServiceImpl(PayMasterRepository payMasterRepository, PayMasterMapper payMasterMapper) {
        this.payMasterRepository = payMasterRepository;
        this.payMasterMapper = payMasterMapper;
    }

    /**
     * Save a payMaster.
     *
     * @param payMasterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PayMasterDTO save(PayMasterDTO payMasterDTO) {
        log.debug("Request to save PayMaster : {}", payMasterDTO);
        PayMaster payMaster = payMasterMapper.payMasterDTOToPayMaster(payMasterDTO);
        payMaster = payMasterRepository.save(payMaster);
        PayMasterDTO result = payMasterMapper.payMasterToPayMasterDTO(payMaster);
        return result;
    }

    /**
     *  Get all the payMasters.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PayMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PayMasters");
        Page<PayMaster> result = payMasterRepository.findAll(pageable);
        return result.map(payMaster -> payMasterMapper.payMasterToPayMasterDTO(payMaster));
    }

    /**
     *  Get one payMaster by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PayMasterDTO findOne(Long id) {
        log.debug("Request to get PayMaster : {}", id);
        PayMaster payMaster = payMasterRepository.findOne(id);
        PayMasterDTO payMasterDTO = payMasterMapper.payMasterToPayMasterDTO(payMaster);
        return payMasterDTO;
    }

    /**
     *  Delete the  payMaster by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayMaster : {}", id);
        payMasterRepository.delete(id);
    }
}
