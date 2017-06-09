package uz.hasan.service;

import uz.hasan.service.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     *  Get all the companies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompanyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" company.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CompanyDTO findOne(Long id);

    /**
     *  Delete the "id" company.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get all the companies without pagination.
     *
     *  @return the list of entities
     */
    List<CompanyDTO> findAll();

    /**
     *  Get the "idNumber" company.
     *
     *  @param idNumber the idNumber of the entity
     *  @return the entity
     */
    CompanyDTO findByIdNumber(String idNumber);

    /**
     * GET  /companies/autocomplete/{name} : get the "name" company.
     *
     * @param name the name of the companyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDTO, or with status 404 (Not Found)
     */
    List<CompanyDTO> findByNameLike(String name);
}
