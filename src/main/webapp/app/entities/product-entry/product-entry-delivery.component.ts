import {Component, OnDestroy, OnInit} from '@angular/core';
import {Response} from '@angular/http';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService} from 'ng-jhipster';

import {ProductEntry} from './product-entry.model';
import {ProductEntryService} from './product-entry.service';
import {EnumAware} from '../receipt/doctypaware.decorator';
import {ReceiptStatus, WholeSaleFlag} from '../receipt/receipt.model';
import {TranslateService} from '@ngx-translate/core';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

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
    isAllChecked = false;
    receiptStatusEnum = ReceiptStatus;
    wholeSaleFlagEnum = WholeSaleFlag;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private productEntryService: ProductEntryService,
                private alertService: JhiAlertService,
                public translateService: TranslateService,
                private router: Router) {
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/product-entry-delivery', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    ngOnDestroy() {
        // this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProductEntry) {
        return item.id;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data) {
        this.productEntries = data;
        if (this.productEntries.length <= 0) {
            this.translateService.get('logisticsApp.productEntry.error.notFound').subscribe((title) => {
                this.alertService.error(title);
            });
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
        const indexProd: number = this.productEntries.indexOf(product);
        if (indexProd !== null && indexProd !== -1) {
            const productEntry = this.productEntries[indexProd];
            if (!productEntry.selected) {
                this.productsSelected.push(productEntry);
                if (this.productsSelected.length === this.productEntries.length) {
                    this.isAllChecked = true;
                }
            } else {
                this.productsSelected.splice(this.productsSelected.indexOf(productEntry), 1);
                if (this.isAllChecked) {
                    this.isAllChecked = false;
                }
            }
            productEntry.selected = !productEntry.selected;
        }
    }

    productAllChecked() {
        if (!this.isAllChecked) {
            this.productsSelected = [];
            this.productEntries.forEach((entry) => {
                entry.selected = true;
                this.productsSelected.push(entry);
            });
            this.isAllChecked = !this.isAllChecked;
        } else {
            this.productEntries.forEach((entry) => entry.selected = false);
            this.isAllChecked = !this.isAllChecked;
            this.productsSelected = [];
        }
    }

    deliverProducts() {
        this.productEntryService.deliver(this.productsSelected).subscribe((res: Response) => {
            this.onSuccessDocx(res);
        });
    }

    private onSuccessDocx(res: Response) {
        /*let mediaType = 'application/octet-stream;charset=UTF-8';
         let blob = new Blob([res.blob()], {type: mediaType});
         let receiptNumber: string = this.productsSelected.pop().receiptId + '';
         let filename = receiptNumber + '_invoice.docx';

         try {
         window.navigator.msSaveOrOpenBlob(blob, filename);
         } catch (ex) {
         FileSaver.saveAs(blob, filename);
         }*/
        this.router.navigate(['/']);
    }
}
