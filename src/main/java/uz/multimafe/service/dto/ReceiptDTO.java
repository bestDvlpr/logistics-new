package uz.multimafe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Receipt entity.
 */
public class ReceiptDTO implements Serializable {

    private Long id;

    @NotNull
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

        ReceiptDTO receiptDTO = (ReceiptDTO) o;

        if ( ! Objects.equals(id, receiptDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "id=" + id +
            ", docNum='" + docNum + "'" +
            '}';
    }
}
