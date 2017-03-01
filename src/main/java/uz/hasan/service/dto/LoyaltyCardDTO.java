package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the LoyaltyCard entity.
 */
public class LoyaltyCardDTO implements Serializable {

    private Long id;

    @NotNull
    private String loyaltyCardID;

    @NotNull
    private String loyaltyCardBonus;

    @NotNull
    private BigDecimal loyaltyCardAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLoyaltyCardID() {
        return loyaltyCardID;
    }

    public void setLoyaltyCardID(String loyaltyCardID) {
        this.loyaltyCardID = loyaltyCardID;
    }
    public String getLoyaltyCardBonus() {
        return loyaltyCardBonus;
    }

    public void setLoyaltyCardBonus(String loyaltyCardBonus) {
        this.loyaltyCardBonus = loyaltyCardBonus;
    }
    public BigDecimal getLoyaltyCardAmount() {
        return loyaltyCardAmount;
    }

    public void setLoyaltyCardAmount(BigDecimal loyaltyCardAmount) {
        this.loyaltyCardAmount = loyaltyCardAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoyaltyCardDTO loyaltyCardDTO = (LoyaltyCardDTO) o;

        if ( ! Objects.equals(id, loyaltyCardDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoyaltyCardDTO{" +
            "id=" + id +
            ", loyaltyCardID='" + loyaltyCardID + "'" +
            ", loyaltyCardBonus='" + loyaltyCardBonus + "'" +
            ", loyaltyCardAmount='" + loyaltyCardAmount + "'" +
            '}';
    }
}
