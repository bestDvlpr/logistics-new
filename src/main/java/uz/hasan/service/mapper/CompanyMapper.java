package uz.hasan.service.mapper;

import uz.hasan.domain.*;
import uz.hasan.service.dto.CompanyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.streetAddress", target = "addressStreetAddress")
    @Mapping(source = "address.region.id", target = "address.regionId")
    @Mapping(source = "address.district.id", target = "address.districtId")
    @Mapping(source = "address.country.id", target = "address.countryId")
    @Mapping(source = "address.region.name", target = "address.regionName")
    @Mapping(source = "address.district.name", target = "address.districtName")
    @Mapping(source = "address.country.name", target = "address.countryName")
    @Mapping(source = "address.streetAddress", target = "address.streetAddress")
    @Mapping(source = "address.id", target = "address.id")
    CompanyDTO companyToCompanyDTO(Company company);

    List<CompanyDTO> companiesToCompanyDTOs(List<Company> companies);

    @Mapping(source = "addressId", target = "address")
    Company companyDTOToCompany(CompanyDTO companyDTO);

    List<Company> companyDTOsToCompanies(List<CompanyDTO> companyDTOs);

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
