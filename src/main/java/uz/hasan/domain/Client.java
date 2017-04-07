package uz.hasan.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "reg_date")
    private ZonedDateTime regDate;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Address> addresses = new HashSet<>();

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_filial_region")
    private String bankFilialRegion;

    @Size(min = 16, max = 24)
    @Column(name = "bank_account_number", length = 24)
    private String bankAccountNumber;

    @Size(min = 5, max = 5)
    @Column(name = "mfo", length = 5)
    private String mfo;

    @Size(min = 9, max = 9)
    @Column(name = "tin", length = 9)
    private String tin;

    @Column(name = "okonx")
    private String okonx;

    @Column(name = "oked")
    private String oked;

    public Client() {
    }

    public Client(Long clientId) {
        this.id = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Client firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Client lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getRegDate() {
        return regDate;
    }

    public Client regDate(ZonedDateTime regDate) {
        this.regDate = regDate;
        return this;
    }

    public void setRegDate(ZonedDateTime regDate) {
        this.regDate = regDate;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getBankName() {
        return bankName;
    }

    public Client bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankFilialRegion() {
        return bankFilialRegion;
    }

    public Client bankFilialRegion(String bankFilialRegion) {
        this.bankFilialRegion = bankFilialRegion;
        return this;
    }

    public void setBankFilialRegion(String bankFilialRegion) {
        this.bankFilialRegion = bankFilialRegion;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Client bankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getMfo() {
        return mfo;
    }

    public Client mfo(String mfo) {
        this.mfo = mfo;
        return this;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getTin() {
        return tin;
    }

    public Client tin(String tin) {
        this.tin = tin;
        return this;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getOkonx() {
        return okonx;
    }

    public Client okonx(String okonx) {
        this.okonx = okonx;
        return this;
    }

    public void setOkonx(String okonx) {
        this.okonx = okonx;
    }

    public String getOked() {
        return oked;
    }

    public Client oked(String oked) {
        this.oked = oked;
        return this;
    }

    public void setOked(String oked) {
        this.oked = oked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if (client.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", regDate='" + regDate + "'" +
            ", bankName='" + bankName + "'" +
            ", bankFilialRegion='" + bankFilialRegion + "'" +
            ", bankAccountNumber='" + bankAccountNumber + "'" +
            ", mfo='" + mfo + "'" +
            ", tin='" + tin + "'" +
            ", okonx='" + okonx + "'" +
            ", oked='" + oked + "'" +
            '}';
    }
}
