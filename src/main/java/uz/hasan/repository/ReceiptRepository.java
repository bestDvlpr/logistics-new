package uz.hasan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.Receipt;

import org.springframework.data.jpa.repository.*;
import uz.hasan.domain.enumeration.ReceiptStatus;

import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Receipt findFirstByDocID(String docID);


    Page<Receipt> findByStatus(Pageable pageable, ReceiptStatus status);
}
