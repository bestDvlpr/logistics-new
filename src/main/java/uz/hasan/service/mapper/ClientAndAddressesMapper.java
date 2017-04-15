package uz.hasan.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Address;
import uz.hasan.domain.Client;
import uz.hasan.repository.AddressRepository;
import uz.hasan.service.dto.AddressDTO;
import uz.hasan.service.dto.ClientAndAddressesDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author: hasan @date: 3/9/17.
 */
@Service
@Transactional
public class ClientAndAddressesMapper {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final ClientMapper clientMapper;

    public ClientAndAddressesMapper(AddressMapper addressMapper, AddressRepository addressRepository,ClientMapper clientMapper) {
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.clientMapper = clientMapper;
    }

    public ClientAndAddressesDTO clientToClientAndAddressesDTO(Client client) {
        if (client == null) {
            return null;
        }
        ClientAndAddressesDTO clientDTO = new ClientAndAddressesDTO();
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setLastName(client.getLastName());
        clientDTO.setRegDate(client.getRegDate());

        Long clientId = client.getId();

        clientDTO.setId(clientId);
        Optional<List<Address>> byClientId = addressRepository.findByClientId(clientId);
        if (byClientId.isPresent()) {
            List<AddressDTO> addressDTOS = addressMapper.addressesToAddressDTOs(byClientId.get());
            clientDTO.setAddressDTOS(addressDTOS);
        }
        clientDTO.setNumbers(new ArrayList<>(client.getPhoneNumbers()));
        return clientDTO;
    }

    public Client clientAndAddressesDTOToClient(ClientAndAddressesDTO clientAndAddressesDTO) {
        if (clientAndAddressesDTO == null) {
            return null;
        }
        Client client = new Client();
        client.setFirstName(clientAndAddressesDTO.getFirstName());
        client.setLastName(clientAndAddressesDTO.getLastName());
        client.setRegDate(clientAndAddressesDTO.getRegDate());

        Long clientId = clientAndAddressesDTO.getId();

        client.setId(clientId);
        Optional<List<Address>> byClientId = addressRepository.findByClientId(clientId);
        byClientId.ifPresent(addresses -> client.setAddresses(new HashSet<>(addresses)));
        client.setPhoneNumbers(new HashSet<>(clientAndAddressesDTO.getNumbers()));
        return client;
    }

    public List<ClientAndAddressesDTO> clientToClientAndAddressesDTOs(List<Client> clients) {
        if (clients == null) {
            return null;
        }
        List<ClientAndAddressesDTO> clientDTOs = new ArrayList<>();
        for (Client client : clients) {
            ClientAndAddressesDTO clientDTO = clientToClientAndAddressesDTO(client);
            clientDTOs.add(clientDTO);
        }
        return clientDTOs;
    }
}
