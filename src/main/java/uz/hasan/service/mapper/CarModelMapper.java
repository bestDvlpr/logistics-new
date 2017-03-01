package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.CarModelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CarModel and its DTO CarModelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarModelMapper {

    CarModelDTO carModelToCarModelDTO(CarModel carModel);

    List<CarModelDTO> carModelsToCarModelDTOs(List<CarModel> carModels);

    CarModel carModelDTOToCarModel(CarModelDTO carModelDTO);

    List<CarModel> carModelDTOsToCarModels(List<CarModelDTO> carModelDTOs);
}
