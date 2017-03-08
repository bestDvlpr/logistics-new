package uz.hasan.web.rest;

import com.codahale.metrics.annotation.Timed;
import uz.hasan.service.XmlHolderService;
import uz.hasan.web.rest.util.HeaderUtil;
import uz.hasan.web.rest.util.PaginationUtil;
import uz.hasan.service.dto.XmlHolderDTO;
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
 * REST controller for managing XmlHolder.
 */
@RestController
@RequestMapping("/api")
public class XmlHolderResource {

    private final Logger log = LoggerFactory.getLogger(XmlHolderResource.class);

    private static final String ENTITY_NAME = "xmlHolder";
        
    private final XmlHolderService xmlHolderService;

    public XmlHolderResource(XmlHolderService xmlHolderService) {
        this.xmlHolderService = xmlHolderService;
    }

    /**
     * POST  /xml-holders : Create a new xmlHolder.
     *
     * @param xmlHolderDTO the xmlHolderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new xmlHolderDTO, or with status 400 (Bad Request) if the xmlHolder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/xml-holders")
    @Timed
    public ResponseEntity<XmlHolderDTO> createXmlHolder(@Valid @RequestBody XmlHolderDTO xmlHolderDTO) throws URISyntaxException {
        log.debug("REST request to save XmlHolder : {}", xmlHolderDTO);
        if (xmlHolderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new xmlHolder cannot already have an ID")).body(null);
        }
        XmlHolderDTO result = xmlHolderService.save(xmlHolderDTO);
        return ResponseEntity.created(new URI("/api/xml-holders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /xml-holders : Updates an existing xmlHolder.
     *
     * @param xmlHolderDTO the xmlHolderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated xmlHolderDTO,
     * or with status 400 (Bad Request) if the xmlHolderDTO is not valid,
     * or with status 500 (Internal Server Error) if the xmlHolderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/xml-holders")
    @Timed
    public ResponseEntity<XmlHolderDTO> updateXmlHolder(@Valid @RequestBody XmlHolderDTO xmlHolderDTO) throws URISyntaxException {
        log.debug("REST request to update XmlHolder : {}", xmlHolderDTO);
        if (xmlHolderDTO.getId() == null) {
            return createXmlHolder(xmlHolderDTO);
        }
        XmlHolderDTO result = xmlHolderService.save(xmlHolderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, xmlHolderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /xml-holders : get all the xmlHolders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of xmlHolders in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/xml-holders")
    @Timed
    public ResponseEntity<List<XmlHolderDTO>> getAllXmlHolders(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of XmlHolders");
        Page<XmlHolderDTO> page = xmlHolderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/xml-holders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /xml-holders/:id : get the "id" xmlHolder.
     *
     * @param id the id of the xmlHolderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the xmlHolderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/xml-holders/{id}")
    @Timed
    public ResponseEntity<XmlHolderDTO> getXmlHolder(@PathVariable Long id) {
        log.debug("REST request to get XmlHolder : {}", id);
        XmlHolderDTO xmlHolderDTO = xmlHolderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(xmlHolderDTO));
    }

    /**
     * DELETE  /xml-holders/:id : delete the "id" xmlHolder.
     *
     * @param id the id of the xmlHolderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/xml-holders/{id}")
    @Timed
    public ResponseEntity<Void> deleteXmlHolder(@PathVariable Long id) {
        log.debug("REST request to delete XmlHolder : {}", id);
        xmlHolderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
