package uz.hasan.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.DeliveryCountByCompany;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;
import uz.hasan.service.ReportService;

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
    public ResponseEntity<List<ProductDeliveryReport>> getGenericReport(@RequestBody DeliveryReportCriteria criteria) throws URISyntaxException {
        log.info("REST request to generate report according to criteria: {}", criteria);
        return new ResponseEntity<>(reportService.getGenericReport(criteria), HttpStatus.OK);
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
//        DeliveryReportCriteria criteria = new DeliveryReportCriteria(startDate, endDate, new CustomCompany(null, companyName), new CustomDistrict(null, districtName));
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
}
