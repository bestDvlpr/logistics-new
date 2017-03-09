package uz.hasan.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the Client entity.
 */
public class ClientAndAddressesDTO extends ClientDTO implements Serializable {

    private List<AddressDTO> addressDTOS;

    public List<AddressDTO> getAddressDTOS() {
        return addressDTOS;
    }

    public void setAddressDTOS(List<AddressDTO> addressDTOS) {
        this.addressDTOS = addressDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClientAndAddressesDTO that = (ClientAndAddressesDTO) o;

        return addressDTOS != null ? addressDTOS.equals(that.addressDTOS) : that.addressDTOS == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (addressDTOS != null ? addressDTOS.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientAndAddressesDTO{" +
            "addressDTOS=" + addressDTOS +
            '}';
    }
}
