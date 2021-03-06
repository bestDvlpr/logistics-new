package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.domain.Location;
import uz.hasan.domain.enumeration.LocationType;
import uz.hasan.service.LocationService;
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
 * REST controller for managing Location.
 */
@RestController
@RequestMapping("/api")
public class LocationResource {

    private final Logger log = LoggerFactory.getLogger(LocationResource.class);

    private static final String ENTITY_NAME = "location";

    private final LocationService locationService;

    public LocationResource(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * POST  /locations : Create a new location.
     *
     * @param location the location to create
     * @return the ResponseEntity with status 201 (Created) and with body the new location, or with status 400 (Bad Request) if the location has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locations")
    @Timed
    public ResponseEntity<Location> createLocation(@Valid @RequestBody Location location) throws URISyntaxException {
        log.debug("REST request to save Location : {}", location);
        if (location.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new location cannot already have an ID")).body(null);
        }
        Location result = locationService.save(location);
        return ResponseEntity.created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locations : Updates an existing location.
     *
     * @param location the location to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated location,
     * or with status 400 (Bad Request) if the location is not valid,
     * or with status 500 (Internal Server Error) if the location couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locations")
    @Timed
    public ResponseEntity<Location> updateLocation(@Valid @RequestBody Location location) throws URISyntaxException {
        log.debug("REST request to update Location : {}", location);
        if (location.getId() == null) {
            return createLocation(location);
        }
        Location result = locationService.save(location);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, location.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locations : get all the locations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of locations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/locations")
    @Timed
    public ResponseEntity<List<Location>> getAllLocations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Locations");
        Page<Location> page = locationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/locations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /locations/all : get all the locations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/locations/all")
    @Timed
    public ResponseEntity<List<Location>> getAllLocations()
        throws URISyntaxException {
        log.debug("REST request to get a page of Locations");
        List<Location> page = locationService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /locations/:id : get the "id" location.
     *
     * @param id the id of the location to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the location, or with status 404 (Not Found)
     */
    @GetMapping("/locations/{id}")
    @Timed
    public ResponseEntity<Location> getLocation(@PathVariable Long id) {
        log.debug("REST request to get Location : {}", id);
        Location location = locationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(location));
    }

    /**
     * GET  /locations/children/:id : get children of the "id" location.
     *
     * @param id the id of the location to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locations, or with status 404 (Not Found)
     */
    @GetMapping("/locations/children/{id}")
    @Timed
    public ResponseEntity<List<Location>> getChildLocations(@ApiParam Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get child Locations of : {}", id);
        Page<Location> children = locationService.findChildren(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(children, "/api/locations/children/{id}");
        return new ResponseEntity<>(children.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /locations/children-list/:id : get children of the "id" location.
     *
     * @param id the id of the location to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locations, or with status 404 (Not Found)
     */
    @GetMapping("/locations/child-list/{id}")
    @Timed
    public ResponseEntity<List<Location>> getChildLocationList(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get child Locations of : {}", id);
        List<Location> children = locationService.findChildren(id);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    /**
     * GET  /locations/countries : get country locations.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the locations, or with status 404 (Not Found)
     */
    @GetMapping("/locations/countries")
    @Timed
    public ResponseEntity<List<Location>> getCountries(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get only country Locations");
        Page<Location> countries = locationService.findByParentIdIsNull(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(countries, "/api/locations/countries");
        return new ResponseEntity<>(countries.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /locations/countries : get country locations.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the locations, or with status 404 (Not Found)
     */
    @GetMapping("/locations/country-list")
    @Timed
    public ResponseEntity<List<Location>> getCountryList(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get only country Locations");
        List<Location> countries = locationService.findByParentIdIsNull();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    /**
     * DELETE  /locations/:id : delete the "id" location.
     *
     * @param id the id of the location to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        log.debug("REST request to delete Location : {}", id);
        locationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get location children by parent ID.
     *
     * @param type the type of the entity
     * @return the entity list
     */
    @GetMapping("/locations/by-type/{type}")
    public ResponseEntity<List<Location>> getByType(@PathVariable LocationType type) throws URISyntaxException {
        log.debug("REST request to get Locations of type: {}", type);
        List<Location> children = locationService.findByType(type);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

}
