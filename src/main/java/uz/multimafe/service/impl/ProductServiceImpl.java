package uz.multimafe.service.impl;

import uz.multimafe.service.ProductService;
import uz.multimafe.domain.Product;
import uz.multimafe.repository.ProductRepository;
import uz.multimafe.service.dto.ProductDTO;
import uz.multimafe.service.mapper.ProductMapper;
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
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.productDTOToProduct(productDTO);
        product = productRepository.save(product);
        ProductDTO result = productMapper.productToProductDTO(product);
        return result;
    }

    /**
     *  Get all the products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        Page<Product> result = productRepository.findAll(pageable);
        return result.map(product -> productMapper.productToProductDTO(product));
    }

    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDTO findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        return productDTO;
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }
}
