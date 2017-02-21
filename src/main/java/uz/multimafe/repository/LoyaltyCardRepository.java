package uz.multimafe.repository;

import uz.multimafe.domain.LoyaltyCard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LoyaltyCard entity.
 */
@SuppressWarnings("unused")
public interface LoyaltyCardRepository extends JpaRepository<LoyaltyCard,Long> {

}
