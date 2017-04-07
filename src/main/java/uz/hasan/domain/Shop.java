package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Shop.
 */
@Entity
@Table(name = "shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "shop_id", nullable = false)
    private String shopId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 16, max = 24)
    @Column(name = "bank_account_number", length = 24)
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_branch_region")
    private String bankBranchRegion;

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

    @ManyToOne
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public Shop shopId(String shopId) {
        this.shopId = shopId;
        return this;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public Shop name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Shop bankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public Shop bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranchRegion() {
        return bankBranchRegion;
    }

    public Shop bankBranchRegion(String bankBranchRegion) {
        this.bankBranchRegion = bankBranchRegion;
        return this;
    }

    public void setBankBranchRegion(String bankBranchRegion) {
        this.bankBranchRegion = bankBranchRegion;
    }

    public String getMfo() {
        return mfo;
    }

    public Shop mfo(String mfo) {
        this.mfo = mfo;
        return this;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getTin() {
        return tin;
    }

    public Shop tin(String tin) {
        this.tin = tin;
        return this;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getOkonx() {
        return okonx;
    }

    public Shop okonx(String okonx) {
        this.okonx = okonx;
        return this;
    }

    public void setOkonx(String okonx) {
        this.okonx = okonx;
    }

    public String getOked() {
        return oked;
    }

    public Shop oked(String oked) {
        this.oked = oked;
        return this;
    }

    public void setOked(String oked) {
        this.oked = oked;
    }

    public Address getAddress() {
        return address;
    }

    public Shop address(Address address) {
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
        Shop shop = (Shop) o;
        if (shop.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shop{" +
            "id=" + id +
            ", shopId='" + shopId + "'" +
            ", name='" + name + "'" +
            ", bankAccountNumber='" + bankAccountNumber + "'" +
            ", bankName='" + bankName + "'" +
            ", bankBranchRegion='" + bankBranchRegion + "'" +
            ", mfo='" + mfo + "'" +
            ", tin='" + tin + "'" +
            ", okonx='" + okonx + "'" +
            ", oked='" + oked + "'" +
            '}';
    }
}
