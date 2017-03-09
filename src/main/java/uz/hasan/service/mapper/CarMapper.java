package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.CarDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Car and its DTO CarDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarMapper {

    @Mapping(source = "carModel.id", target = "carModelId")
    @Mapping(source = "carModel.name", target = "carModelName")
    @Mapping(source = "carColor.id", target = "carColorId")
    @Mapping(source = "carColor.name", target = "carColorName")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    CarDTO carToCarDTO(Car car);

    List<CarDTO> carsToCarDTOs(List<Car> cars);

    @Mapping(source = "carModelId", target = "carModel")
    @Mapping(source = "carColorId", target = "carColor")
    @Mapping(source = "typeId", target = "type")
    @Mapping(target = "drivers", ignore = true)
    @Mapping(target = "receipts", ignore = true)
    Car carDTOToCar(CarDTO carDTO);

    List<Car> carDTOsToCars(List<CarDTO> carDTOs);

    default CarModel carModelFromId(Long id) {
        if (id == null) {
            return null;
        }
        CarModel carModel = new CarModel();
        carModel.setId(id);
        return carModel;
    }

    default CarColor carColorFromId(Long id) {
        if (id == null) {
            return null;
        }
        CarColor carColor = new CarColor();
        carColor.setId(id);
        return carColor;
    }

    default CarType carTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        CarType carType = new CarType();
        carType.setId(id);
        return carType;
    }
}
