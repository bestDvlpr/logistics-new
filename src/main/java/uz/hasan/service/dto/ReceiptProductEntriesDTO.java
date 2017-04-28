package uz.hasan.service.dto;


import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the Receipt entity.
 */
public class ReceiptProductEntriesDTO extends ReceiptDTO implements Serializable {

    private List<ProductEntryDTO> productEntries;

    private Long deliveryDate;

    private AddressDTO address;

    private String deliveredDateTime;

    private ClientAndAddressesDTO client;

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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getDeliveredDateTime() {
        return deliveredDateTime;
    }

    public void setDeliveredDateTime(String deliveredDateTime) {
        this.deliveredDateTime = deliveredDateTime;
    }

    public ClientAndAddressesDTO getClient() {
        return client;
    }

    public void setClient(ClientAndAddressesDTO client) {
        this.client = client;
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
