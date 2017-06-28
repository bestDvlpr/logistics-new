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
import {CommonReportCriteria} from '../report.criteria';
import {ACElement} from '../../shared/autocomplete/element.model';
import {Response} from '@angular/http';
import {isNullOrUndefined} from 'util';
import * as FileSaver from 'file-saver';
import {DataHolderService} from '../../entities/receipt/data-holder.service';
import {ITEMS_PER_PAGE} from '../../shared/constants/pagination.constants';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
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
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    languages: any[];

    constructor(private reportService: ReportService,
                private companyService: CompanyService,
                private locationService: LocationService,
                private activatedRoute: ActivatedRoute,
                private parseLinks: JhiParseLinks,
                private router: Router,
                private alertService: JhiAlertService,
                private languageHelper: JhiLanguageHelper,
                private paginationConfig: PaginationConfig) {
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
        this.getGenericReport();
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

    getGenericReport() {
        const criteria = this.createReportCriteria();

        this.reportService.getGenericReport(criteria, {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
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

    transition() {
        this.router.navigate(['/overall'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.getGenericReport();
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
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

        return new CommonReportCriteria(startDate, endDate, company, district);
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
}
