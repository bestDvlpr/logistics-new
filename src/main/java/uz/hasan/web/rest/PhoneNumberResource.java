package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.domain.PhoneNumber;
import uz.hasan.service.PhoneNumberService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
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
 * REST controller for managing PhoneNumber.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberResource.class);

    private static final String ENTITY_NAME = "phoneNumber";
        
    private final PhoneNumberService phoneNumberService;

    public PhoneNumberResource(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    /**
     * POST  /phone-numbers : Create a new phoneNumber.
     *
     * @param phoneNumber the phoneNumber to create
     * @return the ResponseEntity with status 201 (Created) and with body the new phoneNumber, or with status 400 (Bad Request) if the phoneNumber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/phone-numbers")
    @Timed
    public ResponseEntity<PhoneNumber> createPhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber) throws URISyntaxException {
        log.debug("REST request to save PhoneNumber : {}", phoneNumber);
        if (phoneNumber.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new phoneNumber cannot already have an ID")).body(null);
        }
        PhoneNumber result = phoneNumberService.save(phoneNumber);
        return ResponseEntity.created(new URI("/api/phone-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /phone-numbers : Updates an existing phoneNumber.
     *
     * @param phoneNumber the phoneNumber to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated phoneNumber,
     * or with status 400 (Bad Request) if the phoneNumber is not valid,
     * or with status 500 (Internal Server Error) if the phoneNumber couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/phone-numbers")
    @Timed
    public ResponseEntity<PhoneNumber> updatePhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber) throws URISyntaxException {
        log.debug("REST request to update PhoneNumber : {}", phoneNumber);
        if (phoneNumber.getId() == null) {
            return createPhoneNumber(phoneNumber);
        }
        PhoneNumber result = phoneNumberService.save(phoneNumber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, phoneNumber.getId().toString()))
            .body(result);
    }

    /**
     * GET  /phone-numbers : get all the phoneNumbers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of phoneNumbers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/phone-numbers")
    @Timed
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PhoneNumbers");
        Page<PhoneNumber> page = phoneNumberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/phone-numbers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /phone-numbers/:id : get the "id" phoneNumber.
     *
     * @param id the id of the phoneNumber to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the phoneNumber, or with status 404 (Not Found)
     */
    @GetMapping("/phone-numbers/{id}")
    @Timed
    public ResponseEntity<PhoneNumber> getPhoneNumber(@PathVariable Long id) {
        log.debug("REST request to get PhoneNumber : {}", id);
        PhoneNumber phoneNumber = phoneNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(phoneNumber));
    }

    /**
     * DELETE  /phone-numbers/:id : delete the "id" phoneNumber.
     *
     * @param id the id of the phoneNumber to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/phone-numbers/{id}")
    @Timed
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumber : {}", id);
        phoneNumberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
