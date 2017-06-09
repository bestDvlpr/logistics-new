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
import {Response} from "@angular/http";
import {isNullOrUndefined} from "util";
import * as FileSaver from "file-saver";
import {DataHolderService} from "../../entities/receipt/data-holder.service";
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
    startTime: any = null;
    endTime: any = null;
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
        let criteria = this.createReportCriteria();

        this.reportService.getGenericReport(criteria).subscribe((res) => {
            this.reports = [];
            this.reports = res.json();
        });
    }

    exportGenericReport() {
        let criteria = this.createReportCriteria();
        this.reportService.downloadGenericReport(criteria).subscribe((res: Response) => {
            this.onSuccessExport(res);
        });
    }

    private onSuccessExport(res: Response) {
        let mediaType = 'application/octet-stream;charset=UTF-8';
        let blob = new Blob([res.blob()], {type: mediaType});
        let filename: string = '';
        if (this.startTime === null) {
            filename = DataHolderService.format(new Date()) + '_report.xlsx';
        } else {
            filename = DataHolderService.formatYYYYMMDD(this.startTime) + '-' + DataHolderService.formatYYYYMMDD(this.endTime) + '_report.xlsx';
        }

        try {
            window.navigator.msSaveOrOpenBlob(blob, filename);
        } catch (ex) {
            FileSaver.saveAs(blob, filename);
        }
    }

    private createReportCriteria() {
        let startDate: string;
        let endDate: string;
        if (!isNullOrUndefined(this.startTime) && !isNullOrUndefined(this.endTime)) {
            startDate = DataHolderService.formatYYYYMMDD(this.startTime);
            endDate = DataHolderService.formatYYYYMMDD(this.endTime);
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

        return new CommonReportCriteria(startDate, endDate, company, district);
    }
}
