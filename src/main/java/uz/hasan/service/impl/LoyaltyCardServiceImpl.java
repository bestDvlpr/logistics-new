package uz.hasan.service.impl;

import uz.hasan.service.LoyaltyCardService;
import uz.hasan.domain.LoyaltyCard;
import uz.hasan.repository.LoyaltyCardRepository;
import uz.hasan.service.dto.LoyaltyCardDTO;
import uz.hasan.service.mapper.LoyaltyCardMapper;
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
 * Service Implementation for managing LoyaltyCard.
 */
@Service
@Transactional
public class LoyaltyCardServiceImpl implements LoyaltyCardService{

    private final Logger log = LoggerFactory.getLogger(LoyaltyCardServiceImpl.class);
    
    private final LoyaltyCardRepository loyaltyCardRepository;

    private final LoyaltyCardMapper loyaltyCardMapper;

    public LoyaltyCardServiceImpl(LoyaltyCardRepository loyaltyCardRepository, LoyaltyCardMapper loyaltyCardMapper) {
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.loyaltyCardMapper = loyaltyCardMapper;
    }

    /**
     * Save a loyaltyCard.
     *
     * @param loyaltyCardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LoyaltyCardDTO save(LoyaltyCardDTO loyaltyCardDTO) {
        log.debug("Request to save LoyaltyCard : {}", loyaltyCardDTO);
        LoyaltyCard loyaltyCard = loyaltyCardMapper.loyaltyCardDTOToLoyaltyCard(loyaltyCardDTO);
        loyaltyCard = loyaltyCardRepository.save(loyaltyCard);
        LoyaltyCardDTO result = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);
        return result;
    }

    /**
     *  Get all the loyaltyCards.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoyaltyCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoyaltyCards");
        Page<LoyaltyCard> result = loyaltyCardRepository.findAll(pageable);
        return result.map(loyaltyCard -> loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard));
    }

    /**
     *  Get one loyaltyCard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LoyaltyCardDTO findOne(Long id) {
        log.debug("Request to get LoyaltyCard : {}", id);
        LoyaltyCard loyaltyCard = loyaltyCardRepository.findOne(id);
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(loyaltyCard);
        return loyaltyCardDTO;
    }

    /**
     *  Delete the  loyaltyCard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoyaltyCard : {}", id);
        loyaltyCardRepository.delete(id);
    }
}
