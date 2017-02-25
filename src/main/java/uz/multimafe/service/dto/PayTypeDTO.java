package uz.multimafe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PayType entity.
 */
public class PayTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer amount;

    private String sapCode;

    private String serial;

    private Long paymentTypeId;

    private String paymentTypeName;

    private Long receiptId;

    private String receiptDocNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptDocNum() {
        return receiptDocNum;
    }

    public void setReceiptDocNum(String receiptDocNum) {
        this.receiptDocNum = receiptDocNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayTypeDTO payTypeDTO = (PayTypeDTO) o;

        if ( ! Objects.equals(id, payTypeDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayTypeDTO{" +
            "id=" + id +
            ", amount='" + amount + "'" +
            ", sapCode='" + sapCode + "'" +
            ", serial='" + serial + "'" +
            '}';
    }
}
