package uz.hasan.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Client entity.
 */
public class ClientAndAddressesDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private List<AddressDTO> addressDTOS;

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

    public List<AddressDTO> getAddressDTOS() {
        return addressDTOS;
    }

    public void setAddressDTOS(List<AddressDTO> addressDTOS) {
        this.addressDTOS = addressDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientAndAddressesDTO clientDTO = (ClientAndAddressesDTO) o;

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
            '}';
    }
}
