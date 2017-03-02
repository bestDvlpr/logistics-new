package uz.hasan.service.impl;

import uz.hasan.service.ReceiptService;
import uz.hasan.domain.Receipt;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.mapper.ReceiptMapper;
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
 * Service Implementation for managing Receipt.
 */
@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService{

    private final Logger log = LoggerFactory.getLogger(ReceiptServiceImpl.class);
    
    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
    }

    /**
     * Save a receipt.
     *
     * @param receiptDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReceiptDTO save(ReceiptDTO receiptDTO) {
        log.debug("Request to save Receipt : {}", receiptDTO);
        Receipt receipt = receiptMapper.receiptDTOToReceipt(receiptDTO);
        receipt = receiptRepository.save(receipt);
        ReceiptDTO result = receiptMapper.receiptToReceiptDTO(receipt);
        return result;
    }

    /**
     *  Get all the receipts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReceiptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Receipts");
        Page<Receipt> result = receiptRepository.findAll(pageable);
        return result.map(receipt -> receiptMapper.receiptToReceiptDTO(receipt));
    }

    /**
     *  Get one receipt by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReceiptDTO findOne(Long id) {
        log.debug("Request to get Receipt : {}", id);
        Receipt receipt = receiptRepository.findOne(id);
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(receipt);
        return receiptDTO;
    }

    /**
     *  Delete the  receipt by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receipt : {}", id);
        receiptRepository.delete(id);
    }
}
