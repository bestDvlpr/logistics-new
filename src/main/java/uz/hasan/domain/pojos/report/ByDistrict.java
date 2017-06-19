package uz.hasan.domain.pojos.report;

/**
 * @author: hasan @date: 6/10/17.
 */
public class ByDistrict {

    private String companyId;
    private String companyName;
    private String districtName;
    private String districtName2;
    private Long count;

    public ByDistrict() {

    }

    public ByDistrict(String companyId, String companyName, String districtName, String districtName2, Long count) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.districtName = districtName;
        this.districtName2 = districtName2;
        this.count = count;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictName2() {
        return districtName2;
    }

    public void setDistrictName2(String districtName2) {
        this.districtName2 = districtName2;
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
