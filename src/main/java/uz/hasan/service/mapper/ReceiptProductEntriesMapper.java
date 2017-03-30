package uz.hasan.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.repository.ReceiptRepository;
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
    private CarMapper carMapper;
    private ClientMapper clientMapper;

    public ReceiptProductEntriesMapper(ProductEntryMapper productEntryMapper,
                                       ReceiptMapper receiptMapper,
                                       ClientMapper clientMapper,
                                       CarMapper carMapper) {
        this.productEntryMapper = productEntryMapper;
        this.receiptMapper = receiptMapper;
        this.carMapper = carMapper;
        this.clientMapper = clientMapper;
    }

    public ReceiptProductEntriesDTO receiptToReceiptProductEntryDTO(Receipt receipt) {
        if (receipt == null) {
            return null;
        }
        ReceiptDTO receiptDTO = receiptMapper.receiptToReceiptDTO(receipt);
        List<ProductEntry> productEntries = new ArrayList<>();
        for (ProductEntry entry : receipt.getProductEntries()) {
            productEntries.add(entry);
        }
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
        entriesDTO.setLoyaltyCardId(receiptDTO.getLoyaltyCardId());
        entriesDTO.setLoyaltyCardLoyaltyCardID(receiptDTO.getLoyaltyCardLoyaltyCardID());
        entriesDTO.setProductEntries(productEntryDTOS);
        entriesDTO.setWholeSaleFlag(receiptDTO.getWholeSaleFlag());
        entriesDTO.setStatus(receiptDTO.getStatus());
        entriesDTO.setClient(clientMapper.clientToClientDTO(receipt.getClient()));
        entriesDTO.setPreviousDocID(receiptDTO.getPreviousDocID());
        entriesDTO.setPayMasterId(receiptDTO.getPayMasterId());
        entriesDTO.setPayMasterPayMasterName(receiptDTO.getPayMasterPayMasterName());
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
        receipt.setClient(new Client(receiptDTO.getClientId()));
        receipt.setProductEntries(new HashSet<>(productEntryMapper.productEntryDTOsToProductEntries(receiptDTO.getProductEntries())));
//        receipt.setPayTypes(new HashSet<>(payTypeMapper.payTypeDTOsToPayTypes(receiptDTO.getPayTypes())));
        /*if (receiptDTO.getCars() != null && !receiptDTO.getCars().isEmpty()) {
            receipt.setCars(new HashSet<>(carMapper.carDTOsToCars(new ArrayList<>(receiptDTO.getCars()))));
        }*/
        receipt.setPayMaster(new PayMaster(receiptDTO.getPayMasterId()));
        if (receiptDTO.getLoyaltyCardId() != null) {
            receipt.setLoyaltyCard(new LoyaltyCard(receiptDTO.getLoyaltyCardId()));
        }
        receipt.setStatus(receiptDTO.getStatus());
        receipt.setWholeSaleFlag(receiptDTO.getWholeSaleFlag());
        return receipt;
    }
}
