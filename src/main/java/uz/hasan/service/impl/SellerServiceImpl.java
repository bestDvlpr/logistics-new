package uz.hasan.service.impl;

import uz.hasan.service.SellerService;
import uz.hasan.domain.Seller;
import uz.hasan.repository.SellerRepository;
import uz.hasan.service.dto.SellerDTO;
import uz.hasan.service.mapper.SellerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Seller.
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService{

    private final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
    }

    /**
     * Save a seller.
     *
     * @param sellerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SellerDTO save(SellerDTO sellerDTO) {
        log.debug("Request to save Seller : {}", sellerDTO);
        Seller seller = sellerMapper.sellerDTOToSeller(sellerDTO);
        seller = sellerRepository.save(seller);
        SellerDTO result = sellerMapper.sellerToSellerDTO(seller);
        return result;
    }

    /**
     *  Get all the sellers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SellerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sellers");
        Page<Seller> result = sellerRepository.findAll(pageable);
        return result.map(seller -> sellerMapper.sellerToSellerDTO(seller));
    }

    /**
     *  Get one seller by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SellerDTO findOne(Long id) {
        log.debug("Request to get Seller : {}", id);
        Seller seller = sellerRepository.findOne(id);
        SellerDTO sellerDTO = sellerMapper.sellerToSellerDTO(seller);
        return sellerDTO;
    }

    /**
     *  Delete the  seller by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seller : {}", id);
        sellerRepository.delete(id);
    }
}
