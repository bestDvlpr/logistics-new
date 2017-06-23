package uz.hasan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.enumeration.DocType;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.WholeSaleFlag;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Receipt entity.
 */
@SuppressWarnings("unused")
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    Receipt findFirstByDocID(String docID);

    @Query("select distinct receipt from Receipt receipt")
    @Transactional
    List<Receipt> findAllWithEagerRelationships();

    @Query("select receipt from Receipt receipt where receipt.id =:id")
    @Transactional
    Receipt findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select receipt from Receipt receipt where receipt.sentBy.login = ?#{principal.username}")
    @Transactional
    List<Receipt> findBySentByIsCurrentUser();

    @Query("select receipt from Receipt receipt where receipt.markedAsDeliveredBy.login = ?#{principal.username}")
    @Transactional
    List<Receipt> findByMarkedAsDeliveredByIsCurrentUser();

    Page<Receipt> findByStatus(Pageable pageable, ReceiptStatus status);

    Long countByStatus(ReceiptStatus aNew);

    Page<Receipt> findAllByOrderByDocDateDesc(Pageable pageable);

    Page<Receipt> findAllByOrderByIdDesc(Pageable pageable);

    Page<Receipt> findByStatusAndCompanyIdNumberOrderByIdDesc(Pageable pageable, ReceiptStatus status, String idNumber);

    Page<Receipt> findByCompanyIdNumberOrderByDocDateDesc(Pageable pageable, String idNumber);

    @Query(value = "select count(r) from Receipt r where r.status = :status and r.company.idNumber = :id")
    @Transactional
    Long getCountByStatusAndCompanyIdNumber(@Param("status") ReceiptStatus status, @Param("id") String id);

    @Query(value = "select count(r) from Receipt r where r.status = :status and r.company.idNumber = :id and r.deliveredTime between :startDate and :endDate")
    @Transactional
    Long getCountByStatusAndCompanyIdNumber(@Param("status") ReceiptStatus status, @Param("id") String id, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    Page<Receipt> findAllByStatusNotInOrderByIdDesc(Pageable pageable, Collection<ReceiptStatus> statuses);

    Page<Receipt> findAllByStatusIn(Pageable pageable, Collection<ReceiptStatus> statuses);

    Page<Receipt> findAllByDocType(Pageable pageable, DocType docType);

    Page<Receipt> findByDocTypeAndCompanyIdNumberOrderByDocDateDesc(Pageable pageable, DocType displacement, String idNumber);

    Page<Receipt> findAllByDocTypeAndCompanyIdNumberOrderByIdDesc(Pageable pageable, DocType credit, String idNumber);

    Page<Receipt> findByClientId(Pageable pageable, Long clientId);

    Page<Receipt> findByStatusAndDocTypeIn(Pageable pageable, ReceiptStatus status, List<DocType> types);

    Page<Receipt> findAllByDocTypeIn(Pageable pageable, List<DocType> docTypes);

    Page<Receipt> findAllByDocTypeInAndCompanyIdNumberOrderByIdDesc(Pageable pageable, List<DocType> docTypes, String idNumber);

    Long countByStatusAndDocTypeIn(ReceiptStatus applicationSent, List<DocType> docTypes);

    Long countByStatusAndCompanyIdNumberAndDocTypeInAndWholeSaleFlag(ReceiptStatus status, String idNumber, List<DocType> docTypes, WholeSaleFlag wholeSaleFlag);

    Page<Receipt> findByDocTypeInAndCompanyIdNumberOrderByDocDateDesc(Pageable pageable, List<DocType> types, String idNumber);

    Page<Receipt> findAllByDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(Pageable pageable, List<DocType> docTypes, WholeSaleFlag wholeSaleFlag, String companyIdNumber);

    Page<Receipt> findByStatusAndDocTypeInAndCompanyIdNumberOrderByIdDesc(Pageable pageable, ReceiptStatus status, List<DocType> docTypes, String idNumber);

    Page<Receipt> findByStatusAndDocTypeInAndWholeSaleFlagAndCompanyIdNumberOrderByIdDesc(Pageable pageable, ReceiptStatus status, List<DocType> docTypes, WholeSaleFlag wholeSaleFlag, String idNumber);

    @Query(value = "SELECT * FROM f_common_report_paged_count(COALESCE(NULLIF(?1, 'null')), COALESCE(NULLIF(?2, 'null')), COALESCE(NULLIF(?3, 'null')), COALESCE(NULLIF(?4, 'null')))", nativeQuery = true)
    @Transactional
    Long countByDocDateAndCompanyAndRegion(String startDate, String endDate, String companyName, String districtName);

    Page<Receipt> findAllByDocTypeInAndWholeSaleFlagOrderByIdDesc(Pageable pageable, List<DocType> docTypes, WholeSaleFlag wholesale);
}
