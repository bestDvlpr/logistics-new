package uz.hasan.repository;

import uz.hasan.domain.LocationType;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LocationType entity.
 */
@SuppressWarnings("unused")
public interface LocationTypeRepository extends JpaRepository<LocationType,Long> {

}
