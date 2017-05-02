package uz.hasan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.CarStatus;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.XDocTemplate;
import uz.hasan.repository.*;
import uz.hasan.security.AuthoritiesConstants;
import uz.hasan.service.ExcelService;
import uz.hasan.service.ReceiptService;
import uz.hasan.service.UploadService;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ProductEntryDTO;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.service.mapper.CustomProductEntriesMapper;
import uz.hasan.service.mapper.ProductEntryMapper;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.service.mapper.ReceiptProductEntriesMapper;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service Implementation for managing Receipt.
 */
@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    private final Logger log = LoggerFactory.getLogger(UploadServiceImpl.class);

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    private final ReceiptProductEntriesMapper receiptProductEntriesMapper;

    private final ProductEntryMapper productEntryMapper;

    private final ProductEntryRepository productEntryRepository;

    private final PayMasterRepository payMasterRepository;

    private final LoyaltyCardRepository loyaltyCardRepository;

    private final ClientRepository clientRepository;

    private final CarRepository carRepository;

    private final UserService userService;

    private final CustomProductEntriesMapper customProductEntryMapper;

    private final ExcelService excelService;

    private final EntityManager entityManager;

    public UploadServiceImpl(ReceiptRepository receiptRepository,
                             ReceiptMapper receiptMapper,
                             ReceiptProductEntriesMapper receiptProductEntriesMapper,
                             ProductEntryMapper productEntryMapper,
                             CarRepository carRepository,
                             ProductEntryRepository productEntryRepository,
                             LoyaltyCardRepository loyaltyCardRepository,
                             ClientRepository clientRepository,
                             PayMasterRepository payMasterRepository,
                             UserService userService,
                             CustomProductEntriesMapper customProductEntryMapper,
                             ExcelService excelService,
                             EntityManager entityManager) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
        this.receiptProductEntriesMapper = receiptProductEntriesMapper;
        this.productEntryMapper = productEntryMapper;
        this.productEntryRepository = productEntryRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.clientRepository = clientRepository;
        this.payMasterRepository = payMasterRepository;
        this.carRepository = carRepository;
        this.userService = userService;
        this.customProductEntryMapper = customProductEntryMapper;
        this.excelService = excelService;
        this.entityManager = entityManager;
    }

}
