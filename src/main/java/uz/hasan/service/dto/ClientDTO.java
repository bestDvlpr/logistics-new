package uz.hasan.service.dto;


import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Client entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private ZonedDateTime regDate;

    private Set<String> phoneNumbers;

    private String bankName;

    private String bankFilialRegion;

    @Size(min = 16, max = 24)
    private String bankAccountNumber;

    @Size(min = 5, max = 5)
    private String mfo;

    @Size(min = 9, max = 9)
    private String tin;

    private String okonx;

    private String oked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ZonedDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(ZonedDateTime regDate) {
        this.regDate = regDate;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankFilialRegion() {
        return bankFilialRegion;
    }

    public void setBankFilialRegion(String bankFilialRegion) {
        this.bankFilialRegion = bankFilialRegion;
    }
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }
    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }
    public String getOkonx() {
        return okonx;
    }

    public void setOkonx(String okonx) {
        this.okonx = okonx;
    }
    public String getOked() {
        return oked;
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

        ClientDTO clientDTO = (ClientDTO) o;

        if ( ! Objects.equals(id, clientDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
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
