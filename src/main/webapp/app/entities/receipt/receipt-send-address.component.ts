import {Component, OnInit} from '@angular/core';
import {JhiLanguageService, AlertService, EventManager} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {TranslateService} from 'ng2-translate';
import {Router} from '@angular/router';
import {ClientService} from '../client/client.service';
import {Subscription} from 'rxjs/Rx';
import {DatePipe} from '@angular/common';
import {Company} from "../company/company.model";
import {CompanyService} from "../company/company.service";

@Component({
    selector: 'jhi-receipt-send-address',
    templateUrl: 'receipt-send-address.component.html'
})
export class ReceiptSendAddressComponent implements OnInit {
    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    company: Company;
    productEntries: ProductEntry[];
    addressSelected: number;
    eventSubscriber: Subscription;

    constructor(private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private translateService: TranslateService,
                public dataHolderService: DataHolderService,
                private clientService: ClientService,
                private companyService: CompanyService,
                private eventManager: EventManager,
                private router: Router,
                private datePipe: DatePipe) {
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
                'car'
            ]
        );
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        this.company = this.dataHolderService._company;
        this.registerChangeInAddresses();
    }

    previousState() {
        window.history.back();
    }

    private reloadClient() {
        this.clientService.find(this.client.id).subscribe((res) => {
            this.client = res;
        });
    }

    private reloadCompany() {
        this.companyService.find(this.company.id).subscribe((res) => {
            this.company = res;
        });
    }

    public goSelectProductStep() {
        if (!this.addressSelected) {
            this.translateService.get('error.emailexists').subscribe(title => {
                this.alertService.error(title);
            });
        } else {
            if (this.receipt.addresses === null || this.receipt.addresses === undefined) {
                this.receipt.addresses = [];
            }
            this.receipt.addresses.push(this.addressSelected);
            this.receipt.receiver=this.company;
            this.dataHolderService._client = this.client;
            this.dataHolderService._company = this.company;
            this.dataHolderService._receipt = this.receipt;
            this.dataHolderService._address = this.addressSelected;
            this.router.navigate(['../receipt-send-product']);
        }
    }

    registerChangeInAddresses() {
        if (this.client!==null){
            this.eventSubscriber = this.eventManager.subscribe('addressListModification', (response) => this.reloadClient());
        }
        if (this.company!==null){
            this.eventSubscriber = this.eventManager.subscribe('addressListModification', (response) => this.reloadCompany());
        }
    }
}
