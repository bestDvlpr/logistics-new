package uz.hasan.service.dto;


import uz.hasan.domain.enumeration.DocType;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.WholeSaleFlag;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Receipt entity.
 */
public class ReceiptProductEntriesDTO extends ReceiptDTO implements Serializable {

    private List<ProductEntryDTO> productEntries;

    private Long deliveryDate;

    private Set<AddressDTO> addresses;

    public List<ProductEntryDTO> getProductEntries() {
        return productEntries;
    }

    public void setProductEntries(List<ProductEntryDTO> productEntries) {
        this.productEntries = productEntries;
    }

    public Long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Set<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReceiptProductEntriesDTO that = (ReceiptProductEntriesDTO) o;

        return productEntries != null ? productEntries.equals(that.productEntries) : that.productEntries == null;
    }

    @Override
    public int hashCode() {
        return productEntries != null ? productEntries.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ReceiptProductEntriesDTO{" +
            "productEntries=" + productEntries +
            ", deliveryDate=" + deliveryDate +
            '}';
    }
}
