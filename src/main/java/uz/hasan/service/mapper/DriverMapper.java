package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.DriverDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Driver and its DTO DriverDTO.
 */
@Mapper(componentModel = "spring", uses = {CarMapper.class, })
public interface DriverMapper {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.name", target = "statusName")
    DriverDTO driverToDriverDTO(Driver driver);

    List<DriverDTO> driversToDriverDTOs(List<Driver> drivers);

    @Mapping(source = "statusId", target = "status")
    Driver driverDTOToDriver(DriverDTO driverDTO);

    List<Driver> driverDTOsToDrivers(List<DriverDTO> driverDTOs);

    default Car carFromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }

    default DriverStatus driverStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        DriverStatus driverStatus = new DriverStatus();
        driverStatus.setId(id);
        return driverStatus;
    }
}
