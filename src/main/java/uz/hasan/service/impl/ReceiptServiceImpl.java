package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.ProductEntry;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.repository.*;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.service.mapper.ProductEntryMapper;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.service.mapper.ReceiptProductEntriesMapper;

/**
 * Service Implementation for managing Receipt.
 */
@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

    private final Logger log = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    private final ReceiptProductEntriesMapper receiptProductEntriesMapper;

    private final ProductEntryMapper productEntryMapper;

    private final ProductEntryRepository productEntryRepository;

    private final PayMasterRepository payMasterRepository;

    private final LoyaltyCardRepository loyaltyCardRepository;

    private final ClientRepository clientRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository,
                              ReceiptMapper receiptMapper,
                              ReceiptProductEntriesMapper receiptProductEntriesMapper,
                              ProductEntryMapper productEntryMapper,
                              ProductEntryRepository productEntryRepository,
                              LoyaltyCardRepository loyaltyCardRepository,
                              ClientRepository clientRepository,
                              PayMasterRepository payMasterRepository) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
        this.receiptProductEntriesMapper = receiptProductEntriesMapper;
        this.productEntryMapper = productEntryMapper;
        this.productEntryRepository = productEntryRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.clientRepository = clientRepository;
        this.payMasterRepository = payMasterRepository;
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
        if (receipt.getClient() != null) {
            if (receipt.getClient().getFirstName() != null) {
                clientRepository.save(receipt.getClient());
            } else {
                receipt.setClient(null);
            }
        }

        if (receipt.getPayMaster() != null) {
            if (receipt.getPayMaster().getPayMasterName() != null) {
                payMasterRepository.save(receipt.getPayMaster());
            } else {
                receipt.setPayMaster(null);
            }
        }
        receipt = receiptRepository.save(receipt);
        ReceiptDTO result = receiptMapper.receiptToReceiptDTO(receipt);
        return result;
    }

    /**
     * Get all the receipts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReceiptProductEntriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Receipts");
        Page<Receipt> result = receiptRepository.findAllByOrderByIdDesc(pageable);
        return result.map(receipt -> receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipt));
    }

    /**
     * Get one receipt by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReceiptProductEntriesDTO findOne(Long id) {
        log.debug("Request to get Receipt : {}", id);
        Receipt receipt = receiptRepository.findOneWithEagerRelationships(id);
        return receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipt);
    }

    /**
     * Delete the  receipt by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receipt : {}", id);
        receiptRepository.delete(id);
    }

    /**
     * Get all the new receipts.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllNewReceipts(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        Page<Receipt> result = receiptRepository.findByStatus(pageable, ReceiptStatus.NEW);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all the applied receipts.
     *
     * @param pageable the pagination information
     * @return the list of applied entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAppliedReceipts(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        Page<Receipt> result = receiptRepository.findByStatus(pageable, ReceiptStatus.APPLICATION_SENT);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Send a receipt.
     *
     * @param receiptDTO the entity to send
     * @return the persisted entity
     */
    @Override
    public ReceiptDTO sendOrder(ReceiptProductEntriesDTO receiptDTO) {
        Receipt receipt = receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(receiptDTO);
        receipt.setStatus(ReceiptStatus.APPLICATION_SENT);

        if (receipt.getPayMaster() != null && receipt.getPayMaster().getId() == null) {
            if (receipt.getPayMaster().getPayMasterName() != null) {
                payMasterRepository.save(receipt.getPayMaster());
            } else {
                receipt.setPayMaster(null);
            }
        }

        if (receipt.getLoyaltyCard() != null && receipt.getLoyaltyCard().getId() == null) {
            if (receipt.getLoyaltyCard().getLoyaltyCardAmount() != null) {
                loyaltyCardRepository.save(receipt.getLoyaltyCard());
            } else {
                receipt.setLoyaltyCard(null);
            }
        }

        if (receipt.getClient() != null && receipt.getClient().getId() == null) {
            if (receipt.getClient().getFirstName() != null) {
                clientRepository.save(receipt.getClient());
            } else {
                receipt.setClient(null);
            }
        }

        receipt = receiptRepository.save(receipt);
        for (ProductEntry productEntry : receipt.getProductEntries()) {
            productEntry.setStatus(ReceiptStatus.NEW);
        }
        productEntryRepository.save(receipt.getProductEntries());
        return receiptMapper.receiptToReceiptDTO(receipt);
    }

    /**
     * Count new receipts.
     *
     * @return count of new receipts
     */
    @Override
    public Long countNewReceipts() {
        return receiptRepository.countByStatus(ReceiptStatus.NEW);
    }

    /**
     * Count applied receipts.
     *
     * @return count of applied receipts
     */
    @Override
    public Long countAppliedReceipts() {
        return receiptRepository.countByStatus(ReceiptStatus.APPLICATION_SENT);
    }
}
