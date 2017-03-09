package uz.hasan.service.impl;

import uz.hasan.domain.PhoneNumber;
import uz.hasan.repository.PhoneNumberRepository;
import uz.hasan.service.ClientService;
import uz.hasan.domain.Client;
import uz.hasan.repository.ClientRepository;
import uz.hasan.service.dto.ClientDTO;
import uz.hasan.service.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, PhoneNumberRepository phoneNumberRepository) {
        this.clientRepository = clientRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.clientDTOToClient(clientDTO);
        client = clientRepository.save(client);
        ClientDTO result = clientMapper.clientToClientDTO(client);
        return result;
    }

    /**
     *  Get all the clients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        Page<Client> result = clientRepository.findAll(pageable);
        return result.map(client -> clientMapper.clientToClientDTO(client));
    }

    /**
     *  Get one client by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClientDTO findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        Client client = clientRepository.findOne(id);
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);
        return clientDTO;
    }

    /**
     *  Delete the  client by id.
     *
     *  @param id the id of the entity
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
    public ClientDTO findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }
        PhoneNumber number = phoneNumberRepository.findByNumber(phoneNumber);
        if (number == null) {
            return null;
        }
        return clientMapper.clientToClientDTO(number.getClient());
    }
}
