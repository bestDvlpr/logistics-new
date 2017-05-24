package uz.hasan.service.impl;

import uz.hasan.domain.Company;
import uz.hasan.repository.CompanyRepository;
import uz.hasan.service.AddressService;
import uz.hasan.domain.Address;
import uz.hasan.repository.AddressRepository;
import uz.hasan.service.dto.AddressDTO;
import uz.hasan.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Address.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    private final CompanyRepository companyRepository;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper, CompanyRepository companyRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.companyRepository = companyRepository;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        address = addressRepository.save(address);
        if (!address.getCompanies().isEmpty()) {
            Company company = companyRepository.findOne(address.getCompanies().iterator().next().getId());
            company.setAddress(address);
            companyRepository.save(company);
        }
        AddressDTO result = addressMapper.addressToAddressDTO(address);
        return result;
    }

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        Page<Address> result = addressRepository.findAll(pageable);
        return result.map(address -> addressMapper.addressToAddressDTO(address));
    }

    /**
     * Get one address by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AddressDTO findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        Address address = addressRepository.findOne(id);
        AddressDTO addressDTO = addressMapper.addressToAddressDTO(address);
        return addressDTO;
    }

    /**
     * Delete the  address by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
    }

    @Override
    public List<AddressDTO> findByClientId(Long clientId) {
        if (clientId == null) {
            return Collections.emptyList();
        }

        Optional<List<Address>> addresses = addressRepository.findByClientId(clientId);

        if (addresses.isPresent()) {
            return addressMapper.addressesToAddressDTOs(addresses.get());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Get all the sellers.
     *
     * @return the list of entities
     */
    @Override
    public List<AddressDTO> findAll() {
        List<Address> allAddresses = addressRepository.findAll();
        return addressMapper.addressesToAddressDTOs(allAddresses);
    }
}
