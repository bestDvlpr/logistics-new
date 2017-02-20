package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.ReceiptDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Receipt and its DTO ReceiptDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReceiptMapper {

    ReceiptDTO receiptToReceiptDTO(Receipt receipt);

    List<ReceiptDTO> receiptsToReceiptDTOs(List<Receipt> receipts);

    Receipt receiptDTOToReceipt(ReceiptDTO receiptDTO);

    List<Receipt> receiptDTOsToReceipts(List<ReceiptDTO> receiptDTOs);
}
