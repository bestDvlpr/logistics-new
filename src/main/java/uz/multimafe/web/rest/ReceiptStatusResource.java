package uz.multimafe.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.multimafe.service.ReceiptStatusService;
import uz.multimafe.web.rest.util.HeaderUtil;
import uz.multimafe.web.rest.util.PaginationUtil;
import uz.multimafe.service.dto.ReceiptStatusDTO;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ReceiptStatus.
 */
@RestController
@RequestMapping("/api")
public class ReceiptStatusResource {

    private final Logger log = LoggerFactory.getLogger(ReceiptStatusResource.class);

    private static final String ENTITY_NAME = "receiptStatus";
        
    private final ReceiptStatusService receiptStatusService;

    public ReceiptStatusResource(ReceiptStatusService receiptStatusService) {
        this.receiptStatusService = receiptStatusService;
    }

    /**
     * POST  /receipt-statuses : Create a new receiptStatus.
     *
     * @param receiptStatusDTO the receiptStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new receiptStatusDTO, or with status 400 (Bad Request) if the receiptStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/receipt-statuses")
    @Timed
    public ResponseEntity<ReceiptStatusDTO> createReceiptStatus(@Valid @RequestBody ReceiptStatusDTO receiptStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ReceiptStatus : {}", receiptStatusDTO);
        if (receiptStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new receiptStatus cannot already have an ID")).body(null);
        }
        ReceiptStatusDTO result = receiptStatusService.save(receiptStatusDTO);
        return ResponseEntity.created(new URI("/api/receipt-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /receipt-statuses : Updates an existing receiptStatus.
     *
     * @param receiptStatusDTO the receiptStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated receiptStatusDTO,
     * or with status 400 (Bad Request) if the receiptStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the receiptStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/receipt-statuses")
    @Timed
    public ResponseEntity<ReceiptStatusDTO> updateReceiptStatus(@Valid @RequestBody ReceiptStatusDTO receiptStatusDTO) throws URISyntaxException {
        log.debug("REST request to update ReceiptStatus : {}", receiptStatusDTO);
        if (receiptStatusDTO.getId() == null) {
            return createReceiptStatus(receiptStatusDTO);
        }
        ReceiptStatusDTO result = receiptStatusService.save(receiptStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, receiptStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receipt-statuses : get all the receiptStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receiptStatuses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipt-statuses")
    @Timed
    public ResponseEntity<List<ReceiptStatusDTO>> getAllReceiptStatuses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ReceiptStatuses");
        Page<ReceiptStatusDTO> page = receiptStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipt-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipt-statuses/:id : get the "id" receiptStatus.
     *
     * @param id the id of the receiptStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receiptStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/receipt-statuses/{id}")
    @Timed
    public ResponseEntity<ReceiptStatusDTO> getReceiptStatus(@PathVariable Long id) {
        log.debug("REST request to get ReceiptStatus : {}", id);
        ReceiptStatusDTO receiptStatusDTO = receiptStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(receiptStatusDTO));
    }

    /**
     * DELETE  /receipt-statuses/:id : delete the "id" receiptStatus.
     *
     * @param id the id of the receiptStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/receipt-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteReceiptStatus(@PathVariable Long id) {
        log.debug("REST request to delete ReceiptStatus : {}", id);
        receiptStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
