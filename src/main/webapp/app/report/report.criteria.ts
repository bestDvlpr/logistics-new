import {ACElement} from "../shared/autocomplete/element.model";
/**
 * Created by hasan on 6/6/17.
 */
export class ReportCriteria {
    startDate: string;
    endDate: string;

    constructor(startDate: string, endDate: string) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

export class CommonReportCriteria extends ReportCriteria {
    company: ACElement;
    district: ACElement;

    constructor(startDate: string, endDate: string, company: ACElement, district: ACElement) {
        super(startDate, endDate);
        this.company = company;
        this.district = district;
    }
}
