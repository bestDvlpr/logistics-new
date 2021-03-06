package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.SellerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Seller and its DTO SellerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SellerMapper {

    SellerDTO sellerToSellerDTO(Seller seller);

    List<SellerDTO> sellersToSellerDTOs(List<Seller> sellers);

    Seller sellerDTOToSeller(SellerDTO sellerDTO);

    List<Seller> sellerDTOsToSellers(List<SellerDTO> sellerDTOs);
}
