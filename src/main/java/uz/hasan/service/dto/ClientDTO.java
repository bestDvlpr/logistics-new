package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Client entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    @NotNull
    private String address;

    private Long cityId;

    private String cityName;

    private Long regionId;

    private String regionName;

    private Long streetId;

    private String streetName;

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
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long locationId) {
        this.streetId = locationId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String locationName) {
        this.streetName = locationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;

        if ( ! Objects.equals(id, clientDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
