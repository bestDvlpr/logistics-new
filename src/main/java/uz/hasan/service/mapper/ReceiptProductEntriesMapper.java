package uz.hasan.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.LoyaltyCard;
import uz.hasan.domain.ProductEntry;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.User;
import uz.hasan.repository.ClientRepository;
import uz.hasan.repository.CompanyRepository;
import uz.hasan.repository.PayMasterRepository;
import uz.hasan.repository.UserRepository;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: hasan @date: 3/11/17.
 */
@Service
@Transactional
public class ReceiptProductEntriesMapper {
    private final ProductEntryMapper productEntryMapper;
    private final ReceiptMapper receiptMapper;
    private final ClientAndAddressesMapper clientMapper;
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final ClientRepository clientRepository;
    private final CustomProductEntriesMapper customProductEntryMapper;
    private final PayMasterRepository payMasterRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;

    public ReceiptProductEntriesMapper(ProductEntryMapper productEntryMapper,
                                       ReceiptMapper receiptMapper,
                                       ClientAndAddressesMapper clientMapper,
                                       CompanyRepository companyRepository,
                                       UserService userService,
                                       ClientRepository clientRepository,
                                       CustomProductEntriesMapper customProductEntriesMapper,
                                       PayMasterRepository payMasterRepository,
                                       AddressMapper addressMapper,
                                       UserRepository userRepository,
                                       CompanyMapper companyMapper) {
        this.productEntryMapper = productEntryMapper;
        this.receiptMapper = receiptMapper;
        this.companyRepository = companyRepository;
        this.clientMapper = clientMapper;
        this.userService = userService;
        this.clientRepository = clientRepository;
        this.customProductEntryMapper = customProductEntriesMapper;
        this.payMasterRepository = payMasterRepository;
        this.addressMapper = addressMapper;
        this.userRepository = userRepository;
        this.companyMapper = companyMapper;
    }

    public ReceiptProductEntriesDTO receiptToReceiptProductEntryDTO(Receipt receipt) {
        if (receipt == null) {
            return null;
        }
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(receipt);

        List<ProductEntry> productEntries = new ArrayList<>();

        productEntries.addAll(receipt.getProductEntries());

        List<ProductEntryDTO> productEntryDTOS = productEntryMapper.productEntriesToProductEntryDTOs(productEntries);

        ReceiptProductEntriesDTO entriesDTO = new ReceiptProductEntriesDTO();

        entriesDTO.setAddress(receipt.getAddress() == null ? null : addressMapper.addressToAddressDTO(receipt.getAddress()));
        entriesDTO.setId(receiptDTO.getId());
        entriesDTO.setClientFirstName(receiptDTO.getClientFirstName());
        entriesDTO.setClientId(receiptDTO.getClientId());
        entriesDTO.setDocID(receiptDTO.getDocID());
        entriesDTO.setDocDate(receiptDTO.getDocDate());
        entriesDTO.setDocNum(receiptDTO.getDocNum());
        entriesDTO.setDocType(receiptDTO.getDocType());
        entriesDTO.setProductEntries(productEntryDTOS);
        entriesDTO.setWholeSaleFlag(receiptDTO.getWholeSaleFlag());
        entriesDTO.setStatus(receiptDTO.getStatus());
        entriesDTO.setClient(clientMapper.clientToClientAndAddressesDTO(receipt.getClient()));
        entriesDTO.setPreviousDocID(receiptDTO.getPreviousDocID());
        entriesDTO.setPayMasterId(receiptDTO.getPayMasterId());
        entriesDTO.setPayMasterPayMasterName(receiptDTO.getPayMasterPayMasterName());
        entriesDTO.setCompanyId(receiptDTO.getCompanyId());
        entriesDTO.setCompanyName(receiptDTO.getCompanyName());
        entriesDTO.setFromTime(receipt.getFromTime());
        entriesDTO.setToTime(receipt.getToTime());
        entriesDTO.setSentToDCTime(receipt.getSentToDCTime());

        LoyaltyCard loyaltyCard = receipt.getLoyaltyCard();

        entriesDTO.setLoyaltyCardId(loyaltyCard == null ? null : loyaltyCard.getId());
        entriesDTO.setLoyaltyCardLoyaltyCardID(loyaltyCard == null ? null : loyaltyCard.getLoyaltyCardID());

        User markedAsDeliveredBy = receipt.getMarkedAsDeliveredBy();

        entriesDTO.setMarkedAsDeliveredById(markedAsDeliveredBy == null ? null : markedAsDeliveredBy.getId());
        entriesDTO.setMarkedAsDeliveredByLogin(markedAsDeliveredBy == null ? null : markedAsDeliveredBy.getLogin());
        entriesDTO.setDeliveredTime(receipt.getDeliveredTime());

        User sentBy = receipt.getSentBy();

        entriesDTO.setSentById(sentBy == null ? null : sentBy.getId());
        entriesDTO.setSentByLogin(sentBy == null ? null : sentBy.getLogin());

        entriesDTO.setDeliveryDate(receipt.getDeliveryDate());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime deliveryDate = receipt.getDeliveredTime();
        if (deliveryDate != null) {
            entriesDTO.setDeliveredDateTime(receipt.getDeliveredTime().format(dateTimeFormatter));
        }
        entriesDTO.setReceiver(receipt.getReceiver() != null ? companyMapper.companyToCompanyDTO(receipt.getReceiver()) : null);
        if (entriesDTO.getReceiver() != null) {
            entriesDTO.setReceiverId(receipt.getReceiver().getId());
            entriesDTO.setReceiverName(receipt.getReceiver().getName());
        }

        return entriesDTO;
    }

