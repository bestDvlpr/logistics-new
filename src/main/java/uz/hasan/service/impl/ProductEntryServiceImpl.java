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

        HSSFWorkbook workbook = new HSSFWorkbook();
        for (Receipt receipt : receipts) {
            boolean ready = receipt.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERY_PROCESS));
            if (ready) {
                receipt.setStatus(ReceiptStatus.DELIVERY_PROCESS);
                receiptRepository.save(receipt);

                Map<String, Object> hashMap = createHashMap(new ArrayList<>(receipt.getProductEntries()));
                excelService.generateDocx(XDocTemplate.SHOP_DELIVERY_INVOICE, receipt.getDocID(), hashMap, response);
            } else {
                List<ProductEntry> sortedProds = new ArrayList<>();
                for (ProductEntry entry : productEntries) {
                    if (Objects.equals(entry.getReceipt().getId(), receipt.getId())) {
                        sortedProds.add(entry);
                    }
                }
//                workbook = excelService.createInvoiceSheet(workbook, sortedProds);
                Map<String, Object> hashMap = createHashMap(sortedProds);
                excelService.generateDocx(XDocTemplate.SHOP_DELIVERY_INVOICE, sortedProds.get(0).getReceipt().getDocID(), hashMap, response);
            }
        }

//        FileOutputStream outputStream = null;
        /*try {
            outputStream = new FileOutputStream(new File("/home/hasan/Invoice.xlsx"), false);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return;
    }

    private Map<String, Object> createHashMap(List<ProductEntry> productEntries) {
        if (productEntries == null || productEntries.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        ProductEntry productEntry = productEntries.get(0);
        Shop shop = productEntry.getShop();
        result.put("shopAddress", (shop != null && shop.getAddress() != null) ? shop.getAddress().getStreetAddress() : "");
        result.put("shopBankAccountNumber", (shop != null) ? shop.getBankAccountNumber() : "");
        result.put("shopBankBranchRegion", (shop != null) ? shop.getBankBranchRegion() : "");
        result.put("shopBankName", (shop != null) ? shop.getBankName() : "");
        result.put("shopBankMfo", (shop != null) ? shop.getMfo() : "");
        result.put("shopName", (shop != null) ? shop.getName() : "");
        result.put("shopOkonx", (shop != null) ? shop.getOkonx() : "");
        result.put("shopOked", (shop != null) ? shop.getOked() : "");
        result.put("shopTin", (shop != null) ? shop.getTin() : "");
        /* add client fields*/
        Receipt receipt = productEntry.getReceipt();
        Client client = null;
        if (receipt != null) {
            client = receipt.getClient();
        }
        result.put("clientAddress", (client == null || client.getAddresses().isEmpty()) ? "" : client.getAddresses().iterator().next().getStreetAddress());
        result.put("clientBankAccountNumber", (client != null && client.getBankAccountNumber() != null) ? client.getBankAccountNumber() : "");
        result.put("clientBankBranchRegion", (client != null && client.getBankFilialRegion() != null) ? client.getBankFilialRegion() : "");
        result.put("clientBankName", (client != null && client.getBankName() != null) ? client.getBankName() : "");
        result.put("clientBankMfo", (client != null && client.getMfo() != null) ? client.getMfo() : "");
        result.put("clientOkonx", (client != null && client.getOkonx() != null) ? client.getOkonx() : "");
        result.put("clientOked", (client != null && client.getOked() != null) ? client.getOked() : "");
        result.put("clientTin", (client != null && client.getTin() != null) ? client.getTin() : "");
        result.put("clientFirstName", (client != null && client.getFirstName() != null) ? client.getFirstName() : "");
        result.put("clientLastName", (client != null && client.getLastName() != null) ? client.getLastName() : "");
        result.put("clientPhoneNumbers", (client == null || client.getPhoneNumbers().isEmpty()) ? "" : client.getPhoneNumbers().iterator().next());
        result.put("receiptDocId", (receipt != null) ? receipt.getDocID() : "");
        result.put("receiptId", receipt != null ? receipt.getId() : "");
        List<uz.hasan.invoice.Product> products = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductEntry entry : productEntries) {
            Product product = new Product();
            product.setDeliveryStartTime(formatter.format(Date.from(entry.getDeliveryStartTime().toInstant())));            product.setPrice(String.valueOf(entry.getPrice()));
            product.setProductName((entry.getProduct() != null && entry.getProduct().getName() != null) ? entry.getProduct().getName() : "");
            product.setQuantity(String.valueOf(entry.getQty()));
            product.setReceiptId((receipt != null && receipt.getId() != null) ? String.valueOf(receipt.getId()) : "");
            product.setDocId((receipt != null && receipt.getDocID() != null) ? receipt.getDocID() : "");
            product.setCarNumber((entry.getAttachedCar() != null && !entry.getAttachedCar().getDrivers().isEmpty()) ? entry.getAttachedCar().getNumber() : "");
            products.add(product);
//            result.put("shopPhoneNumbers", shop.getPhoneNumbers()); // TODO: 06.04.2017 add phoneNumbers attr. to shop model
        /* add product fields*/
            totalPrice = totalPrice.add(entry.getPrice() != null ? entry.getPrice() : BigDecimal.ZERO);
        }

        result.put("product", products);
        if (!products.isEmpty()) {
            result.put("deliveryStartTime", formatter.format(Date.from(productEntries.get(0).getDeliveryStartTime().toInstant())));
        }
        result.put("sumPrice", totalPrice);
        return result;
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
        if (notYetDeliveredProductEntries == null || notYetDeliveredProductEntries > 1) {
            car.setStatus(CarStatus.IDLE);
            carRepository.save(car);
        }

        return productEntryMapper.productEntriesToProductEntryDTOs(resultList);
    }
}
