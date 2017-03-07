package uz.hasan.service.impl;

import uz.hasan.service.ProductEntryService;
import uz.hasan.domain.ProductEntry;
import uz.hasan.repository.ProductEntryRepository;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.mapper.ProductEntryMapper;
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
 * Service Implementation for managing ProductEntry.
 */
@Service
@Transactional
public class ProductEntryServiceImpl implements ProductEntryService {

    private final Logger log = LoggerFactory.getLogger(ProductEntryServiceImpl.class);

    private final ProductEntryRepository productEntryRepository;

    private final ProductEntryMapper productEntryMapper;

    public ProductEntryServiceImpl(ProductEntryRepository productEntryRepository, ProductEntryMapper productEntryMapper) {
        this.productEntryRepository = productEntryRepository;
        this.productEntryMapper = productEntryMapper;
    }

    /**
     * Save a productEntry.
     *
     * @param productEntryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductEntryDTO save(ProductEntryDTO productEntryDTO) {
        log.debug("Request to save ProductEntry : {}", productEntryDTO);
        ProductEntry productEntry = productEntryMapper.productEntryDTOToProductEntry(productEntryDTO);
        productEntry = productEntryRepository.save(productEntry);
        ProductEntryDTO result = productEntryMapper.productEntryToProductEntryDTO(productEntry);
        return result;
    }

    /**
     * Get all the productEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductEntries");
        Page<ProductEntry> result = productEntryRepository.findAll(pageable);
        return result.map(productEntry -> productEntryMapper.productEntryToProductEntryDTO(productEntry));
    }

    /**
     * Get one productEntry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductEntryDTO findOne(Long id) {
        log.debug("Request to get ProductEntry : {}", id);
        ProductEntry productEntry = productEntryRepository.findOne(id);
        ProductEntryDTO productEntryDTO = productEntryMapper.productEntryToProductEntryDTO(productEntry);
        return productEntryDTO;
    }

    /**
     * Delete the  productEntry by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductEntry : {}", id);
        productEntryRepository.delete(id);
    }

    /**
     * Get all the productEntries by receipt ID.
     *
     * @param receiptId the receipt ID
     * @return the list of entities
     */
    @Override
    public List<ProductEntryDTO> findAllByReceiptId(Long receiptId) {
        log.debug("Request to get all ProductEntries by receiptId: {}", receiptId);
        List<ProductEntry> result = productEntryRepository.findByReceiptId(receiptId);
        return productEntryMapper.productEntriesToProductEntryDTOs(result);
    }
}
