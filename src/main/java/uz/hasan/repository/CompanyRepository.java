package uz.hasan.repository;

import uz.hasan.domain.Company;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findByIdNumber(String shopId);

    Company findByName(String companyName);

    List<Company> findByNameLike(String name);
}
