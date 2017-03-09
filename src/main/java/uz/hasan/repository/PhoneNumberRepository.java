package uz.hasan.repository;

import uz.hasan.domain.PhoneNumber;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PhoneNumber entity.
 */
@SuppressWarnings("unused")
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber,Long> {

    PhoneNumber findByNumber(String phoneNumber);
}
