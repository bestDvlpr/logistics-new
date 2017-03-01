package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.PayMasterService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.PayMasterDTO;
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
 * REST controller for managing PayMaster.
 */
@RestController
@RequestMapping("/api")
public class PayMasterResource {

    private final Logger log = LoggerFactory.getLogger(PayMasterResource.class);

    private static final String ENTITY_NAME = "payMaster";

    private final PayMasterService payMasterService;

    public PayMasterResource(PayMasterService payMasterService) {
        this.payMasterService = payMasterService;
    }

    /**
     * POST  /pay-masters : Create a new payMaster.
     *
     * @param payMasterDTO the payMasterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payMasterDTO, or with status 400 (Bad Request) if the payMaster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pay-masters")
    @Timed
    public ResponseEntity<PayMasterDTO> createPayMaster(@Valid @RequestBody PayMasterDTO payMasterDTO) throws URISyntaxException {
        log.debug("REST request to save PayMaster : {}", payMasterDTO);
        if (payMasterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new payMaster cannot already have an ID")).body(null);
        }
        PayMasterDTO result = payMasterService.save(payMasterDTO);
        return ResponseEntity.created(new URI("/api/pay-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pay-masters : Updates an existing payMaster.
     *
     * @param payMasterDTO the payMasterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payMasterDTO,
     * or with status 400 (Bad Request) if the payMasterDTO is not valid,
     * or with status 500 (Internal Server Error) if the payMasterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pay-masters")
    @Timed
    public ResponseEntity<PayMasterDTO> updatePayMaster(@Valid @RequestBody PayMasterDTO payMasterDTO) throws URISyntaxException {
        log.debug("REST request to update PayMaster : {}", payMasterDTO);
        if (payMasterDTO.getId() == null) {
            return createPayMaster(payMasterDTO);
        }
        PayMasterDTO result = payMasterService.save(payMasterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pay-masters : get all the payMasters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of payMasters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pay-masters")
    @Timed
    public ResponseEntity<List<PayMasterDTO>> getAllPayMasters(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PayMasters");
        Page<PayMasterDTO> page = payMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pay-masters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pay-masters/:id : get the "id" payMaster.
     *
     * @param id the id of the payMasterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payMasterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pay-masters/{id}")
    @Timed
    public ResponseEntity<PayMasterDTO> getPayMaster(@PathVariable Long id) {
        log.debug("REST request to get PayMaster : {}", id);
        PayMasterDTO payMasterDTO = payMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payMasterDTO));
    }

    /**
     * DELETE  /pay-masters/:id : delete the "id" payMaster.
     *
     * @param id the id of the payMasterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pay-masters/{id}")
    @Timed
    public ResponseEntity<Void> deletePayMaster(@PathVariable Long id) {
        log.debug("REST request to delete PayMaster : {}", id);
        payMasterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
