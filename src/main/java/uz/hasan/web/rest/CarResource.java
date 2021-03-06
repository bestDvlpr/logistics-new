package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.CarService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.CarDTO;
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
 * REST controller for managing Car.
 */
@RestController
@RequestMapping("/api")
public class CarResource {

    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    private static final String ENTITY_NAME = "car";

    private final CarService carService;

    public CarResource(CarService carService) {
        this.carService = carService;
    }

    /**
     * POST  /cars : Create a new car.
     *
     * @param carDTO the carDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carDTO, or with status 400 (Bad Request) if the car has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cars")
    @Timed
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO carDTO) throws URISyntaxException {
        log.debug("REST request to save Car : {}", carDTO);
        if (carDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car cannot already have an ID")).body(null);
        }
        CarDTO result = carService.save(carDTO);
        return ResponseEntity.created(new URI("/api/cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cars : Updates an existing car.
     *
     * @param carDTO the carDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carDTO,
     * or with status 400 (Bad Request) if the carDTO is not valid,
     * or with status 500 (Internal Server Error) if the carDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cars")
    @Timed
    public ResponseEntity<CarDTO> updateCar(@Valid @RequestBody CarDTO carDTO) throws URISyntaxException {
        log.debug("REST request to update Car : {}", carDTO);
        if (carDTO.getId() == null) {
            return createCar(carDTO);
        }
        CarDTO result = carService.save(carDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cars : get all the cars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cars")
    @Timed
    public ResponseEntity<List<CarDTO>> getAllCars(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cars");
        Page<CarDTO> page = carService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cars : get all the cars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cars/without-pagination")
    @Timed
    public ResponseEntity<List<CarDTO>> getAllCars()
        throws URISyntaxException {
        log.debug("REST request to get a page of Cars");
        List<CarDTO> page = carService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /cars/idles : get all idle cars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of CarStatus.IDLE cars in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cars/idles")
    @Timed
    public ResponseEntity<List<CarDTO>> getAllIdleCars()
        throws URISyntaxException {
        log.debug("REST request to get a list of idle Cars");
        List<CarDTO> list = carService.findAllIdleCars();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * GET  /cars/:id : get the "id" car.
     *
     * @param id the id of the carDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cars/{id}")
    @Timed
    public ResponseEntity<CarDTO> getCar(@PathVariable Long id) {
        log.debug("REST request to get Car : {}", id);
        CarDTO carDTO = carService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carDTO));
    }

    /**
     * DELETE  /cars/:id : delete the "id" car.
     *
     * @param id the id of the carDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cars/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        log.debug("REST request to delete Car : {}", id);
        carService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
