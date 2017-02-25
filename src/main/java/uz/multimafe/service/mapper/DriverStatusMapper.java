package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.DriverStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DriverStatus and its DTO DriverStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverStatusMapper {

    DriverStatusDTO driverStatusToDriverStatusDTO(DriverStatus driverStatus);

    List<DriverStatusDTO> driverStatusesToDriverStatusDTOs(List<DriverStatus> driverStatuses);

    DriverStatus driverStatusDTOToDriverStatus(DriverStatusDTO driverStatusDTO);

    List<DriverStatus> driverStatusDTOsToDriverStatuses(List<DriverStatusDTO> driverStatusDTOs);
}
