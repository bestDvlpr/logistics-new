package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.XmlHolderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity XmlHolder and its DTO XmlHolderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface XmlHolderMapper {

    XmlHolderDTO xmlHolderToXmlHolderDTO(XmlHolder xmlHolder);

    List<XmlHolderDTO> xmlHoldersToXmlHolderDTOs(List<XmlHolder> xmlHolders);

    XmlHolder xmlHolderDTOToXmlHolder(XmlHolderDTO xmlHolderDTO);

    List<XmlHolder> xmlHolderDTOsToXmlHolders(List<XmlHolderDTO> xmlHolderDTOs);
}
