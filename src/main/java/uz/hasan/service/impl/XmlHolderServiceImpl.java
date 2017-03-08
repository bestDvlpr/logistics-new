package uz.hasan.service.impl;

import uz.hasan.service.XmlHolderService;
import uz.hasan.domain.XmlHolder;
import uz.hasan.repository.XmlHolderRepository;
import uz.hasan.service.dto.XmlHolderDTO;
import uz.hasan.service.mapper.XmlHolderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing XmlHolder.
 */
@Service
@Transactional
public class XmlHolderServiceImpl implements XmlHolderService{

    private final Logger log = LoggerFactory.getLogger(XmlHolderServiceImpl.class);
    
    private final XmlHolderRepository xmlHolderRepository;

    private final XmlHolderMapper xmlHolderMapper;

    public XmlHolderServiceImpl(XmlHolderRepository xmlHolderRepository, XmlHolderMapper xmlHolderMapper) {
        this.xmlHolderRepository = xmlHolderRepository;
        this.xmlHolderMapper = xmlHolderMapper;
    }

    /**
     * Save a xmlHolder.
     *
     * @param xmlHolderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public XmlHolderDTO save(XmlHolderDTO xmlHolderDTO) {
        log.debug("Request to save XmlHolder : {}", xmlHolderDTO);
        XmlHolder xmlHolder = xmlHolderMapper.xmlHolderDTOToXmlHolder(xmlHolderDTO);
        xmlHolder = xmlHolderRepository.save(xmlHolder);
        XmlHolderDTO result = xmlHolderMapper.xmlHolderToXmlHolderDTO(xmlHolder);
        return result;
    }

    /**
     *  Get all the xmlHolders.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<XmlHolderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all XmlHolders");
        Page<XmlHolder> result = xmlHolderRepository.findAll(pageable);
        return result.map(xmlHolder -> xmlHolderMapper.xmlHolderToXmlHolderDTO(xmlHolder));
    }

    /**
     *  Get one xmlHolder by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public XmlHolderDTO findOne(Long id) {
        log.debug("Request to get XmlHolder : {}", id);
        XmlHolder xmlHolder = xmlHolderRepository.findOne(id);
        XmlHolderDTO xmlHolderDTO = xmlHolderMapper.xmlHolderToXmlHolderDTO(xmlHolder);
        return xmlHolderDTO;
    }

    /**
     *  Delete the  xmlHolder by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete XmlHolder : {}", id);
        xmlHolderRepository.delete(id);
    }
}
