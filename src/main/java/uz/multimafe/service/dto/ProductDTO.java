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
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String sapCode;

    @NotNull
    private String sapType;

    @NotNull
    private String name;

    private String serial;

    @NotNull
    private Integer qty;

    @NotNull
    private String uom;

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

    private Long sellerId;

    private String sellerSellerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }
    public String getSapType() {
        return sapType;
    }

    public void setSapType(String sapType) {
        this.sapType = sapType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerSellerName() {
        return sellerSellerName;
    }

    public void setSellerSellerName(String sellerSellerName) {
        this.sellerSellerName = sellerSellerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;

        if ( ! Objects.equals(id, productDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", sapCode='" + sapCode + "'" +
            ", sapType='" + sapType + "'" +
            ", name='" + name + "'" +
            ", serial='" + serial + "'" +
            ", qty='" + qty + "'" +
            ", uom='" + uom + "'" +
            ", price='" + price + "'" +
            ", deliveryFlag='" + deliveryFlag + "'" +
            ", hallFlag='" + hallFlag + "'" +
            ", defectFlag='" + defectFlag + "'" +
            ", virtualFlag='" + virtualFlag + "'" +
            ", reason='" + reason + "'" +
            ", comment='" + comment + "'" +
            ", guid='" + guid + "'" +
            '}';
    }
}
