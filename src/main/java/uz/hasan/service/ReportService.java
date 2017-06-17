package uz.hasan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.CompanyDeliveryCounts;
import uz.hasan.domain.pojos.report.DeliveryCountByCompany;
import uz.hasan.domain.pojos.report.DeliveryCountByCompanyByDistrict;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Service Interface for managing Report.
 */
public interface ReportService {

    /**
     * Generate a generic report page.
     *
     * @param criteria the entity to filter result by
     * @param pageable the entity to make result pageable
     * @return the persisted entity list
     */
    Page<ProductDeliveryReport> getGenericReport(DeliveryReportCriteria criteria, Pageable pageable);

    /**
     * Generate a generic report list.
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    List<ProductDeliveryReport> getGenericReport(DeliveryReportCriteria criteria);

    /**
     * Generate a generic report list and export them to MS-Excel file.
     *
     * @param criteria the entity to filter result by
     * @param response the entity to give generated file to
     */
    void exportGenericReport(DeliveryReportCriteria criteria, HttpServletResponse response);

    /**
     * Generate a generic report list and export them to MS-Excel file.
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    List<DeliveryCountByCompany> getDeliveryCountByCompanyByStatus(DeliveryReportCriteria criteria);

    /**
     * Generate a generic report list.
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    List<CompanyDeliveryCounts> countsByCompany(DeliveryReportCriteria criteria);

    /**
     * Generate a count report by criteria
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    List<DeliveryCountByCompanyByDistrict> countByCompanyByStatus(DeliveryReportCriteria criteria);
}
