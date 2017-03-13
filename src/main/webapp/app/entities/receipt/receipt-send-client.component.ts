import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService, AlertService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {ProductEntryService} from '../product-entry/product-entry.service';
import {Response} from '@angular/http';
import {ClientService} from '../client/client.service';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';

@Component({
    selector: 'jhi-receipt-send-client',
    templateUrl: './receipt-send-client.component.html'
})
export class ReceiptSendClientComponent implements OnInit {
    receipt: Receipt;
    private subscription: any;
    phoneNumber: string;
    client: Client;
    productEntries: ProductEntry[];
    private clientSelected: boolean = false;
    public isCollapsed = true;

    constructor(private jhiLanguageService: JhiLanguageService,
                public dataHolderService: DataHolderService) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']);
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        console.log(this.receipt);
        console.log(this.client);
    }

    previousState() {
        window.history.back();
    }

    public toggleClientSelected() {
        this.clientSelected = !this.clientSelected;
    }
}
