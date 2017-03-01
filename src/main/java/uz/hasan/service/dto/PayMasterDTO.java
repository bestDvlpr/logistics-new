package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PayMaster entity.
 */
public class PayMasterDTO implements Serializable {

    private Long id;

    @NotNull
    private String paymasterID;

    @NotNull
    private String payMasterName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayMasterDTO payMasterDTO = (PayMasterDTO) o;

        if ( ! Objects.equals(id, payMasterDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayMasterDTO{" +
            "id=" + id +
            ", paymasterID='" + paymasterID + "'" +
            ", payMasterName='" + payMasterName + "'" +
            '}';
    }
}
