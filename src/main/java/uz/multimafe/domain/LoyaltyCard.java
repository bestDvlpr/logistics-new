package uz.multimafe.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A LoyaltyCard.
 */
@Entity
@Table(name = "loyalty_card")
public class LoyaltyCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "loyalty_card_id", nullable = false)
    private String loyaltyCardID;

    @NotNull
    @Column(name = "loyalty_card_bonus", nullable = false)
    private String loyaltyCardBonus;

    @NotNull
    @Column(name = "loyalty_card_amount", precision=10, scale=2, nullable = false)
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

    public LoyaltyCard loyaltyCardID(String loyaltyCardID) {
        this.loyaltyCardID = loyaltyCardID;
        return this;
    }

    public void setLoyaltyCardID(String loyaltyCardID) {
        this.loyaltyCardID = loyaltyCardID;
    }

    public String getLoyaltyCardBonus() {
        return loyaltyCardBonus;
    }

    public LoyaltyCard loyaltyCardBonus(String loyaltyCardBonus) {
        this.loyaltyCardBonus = loyaltyCardBonus;
        return this;
    }

    public void setLoyaltyCardBonus(String loyaltyCardBonus) {
        this.loyaltyCardBonus = loyaltyCardBonus;
    }

    public BigDecimal getLoyaltyCardAmount() {
        return loyaltyCardAmount;
    }

    public LoyaltyCard loyaltyCardAmount(BigDecimal loyaltyCardAmount) {
        this.loyaltyCardAmount = loyaltyCardAmount;
        return this;
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
        LoyaltyCard loyaltyCard = (LoyaltyCard) o;
        if (loyaltyCard.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, loyaltyCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoyaltyCard{" +
            "id=" + id +
            ", loyaltyCardID='" + loyaltyCardID + "'" +
            ", loyaltyCardBonus='" + loyaltyCardBonus + "'" +
            ", loyaltyCardAmount='" + loyaltyCardAmount + "'" +
            '}';
    }
}
