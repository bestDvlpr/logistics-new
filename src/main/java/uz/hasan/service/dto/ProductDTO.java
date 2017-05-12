package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String sapCode;

    @NotNull
    private String sapType;

    @NotNull
    private String name;

    @NotNull
    private String uom;

    private BigDecimal width;

    private BigDecimal length;

    private BigDecimal height;

    private BigDecimal weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }
    public String getSapType() {
        return sapType;
    }

    public void setSapType(String sapType) {
        this.sapType = sapType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }
    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;

        if ( ! Objects.equals(id, productDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", sapCode='" + sapCode + "'" +
            ", sapType='" + sapType + "'" +
            ", name='" + name + "'" +
            ", uom='" + uom + "'" +
            ", width='" + width + "'" +
            ", length='" + length + "'" +
            ", height='" + height + "'" +
            ", weight='" + weight + "'" +
            '}';
    }
}
