package uz.hasan.repository;

import uz.hasan.domain.PayType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PayType entity.
 */
@SuppressWarnings("unused")
public interface PayTypeRepository extends JpaRepository<PayType,Long> {

}
