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
}
