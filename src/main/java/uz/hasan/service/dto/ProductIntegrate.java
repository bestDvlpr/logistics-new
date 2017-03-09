package uz.hasan.service.dto;

import uz.hasan.domain.ProductEntry;

import java.io.Serializable;
import java.math.BigDecimal;

/**
     * A ProductIntegrate.
     */
    public class ProductIntegrate implements Serializable {

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
