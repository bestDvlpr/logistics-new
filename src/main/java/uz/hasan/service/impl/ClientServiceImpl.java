package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Client;
import uz.hasan.domain.PhoneNumber;
import uz.hasan.repository.ClientRepository;
import uz.hasan.repository.PhoneNumberRepository;
import uz.hasan.service.ClientService;
import uz.hasan.service.dto.ClientAndAddressesDTO;
import uz.hasan.service.dto.ClientDTO;
import uz.hasan.service.mapper.ClientAndAddressesMapper;
import uz.hasan.service.mapper.ClientMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final ClientMapper clientMapper;

    private final ClientAndAddressesMapper clientAndAddressesMapper;

    public ClientServiceImpl(ClientRepository clientRepository, PhoneNumberRepository phoneNumberRepository, ClientMapper clientMapper, ClientAndAddressesMapper clientAndAddressesMapper) {
        this.clientRepository = clientRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.clientMapper = clientMapper;
        this.clientAndAddressesMapper = clientAndAddressesMapper;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientAndAddressesDTO save(ClientAndAddressesDTO clientDTO) {
        if (clientDTO==null){
            return null;
        }
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientAndAddressesMapper.clientAndAddressesDTOToClient(clientDTO);
        client = clientRepository.save(client);
        List<PhoneNumber> toSave = new ArrayList<>();
        for (PhoneNumber phone : clientDTO.getNumbers()) {
            phone.setClient(client);
            toSave.add(phone);
        }
        phoneNumberRepository.save(toSave);
        ClientAndAddressesDTO result = clientAndAddressesMapper.clientToClientAndAddressesDTO(client);
        return result;
    }

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientAndAddressesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        Page<Client> result = clientRepository.findAll(pageable);
        return result.map(clientAndAddressesMapper::clientToClientAndAddressesDTO);
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClientAndAddressesDTO findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        Client client = clientRepository.findOne(id);
        ClientAndAddressesDTO clientDTO = clientAndAddressesMapper.clientToClientAndAddressesDTO(client);
        return clientDTO;
    }

    /**
     * Delete the  client by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.delete(id);
    }

    /**
     * Get one client by phoneNumber.
     *
     * @param phoneNumber the phoneNumber of the entity
     * @return the entity
     */
    @Override
    public ClientAndAddressesDTO findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }
        PhoneNumber number = phoneNumberRepository.findByNumber(phoneNumber);
        if (number == null) {
            return null;
        }
        return clientAndAddressesMapper.clientToClientAndAddressesDTO(number.getClient());
    }
}
