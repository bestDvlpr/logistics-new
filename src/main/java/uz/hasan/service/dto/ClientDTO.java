package uz.hasan.service.dto;


import javax.validation.constraints.NotNull;
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

    @NotNull
    private Set<String> phoneNumbers;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;

        if (!Objects.equals(id, clientDTO.id)) {
            return false;
        }

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
            '}';
    }
}
