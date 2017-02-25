package uz.multimafe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = "(\\+\\d{12})")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mobile_id")
    private String mobileId;

    @ManyToMany
    @NotNull
    @JoinTable(name = "driver_cars",
               joinColumns = @JoinColumn(name="drivers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="cars_id", referencedColumnName="id"))
    private Set<Car> cars = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private DriverStatus status;

    @ManyToMany(mappedBy = "drivers")
    @JsonIgnore
    private Set<Receipt> receipts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public Driver phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Driver address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileId() {
        return mobileId;
    }

    public Driver mobileId(String mobileId) {
        this.mobileId = mobileId;
        return this;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Driver cars(Set<Car> cars) {
        this.cars = cars;
        return this;
    }

    public Driver addCars(Car car) {
        this.cars.add(car);
        car.getDrivers().add(this);
        return this;
    }

    public Driver removeCars(Car car) {
        this.cars.remove(car);
        car.getDrivers().remove(this);
        return this;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public Driver status(DriverStatus driverStatus) {
        this.status = driverStatus;
        return this;
    }

    public void setStatus(DriverStatus driverStatus) {
        this.status = driverStatus;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public Driver receipts(Set<Receipt> receipts) {
        this.receipts = receipts;
        return this;
    }

    public Driver addReceipts(Receipt receipt) {
        this.receipts.add(receipt);
        receipt.getDrivers().add(this);
        return this;
    }

    public Driver removeReceipts(Receipt receipt) {
        this.receipts.remove(receipt);
        receipt.getDrivers().remove(this);
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
        Driver driver = (Driver) o;
        if (driver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + id +
            ", phone='" + phone + "'" +
            ", address='" + address + "'" +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", mobileId='" + mobileId + "'" +
            '}';
    }
}
