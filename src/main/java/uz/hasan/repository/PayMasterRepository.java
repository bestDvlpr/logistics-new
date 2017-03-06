package uz.hasan.repository;

import uz.hasan.domain.PayMaster;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PayMaster entity.
 */
@SuppressWarnings("unused")
public interface PayMasterRepository extends JpaRepository<PayMaster, Long> {

    PayMaster findByPaymasterID(String paymasterID);

}
