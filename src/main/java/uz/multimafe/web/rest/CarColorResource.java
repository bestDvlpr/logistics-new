package uz.multimafe.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.multimafe.domain.CarColor;

import uz.multimafe.repository.CarColorRepository;
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
 * REST controller for managing CarColor.
 */
@RestController
@RequestMapping("/api")
public class CarColorResource {

    private final Logger log = LoggerFactory.getLogger(CarColorResource.class);

    private static final String ENTITY_NAME = "carColor";
        
    private final CarColorRepository carColorRepository;

    public CarColorResource(CarColorRepository carColorRepository) {
        this.carColorRepository = carColorRepository;
    }

    /**
     * POST  /car-colors : Create a new carColor.
     *
     * @param carColor the carColor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carColor, or with status 400 (Bad Request) if the carColor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-colors")
    @Timed
    public ResponseEntity<CarColor> createCarColor(@Valid @RequestBody CarColor carColor) throws URISyntaxException {
        log.debug("REST request to save CarColor : {}", carColor);
        if (carColor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carColor cannot already have an ID")).body(null);
        }
        CarColor result = carColorRepository.save(carColor);
        return ResponseEntity.created(new URI("/api/car-colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-colors : Updates an existing carColor.
     *
     * @param carColor the carColor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carColor,
     * or with status 400 (Bad Request) if the carColor is not valid,
     * or with status 500 (Internal Server Error) if the carColor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-colors")
    @Timed
    public ResponseEntity<CarColor> updateCarColor(@Valid @RequestBody CarColor carColor) throws URISyntaxException {
        log.debug("REST request to update CarColor : {}", carColor);
        if (carColor.getId() == null) {
            return createCarColor(carColor);
        }
        CarColor result = carColorRepository.save(carColor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carColor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-colors : get all the carColors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carColors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/car-colors")
    @Timed
    public ResponseEntity<List<CarColor>> getAllCarColors(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CarColors");
        Page<CarColor> page = carColorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-colors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-colors/:id : get the "id" carColor.
     *
     * @param id the id of the carColor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carColor, or with status 404 (Not Found)
     */
    @GetMapping("/car-colors/{id}")
    @Timed
    public ResponseEntity<CarColor> getCarColor(@PathVariable Long id) {
        log.debug("REST request to get CarColor : {}", id);
        CarColor carColor = carColorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carColor));
    }

    /**
     * DELETE  /car-colors/:id : delete the "id" carColor.
     *
     * @param id the id of the carColor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-colors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarColor(@PathVariable Long id) {
        log.debug("REST request to delete CarColor : {}", id);
        carColorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
