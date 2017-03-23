package uz.hasan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import uz.hasan.domain.enumeration.CarStatus;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CarStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private CarModel carModel;

    @ManyToOne
    private CarColor carColor;

    @ManyToOne(optional = false)
    @NotNull
    private CarType type;

    @ManyToMany(mappedBy = "cars")
    @JsonIgnore
    private Set<Driver> drivers = new HashSet<>();

    @ManyToMany(mappedBy = "cars")
    @JsonIgnore
    private Set<Receipt> receipts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Car number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Car deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CarStatus getStatus() {
        return status;
    }

    public Car status(CarStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public Car carModel(CarModel carModel) {
        this.carModel = carModel;
        return this;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public CarColor getCarColor() {
        return carColor;
    }

    public Car carColor(CarColor carColor) {
        this.carColor = carColor;
        return this;
    }

    public void setCarColor(CarColor carColor) {
        this.carColor = carColor;
    }

    public CarType getType() {
        return type;
    }

    public Car type(CarType carType) {
        this.type = carType;
        return this;
    }

    public void setType(CarType carType) {
        this.type = carType;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public Car drivers(Set<Driver> drivers) {
        this.drivers = drivers;
        return this;
    }

    public Car addDrivers(Driver driver) {
        this.drivers.add(driver);
        driver.getCars().add(this);
        return this;
    }

    public Car removeDrivers(Driver driver) {
        this.drivers.remove(driver);
        driver.getCars().remove(this);
        return this;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public Car receipts(Set<Receipt> receipts) {
        this.receipts = receipts;
        return this;
    }

    public Car addReceipts(Receipt receipt) {
        this.receipts.add(receipt);
        receipt.getCars().add(this);
        return this;
    }

    public Car removeReceipts(Receipt receipt) {
        this.receipts.remove(receipt);
        receipt.getCars().remove(this);
        return this;
    }

    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", deleted='" + deleted + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
