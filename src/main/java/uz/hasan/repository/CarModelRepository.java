package uz.hasan.repository;

import uz.hasan.domain.CarModel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarModel entity.
 */
@SuppressWarnings("unused")
public interface CarModelRepository extends JpaRepository<CarModel,Long> {

}
