package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.AddressDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "district.id", target = "districtId")
    @Mapping(source = "district.name", target = "districtName")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.firstName", target = "clientFirstName")
    AddressDTO addressToAddressDTO(Address address);

    List<AddressDTO> addressesToAddressDTOs(List<Address> addresses);

    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "districtId", target = "district")
    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "receipts", ignore = true)
    Address addressDTOToAddress(AddressDTO addressDTO);

    List<Address> addressDTOsToAddresses(List<AddressDTO> addressDTOs);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }

    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
