package uz.hasan.repository;

import uz.hasan.domain.LoyaltyCard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LoyaltyCard entity.
 */
@SuppressWarnings("unused")
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard, Long> {

    LoyaltyCard findFirstByLoyaltyCardID(String loyaltyCardID);
}
