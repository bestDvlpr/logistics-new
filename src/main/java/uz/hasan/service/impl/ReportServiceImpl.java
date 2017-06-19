package uz.hasan.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.pojos.criteria.CustomCompany;
import uz.hasan.domain.pojos.criteria.CustomDistrict;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.*;
import uz.hasan.repository.CompanyRepository;
import uz.hasan.repository.ReceiptRepository;
import uz.hasan.repository.ReportRepository;
import uz.hasan.service.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author: hasan @date: 6/9/17.
 */

/**
 * Service Implementation for managing Report.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;
    private final ReceiptRepository receiptRepository;
    private final CompanyRepository companyRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ReceiptRepository receiptRepository, CompanyRepository companyRepository) {
        this.reportRepository = reportRepository;
        this.receiptRepository = receiptRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Generate a generic report page.
     *
     * @param criteria the entity to filter result by
     * @param pageable the entity to make result pageable
     * @return the persisted entity list
     */
    @Override
    public Page<ProductDeliveryReport> getGenericReport(DeliveryReportCriteria criteria, Pageable pageable) {
        criteria = checkCriteriaToNull(criteria);
        String limit = "null";
        String offset = "null";
        if (pageable != null) {
            limit = String.valueOf(pageable.getPageSize());
            offset = String.valueOf(pageable.getPageNumber());
        }
        List<ProductDeliveryReport> reports = reportRepository.pagedOverallReport(criteria.getStartDate(), criteria.getEndDate(), criteria.getCompanyName(), criteria.getDistrictName(), limit, offset);
        Long count = receiptRepository.countByDocDateAndCompanyAndRegion(criteria.getStartDate(), criteria.getEndDate(), criteria.getCompanyName(), criteria.getDistrictName());
        return new PageImpl<>(reports, pageable, count);
    }

    /**
     * Generate a generic report list.
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    @Override
    public List<ProductDeliveryReport> getGenericReport(DeliveryReportCriteria criteria) {
        criteria = checkCriteriaToNull(criteria);
        return reportRepository.overallReport(criteria.getStartDate(), criteria.getEndDate(), criteria.getCompanyName(), criteria.getDistrictName());
    }

    /**
     * Generate a generic report list and export them to MS-Excel file.
     *
     * @param criteria the entity to filter result by
     * @param response the entity to give generated file to
     */
    @Override
    public void exportGenericReport(DeliveryReportCriteria criteria, HttpServletResponse response) {
        List<ProductDeliveryReport> reports = this.getGenericReport(criteria);

        String fileName = "report.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();

        List<HashMap<String, String>> hashMapList = createRawData(reports);

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            XSSFSheet sheet = workbook.createSheet();
            int rowNum = 1;

            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Arial");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            CellStyle style = workbook.createCellStyle();
            style.setFont(font);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderBottom(CellStyle.BORDER_THIN);

            Font font2 = workbook.createFont();
            font2.setFontHeightInPoints((short) 10);
            font2.setFontName("Arial");

            CellStyle style2 = workbook.createCellStyle();
            style2.setFont(font2);
            style2.setBorderTop(CellStyle.BORDER_THIN);
            style2.setBorderLeft(CellStyle.BORDER_THIN);
            style2.setBorderRight(CellStyle.BORDER_THIN);
            style2.setBorderBottom(CellStyle.BORDER_THIN);

            Row initialRow = sheet.createRow(0);
            Cell companyName = initialRow.createCell(0);
            companyName.setCellStyle(style);
            companyName.setCellValue("Компания");
            Cell date = initialRow.createCell(1);
            date.setCellStyle(style);
            date.setCellValue("Дата");
            Cell id = initialRow.createCell(2);
            id.setCellStyle(style);
            id.setCellValue("Номер чека");
            Cell productName = initialRow.createCell(3);
            productName.setCellStyle(style);
            productName.setCellValue("Товары");
            Cell productQty = initialRow.createCell(4);
            productQty.setCellStyle(style);
            productQty.setCellValue("Количество");
            Cell docDate = initialRow.createCell(5);
            docDate.setCellStyle(style);
            docDate.setCellValue("Дата чека");
            Cell client = initialRow.createCell(6);
            client.setCellStyle(style);
            client.setCellValue("Клиент");
            Cell phoneNumber = initialRow.createCell(7);
            phoneNumber.setCellStyle(style);
            phoneNumber.setCellValue("Номер телефона");
            Cell districtName = initialRow.createCell(8);
            districtName.setCellStyle(style);
            districtName.setCellValue("Район");
            Cell address = initialRow.createCell(9);
            address.setCellStyle(style);
            address.setCellValue("Адрес");
            Cell carModel = initialRow.createCell(10);
            carModel.setCellStyle(style);
            carModel.setCellValue("Автомобиль");
            Cell carNumber = initialRow.createCell(11);
            carNumber.setCellStyle(style);
            carNumber.setCellValue("Номер авт.");
            /*Cell driver = initialRow.createCell(12);
            driver.setCellStyle(style);
            driver.setCellValue("Водитель");*/
            Cell sentToDCTime = initialRow.createCell(12);
            sentToDCTime.setCellStyle(style);
            sentToDCTime.setCellValue("Время отправки");
            Cell deliveredTime = initialRow.createCell(13);
            deliveredTime.setCellStyle(style);
            deliveredTime.setCellValue("Доставленное время");
            Cell deliveryTookTime = initialRow.createCell(14);
            deliveryTookTime.setCellStyle(style);
            deliveryTookTime.setCellValue("Потраченное время");

            for (HashMap<String, String> hashMap : hashMapList) {

                int cellNum = 0;

                Row row = sheet.createRow(rowNum++);

                for (String key : hashMap.keySet()) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellStyle(style2);
                    cell.setCellValue(hashMap.get(key));
                }
            }

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Generate a generic report list and export them to MS-Excel file.
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    @Override
    public List<DeliveryCountByCompany> getDeliveryCountByCompanyByStatus(DeliveryReportCriteria criteria) {

        List<CustomCompany> companies;

        ZonedDateTime start;
        ZonedDateTime end;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (criteria == null ||
            criteria.getStartDate() == null ||
            criteria.getStartDate().isEmpty() ||
            criteria.getStartDate().equals("null") ||
            criteria.getEndDate() == null ||
            criteria.getEndDate().isEmpty() ||
            criteria.getEndDate().equals("null")) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

            Date firstDate = calendar.getTime();
            Date today = new Date();

            start = ZonedDateTime.ofInstant(firstDate.toInstant(), ZoneId.systemDefault());
            end = ZonedDateTime.now();

            criteria.setStartDate(simpleDateFormat.format(firstDate));
            criteria.setEndDate(simpleDateFormat.format(today));
        } else {
            Date firstDate = null;
            try {
                firstDate = simpleDateFormat.parse(criteria.getStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            start = ZonedDateTime.ofInstant(firstDate.toInstant(), ZoneId.systemDefault());
            end = ZonedDateTime.now();
        }

        companies = companyRepository.customCompany(criteria.getStatus().name(), criteria.getStartDate(), criteria.getEndDate());

        Map<String, Long> companyWithDeliveryCounts = new HashMap<>();

        for (CustomCompany company : companies) {
            companyWithDeliveryCounts.put(company.getName(), receiptRepository.getCountByStatusAndCompanyIdNumber(criteria.getStatus(), company.getIdNumber(), start, end));
        }

        List<DeliveryCountByCompany> list = new ArrayList<>();

        Long all = 0L;

        for (String key : companyWithDeliveryCounts.keySet()) {
            Long count = companyWithDeliveryCounts.get(key);
            list.add(new DeliveryCountByCompany(null, key, count));
            all += count;
        }

        list.add(new DeliveryCountByCompany(null, null, all));

        return list;
    }

    /**
     * Generate a generic report list.
     *
     * @param criteria the entity to filter result by
     * @return the persisted entity list
     */
    @Override
    public List<CompanyDeliveryCounts> countsByCompany(DeliveryReportCriteria criteria) {
        criteria = checkCriteriaToNull(criteria);
        List<DeliveryCountByCompany> list = reportRepository.countByCompany(criteria.getStartDate(), criteria.getEndDate(), criteria.getCompany().getName(), criteria.getDistrict().getName());
        Set<String> companyNames = new HashSet<>();

        for (DeliveryCountByCompany countByCompany : list) {
            companyNames.add(countByCompany.getCompanyName());
        }

        List<CompanyDeliveryCounts> result = new ArrayList<>();

        for (String companyName : companyNames) {

            Set<CountByDate> countByDates = new LinkedHashSet<>();

            Map<String, Long> map = new LinkedHashMap<>();

            for (DeliveryCountByCompany countByCompany : list) {


                if (companyName.equals(countByCompany.getCompanyName())) {
                    map.put(countByCompany.getDate(), countByCompany.getCount());
                }

                map.putIfAbsent(countByCompany.getDate(), 0L);

            }

            for (String key : map.keySet()) {
                CountByDate countByDate = new CountByDate(key, map.get(key));
                countByDates.add(countByDate);
            }

            CompanyDeliveryCounts companyWithCount = new CompanyDeliveryCounts(companyName, countByDates);

            result.add(companyWithCount);
        }

        return result;
    }

    @Override
    public List<DeliveryCountByCompanyByDistrict> countByCompanyByStatus(DeliveryReportCriteria criteria) {
        String startDate = (criteria.getStartDate() != null && !criteria.getStartDate().isEmpty()) ? criteria.getStartDate() : "null";
        String endDate = (criteria.getEndDate() != null && !criteria.getEndDate().isEmpty()) ? criteria.getEndDate() : "null";
        String status = criteria.getStatus() != null ? criteria.getStatus().name() : "null";
        String district = criteria.getDistrictName() != null ? criteria.getDistrictName() : "null";
        String companyName = criteria.getCompanyName() != null ? criteria.getCompanyName() : "null";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (startDate.equals("null") || endDate.equals("null")) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

            Date firstDate = calendar.getTime();
            Date today = new Date();

            startDate = simpleDateFormat.format(firstDate);
            endDate = simpleDateFormat.format(today);
        }

        List<ByDistrict> countByDistrictByCompanies = reportRepository.countByCompanyByDistrictByStatus(startDate, endDate, companyName, district, status);
        Set<String> districtNames = new HashSet<>();

        for (ByDistrict countByCompany : countByDistrictByCompanies) {
            if (countByCompany.getDistrictName() != null && !countByCompany.getDistrictName().isEmpty()) {
                districtNames.add(countByCompany.getDistrictName());
            } else if (countByCompany.getDistrictName2() != null && !countByCompany.getDistrictName2().isEmpty()) {
                districtNames.add(countByCompany.getDistrictName2());
            }
        }

        List<DeliveryCountByCompanyByDistrict> byCompanies = new LinkedList<>();
        for (String districtName : districtNames) {
            List<ByDistrict> countByCompanies = new LinkedList<>();
            for (ByDistrict countByCompany : countByDistrictByCompanies) {
                if (districtName.equals(countByCompany.getDistrictName())) {
                    countByCompanies.add(countByCompany);
                }
            }
            byCompanies.add(new DeliveryCountByCompanyByDistrict(districtName, countByCompanies));
        }

        return byCompanies;
    }

    private List<HashMap<String, String>> createRawData(List<ProductDeliveryReport> deliveryReports) {
        List<HashMap<String, String>> result = new ArrayList<>();

        for (ProductDeliveryReport report : deliveryReports) {
            HashMap<String, String> hashMap = new LinkedHashMap<>();
            hashMap.put("companyName", report.getCompanyName());
            hashMap.put("date", report.getDate());
            hashMap.put("id", String.valueOf(report.getId()));
            hashMap.put("productName", report.getProductName());
            hashMap.put("productQty", String.valueOf(report.getProductQty()));
            hashMap.put("docDate", report.getDocDate());
            hashMap.put("client", report.getClientFirstName() + " " + report.getClientLastName());
            hashMap.put("phoneNumber", report.getPhoneNumber());
            hashMap.put("districtName", report.getDistrictName());
            hashMap.put("address", report.getAddress());
            hashMap.put("carModel", report.getCarModel());
            hashMap.put("carNumber", report.getCarNumber());
//            hashMap.put("driver", report.getDriverFirstName() + " " + report.getDriverLastName());
            hashMap.put("sentToDCTime", report.getSentToDCTime());
            hashMap.put("deliveredTime", report.getDeliveredTime());
            hashMap.put("deliveryTookTime", report.getDeliveryTookTime());
            result.add(hashMap);
        }

        return result;
    }

    private DeliveryReportCriteria checkCriteriaToNull(DeliveryReportCriteria criteria) {
        if (criteria == null || (criteria.getDistrict() == null && criteria.getCompany() == null && criteria.getStartDate() == null && criteria.getEndDate() == null)) {
            criteria = new DeliveryReportCriteria("null", "null", new CustomCompany(null, "null"), new CustomDistrict(null, "null"));
        }
        if (criteria.getStartDate() == null || criteria.getEndDate() == null) {
            criteria.setStartDate("null");
            criteria.setEndDate("null");
        }
        if (criteria.getCompany() == null) {
            criteria.setCompany(new CustomCompany(null, "null"));
        }
        if (criteria.getDistrict() == null) {
            criteria.setDistrict(new CustomDistrict(null, "null"));
        }
        return criteria;
    }

}
