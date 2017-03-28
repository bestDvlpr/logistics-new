package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.ProductEntryService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.ProductEntryDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProductEntry.
 */
@RestController
@RequestMapping("/api")
public class ProductEntryResource {

    private final Logger log = LoggerFactory.getLogger(ProductEntryResource.class);

    private static final String ENTITY_NAME = "productEntry";

    private final ProductEntryService productEntryService;

    public ProductEntryResource(ProductEntryService productEntryService) {
        this.productEntryService = productEntryService;
    }

    /**
     * POST  /product-entries : Create a new productEntry.
     *
     * @param productEntryDTO the productEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productEntryDTO, or with status 400 (Bad Request) if the productEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-entries")
    @Timed
    public ResponseEntity<ProductEntryDTO> createProductEntry(@Valid @RequestBody ProductEntryDTO productEntryDTO) throws URISyntaxException {
        log.debug("REST request to save ProductEntry : {}", productEntryDTO);
        if (productEntryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new productEntry cannot already have an ID")).body(null);
        }
        ProductEntryDTO result = productEntryService.save(productEntryDTO);
        return ResponseEntity.created(new URI("/api/product-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /product-entries/delivery : Deliver productEntries.
     *
     * @param productEntryDTOs the productEntryDTOs to delivery
     * @return the ResponseEntity with status 201 (Created) and with body the new productEntryDTO, or with status 400 (Bad Request) if the productEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-entries/delivery")
    @Timed
    public ResponseEntity<List<ProductEntryDTO>> deliverProductEntries(@Valid @RequestBody List<ProductEntryDTO> productEntryDTOs) throws URISyntaxException {
        log.debug("REST request to save ProductEntry : {}", productEntryDTOs);
        if (productEntryDTOs.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.deliveryFailureAlert("listEmpty", "A new productEntries list cannot be empty")).body(null);
        }
        List<ProductEntryDTO> result = productEntryService.deliver(productEntryDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST  /product-entries/delivered : Deliver productEntries.
     *
     * @param productEntryDTOs the productEntryDTOs to delivery
     * @return the ResponseEntity with status 201 (Created) and with body the new productEntryDTO, or with status 400 (Bad Request) if the productEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-entries/delivered")
    @Timed
    public ResponseEntity<List<ProductEntryDTO>> productEntriesDelivered(@Valid @RequestBody List<ProductEntryDTO> productEntryDTOs) throws URISyntaxException {
        log.debug("REST request to save ProductEntry : {}", productEntryDTOs);
        if (productEntryDTOs.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.deliveryFailureAlert("listEmpty", "A new productEntries list cannot be empty")).body(null);
        }
        List<ProductEntryDTO> result = productEntryService.delivered(productEntryDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * PUT  /product-entries : Updates an existing productEntry.
     *
     * @param productEntryDTO the productEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productEntryDTO,
     * or with status 400 (Bad Request) if the productEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the productEntryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-entries")
    @Timed
    public ResponseEntity<ProductEntryDTO> updateProductEntry(@Valid @RequestBody ProductEntryDTO productEntryDTO) throws URISyntaxException {
        log.debug("REST request to update ProductEntry : {}", productEntryDTO);
        if (productEntryDTO.getId() == null) {
            return createProductEntry(productEntryDTO);
        }
        ProductEntryDTO result = productEntryService.save(productEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-entries : get all the productEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-entries")
    @Timed
    public ResponseEntity<List<ProductEntryDTO>> getAllProductEntries(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProductEntries");
        Page<ProductEntryDTO> page = productEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /product-entries/:receiptId : get all the productEntries of receipt.
     *
     * @param receiptId the receipt ID
     * @return the ResponseEntity with status 200 (OK) and the list of productEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-entries/receipt-entries/{receiptId}")
    @Timed
    public ResponseEntity<List<ProductEntryDTO>> getAllReceiptProductEntries(
        @PathVariable Long receiptId
    ) throws URISyntaxException {
        log.debug("REST request to get a productEntryDTOS of ProductEntries by receiptId: {}", receiptId);
        List<ProductEntryDTO> productEntryDTOS = productEntryService.findAllByReceiptId(receiptId);
        return new ResponseEntity<>(productEntryDTOS, HttpStatus.OK);
    }

    /**
     * GET  /product-entries/car-entries/:carNumber : get the productEntries attached to car and with status NEW.
     *
     * @param carNumber the receipt ID
     * @return the ResponseEntity with status 200 (OK) and the list of productEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-entries/car-entries/{carNumber}")
    @Timed
    public ResponseEntity<List<ProductEntryDTO>> getAllNewProductEntriesByAttachedCar(
        @PathVariable String carNumber
    ) throws URISyntaxException {
        log.debug("REST request to get a productEntryDTOS of ProductEntries by car number: {}", carNumber);
        List<ProductEntryDTO> productEntryDTOS = productEntryService.findNewProductsByCarNumber(carNumber);
        return new ResponseEntity<>(productEntryDTOS, HttpStatus.OK);
    }

    /**
     * GET  /product-entries/car-entries/:carNumber : get all the productEntries attached to car in last 48 hours.
     *
     * @param carNumber the receipt ID
     * @return the ResponseEntity with status 200 (OK) and the list of productEntries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/product-entries/car-entries-all/{carNumber}")
    @Timed
    public ResponseEntity<List<ProductEntryDTO>> getAllProductEntriesByAttachedCar(
        @PathVariable String carNumber
    ) throws URISyntaxException {
        log.debug("REST request to get a productEntryDTOS of ProductEntries by car number: {}", carNumber);
        List<ProductEntryDTO> productEntryDTOS = productEntryService.findLastProductsByCarNumber(carNumber);
        return new ResponseEntity<>(productEntryDTOS, HttpStatus.OK);
    }

    /**
     * GET  /product-entries/:id : get the "id" productEntry.
     *
     * @param id the id of the productEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-entries/{id}")
    @Timed
    public ResponseEntity<ProductEntryDTO> getProductEntry(@PathVariable Long id) {
        log.debug("REST request to get ProductEntry : {}", id);
        ProductEntryDTO productEntryDTO = productEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productEntryDTO));
    }

    /**
     * DELETE  /product-entries/:id : delete the "id" productEntry.
     *
     * @param id the id of the productEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductEntry(@PathVariable Long id) {
        log.debug("REST request to delete ProductEntry : {}", id);
        productEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
