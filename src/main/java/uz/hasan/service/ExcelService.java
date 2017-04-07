package uz.hasan.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.ProductEntry;
import uz.hasan.domain.enumeration.XDocTemplate;
import uz.hasan.service.dto.ShopDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing Shop.
 */
public interface ExcelService {

    HSSFWorkbook createInvoiceSheet(HSSFWorkbook workbook, List<ProductEntry> productEntries);

    void generateDocx(XDocTemplate template, String docNumber, Map<String, Object> map, HttpServletResponse response);
}
