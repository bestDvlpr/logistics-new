package uz.hasan.service.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by User on 3/4/2017.
 */
public class IntegrateDTO {

    private String docNum;

    private String docID;

    private String docType;

    private String previousDocID;

    private Long docDate;


    private String loyaltyCardID;

    private BigDecimal loyaltyCardBonus;

    private BigDecimal loyaltyCardAmount;

    private String paymasterID;

    private String payMasterName;

    private String wholesaleFlag;


    private List<ProductIntegrate> products;

    private List<PaymentIntegrate> payments;

    private ClientDTO client;


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

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
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


    public String getLoyaltyCardID() {
        return loyaltyCardID;
    }

    public void setLoyaltyCardID(String loyaltyCardID) {
        this.loyaltyCardID = loyaltyCardID;
    }

    public BigDecimal getLoyaltyCardBonus() {
        return loyaltyCardBonus;
    }

    public void setLoyaltyCardBonus(BigDecimal loyaltyCardBonus) {
        this.loyaltyCardBonus = loyaltyCardBonus;
    }

    public BigDecimal getLoyaltyCardAmount() {
        return loyaltyCardAmount;
    }

    public void setLoyaltyCardAmount(BigDecimal loyaltyCardAmount) {
        this.loyaltyCardAmount = loyaltyCardAmount;
    }


    public String getPaymasterID() {
        return paymasterID;
    }

    public void setPaymasterID(String paymasterID) {
        this.paymasterID = paymasterID;
    }

    public String getPayMasterName() {
        return payMasterName;
    }

    public void setPayMasterName(String payMasterName) {
        this.payMasterName = payMasterName;
    }

    public void setProducts(List<ProductIntegrate> products) {
        this.products = products;
    }

    public void setPayments(List<PaymentIntegrate> payments) {
        this.payments = payments;
    }

    public List<ProductIntegrate> getProducts() {
        return products;
    }

    public List<PaymentIntegrate> getPayments() {
        return payments;
    }

    public String getWholesaleFlag() {
        return wholesaleFlag;
    }

    public void setWholesaleFlag(String wholesaleFlag) {
        this.wholesaleFlag = wholesaleFlag;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
}
