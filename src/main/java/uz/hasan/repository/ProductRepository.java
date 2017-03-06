package uz.hasan.repository;

import uz.hasan.domain.Product;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findFirstBySapCode(String sapCode);

}
