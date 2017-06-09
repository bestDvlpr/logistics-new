import {Component, OnInit} from "@angular/core";
import {ReportService} from "../report.service";
import {ProductDeliveryReport} from "../product-delivery-report.model";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {JhiLanguageService} from "ng-jhipster";
import {Company} from "../../entities/company/company.model";
import {CompanyService} from "../../entities/company/company.service";
import {LocationService} from "../../entities/location/location.service";
import {EnumAware} from "../../entities/receipt/doctypaware.decorator";
import {Location, LocationType} from "../../entities/location/location.model";
import {CommonReportCriteria} from "../report.criteria";
import {ACElement} from "../../shared/autocomplete/element.model";
import {isNullOrUndefined} from "util";
/**
 * @author: hasan @date: 6/3/17.
 */
@Component({
    selector: 'jhi-overall-report',
    templateUrl: './overall.report.component.html'
})
@EnumAware
export class OverallReportComponent implements OnInit {

    reports: ProductDeliveryReport[];
    fromMinDate: NgbDateStruct;
    fromMaxDate: NgbDateStruct;
    toMinDate: NgbDateStruct;
    toMaxDate: NgbDateStruct;
    startTime: any;
    endTime: any;
    selectedCompany: string;
    selectedRegion: string;
    companies: Company[];
    districts: Location[];
    companiesSource: string[] = [];
    regionsSource: string[] = [];
    private locationType = LocationType;

    constructor(private reportService: ReportService,
                private companyService: CompanyService,
                private locationService: LocationService,
                private jhiLanguageService: JhiLanguageService) {
    }

    ngOnInit() {
        this.getAllCompanies();
        this.getAllDistricts();
        this.jhiLanguageService.setLocations(
            [
                'report',
                'receipt',
                'docType',
                'wholeSaleFlag',
                'productEntry',
                'product',
                'client',
                'phoneNumber',
                'address',
                'car',
                'carModel',
                'driver',
                'receiptStatus',
                'salesType'
            ]
        );
        this.getGenericReport();
    }

    trackId(index: number, item: ProductDeliveryReport) {
        return item.id;
    }

    filter() {

    }

    private getAllCompanies() {
        this.companyService.all().subscribe((res) => {
            this.companies = res.json();
            this.companies.filter(x => this.companiesSource.push(x.name));
        });
    }

    private getAllDistricts() {
        this.locationService.findByType(this.locationType.DISTRICT).subscribe((res) => {
            this.districts = res.json();
            this.districts.filter(x => this.regionsSource.push(x.name));
        });
    }

    getGenericReport() {
        let startDate: string;
        let endDate: string;
        if (!isNullOrUndefined(this.startTime) && !isNullOrUndefined(this.endTime)) {
            startDate = this.startTime.year +
                '-' + ((this.startTime.month < 10) ? '0' + this.startTime.month : this.startTime.month) +
                '-' + ((this.startTime.day < 10) ? '0' + this.startTime.day : this.startTime.day);
            endDate = this.endTime.year +
                '-' + ((this.endTime.month < 10) ? '0' + this.endTime.month : this.endTime.month) +
                '-' + ((this.endTime.day < 10) ? '0' + this.endTime.day : this.endTime.day);
        }

        let district: ACElement = null;
        let company: ACElement = null;

        if (this.selectedRegion === "") {
            this.selectedRegion = null;
        }

        if (this.selectedCompany === "") {
            this.selectedCompany = null;
        }

        if (!isNullOrUndefined(this.companies) && !isNullOrUndefined(this.selectedCompany)) {
            let chosenCompany = this.companies.find(c => c.name === this.selectedCompany);
            company = new ACElement(chosenCompany.id, chosenCompany.name);
        }

        if (!isNullOrUndefined(this.districts) && !isNullOrUndefined(this.selectedRegion)) {
            let chosenDistrict = this.districts.find(r => r.name === this.selectedRegion);
            district = new ACElement(chosenDistrict.id, chosenDistrict.name);
        }

        let criteria = new CommonReportCriteria(startDate, endDate, company, district);

        this.reportService.getGenericReport(criteria).subscribe((res) => {
            this.reports = [];
            this.reports = res.json();
        });
    }
}
