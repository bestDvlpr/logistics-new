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
    ProductEntryDTO productEntryToProductEntryDTO(ProductEntry productEntry);

    List<ProductEntryDTO> productEntriesToProductEntryDTOs(List<ProductEntry> productEntries);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "sellerIDId", target = "sellerID")
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
}
