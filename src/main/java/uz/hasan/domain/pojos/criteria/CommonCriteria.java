package uz.hasan.domain.pojos.criteria;

/**
 * @author: hasan @date: 6/5/17.
 */
public class CommonCriteria {
    private String startDate;
    private String endDate;

    public CommonCriteria() {
    }

    public CommonCriteria(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
