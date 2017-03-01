package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.PaymentTypeDTO;

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
