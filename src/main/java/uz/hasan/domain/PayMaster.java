package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PayMaster.
 */
@Entity
@Table(name = "pay_master")
public class PayMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "paymaster_id", nullable = false)
    private String paymasterID;

    @NotNull
    @Column(name = "pay_master_name", nullable = false)
    private String payMasterName;

    public PayMaster(Long payMasterId) {
        this.id = payMasterId;
    }

    public PayMaster() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymasterID() {
        return paymasterID;
    }

    public PayMaster paymasterID(String paymasterID) {
        this.paymasterID = paymasterID;
        return this;
    }

    public void setPaymasterID(String paymasterID) {
        this.paymasterID = paymasterID;
    }

    public String getPayMasterName() {
        return payMasterName;
    }

    public PayMaster payMasterName(String payMasterName) {
        this.payMasterName = payMasterName;
        return this;
    }

    public void setPayMasterName(String payMasterName) {
        this.payMasterName = payMasterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PayMaster payMaster = (PayMaster) o;
        if (payMaster.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, payMaster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayMaster{" +
            "id=" + id +
            ", paymasterID='" + paymasterID + "'" +
            ", payMasterName='" + payMasterName + "'" +
            '}';
    }
}
