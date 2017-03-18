package uz.hasan.service;

import uz.hasan.service.dto.XmlHolderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing XmlHolder.
 */
public interface XmlHolderService {

    /**
     * Save a xmlHolder.
     *
     * @param xmlHolderDTO the entity to save
     * @return the persisted entity
     */
    XmlHolderDTO save(XmlHolderDTO xmlHolderDTO);

    /**
     *  Get all the xmlHolders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<XmlHolderDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" xmlHolder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    XmlHolderDTO findOne(Long id);

    /**
     *  Delete the "id" xmlHolder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
