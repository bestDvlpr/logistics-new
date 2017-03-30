package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.ShopDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Shop and its DTO ShopDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShopMapper {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetAddress", target = "addressStreetAddress")
    ShopDTO shopToShopDTO(Shop shop);

    List<ShopDTO> shopsToShopDTOs(List<Shop> shops);

    @Mapping(source = "addressId", target = "address")
    Shop shopDTOToShop(ShopDTO shopDTO);

    List<Shop> shopDTOsToShops(List<ShopDTO> shopDTOs);

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
