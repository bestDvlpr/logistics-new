package uz.hasan.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.ProductEntry;
import uz.hasan.domain.Receipt;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: hasan @date: 3/11/17.
 */
@Service
@Transactional
public class ReceiptProductEntriesMapper {
    private final ProductEntryMapper productEntryMapper;
    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;

    public ReceiptProductEntriesMapper(ProductEntryMapper productEntryMapper, ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
        this.productEntryMapper = productEntryMapper;
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
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
        entriesDTO.setCars(receiptDTO.getCars());
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
}
