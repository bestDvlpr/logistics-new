package uz.hasan.service.impl;

import uz.hasan.service.PhoneNumberService;
import uz.hasan.domain.PhoneNumber;
import uz.hasan.repository.PhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing PhoneNumber.
 */
@Service
@Transactional
public class PhoneNumberServiceImpl implements PhoneNumberService{

    private final Logger log = LoggerFactory.getLogger(PhoneNumberServiceImpl.class);
    
    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    /**
     * Save a phoneNumber.
     *
     * @param phoneNumber the entity to save
     * @return the persisted entity
     */
    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        log.debug("Request to save PhoneNumber : {}", phoneNumber);
        PhoneNumber result = phoneNumberRepository.save(phoneNumber);
        return result;
    }

    /**
     *  Get all the phoneNumbers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhoneNumber> findAll(Pageable pageable) {
        log.debug("Request to get all PhoneNumbers");
        Page<PhoneNumber> result = phoneNumberRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one phoneNumber by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PhoneNumber findOne(Long id) {
        log.debug("Request to get PhoneNumber : {}", id);
        PhoneNumber phoneNumber = phoneNumberRepository.findOne(id);
        return phoneNumber;
    }

    /**
     *  Delete the  phoneNumber by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhoneNumber : {}", id);
        phoneNumberRepository.delete(id);
    }
}
