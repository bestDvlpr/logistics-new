package uz.hasan.repository;

import uz.hasan.domain.ProductEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductEntry entity.
 */
@SuppressWarnings("unused")
public interface ProductEntryRepository extends JpaRepository<ProductEntry, Long> {

    List<ProductEntry> findByReceiptId(Long id);
}
