package uz.hasan.domain.pojos.report;

/**
 * @author: hasan @date: 6/10/17.
 */
public class DeliveryCountByCompany {

    private String companyId;
    private String companyName;
    private String date;
    private Long count;

    public DeliveryCountByCompany() {

    }

    public DeliveryCountByCompany(String companyId, String companyName, Long count) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.count = count;
    }

    public DeliveryCountByCompany(String companyId, String companyName, String date, Long count) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
