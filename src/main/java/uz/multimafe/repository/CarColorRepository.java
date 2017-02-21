package uz.multimafe.repository;

import uz.multimafe.domain.CarColor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarColor entity.
 */
@SuppressWarnings("unused")
public interface CarColorRepository extends JpaRepository<CarColor,Long> {

}
