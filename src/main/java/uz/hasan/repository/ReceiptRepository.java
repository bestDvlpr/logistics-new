package uz.hasan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.Receipt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import uz.hasan.domain.enumeration.ReceiptStatus;

import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Receipt findFirstByDocID(String docID);

    @Query("select distinct receipt from Receipt receipt left join fetch receipt.addresses")
    List<Receipt> findAllWithEagerRelationships();

    @Query("select receipt from Receipt receipt left join fetch receipt.addresses where receipt.id =:id")
    Receipt findOneWithEagerRelationships(@Param("id") Long id);

    Page<Receipt> findByStatus(Pageable pageable, ReceiptStatus status);

    Long countByStatus(ReceiptStatus aNew);

    Page<Receipt> findAllByOrderByIdDesc(Pageable pageable);

    Page<Receipt> findByStatusAndShopId(Pageable pageable, ReceiptStatus status, String shopId);
}
