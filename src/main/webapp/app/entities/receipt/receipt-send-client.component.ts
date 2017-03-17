import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiLanguageService, AlertService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {TranslateService} from 'ng2-translate';
import {ClientService} from '../client/client.service';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Response} from '@angular/http';
import {EventManager} from 'ng-jhipster';

@Component({
    selector: 'jhi-receipt-send-client',
    templateUrl: 'receipt-send-client.component.html'
})
export class ReceiptSendClientComponent implements OnInit {

    receipt: Receipt;
    private subscription: any;
    phoneNumber: string;
    client: Client;
    public clientSelected: boolean = false;
    public uncheckedProdsExist: boolean = false;
    isSaving: boolean;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                private route: ActivatedRoute,
                private alertService: AlertService,
                private clientService: ClientService,
                public dataHolderService: DataHolderService,
                public translateService: TranslateService,
                private router: Router,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(
            ['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']
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

    public goAddressSelectStep() {
        this.receipt.clientId = this.client.id;
        this.dataHolderService._client = this.client;
        this.dataHolderService._receipt = this.receipt;
        this.router.navigate(['../receipt/send/address']);
    }

    public sendOrder() {
        if (this.uncheckedProdsExist) {
            this.alertService.error(this.translateService.instant('error.NotNull'));
        } else {
            this.receiptService.sendOrder(this.receipt).subscribe(
                (res: Receipt) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json())
            );
            this.router.navigate(['receipt']);
        }

    }

    private onSaveSuccess(result: Receipt) {
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