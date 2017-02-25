package uz.multimafe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import uz.multimafe.domain.enumeration.SalesType;
import uz.multimafe.domain.enumeration.SalesPlace;
import uz.multimafe.domain.enumeration.DefectFlag;
import uz.multimafe.domain.enumeration.VirtualFlag;

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

    private Long productId;

    private String productName;

    private Long sellerIDId;

    private String sellerIDSellerID;

    private Long receiptId;

    private String receiptDocNum;

    private Long driverId;

    private String driverLastName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductEntryDTO productEntryDTO = (ProductEntryDTO) o;

        if ( ! Objects.equals(id, productEntryDTO.id)) { return false; }

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
            '}';
    }
}
