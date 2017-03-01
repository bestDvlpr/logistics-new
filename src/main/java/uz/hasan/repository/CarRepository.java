package uz.hasan.repository;

import uz.hasan.domain.Car;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Car entity.
 */
@SuppressWarnings("unused")
public interface CarRepository extends JpaRepository<Car,Long> {

}
