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

@Component({
    selector: 'jhi-receipt-send-address',
    templateUrl: 'receipt-send-address.component.html'
})
export class ReceiptSendAddressComponent implements OnInit {
    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    productEntries: ProductEntry[];
    addressSelected: number;
    eventSubscriber: Subscription;

    constructor(private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private translateService: TranslateService,
                public dataHolderService: DataHolderService,
                private clientService: ClientService,
                private eventManager: EventManager,
                private router: Router) {
        this.jhiLanguageService.setLocations(
            ['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']
        );
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
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

    public goSelectProductStep() {
        if (!this.addressSelected) {
            this.alertService.error(this.translateService.instant('error.emailexists'));
        } else {
            this.receipt.addresses.push(this.addressSelected);
            this.dataHolderService._client = this.client;
            this.dataHolderService._receipt = this.receipt;
            this.dataHolderService._address = this.addressSelected;
            this.router.navigate(['../receipt-send-product']);
        }
    }

    registerChangeInAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('addressListModification', (response) => this.reloadClient());
    }
}
