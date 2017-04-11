package uz.hasan.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.repository.*;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @author: hasan @date: 3/11/17.
 */
@Service
@Transactional
public class ReceiptProductEntriesMapper {
    private final ProductEntryMapper productEntryMapper;
    private final ReceiptMapper receiptMapper;
    private final ClientMapper clientMapper;
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final ClientRepository clientRepository;
    private final CustomProductEntriesMapper customProductEntryMapper;
    private final PayMasterRepository payMasterRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    public ReceiptProductEntriesMapper(ProductEntryMapper productEntryMapper,
                                       ReceiptMapper receiptMapper,
                                       ClientMapper clientMapper,
                                       ShopRepository shopRepository,
                                       UserService userService,
                                       ClientRepository clientRepository,
                                       CustomProductEntriesMapper customProductEntriesMapper,
                                       PayMasterRepository payMasterRepository,
                                       AddressMapper addressMapper,
                                       UserRepository userRepository) {
        this.productEntryMapper = productEntryMapper;
        this.receiptMapper = receiptMapper;
        this.shopRepository = shopRepository;
        this.clientMapper = clientMapper;
        this.userService = userService;
        this.clientRepository = clientRepository;
        this.customProductEntryMapper = customProductEntriesMapper;
        this.payMasterRepository = payMasterRepository;
        this.addressMapper = addressMapper;
        this.userRepository = userRepository;
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
        entriesDTO.setAddresses(receiptDTO.getAddresses());
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
        entriesDTO.setClient(clientMapper.clientToClientDTO(receipt.getClient()));
        entriesDTO.setPreviousDocID(receiptDTO.getPreviousDocID());
        entriesDTO.setPayMasterId(receiptDTO.getPayMasterId());
        entriesDTO.setPayMasterPayMasterName(receiptDTO.getPayMasterPayMasterName());
        entriesDTO.setShopId(receiptDTO.getShopId());
        entriesDTO.setShopName(receiptDTO.getShopName());
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
        receipt.setShop(receiptDTO.getShopId() != null ? shopRepository.findOne(receiptDTO.getShopId()) : null);
        receipt.setFromTime(receiptDTO.getFromTime());
        receipt.setToTime(receiptDTO.getToTime());
        receipt.setSentToDCTime(receiptDTO.getSentToDCTime());
        receipt.setMarkedAsDeliveredBy(receiptDTO.getMarkedAsDeliveredById() != null ? userService.getUserWithAuthorities(receiptDTO.getMarkedAsDeliveredById()) : null);
        receipt.setDeliveredTime(receiptDTO.getDeliveredTime());
        receipt.setAddresses(receiptDTO.getAddresses() != null ? new HashSet<>(addressMapper.addressDTOsToAddresses(new ArrayList<>(receiptDTO.getAddresses()))) : null);
        receipt.setSentBy(receiptDTO.getSentById() != null ? userRepository.findOne(receiptDTO.getSentById()) : null);
        return receipt;
    }
}
