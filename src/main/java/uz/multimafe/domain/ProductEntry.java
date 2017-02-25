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
 * A ProductEntry.
 */
@Entity
@Table(name = "product_entry")
public class ProductEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @NotNull
    @Column(name = "qty", precision=10, scale=2, nullable = false)
    private BigDecimal qty;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @ManyToOne(optional = false)
    @NotNull
    private Product product;

    @ManyToOne
    private Seller sellerID;

    @ManyToOne(optional = false)
    @NotNull
    private Receipt receipt;

    @ManyToOne
    private Driver driver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntry price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SalesType getDeliveryFlag() {
        return deliveryFlag;
    }

    public ProductEntry deliveryFlag(SalesType deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
        return this;
    }

    public void setDeliveryFlag(SalesType deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public SalesPlace getHallFlag() {
        return hallFlag;
    }

    public ProductEntry hallFlag(SalesPlace hallFlag) {
        this.hallFlag = hallFlag;
        return this;
    }

    public void setHallFlag(SalesPlace hallFlag) {
        this.hallFlag = hallFlag;
    }

    public DefectFlag getDefectFlag() {
        return defectFlag;
    }

    public ProductEntry defectFlag(DefectFlag defectFlag) {
        this.defectFlag = defectFlag;
        return this;
    }

    public void setDefectFlag(DefectFlag defectFlag) {
        this.defectFlag = defectFlag;
    }

    public VirtualFlag getVirtualFlag() {
        return virtualFlag;
    }

    public ProductEntry virtualFlag(VirtualFlag virtualFlag) {
        this.virtualFlag = virtualFlag;
        return this;
    }

    public void setVirtualFlag(VirtualFlag virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    public String getReason() {
        return reason;
    }

    public ProductEntry reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public ProductEntry comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getGuid() {
        return guid;
    }

    public ProductEntry guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public ProductEntry qty(BigDecimal qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public ProductEntry discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public ProductEntry product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Seller getSellerID() {
        return sellerID;
    }

    public ProductEntry sellerID(Seller seller) {
        this.sellerID = seller;
        return this;
    }

    public void setSellerID(Seller seller) {
        this.sellerID = seller;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public ProductEntry receipt(Receipt receipt) {
        this.receipt = receipt;
        return this;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Driver getDriver() {
        return driver;
    }

    public ProductEntry driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductEntry productEntry = (ProductEntry) o;
        if (productEntry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductEntry{" +
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
