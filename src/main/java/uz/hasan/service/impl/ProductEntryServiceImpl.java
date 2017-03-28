package uz.hasan.service.impl;

import uz.hasan.domain.Car;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.enumeration.CarStatus;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.repository.CarRepository;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.service.ProductEntryService;
import uz.hasan.domain.ProductEntry;
import uz.hasan.repository.ProductEntryRepository;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.mapper.ProductEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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

    private final ReceiptRepository receiptRepository;
    private final CarRepository carRepository;

    public ProductEntryServiceImpl(ProductEntryRepository productEntryRepository, ProductEntryMapper productEntryMapper, ReceiptRepository receiptRepository, CarRepository carRepository) {
        this.productEntryRepository = productEntryRepository;
        this.productEntryMapper = productEntryMapper;
        this.receiptRepository = receiptRepository;
        this.carRepository = carRepository;
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
        List<ProductEntry> result = productEntryRepository.findByAttachedCarNumberAndStatus(carNumber, ReceiptStatus.NEW);
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
        List<ProductEntry> result = productEntryRepository.findByAttachedCarNumberAndStatusNot(carNumber, ReceiptStatus.DELIVERED);
        return productEntryMapper.productEntriesToProductEntryDTOs(result);
    }

    /**
     * Deliver a productEntries.
     *
     * @param productEntryDTOs the entities to deliver
     * @return the persisted entities
     */
    @Override
    public List<ProductEntryDTO> deliver(List<ProductEntryDTO> productEntryDTOs) {
        if (productEntryDTOs.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductEntryDTO> productEntryDTOS = new ArrayList<>();
        Set<Receipt> receipts = new HashSet<>();
        for (ProductEntryDTO dto : productEntryDTOs) {
            dto.setStatus(ReceiptStatus.DELIVERY_PROCESS);
            ProductEntry productEntry = productEntryRepository.save(productEntryMapper.productEntryDTOToProductEntry(dto));
            productEntryDTOS.add(productEntryMapper.productEntryToProductEntryDTO(productEntry));
            Receipt receipt = productEntry.getReceipt();
            receipts.add(receipt);
        }
        for (Receipt receipt : receipts) {
            boolean ready = receipt.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERY_PROCESS));
            if (ready) {
                receipt.setStatus(ReceiptStatus.DELIVERY_PROCESS);
                receiptRepository.save(receipt);
            }
        }
        return productEntryDTOS;
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
        List<ProductEntryDTO> productEntryDTOS = new ArrayList<>();
        Set<Receipt> receipts = new HashSet<>();

        for (ProductEntryDTO dto : productEntryDTOs) {
            dto.setStatus(ReceiptStatus.DELIVERED);
            ProductEntry productEntry = productEntryRepository.save(productEntryMapper.productEntryDTOToProductEntry(dto));
            productEntryDTOS.add(productEntryMapper.productEntryToProductEntryDTO(productEntry));
            Receipt receipt = productEntry.getReceipt();
            receipts.add(receipt);
        }

        for (Receipt receipt : receipts) {
            boolean ready = receipt.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERED));
            if (ready) {
                receipt.setStatus(ReceiptStatus.DELIVERED);
                receiptRepository.save(receipt);
            }
        }

        Car car = carRepository.findOne(productEntryMapper.productEntryDTOsToProductEntries(productEntryDTOS).get(0).getAttachedCar().getId());
        boolean idle = car.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERED));
        if (idle) {
            car.setStatus(CarStatus.IDLE);
            carRepository.save(car);
        }

        return productEntryDTOS;
    }
}
