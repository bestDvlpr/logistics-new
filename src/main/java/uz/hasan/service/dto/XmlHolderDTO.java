package uz.hasan.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the XmlHolder entity.
 */
public class XmlHolderDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10000)
    private String xmlContent;

    private ZonedDateTime date;

    private Boolean checked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlHolderDTO xmlHolderDTO = (XmlHolderDTO) o;

        if ( ! Objects.equals(id, xmlHolderDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "XmlHolderDTO{" +
            "id=" + id +
            ", xmlContent='" + xmlContent + "'" +
            ", date='" + date + "'" +
            ", checked='" + checked + "'" +
            '}';
    }
}
