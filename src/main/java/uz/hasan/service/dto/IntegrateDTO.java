package uz.hasan.service.dto;

import uz.hasan.domain.ProductEntry;

import java.io.Serializable;
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


    private List<Product> products;

    private List<Payment> payments;


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

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public String getWholesaleFlag() {
        return wholesaleFlag;
    }

    public void setWholesaleFlag(String wholesaleFlag) {
        this.wholesaleFlag = wholesaleFlag;
    }

    /**
     * A Product.
     */
    public class Product implements Serializable {

        /**
         * SAPCode="000000000001002193"
         * SAPType="HAWA"
         * Name="Кинотеатр домашний JBL ARENA 125CB"
         * Serial="3434343"
         * Qty="1"
         * UOM="PCE"
         * Price="1651000"
         * Discount="0"
         * DiscountID=""
         * Amount="1651000"
         * DeliveryFlag="1"
         * HallFlag="1"
         * DefectFlag="0"
         * VirtualFlag="0"
         * Reason=""
         * Comment=""
         * SellerID=""
         * SellerName=""
         * GUID="727F307892FF4765B13EF756DF6EE3E0"
         */


        private static final long serialVersionUID = 1L;


        private String sapCode;

        private String sapType;

        private String serial;

        private String name;

        private String uom;

        private BigDecimal price;

        private BigDecimal amount;

        private String deliveryFlag;

        private String hallFlag;

        private String defectFlag;

        private String virtualFlag;

        private String reason;

        private String sellerID;

        private String discountID;

        private String sellerName;

        private String comment;

        private String guid;

        private BigDecimal qty;

        private BigDecimal discount;

        private Boolean saved;


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

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getDeliveryFlag() {
            return deliveryFlag;
        }

        public void setDeliveryFlag(String deliveryFlag) {
            this.deliveryFlag = deliveryFlag;
        }

        public String getHallFlag() {
            return hallFlag;
        }

        public void setHallFlag(String hallFlag) {
            this.hallFlag = hallFlag;
        }

        public String getDefectFlag() {
            return defectFlag;
        }

        public void setDefectFlag(String defectFlag) {
            this.defectFlag = defectFlag;
        }

        public String getVirtualFlag() {
            return virtualFlag;
        }

        public void setVirtualFlag(String virtualFlag) {
            this.virtualFlag = virtualFlag;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getSellerID() {
            return sellerID;
        }

        public void setSellerID(String sellerID) {
            this.sellerID = sellerID;
        }

        public String getDiscountID() {
            return discountID;
        }

        public void setDiscountID(String discountID) {
            this.discountID = discountID;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
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

        public Boolean getSaved() {
            return saved;
        }

        public void setSaved(Boolean saved) {
            this.saved = saved;
        }


        public boolean eq(ProductEntry entry) {
            if (getSapCode() == null
                || getSerial() == null
                || entry.getProduct() == null
                || entry.getProduct().getSapCode() == null
                || entry.getSerialNumber() == null)
                return false;

            return this.getSapCode().equals(entry.getProduct().getSapCode()) && this.getSerial().equals(entry.getSerialNumber());
        }

    }


    public class Payment implements Serializable {

    /*
      Type="CASH"
       Amount="5000000"
       SAPCode=""
       Serial=""
       */


        private String type;

        private BigDecimal amount;

        private String sapCode;

        private String serial;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getSapCode() {
            return sapCode;
        }

        public void setSapCode(String sapCode) {
            this.sapCode = sapCode;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

    }


}
