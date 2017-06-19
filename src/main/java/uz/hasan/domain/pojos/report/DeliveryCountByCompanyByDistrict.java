package uz.hasan.domain.pojos.report;

import java.util.List;

/**
 * @author: hasan @date: 6/17/17.
 */
public class DeliveryCountByCompanyByDistrict {
    private String districtName;
    private List<ByDistrict> countByCompanies;

    public DeliveryCountByCompanyByDistrict() {
    }

    public DeliveryCountByCompanyByDistrict(String districtName, List<ByDistrict> countByCompanies) {
        this.districtName = districtName;
        this.countByCompanies = countByCompanies;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public List<ByDistrict> getCountByCompanies() {
        return countByCompanies;
    }

    public void setCountByCompanies(List<ByDistrict> countByCompanies) {
        this.countByCompanies = countByCompanies;
    }
}
