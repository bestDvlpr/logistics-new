package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.PayMasterDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PayMaster and its DTO PayMasterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayMasterMapper {

    PayMasterDTO payMasterToPayMasterDTO(PayMaster payMaster);

    List<PayMasterDTO> payMastersToPayMasterDTOs(List<PayMaster> payMasters);

    PayMaster payMasterDTOToPayMaster(PayMasterDTO payMasterDTO);

    List<PayMaster> payMasterDTOsToPayMasters(List<PayMasterDTO> payMasterDTOs);
}
