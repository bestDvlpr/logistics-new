package uz.hasan.repository;

import uz.hasan.domain.XmlHolder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the XmlHolder entity.
 */
@SuppressWarnings("unused")
public interface XmlHolderRepository extends JpaRepository<XmlHolder,Long> {

}
