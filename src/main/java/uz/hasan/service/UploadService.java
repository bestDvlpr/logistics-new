package uz.hasan.service;

import org.springframework.web.multipart.MultipartFile;
import uz.hasan.domain.enumeration.DocType;
import uz.hasan.service.dto.ReceiptProductEntriesDTO;

/**
 * Service Interface for managing Receipt.
 */
public interface UploadService {

    ReceiptProductEntriesDTO createDisplacementApplication(MultipartFile file);

    ReceiptProductEntriesDTO createApplicationFromFile(MultipartFile file, DocType docType, String companyId);
}
