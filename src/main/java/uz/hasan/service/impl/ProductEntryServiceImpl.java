package uz.hasan.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.CarStatus;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.XDocTemplate;
import uz.hasan.invoice.Product;
import uz.hasan.repository.CarRepository;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.service.ExcelService;
import uz.hasan.service.ProductEntryService;
import uz.hasan.repository.ProductEntryRepository;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.mapper.CustomProductEntriesMapper;
import uz.hasan.service.mapper.ProductEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service Implementation for managing ProductEntry.
 */
@Service
@Transactional
public class ProductEntryServiceImpl implements ProductEntryService {

    private final Logger log = LoggerFactory.getLogger(ProductEntryServiceImpl.class);

    private final ProductEntryRepository productEntryRepository;

    private final ProductEntryMapper productEntryMapper;
    private final CustomProductEntriesMapper customProductEntriesMapper;

    private final ReceiptRepository receiptRepository;
    private final CarRepository carRepository;
    private final UserService userService;
    private final ExcelService excelService;

    public ProductEntryServiceImpl(ProductEntryRepository productEntryRepository,
                                   ProductEntryMapper productEntryMapper,
                                   ReceiptRepository receiptRepository,
                                   CarRepository carRepository,
                                   UserService userService,
                                   ExcelService excelService,
                                   CustomProductEntriesMapper customProductEntriesMapper) {
        this.productEntryRepository = productEntryRepository;
        this.productEntryMapper = productEntryMapper;
        this.receiptRepository = receiptRepository;
        this.carRepository = carRepository;
        this.userService = userService;
        this.excelService = excelService;
        this.customProductEntriesMapper = customProductEntriesMapper;
    }

    /**
     * Save a productEntry.
     *
     * @param productEntryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductEntryDTO save(ProductEntryDTO productEntryDTO) {
        log.debug("Request to save ProductEntry : {}", productEntryDTO);
        ProductEntry productEntry = productEntryMapper.productEntryDTOToProductEntry(productEntryDTO);
        productEntry = productEntryRepository.save(productEntry);
        ProductEntryDTO result = productEntryMapper.productEntryToProductEntryDTO(productEntry);
        return result;
    }

    /**
     * Get all the productEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductEntries");
        Page<ProductEntry> result = productEntryRepository.findAll(pageable);
        return result.map(productEntry -> productEntryMapper.productEntryToProductEntryDTO(productEntry));
    }

    /**
     * Get one productEntry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductEntryDTO findOne(Long id) {
        log.debug("Request to get ProductEntry : {}", id);
        ProductEntry productEntry = productEntryRepository.findOne(id);
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);
        return productEntryDTO;
    }

    /**
     * Delete the  productEntry by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductEntry : {}", id);
        productEntryRepository.delete(id);
    }

    /**
     * Get all the productEntries by receipt ID.
     *
     * @param receiptId the receipt ID
     * @return the list of entities
     */
    @Override
    public List<ProductEntryDTO> findAllByReceiptId(Long receiptId) {
        log.debug("Request to get all ProductEntries by receiptId: {}", receiptId);
        List<ProductEntry> result = productEntryRepository.findByReceiptId(receiptId);
        return productEntryMapper.productEntriesToProductEntryDTOs(result);
    }

    /**
     * Get new the productEntries by car number.
     *
     * @param carNumber the car number
     * @return the list of entities
     */
    @Override
    public List<ProductEntryDTO> findNewProductsByCarNumber(String carNumber) {
        log.debug("Request to get new ProductEntries by car number: {}", carNumber);
        List<ProductEntry> result = productEntryRepository.findByAttachedCarNumberAndStatus(carNumber, ReceiptStatus.ATTACHED_TO_DRIVER);
        return productEntryMapper.productEntriesToProductEntryDTOs(result);
    }

    /**
     * Get all the productEntries by car number.
     *
     * @param carNumber the car number
     * @return the list of entities
     */
    @Override
    public List<ProductEntryDTO> findLastProductsByCarNumber(String carNumber) {
        log.debug("Request to get all ProductEntries by car number: {}", carNumber);
        List<ProductEntry> result = productEntryRepository.findByAttachedCarNumberAndStatus(carNumber, ReceiptStatus.DELIVERY_PROCESS);
        return productEntryMapper.productEntriesToProductEntryDTOs(result);
    }

    /**
     * Deliver a productEntries.
     *
     * @param productEntryDTOs the entities to deliver
     * @return the persisted entities
     */
    @Override
    public void deliver(List<ProductEntryDTO> productEntryDTOs, HttpServletResponse response) {
        if (productEntryDTOs.isEmpty()) {
            return;
        }

        Set<Receipt> receipts = new HashSet<>();
        List<ProductEntry> productEntries = customProductEntriesMapper.productEntryDTOsToProductEntries(productEntryDTOs);
        List<ProductEntry> resultList = new ArrayList<>();

        for (ProductEntry entry : productEntries) {

            entry.setStatus(ReceiptStatus.DELIVERY_PROCESS);
            entry.setDeliveryItemsSentBy(userService.getUserWithAuthorities());
            entry.setDeliveryStartTime(ZonedDateTime.now());

            ProductEntry saveResult = productEntryRepository.save(entry);
            resultList.add(saveResult);

            Receipt receipt = saveResult.getReceipt();
            receipts.add(receipt);

        }

        for (Receipt receipt : receipts) {
            boolean ready = receipt.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERY_PROCESS));
            if (ready) {
                receipt.setStatus(ReceiptStatus.DELIVERY_PROCESS);
                receiptRepository.save(receipt);

            }
        }
    }

    /**
     * Make productEntries as delivered.
     *
     * @param productEntryDTOs the entities delivered
     * @return the persisted entities
     */
    public List<ProductEntryDTO> delivered(List<ProductEntryDTO> productEntryDTOs) {
        if (productEntryDTOs.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Receipt> receipts = new HashSet<>();
        List<ProductEntry> productEntries = productEntryMapper.productEntryDTOsToProductEntries(productEntryDTOs);
        List<ProductEntry> resultList = new ArrayList<>();

        User currentUser = userService.getUserWithAuthorities();// Get current user

        for (ProductEntry productEntry : productEntries) {

            productEntry.setStatus(ReceiptStatus.DELIVERED);
            productEntry.setDeliveryEndTime(ZonedDateTime.now());
            productEntry.setMarkedAsDeliveredBy(currentUser);

            ProductEntry result = productEntryRepository.save(productEntry);
            resultList.add(result);

            Receipt receipt = result.getReceipt();
            receipts.add(receipt);
        }

        for (Receipt receipt : receipts) {
            boolean ready = receipt.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERED));
            if (ready) {
                receipt.setStatus(ReceiptStatus.DELIVERED);
                receipt.setDeliveredTime(ZonedDateTime.now());
                receipt.setMarkedAsDeliveredBy(currentUser);

                receiptRepository.save(receipt);
            }
        }

        Car car = carRepository.findOne(resultList.get(0).getAttachedCar().getId());
        Long notYetDeliveredProductEntries = productEntryRepository.countByAttachedCarNumberAndStatusNot(car.getNumber(), ReceiptStatus.DELIVERED);
        if (notYetDeliveredProductEntries == null || notYetDeliveredProductEntries < 1) {
            car.setStatus(CarStatus.IDLE);
            carRepository.save(car);
        }

        return productEntryMapper.productEntriesToProductEntryDTOs(resultList);
    }
}
