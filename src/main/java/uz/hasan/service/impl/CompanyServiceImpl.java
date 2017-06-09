package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.Company;
import uz.hasan.repository.CompanyRepository;
import uz.hasan.service.CompanyService;
import uz.hasan.service.dto.CompanyDTO;
import uz.hasan.service.mapper.CompanyMapper;

import java.util.List;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        company = companyRepository.save(company);
        CompanyDTO result = companyMapper.companyToCompanyDTO(company);
        return result;
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        Page<Company> result = companyRepository.findAll(pageable);
        return result.map(company -> companyMapper.companyToCompanyDTO(company));
    }

    /**
     * Get one company by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOne(id);
        CompanyDTO companyDTO = companyMapper.companyToCompanyDTO(company);
        return companyDTO;
    }

    /**
     * Delete the  company by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }

    /**
     * Get all the companies without pagination.
     *
     * @return the list of entities
     */
    public List<CompanyDTO> findAll() {
        log.debug("Request to get all Companies without pagination");
        List<Company> result = companyRepository.findAll();
        return companyMapper.companiesToCompanyDTOs(result);
    }

    /**
     *  Get the "idNumber" company.
     *
     *  @param idNumber the idNumber of the entity
     *  @return the entity
     */
    public CompanyDTO findByIdNumber(String idNumber){
        log.debug("Request to get Company : {}", idNumber);
        Company company = companyRepository.findByIdNumber(idNumber);
        return companyMapper.companyToCompanyDTO(company);
    }

    /**
     * GET  /companies/autocomplete/{name} : get the "name" company.
     *
     * @param name the name of the companyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDTO, or with status 404 (Not Found)
     */
    @Override
    public List<CompanyDTO> findByNameLike(String name) {
        log.debug("REST request to get Company : {}", name);
        List<Company> companies = companyRepository.findByNameLike(name);
        return companyMapper.companiesToCompanyDTOs(companies);
    }
}
