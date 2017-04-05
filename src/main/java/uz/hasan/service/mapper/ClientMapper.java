package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.ClientDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper {

    @Mapping(target = "phoneNumbers", ignore = true)
    ClientDTO clientToClientDTO(Client client);

    List<ClientDTO> clientsToClientDTOs(List<Client> clients);

    @Mapping(target = "phoneNumbers", ignore = true)
    Client clientDTOToClient(ClientDTO clientDTO);

    List<Client> clientDTOsToClients(List<ClientDTO> clientDTOs);
}
