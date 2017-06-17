package uz.hasan.domain.pojos.report;

/**
 * @author: hasan @date: 6/10/17.
 */
public class ByDistrict {

    private String companyId;
    private String companyName;
    private String districtName;
    private Long count;

    public ByDistrict() {

    }

    public ByDistrict(String companyId, String companyName, String districtName, Long count) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.districtName = districtName;
        this.count = count;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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
