package uz.multimafe.repository;

import uz.multimafe.domain.Receipt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

    @Query("select distinct receipt from Receipt receipt left join fetch receipt.drivers")
    List<Receipt> findAllWithEagerRelationships();

    @Query("select receipt from Receipt receipt left join fetch receipt.drivers where receipt.id =:id")
    Receipt findOneWithEagerRelationships(@Param("id") Long id);

}
