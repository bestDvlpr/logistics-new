package uz.multimafe.repository;

import uz.multimafe.domain.LocationType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LocationType entity.
 */
@SuppressWarnings("unused")
public interface LocationTypeRepository extends JpaRepository<LocationType,Long> {

}
