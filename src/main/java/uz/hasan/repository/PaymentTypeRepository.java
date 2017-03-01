package uz.hasan.repository;

import uz.hasan.domain.PaymentType;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the PaymentType entity.
 */
@SuppressWarnings("unused")
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {

}
