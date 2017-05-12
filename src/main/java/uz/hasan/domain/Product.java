package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sap_code")
    private String sapCode;

    @NotNull
    @Column(name = "sap_type", nullable = false)
    private String sapType;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "uom", nullable = false)
    private String uom;

    @Column(name = "width", precision=10, scale=2)
    private BigDecimal width;

    @Column(name = "length", precision=10, scale=2)
    private BigDecimal length;

    @Column(name = "height", precision=10, scale=2)
    private BigDecimal height;

    @Column(name = "weight", precision=10, scale=2)
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

    public Product sapCode(String sapCode) {
        this.sapCode = sapCode;
        return this;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getSapType() {
        return sapType;
    }

    public Product sapType(String sapType) {
        this.sapType = sapType;
        return this;
    }

    public void setSapType(String sapType) {
        this.sapType = sapType;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUom() {
        return uom;
    }

    public Product uom(String uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public Product width(BigDecimal width) {
        this.width = width;
        return this;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public Product length(BigDecimal length) {
        this.length = length;
        return this;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public Product height(BigDecimal height) {
        this.height = height;
        return this;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public Product weight(BigDecimal weight) {
        this.weight = weight;
        return this;
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
        Product product = (Product) o;
        if (product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
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
