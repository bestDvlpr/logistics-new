package uz.hasan.repository;

import uz.hasan.domain.CarColor;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CarColor entity.
 */
@SuppressWarnings("unused")
public interface CarColorRepository extends JpaRepository<CarColor,Long> {

}
