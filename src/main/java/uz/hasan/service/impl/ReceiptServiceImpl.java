package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.*;
import uz.hasan.repository.*;
import uz.hasan.security.AuthoritiesConstants;
import uz.hasan.service.ExcelService;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UserService;
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
import java.util.stream.Collectors;

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
    public Page<ReceiptProductEntriesDTO> findAllNewReceipts(Pageable pageable) {
        log.debug("Request to get all new Receipts");

        Page<Receipt> result = receiptRepository.findByStatus(pageable, ReceiptStatus.NEW);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all the new receipts by shop id.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllNewSalesRetailReceipts(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        List<DocType> docTypes = new ArrayList<>();
        docTypes.add(DocType.SALES);
        Company company = userService.getUserWithAuthorities().getCompany();
        String idNumber = null;
        if (company != null) {
            idNumber = company.getIdNumber();
        }
        Page<Receipt> result = receiptRepository.findByStatusAndDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(pageable, ReceiptStatus.NEW, docTypes, WholeSaleFlag.RETAIL, idNumber);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all the new receipts by shop id.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllNewCreditReceipts(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        List<DocType> docTypes = new ArrayList<>();
        docTypes.add(DocType.CREDIT);
        docTypes.add(DocType.INSTALLMENT);
        Page<Receipt> result = receiptRepository.findByStatusAndDocTypeInAndCompanyIdNumberOrderByIdDesc(pageable, ReceiptStatus.NEW, docTypes, userService.getUserWithAuthorities().getCompany().getIdNumber());
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all the new receipts by shop id.
     *
     * @param pageable the pagination information
     * @return the list of new entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findAllNewSalesWholeSaleReceipts(Pageable pageable) {
        log.debug("Request to get all new Receipts");
        List<DocType> docTypes = new ArrayList<>();
        docTypes.add(DocType.CREDIT);
        docTypes.add(DocType.INSTALLMENT);
        Company company = userService.getUserWithAuthorities().getCompany();
        String idNumber = null;
        if (company != null) {
            idNumber = company.getIdNumber();
        }
        Page<Receipt> result = receiptRepository.findByStatusAndDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(pageable, ReceiptStatus.NEW, docTypes, WholeSaleFlag.WHOLESALE, idNumber);
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
        Set<Authority> authorities = userWithAuthorities.getAuthorities();
        String idNumber = userWithAuthorities.getCompany().getIdNumber();

        Page<Receipt> result;

        if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CREDIT))) {
            List<DocType> types = new ArrayList<>();
            types.add(DocType.INSTALLMENT);
            types.add(DocType.CREDIT);
            result = receiptRepository.findByDocTypeInAndCompanyIdNumberOrderByDocDateDesc(pageable, types, idNumber);
        } else if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.WAREHOUSE))) {
            result = receiptRepository.findByDocTypeAndCompanyIdNumberOrderByDocDateDesc(pageable, DocType.DISPLACEMENT, idNumber);
        } else if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CORPORATE))) {
            List<DocType> types = new ArrayList<>();
            types.add(DocType.DISPLACEMENT);
            types.add(DocType.SALES);
            result = receiptRepository.findAllByDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(pageable, types, WholeSaleFlag.WHOLESALE, idNumber);
        } else if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CASHIER))) {
            result = receiptRepository.findAllByDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(pageable, Arrays.asList(DocType.values()), WholeSaleFlag.RETAIL, idNumber);
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

        Set<Receipt> results = divideReceipt(receiptDTO);

        return receiptProductEntriesMapper.receiptsToReceiptProductEntryDTOs(new ArrayList<>(results));
    }

    private Set<Receipt> divideReceipt(ReceiptProductEntriesDTO receiptDTO) {

        Receipt receipt = receiptProductEntriesMapper.receiptProductEntryDTOToReceipt(receiptDTO);
        Set<ProductEntry> receiptProductEntries = receipt.getProductEntries();
        Set<Receipt> receipts = new HashSet<>();
        Set<Set<ProductEntry>> entries = new HashSet<>();
        entries.add(receiptProductEntries);

        ProductEntry nextProductEntry = receiptProductEntries.iterator().next();
        SalesType deliveryFlag = nextProductEntry.getDeliveryFlag();
        Address address = nextProductEntry.getAddress();
        Long date = nextProductEntry.getDeliveryDate();

        Boolean withSameCar = true;
        if (nextProductEntry.getAttachedCar() != null && nextProductEntry.getAttachedCar().getNumber() != null) {
            String carNumber = nextProductEntry.getAttachedCar().getNumber();
            withSameCar = receiptProductEntries.stream().allMatch(productEntry -> productEntry.getAttachedCar().getNumber().equals(carNumber));
        }

        boolean withSameDeliveryFlag = receiptProductEntries.stream().allMatch(productEntry -> productEntry.getDeliveryFlag().equals(deliveryFlag));
        boolean withSameAddress = receiptProductEntries.stream().allMatch(productEntry -> productEntry.getAddress().equals(address));
        boolean withSameDeliveryDate = receiptProductEntries.stream().allMatch(productEntry -> productEntry.getDeliveryDate().equals(date));

        if (!withSameAddress) {
            Set<Set<ProductEntry>> result = new HashSet<>();
            for (Set<ProductEntry> entrySet : entries) {
                Set<Address> addresses = new HashSet<>();
                entrySet.forEach(productEntry -> addresses.add(productEntry.getAddress()));
                for (Address ad : addresses) {
                    Set<ProductEntry> byAddress = entrySet.stream()
                        .filter(productEntry -> productEntry.getAddress().equals(ad))
                        .collect(Collectors.toSet());
                    result.add(byAddress);
                }
            }
            entries = result;
        }

        if (!withSameDeliveryFlag) {
            Set<Set<ProductEntry>> result = new HashSet<>();
            for (Set<ProductEntry> entrySet : entries) {
                Set<SalesType> salesTypes = new HashSet<>();
                entrySet.forEach(productEntry -> salesTypes.add(productEntry.getDeliveryFlag()));
                for (SalesType salesType : salesTypes) {
                    Set<ProductEntry> bySalesType = entrySet.stream()
                        .filter(productEntry -> productEntry.getDeliveryFlag().equals(salesType))
                        .collect(Collectors.toSet());
                    result.add(bySalesType);
                }
            }
            entries = result;
        }

        if (!withSameDeliveryDate) {
            Set<Set<ProductEntry>> result = new HashSet<>();
            for (Set<ProductEntry> entrySet : entries) {
                Set<Long> dates = new HashSet<>();
                entrySet.forEach(productEntry -> dates.add(productEntry.getDeliveryDate()));
                for (Long d : dates) {
                    Set<ProductEntry> bySalesType = entrySet.stream()
                        .filter(productEntry -> productEntry.getDeliveryDate().equals(d))
                        .collect(Collectors.toSet());
                    result.add(bySalesType);
                }
            }
            entries = result;
        }

        if (!withSameCar) {
            Set<Set<ProductEntry>> result = new HashSet<>();
            for (Set<ProductEntry> entrySet : entries) {
                Set<String> numbers = new HashSet<>();
                entrySet.forEach(productEntry -> numbers.add(productEntry.getAttachedCar().getNumber()));
                for (String d : numbers) {
                    Set<ProductEntry> byCarNumber = entrySet.stream()
                        .filter(productEntry -> productEntry.getAttachedCar().getNumber().equals(d))
                        .collect(Collectors.toSet());
                    result.add(byCarNumber);
                }
            }
            entries = result;
        }

        if (entries.size() > 1) {
            Set<ProductEntry> productEntries = entries.iterator().next();
            receipts.add(sendReceiptWithProds(receipt, productEntries, false));
            entries.remove(productEntries);

            for (Set<ProductEntry> entrySet : entries) {
                receipts.add(sendReceiptWithProds(receipt, entrySet, true));
            }
        } else {
            Set<ProductEntry> productEntries = entries.iterator().next();
            receipts.add(sendReceiptWithProds(receipt, productEntries, false));
            entries.remove(productEntries);
        }
        return receipts;
    }

    /**
     * Count new receipts.
     *
     * @return count of new receipts
     */
    @Override
    public Long countNewReceipts() {
        User userWithAuthorities = userService.getUserWithAuthorities();
        if (userWithAuthorities == null || userWithAuthorities.getAuthorities().isEmpty()) {
            return 0L;
        }
        Set<Authority> authorities = userWithAuthorities.getAuthorities();
        if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN)) ||
            authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.MANAGER)) ||
            authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.DISPATCHER))) {
            List<DocType> docTypes = new ArrayList<>();
            docTypes.add(DocType.SALES);
            docTypes.add(DocType.RETURN);
            return receiptRepository.countByStatusAndDocTypeIn(ReceiptStatus.APPLICATION_SENT, docTypes);
        } else {
            List<DocType> docTypes = new ArrayList<>();
            WholeSaleFlag wholeSaleFlag = WholeSaleFlag.RETAIL;
            if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CREDIT))) {
                docTypes.add(DocType.CREDIT);
                docTypes.add(DocType.INSTALLMENT);
            }
            if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CASHIER))) {
                docTypes.add(DocType.SALES);
            }
            if (authorities.stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CORPORATE))) {
                docTypes.add(DocType.SALES);
                docTypes.add(DocType.DISPLACEMENT);
                wholeSaleFlag = WholeSaleFlag.WHOLESALE;
            }
            return receiptRepository.countByStatusAndCompanyIdNumberAndDocTypeInAndWholeSaleFlag(ReceiptStatus.NEW, userWithAuthorities.getCompany().getIdNumber(), docTypes, wholeSaleFlag);
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

        List<ProductEntry> productEntryList = customProductEntryMapper.productEntryDTOsToProductEntries(receiptDTO.getProductEntries());
        productEntryList.forEach(productEntry -> productEntry.setAttachedToCarTime(ZonedDateTime.now()));
        productEntryList.forEach(productEntry -> productEntry.setAttachedToDriverBy(userService.getUserWithAuthorities()));
        productEntryList.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.ATTACHED_TO_DRIVER));
        receiptDTO.setProductEntries(productEntryMapper.productEntriesToProductEntryDTOs(productEntryList));

        Set<Receipt> receipts = this.divideReceipt(receiptDTO);

