package uz.multimafe.repository;

import uz.multimafe.domain.CarType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarType entity.
 */
@SuppressWarnings("unused")
public interface CarTypeRepository extends JpaRepository<CarType,Long> {

}
