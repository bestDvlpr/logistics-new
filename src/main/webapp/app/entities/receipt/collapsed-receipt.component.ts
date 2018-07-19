import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {JhiEventManager} from 'ng-jhipster';
import {Receipt, ReceiptStatus} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Address} from '../address/address.model';
import {Subscription} from 'rxjs/Subscription';
import {EnumAware} from './doctypaware.decorator';
import {Company} from '../company/company.model';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-collapsed-receipt',
    templateUrl: 'collapsed-receipt.component.html'
})
@EnumAware
export class CollapsedReceiptComponent implements OnInit, OnDestroy {

    @Input()receipt: Receipt;
    @Input()aaa: string;
    phoneNumber: string;
    client: Client;
    company: Company;
    address: Address;
    productEntries: ProductEntry[];
    productsSelected: ProductEntry[] = [];
    isCollapsed = false;
    productCarExists = false;
    eventSubscriber: Subscription;
    receiptStatusEnum = ReceiptStatus;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                public eventManager: JhiEventManager,
                private router: Router) {
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        this.company = this.dataHolderService._company;
        this.address = this.dataHolderService._address;
        if (this.receipt !== null && this.receipt.productEntries !== null) {
            for (const prod of this.receipt.productEntries) {
                if (prod.attachedCarId !== null) {
                    this.productCarExists = true;
                }
            }
        }
        this.registerChangeInAddresses();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    load(id) {
        this.receiptService.find(id).subscribe((receipt) => {
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
