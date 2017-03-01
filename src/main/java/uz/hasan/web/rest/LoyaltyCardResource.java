package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.LoyaltyCardService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.LoyaltyCardDTO;
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
 * REST controller for managing LoyaltyCard.
 */
@RestController
@RequestMapping("/api")
public class LoyaltyCardResource {

    private final Logger log = LoggerFactory.getLogger(LoyaltyCardResource.class);

    private static final String ENTITY_NAME = "loyaltyCard";

    private final LoyaltyCardService loyaltyCardService;

    public LoyaltyCardResource(LoyaltyCardService loyaltyCardService) {
        this.loyaltyCardService = loyaltyCardService;
    }

    /**
     * POST  /loyalty-cards : Create a new loyaltyCard.
     *
     * @param loyaltyCardDTO the loyaltyCardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loyaltyCardDTO, or with status 400 (Bad Request) if the loyaltyCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loyalty-cards")
    @Timed
    public ResponseEntity<LoyaltyCardDTO> createLoyaltyCard(@Valid @RequestBody LoyaltyCardDTO loyaltyCardDTO) throws URISyntaxException {
        log.debug("REST request to save LoyaltyCard : {}", loyaltyCardDTO);
        if (loyaltyCardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new loyaltyCard cannot already have an ID")).body(null);
        }
        LoyaltyCardDTO result = loyaltyCardService.save(loyaltyCardDTO);
        return ResponseEntity.created(new URI("/api/loyalty-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loyalty-cards : Updates an existing loyaltyCard.
     *
     * @param loyaltyCardDTO the loyaltyCardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loyaltyCardDTO,
     * or with status 400 (Bad Request) if the loyaltyCardDTO is not valid,
     * or with status 500 (Internal Server Error) if the loyaltyCardDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loyalty-cards")
    @Timed
    public ResponseEntity<LoyaltyCardDTO> updateLoyaltyCard(@Valid @RequestBody LoyaltyCardDTO loyaltyCardDTO) throws URISyntaxException {
        log.debug("REST request to update LoyaltyCard : {}", loyaltyCardDTO);
        if (loyaltyCardDTO.getId() == null) {
            return createLoyaltyCard(loyaltyCardDTO);
        }
        LoyaltyCardDTO result = loyaltyCardService.save(loyaltyCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loyaltyCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loyalty-cards : get all the loyaltyCards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loyaltyCards in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/loyalty-cards")
    @Timed
    public ResponseEntity<List<LoyaltyCardDTO>> getAllLoyaltyCards(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LoyaltyCards");
        Page<LoyaltyCardDTO> page = loyaltyCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loyalty-cards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /loyalty-cards/:id : get the "id" loyaltyCard.
     *
     * @param id the id of the loyaltyCardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loyaltyCardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/loyalty-cards/{id}")
    @Timed
    public ResponseEntity<LoyaltyCardDTO> getLoyaltyCard(@PathVariable Long id) {
        log.debug("REST request to get LoyaltyCard : {}", id);
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loyaltyCardDTO));
    }

    /**
     * DELETE  /loyalty-cards/:id : delete the "id" loyaltyCard.
     *
     * @param id the id of the loyaltyCardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loyalty-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoyaltyCard(@PathVariable Long id) {
        log.debug("REST request to delete LoyaltyCard : {}", id);
        loyaltyCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
