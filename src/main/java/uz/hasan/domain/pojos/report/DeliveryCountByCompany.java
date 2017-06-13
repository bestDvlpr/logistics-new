package uz.hasan.domain.pojos.report;

/**
 * @author: hasan @date: 6/10/17.
 */
public class DeliveryCountByCompany {

    private String companyId;
    private String companyName;
    private Long count;

    public DeliveryCountByCompany() {

    }

    public DeliveryCountByCompany(String companyId, String companyName, Long count) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.count = count;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
