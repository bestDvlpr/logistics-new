package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.CarTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CarType and its DTO CarTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarTypeMapper {

    CarTypeDTO carTypeToCarTypeDTO(CarType carType);

    List<CarTypeDTO> carTypesToCarTypeDTOs(List<CarType> carTypes);

    CarType carTypeDTOToCarType(CarTypeDTO carTypeDTO);

    List<CarType> carTypeDTOsToCarTypes(List<CarTypeDTO> carTypeDTOs);
}
