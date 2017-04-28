package uz.hasan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.Receipt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import uz.hasan.domain.enumeration.ReceiptStatus;

import java.util.Collection;
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

    @Query("select receipt from Receipt receipt where receipt.sentBy.login = ?#{principal.username}")
    List<Receipt> findBySentByIsCurrentUser();

    @Query("select receipt from Receipt receipt where receipt.markedAsDeliveredBy.login = ?#{principal.username}")
    List<Receipt> findByMarkedAsDeliveredByIsCurrentUser();

    Page<Receipt> findByStatus(Pageable pageable, ReceiptStatus status);

    Long countByStatus(ReceiptStatus aNew);

    Page<Receipt> findAllByOrderByDocDateDesc(Pageable pageable);

    Page<Receipt> findAllByOrderByIdDesc(Pageable pageable);

    Page<Receipt> findByStatusAndCompanyIdNumber(Pageable pageable, ReceiptStatus status, String idNumber);

    Page<Receipt> findByCompanyIdNumberOrderByDocDateDesc(Pageable pageable, String idNumber);

    @Query(value = "select count(r) from Receipt r where r.status = :status and r.company.idNumber = :id")
    Long getCountByStatusAndCompanyIdNumber(@Param("status") ReceiptStatus status, @Param("id") String id);

    Page<Receipt> findAllByStatusNotIn(Pageable pageable, Collection<ReceiptStatus> statuses);

    Page<Receipt> findAllByStatusIn(Pageable pageable, Collection<ReceiptStatus> statuses);
}
