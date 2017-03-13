import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiLanguageService, AlertService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {ClientService} from '../client/client.service';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';

@Component({
    selector: 'jhi-receipt-send',
    templateUrl: './receipt-send.component.html'
})
export class ReceiptSendComponent implements OnInit {

    receipt: Receipt;
    private subscription: any;
    phoneNumber: string;
    client: Client;
    productEntries: ProductEntry[];
    private clientSelected: boolean = false;
    private createClientSelected: boolean = false;
    public isCollapsed = true;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                private route: ActivatedRoute,
                private alertService: AlertService,
                private clientService: ClientService,
                public dataHolderService: DataHolderService,
                private router: Router) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    public findClient() {
        this.clientService.byPhoneNumber(this.phoneNumber).subscribe(res => {
            this.client = res;
        });
        this.client = null;
        this.clientSelected = false;
        this.createClientSelected = false;
    }

    public toggleClientSelected() {
        this.clientSelected = !this.clientSelected;
    }

    public toggleCreateClientSelected() {
        this.createClientSelected = !this.createClientSelected;
    }

    public nextStep() {
        this.dataHolderService._client = this.client;
        this.dataHolderService._receipt = this.receipt;
        this.router.navigate(['../receipt/send/client']);
    }
}
