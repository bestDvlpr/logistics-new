package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.Product;
import uz.hasan.domain.enumeration.CarStatus;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.XDocTemplate;
import uz.hasan.invoice.*;
import uz.hasan.repository.*;
import uz.hasan.security.AuthoritiesConstants;
import uz.hasan.service.ExcelService;
import uz.hasan.service.ProductEntryService;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.service.mapper.CustomProductEntriesMapper;
import uz.hasan.service.mapper.ProductEntryMapper;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.service.mapper.ReceiptProductEntriesMapper;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.*;

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

    private final ExcelService excelService;

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
                              CustomProductEntriesMapper customProductEntryMapper,
                              ExcelService excelService) {
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
        this.excelService = excelService;
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
        Page<Receipt> result = receiptRepository.findAll(pageable);
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
    public List<ReceiptProductEntriesDTO> sendOrder(List<ReceiptProductEntriesDTO> receiptDTOs) {

        List<Receipt> receipts = new ArrayList<>();
        List<Receipt> result = new ArrayList<>();
        for (ReceiptProductEntriesDTO entriesDTO : receiptDTOs) {
            receipts.add(receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(entriesDTO));
        }
        for (Receipt receipt : receipts) {
            Set<ProductEntry> productEntries = receipt.getProductEntries();
            productEntries.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.APPLICATION_SENT));
            productEntryRepository.save(productEntries);
            Address address = productEntries.iterator().next().getAddress();
            User userWithAuthorities = userService.getUserWithAuthorities();
            receipt.setSentBy(userWithAuthorities);
            receipt.setStatus(ReceiptStatus.APPLICATION_SENT);
            receipt.setSentToDCTime(ZonedDateTime.now());
            receipt.setAddress(address);
            receipt = receiptRepository.save(receipt);
            result.add(receipt);
        }
        return receiptProductEntriesMapper.receiptsToReceiptProductEntryDTOs(result);

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
            authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.MANAGER)) ||
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
    public ReceiptProductEntriesDTO attachOrder(ReceiptProductEntriesDTO receiptDTO) {
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

    @Override
    public void download(Long receiptId, HttpServletResponse response) {
        if (receiptId == null) {
            return;
        }

        Receipt receipt = receiptRepository.findOne(receiptId);
        Set<ProductEntry> productEntries = receipt.getProductEntries();

//        receipt.setStatus(ReceiptStatus.DELIVERY_PROCESS);
//        receiptRepository.save(receipt);

        Map<String, Object> hashMap = createHashMap(new ArrayList<>(productEntries));
        excelService.generateDocx(XDocTemplate.SHOP_DELIVERY_INVOICE, receipt.getDocID(), hashMap, response);
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
        if (receipt != null) {
            client = receipt.getClient();
        }
        if (client == null || client.getAddresses().isEmpty()) {
            result.put("clientAddress", "");
        } else {
            Address address = client.getAddresses().iterator().next();
            String streetAddress = "";
            streetAddress += address.getStreetAddress() + ", ";
            streetAddress += address.getDistrict().getName() + ", ";
            streetAddress += (address.getCity() != null) ? address.getCity().getName() + ", " : "";
            streetAddress += address.getRegion().getName() + ", ";
            streetAddress += address.getCountry().getName();
            result.put("clientAddress", streetAddress);
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
        if (client != null || !client.getPhoneNumbers().isEmpty()) {
            List<String> phoneNumbers = new ArrayList<>();
            for (PhoneNumber number : client.getPhoneNumbers()) {
                phoneNumbers.add(number.getNumber());
            }
            result.put("clientPhoneNumbers", phoneNumbers);
        }
        result.put("receiptDocId", (receipt != null) ? receipt.getDocID() : "");
        result.put("receiptId", receipt != null ? receipt.getId() : "");
        List<uz.hasan.invoice.Product> products = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductEntry entry : productEntries) {
            uz.hasan.invoice.Product product = new uz.hasan.invoice.Product();
            product.setDeliveryStartTime(entry.getDeliveryStartTime() != null ? formatter.format(Date.from(entry.getDeliveryStartTime().toInstant())) : null);
            product.setPrice(String.valueOf(entry.getPrice()));
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
            result.put("deliveryStartTime", formatter.format(receipt != null && receipt.getDocDate() != null ? new Date(receipt.getDocDate() + (3600 * 1000)) : Date.from(ZonedDateTime.now().plusHours(1).toInstant())));
        }
        result.put("sumPrice", totalPrice);
        result.put("fromTime", (receipt == null || receipt.getFromTime() == null) ? "" : receipt.getFromTime());
        result.put("toTime", (receipt == null || receipt.getToTime() == null) ? "" : receipt.getToTime());
        result.put("deliveryDate", (receipt == null || receipt.getDeliveryDate() == null) ? "" : formatter.format(new Date(receipt.getDeliveryDate())));
        return result;
    }
}
