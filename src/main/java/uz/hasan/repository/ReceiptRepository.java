package uz.hasan.repository;

import uz.hasan.domain.Receipt;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

}
