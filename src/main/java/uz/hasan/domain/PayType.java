package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import uz.hasan.domain.enumeration.PaymentType;

/**
 * A PayType.
 */
@Entity
@Table(name = "pay_type")
public class PayType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sap_code")
    private String sapCode;

    @Column(name = "serial")
    private String serial;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @NotNull
    private Receipt receipt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSapCode() {
        return sapCode;
    }

    public PayType sapCode(String sapCode) {
        this.sapCode = sapCode;
        return this;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getSerial() {
        return serial;
    }

    public PayType serial(String serial) {
        this.serial = serial;
        return this;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PayType paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PayType amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public PayType receipt(Receipt receipt) {
        this.receipt = receipt;
        return this;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PayType payType = (PayType) o;
        if (payType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, payType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayType{" +
            "id=" + id +
            ", sapCode='" + sapCode + "'" +
            ", serial='" + serial + "'" +
            ", paymentType='" + paymentType + "'" +
            ", amount='" + amount + "'" +
            '}';
    }
}
