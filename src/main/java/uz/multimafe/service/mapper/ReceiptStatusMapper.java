package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.ReceiptStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ReceiptStatus and its DTO ReceiptStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReceiptStatusMapper {

    ReceiptStatusDTO receiptStatusToReceiptStatusDTO(ReceiptStatus receiptStatus);

    List<ReceiptStatusDTO> receiptStatusesToReceiptStatusDTOs(List<ReceiptStatus> receiptStatuses);

    ReceiptStatus receiptStatusDTOToReceiptStatus(ReceiptStatusDTO receiptStatusDTO);

    List<ReceiptStatus> receiptStatusDTOsToReceiptStatuses(List<ReceiptStatusDTO> receiptStatusDTOs);
}
