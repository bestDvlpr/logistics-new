package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.ShopService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.ShopDTO;
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
 * REST controller for managing Shop.
 */
@RestController
@RequestMapping("/api")
public class ShopResource {

    private final Logger log = LoggerFactory.getLogger(ShopResource.class);

    private static final String ENTITY_NAME = "shop";
        
    private final ShopService shopService;

    public ShopResource(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * POST  /shops : Create a new shop.
     *
     * @param shopDTO the shopDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shopDTO, or with status 400 (Bad Request) if the shop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shops")
    @Timed
    public ResponseEntity<ShopDTO> createShop(@Valid @RequestBody ShopDTO shopDTO) throws URISyntaxException {
        log.debug("REST request to save Shop : {}", shopDTO);
        if (shopDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shop cannot already have an ID")).body(null);
        }
        ShopDTO result = shopService.save(shopDTO);
        return ResponseEntity.created(new URI("/api/shops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shops : Updates an existing shop.
     *
     * @param shopDTO the shopDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shopDTO,
     * or with status 400 (Bad Request) if the shopDTO is not valid,
     * or with status 500 (Internal Server Error) if the shopDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shops")
    @Timed
    public ResponseEntity<ShopDTO> updateShop(@Valid @RequestBody ShopDTO shopDTO) throws URISyntaxException {
        log.debug("REST request to update Shop : {}", shopDTO);
        if (shopDTO.getId() == null) {
            return createShop(shopDTO);
        }
        ShopDTO result = shopService.save(shopDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shopDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shops : get all the shops.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shops in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/shops")
    @Timed
    public ResponseEntity<List<ShopDTO>> getAllShops(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Shops");
        Page<ShopDTO> page = shopService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shops");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shops/:id : get the "id" shop.
     *
     * @param id the id of the shopDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shopDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shops/{id}")
    @Timed
    public ResponseEntity<ShopDTO> getShop(@PathVariable Long id) {
        log.debug("REST request to get Shop : {}", id);
        ShopDTO shopDTO = shopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shopDTO));
    }

    /**
     * DELETE  /shops/:id : delete the "id" shop.
     *
     * @param id the id of the shopDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shops/{id}")
    @Timed
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        log.debug("REST request to delete Shop : {}", id);
        shopService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
