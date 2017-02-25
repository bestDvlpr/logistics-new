package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.PayTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PayType and its DTO PayTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayTypeMapper {

    @Mapping(source = "paymentType.id", target = "paymentTypeId")
    @Mapping(source = "paymentType.name", target = "paymentTypeName")
    @Mapping(source = "receipt.id", target = "receiptId")
    @Mapping(source = "receipt.docNum", target = "receiptDocNum")
    PayTypeDTO payTypeToPayTypeDTO(PayType payType);

    List<PayTypeDTO> payTypesToPayTypeDTOs(List<PayType> payTypes);

    @Mapping(source = "paymentTypeId", target = "paymentType")
    @Mapping(source = "receiptId", target = "receipt")
    PayType payTypeDTOToPayType(PayTypeDTO payTypeDTO);

    List<PayType> payTypeDTOsToPayTypes(List<PayTypeDTO> payTypeDTOs);

    default PaymentType paymentTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentType paymentType = new PaymentType();
        paymentType.setId(id);
        return paymentType;
    }

    default Receipt receiptFromId(Long id) {
        if (id == null) {
            return null;
        }
        Receipt receipt = new Receipt();
        receipt.setId(id);
        return receipt;
    }
}
