package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Shop entity.
 */
public class ShopDTO implements Serializable {

    private Long id;

    @NotNull
    private String shopId;

    @NotNull
    private String name;

    private Long addressId;

    private String addressStreetAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddressStreetAddress() {
        return addressStreetAddress;
    }

    public void setAddressStreetAddress(String addressStreetAddress) {
        this.addressStreetAddress = addressStreetAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShopDTO shopDTO = (ShopDTO) o;

        if ( ! Objects.equals(id, shopDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShopDTO{" +
            "id=" + id +
            ", shopId='" + shopId + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
