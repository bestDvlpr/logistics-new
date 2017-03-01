package uz.hasan.repository;

import uz.hasan.domain.CarType;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CarType entity.
 */
@SuppressWarnings("unused")
public interface CarTypeRepository extends JpaRepository<CarType,Long> {

}
