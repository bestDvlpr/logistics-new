package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.ProductEntryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProductEntry and its DTO ProductEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductEntryMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "sellerID.id", target = "sellerIDId")
    @Mapping(source = "sellerID.sellerID", target = "sellerIDSellerID")
    @Mapping(source = "receipt.id", target = "receiptId")
    @Mapping(source = "receipt.docNum", target = "receiptDocNum")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.lastName", target = "driverLastName")
    ProductEntryDTO productEntryToProductEntryDTO(ProductEntry productEntry);

    List<ProductEntryDTO> productEntriesToProductEntryDTOs(List<ProductEntry> productEntries);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "sellerIDId", target = "sellerID")
    @Mapping(source = "receiptId", target = "receipt")
    @Mapping(source = "driverId", target = "driver")
    ProductEntry productEntryDTOToProductEntry(ProductEntryDTO productEntryDTO);

    List<ProductEntry> productEntryDTOsToProductEntries(List<ProductEntryDTO> productEntryDTOs);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default Seller sellerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }

    default Receipt receiptFromId(Long id) {
        if (id == null) {
            return null;
        }
        Receipt receipt = new Receipt();
        receipt.setId(id);
        return receipt;
    }

    default Driver driverFromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}
