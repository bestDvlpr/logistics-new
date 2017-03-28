import {Component, OnInit, OnDestroy} from '@angular/core';
import {Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {EventManager, ParseLinks, JhiLanguageService, AlertService} from 'ng-jhipster';

import {ProductEntry} from './product-entry.model';
import {ProductEntryService} from './product-entry.service';
import {EnumAware} from '../receipt/doctypaware.decorator';
import {ReceiptStatus, WholeSaleFlag} from '../receipt/receipt.model';
import {TranslateService} from 'ng2-translate';

@Component({
    selector: 'jhi-product-entry-delivery',
    templateUrl: './product-entry-delivery.component.html'
})
@EnumAware
export class ProductEntryDeliveryComponent implements OnInit, OnDestroy {

    currentAccount: any;
    productEntries: ProductEntry[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    carNumber: string;
    productsSelected: ProductEntry[] = [];
    isAllChecked: boolean = false;
    receiptStatusEnum = ReceiptStatus;
    wholeSaleFlagEnum = WholeSaleFlag;

    constructor(private jhiLanguageService: JhiLanguageService,
                private productEntryService: ProductEntryService,
                private alertService: AlertService,
                public translateService: TranslateService,
                private router: Router) {
        this.jhiLanguageService.setLocations(
            ['productEntry', 'salesType', 'salesPlace', 'defectFlag', 'virtualFlag', 'receiptStatus', 'car']
        );
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/product-entry-delivery', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
    }

    ngOnInit() {
    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProductEntry) {
        return item.id;
    }


    sort() {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data) {
        this.productEntries = data;
        if (this.productEntries.length <= 0) {
            this.alertService.error(this.translateService.instant('logisticsApp.productEntry.error.notFound'));
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    searchByCar() {
        this.productEntryService.byCarNumber(this.carNumber).subscribe((res: Response) => {
            this.onSuccess(res.json());
        });
    }

    productChecked(product: ProductEntry) {
        let indexProd: number = this.productEntries.indexOf(product);
        if (indexProd !== null && indexProd !== -1) {
            let productEntry = this.productEntries[indexProd];
            productEntry.selected = !productEntry.selected;
            if (!productEntry.selected) {
                this.productsSelected.push(productEntry);
            } else {
                this.productsSelected.splice(this.productsSelected.indexOf(productEntry), 1);
            }
        }
    }

    productAllChecked() {
        if (!this.isAllChecked) {
            this.productsSelected = [];
            this.productEntries.forEach(entry => {
                entry.selected = true;
                this.productsSelected.push(entry);
            });
            this.isAllChecked = !this.isAllChecked;
        } else {
            this.productEntries.forEach(entry => entry.selected = false);
            this.isAllChecked = !this.isAllChecked;
            this.productsSelected = [];
        }
    }

    deliverProducts() {
        this.productEntryService.deliver(this.productsSelected).subscribe((res: Response) => {
            this.productsSelected = res.json();
            this.router.navigate(['/']);
        });
    }
}
