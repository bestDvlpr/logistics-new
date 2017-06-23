import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {Account, LoginModalService, Principal} from "../shared";
import {DeliveryCountByCompany} from "../report/delivery-count-by-ompany";
import {LineChartData} from "../report/line-chart-data.model";
import {CommonReportCriteria} from "../report/report.criteria";
import {ReportService} from "../report/report.service";
import {DataHolderService} from "../entities/receipt/data-holder.service";
import {TranslateService} from "ng2-translate";
import {ReceiptStatus} from "../entities/receipt/receipt.model";
import {JhiLanguageHelper} from "../shared/language/language.helper";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    overallCountByCompany: DeliveryCountByCompany[];
    countByCompany: LineChartData[];
    criteriaForAll: CommonReportCriteria;
    criteriaForByCompany: CommonReportCriteria;
    pieChartOptions: any = null;
    lineChartOptions: any = null;
    startTime: any;
    endTime: any;
    languages: any[];
    w = window;

    @ViewChild('deliveryAll') deliveryDiv: ElementRef;
    @ViewChild('deliveryCountAll') deliveryCountDiv: ElementRef;

    constructor(private languageHelper: JhiLanguageHelper,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private reportService: ReportService,
                private dataHolderService: DataHolderService,
                private translateService: TranslateService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
        this.deliveryCountChart();
        this.deliveryCountByCompanyChart();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe("authenticationSuccess", (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    deliveryCountChart() {
        this.reportService.deliveryCountChart(ReceiptStatus.DELIVERED, null, null).subscribe((res) => {
            this.overallCountByCompany = res;
            const data = [];
            const overall = this.overallCountByCompany.find((x) => x.companyName === null);
            for (const a of this.overallCountByCompany) {
                if (a.companyName !== null) {
                    data.push({
                        name: a.companyName + ' ' + (Math.round((a.count / overall.count) * 10000) / 100) + ' %',
                        y: a.count
                    });
                }
            }

            this.pieChartOptions = {
                title: {text: this.translateService.instant('global.messages.report.delivery.deliveryStats')},
                series: [{
                    name: this.translateService.instant('global.messages.report.delivery.byCompany'),
                    data
                }],
                chart: {
                    type: 'pie',
                    width: this.deliveryDiv.nativeElement.offsetWidth / 12 * 10,
                    height: this.w.innerHeight / 12 * 7
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<span style="font-size: 14px; font-weight: bold;">{point.key}</span><br/>',
                    style: {
                        fontSize: '15px'
                    }
                }
            };
        });
    }

    deliveryCountByCompanyChart() {
        if (this.startTime && this.endTime) {
            const startDate = DataHolderService.formatYYYYMMDD(this.startTime);
            const endDate = DataHolderService.formatYYYYMMDD(this.endTime);
            this.criteriaForByCompany = new CommonReportCriteria();
            this.criteriaForByCompany.startDate = startDate;
            this.criteriaForByCompany.endDate = endDate;
        }
        this.reportService.countByCompany(this.criteriaForByCompany).subscribe((res) => {
            this.countByCompany = res;
            const series: any[] = [];
            const categories = [];
            const companyNames = [];
            for (const a of this.countByCompany) {
                companyNames.push(a.companyName);
                const data = [];
                for (const b of a.map) {
                    data.push(b.count);
                    categories.push(b.name);
                }
                series.push({name: a.companyName, data});
            }

            this.lineChartOptions = {
                title: {text: this.translateService.instant('global.messages.report.delivery.dailyCountByCompany')},
                series,
                xAxis: [{
                    categories,
                    crosshair: true,
                    labels: {
                        style: {
                            fontSize: '15px'
                        }
                    }
                }], chart: {
                    type: 'line',
                    width: this.deliveryCountDiv.nativeElement.offsetWidth / 12 * 10,
                    height: this.w.innerHeight / 12 * 7
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<span style="font-size: 12px; font-weight: bold;">{point.key}</span><br/>',
                    useHTML: true,
                    style: {
                        fontSize: '15px'
                    }
                }
            };
        });
    }

}
