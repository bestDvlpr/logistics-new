package uz.hasan.web.rest;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.CompanyDeliveryCounts;
import uz.hasan.domain.pojos.report.DeliveryCountByCompany;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;
import uz.hasan.service.ReportService;
import uz.hasan.web.rest.util.PaginationUtil;

import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Report.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private final ReportService reportService;

    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * POST /report/generic : Generate a generic report.
     *
     * @param criteria the entity to save
     * @return the persisted entity
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/report/generic")
    public ResponseEntity<List<ProductDeliveryReport>> getGenericReport(@RequestBody DeliveryReportCriteria criteria, @ApiParam Pageable pageable) throws URISyntaxException {
        log.info("REST request to generate report according to criteria: {}", criteria);
        Page<ProductDeliveryReport> page = reportService.getGenericReport(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/report/generic");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST /report/generic/export : Generate a generic report and create file from it.
     *
     * @param criteria the var for save
     * @return the persisted entity
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/report/generic/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity exportGenericReport(@RequestBody DeliveryReportCriteria criteria, HttpServletResponse response) throws URISyntaxException {
        log.info("REST request to generate report according to criteria: {}", criteria);
        reportService.exportGenericReport(criteria, response);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/report/by-status/{status}/{startDate}/{endDate}")
    public ResponseEntity<List<DeliveryCountByCompany>> getCountByCompanyByStatus(@PathVariable ReceiptStatus status, @PathVariable String startDate, @PathVariable String endDate) {
        if (status == null) {
            return null;
        }
        List<DeliveryCountByCompany> deliveryCounts = reportService.getCountByCompanyByStatus(status, startDate, endDate);
        return new ResponseEntity<>(deliveryCounts, HttpStatus.OK);
    }

    @PostMapping(value = "/report/count-by-company", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyDeliveryCounts>> getCountByCompany(@RequestBody DeliveryReportCriteria criteria) {
        List<CompanyDeliveryCounts> deliveryCounts = reportService.countsByCompany(criteria);
        return new ResponseEntity<>(deliveryCounts, HttpStatus.OK);
    }
}
