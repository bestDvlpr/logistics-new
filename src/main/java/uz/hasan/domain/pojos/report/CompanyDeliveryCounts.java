package uz.hasan.domain.pojos.report;

import java.util.List;
import java.util.Set;

/**
 * @author: hasan @date: 6/13/17.
 */
public class CompanyDeliveryCounts {

    private String companyName;
    private Set<CountByDate> map;

    public CompanyDeliveryCounts(String companyName, Set<CountByDate> map) {
        this.companyName = companyName;
        this.map = map;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<CountByDate> getMap() {
        return map;
    }

    public void setMap(Set<CountByDate> map) {
        this.map = map;
    }
}
