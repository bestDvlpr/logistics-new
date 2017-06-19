import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {EventManager, JhiLanguageService} from "ng-jhipster";
import {Account, LoginModalService, Principal} from "../shared";
import {ReportService} from "../report/report.service";
import {ReceiptStatus} from "../entities/receipt/receipt.model";
import {DeliveryCountByCompany} from "../report/delivery-count-by-ompany";
import {TranslateService} from "ng2-translate";
import {CommonReportCriteria} from "../report/report.criteria";
import {LineChartData} from "../report/line-chart-data.model";
import {DataHolderService} from "../entities/receipt/data-holder.service";

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

    @ViewChild('deliveryAll') deliveryDiv: ElementRef;
    @ViewChild('deliveryCountAll') deliveryCountDiv: ElementRef;

    constructor(private jhiLanguageService: JhiLanguageService,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private reportService: ReportService,
                private dataHolderService: DataHolderService,
                private translateService: TranslateService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['home', 'global']);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.deliveryCountChart();
        this.deliveryCountByCompanyChart();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
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
            let data = [];
            let overall = this.overallCountByCompany.find(x => x.companyName === null);
            for (let a of this.overallCountByCompany) {
                if (a.companyName !== null) {
                    data.push({
                        name: a.companyName + ' ' + (Math.round((a.count / overall.count) * 10000) / 100) + ' %',
                        y: a.count
                    });
                }
            }

            var w = window,
                d = document,
                e = d.documentElement,
                g = d.getElementsByTagName('body')[0],
                x = w.innerWidth || e.clientWidth || g.clientWidth,
                y = w.innerHeight || e.clientHeight || g.clientHeight;
            // alert(x + ' × ' + y);

            this.pieChartOptions = {
                title: {text: this.translateService.instant('global.messages.report.delivery.deliveryStats')},
                series: [{
                    name: this.translateService.instant('global.messages.report.delivery.byCompany'),
                    data: data
                }],
                chart: {
                    type: 'pie',
                    width: this.deliveryDiv.nativeElement.offsetWidth / 12 * 10,
                    height: w.innerHeight / 12 * 7
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    headerFormat: '<span style="font-size: 14px; font-weight: bold;">{point.key}</span><br/>',
                    style:{
                        fontSize: '15px'
                    }
                }
            }
        })
    }

    deliveryCountByCompanyChart() {
        if (this.startTime && this.endTime) {
            let startDate = DataHolderService.formatYYYYMMDD(this.startTime);
            let endDate = DataHolderService.formatYYYYMMDD(this.endTime);
            this.criteriaForByCompany = new CommonReportCriteria();
            this.criteriaForByCompany.startDate = startDate;
            this.criteriaForByCompany.endDate = endDate;
        }
        this.reportService.countByCompany(this.criteriaForByCompany).subscribe((res) => {
            this.countByCompany = res;
            console.log(this.countByCompany);
            let series: any[] = [];
            let categories = [];
            let companyNames = [];
            for (let a of this.countByCompany) {
                companyNames.push(a.companyName);
                let data = [];
                for (let b of a.map) {
                    data.push(b.count);
                    categories.push(b.name);
                }
                series.push({name: a.companyName, data: data})
            }
            var w = window,
                d = document,
                e = d.documentElement,
                g = d.getElementsByTagName('body')[0],
                x = w.innerWidth || e.clientWidth || g.clientWidth,
                y = w.innerHeight || e.clientHeight || g.clientHeight;
            this.lineChartOptions = {
                title: {text: this.translateService.instant('global.messages.report.delivery.dailyCountByCompany')},
                series: series,
                xAxis: [{
                    categories: categories,
                    crosshair: true,
                    labels: {
                        style: {
                            fontSize:'15px'
                        }
                    }
                }], chart: {
                    type: 'line',
                    width: this.deliveryCountDiv.nativeElement.offsetWidth / 12 * 10,
                    height: w.innerHeight / 12 * 7
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
            console.log(series);
        });
    }


}
