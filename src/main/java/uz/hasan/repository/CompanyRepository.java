package uz.hasan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.hasan.domain.Company;
import uz.hasan.domain.pojos.criteria.CustomCompany;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByIdNumber(String shopId);

    Company findByName(String companyName);

    List<Company> findByNameLike(String name);

    @Query(nativeQuery = true)
    List<CustomCompany> customCompany(String startDate, String endDate);

    @Query(value = "SELECT DISTINCT company_id FROM receipt WHERE status='DELIVERED'", nativeQuery = true)
    List<Long> getAllShopIds();
}
