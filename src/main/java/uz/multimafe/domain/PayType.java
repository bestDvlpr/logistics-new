package uz.multimafe.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

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

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "sap_code")
    private String sapCode;

    @Column(name = "serial")
    private String serial;

    @ManyToOne(optional = false)
    @NotNull
    private PaymentType paymentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public PayType amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
            ", amount='" + amount + "'" +
            ", sapCode='" + sapCode + "'" +
            ", serial='" + serial + "'" +
            '}';
    }
}
