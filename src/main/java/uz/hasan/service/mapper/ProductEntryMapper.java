package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.ProductEntryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProductEntry and its DTO ProductEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductEntryMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.id", target = "product.id")
    @Mapping(source = "product.name", target = "product.name")
    @Mapping(source = "sellerID.id", target = "sellerIDId")
    @Mapping(source = "sellerID.sellerID", target = "sellerIDSellerID")
    @Mapping(source = "receipt.id", target = "receiptId")
    @Mapping(source = "receipt.docNum", target = "receiptDocNum")
    @Mapping(source = "receipt.docID", target = "receiptDocId")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.lastName", target = "driverLastName")
    @Mapping(source = "attachedCar.id", target = "attachedCarId")
    @Mapping(source = "attachedCar.number", target = "attachedCarNumber")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetAddress", target = "addressStreetAddress")
    @Mapping(source = "address.id", target = "address.id")
    @Mapping(source = "address.streetAddress", target = "address.streetAddress")
    @Mapping(source = "address.country.id", target = "address.countryId")
    @Mapping(source = "address.country.name", target = "address.countryName")
    @Mapping(source = "address.region.id", target = "address.regionId")
    @Mapping(source = "address.region.name", target = "address.regionName")
    @Mapping(source = "address.city.id", target = "address.cityId")
    @Mapping(source = "address.city.name", target = "address.cityName")
    @Mapping(source = "address.district.id", target = "address.districtId")
    @Mapping(source = "address.district.name", target = "address.districtName")
    @Mapping(source = "shop.id", target = "shopId")
    @Mapping(source = "shop.name", target = "shopName")
    ProductEntryDTO productEntryToProductEntryDTO(ProductEntry productEntry);

    List<ProductEntryDTO> productEntriesToProductEntryDTOs(List<ProductEntry> productEntries);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "sellerIDId", target = "sellerID")
    @Mapping(source = "receiptId", target = "receipt")
    @Mapping(source = "driverId", target = "driver")
    @Mapping(source = "attachedCarId", target = "attachedCar")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "shopId", target = "shop.id")
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

    default Car carFromId(Long id) {
        if (id == null) {
            return null;
        }
        Car car = new Car();
        car.setId(id);
        return car;
    }

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
