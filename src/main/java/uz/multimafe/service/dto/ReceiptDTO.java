package uz.multimafe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import uz.multimafe.domain.enumeration.DocType;
import uz.multimafe.domain.enumeration.WholeSaleFlag;

/**
 * A DTO for the Receipt entity.
 */
public class ReceiptDTO implements Serializable {

    private Long id;

    @NotNull
    private String docNum;

    @NotNull
    private String docID;

    @NotNull
    private DocType docType;

    private String previousDocID;

    @NotNull
    private Long docDate;

    @NotNull
    private WholeSaleFlag wholeSaleFlag;

    private Long payMasterId;

    private String payMasterPayMasterName;

    private Long loyaltyCardId;

    private String loyaltyCardLoyaltyCardID;

    private Long statusId;

    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }
    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }
    public String getPreviousDocID() {
        return previousDocID;
    }

    public void setPreviousDocID(String previousDocID) {
        this.previousDocID = previousDocID;
    }
    public Long getDocDate() {
        return docDate;
    }

    public void setDocDate(Long docDate) {
        this.docDate = docDate;
    }
    public WholeSaleFlag getWholeSaleFlag() {
        return wholeSaleFlag;
    }

    public void setWholeSaleFlag(WholeSaleFlag wholeSaleFlag) {
        this.wholeSaleFlag = wholeSaleFlag;
    }

    public Long getPayMasterId() {
        return payMasterId;
    }

    public void setPayMasterId(Long payMasterId) {
        this.payMasterId = payMasterId;
    }

    public String getPayMasterPayMasterName() {
        return payMasterPayMasterName;
    }

    public void setPayMasterPayMasterName(String payMasterPayMasterName) {
        this.payMasterPayMasterName = payMasterPayMasterName;
    }

    public Long getLoyaltyCardId() {
        return loyaltyCardId;
    }

    public void setLoyaltyCardId(Long loyaltyCardId) {
        this.loyaltyCardId = loyaltyCardId;
    }

    public String getLoyaltyCardLoyaltyCardID() {
        return loyaltyCardLoyaltyCardID;
    }

    public void setLoyaltyCardLoyaltyCardID(String loyaltyCardLoyaltyCardID) {
        this.loyaltyCardLoyaltyCardID = loyaltyCardLoyaltyCardID;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long receiptStatusId) {
        this.statusId = receiptStatusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String receiptStatusName) {
        this.statusName = receiptStatusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReceiptDTO receiptDTO = (ReceiptDTO) o;

        if ( ! Objects.equals(id, receiptDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
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
