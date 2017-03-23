import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Address} from '../address/address.model';

@Component({
    selector: 'jhi-collapsed-receipt',
    templateUrl: 'collapsed-receipt.component.html'
})
export class CollapsedReceiptComponent implements OnInit {

    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    address: Address;
    productEntries: ProductEntry[];
    public productsSelected: ProductEntry[] = [];
    public isCollapsed = false;
    productCarExists: boolean = false;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                private router: Router) {
        this.jhiLanguageService.setLocations(
            ['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']
        );
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        this.address = this.dataHolderService._address;
        if (this.receipt !== null && this.receipt.productEntries !== null) {
            for (let prod of this.receipt.productEntries) {
                if (prod.attachedCarId !== null) {
                    this.productCarExists = true;
                }
            }
        }
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }
}
