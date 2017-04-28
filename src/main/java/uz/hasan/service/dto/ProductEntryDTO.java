package uz.hasan.service.dto;


import uz.hasan.domain.enumeration.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the ProductEntry entity.
 */
public class ProductEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal price;

    @NotNull
    private SalesType deliveryFlag;

    @NotNull
    private SalesPlace hallFlag;

    @NotNull
    private DefectFlag defectFlag;

    @NotNull
    private VirtualFlag virtualFlag;

    private String reason;

    private String comment;

    @NotNull
    private String guid;

    @NotNull
    private BigDecimal qty;

    private BigDecimal discount;

    @NotNull
    private ReceiptStatus status;

    @NotNull
    private Boolean cancelled;

    private String serialNumber;

    private ZonedDateTime attachedToCarTime;

    private ZonedDateTime deliveryStartTime;

    private ZonedDateTime deliveryEndTime;

    private Long productId;

    private String productName;

    private Long sellerIDId;

    private String sellerIDSellerID;

    private Long receiptId;

    private String receiptDocNum;

    private Long driverId;

    private String driverLastName;

    private Long attachedCarId;

    private String attachedCarNumber;

    private Long addressId;

    private String addressStreetAddress;

    private Long attachedToDriverById;

    private String attachedToDriverByLogin;

    private Long deliveryItemsSentById;

    private String deliveryItemsSentByLogin;

    private Long markedAsDeliveredById;

    private String markedAsDeliveredByLogin;

    private Long companyId;

    private String companyName;

    private ProductDTO product;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SalesType getDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(SalesType deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public SalesPlace getHallFlag() {
        return hallFlag;
    }

    public void setHallFlag(SalesPlace hallFlag) {
        this.hallFlag = hallFlag;
    }

    public DefectFlag getDefectFlag() {
        return defectFlag;
    }

    public void setDefectFlag(DefectFlag defectFlag) {
        this.defectFlag = defectFlag;
    }

    public VirtualFlag getVirtualFlag() {
        return virtualFlag;
    }

    public void setVirtualFlag(VirtualFlag virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ZonedDateTime getAttachedToCarTime() {
        return attachedToCarTime;
    }

    public void setAttachedToCarTime(ZonedDateTime attachedToCarTime) {
        this.attachedToCarTime = attachedToCarTime;
    }

    public ZonedDateTime getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(ZonedDateTime deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public ZonedDateTime getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(ZonedDateTime deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSellerIDId() {
        return sellerIDId;
    }

    public void setSellerIDId(Long sellerId) {
        this.sellerIDId = sellerId;
    }

    public String getSellerIDSellerID() {
        return sellerIDSellerID;
    }

    public void setSellerIDSellerID(String sellerSellerID) {
        this.sellerIDSellerID = sellerSellerID;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptDocNum() {
        return receiptDocNum;
    }

    public void setReceiptDocNum(String receiptDocNum) {
        this.receiptDocNum = receiptDocNum;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public Long getAttachedCarId() {
        return attachedCarId;
    }

    public void setAttachedCarId(Long carId) {
        this.attachedCarId = carId;
    }

    public String getAttachedCarNumber() {
        return attachedCarNumber;
    }

    public void setAttachedCarNumber(String carNumber) {
        this.attachedCarNumber = carNumber;
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

    public Long getAttachedToDriverById() {
        return attachedToDriverById;
    }

    public void setAttachedToDriverById(Long userId) {
        this.attachedToDriverById = userId;
    }

    public String getAttachedToDriverByLogin() {
        return attachedToDriverByLogin;
    }

    public void setAttachedToDriverByLogin(String userLogin) {
        this.attachedToDriverByLogin = userLogin;
    }

    public Long getDeliveryItemsSentById() {
        return deliveryItemsSentById;
    }

    public void setDeliveryItemsSentById(Long userId) {
        this.deliveryItemsSentById = userId;
    }

    public String getDeliveryItemsSentByLogin() {
        return deliveryItemsSentByLogin;
    }

    public void setDeliveryItemsSentByLogin(String userLogin) {
        this.deliveryItemsSentByLogin = userLogin;
    }

    public Long getMarkedAsDeliveredById() {
        return markedAsDeliveredById;
    }

    public void setMarkedAsDeliveredById(Long userId) {
        this.markedAsDeliveredById = userId;
    }

    public String getMarkedAsDeliveredByLogin() {
        return markedAsDeliveredByLogin;
    }

    public void setMarkedAsDeliveredByLogin(String userLogin) {
        this.markedAsDeliveredByLogin = userLogin;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductEntryDTO productEntryDTO = (ProductEntryDTO) o;

        if (!Objects.equals(id, productEntryDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductEntryDTO{" +
            "id=" + id +
            ", price='" + price + "'" +
            ", deliveryFlag='" + deliveryFlag + "'" +
            ", hallFlag='" + hallFlag + "'" +
            ", defectFlag='" + defectFlag + "'" +
            ", virtualFlag='" + virtualFlag + "'" +
            ", reason='" + reason + "'" +
            ", comment='" + comment + "'" +
            ", guid='" + guid + "'" +
            ", qty='" + qty + "'" +
            ", discount='" + discount + "'" +
            ", status='" + status + "'" +
            ", cancelled='" + cancelled + "'" +
            ", serialNumber='" + serialNumber + "'" +
            ", attachedToCarTime='" + attachedToCarTime + "'" +
            ", deliveryStartTime='" + deliveryStartTime + "'" +
            ", deliveryEndTime='" + deliveryEndTime + "'" +
            '}';
    }
}
