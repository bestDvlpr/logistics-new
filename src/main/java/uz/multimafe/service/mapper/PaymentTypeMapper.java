package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.PaymentTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PaymentType and its DTO PaymentTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentTypeMapper {

    PaymentTypeDTO paymentTypeToPaymentTypeDTO(PaymentType paymentType);

    List<PaymentTypeDTO> paymentTypesToPaymentTypeDTOs(List<PaymentType> paymentTypes);

    PaymentType paymentTypeDTOToPaymentType(PaymentTypeDTO paymentTypeDTO);

    List<PaymentType> paymentTypeDTOsToPaymentTypes(List<PaymentTypeDTO> paymentTypeDTOs);
}
