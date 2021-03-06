package uz.hasan.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import uz.hasan.domain.enumeration.DocType;
import uz.hasan.domain.enumeration.WholeSaleFlag;
import uz.hasan.domain.enumeration.ReceiptStatus;

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

    @NotNull
    private ReceiptStatus status;

    private ZonedDateTime sentToDCTime;

    private ZonedDateTime deliveredTime;

    private String fromTime;

    private String toTime;

    private Long deliveryDate;

    private Long payMasterId;

    private String payMasterPayMasterName;

    private Long loyaltyCardId;

    private String loyaltyCardLoyaltyCardID;

    private Long clientId;

    private String clientFirstName;

    private Long sentById;

    private String sentByLogin;

    private Long markedAsDeliveredById;

    private String markedAsDeliveredByLogin;

    private Long addressId;

    private String addressStreetAddress;

    private Long companyId;

    private String companyName;

    private Long receiverId;

    private String receiverName;

    private CompanyDTO receiver;

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
    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }
    public ZonedDateTime getSentToDCTime() {
        return sentToDCTime;
    }

    public void setSentToDCTime(ZonedDateTime sentToDCTime) {
        this.sentToDCTime = sentToDCTime;
    }
    public ZonedDateTime getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(ZonedDateTime deliveredTime) {
        this.deliveredTime = deliveredTime;
    }
    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }
    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
    public Long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public Long getSentById() {
        return sentById;
    }

    public void setSentById(Long userId) {
        this.sentById = userId;
    }

    public String getSentByLogin() {
        return sentByLogin;
    }

    public void setSentByLogin(String userLogin) {
        this.sentByLogin = userLogin;
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

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public CompanyDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(CompanyDTO receiver) {
        this.receiver = receiver;
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
            ", status='" + status + "'" +
            ", sentToDCTime='" + sentToDCTime + "'" +
            ", deliveredTime='" + deliveredTime + "'" +
            ", fromTime='" + fromTime + "'" +
            ", toTime='" + toTime + "'" +
            ", deliveryDate='" + deliveryDate + "'" +
            '}';
    }
}
