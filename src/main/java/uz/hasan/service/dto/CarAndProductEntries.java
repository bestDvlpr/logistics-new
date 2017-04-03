package uz.hasan.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: hasan @date: 3/25/17.
 */
public class CarAndProductEntries extends CarDTO {
    private List<ProductEntryDTO> productEntries;

    public List<ProductEntryDTO> getProductEntries() {
        return productEntries;
    }

    public void setProductEntries(List<ProductEntryDTO> productEntries) {
        this.productEntries = productEntries;
    }
}
