package uz.multimafe.repository;

import uz.multimafe.domain.Driver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
public interface DriverRepository extends JpaRepository<Driver,Long> {

}
