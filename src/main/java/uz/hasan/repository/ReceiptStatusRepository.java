package uz.hasan.repository;

import uz.hasan.domain.ReceiptStatus;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ReceiptStatus entity.
 */
@SuppressWarnings("unused")
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus,Long> {

}
