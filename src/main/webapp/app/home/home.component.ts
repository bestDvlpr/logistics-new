import {Component, OnInit} from "@angular/core";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {EventManager, JhiLanguageService} from "ng-jhipster";
import {Account, LoginModalService, Principal} from "../shared";
import {ReportService} from "../report/report.service";
import {ReceiptStatus} from "../entities/receipt/receipt.model";
import {DeliveryCountByCompany} from "../report/delivery-count-by-ompany";

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
    countByCompany: DeliveryCountByCompany[];

    pieChartOptions: Object;

    constructor(private jhiLanguageService: JhiLanguageService,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private reportService: ReportService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.deliveryCountChart();
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
        this.reportService.deliveryCountChart(ReceiptStatus.DELIVERED).subscribe((res) => {
            this.countByCompany = res;
            let data = [];
            let overall = this.countByCompany.find(x => x.companyName === null);
            for (let a of this.countByCompany) {
                if (a.companyName !== null) {
                    data.push({
                        name: a.companyName + ' ' + (Math.round((a.count / overall.count) * 10000) / 100) + ' %',
                        y: a.count
                    });
                }
                console.log(Math.round((a.count / overall.count)));
                console.log((a.count / overall.count));
                console.log(overall.count);
            }

            this.pieChartOptions = {
                title: {text: 'Simple Chart'},
                series: [{
                    name: 'Delivery counts',
                    data: data
                }],
                chart: {
                    type: 'pie'
                }
            }
        })
    }
}
