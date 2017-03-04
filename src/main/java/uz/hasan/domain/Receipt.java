package uz.hasan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import uz.hasan.domain.enumeration.DocType;

import uz.hasan.domain.enumeration.WholeSaleFlag;

/**
 * A Receipt.
 */
@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "doc_num", nullable = false)
    private String docNum;

    @NotNull
    @Column(name = "doc_id", nullable = false)
    private String docID;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", nullable = false)
    private DocType docType;

    @Column(name = "previous_doc_id")
    private String previousDocID;

    @NotNull
    @Column(name = "doc_date", nullable = false)
    private Long docDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "whole_sale_flag", nullable = false)
    private WholeSaleFlag wholeSaleFlag;

    @ManyToOne(optional = false)
    @NotNull
    private PayMaster payMaster;

    @ManyToOne(optional = false)
    @NotNull
    private LoyaltyCard loyaltyCard;

    @ManyToOne(optional = false)
    @NotNull
    private ReceiptStatus status;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "receipt")
    @JsonIgnore
    private Set<ProductEntry> productEntries = new HashSet<>();

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

    public ReceiptStatus getStatus() {
        return status;
    }

    public Receipt status(ReceiptStatus receiptStatus) {
        this.status = receiptStatus;
        return this;
    }

    public void setStatus(ReceiptStatus receiptStatus) {
        this.status = receiptStatus;
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
            '}';
    }
}
