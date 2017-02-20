package uz.multimafe.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Receipt.
 */
@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "doc_num", nullable = false)
    private String docNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocNum() {
        return docNum;
    }

    public Receipt docNum(String docNum) {
        this.docNum = docNum;
        return this;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Receipt receipt = (Receipt) o;
        if (receipt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, receipt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Receipt{" +
            "id=" + id +
            ", docNum='" + docNum + "'" +
            '}';
    }
}
