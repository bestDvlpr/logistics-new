package uz.hasan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.pojos.report.ByDistrict;
import uz.hasan.domain.pojos.report.DeliveryCountByCompany;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;

import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
@Transactional
public interface ReportRepository extends JpaRepository<Receipt, Long> {

    @Query(nativeQuery = true)
    @Transactional
    List<ProductDeliveryReport> overallReport(String startDate, String endDate, String companyName, String districtName);

    @Query(nativeQuery = true)
    @Transactional
    List<DeliveryCountByCompany> countByCompany(String startDate, String endDate, String companyName, String districtName);

    @Query(nativeQuery = true)
    @Transactional
    List<ProductDeliveryReport> pagedOverallReport(String startDate, String endDate, String companyName, String districtName, String limit, String offset);
    @Query(nativeQuery = true)
    @Transactional
    List<ByDistrict> countByCompanyByDistrictByStatus(String startDate, String endDate, String companyName, String districtName, String status);
}
