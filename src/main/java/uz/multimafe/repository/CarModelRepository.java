package uz.multimafe.repository;

import uz.multimafe.domain.CarModel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarModel entity.
 */
@SuppressWarnings("unused")
public interface CarModelRepository extends JpaRepository<CarModel,Long> {

}
