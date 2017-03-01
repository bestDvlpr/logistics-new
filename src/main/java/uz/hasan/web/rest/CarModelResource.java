package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.CarModelService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.CarModelDTO;
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
 * REST controller for managing CarModel.
 */
@RestController
@RequestMapping("/api")
public class CarModelResource {

    private final Logger log = LoggerFactory.getLogger(CarModelResource.class);

    private static final String ENTITY_NAME = "carModel";

    private final CarModelService carModelService;

    public CarModelResource(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    /**
     * POST  /car-models : Create a new carModel.
     *
     * @param carModelDTO the carModelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carModelDTO, or with status 400 (Bad Request) if the carModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-models")
    @Timed
    public ResponseEntity<CarModelDTO> createCarModel(@Valid @RequestBody CarModelDTO carModelDTO) throws URISyntaxException {
        log.debug("REST request to save CarModel : {}", carModelDTO);
        if (carModelDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carModel cannot already have an ID")).body(null);
        }
        CarModelDTO result = carModelService.save(carModelDTO);
        return ResponseEntity.created(new URI("/api/car-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-models : Updates an existing carModel.
     *
     * @param carModelDTO the carModelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carModelDTO,
     * or with status 400 (Bad Request) if the carModelDTO is not valid,
     * or with status 500 (Internal Server Error) if the carModelDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-models")
    @Timed
    public ResponseEntity<CarModelDTO> updateCarModel(@Valid @RequestBody CarModelDTO carModelDTO) throws URISyntaxException {
        log.debug("REST request to update CarModel : {}", carModelDTO);
        if (carModelDTO.getId() == null) {
            return createCarModel(carModelDTO);
        }
        CarModelDTO result = carModelService.save(carModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-models : get all the carModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carModels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/car-models")
    @Timed
    public ResponseEntity<List<CarModelDTO>> getAllCarModels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CarModels");
        Page<CarModelDTO> page = carModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-models/:id : get the "id" carModel.
     *
     * @param id the id of the carModelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carModelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-models/{id}")
    @Timed
    public ResponseEntity<CarModelDTO> getCarModel(@PathVariable Long id) {
        log.debug("REST request to get CarModel : {}", id);
        CarModelDTO carModelDTO = carModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carModelDTO));
    }

    /**
     * DELETE  /car-models/:id : delete the "id" carModel.
     *
     * @param id the id of the carModelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarModel(@PathVariable Long id) {
        log.debug("REST request to delete CarModel : {}", id);
        carModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
