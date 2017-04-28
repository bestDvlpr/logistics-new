package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.ProductEntryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProductEntry and its DTO ProductEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, UserMapper.class, })
public interface ProductEntryMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.id", target = "product.id")
    @Mapping(source = "product.name", target = "product.name")
    @Mapping(source = "sellerID.id", target = "sellerIDId")
    @Mapping(source = "sellerID.sellerID", target = "sellerIDSellerID")
    @Mapping(source = "receipt.id", target = "receiptId")
    @Mapping(source = "receipt.docNum", target = "receiptDocNum")
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.lastName", target = "driverLastName")
    @Mapping(source = "attachedCar.id", target = "attachedCarId")
    @Mapping(source = "attachedCar.number", target = "attachedCarNumber")
    @Mapping(source = "address.id", target = "address.id")
    @Mapping(source = "address.streetAddress", target = "address.streetAddress")
    @Mapping(source = "address.region.name", target = "address.regionName")
    @Mapping(source = "address.region.id", target = "address.regionId")
    @Mapping(source = "address.district.name", target = "address.districtName")
    @Mapping(source = "address.district.id", target = "address.districtId")
    @Mapping(source = "attachedToDriverBy.id", target = "attachedToDriverById")
    @Mapping(source = "attachedToDriverBy.login", target = "attachedToDriverByLogin")
    @Mapping(source = "deliveryItemsSentBy.id", target = "deliveryItemsSentById")
    @Mapping(source = "deliveryItemsSentBy.login", target = "deliveryItemsSentByLogin")
    @Mapping(source = "markedAsDeliveredBy.id", target = "markedAsDeliveredById")
    @Mapping(source = "markedAsDeliveredBy.login", target = "markedAsDeliveredByLogin")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    ProductEntryDTO productEntryToProductEntryDTO(ProductEntry productEntry);

    List<ProductEntryDTO> productEntriesToProductEntryDTOs(List<ProductEntry> productEntries);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "sellerIDId", target = "sellerID")
    @Mapping(source = "receiptId", target = "receipt")
    @Mapping(source = "driverId", target = "driver")
    @Mapping(source = "attachedCarId", target = "attachedCar")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "attachedToDriverById", target = "attachedToDriverBy")
    @Mapping(source = "deliveryItemsSentById", target = "deliveryItemsSentBy")
    @Mapping(source = "markedAsDeliveredById", target = "markedAsDeliveredBy")
    @Mapping(source = "companyId", target = "company")
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

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
