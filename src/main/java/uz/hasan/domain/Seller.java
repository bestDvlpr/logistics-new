package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Seller.
 */
@Entity
@Table(name = "seller")
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private String sellerID;

    @Column(name = "seller_name")
    private String sellerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSellerID() {
        return sellerID;
    }

    public Seller sellerID(String sellerID) {
        this.sellerID = sellerID;
        return this;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Seller sellerName(String sellerName) {
        this.sellerName = sellerName;
        return this;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seller seller = (Seller) o;
        if (seller.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seller{" +
            "id=" + id +
            ", sellerID='" + sellerID + "'" +
            ", sellerName='" + sellerName + "'" +
            '}';
    }
}
