package uz.hasan.repository;

import uz.hasan.domain.Seller;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Seller entity.
 */
@SuppressWarnings("unused")
public interface SellerRepository extends JpaRepository<Seller,Long> {

}
