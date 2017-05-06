package uz.hasan.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.hasan.domain.Company;
import uz.hasan.domain.Product;
import uz.hasan.domain.ProductEntry;
import uz.hasan.domain.Receipt;
import uz.hasan.domain.enumeration.DefectFlag;
import uz.hasan.domain.enumeration.DocType;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.repository.*;
import uz.hasan.service.ExcelService;
import uz.hasan.service.UploadService;
import uz.hasan.service.UserService;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;
import uz.hasan.service.mapper.CustomProductEntriesMapper;
import uz.hasan.service.mapper.ProductEntryMapper;
import uz.hasan.service.mapper.ReceiptMapper;
import uz.hasan.service.mapper.ReceiptProductEntriesMapper;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    private final ProductRepository productRepository;

    private final CompanyRepository companyRepository;

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
                             EntityManager entityManager,
                             ProductRepository productRepository,
                             CompanyRepository companyRepository) {
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
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public ReceiptProductEntriesDTO createDisplacementApplication(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiptProductEntriesMapper.receiptToReceiptProductEntryDTO(createReceipt(inputStream));
    }

    private Receipt createReceipt(InputStream file) {
        Receipt receipt = new Receipt();
        try {
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            String rowInvoiceNum = sheet.getRow(1).getCell(1).getStringCellValue();

            String docID = rowInvoiceNum.substring(11, 22);
            String receiptDate = rowInvoiceNum.substring(25, 36);
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = format.parse(receiptDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Set<ProductEntry> productEntrySet = new HashSet<>();

            Company company = userService.getUserWithAuthorities().getCompany();

            receipt.setDocType(DocType.DISPLACEMENT);
            receipt.setCompany(company);
            receipt.setDocID(docID);
            receipt.setDocNum(docID);
            receipt.setStatus(ReceiptStatus.APPLICATION_SENT);
            receipt.setDocDate(date != null ? date.getTime() : ZonedDateTime.now().toEpochSecond());
            receipt.setSentToDCTime(ZonedDateTime.now());

            Row companyRow = sheet.getRow(5);
            Cell companyCell = companyRow.getCell(1);
            String stringCellValue = companyCell.getStringCellValue();
            String[] split = stringCellValue.trim().split("\\s+");
            String companyName = split[1] + " " + split[2].replace(',',' ');
            Company receiver = companyRepository.findByName(companyName.trim());
            if (receiver != null) {
                receipt.setAddress(receiver.getAddress());
                receipt.setReceiver(receiver);
            }

            receipt = receiptRepository.save(receipt);

            for (int i = 8; sheet.getRow(i).getCell(1).getNumericCellValue() != 0; i++) {
                Row row = sheet.getRow(i);
                String sapCode = String.valueOf(((int) row.getCell(2).getNumericCellValue()));
                sapCode = "00000000000" + sapCode;
                String name = row.getCell(5).getStringCellValue();
                double quantity = row.getCell(7).getNumericCellValue();

                Product product = productRepository.findFirstBySapCode(sapCode);

                if (product == null) {
                    Product newProduct = new Product();
                    newProduct.setName(name);
                    newProduct.setSapCode(sapCode);
                    newProduct.setUom("PCE");
                    newProduct.setSapType("HAWA");
                    product = productRepository.save(newProduct);
                }

                if (quantity > 1) {
                    int qty = ((int) quantity);
                    for (int j = 0; j < qty; j++) {
                        productEntrySet.add(createProductEntry(receipt, company, product));
                    }
                } else {
                    productEntrySet.add(createProductEntry(receipt, company, product));
                }
            }

            productEntryRepository.save(productEntrySet);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return receipt;
    }

    private ProductEntry createProductEntry(Receipt receipt, Company company, Product product) {
        ProductEntry productEntry = new ProductEntry();
        productEntry.setProduct(product);
        productEntry.setStatus(ReceiptStatus.NEW);
        productEntry.setCompany(company);
        productEntry.setCancelled(false);
        productEntry.setDefectFlag(DefectFlag.WELL);
        productEntry.setQty(BigDecimal.ONE);
        productEntry.setReceipt(receipt);
        productEntry.setAddress(receipt.getAddress());
        return productEntry;
    }
}
