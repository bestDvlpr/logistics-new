package uz.multimafe.service.mapper;

import uz.multimafe.domain.*;
import uz.multimafe.service.dto.ProductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "seller.sellerName", target = "sellerSellerName")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Mapping(source = "sellerId", target = "seller")
    Product productDTOToProduct(ProductDTO productDTO);

    List<Product> productDTOsToProducts(List<ProductDTO> productDTOs);

    default Seller sellerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }
}