    public List<ReceiptProductEntriesDTO> receiptsToReceiptProductEntryDTOs(List<Receipt> receiptList) {
        if (receiptList == null || receiptList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ReceiptProductEntriesDTO> receiptProductEntriesDTOS = new ArrayList<>();
        for (Receipt receipt : receiptList) {
            receiptProductEntriesDTOS.add(receiptToReceiptProductEntryDTO(receipt));
        }
        return receiptProductEntriesDTOS;
    }

    public Receipt receiptProductEntryDTOToReceipt(ReceiptProductEntriesDTO receiptDTO) {
        if (receiptDTO == null) {
            return null;
        }
        Receipt receipt = new Receipt();
        receipt.setId(receiptDTO.getId());
        receipt.setDocType(receiptDTO.getDocType());
        receipt.setDocNum(receiptDTO.getDocNum());
        receipt.setDocDate(receiptDTO.getDocDate());
        receipt.setDocID(receiptDTO.getDocID());
        receipt.setPreviousDocID(receiptDTO.getPreviousDocID());
        receipt.setClient(receiptDTO.getClientId() != null ? clientRepository.findOne(receiptDTO.getClientId()) : null);
        receipt.setProductEntries(new HashSet<>(customProductEntryMapper.productEntryDTOsToProductEntries(receiptDTO.getProductEntries())));
        receipt.setPayMaster(receiptDTO.getPayMasterId() != null ? payMasterRepository.findOne(receiptDTO.getPayMasterId()) : null);
        if (receiptDTO.getLoyaltyCardId() != null) {
            receipt.setLoyaltyCard(receiptDTO.getLoyaltyCardId() != null ? new LoyaltyCard(receiptDTO.getLoyaltyCardId()) : null);
        }
        receipt.setStatus(receiptDTO.getStatus());
        receipt.setWholeSaleFlag(receiptDTO.getWholeSaleFlag());
        receipt.setCompany(receiptDTO.getCompanyId() != null ? companyRepository.findOne(receiptDTO.getCompanyId()) : null);
        receipt.setFromTime(receiptDTO.getFromTime());
        receipt.setToTime(receiptDTO.getToTime());
        receipt.setSentToDCTime(receiptDTO.getSentToDCTime());
        receipt.setMarkedAsDeliveredBy(receiptDTO.getMarkedAsDeliveredById() != null ? userService.getUserWithAuthorities(receiptDTO.getMarkedAsDeliveredById()) : null);
        receipt.setDeliveredTime(receiptDTO.getDeliveredTime());
        receipt.setAddress(receiptDTO.getAddress() != null ? addressMapper.addressDTOToAddress(receiptDTO.getAddress()) : null);
        receipt.setSentBy(receiptDTO.getSentById() != null ? userRepository.findOne(receiptDTO.getSentById()) : null);

        Long deliveryDate = receiptDTO.getDeliveryDate();
        receipt.setDeliveryDate(deliveryDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deliveredDateTime = receiptDTO.getDeliveredDateTime();
        ZonedDateTime dateTime = null;
        if (deliveredDateTime != null) {
            Date parse = null;
            try {
                parse = format.parse(deliveredDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (parse != null) {
                dateTime = ZonedDateTime.ofInstant(parse.toInstant(), ZoneId.systemDefault());
            }
        }
        receipt.setDeliveredTime(dateTime);
        if (receiptDTO.getReceiver() != null && receiptDTO.getReceiver().getId()!=null) {
            receipt.setReceiver(companyMapper.companyDTOToCompany(receiptDTO.getReceiver()));
        } else {
            receipt.setReceiver(receiptDTO.getReceiverId() != null ? companyRepository.findOne(receiptDTO.getReceiverId()) : null);
        }

        return receipt;
    }
}
