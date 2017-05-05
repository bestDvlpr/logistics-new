import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService, EventManager, JhiLanguageService} from "ng-jhipster";
import {Receipt} from "./receipt.model";
import {ReceiptService} from "./receipt.service";
import {TranslateService} from "ng2-translate";
import {ClientService} from "../client/client.service";
import {Client} from "../client/client.model";
import {DataHolderService} from "./data-holder.service";
import {Response} from "@angular/http";
import {Company} from "../company/company.model";
import {CompanyService} from "../company/company.service";

@Component({
    selector: 'jhi-receipt-send-client',
    templateUrl: 'receipt-send-client.component.html'
})
export class ReceiptSendClientComponent implements OnInit {

    receipt: Receipt;
    private subscription: any;
    phoneNumber: string;
    client: Client;
    clientSelected = false;
    isCompanySelected = false;
    companySelected: Company;
    uncheckedProdsExist = false;
    isSaving: boolean;
    companies: Company[];

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                private route: ActivatedRoute,
                private alertService: AlertService,
                private clientService: ClientService,
                public dataHolderService: DataHolderService,
                public translateService: TranslateService,
                private router: Router,
                private companyService: CompanyService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(
            [
                'receipt',
                'docType',
                'wholeSaleFlag',
                'productEntry',
                'product',
                'client',
                'phoneNumber',
                'address',
                'car',
                'companyType'
            ]
        );
    }

    ngOnInit() {
        if (this.dataHolderService._receipt !== null) {
            this.receipt = this.dataHolderService._receipt;
            if (this.receipt.productEntries !== undefined) {
                for (let prod of this.receipt.productEntries) {
                    if (prod.addressId == null) {
                        this.uncheckedProdsExist = true;
                    }
                }
            }
        } else {
            this.subscription = this.route.params.subscribe(params => {
                this.load(params['id']);
            });
        }
        if (this.dataHolderService._client !== null) {
            this.client = this.dataHolderService._client;
        }
        this.companyService.all().subscribe((res) => {
            this.companies = res.json();
        });
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
            for (let prod of this.receipt.productEntries) {
                if (prod.addressId === null) {
                    this.uncheckedProdsExist = true;
                }
            }
        });
    }

    previousState() {
        window.history.back();
    }

    public findClient() {
        this.clientService.byPhoneNumber(this.phoneNumber).subscribe(res => {
            this.client = res;
        });
        this.client = null;
        this.clientSelected = false;
    }

    public toggleClientSelected() {
        this.clientSelected = !this.clientSelected;
    }

    public toggleCompanySelected(company: Company) {
        this.isCompanySelected = !this.isCompanySelected;
        if (this.isCompanySelected) {
            for (let a of this.companies) {
                if (a === company) {
                    this.companySelected = a;
                }
            }
        }
    }

    public goAddressSelectStep() {
        this.receipt.clientId = this.client.id;
        this.dataHolderService._client = this.client;
        this.dataHolderService._receipt = this.receipt;
        this.router.navigate(['../receipt-send-address']);
    }

    public sendOrder() {
        if (this.uncheckedProdsExist) {
            this.translateService.get('error.NotNull').subscribe(title => {
                this.alertService.error(title);
            });
        } else {
            this.receiptService.sendOrder(this.receipt).subscribe(
                (res: Receipt[]) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json())
            );
            this.router.navigate(['receipt']);
        }

    }

    private onSaveSuccess(result: Receipt[]) {
        this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
