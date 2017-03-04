package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.PayTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PayType and its DTO PayTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayTypeMapper {

    @Mapping(source = "receipt.id", target = "receiptId")
    @Mapping(source = "receipt.docNum", target = "receiptDocNum")
    PayTypeDTO payTypeToPayTypeDTO(PayType payType);

    List<PayTypeDTO> payTypesToPayTypeDTOs(List<PayType> payTypes);

    @Mapping(source = "receiptId", target = "receipt")
    PayType payTypeDTOToPayType(PayTypeDTO payTypeDTO);

    List<PayType> payTypeDTOsToPayTypes(List<PayTypeDTO> payTypeDTOs);

    default Receipt receiptFromId(Long id) {
        if (id == null) {
            return null;
        }
        Receipt receipt = new Receipt();
        receipt.setId(id);
        return receipt;
    }
}
