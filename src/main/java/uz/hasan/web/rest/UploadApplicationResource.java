package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.hasan.service.UploadService;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

import java.net.URISyntaxException;

/**
 * REST controller for managing Receipt.
 */
@RestController
@RequestMapping("/api")
public class UploadApplicationResource {

    private final Logger log = LoggerFactory.getLogger(UploadApplicationResource.class);

    private final UploadService uploadService;

    public UploadApplicationResource(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * POST  /receipts : Create a new receipt.
     *
     * @param file {@link MultipartFile} object
     * @return the ResponseEntity with status 201 (Created) and with body the new receiptDTO, or with status 400 (Bad Request) if the receipt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/uploads/credit", headers = ("content-type=multipart/*"))
    @Timed
    public ResponseEntity<ReceiptProductEntriesDTO> uploadCreditReceipt(@RequestPart("file") MultipartFile file) throws URISyntaxException {
        log.debug("REST request to save Receipt : {}", file);
        ReceiptProductEntriesDTO receiptProductEntriesDTO = uploadService.createDisplacementApplication(file);
        return new ResponseEntity<>(receiptProductEntriesDTO, HttpStatus.OK);
    }

}
