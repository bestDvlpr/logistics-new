package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.CarStatus;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.XDocTemplate;
import uz.hasan.repository.*;
import uz.hasan.security.AuthoritiesConstants;
import uz.hasan.service.ExcelService;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.service.mapper.CustomProductEntriesMapper;
import uz.hasan.service.mapper.ProductEntryMapper;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.service.mapper.ReceiptProductEntriesMapper;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    private final EntityManager entityManager;

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
                              ExcelService excelService,
                              EntityManager entityManager) {
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
        this.entityManager = entityManager;
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
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
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
    public Page<ReceiptProductEntriesDTO> findAllNewReceiptsByCompanyId(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        Page<Receipt> result = receiptRepository.findByStatusAndCompanyIdNumber(pageable, ReceiptStatus.NEW, userService.getUserWithAuthorities().getCompany().getIdNumber());
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all receipts by shopId.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllReceiptsByCompanyId(Pageable pageable) {
        log.debug("Request to get all new Receipts");

        User userWithAuthorities = userService.getUserWithAuthorities();
        Page<Receipt> result;
        if (userWithAuthorities.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CASHIER))) {
            String idNumber = userWithAuthorities.getCompany().getIdNumber();
            result = receiptRepository.findByCompanyIdNumberOrderByDocDateDesc(pageable, idNumber);
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
    public List<ReceiptProductEntriesDTO> sendOrder(ReceiptProductEntriesDTO receiptDTO) {

        Receipt receipt = receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(receiptDTO);

        Set<ProductEntry> productEntries = receipt.getProductEntries();
        productEntries.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.APPLICATION_SENT));
        // 1. Check whether product entries addresses are the same
        Address address = productEntries.iterator().next().getAddress();
        boolean allWithTheSameAddress = productEntries.stream().allMatch(productEntry -> productEntry.getAddress().equals(address));
        // 2. If they are the same, then continue as usual
        List<Receipt> results = new ArrayList<>();
        if (allWithTheSameAddress) {
            receipt = sendReceiptWithProds(receipt, productEntries, false);
            results.add(receipt);
        } else {// 3. If they are not the same:
            // 3.0. Collect all addresses
            Set<Address> addresses = new HashSet<>();
            productEntries.forEach(productEntry -> addresses.add(productEntry.getAddress()));
            Set<ProductEntry> extracted = new HashSet<>();//to collect by address
            for (ProductEntry productEntry : productEntries) {
                if (productEntry.getAddress().equals(address)) {
                    extracted.add(productEntry);
                }
            }
            receipt.setProductEntries(extracted);
            receipt.setAddress(address);
            Receipt saved = sendReceiptWithProds(receipt, extracted, false);

            addresses.remove(address);
            // 3.1. Create new receipt and save
            for (Address addrs : addresses) {
                Set<ProductEntry> sorted = new HashSet<>();
                // 3.2. Collect products with the same address
                for (ProductEntry productEntry : productEntries) {
                    if (productEntry.getAddress().equals(addrs)) {
                        sorted.add(productEntry);
                    }
                }
                // 3.3. Detach the receipt to save it again
//                entityManager.detach(saved);
//                saved.setAddress(addrs);
                // 3.4. Set collected products to receipt and save
                sendReceiptWithProds(saved, sorted, true);
            }
            // 4. Redo the steps of 3.1, 3.2, 3.3, 3.4
        }

        return receiptProductEntriesMapper.receiptsToReceiptProductEntryDTOs(results);
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
            return receiptRepository.getCountByStatusAndCompanyIdNumber(ReceiptStatus.NEW, userWithAuthorities.getCompany().getIdNumber());
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

    /**
     * Download receipt as ms-word document.
     */
    @Override
    public void download(Long receiptId, HttpServletResponse response) {
        if (receiptId == null) {
            return;
        }

        Receipt receipt = receiptRepository.findOne(receiptId);
        Set<ProductEntry> productEntries = receipt.getProductEntries();

        Map<String, Object> hashMap = createHashMap(new ArrayList<>(productEntries));

        if (receipt.getStatus().equals(ReceiptStatus.APPLICATION_SENT)) {
            excelService.generateDocx(XDocTemplate.SHOP_DELIVERY_PRE_INVOICE, receipt.getDocID(), hashMap, response);
        }
        if (receipt.getStatus().equals(ReceiptStatus.ATTACHED_TO_DRIVER)) {
            excelService.generateDocx(XDocTemplate.SHOP_DELIVERY_INVOICE, receipt.getDocID(), hashMap, response);
        }

        if (receipt.getStatus().equals(ReceiptStatus.ATTACHED_TO_DRIVER)) {
            receipt.setStatus(ReceiptStatus.DELIVERY_PROCESS);
            productEntries.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.DELIVERY_PROCESS));
            productEntries.forEach(productEntry -> productEntry.setDeliveryStartTime(ZonedDateTime.now()));

            receiptRepository.save(receipt);
            productEntryRepository.save(productEntries);
        }
    }

    /**
     * Mark receipt and its products as delivered.
     *
     * @return receipt with attached to car products
     */
    @Override
    public ReceiptProductEntriesDTO delivered(ReceiptProductEntriesDTO receiptDTO) {
        if (receiptDTO == null) {
            return null;
        }

        Set<Car> attachedCars = new HashSet<>();
        Receipt receipt = receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(receiptDTO);
        for (ProductEntry entry : receipt.getProductEntries()) {
            attachedCars.add(entry.getAttachedCar());
        }

        for (Car car : attachedCars) {
            boolean carIsIdle = false;
            List<ProductEntry> productEntryList = productEntryRepository.findByAttachedCarNumber(car.getNumber());
            if (!productEntryList.isEmpty()) {
                carIsIdle = productEntryList.stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERED));
            }
            if (carIsIdle) {
                car.setStatus(CarStatus.IDLE);
            }
        }

        carRepository.save(attachedCars);

        return setStatusAndSave(receiptDTO, ReceiptStatus.DELIVERED);
    }

    /**
     * Mark receipt and its products as taken out.
     *
     * @return receipt with attached to car products
     */
    @Override
    public ReceiptProductEntriesDTO takenOut(ReceiptProductEntriesDTO receiptDTO) {
        if (receiptDTO == null) {
            return null;
        }

        return setStatusAndSave(receiptDTO, ReceiptStatus.TAKEOUT);
    }

    /**
     * Get all the receipts.
     *
     * @return the list of entities
     */
    @Override
    public List<ReceiptProductEntriesDTO> findAll() {
        List<Receipt> receiptList = receiptRepository.findAll();
        return receiptProductEntriesMapper.receiptsToReceiptProductEntryDTOs(receiptList);
    }

    @Override
    public Page<ReceiptProductEntriesDTO> findAllAccepted(Pageable pageable) {
        log.debug("Request to get accepted  Receipts");
        List<ReceiptStatus> statuses = new ArrayList<>();
        statuses.add(ReceiptStatus.NEW);
        statuses.add(ReceiptStatus.APPLICATION_SENT);
        statuses.add(ReceiptStatus.DELIVERED);
        statuses.add(ReceiptStatus.TAKEOUT);
        Page<Receipt> result = receiptRepository.findAllByStatusNotIn(pageable, statuses);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all archived receipts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findArchivedReceipts(Pageable pageable) {
        log.debug("Request to get all Archived Receipts");
        List<ReceiptStatus> statuses = new ArrayList<>();
        statuses.add(ReceiptStatus.DELIVERED);
        statuses.add(ReceiptStatus.TAKEOUT);
        Page<Receipt> result = receiptRepository.findAllByStatusIn(pageable, statuses);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    private Receipt sendReceiptWithProds(Receipt receipt, Set<ProductEntry> productEntries, Boolean isNew) {
        Receipt result;
        User userWithAuthorities = userService.getUserWithAuthorities();
        if (isNew) {
            Receipt newReceipt = new Receipt();
            BeanUtils.copyProperties(receipt, newReceipt);
            newReceipt.setAddress(productEntries.iterator().next().getAddress());
            newReceipt.setProductEntries(productEntries);
            newReceipt.setLoyaltyCard(receipt.getLoyaltyCard());
            newReceipt.setClient(receipt.getClient());
            newReceipt.setCompany(receipt.getCompany());
            newReceipt.setPayMaster(receipt.getPayMaster());
            HashSet<PayType> objects = new HashSet<>();
            objects.addAll(receipt.getPayTypes());
            newReceipt.setPayTypes(objects);
            newReceipt.setMarkedAsDeliveredBy(receipt.getMarkedAsDeliveredBy());
            newReceipt.setStatus(ReceiptStatus.APPLICATION_SENT);
            newReceipt.setSentToDCTime(ZonedDateTime.now());
            newReceipt.setSentBy(userWithAuthorities);
            newReceipt.setId(null);
            result = receiptRepository.save(newReceipt);
        } else {
            receipt.setAddress(productEntries.iterator().next().getAddress());
            receipt.setMarkedAsDeliveredBy(receipt.getMarkedAsDeliveredBy());
            receipt.setStatus(ReceiptStatus.APPLICATION_SENT);
            receipt.setSentToDCTime(ZonedDateTime.now());
            receipt.setSentBy(userWithAuthorities);
            result = receiptRepository.save(receipt);
        }

        productEntries.forEach(productEntry -> productEntry.setReceipt(result));
        productEntryRepository.save(productEntries);
        return result;
    }

    private Map<String, Object> createHashMap(List<ProductEntry> productEntries) {
        if (productEntries == null || productEntries.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        ProductEntry productEntry = productEntries.get(0);
        Company company = productEntry.getCompany();
        if (company == null || company.getAddress() == null) {
            result.put("shopAddress", "");
        } else {
            Address shopAddress = company.getAddress();
            String streetAddress = "";
            streetAddress += shopAddress.getStreetAddress() + ", ";
            streetAddress += shopAddress.getDistrict().getName() + ", ";
            streetAddress += (shopAddress.getCity() != null) ? shopAddress.getCity().getName() + ", " : "";
            streetAddress += shopAddress.getRegion().getName() + ", ";
            streetAddress += shopAddress.getCountry().getName();
            result.put("shopAddress", streetAddress);
        }
        result.put("shopBankAccountNumber", ""); // TODO: 4/28/17 change shop to company
        result.put("shopBankBranchRegion", "");
        result.put("shopBankName", "");
        result.put("shopBankMfo", "");
        result.put("shopName", (company != null) ? company.getName() : "");
        result.put("shopOkonx", "");
        result.put("shopOked", "");
        result.put("shopTin", "");
        /* add client fields*/
        Receipt receipt = productEntry.getReceipt();
        Client client = null;
        if (receipt != null) {
            client = receipt.getClient();
        }
        if (receipt == null || receipt.getAddress() == null) {
            result.put("clientAddress", "");
        } else {
            Address address = receipt.getAddress();
            String streetAddress = "";
            streetAddress += address.getStreetAddress() + ", ";
            streetAddress += address.getDistrict().getName() + ", ";
            streetAddress += (address.getCity() != null) ? address.getCity().getName() + ", " : "";
            streetAddress += address.getRegion().getName() + ", ";
            streetAddress += address.getCountry().getName();
            result.put("clientAddress", streetAddress);
        }
        result.put("clientBankAccountNumber", (client != null && client.getBankAccountNumber() != null) ? client.getBankAccountNumber() : "");
        result.put("clientBankBranchRegion", (client != null && client.getBankFilialRegion() != null) ? client.getBankFilialRegion() : "");
        result.put("clientBankName", (client != null && client.getBankName() != null) ? client.getBankName() : "");
        result.put("clientBankMfo", (client != null && client.getMfo() != null) ? client.getMfo() : "");
        result.put("clientOkonx", (client != null && client.getOkonx() != null) ? client.getOkonx() : "");
        result.put("clientOked", (client != null && client.getOked() != null) ? client.getOked() : "");
        result.put("clientTin", (client != null && client.getTin() != null) ? client.getTin() : "");
        result.put("clientFirstName", (client != null && client.getFirstName() != null) ? client.getFirstName() : "");
        result.put("clientLastName", (client != null && client.getLastName() != null) ? client.getLastName() : "");
        if (client != null && !client.getPhoneNumbers().isEmpty()) {
            List<String> phoneNumbers = new ArrayList<>();
            for (PhoneNumber number : client.getPhoneNumbers()) {
                phoneNumbers.add(number.getNumber());
            }
            result.put("clientPhoneNumbers", phoneNumbers);
        }
        result.put("receiptDocId", (receipt != null) ? receipt.getDocID() : "");
        result.put("receiptId", receipt != null ? receipt.getId() : "");

        Car attachedCar = receipt != null ? receipt.getProductEntries().iterator().next().getAttachedCar() : null;
        if (attachedCar != null && attachedCar.getNumber() != null) {
            result.put("carNumber", attachedCar.getNumber());
        }
        result.put("driverName", "");
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
            products.add(product);
//            result.put("shopPhoneNumbers", company.getPhoneNumbers()); // TODO: 06.04.2017 add phoneNumbers attr. to company model
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

    private ReceiptProductEntriesDTO setStatusAndSave(ReceiptProductEntriesDTO receiptDTO, ReceiptStatus status) {
        Receipt receipt = receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(receiptDTO);
        receipt.setStatus(status);

        if (status.equals(ReceiptStatus.DELIVERED)) {
            receipt.setMarkedAsDeliveredBy(userService.getUserWithAuthorities());
            if (receiptDTO.getDeliveredDateTime() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(receiptDTO.getDeliveredDateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    ZonedDateTime deliveredTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                    receipt.setDeliveredTime(deliveredTime);
                    receipt.getProductEntries().forEach(productEntry -> productEntry.setDeliveryEndTime(deliveredTime));
                } else {
                    ZonedDateTime now = ZonedDateTime.now();
                    receipt.setDeliveredTime(now);
                    receipt.getProductEntries().forEach(productEntry -> productEntry.setDeliveryEndTime(now));
                }
            }
        }

        Set<ProductEntry> productEntries = receipt.getProductEntries();
        productEntries.forEach(productEntry -> productEntry.setStatus(status));

        Set<Car> cars = new HashSet<>();
        for (ProductEntry entry : productEntries) {
            cars.add(entry.getAttachedCar());
        }
        if (!cars.isEmpty() &&
            cars.iterator().next() != null &&
            cars.stream().allMatch(car -> car.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERED)))) {
            cars.forEach(car -> car.setStatus(CarStatus.IDLE));
            carRepository.save(cars);
        }

        receiptRepository.save(receipt);
        productEntryRepository.save(productEntries);
        return receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipt);
    }
}
