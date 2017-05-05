package uz.hasan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Service Interface for managing Receipt.
 */
public interface UploadService {

    ReceiptProductEntriesDTO createDisplacementApplication(MultipartFile file);
}
