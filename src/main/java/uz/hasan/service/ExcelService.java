package uz.hasan.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.hasan.domain.ProductEntry;
import uz.hasan.service.dto.ShopDTO;

import java.util.List;

/**
 * Service Interface for managing Shop.
 */
public interface ExcelService {

    HSSFWorkbook createInvoiceSheet(HSSFWorkbook workbook, List<ProductEntry> productEntries);
}
