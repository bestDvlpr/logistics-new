package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.CarTypeService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.CarTypeDTO;
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
 * REST controller for managing CarType.
 */
@RestController
@RequestMapping("/api")
public class CarTypeResource {

    private final Logger log = LoggerFactory.getLogger(CarTypeResource.class);

    private static final String ENTITY_NAME = "carType";

    private final CarTypeService carTypeService;

    public CarTypeResource(CarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }

    /**
     * POST  /car-types : Create a new carType.
     *
     * @param carTypeDTO the carTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carTypeDTO, or with status 400 (Bad Request) if the carType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-types")
    @Timed
    public ResponseEntity<CarTypeDTO> createCarType(@Valid @RequestBody CarTypeDTO carTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CarType : {}", carTypeDTO);
        if (carTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carType cannot already have an ID")).body(null);
        }
        CarTypeDTO result = carTypeService.save(carTypeDTO);
        return ResponseEntity.created(new URI("/api/car-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-types : Updates an existing carType.
     *
     * @param carTypeDTO the carTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carTypeDTO,
     * or with status 400 (Bad Request) if the carTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the carTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-types")
    @Timed
    public ResponseEntity<CarTypeDTO> updateCarType(@Valid @RequestBody CarTypeDTO carTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CarType : {}", carTypeDTO);
        if (carTypeDTO.getId() == null) {
            return createCarType(carTypeDTO);
        }
        CarTypeDTO result = carTypeService.save(carTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-types : get all the carTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/car-types")
    @Timed
    public ResponseEntity<List<CarTypeDTO>> getAllCarTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CarTypes");
        Page<CarTypeDTO> page = carTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-types/:id : get the "id" carType.
     *
     * @param id the id of the carTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-types/{id}")
    @Timed
    public ResponseEntity<CarTypeDTO> getCarType(@PathVariable Long id) {
        log.debug("REST request to get CarType : {}", id);
        CarTypeDTO carTypeDTO = carTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carTypeDTO));
    }

    /**
     * DELETE  /car-types/:id : delete the "id" carType.
     *
     * @param id the id of the carTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarType(@PathVariable Long id) {
        log.debug("REST request to delete CarType : {}", id);
        carTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
