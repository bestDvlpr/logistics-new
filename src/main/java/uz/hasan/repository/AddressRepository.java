package uz.hasan.repository;

import uz.hasan.domain.Address;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
public interface AddressRepository extends JpaRepository<Address,Long> {

    Optional<List<Address>> findByClientId(Long clientId);
    /*
    * @Query("select receipt from Receipt receipt where receipt.sentBy.login = ?#{principal.username}")
    List<Receipt> findBySentByIsCurrentUser();

    @Query("select distinct receipt from Receipt receipt")
    List<Receipt> findAllWithEagerRelationships();
    @Query("select receipt from Receipt receipt where receipt.markedAsDeliveredBy.login = ?#{principal.username}")
    List<Receipt> findByMarkedAsDeliveredByIsCurrentUser();

    * */
}
