package uz.multimafe.repository;

import uz.multimafe.domain.ProductEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductEntry entity.
 */
@SuppressWarnings("unused")
public interface ProductEntryRepository extends JpaRepository<ProductEntry,Long> {

}
