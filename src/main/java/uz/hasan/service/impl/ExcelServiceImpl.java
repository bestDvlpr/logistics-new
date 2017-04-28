package uz.hasan.service.impl;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.hasan.domain.*;
import uz.hasan.domain.enumeration.XDocTemplate;
import uz.hasan.service.ExcelService;
import uz.hasan.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: hasan @date: 4/5/17.
 */
@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    private final Logger log = LoggerFactory.getLogger(ExcelServiceImpl.class);
    private final UserService userService;

    public ExcelServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HSSFWorkbook createInvoiceSheet(HSSFWorkbook workbook, List<ProductEntry> productEntries) {
        /*-- Create sheet --*/
        HSSFSheet sheet1 = workbook.createSheet();

        /* create cell styles*/
        Font fontBig = workbook.getFontAt((short) 0);
        fontBig.setFontHeightInPoints((short) 12);
        fontBig.setFontName("Tahoma");
        fontBig.setItalic(false);
        fontBig.setBold(false);

        Font fontSmall = workbook.getFontAt((short) 2);
        fontSmall.setFontHeightInPoints((short) 12);
        fontSmall.setFontName("Tahoma");
        fontSmall.setItalic(false);
        fontSmall.setBold(false);

        CellStyle primaryStyle = workbook.getCellStyleAt(0);
        CellStyle borderedStyle = workbook.getCellStyleAt(2);

        Map<String, Object> properties = new HashMap<>();
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);


        /*-- Create first row and its cells --*/
        Row firstRow = sheet1.createRow(0);
        Cell cell00 = firstRow.createCell(0);
        cell00.setCellValue(userService.getUserWithAuthorities().getCompany().getName());
        Cell cell01 = firstRow.createCell(1);
        Cell cell02 = firstRow.createCell(2);
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

        /*-- Create second row and its cells --*/
        Row secondRow = sheet1.createRow(1);
        Cell cell10 = secondRow.createCell(0);
        cell10.setCellValue("ЕДИНЫЙ ДОСТАВОЧНЫЙ ТАЛОН № ___");
        Cell cell11 = secondRow.createCell(1);
        Cell cell12 = secondRow.createCell(2);
        sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));

        /*-- Create third row and its cells --*/
        Row thirdRow = sheet1.createRow(2);
        Cell cell20 = thirdRow.createCell(0);
        cell20.setCellValue("ДАТА _____________");
        Cell cell21 = thirdRow.createCell(1);
        Cell cell22 = thirdRow.createCell(2);
        sheet1.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));

        /*-- Create forthRow row and its cells --*/
        Row forthRow = sheet1.createRow(3);
        Cell numberCell = forthRow.createCell(0);
        numberCell.setCellValue("#");
        Cell productNameCell = forthRow.createCell(1);
        productNameCell.setCellValue("Наименование товара");
        Cell modelCell = forthRow.createCell(2);
        modelCell.setCellValue("Адрес");

        /*-- Create rows and their cells for each product --*/
        int rowNum = 4;
        for (ProductEntry entry : productEntries) {
            Row row = sheet1.createRow(rowNum++);
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(entry.getProduct().getName());
            Cell cell3 = row.createCell(2);
            Address address = entry.getAddress();
            cell3.setCellValue(address.getRegion().getName().concat(", ").concat(address.getStreetAddress()));
        }

        /*-- Create row and its cells for receipt doc num --*/
        Row rowDocNum = sheet1.createRow(rowNum++);
        Cell cellDocNum = rowDocNum.createCell(1);
        cellDocNum.setCellValue("На основании чека №");
        Cell cellDocNumVal = rowDocNum.createCell(2);
        Receipt receipt = productEntries.get(0).getReceipt();
        cellDocNumVal.setCellValue(receipt.getDocNum());

        /*-- Create row and its cells for customer --*/
        Row rowCustomer = sheet1.createRow(rowNum++);
        Cell cellCustomer = rowCustomer.createCell(1);
        cellCustomer.setCellValue("Ф.И.О. покупателья");
        Cell cellCustomerValues = rowCustomer.createCell(2);
        Client client = receipt.getClient();
        cellCustomerValues.setCellValue(client != null ? client.getFirstName().concat(" ").concat(client.getLastName()) : "");

        /*-- Create row and its cells for delivery date--*/
        Row rowDeliveryDate = sheet1.createRow(rowNum++);
        Cell cellDate = rowDeliveryDate.createCell(1);
        cellDate.setCellValue("Дата доставки");
        Cell cellDateValues = rowDeliveryDate.createCell(2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        cellDateValues.setCellValue(simpleDateFormat.format(new Date()));

        /*-- Create row and its cells for delivery time range--*/
        Row rowDeliveryTime = sheet1.createRow(rowNum++);
        Cell cellDeliveryTime = rowDeliveryTime.createCell(1);
        cellDeliveryTime.setCellValue("Время доставки");
        Cell cellDeliveryTimeValues = rowDeliveryTime.createCell(2);
        cellDeliveryTimeValues.setCellValue(receipt.getFromTime().concat(" - ".concat(receipt.getToTime())));

        /*-- Create row and its cells for contact details of the client--*/
        Row rowClientPhone = sheet1.createRow(rowNum++);
        Cell cellClientPhone = rowClientPhone.createCell(1);
        cellClientPhone.setCellValue("Контактные данные");
        Cell cellClientPhoneValues = rowClientPhone.createCell(2);
        List<PhoneNumber> phoneNumbers = null;
        if (client != null && !client.getPhoneNumbers().isEmpty()) {
            phoneNumbers = new ArrayList<>(client.getPhoneNumbers());
        }
        cellClientPhoneValues.setCellValue(phoneNumbers != null ? phoneNumbers.get(0).getNumber() : "");

        /*-- Create row and its cells for delivery organization--*/
        Row rowDeliveryOrg = sheet1.createRow(rowNum++);
        Cell cellDeliveryOrg = rowDeliveryOrg.createCell(1);
        cellDeliveryOrg.setCellValue("Перевозчик");
        Cell cellDeliveryOrgValues = rowDeliveryOrg.createCell(2);
        cellDeliveryOrgValues.setCellValue("OOO \"ART INVENTION\"");

        /*-- Create row and its cells for seller--*/
        Row rowSeller = sheet1.createRow(rowNum++);
        Cell cellSeller = rowSeller.createCell(1);
        cellSeller.setCellValue("Ф.И.О. продавца и подпись");
        Cell cellSellerValues = rowSeller.createCell(2);
        Seller seller = productEntries.get(0).getSellerID();
        cellSellerValues.setCellValue(seller != null ? seller.getSellerName() : "");

        /*-- Create row and its cells for delivery operator--*/
        /*Row rowDeliveryOperator = sheet1.createRow(rowNum++);
        Cell cellDeliveryOperator = rowDeliveryOperator.createCell(1);
        cellDeliveryOperator.setCellValue("На основании чека №");
        Cell cellDeliveryOperatorValues = rowDeliveryOperator.createCell(2);
        cellDeliveryOperatorValues.setCellValue(receipt);*/

        /*-- Create row and its cells for driver--*/
        Row rowDriver = sheet1.createRow(rowNum++);
        Cell cellDriver = rowDriver.createCell(1);
        cellDriver.setCellValue("Ф.И.О. водителья");
        Cell cellDriverValues = rowDriver.createCell(2);
        List<Driver> drivers = new ArrayList<>(productEntries.get(0).getAttachedCar().getDrivers());
        Driver driver = null;
        if (!drivers.isEmpty()) {
            driver = drivers.get(0);
        }
        cellDriverValues.setCellValue(driver != null ? driver.getFirstName().concat(" ").concat(driver.getLastName()) : null);

        /*-- Create row and its cells for car number--*/
        Row rowCarNumber = sheet1.createRow(rowNum++);
        Cell cellCarNumber = rowCarNumber.createCell(1);
        cellCarNumber.setCellValue("Гос номер автомашины");
        Cell cellCarNumberValues = rowCarNumber.createCell(2);
        Car car = productEntries.get(0).getAttachedCar();
        cellCarNumberValues.setCellValue(car != null ? car.getNumber() : "");

        /*-- Create row and its cells for client signature--*/
        Row rowClientSignature = sheet1.createRow(rowNum++);
        Cell cellClientSignature = rowClientSignature.createCell(1);
        cellClientSignature.setCellValue("Подпись покупателья");

        /*-- Create row and its cells for product dispatcher org--*/
        Row rowDispatcherOrg = sheet1.createRow(rowNum++);
        Cell cellDispatcherOrg = rowDispatcherOrg.createCell(1);
        cellDispatcherOrg.setCellValue("Отправитель товара");
        Cell cellDispatcherOrgValue = rowDispatcherOrg.createCell(2);
        cellDispatcherOrgValue.setCellValue(receipt.getCompany().getName());

        rowNum = rowNum + 2;
        /*- Delivery regulations -*/
        int rownumIncremented = rowNum++;
        Row rowDeliveryRegulations = sheet1.createRow(rownumIncremented);
        Cell cellDeliveryRegulations = rowDeliveryRegulations.createCell(0);
        cellDeliveryRegulations.setCellValue("УСЛОВИЯ ДОСТАВКИ:");
        sheet1.addMergedRegion(new CellRangeAddress(rownumIncremented, rownumIncremented, 0, 2));

        int rownumIncremented1 = rowNum++;
        Row rowDeliveryRegulation1 = sheet1.createRow(rownumIncremented1);
        Cell cellDeliveryRegulation1 = rowDeliveryRegulation1.createCell(0);
        cellDeliveryRegulation1.setCellValue("1. Доставка осуществляется в течении 48 часов с момента оплаты");
        sheet1.addMergedRegion(new CellRangeAddress(rownumIncremented1, rownumIncremented1, 0, 2));

        int rownumIncremented2 = rowNum++;
        Row rowDeliveryRegulation2 = sheet1.createRow(rownumIncremented2);
        Cell cellDeliveryRegulation2 = rowDeliveryRegulation2.createCell(0);
        cellDeliveryRegulation2.setCellValue("2. Доставка организируется только по территории Ташкента");
        sheet1.addMergedRegion(new CellRangeAddress(rownumIncremented2, rownumIncremented2, 0, 2));

        int rownumIncremented3 = rowNum++;
        Row rowDeliveryRegulation3 = sheet1.createRow(rownumIncremented3);
        Cell cellDeliveryRegulation3 = rowDeliveryRegulation3.createCell(0);
        cellDeliveryRegulation3.setCellValue("3. Доставка товара до подъезда или до двери участок покупателья");
        sheet1.addMergedRegion(new CellRangeAddress(rownumIncremented3, rownumIncremented3, 0, 2));

        int rownumIncremented4 = rowNum++;
        Row rowDeliveryRegulation4 = sheet1.createRow(rownumIncremented4);
        Cell cellDeliveryRegulation4 = rowDeliveryRegulation4.createCell(0);
        cellDeliveryRegulation4.setCellValue("Вопросы и предложения по номеру: (99871) 202-22-12");
        sheet1.addMergedRegion(new CellRangeAddress(rownumIncremented4, rownumIncremented4, 0, 2));

        int rownumIncremented5 = rowNum++;
        Row rowDeliveryRegulation5 = sheet1.createRow(rownumIncremented5);
        Cell cellDeliveryRegulation5 = rowDeliveryRegulation5.createCell(0);
        cellDeliveryRegulation5.setCellValue("С Условием доставки ознакомлен(а)");
        sheet1.addMergedRegion(new CellRangeAddress(rownumIncremented5, rownumIncremented5, 0, 2));

        while (sheet1.rowIterator().hasNext()) {
            Row row = sheet1.rowIterator().next();
            while (row.cellIterator().hasNext()) {
                Cell cell = row.cellIterator().next();
                CellUtil.setCellStyleProperties(cell, properties);
            }
        }

        return workbook;
    }

    @Override
    public void generateDocx(XDocTemplate template, String docNumber, Map<String, Object> map, HttpServletResponse response) {

        String language = LocaleContextHolder.getLocale().getLanguage();
        String fileName = language + File.separator + template.getName();

        InputStream in = getInputStream(response, template.getName());

        try {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            IContext context = report.createContext();

//            prepareUtils(context);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + ";charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + docNumber +"_"+ template.getName() + "\"");

            report.process(context, response.getOutputStream());

        } catch (IOException | XDocReportException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private InputStream getInputStream(HttpServletResponse response, String fileName) {
        String folderName = "doc";
        InputStream in = null;
        String homePathFile = System.getProperty("user.home") + File.separator + folderName + File.separator + fileName;
        String resourcePathFile = folderName + File.separator + fileName;
        try {
            in = new FileInputStream(homePathFile);
        } catch (FileNotFoundException e) {
            log.warn("File " + fileName + " not found in user home directory!");
            if (SystemUtils.IS_OS_LINUX) {
                in = ExcelServiceImpl.class.getResourceAsStream(File.separator + resourcePathFile);
            } else if (SystemUtils.IS_OS_WINDOWS) {
                in = ExcelServiceImpl.class.getClassLoader().getResourceAsStream(resourcePathFile);
            }
        }

        if (in == null) {
            String s = fileName + " not found!";
            printResponse(response, s);
            return null;
        }

        return in;
    }

    private void printResponse(HttpServletResponse response, String s) {
        response.setHeader("X-logisticsApp-alert", s);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("text/plain");
        try {
            response.getWriter().println(s);
        } catch (IOException e) {
            log.warn("Cannot write to response.");
        }
    }
}
