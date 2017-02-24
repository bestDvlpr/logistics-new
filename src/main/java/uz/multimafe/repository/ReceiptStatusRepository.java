package uz.multimafe.repository;

import uz.multimafe.domain.ReceiptStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ReceiptStatus entity.
 */
@SuppressWarnings("unused")
public interface ReceiptStatusRepository extends JpaRepository<ReceiptStatus,Long> {

}
