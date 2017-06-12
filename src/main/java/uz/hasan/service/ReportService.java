package uz.hasan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.DeliveryCountByCompany;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;
import uz.hasan.service.dto.CarDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Service Interface for managing Report.
 */
public interface ReportService {

    /**
     * Generate a generic report.
     *
     * @param criteria the entity to save
     * @return the persisted entity
     */
    List<ProductDeliveryReport> getGenericReport(DeliveryReportCriteria criteria);

    void exportGenericReport(DeliveryReportCriteria criteria, HttpServletResponse response);

    List<DeliveryCountByCompany> getCountByCompanyByStatus(ReceiptStatus status);
}
