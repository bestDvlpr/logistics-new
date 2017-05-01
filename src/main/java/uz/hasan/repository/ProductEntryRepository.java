package uz.hasan.repository;

import uz.hasan.domain.ProductEntry;

import org.springframework.data.jpa.repository.*;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.enumeration.ReceiptStatus;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductEntry entity.
 */
@SuppressWarnings("unused")
public interface ProductEntryRepository extends JpaRepository<ProductEntry, Long> {

    List<ProductEntry> findByReceiptId(Long id);

    List<ProductEntry> findByAttachedCarNumberAndStatus(String carNumber, ReceiptStatus status);

    List<ProductEntry> findByAttachedCarNumber(String carNumber);

    List<ProductEntry> findByAttachedCarNumberAndStatusNot(String carNumber, ReceiptStatus status);

    Long countByAttachedCarNumberAndStatusNot(String number, ReceiptStatus status);

    List<ProductEntry> findByAttachedCarNumberAndStatusAndCompanyId(String carNumber, ReceiptStatus status, Long companyId);

    List<ProductEntry> findAllByAttachedCarNumberAndStatusAndCompanyId(String carNumber, ReceiptStatus status, Long companyId);
}
