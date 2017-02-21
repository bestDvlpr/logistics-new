package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.DriverDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Driver and its DTO DriverDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverMapper {

    @Mapping(source = "car.id", target = "carId")
    @Mapping(source = "car.number", target = "carNumber")
    DriverDTO driverToDriverDTO(Driver driver);

    List<DriverDTO> driversToDriverDTOs(List<Driver> drivers);

    @Mapping(source = "carId", target = "car")
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
}
