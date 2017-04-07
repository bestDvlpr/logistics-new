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

    @Size(min = 16, max = 24)
    private String bankAccountNumber;

    private String bankName;

    private String bankBranchRegion;

    @Size(min = 5, max = 5)
    private String mfo;

    @Size(min = 9, max = 9)
    private String tin;

    private String okonx;

    private String oked;

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
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankBranchRegion() {
        return bankBranchRegion;
    }

    public void setBankBranchRegion(String bankBranchRegion) {
        this.bankBranchRegion = bankBranchRegion;
    }
    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }
    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }
    public String getOkonx() {
        return okonx;
    }

    public void setOkonx(String okonx) {
        this.okonx = okonx;
    }
    public String getOked() {
        return oked;
    }

    public void setOked(String oked) {
        this.oked = oked;
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
            ", bankAccountNumber='" + bankAccountNumber + "'" +
            ", bankName='" + bankName + "'" +
            ", bankBranchRegion='" + bankBranchRegion + "'" +
            ", mfo='" + mfo + "'" +
            ", tin='" + tin + "'" +
            ", okonx='" + okonx + "'" +
            ", oked='" + oked + "'" +
            '}';
    }
}
