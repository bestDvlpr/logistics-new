package uz.hasan.repository;

import uz.hasan.domain.Car;

import org.springframework.data.jpa.repository.*;
import uz.hasan.domain.enumeration.CarStatus;

import java.util.List;

/**
 * Spring Data JPA repository for the Car entity.
 */
@SuppressWarnings("unused")
public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> findByStatus(CarStatus status);
}
