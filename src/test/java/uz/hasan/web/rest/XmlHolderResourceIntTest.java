package uz.hasan.web.rest;

import uz.hasan.LogisticsApp;

import uz.hasan.domain.XmlHolder;
import uz.hasan.repository.XmlHolderRepository;
import uz.hasan.service.XmlHolderService;
import uz.hasan.service.dto.XmlHolderDTO;
import uz.hasan.service.mapper.XmlHolderMapper;
import uz.hasan.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static uz.hasan.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the XmlHolderResource REST controller.
 *
 * @see XmlHolderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApp.class)
public class XmlHolderResourceIntTest {

    private static final String DEFAULT_XML_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_XML_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    @Autowired
    private XmlHolderRepository xmlHolderRepository;

    @Autowired
    private XmlHolderMapper xmlHolderMapper;

    @Autowired
    private XmlHolderService xmlHolderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restXmlHolderMockMvc;

    private XmlHolder xmlHolder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        XmlHolderResource xmlHolderResource = new XmlHolderResource(xmlHolderService);
        this.restXmlHolderMockMvc = MockMvcBuilders.standaloneSetup(xmlHolderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static XmlHolder createEntity(EntityManager em) {
        XmlHolder xmlHolder = new XmlHolder()
                .xmlContent(DEFAULT_XML_CONTENT)
                .date(DEFAULT_DATE)
                .checked(DEFAULT_CHECKED);
        return xmlHolder;
    }

    @Before
    public void initTest() {
        xmlHolder = createEntity(em);
    }

    @Test
    @Transactional
    public void createXmlHolder() throws Exception {
        int databaseSizeBeforeCreate = xmlHolderRepository.findAll().size();

        // Create the XmlHolder
        XmlHolderDTO xmlHolderDTO = xmlHolderMapper.xmlHolderToXmlHolderDTO(xmlHolder);

        restXmlHolderMockMvc.perform(post("/api/xml-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xmlHolderDTO)))
            .andExpect(status().isCreated());

        // Validate the XmlHolder in the database
        List<XmlHolder> xmlHolderList = xmlHolderRepository.findAll();
        assertThat(xmlHolderList).hasSize(databaseSizeBeforeCreate + 1);
        XmlHolder testXmlHolder = xmlHolderList.get(xmlHolderList.size() - 1);
        assertThat(testXmlHolder.getXmlContent()).isEqualTo(DEFAULT_XML_CONTENT);
        assertThat(testXmlHolder.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testXmlHolder.isChecked()).isEqualTo(DEFAULT_CHECKED);
    }

    @Test
    @Transactional
    public void createXmlHolderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = xmlHolderRepository.findAll().size();

        // Create the XmlHolder with an existing ID
        XmlHolder existingXmlHolder = new XmlHolder();
        existingXmlHolder.setId(1L);
        XmlHolderDTO existingXmlHolderDTO = xmlHolderMapper.xmlHolderToXmlHolderDTO(existingXmlHolder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restXmlHolderMockMvc.perform(post("/api/xml-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingXmlHolderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<XmlHolder> xmlHolderList = xmlHolderRepository.findAll();
        assertThat(xmlHolderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkXmlContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = xmlHolderRepository.findAll().size();
        // set the field null
        xmlHolder.setXmlContent(null);

        // Create the XmlHolder, which fails.
        XmlHolderDTO xmlHolderDTO = xmlHolderMapper.xmlHolderToXmlHolderDTO(xmlHolder);

        restXmlHolderMockMvc.perform(post("/api/xml-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xmlHolderDTO)))
            .andExpect(status().isBadRequest());

        List<XmlHolder> xmlHolderList = xmlHolderRepository.findAll();
        assertThat(xmlHolderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllXmlHolders() throws Exception {
        // Initialize the database
        xmlHolderRepository.saveAndFlush(xmlHolder);

        // Get all the xmlHolderList
        restXmlHolderMockMvc.perform(get("/api/xml-holders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(xmlHolder.getId().intValue())))
            .andExpect(jsonPath("$.[*].xmlContent").value(hasItem(DEFAULT_XML_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getXmlHolder() throws Exception {
        // Initialize the database
        xmlHolderRepository.saveAndFlush(xmlHolder);

        // Get the xmlHolder
        restXmlHolderMockMvc.perform(get("/api/xml-holders/{id}", xmlHolder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(xmlHolder.getId().intValue()))
            .andExpect(jsonPath("$.xmlContent").value(DEFAULT_XML_CONTENT.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingXmlHolder() throws Exception {
        // Get the xmlHolder
        restXmlHolderMockMvc.perform(get("/api/xml-holders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateXmlHolder() throws Exception {
        // Initialize the database
        xmlHolderRepository.saveAndFlush(xmlHolder);
        int databaseSizeBeforeUpdate = xmlHolderRepository.findAll().size();

        // Update the xmlHolder
        XmlHolder updatedXmlHolder = xmlHolderRepository.findOne(xmlHolder.getId());
        updatedXmlHolder
                .xmlContent(UPDATED_XML_CONTENT)
                .date(UPDATED_DATE)
                .checked(UPDATED_CHECKED);
        XmlHolderDTO xmlHolderDTO = xmlHolderMapper.xmlHolderToXmlHolderDTO(updatedXmlHolder);

        restXmlHolderMockMvc.perform(put("/api/xml-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xmlHolderDTO)))
            .andExpect(status().isOk());

        // Validate the XmlHolder in the database
        List<XmlHolder> xmlHolderList = xmlHolderRepository.findAll();
        assertThat(xmlHolderList).hasSize(databaseSizeBeforeUpdate);
        XmlHolder testXmlHolder = xmlHolderList.get(xmlHolderList.size() - 1);
        assertThat(testXmlHolder.getXmlContent()).isEqualTo(UPDATED_XML_CONTENT);
        assertThat(testXmlHolder.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testXmlHolder.isChecked()).isEqualTo(UPDATED_CHECKED);
    }

    @Test
    @Transactional
    public void updateNonExistingXmlHolder() throws Exception {
        int databaseSizeBeforeUpdate = xmlHolderRepository.findAll().size();

        // Create the XmlHolder
        XmlHolderDTO xmlHolderDTO = xmlHolderMapper.xmlHolderToXmlHolderDTO(xmlHolder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restXmlHolderMockMvc.perform(put("/api/xml-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(xmlHolderDTO)))
            .andExpect(status().isCreated());

        // Validate the XmlHolder in the database
        List<XmlHolder> xmlHolderList = xmlHolderRepository.findAll();
        assertThat(xmlHolderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteXmlHolder() throws Exception {
        // Initialize the database
        xmlHolderRepository.saveAndFlush(xmlHolder);
        int databaseSizeBeforeDelete = xmlHolderRepository.findAll().size();

        // Get the xmlHolder
        restXmlHolderMockMvc.perform(delete("/api/xml-holders/{id}", xmlHolder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<XmlHolder> xmlHolderList = xmlHolderRepository.findAll();
        assertThat(xmlHolderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(XmlHolder.class);
    }
}
