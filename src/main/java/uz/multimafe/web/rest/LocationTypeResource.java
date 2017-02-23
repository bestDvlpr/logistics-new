package uz.multimafe.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.multimafe.domain.LocationType;
import uz.multimafe.service.LocationTypeService;
import uz.multimafe.web.rest.util.HeaderUtil;
import uz.multimafe.web.rest.util.PaginationUtil;
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
 * REST controller for managing LocationType.
 */
@RestController
@RequestMapping("/api")
public class LocationTypeResource {

    private final Logger log = LoggerFactory.getLogger(LocationTypeResource.class);

    private static final String ENTITY_NAME = "locationType";
        
    private final LocationTypeService locationTypeService;

    public LocationTypeResource(LocationTypeService locationTypeService) {
        this.locationTypeService = locationTypeService;
    }

    /**
     * POST  /location-types : Create a new locationType.
     *
     * @param locationType the locationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationType, or with status 400 (Bad Request) if the locationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/location-types")
    @Timed
    public ResponseEntity<LocationType> createLocationType(@Valid @RequestBody LocationType locationType) throws URISyntaxException {
        log.debug("REST request to save LocationType : {}", locationType);
        if (locationType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new locationType cannot already have an ID")).body(null);
        }
        LocationType result = locationTypeService.save(locationType);
        return ResponseEntity.created(new URI("/api/location-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /location-types : Updates an existing locationType.
     *
     * @param locationType the locationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationType,
     * or with status 400 (Bad Request) if the locationType is not valid,
     * or with status 500 (Internal Server Error) if the locationType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/location-types")
    @Timed
    public ResponseEntity<LocationType> updateLocationType(@Valid @RequestBody LocationType locationType) throws URISyntaxException {
        log.debug("REST request to update LocationType : {}", locationType);
        if (locationType.getId() == null) {
            return createLocationType(locationType);
        }
        LocationType result = locationTypeService.save(locationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /location-types : get all the locationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of locationTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/location-types")
    @Timed
    public ResponseEntity<List<LocationType>> getAllLocationTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LocationTypes");
        Page<LocationType> page = locationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/location-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /location-types/:id : get the "id" locationType.
     *
     * @param id the id of the locationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationType, or with status 404 (Not Found)
     */
    @GetMapping("/location-types/{id}")
    @Timed
    public ResponseEntity<LocationType> getLocationType(@PathVariable Long id) {
        log.debug("REST request to get LocationType : {}", id);
        LocationType locationType = locationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationType));
    }

    /**
     * DELETE  /location-types/:id : delete the "id" locationType.
     *
     * @param id the id of the locationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/location-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocationType(@PathVariable Long id) {
        log.debug("REST request to delete LocationType : {}", id);
        locationTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