//        List<ProductEntry> productEntries = productEntryRepository.save(productEntryList);
//
//        for (ProductEntry entry : productEntries) {
//            Car attachedCar = entry.getAttachedCar();
//            if (attachedCar != null) { // TODO: 4/3/17 mark as busy when car is fully occupied
//                attachedCar.setStatus(CarStatus.BUSY);
//                carRepository.save(attachedCar);
//            }
//        }
//
//        receiptDTO.setStatus(ReceiptStatus.ATTACHED_TO_DRIVER);

        return receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipts.iterator().next());
    }

    /**
     * Download receipt as ms-word document.
     *
     * @param receiptId Receipt object's id
     * @param response  HttpServletResponse object
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
     * @param receiptDTO {@link ReceiptProductEntriesDTO} object
     * @return receipt with attached to car products
     */
    @Override
    public ReceiptProductEntriesDTO delivered(ReceiptProductEntriesDTO receiptDTO) {
        if (receiptDTO == null) {
            return null;
        }

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
        statuses.add(ReceiptStatus.ATTACHED_TO_DRIVER);
        statuses.add(ReceiptStatus.DELIVERY_PROCESS);
        Page<Receipt> result = receiptRepository.findAllByStatusIn(pageable, statuses);
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

    /**
     * Get all archived receipts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findDisplacementReceipts(Pageable pageable) {
        log.debug("Request to get all displacement Receipts");
        Page<Receipt> result = receiptRepository.findAllByDocType(pageable, DocType.DISPLACEMENT);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all credit receipts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findCreditReceipts(Pageable pageable) {
        log.debug("Request to get all credit Receipts");
        List<DocType> docTypes = new ArrayList<>();
        docTypes.add(DocType.CREDIT);
        docTypes.add(DocType.INSTALLMENT);
        Company company = userService.getUserWithAuthorities().getCompany();
        String companyIdNumber = null;
        if (company != null) {
            companyIdNumber = company.getIdNumber();
        }
        Page<Receipt> result = receiptRepository.findAllByDocTypeInAndCompanyIdNumberOrderByIdDesc(pageable, docTypes, companyIdNumber);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all corporate receipts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findCorporateReceipts(Pageable pageable) {
        log.debug("Request to get all corporate Receipts");
        List<DocType> docTypes = new ArrayList<>();
        docTypes.add(DocType.DISPLACEMENT);
        docTypes.add(DocType.SALES);
//        Company company = userService.getUserWithAuthorities().getCompany();
//        String companyIdNumber = null;
//        if (company != null) {
//            companyIdNumber = company.getIdNumber();
//        }
//        Page<Receipt> result = receiptRepository.findAllByDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(pageable, docTypes, WholeSaleFlag.WHOLESALE, companyIdNumber);
        Page<Receipt> result = receiptRepository.findAllByDocTypeInAndWholeSaleFlagOrderByIdDesc(pageable, docTypes, WholeSaleFlag.WHOLESALE);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get credit receipts by company id.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findCreditReceiptsByCompanyId(Pageable pageable) {
        log.debug("Request to get all credit Receipts");
        String idNumber = userService.getUserWithAuthorities().getCompany().getIdNumber();
        List<DocType> docTypes = new ArrayList<>();
        docTypes.add(DocType.CREDIT);
        docTypes.add(DocType.INSTALLMENT);
        Page<Receipt> result = receiptRepository.findAllByDocTypeInAndCompanyIdNumberOrderByIdDesc(pageable, docTypes, idNumber);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Get all receipts.
     *
     * @param pageable the pagination information
     * @param clientId the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReceiptProductEntriesDTO> findByClientId(Pageable pageable, Long clientId) {
        log.debug("Request to get all Receipts of client {}", clientId);
        Page<Receipt> result = receiptRepository.findByClientId(pageable, clientId);
        return result.map(receiptProductEntriesMapper::receiptToReceiptProductEntryDTO);
    }

    /**
     * Cancel attached car of the receipt.
     *
     * @param id the receipt's id
     * @return the cancelled car receipt
     */
    @Override
    public ReceiptProductEntriesDTO cancelAttachedCar(Long id) {
        Receipt receipt = receiptRepository.findOne(id);

        receipt.setStatus(ReceiptStatus.APPLICATION_SENT);

        Set<ProductEntry> productEntries = receipt.getProductEntries();
        productEntries.forEach(productEntry -> productEntry.setAttachedCar(null));
        productEntries.forEach(productEntry -> productEntry.setAttachedToDriverBy(null));
        productEntries.forEach(productEntry -> productEntry.setAttachedToCarTime(null));
        productEntries.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.APPLICATION_SENT));

        productEntryRepository.save(productEntries);

        receipt = receiptRepository.save(receipt);

        return receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipt);
    }

    private Receipt sendReceiptWithProds(Receipt receipt, Set<ProductEntry> productEntries, Boolean isNew) {
        Receipt result;
        User userWithAuthorities = userService.getUserWithAuthorities();
        if (receipt == null || productEntries == null || productEntries.isEmpty()) {
            return null;
        }
        if (isNew) {
            if (productEntries.iterator().next().getDeliveryFlag().equals(SalesType.TAKEOUT)) {
                receipt.setStatus(ReceiptStatus.TAKEOUT);
                receipt.setDeliveryDate(null);
                receipt.setFromTime(null);
                receipt.setToTime(null);
                receipt.setAddress(null);

                productEntries.forEach(productEntry -> productEntry.setStatus(ReceiptStatus.TAKEOUT));
                productEntries.forEach(productEntry -> productEntry.setAddress(null));
            } else {
                if (userWithAuthorities.getAuthorities().contains(new Authority(AuthoritiesConstants.DISPATCHER))) {
                    receipt.setStatus(ReceiptStatus.ATTACHED_TO_DRIVER);
                } else {
                    receipt.setStatus(ReceiptStatus.APPLICATION_SENT);
                }
            }
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
            newReceipt.setSentToDCTime(ZonedDateTime.now());
            newReceipt.setSentBy(userWithAuthorities);
            newReceipt.setId(null);
            newReceipt.setDeliveryDate(productEntries.iterator().next().getDeliveryDate());
            result = receiptRepository.save(newReceipt);
        } else {
            receipt.setAddress(productEntries.iterator().next().getAddress());
            receipt.setMarkedAsDeliveredBy(receipt.getMarkedAsDeliveredBy());
            if (userWithAuthorities.getAuthorities().contains(new Authority(AuthoritiesConstants.DISPATCHER))) {
                receipt.setStatus(ReceiptStatus.ATTACHED_TO_DRIVER);
            } else {
                receipt.setStatus(ReceiptStatus.APPLICATION_SENT);
            }
            receipt.setSentToDCTime(ZonedDateTime.now());
            receipt.setSentBy(userWithAuthorities);
            receipt.setProductEntries(productEntries);
            receipt.setDeliveryDate(productEntries.iterator().next().getDeliveryDate());
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
        Company receiver = null;
        if (receipt != null) {
            client = receipt.getClient();
            if (client == null) {
                receiver = receipt.getReceiver();
            }
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
        result.put("clientFirstName", (client != null && client.getFirstName() != null) ? client.getFirstName() : "");
        result.put("clientLastName", (client != null && client.getLastName() != null) ? client.getLastName() : "");
        if (receiver != null) {
            result.put("clientFirstName", receiver.getName());
        }
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
            product.setPrice(entry.getPrice() != null ? String.valueOf(entry.getPrice()) : "---");
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
        productEntryRepository.save(productEntries);

        if (!cars.isEmpty() &&
            cars.iterator().next() != null &&
            cars.stream().allMatch(car -> car.getProductEntries().stream().allMatch(productEntry -> productEntry.getStatus().equals(ReceiptStatus.DELIVERED)))) {
            cars.forEach(car -> car.setStatus(CarStatus.IDLE));
            carRepository.save(cars);
        }

        receiptRepository.save(receipt);
        return receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(receipt);
    }
}
