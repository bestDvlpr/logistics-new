package uz.hasan.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentIntegrate implements Serializable {

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
