package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.PayTypeService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.PayTypeDTO;
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
 * REST controller for managing PayType.
 */
@RestController
@RequestMapping("/api")
public class PayTypeResource {

    private final Logger log = LoggerFactory.getLogger(PayTypeResource.class);

    private static final String ENTITY_NAME = "payType";

    private final PayTypeService payTypeService;

    public PayTypeResource(PayTypeService payTypeService) {
        this.payTypeService = payTypeService;
    }

    /**
     * POST  /pay-types : Create a new payType.
     *
     * @param payTypeDTO the payTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payTypeDTO, or with status 400 (Bad Request) if the payType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pay-types")
    @Timed
    public ResponseEntity<PayTypeDTO> createPayType(@Valid @RequestBody PayTypeDTO payTypeDTO) throws URISyntaxException {
        log.debug("REST request to save PayType : {}", payTypeDTO);
        if (payTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new payType cannot already have an ID")).body(null);
        }
        PayTypeDTO result = payTypeService.save(payTypeDTO);
        return ResponseEntity.created(new URI("/api/pay-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pay-types : Updates an existing payType.
     *
     * @param payTypeDTO the payTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payTypeDTO,
     * or with status 400 (Bad Request) if the payTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the payTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pay-types")
    @Timed
    public ResponseEntity<PayTypeDTO> updatePayType(@Valid @RequestBody PayTypeDTO payTypeDTO) throws URISyntaxException {
        log.debug("REST request to update PayType : {}", payTypeDTO);
        if (payTypeDTO.getId() == null) {
            return createPayType(payTypeDTO);
        }
        PayTypeDTO result = payTypeService.save(payTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pay-types : get all the payTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of payTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pay-types")
    @Timed
    public ResponseEntity<List<PayTypeDTO>> getAllPayTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PayTypes");
        Page<PayTypeDTO> page = payTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pay-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pay-types/:id : get the "id" payType.
     *
     * @param id the id of the payTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pay-types/{id}")
    @Timed
    public ResponseEntity<PayTypeDTO> getPayType(@PathVariable Long id) {
        log.debug("REST request to get PayType : {}", id);
        PayTypeDTO payTypeDTO = payTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payTypeDTO));
    }

    /**
     * DELETE  /pay-types/:id : delete the "id" payType.
     *
     * @param id the id of the payTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pay-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePayType(@PathVariable Long id) {
        log.debug("REST request to delete PayType : {}", id);
        payTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
