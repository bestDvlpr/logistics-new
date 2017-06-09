package uz.hasan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.Location;

import org.springframework.data.jpa.repository.*;
import uz.hasan.domain.enumeration.LocationType;

import java.util.List;

/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
public interface LocationRepository extends JpaRepository<Location,Long> {

    Page<Location> findByParentId(Pageable pageable, Long parentId);

    Page<Location> findByParentIdIsNull(Pageable pageable);

    List<Location> findByParentId(Long parentId);

    List<Location> findByParentIdIsNull();

    List<Location> findByType(LocationType type);
}
