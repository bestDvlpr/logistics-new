package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.DriverStatusDTO;

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
