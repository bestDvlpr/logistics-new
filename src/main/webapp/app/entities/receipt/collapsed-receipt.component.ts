import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {Receipt, ReceiptStatus} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Address} from '../address/address.model';
import {Subscription} from "rxjs/Subscription";
import {EnumAware} from "./doctypaware.decorator";

@Component({
    selector: 'jhi-collapsed-receipt',
    templateUrl: 'collapsed-receipt.component.html'
})
@EnumAware
export class CollapsedReceiptComponent implements OnInit, OnDestroy {

    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    address: Address;
    productEntries: ProductEntry[];
    public productsSelected: ProductEntry[] = [];
    public isCollapsed = false;
    productCarExists: boolean = false;
    eventSubscriber: Subscription;
    receiptStatusEnum = ReceiptStatus;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                public eventManager: EventManager,
                private router: Router) {
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
                'receiptStatus',
                'companyType',
                'company'
            ]
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
        this.registerChangeInAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }


    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    registerChangeInAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('addressListModification', (response) => this.load(this.dataHolderService._receipt.id));
    }
}
