package uz.multimafe.repository;

import uz.multimafe.domain.Driver;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
public interface DriverRepository extends JpaRepository<Driver,Long> {

    @Query("select distinct driver from Driver driver left join fetch driver.cars")
    List<Driver> findAllWithEagerRelationships();

    @Query("select driver from Driver driver left join fetch driver.cars where driver.id =:id")
    Driver findOneWithEagerRelationships(@Param("id") Long id);

}
