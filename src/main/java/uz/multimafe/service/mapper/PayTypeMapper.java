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
    PayTypeDTO payTypeToPayTypeDTO(PayType payType);

    List<PayTypeDTO> payTypesToPayTypeDTOs(List<PayType> payTypes);

    @Mapping(source = "paymentTypeId", target = "paymentType")
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
}
