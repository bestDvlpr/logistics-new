package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;
import uz.hasan.service.IntegrationService;
import uz.hasan.service.dto.DriverDTO;
import uz.hasan.service.dto.IntegrateDTO;
import uz.hasan.service.dto.JsonApp;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by User on 3/4/2017.
 */

@RestController
@RequestMapping("/api")
public class IntegrationResource {


    private final Logger log = LoggerFactory.getLogger(IntegrationResource.class);

    private final IntegrationService integrationService;

    public IntegrationResource(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    /**
     * POST  /integrate : Integrate from service.
     *
     * @param integrateDTOs the integrateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new driverStatusDTO, or with status 400 (Bad Request) if the driverStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/integrate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JsonApp> integrate(@RequestBody List<IntegrateDTO> integrateDTOs) throws URISyntaxException {
        log.debug("REST request to save DriverStatus : {}", integrateDTOs);


        Boolean success = integrationService.integrate(integrateDTOs);
        JsonApp jsonApp = new JsonApp(success, HttpStatus.OK.value(), null);

        return new ResponseEntity<>(jsonApp, HttpStatus.OK);
    }


}
