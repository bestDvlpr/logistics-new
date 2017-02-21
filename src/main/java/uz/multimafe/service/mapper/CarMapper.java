package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.CarDTO;

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
    CarDTO carToCarDTO(Car car);

    List<CarDTO> carsToCarDTOs(List<Car> cars);

    @Mapping(source = "carModelId", target = "carModel")
    @Mapping(source = "carColorId", target = "carColor")
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
}
