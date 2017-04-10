package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.CarStatus;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.repository.*;
import uz.hasan.security.AuthoritiesConstants;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.service.mapper.CustomProductEntriesMapper;
import uz.hasan.service.mapper.ProductEntryMapper;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.service.mapper.ReceiptProductEntriesMapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

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

    private final CarRepository carRepository;

    private final UserService userService;
    private final CustomProductEntriesMapper customProductEntryMapper;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository,
                              ReceiptMapper receiptMapper,
                              ReceiptProductEntriesMapper receiptProductEntriesMapper,
                              ProductEntryMapper productEntryMapper,
                              CarRepository carRepository,
                              ProductEntryRepository productEntryRepository,
                              LoyaltyCardRepository loyaltyCardRepository,
                              ClientRepository clientRepository,
                              PayMasterRepository payMasterRepository,
                              UserService userService,
                              CustomProductEntriesMapper customProductEntryMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
        this.receiptProductEntriesMapper = receiptProductEntriesMapper;
        this.productEntryMapper = productEntryMapper;
        this.productEntryRepository = productEntryRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.clientRepository = clientRepository;
        this.payMasterRepository = payMasterRepository;
        this.carRepository = carRepository;
        this.userService = userService;
        this.customProductEntryMapper = customProductEntryMapper;
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

        if (receipt.getLoyaltyCard() != null && receipt.getLoyaltyCard().getId() == null) {
            if (receipt.getLoyaltyCard().getLoyaltyCardAmount() != null) {
                loyaltyCardRepository.save(receipt.getLoyaltyCard());
            } else {
                receipt.setLoyaltyCard(null);
            }
        }

        receipt = receiptRepository.save(receipt);
        ReceiptDTO result = receiptMapper.receiptToReceiptDTO(receipt);
        return result;
    }

    /**
     * Save a receipt.
     *
     * @param receiptDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReceiptProductEntriesDTO save(ReceiptProductEntriesDTO receiptDTO) {
        log.debug("Request to save Receipt : {}", receiptDTO);
        Receipt receipt = receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(receiptDTO);
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

        if (receipt.getLoyaltyCard() != null && receipt.getLoyaltyCard().getId() == null) {
            if (receipt.getLoyaltyCard().getLoyaltyCardAmount() != null) {
                loyaltyCardRepository.save(receipt.getLoyaltyCard());
            } else {
                receipt.setLoyaltyCard(null);
            }
        }

        receipt = receiptRepository.save(receipt);
        ReceiptProductEntriesDTO result = receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipt);
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
        Page<Receipt> result = receiptRepository.findAllByOrderByDocDateDesc(pageable);
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
     * Get all the new receipts by shop id.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllNewReceiptsByShopId(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        Page<Receipt> result = receiptRepository.findByStatusAndShopShopId(pageable, ReceiptStatus.NEW, userService.getUserWithAuthorities().getShop().getShopId());
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all receipts by shopId.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllReceiptsByShopId(Pageable pageable) {
        log.debug("Request to get all new Receipts");

        User userWithAuthorities = userService.getUserWithAuthorities();
        Page<Receipt> result;
        if (userWithAuthorities.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CASHIER))) {
            String shopId = userWithAuthorities.getShop().getShopId();
            result = receiptRepository.findByShopShopIdOrderByDocDateDesc(pageable, shopId);
        } else {
            result = receiptRepository.findAll(pageable);
        }
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

        receipt.getProductEntries().forEach(productEntry -> productEntry.setStatus(ReceiptStatus.APPLICATION_SENT));
        productEntryRepository.save(receipt.getProductEntries());

        User userWithAuthorities = userService.getUserWithAuthorities();
        receipt.setSentBy(userWithAuthorities);
        receipt.setStatus(ReceiptStatus.APPLICATION_SENT);
        receipt.setSentToDCTime(ZonedDateTime.now());
        receipt = receiptRepository.save(receipt);
        return receiptMapper.receiptToReceiptDTO(receipt);
    }

    /**
     * Count new receipts.
     *
     * @return count of new receipts
     */
    @Override
    public Long countNewReceipts() {
        User userWithAuthorities = userService.getUserWithAuthorities();
        Set<Authority> authorities = userWithAuthorities.getAuthorities();
        if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN)) ||
            authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.MANAGER))||
            authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.DISPATCHER))) {
            return receiptRepository.countByStatus(ReceiptStatus.NEW);
        } else {
            return receiptRepository.getCountByStatusAndShopId(ReceiptStatus.NEW, userWithAuthorities.getShop().getShopId());
        }
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

    /**
     * Attach receipt products to car(s).
     *
     * @return receipt with attached to car products
     */
    @Override
    public ReceiptDTO attachOrder(ReceiptProductEntriesDTO receiptDTO) {
        if (receiptDTO == null || receiptDTO.getProductEntries() == null || receiptDTO.getProductEntries().isEmpty()) {
            return null;
        }

        List<ProductEntryDTO> productEntryDTOs = receiptDTO.getProductEntries();
        List<ProductEntry> productEntryList = customProductEntryMapper.productEntryDTOsToProductEntries(productEntryDTOs);
        productEntryList.forEach(productEntry -> productEntry.setAttachedToCarTime(ZonedDateTime.now()));
        productEntryList.forEach(productEntry -> productEntry.setAttachedToDriverBy(userService.getUserWithAuthorities()));
        productEntryList.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.ATTACHED_TO_DRIVER));
        List<ProductEntry> productEntries = productEntryRepository.save(productEntryList);

        for (ProductEntry entry : productEntries) {
            Car attachedCar = entry.getAttachedCar();
            if (attachedCar != null) { // TODO: 4/3/17 mark as busy when car is fully occupied
                attachedCar.setStatus(CarStatus.BUSY);
                carRepository.save(attachedCar);
            }
        }

        receiptDTO.setStatus(ReceiptStatus.ATTACHED_TO_DRIVER);

        return save(receiptDTO);
    }
}
