package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Address entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    private String latitude;

    private String longitude;

    private Long countryId;

    private String countryName;

    private Long regionId;

    private String regionName;

    private Long cityId;

    private String cityName;

    private Long districtId;

    private String districtName;

    private Long clientId;

    private String clientStreetAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long locationId) {
        this.countryId = locationId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String locationName) {
        this.countryName = locationName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long locationId) {
        this.regionId = locationId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String locationName) {
        this.regionName = locationName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long locationId) {
        this.cityId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String locationName) {
        this.cityName = locationName;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long locationId) {
        this.districtId = locationId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String locationName) {
        this.districtName = locationName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientStreetAddress() {
        return clientStreetAddress;
    }

    public void setClientStreetAddress(String clientStreetAddress) {
        this.clientStreetAddress = clientStreetAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;

        if ( ! Objects.equals(id, addressDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            '}';
    }
}
