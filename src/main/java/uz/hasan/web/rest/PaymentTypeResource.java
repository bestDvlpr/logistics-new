package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.PaymentTypeService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.PaymentTypeDTO;
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
 * REST controller for managing PaymentType.
 */
@RestController
@RequestMapping("/api")
public class PaymentTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeResource.class);

    private static final String ENTITY_NAME = "paymentType";

    private final PaymentTypeService paymentTypeService;

    public PaymentTypeResource(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    /**
     * POST  /payment-types : Create a new paymentType.
     *
     * @param paymentTypeDTO the paymentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentTypeDTO, or with status 400 (Bad Request) if the paymentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-types")
    @Timed
    public ResponseEntity<PaymentTypeDTO> createPaymentType(@Valid @RequestBody PaymentTypeDTO paymentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentType : {}", paymentTypeDTO);
        if (paymentTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paymentType cannot already have an ID")).body(null);
        }
        PaymentTypeDTO result = paymentTypeService.save(paymentTypeDTO);
        return ResponseEntity.created(new URI("/api/payment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-types : Updates an existing paymentType.
     *
     * @param paymentTypeDTO the paymentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentTypeDTO,
     * or with status 400 (Bad Request) if the paymentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-types")
    @Timed
    public ResponseEntity<PaymentTypeDTO> updatePaymentType(@Valid @RequestBody PaymentTypeDTO paymentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentType : {}", paymentTypeDTO);
        if (paymentTypeDTO.getId() == null) {
            return createPaymentType(paymentTypeDTO);
        }
        PaymentTypeDTO result = paymentTypeService.save(paymentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-types : get all the paymentTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paymentTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/payment-types")
    @Timed
    public ResponseEntity<List<PaymentTypeDTO>> getAllPaymentTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PaymentTypes");
        Page<PaymentTypeDTO> page = paymentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment-types/:id : get the "id" paymentType.
     *
     * @param id the id of the paymentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-types/{id}")
    @Timed
    public ResponseEntity<PaymentTypeDTO> getPaymentType(@PathVariable Long id) {
        log.debug("REST request to get PaymentType : {}", id);
        PaymentTypeDTO paymentTypeDTO = paymentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentTypeDTO));
    }

    /**
     * DELETE  /payment-types/:id : delete the "id" paymentType.
     *
     * @param id the id of the paymentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentType : {}", id);
        paymentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
