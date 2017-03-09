package uz.hasan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @ManyToOne(optional = false)
    @NotNull
    private Location country;

    @ManyToOne(optional = false)
    @NotNull
    private Location region;

    @ManyToOne
    private Location city;

    @ManyToOne(optional = false)
    @NotNull
    private Location district;

    @ManyToOne
    private Client client;

    @ManyToMany(mappedBy = "addresses")
    @JsonIgnore
    private Set<Receipt> receipts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Address streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public Address latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Address longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Location getCountry() {
        return country;
    }

    public Address country(Location location) {
        this.country = location;
        return this;
    }

    public void setCountry(Location location) {
        this.country = location;
    }

    public Location getRegion() {
        return region;
    }

    public Address region(Location location) {
        this.region = location;
        return this;
    }

    public void setRegion(Location location) {
        this.region = location;
    }

    public Location getCity() {
        return city;
    }

    public Address city(Location location) {
        this.city = location;
        return this;
    }

    public void setCity(Location location) {
        this.city = location;
    }

    public Location getDistrict() {
        return district;
    }

    public Address district(Location location) {
        this.district = location;
        return this;
    }

    public void setDistrict(Location location) {
        this.district = location;
    }

    public Client getClient() {
        return client;
    }

    public Address client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public Address receipts(Set<Receipt> receipts) {
        this.receipts = receipts;
        return this;
    }

    public Address addReceipts(Receipt receipt) {
        this.receipts.add(receipt);
        receipt.getAddresses().add(this);
        return this;
    }

    public Address removeReceipts(Receipt receipt) {
        this.receipts.remove(receipt);
        receipt.getAddresses().remove(this);
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
        Address address = (Address) o;
        if (address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", streetAddress='" + streetAddress + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            '}';
    }
}
