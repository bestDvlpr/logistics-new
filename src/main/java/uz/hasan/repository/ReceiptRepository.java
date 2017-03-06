package uz.hasan.repository;

import uz.hasan.domain.Receipt;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Receipt findFirstByDocID(String docID);


}
