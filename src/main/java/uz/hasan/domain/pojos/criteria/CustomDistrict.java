package uz.hasan.domain.pojos.criteria;

/**
 * @author: hasan @date: 6/8/17.
 */
public class CustomDistrict {
    private Long id;
    private String name;

    public CustomDistrict() {
    }

    public CustomDistrict(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
