package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CarModel entity.
 */
public class CarModelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private BigDecimal width;

    private BigDecimal length;

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

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarModelDTO carModelDTO = (CarModelDTO) o;

        if ( ! Objects.equals(id, carModelDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarModelDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", width='" + width + "'" +
            ", length='" + length + "'" +
            ", height='" + height + "'" +
            '}';
    }
}
