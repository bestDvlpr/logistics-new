package uz.hasan.repository;

import uz.hasan.domain.LoyaltyCard;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LoyaltyCard entity.
 */
@SuppressWarnings("unused")
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard,Long> {

}
