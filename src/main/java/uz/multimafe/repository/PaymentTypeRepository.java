package uz.multimafe.repository;

import uz.multimafe.domain.PaymentType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PaymentType entity.
 */
@SuppressWarnings("unused")
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {

}
