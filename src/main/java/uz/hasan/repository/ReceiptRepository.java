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

    @Query("select distinct receipt from Receipt receipt")
    List<Receipt> findAllWithEagerRelationships();

    @Query("select receipt from Receipt receipt where receipt.id =:id")
    Receipt findOneWithEagerRelationships(@Param("id") Long id);

    Page<Receipt> findByStatus(Pageable pageable, ReceiptStatus status);

    Long countByStatus(ReceiptStatus aNew);

    Page<Receipt> findAllByOrderByDocDateDesc(Pageable pageable);

    Page<Receipt> findAllByOrderByIdDesc(Pageable pageable);

    Page<Receipt> findByStatusAndShopShopId(Pageable pageable, ReceiptStatus status, String shopId);

    Page<Receipt> findByShopShopIdOrderByDocDateDesc(Pageable pageable, String shopId);

    @Query(value = "select count(r) from Receipt r where r.status = :status and r.shop.shopId = :id")
    Long getCountByStatusAndShopId(@Param("status") ReceiptStatus status, @Param("id") String id);

    Page<Receipt> findAllByStatusNotIn(Pageable pageable, List<ReceiptStatus> statuses);

    Page<Receipt> findAllByStatusIn(Pageable pageable, List<ReceiptStatus> statuses);
}
