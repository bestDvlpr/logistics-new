package uz.hasan.domain.pojos.criteria;

/**
 * @author: hasan @date: 6/5/17.
 */
public class DeliveryReportCriteria extends CommonCriteria {
    private CustomCompany company;
    private CustomDistrict district;

    public DeliveryReportCriteria() {
    }

    public DeliveryReportCriteria(CustomCompany company, CustomDistrict district) {
        this.company = company;
        this.district = district;
    }

    public DeliveryReportCriteria(String startDate, String endDate, CustomCompany company, CustomDistrict district) {
        super(startDate, endDate);
        this.company = company;
        this.district = district;
    }

    public CustomCompany getCompany() {
        return company;
    }

    public void setCompany(CustomCompany company) {
        this.company = company;
    }

    public CustomDistrict getDistrict() {
        return district;
    }

    public void setDistrict(CustomDistrict district) {
        this.district = district;
    }

    public Long getCompanyId() {
        if (this.company == null) {
            return null;
        }
        return this.company.getId();
    }

    public Long getDistrictId() {
        if (this.district == null) {
            return null;
        }
        return this.district.getId();
    }

    public String getCompanyName() {
        if (this.company == null) {
            return null;
        }
        return this.company.getName();
    }

    public String getDistrictName() {
        if (this.district == null) {
            return null;
        }
        return this.district.getName();
    }
}
