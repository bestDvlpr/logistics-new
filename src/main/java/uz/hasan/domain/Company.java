package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import uz.hasan.domain.enumeration.CompanyType;
import uz.hasan.domain.pojos.criteria.CustomCompany;

/**
 * A Company.
 */
@Entity
@SqlResultSetMapping(
    name = "CustomCompanyMapping",
    classes = {
        @ConstructorResult(
            targetClass = CustomCompany.class,
            columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "idNumber", type = String.class),
                @ColumnResult(name = "name", type = String.class)
            }
        )
    }
)
@NamedNativeQuery(name = "Company.customCompany",
    query = "SELECT * FROM f_custom_company(COALESCE(NULLIF(?1, 'null')), COALESCE(NULLIF(?2, 'null')), COALESCE(NULLIF(?3, 'null')))",
    resultClass = CustomCompany.class,
    resultSetMapping = "CustomCompanyMapping"
)
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "id_number")
    private String idNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CompanyType type;

    @ManyToOne
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public Company idNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public CompanyType getType() {
        return type;
    }

    public Company type(CompanyType type) {
        this.type = type;
        return this;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public Company address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", idNumber='" + idNumber + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
