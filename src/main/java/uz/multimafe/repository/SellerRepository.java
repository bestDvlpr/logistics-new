package uz.multimafe.repository;

import uz.multimafe.domain.Seller;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Seller entity.
 */
@SuppressWarnings("unused")
public interface SellerRepository extends JpaRepository<Seller,Long> {

}
