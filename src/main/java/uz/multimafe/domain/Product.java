package uz.multimafe.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import uz.multimafe.domain.enumeration.SalesType;

import uz.multimafe.domain.enumeration.SalesPlace;

import uz.multimafe.domain.enumeration.DefectFlag;

import uz.multimafe.domain.enumeration.VirtualFlag;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sap_code", nullable = false)
    private String sapCode;

    @NotNull
    @Column(name = "sap_type", nullable = false)
    private String sapType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "serial")
    private String serial;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Integer qty;

    @NotNull
    @Column(name = "uom", nullable = false)
    private String uom;

    @NotNull
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_flag", nullable = false)
    private SalesType deliveryFlag;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hall_flag", nullable = false)
    private SalesPlace hallFlag;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "defect_flag", nullable = false)
    private DefectFlag defectFlag;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "virtual_flag", nullable = false)
    private VirtualFlag virtualFlag;

    @Column(name = "reason")
    private String reason;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "guid", nullable = false)
    private String guid;

    @ManyToOne
    private Seller seller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSapCode() {
        return sapCode;
    }

    public Product sapCode(String sapCode) {
        this.sapCode = sapCode;
        return this;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getSapType() {
        return sapType;
    }

    public Product sapType(String sapType) {
        this.sapType = sapType;
        return this;
    }

    public void setSapType(String sapType) {
        this.sapType = sapType;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public Product serial(String serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getQty() {
        return qty;
    }

    public Product qty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getUom() {
        return uom;
    }

    public Product uom(String uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SalesType getDeliveryFlag() {
        return deliveryFlag;
    }

    public Product deliveryFlag(SalesType deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
        return this;
    }

    public void setDeliveryFlag(SalesType deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public SalesPlace getHallFlag() {
        return hallFlag;
    }

    public Product hallFlag(SalesPlace hallFlag) {
        this.hallFlag = hallFlag;
        return this;
    }

    public void setHallFlag(SalesPlace hallFlag) {
        this.hallFlag = hallFlag;
    }

    public DefectFlag getDefectFlag() {
        return defectFlag;
    }

    public Product defectFlag(DefectFlag defectFlag) {
        this.defectFlag = defectFlag;
        return this;
    }

    public void setDefectFlag(DefectFlag defectFlag) {
        this.defectFlag = defectFlag;
    }

    public VirtualFlag getVirtualFlag() {
        return virtualFlag;
    }

    public Product virtualFlag(VirtualFlag virtualFlag) {
        this.virtualFlag = virtualFlag;
        return this;
    }

    public void setVirtualFlag(VirtualFlag virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    public String getReason() {
        return reason;
    }

    public Product reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public Product comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGuid() {
        return guid;
    }

    public Product guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product seller(Seller seller) {
        this.seller = seller;
        return this;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
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
