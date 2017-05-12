package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A CarModel.
 */
@Entity
@Table(name = "car_model")
public class CarModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "width", precision=10, scale=2)
    private BigDecimal width;

    @Column(name = "length", precision=10, scale=2)
    private BigDecimal length;

    @Column(name = "height", precision=10, scale=2)
    private BigDecimal height;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CarModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public CarModel width(BigDecimal width) {
        this.width = width;
        return this;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getLength() {
        return length;
    }

    public CarModel length(BigDecimal length) {
        this.length = length;
        return this;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public CarModel height(BigDecimal height) {
        this.height = height;
        return this;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        if (carModel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, carModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", width='" + width + "'" +
            ", length='" + length + "'" +
            ", height='" + height + "'" +
            '}';
    }
}
