package uz.hasan.service;

import uz.hasan.service.dto.ClientAndAddressesDTO;
import uz.hasan.service.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Client.
 */
public interface ClientService {

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save
     * @return the persisted entity
     */
    ClientDTO save(ClientDTO clientDTO);

    /**
     *  Get all the clients.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" client.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientDTO findOne(Long id);

    /**
     *  Delete the "id" client.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get the "phoneNumber" client.
     *
     *  @param phoneNumber the phone number of the entity
     *  @return the entity
     */
    ClientAndAddressesDTO findByPhoneNumber(String phoneNumber);
}
