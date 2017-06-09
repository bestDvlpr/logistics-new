package uz.hasan.service.impl;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.pojos.criteria.CustomCompany;
import uz.hasan.domain.pojos.criteria.CustomDistrict;
import uz.hasan.domain.pojos.criteria.DeliveryReportCriteria;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;
import uz.hasan.repository.ReportRepository;
import uz.hasan.service.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Generate a generic report.
     *
     * @param criteria the entity to use for filter
     * @return the persisted entity
     */
    @Override
    public List<ProductDeliveryReport> getGenericReport(DeliveryReportCriteria criteria) {
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
        return reportRepository.overallReport(criteria.getStartDate(), criteria.getEndDate(), criteria.getCompanyName(), criteria.getDistrictName());
    }

    /**
     * Generate a generic report and return it as file.
     *
     * @param criteria the entity to use for filter
     * @param response the entity to save file to
     */
    @Override
    public void exportGenericReport(DeliveryReportCriteria criteria, HttpServletResponse response) {

        List<ProductDeliveryReport> reports = this.getGenericReport(criteria);

        String fileName = "report.xlsx";
        HSSFWorkbook workbook = new HSSFWorkbook();

        List<HashMap<String, String>> hashMapList = createRawData(reports);

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            HSSFSheet sheet = workbook.createSheet();
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
            Cell driver = initialRow.createCell(12);
            driver.setCellStyle(style);
            driver.setCellValue("Водитель");
            Cell sentToDCTime = initialRow.createCell(13);
            sentToDCTime.setCellStyle(style);
            sentToDCTime.setCellValue("Время отправки");
            Cell deliveredTime = initialRow.createCell(14);
            deliveredTime.setCellStyle(style);
            deliveredTime.setCellValue("Доставленное время");
            Cell deliveryTookTime = initialRow.createCell(15);
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
            hashMap.put("driver", report.getDriverFirstName() + " " + report.getDriverLastName());
            hashMap.put("sentToDCTime", report.getSentToDCTime());
            hashMap.put("deliveredTime", report.getDeliveredTime());
            hashMap.put("deliveryTookTime", report.getDeliveryTookTime());
            result.add(hashMap);
        }

        return result;
    }

}