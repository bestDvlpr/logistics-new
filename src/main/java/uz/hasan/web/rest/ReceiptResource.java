package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.hasan.domain.enumeration.DocType;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UploadService;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;

import javax.servlet.http.HttpServletResponse;
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

    private final UploadService uploadService;

    public ReceiptResource(ReceiptService receiptService, UploadService uploadService) {
        this.receiptService = receiptService;
        this.uploadService = uploadService;
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
    public ResponseEntity<ReceiptProductEntriesDTO> createReceipt(@Valid @RequestBody ReceiptProductEntriesDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to save Receipt : {}", receiptDTO);
        if (receiptDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new receipt cannot already have an ID")).body(null);
        }
        ReceiptProductEntriesDTO result = receiptService.save(receiptDTO);
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
    public ResponseEntity<ReceiptProductEntriesDTO> updateReceipt(@Valid @RequestBody ReceiptProductEntriesDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to update Receipt : {}", receiptDTO);
        if (receiptDTO.getId() == null) {
            return createReceipt(receiptDTO);
        }
        ReceiptProductEntriesDTO result = receiptService.save(receiptDTO);
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
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/accepted : get all accepted receipts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/accepted")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllAcceptedReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAllAccepted(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/accepted");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts : get all the receipts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/all")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAll()
        throws URISyntaxException {
        log.debug("REST request to get a all of Receipts");
        List<ReceiptProductEntriesDTO> all = receiptService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    /**
     * GET  /receipts/by-company-id : get all the receipts by company id.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/by-company-id")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllReceiptsByCompanyId(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAllReceiptsByCompanyId(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/by-company-id");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts : get all {@link uz.hasan.domain.enumeration.ReceiptStatus}.NEW the receipts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/new")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllNewReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of New Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAllNewReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/new");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts : get all {@link uz.hasan.domain.enumeration.ReceiptStatus}.NEW the receipts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/new/sales/retail")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllNewSalesRetailReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of New Sales Retail Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAllNewSalesRetailReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/new/sales/retail");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts : get all {@link uz.hasan.domain.enumeration.ReceiptStatus}.NEW the receipts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/new/sales/wholesale")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllNewSalesWholesaleReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of New Sales Wholesale Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAllNewSalesWholeSaleReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/new/sales/wholesale");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/applied : get all receipts of status {@link uz.hasan.domain.enumeration.ReceiptStatus}.APPLICATION_SENT .
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/applied")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllAppliedReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Applied Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findAppliedReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/applied");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/archived : get all receipts of status {@link uz.hasan.domain.enumeration.ReceiptStatus}.APPLICATION_SENT .
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/archived")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllArchivedReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Archived Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findArchivedReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/archived");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/displacement : get all receipts of type {@link uz.hasan.domain.enumeration.SalesType} DISPLACEMENT.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/displacement")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllDispalcementReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Displacement Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findDisplacementReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/displacement");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/upload/displacement : upload receipts {@link uz.hasan.domain.enumeration.SalesType} DISPLACEMENT.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @PostMapping(value = "/receipts/upload/displacement", headers = ("content-type=multipart/*"))
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> uploadDisplacementReceipt(@RequestPart("file") MultipartFile file)
        throws URISyntaxException {
        log.debug("REST request to get a page of Displacement Receipts");
        return null;
    }

    /**
     * GET  /receipts/upload/credit : upload receipts {@link uz.hasan.domain.enumeration.DocType} CREDIT.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @PostMapping(value = "/receipts/upload/{docType}/{companyId}", headers = ("content-type=multipart/*"))
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> uploadReceiptWithCompany(@PathVariable DocType docType, @PathVariable String companyId, @RequestPart("file") MultipartFile file)
        throws URISyntaxException {
        log.debug("REST request to upload application file: {}", file);
        ReceiptProductEntriesDTO receiptProductEntriesDTO = uploadService.createApplicationFromFile(file, docType, companyId);
        return new ResponseEntity<>(receiptProductEntriesDTO, HttpStatus.OK);
    }

    /**
     * GET  /receipts/upload/credit : upload receipts {@link uz.hasan.domain.enumeration.DocType} CREDIT.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @PostMapping(value = "/receipts/upload/{docType}", headers = ("content-type=multipart/*"))
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> uploadReceipt(@PathVariable DocType docType, @RequestPart("file") MultipartFile file)
        throws URISyntaxException {
        log.debug("REST request to upload application file: {}", file);
        ReceiptProductEntriesDTO receiptProductEntriesDTO = uploadService.createApplicationFromFile(file, docType, null);
        return new ResponseEntity<>(receiptProductEntriesDTO, HttpStatus.OK);
    }

    /**
     * GET  /receipts/credit : get all receipts of type {@link uz.hasan.domain.enumeration.SalesType} DISPLACEMENT.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/credit")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllCreditReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Displacement Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findCreditReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/credit");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/corporate : get all receipts of type {@link uz.hasan.domain.enumeration.SalesType} DISPLACEMENT
     * or {@link uz.hasan.domain.enumeration.SalesType} SALES.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/corporate")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getAllCorporateReceipts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Displacement or Sales Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findCorporateReceipts(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/credit");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/credit : get all receipts of type {@link uz.hasan.domain.enumeration.SalesType} DISPLACEMENT.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/credit/by-company-id")
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> getCreditReceiptsByCompanyId(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Displacement Receipts");
        Page<ReceiptProductEntriesDTO> page = receiptService.findCreditReceiptsByCompanyId(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/credit/by-company-id");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipts/count/new : get count of new receipts.
     *
     * @return the ResponseEntity with status 200 (OK) and the count of new receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/count/new")
    @Timed
    public ResponseEntity<Long> countNewReceipts() throws URISyntaxException {
        log.debug("REST request to get count of new Receipts");
        Long count = receiptService.countNewReceipts();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * GET  /receipts/count/applied : get count of applied receipts.
     *
     * @return the ResponseEntity with status 200 (OK) and the count of new receipts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/receipts/count/applied")
    @Timed
    public ResponseEntity<Long> countAppliedReceipts() throws URISyntaxException {
        log.debug("REST request to get count of applied Receipts");
        Long count = receiptService.countAppliedReceipts();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * GET  /receipts/:id : get the "id" receipt.
     *
     * @param id the id of the receiptDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the receiptDTO, or with status 404 (Not Found)
     */
    @GetMapping("/receipts/{id}")
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> getReceipt(@PathVariable Long id) {
        log.debug("REST request to get Receipt : {}", id);
        ReceiptProductEntriesDTO receiptDTO = receiptService.findOne(id);
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

    /**
     * POST  /receipts/order : Send receipt to logistic manager as order.
     *
     * @param receiptDTO the receiptDTO to send
     * @return the ResponseEntity with status 200 (OK) and with body the sent receiptDTO, or with status 400 (Bad Request) if the receipt has already sent
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/receipts/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ReceiptProductEntriesDTO>> sendOrder(@RequestBody ReceiptProductEntriesDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to send Receipt : {}", receiptDTO);
        /*if (receiptDTOs.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new receipt cannot already have an ID")).body(null);
        }*/
        List<ReceiptProductEntriesDTO> result = receiptService.sendOrder(receiptDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * POST  /receipts/attached : Attach receipt products to several or one car.
     *
     * @param receiptDTO the receiptDTO to send
     * @return the ResponseEntity with status 200 (OK) and with body the sent receiptDTO, or with status 400 (Bad Request) if the receipt has already sent
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/receipts/attached", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> attachOrderProducts(@RequestBody ReceiptProductEntriesDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to send Receipt : {}", receiptDTO);
        ReceiptProductEntriesDTO result = receiptService.attachOrder(receiptDTO);
        return ResponseEntity.created(new URI("/api/receipts/attached" + result.getId()))
            .headers(HeaderUtil.createEntitySentAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /receipts/delivered : Make receipt and its products as delivered.
     *
     * @param receiptDTO the receiptDTO to send
     * @return the ResponseEntity with status 200 (OK) and with body the sent receiptDTO, or with status 400 (Bad Request) if the receipt has already sent
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/receipts/delivered", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> delivered(@RequestBody ReceiptProductEntriesDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to send Receipt : {}", receiptDTO);
        ReceiptProductEntriesDTO result = receiptService.delivered(receiptDTO);
        return ResponseEntity.created(new URI("/api/receipts/delivered" + result.getId()))
            .headers(HeaderUtil.createEntitySentAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /receipts/taken-out : Make receipt and its products as taken out by client.
     *
     * @param receiptDTO the receiptDTO to send
     * @return the ResponseEntity with status 200 (OK) and with body the sent receiptDTO, or with status 400 (Bad Request) if the receipt has already sent
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/receipts/taken-out", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> takenOut(@RequestBody ReceiptProductEntriesDTO receiptDTO) throws URISyntaxException {
        log.debug("REST request to send Receipt : {}", receiptDTO);
        ReceiptProductEntriesDTO result = receiptService.takenOut(receiptDTO);
        return ResponseEntity.created(new URI("/api/receipts/taken-out" + result.getId()))
            .headers(HeaderUtil.createEntitySentAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /receipt/sent-receipt : Download receipt.
     *
     * @param receiptId the Receipt id to download
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping(value = "/receipts/sent-receipt/{receiptId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Timed
    @ResponseBody
    public void downloadReceipt(@PathVariable Long receiptId, HttpServletResponse response) throws URISyntaxException {
        log.debug("REST request to download Receipt : {}", receiptId);
        if (receiptId == null) {
            response.addHeader("listEmpty", "A Receipt id cannot be empty");
        }
        receiptService.download(receiptId, response);
    }

    /**
     * GET  /receipt/by-client-id : get client shopping history.
     *
     * @param clientId the Receipt id to download
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/receipts/by-client-id/{clientId}")
    @Timed
    @ResponseBody
    public ResponseEntity<List<ReceiptProductEntriesDTO>> downloadReceipt(@ApiParam Pageable pageable, @PathVariable Long clientId) throws URISyntaxException {
        log.debug("REST request to download Receipt : {}", clientId);
        if (clientId == null) {
            return null;
        }
        Page<ReceiptProductEntriesDTO> page = receiptService.findByClientId(pageable, clientId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/receipts/by-client-id");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /receipt/cancel-attached-car : remove attached car info from receipt.
     *
     * @param id the Receipt id to cancel car
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/receipts/cancel-attached-car/{id}")
    @Timed
    @ResponseBody
    public ResponseEntity<ReceiptProductEntriesDTO> downloadReceipt(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to cancel attached car of Receipt : {}", id);
        if (id == null) {
            return null;
        }
        ReceiptProductEntriesDTO receiptProductEntriesDTO = receiptService.cancelAttachedCar(id);
        return new ResponseEntity<>(receiptProductEntriesDTO, HttpStatus.OK);
    }

}
