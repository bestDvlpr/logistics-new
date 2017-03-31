package uz.hasan.repository;

import uz.hasan.domain.Shop;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Shop entity.
 */
@SuppressWarnings("unused")
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByShopId(String shopId);
}
