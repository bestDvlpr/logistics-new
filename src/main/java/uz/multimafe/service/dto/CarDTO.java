package uz.multimafe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Car entity.
 */
public class CarDTO implements Serializable {

    private Long id;

    @NotNull
    private String number;

    @NotNull
    private Boolean deleted;

    private Long carModelId;

    private String carModelName;

    private Long carColorId;

    private String carColorName;

    private Long typeId;

    private String typeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Long carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public Long getCarColorId() {
        return carColorId;
    }

    public void setCarColorId(Long carColorId) {
        this.carColorId = carColorId;
    }

    public String getCarColorName() {
        return carColorName;
    }

    public void setCarColorName(String carColorName) {
        this.carColorName = carColorName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long carTypeId) {
        this.typeId = carTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String carTypeName) {
        this.typeName = carTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarDTO carDTO = (CarDTO) o;

        if ( ! Objects.equals(id, carDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarDTO{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", deleted='" + deleted + "'" +
            '}';
    }
}
