package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.DriverStatusService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.DriverStatusDTO;
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
 * REST controller for managing DriverStatus.
 */
@RestController
@RequestMapping("/api")
public class DriverStatusResource {

    private final Logger log = LoggerFactory.getLogger(DriverStatusResource.class);

    private static final String ENTITY_NAME = "driverStatus";

    private final DriverStatusService driverStatusService;

    public DriverStatusResource(DriverStatusService driverStatusService) {
        this.driverStatusService = driverStatusService;
    }

    /**
     * POST  /driver-statuses : Create a new driverStatus.
     *
     * @param driverStatusDTO the driverStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new driverStatusDTO, or with status 400 (Bad Request) if the driverStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/driver-statuses")
    @Timed
    public ResponseEntity<DriverStatusDTO> createDriverStatus(@Valid @RequestBody DriverStatusDTO driverStatusDTO) throws URISyntaxException {
        log.debug("REST request to save DriverStatus : {}", driverStatusDTO);
        if (driverStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new driverStatus cannot already have an ID")).body(null);
        }
        DriverStatusDTO result = driverStatusService.save(driverStatusDTO);
        return ResponseEntity.created(new URI("/api/driver-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /driver-statuses : Updates an existing driverStatus.
     *
     * @param driverStatusDTO the driverStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated driverStatusDTO,
     * or with status 400 (Bad Request) if the driverStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the driverStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/driver-statuses")
    @Timed
    public ResponseEntity<DriverStatusDTO> updateDriverStatus(@Valid @RequestBody DriverStatusDTO driverStatusDTO) throws URISyntaxException {
        log.debug("REST request to update DriverStatus : {}", driverStatusDTO);
        if (driverStatusDTO.getId() == null) {
            return createDriverStatus(driverStatusDTO);
        }
        DriverStatusDTO result = driverStatusService.save(driverStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, driverStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /driver-statuses : get all the driverStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of driverStatuses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/driver-statuses")
    @Timed
    public ResponseEntity<List<DriverStatusDTO>> getAllDriverStatuses(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DriverStatuses");
        Page<DriverStatusDTO> page = driverStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/driver-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /driver-statuses/:id : get the "id" driverStatus.
     *
     * @param id the id of the driverStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the driverStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driver-statuses/{id}")
    @Timed
    public ResponseEntity<DriverStatusDTO> getDriverStatus(@PathVariable Long id) {
        log.debug("REST request to get DriverStatus : {}", id);
        DriverStatusDTO driverStatusDTO = driverStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(driverStatusDTO));
    }

    /**
     * DELETE  /driver-statuses/:id : delete the "id" driverStatus.
     *
     * @param id the id of the driverStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/driver-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDriverStatus(@PathVariable Long id) {
        log.debug("REST request to delete DriverStatus : {}", id);
        driverStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
