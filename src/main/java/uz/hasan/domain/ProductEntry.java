package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import uz.hasan.domain.enumeration.SalesType;

import uz.hasan.domain.enumeration.SalesPlace;

import uz.hasan.domain.enumeration.DefectFlag;

import uz.hasan.domain.enumeration.VirtualFlag;

import uz.hasan.domain.enumeration.ReceiptStatus;

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

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_flag")
    private SalesType deliveryFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "hall_flag")
    private SalesPlace hallFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "defect_flag")
    private DefectFlag defectFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "virtual_flag")
    private VirtualFlag virtualFlag;

    @Column(name = "reason")
    private String reason;

    @Column(name = "comment")
    private String comment;

    @Column(name = "guid")
    private String guid;

    @NotNull
    @Column(name = "qty", precision=10, scale=2, nullable = false)
    private BigDecimal qty;

    @Column(name = "discount", precision=10, scale=2)
    private BigDecimal discount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReceiptStatus status;

    @NotNull
    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "attached_to_car_time")
    private ZonedDateTime attachedToCarTime;

    @Column(name = "delivery_start_time")
    private ZonedDateTime deliveryStartTime;

    @Column(name = "delivery_end_time")
    private ZonedDateTime deliveryEndTime;

    @Column(name = "delivery_date")
    private Long deliveryDate;

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

    @ManyToOne
    private Car attachedCar;

    @ManyToOne
    private Address address;

    @ManyToOne
    private User attachedToDriverBy;

    @ManyToOne
    private User deliveryItemsSentBy;

    @ManyToOne
    private User markedAsDeliveredBy;

    @ManyToOne
    private Company company;

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

    public ReceiptStatus getStatus() {
        return status;
    }

    public ProductEntry status(ReceiptStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public ProductEntry cancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ProductEntry serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ZonedDateTime getAttachedToCarTime() {
        return attachedToCarTime;
    }

    public ProductEntry attachedToCarTime(ZonedDateTime attachedToCarTime) {
        this.attachedToCarTime = attachedToCarTime;
        return this;
    }

    public void setAttachedToCarTime(ZonedDateTime attachedToCarTime) {
        this.attachedToCarTime = attachedToCarTime;
    }

    public ZonedDateTime getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public ProductEntry deliveryStartTime(ZonedDateTime deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
        return this;
    }

    public void setDeliveryStartTime(ZonedDateTime deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public ZonedDateTime getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public ProductEntry deliveryEndTime(ZonedDateTime deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
        return this;
    }

    public void setDeliveryEndTime(ZonedDateTime deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public Long getDeliveryDate() {
        return deliveryDate;
    }

    public ProductEntry deliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public Car getAttachedCar() {
        return attachedCar;
    }

    public ProductEntry attachedCar(Car car) {
        this.attachedCar = car;
        return this;
    }

    public void setAttachedCar(Car car) {
        this.attachedCar = car;
    }

    public Address getAddress() {
        return address;
    }

    public ProductEntry address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getAttachedToDriverBy() {
        return attachedToDriverBy;
    }

    public ProductEntry attachedToDriverBy(User user) {
        this.attachedToDriverBy = user;
        return this;
    }

    public void setAttachedToDriverBy(User user) {
        this.attachedToDriverBy = user;
    }

    public User getDeliveryItemsSentBy() {
        return deliveryItemsSentBy;
    }

    public ProductEntry deliveryItemsSentBy(User user) {
        this.deliveryItemsSentBy = user;
        return this;
    }

    public void setDeliveryItemsSentBy(User user) {
        this.deliveryItemsSentBy = user;
    }

    public User getMarkedAsDeliveredBy() {
        return markedAsDeliveredBy;
    }

    public ProductEntry markedAsDeliveredBy(User user) {
        this.markedAsDeliveredBy = user;
        return this;
    }

    public void setMarkedAsDeliveredBy(User user) {
        this.markedAsDeliveredBy = user;
    }

    public Company getCompany() {
        return company;
    }

    public ProductEntry company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
            ", status='" + status + "'" +
            ", cancelled='" + cancelled + "'" +
            ", serialNumber='" + serialNumber + "'" +
            ", attachedToCarTime='" + attachedToCarTime + "'" +
            ", deliveryStartTime='" + deliveryStartTime + "'" +
            ", deliveryEndTime='" + deliveryEndTime + "'" +
            ", deliveryDate='" + deliveryDate + "'" +
            '}';
    }
}
