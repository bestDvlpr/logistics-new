package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.ReceiptService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.ReceiptDTO;
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
 * REST controller for managing Receipt.
 */
@RestController
@RequestMapping("/api")
public class ReceiptResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptResource.class);

    private static final String ENTITY_NAME = "receipt";

    private final ReceiptService receiptService;

    public ReceiptResource(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * POST  /receipts : Create a new receipt.
     *
     * @param receiptDTO the receiptDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receiptDTO, or with status 400 (Bad Request) if the receipt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/receipts")
    @Timed
    public ResponseEntity<ReceiptDTO> createReceipt(@Valid @RequestBody ReceiptDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to save Receipt : {}", receiptDTO);
        if (receiptDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new receipt cannot already have an ID")).body(null);
        }
        ReceiptDTO result = receiptService.save(receiptDTO);
        return ResponseEntity.created(new URI("/api/receipts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receipts : Updates an existing receipt.
     *
     * @param receiptDTO the receiptDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receiptDTO,
     * or with status 400 (Bad Request) if the receiptDTO is not valid,
     * or with status 500 (Internal Server Error) if the receiptDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/receipts")
    @Timed
    public ResponseEntity<ReceiptDTO> updateReceipt(@Valid @RequestBody ReceiptDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to update Receipt : {}", receiptDTO);
        if (receiptDTO.getId() == null) {
            return createReceipt(receiptDTO);
        }
        ReceiptDTO result = receiptService.save(receiptDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receiptDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receipts : get all the receipts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts")
    @Timed
    public ResponseEntity<List<ReceiptDTO>> getAllReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Receipts");
        Page<ReceiptDTO> page = receiptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/:id : get the "id" receipt.
     *
     * @param id the id of the receiptDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receiptDTO, or with status 404 (Not Found)
     */
    @GetMapping("/receipts/{id}")
    @Timed
    public ResponseEntity<ReceiptDTO> getReceipt(@PathVariable Long id) {
        log.debug("REST request to get Receipt : {}", id);
        ReceiptDTO receiptDTO = receiptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(receiptDTO));
    }

    /**
     * DELETE  /receipts/:id : delete the "id" receipt.
     *
     * @param id the id of the receiptDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/receipts/{id}")
    @Timed
    public ResponseEntity<Void> deleteReceipt(@PathVariable Long id) {
        log.debug("REST request to delete Receipt : {}", id);
        receiptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
