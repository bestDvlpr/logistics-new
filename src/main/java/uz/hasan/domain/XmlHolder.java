package uz.hasan.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A XmlHolder.
 */
@Entity
@Table(name = "xml_holder")
public class XmlHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 10000)
    @Column(name = "xml_content", length = 10000, nullable = false)
    private String xmlContent;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "checked")
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

    public XmlHolder xmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
        return this;
    }

    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public XmlHolder date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Boolean isChecked() {
        return checked;
    }

    public XmlHolder checked(Boolean checked) {
        this.checked = checked;
        return this;
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
        XmlHolder xmlHolder = (XmlHolder) o;
        if (xmlHolder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, xmlHolder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "XmlHolder{" +
            "id=" + id +
            ", xmlContent='" + xmlContent + "'" +
            ", date='" + date + "'" +
            ", checked='" + checked + "'" +
            '}';
    }
}
