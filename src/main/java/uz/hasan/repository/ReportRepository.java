package uz.hasan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;
import uz.hasan.domain.Receipt;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReportRepository extends JpaRepository<Receipt, Long> {

    @Query(nativeQuery = true)
    List<ProductDeliveryReport> overallReport(String startDate, String endDate, String companyName, String districtName);
}
