package uz.multimafe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Seller entity.
 */
public class SellerDTO implements Serializable {

    private Long id;

    @NotNull
    private String sellerID;

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

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }
    public String getSellerName() {
        return sellerName;
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

        SellerDTO sellerDTO = (SellerDTO) o;

        if ( ! Objects.equals(id, sellerDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SellerDTO{" +
            "id=" + id +
            ", sellerID='" + sellerID + "'" +
            ", sellerName='" + sellerName + "'" +
            '}';
    }
}
