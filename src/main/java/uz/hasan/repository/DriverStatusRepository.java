package uz.hasan.repository;

import uz.hasan.domain.DriverStatus;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the DriverStatus entity.
 */
@SuppressWarnings("unused")
public interface DriverStatusRepository extends JpaRepository<DriverStatus,Long> {

}
