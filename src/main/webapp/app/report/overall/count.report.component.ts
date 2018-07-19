import {Component, OnInit} from '@angular/core';
import {ReportService} from '../report.service';
import {ProductDeliveryReport} from '../product-delivery-report.model';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiParseLinks} from 'ng-jhipster';
import {Company} from '../../entities/company/company.model';
import {CompanyService} from '../../entities/company/company.service';
import {LocationService} from '../../entities/location/location.service';
import {EnumAware} from '../../entities/receipt/doctypaware.decorator';
import {Location, LocationType} from '../../entities/location/location.model';
import {CountReportCriteria} from '../report.criteria';
import {ACElement} from '../../shared/autocomplete/element.model';
import {Response} from '@angular/http';
import {isNullOrUndefined} from 'util';
import * as FileSaver from 'file-saver';
import {DataHolderService} from '../../entities/receipt/data-holder.service';
import {ITEMS_PER_PAGE} from '../../shared/constants/pagination.constants';
import {ActivatedRoute, Router} from '@angular/router';
import {ReceiptStatus} from '../../entities/receipt/receipt.model';
import {TranslateService} from '@ngx-translate/core';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
/**
 * @author: hasan @date: 6/3/17.
 */
@Component({
    selector: 'jhi-count-report',
    templateUrl: './count.report.component.html'
})
@EnumAware
export class CountReportComponent implements OnInit {

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
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    chartOptions = [];
    countByDistrict: any[];
    receiptStatus: ReceiptStatus;
    languages: any[];

    constructor(private reportService: ReportService,
                private companyService: CompanyService,
                private translateService: TranslateService,
                private locationService: LocationService,
                private activatedRoute: ActivatedRoute,
                private parseLinks: JhiParseLinks,
                private router: Router,
                private dataholderService: DataHolderService,
                private alertService: JhiAlertService,
                private languageHelper: JhiLanguageHelper) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.getAllCompanies();
        this.getAllDistricts();
        this.countByStatus();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    trackId(index: number, item: ProductDeliveryReport) {
        return item.id;
    }

    filter() {

    }

    private getAllCompanies() {
        this.companyService.all().subscribe((res) => {
            this.companies = res.json();
            this.companies.filter((x) => this.companiesSource.push(x.name));
        });
    }

    private getAllDistricts() {
        this.locationService.findByType(this.locationType.DISTRICT).subscribe((res) => {
            this.districts = res.json();
            this.districts.filter((x) => this.regionsSource.push(x.name));
        });
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.reports = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    exportGenericReport() {
        const criteria = this.createReportCriteria();
        this.reportService.downloadGenericReport(criteria).subscribe((res: Response) => {
            this.onSuccessExport(res);
        });
    }

    private onSuccessExport(res: Response) {
        const mediaType = 'application/octet-stream;charset=UTF-8';
        const blob = new Blob([res.blob()], {type: mediaType});
        let filename = '';
        if (this.startTime === null) {
            filename = DataHolderService.format(new Date()) + '_report.xlsx';
        } else {
            filename = DataHolderService.formatYYYYMMDD(this.startTime) + '-' + DataHolderService.formatYYYYMMDD(this.endTime) + '_report.xlsx';
            let a = res['name'];
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

        if (this.selectedRegion === '') {
            this.selectedRegion = null;
        }

        if (this.selectedCompany === '') {
            this.selectedCompany = null;
        }

        if (!isNullOrUndefined(this.companies) && !isNullOrUndefined(this.selectedCompany)) {
            const chosenCompany = this.companies.find((c) => c.name === this.selectedCompany);
            company = new ACElement(chosenCompany.id, chosenCompany.name);
        }

        if (!isNullOrUndefined(this.districts) && !isNullOrUndefined(this.selectedRegion)) {
            const chosenDistrict = this.districts.find((r) => r.name === this.selectedRegion);
            district = new ACElement(chosenDistrict.id, chosenDistrict.name);
        }

        return new CountReportCriteria(startDate, endDate, company, district, this.receiptStatus);
    }

    countByStatus() {
        const w = window;
        const criteria = this.createReportCriteria();
        this.reportService.countByStatus(criteria).subscribe((res: Response) => {
            this.chartOptions = [];
            for (const a of res.json()) {
                const items = this.dataholderService.drawChart(a);
                items.chart.width = w.innerWidth / 12 * 5;
                items.chart.height = w.innerHeight / 12 * 5;
                this.chartOptions.push(items);
                const items2 = this.dataholderService.drawColumnChart(a);
                this.chartOptions.push(items2);
            }
            console.log(res.json());
            console.log(this.chartOptions);
            this.countByDistrict = res.json();
        });
    }
}
