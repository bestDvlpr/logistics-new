package uz.hasan.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.hasan.domain.pojos.criteria.CustomCompany;
import uz.hasan.domain.pojos.criteria.CustomDistrict;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;
import uz.hasan.repository.ReportRepository;

import java.util.List;

/**
 * REST controller for managing Report.
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private final ReportRepository reportRepository;

    public ReportResource(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PostMapping("/report/generic")
    public ResponseEntity<List<ProductDeliveryReport>> getGenericReport(@RequestBody DeliveryReportCriteria criteria) {
        if (criteria == null || (criteria.getDistrict() == null && criteria.getCompany() == null && criteria.getStartDate() == null && criteria.getEndDate() == null)) {
            criteria = new DeliveryReportCriteria("null", "null", new CustomCompany(null, "null"), new CustomDistrict(null, "null"));
        }
        if (criteria.getStartDate()==null || criteria.getEndDate()==null){
            criteria.setStartDate("null");
            criteria.setEndDate("null");
        }
        if (criteria.getCompany()==null){
            criteria.setCompany(new CustomCompany(null, "null"));
        }
        if (criteria.getDistrict()==null){
            criteria.setDistrict(new CustomDistrict(null, "null"));
        }
        return new ResponseEntity<>(reportRepository.overallReport(criteria.getStartDate(), criteria.getEndDate(), criteria.getCompanyName(), criteria.getDistrictName()), HttpStatus.OK);
    }

}
