package uz.hasan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uz.hasan.domain.enumeration.DocType;
import uz.hasan.domain.enumeration.ReceiptStatus;
import uz.hasan.domain.enumeration.WholeSaleFlag;
import uz.hasan.domain.pojos.report.ByDistrict;
import uz.hasan.domain.pojos.report.DeliveryCountByCompany;
import uz.hasan.domain.pojos.report.ProductDeliveryReport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Receipt.
 */
@Entity
@SqlResultSetMappings(
    value = {
        @SqlResultSetMapping(
            name = "ProductDeliveryReportMapping",
            classes = {
                @ConstructorResult(
                    targetClass = ProductDeliveryReport.class,
                    columns = {
                        @ColumnResult(name = "companyName", type = String.class),
                        @ColumnResult(name = "date", type = String.class),
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "docType", type = String.class),
                        @ColumnResult(name = "productName", type = String.class),
                        @ColumnResult(name = "productQty", type = Long.class),
                        @ColumnResult(name = "docDate", type = String.class),
                        @ColumnResult(name = "clientFirstName", type = String.class),
                        @ColumnResult(name = "clientLastName", type = String.class),
                        @ColumnResult(name = "phoneNumber", type = String.class),
                        @ColumnResult(name = "districtName", type = String.class),
                        @ColumnResult(name = "address", type = String.class),
                        @ColumnResult(name = "carModel", type = String.class),
                        @ColumnResult(name = "carNumber", type = String.class),
                        @ColumnResult(name = "driverFirstName", type = String.class),
                        @ColumnResult(name = "driverLastName", type = String.class),
                        @ColumnResult(name = "sentToDCTime", type = String.class),
                        @ColumnResult(name = "deliveredTime", type = String.class),
                        @ColumnResult(name = "deliveryTookTime", type = String.class),
                        @ColumnResult(name = "companyId", type = Long.class)
                    }
                )
            }
        ),
        @SqlResultSetMapping(
            name = "DeliveryCountByCompanyMapping",
            classes = {
                @ConstructorResult(
                    targetClass = DeliveryCountByCompany.class,
                    columns = {
                        @ColumnResult(name = "companyId", type = String.class),
                        @ColumnResult(name = "companyName", type = String.class),
                        @ColumnResult(name = "date", type = String.class),
                        @ColumnResult(name = "count", type = Long.class)
                    }
                )
            }
        ),
        @SqlResultSetMapping(
            name = "DeliveryCountByCompanyByDistrictMapping",
            classes = {
                @ConstructorResult(
                    targetClass = ByDistrict.class,
                    columns = {
                        @ColumnResult(name = "companyId", type = String.class),
                        @ColumnResult(name = "companyName", type = String.class),
                        @ColumnResult(name = "districtName", type = String.class),
                        @ColumnResult(name = "districtName2", type = String.class),
                        @ColumnResult(name = "count", type = Long.class)
                    }
                )
            }
        )
    }
)
@NamedNativeQueries(
    value = {
        @NamedNativeQuery(name = "Receipt.overallReport",
            query = "SELECT * FROM f_common_report(COALESCE(NULLIF(?1, 'null')), COALESCE(NULLIF(?2, 'null')), COALESCE(NULLIF(?3, 'null')), COALESCE(NULLIF(?4, 'null')))",
            resultClass = ProductDeliveryReport.class,
            resultSetMapping = "ProductDeliveryReportMapping"
        ),
        @NamedNativeQuery(name = "Receipt.pagedOverallReport",
            query = "SELECT * FROM f_common_report_paged(COALESCE(NULLIF(?1, 'null')), COALESCE(NULLIF(?2, 'null')), COALESCE(NULLIF(?3, 'null')), COALESCE(NULLIF(?4, 'null')), COALESCE(NULLIF(?5, 'null')), COALESCE(NULLIF(?6, 'null')))",
            resultClass = ProductDeliveryReport.class,
            resultSetMapping = "ProductDeliveryReportMapping"
        ),
        @NamedNativeQuery(name = "Receipt.countByCompany",
            query = "SELECT * FROM f_delivery_count_by_company(COALESCE(NULLIF(?1, 'null')), COALESCE(NULLIF(?2, 'null')), COALESCE(NULLIF(?3, 'null')), COALESCE(NULLIF(?4, 'null')))",
            resultClass = DeliveryCountByCompany.class,
            resultSetMapping = "DeliveryCountByCompanyMapping"
        ),
        @NamedNativeQuery(name = "Receipt.countByCompanyByDistrictByStatus",
            query = "SELECT * FROM f_delivery_count_by_company_by_status_by_date(COALESCE(NULLIF(?1, 'null')), COALESCE(NULLIF(?2, 'null')), COALESCE(NULLIF(?3, 'null')), COALESCE(NULLIF(?4, 'null')), COALESCE(NULLIF(?5, 'null')))",
            resultClass = ByDistrict.class,
            resultSetMapping = "DeliveryCountByCompanyByDistrictMapping"
        )
    }
)
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "doc_id")
    private String docID;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", nullable = false)
    private DocType docType;

    @Column(name = "previous_doc_id")
    private String previousDocID;

    @Column(name = "doc_date")
    private Long docDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "whole_sale_flag")
    private WholeSaleFlag wholeSaleFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReceiptStatus status;

    @Column(name = "sent_to_dc_time")
    private ZonedDateTime sentToDCTime;

    @Column(name = "delivered_time")
    private ZonedDateTime deliveredTime;

    @Column(name = "from_time")
    private String fromTime;

    @Column(name = "to_time")
    private String toTime;

    @Column(name = "delivery_date")
    private Long deliveryDate;

    @ManyToOne
    private PayMaster payMaster;

    @ManyToOne
    private LoyaltyCard loyaltyCard;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "receipt")
    @JsonIgnore
    private Set<ProductEntry> productEntries = new HashSet<>();

    @OneToMany(mappedBy = "receipt")
    @JsonIgnore
    private Set<PayType> payTypes = new HashSet<>();

    @ManyToOne
    private User sentBy;

    @ManyToOne
    private User markedAsDeliveredBy;

    @ManyToOne
    private Address address;

    @ManyToOne(optional = false)
    @NotNull
    private Company company;

    @ManyToOne
    private Company receiver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocNum() {
        return docNum;
    }

    public Receipt docNum(String docNum) {
        this.docNum = docNum;
        return this;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocID() {
        return docID;
    }

    public Receipt docID(String docID) {
        this.docID = docID;
        return this;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public DocType getDocType() {
        return docType;
    }

    public Receipt docType(DocType docType) {
        this.docType = docType;
        return this;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public String getPreviousDocID() {
        return previousDocID;
    }

    public Receipt previousDocID(String previousDocID) {
        this.previousDocID = previousDocID;
        return this;
    }

    public void setPreviousDocID(String previousDocID) {
        this.previousDocID = previousDocID;
    }

    public Long getDocDate() {
        return docDate;
    }

    public Receipt docDate(Long docDate) {
        this.docDate = docDate;
        return this;
    }

    public void setDocDate(Long docDate) {
        this.docDate = docDate;
    }

    public WholeSaleFlag getWholeSaleFlag() {
        return wholeSaleFlag;
    }

    public Receipt wholeSaleFlag(WholeSaleFlag wholeSaleFlag) {
        this.wholeSaleFlag = wholeSaleFlag;
        return this;
    }

    public void setWholeSaleFlag(WholeSaleFlag wholeSaleFlag) {
        this.wholeSaleFlag = wholeSaleFlag;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public Receipt status(ReceiptStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    public ZonedDateTime getSentToDCTime() {
        return sentToDCTime;
    }

    public Receipt sentToDCTime(ZonedDateTime sentToDCTime) {
        this.sentToDCTime = sentToDCTime;
        return this;
    }

    public void setSentToDCTime(ZonedDateTime sentToDCTime) {
        this.sentToDCTime = sentToDCTime;
    }

    public ZonedDateTime getDeliveredTime() {
        return deliveredTime;
    }

    public Receipt deliveredTime(ZonedDateTime deliveredTime) {
        this.deliveredTime = deliveredTime;
        return this;
    }

    public void setDeliveredTime(ZonedDateTime deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public Receipt fromTime(String fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public Receipt toTime(String toTime) {
        this.toTime = toTime;
        return this;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public Long getDeliveryDate() {
        return deliveryDate;
    }

    public Receipt deliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PayMaster getPayMaster() {
        return payMaster;
    }

    public Receipt payMaster(PayMaster payMaster) {
        this.payMaster = payMaster;
        return this;
    }

    public void setPayMaster(PayMaster payMaster) {
        this.payMaster = payMaster;
    }

    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public Receipt loyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
        return this;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }

    public Client getClient() {
        return client;
    }

    public Receipt client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<ProductEntry> getProductEntries() {
        return productEntries;
    }

    public Receipt productEntries(Set<ProductEntry> productEntries) {
        this.productEntries = productEntries;
        return this;
    }

    public Receipt addProductEntries(ProductEntry productEntry) {
        this.productEntries.add(productEntry);
        productEntry.setReceipt(this);
        return this;
    }

    public Receipt removeProductEntries(ProductEntry productEntry) {
        this.productEntries.remove(productEntry);
        productEntry.setReceipt(null);
        return this;
    }

    public void setProductEntries(Set<ProductEntry> productEntries) {
        this.productEntries = productEntries;
    }

    public Set<PayType> getPayTypes() {
        return payTypes;
    }

    public Receipt payTypes(Set<PayType> payTypes) {
        this.payTypes = payTypes;
        return this;
    }

    public Receipt addPayTypes(PayType payType) {
        this.payTypes.add(payType);
        payType.setReceipt(this);
        return this;
    }

    public Receipt removePayTypes(PayType payType) {
        this.payTypes.remove(payType);
        payType.setReceipt(null);
        return this;
    }

    public void setPayTypes(Set<PayType> payTypes) {
        this.payTypes = payTypes;
    }

    public User getSentBy() {
        return sentBy;
    }

    public Receipt sentBy(User user) {
        this.sentBy = user;
        return this;
    }

    public void setSentBy(User user) {
        this.sentBy = user;
    }

    public User getMarkedAsDeliveredBy() {
        return markedAsDeliveredBy;
    }

    public Receipt markedAsDeliveredBy(User user) {
        this.markedAsDeliveredBy = user;
        return this;
    }

    public void setMarkedAsDeliveredBy(User user) {
        this.markedAsDeliveredBy = user;
    }

    public Address getAddress() {
        return address;
    }

    public Receipt address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public Receipt company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getReceiver() {
        return receiver;
    }

    public Receipt receiver(Company company) {
        this.receiver = company;
        return this;
    }

    public void setReceiver(Company company) {
        this.receiver = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) o;
        if (receipt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, receipt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Receipt{" +
            "id=" + id +
            ", docNum='" + docNum + "'" +
            ", docID='" + docID + "'" +
            ", docType='" + docType + "'" +
            ", previousDocID='" + previousDocID + "'" +
            ", docDate='" + docDate + "'" +
            ", wholeSaleFlag='" + wholeSaleFlag + "'" +
            ", status='" + status + "'" +
            ", sentToDCTime='" + sentToDCTime + "'" +
            ", deliveredTime='" + deliveredTime + "'" +
            ", fromTime='" + fromTime + "'" +
            ", toTime='" + toTime + "'" +
            ", deliveryDate='" + deliveryDate + "'" +
            '}';
    }
}
