package uz.multimafe.repository;

import uz.multimafe.domain.DriverStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DriverStatus entity.
 */
@SuppressWarnings("unused")
public interface DriverStatusRepository extends JpaRepository<DriverStatus,Long> {

}
