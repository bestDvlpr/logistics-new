package uz.hasan.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
     * @param file
     * @return the ResponseEntity with status 201 (Created) and with body the new receiptDTO, or with status 400 (Bad Request) if the receipt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/uploads/credit", headers = ("content-type=multipart/*"))
//    @Timed
//    @ResponseBody
    public ResponseEntity<ReceiptProductEntriesDTO> uploadCreditReceipt(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Receipt : {}", file);
        /*if (receiptDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new receipt cannot already have an ID")).body(null);
        }*/
//        ReceiptProductEntriesDTO result = uploadService.save(receiptDTO);
//        return ResponseEntity.created(new URI("/api/receipts/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
        return null;
    }

}
