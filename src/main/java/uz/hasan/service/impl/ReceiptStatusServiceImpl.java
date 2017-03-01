package uz.hasan.service.impl;

import uz.hasan.service.ReceiptStatusService;
import uz.hasan.domain.ReceiptStatus;
import uz.hasan.repository.ReceiptStatusRepository;
import uz.hasan.service.dto.ReceiptStatusDTO;
import uz.hasan.service.mapper.ReceiptStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing ReceiptStatus.
 */
@Service
@Transactional
public class ReceiptStatusServiceImpl implements ReceiptStatusService{

    private final Logger log = LoggerFactory.getLogger(ReceiptStatusServiceImpl.class);

    private final ReceiptStatusRepository receiptStatusRepository;

    private final ReceiptStatusMapper receiptStatusMapper;

    public ReceiptStatusServiceImpl(ReceiptStatusRepository receiptStatusRepository, ReceiptStatusMapper receiptStatusMapper) {
        this.receiptStatusRepository = receiptStatusRepository;
        this.receiptStatusMapper = receiptStatusMapper;
    }

    /**
     * Save a receiptStatus.
     *
     * @param receiptStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReceiptStatusDTO save(ReceiptStatusDTO receiptStatusDTO) {
        log.debug("Request to save ReceiptStatus : {}", receiptStatusDTO);
        ReceiptStatus receiptStatus = receiptStatusMapper.receiptStatusDTOToReceiptStatus(receiptStatusDTO);
        receiptStatus = receiptStatusRepository.save(receiptStatus);
        ReceiptStatusDTO result = receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus);
        return result;
    }

    /**
     *  Get all the receiptStatuses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReceiptStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReceiptStatuses");
        Page<ReceiptStatus> result = receiptStatusRepository.findAll(pageable);
        return result.map(receiptStatus -> receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus));
    }

    /**
     *  Get one receiptStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReceiptStatusDTO findOne(Long id) {
        log.debug("Request to get ReceiptStatus : {}", id);
        ReceiptStatus receiptStatus = receiptStatusRepository.findOne(id);
        ReceiptStatusDTO receiptStatusDTO = receiptStatusMapper.receiptStatusToReceiptStatusDTO(receiptStatus);
        return receiptStatusDTO;
    }

    /**
     *  Delete the  receiptStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReceiptStatus : {}", id);
        receiptStatusRepository.delete(id);
    }
}
