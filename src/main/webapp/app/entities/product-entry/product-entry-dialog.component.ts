import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductEntry } from './product-entry.model';
import { ProductEntryPopupService } from './product-entry-popup.service';
import { ProductEntryService } from './product-entry.service';
import { Product, ProductService } from '../product';
import { Seller, SellerService } from '../seller';
import { Receipt, ReceiptService } from '../receipt';
import { Driver, DriverService } from '../driver';
@Component({
    selector: 'jhi-product-entry-dialog',
    templateUrl: './product-entry-dialog.component.html'
})
export class ProductEntryDialogComponent implements OnInit {

    productEntry: ProductEntry;
    authorities: any[];
    isSaving: boolean;

    products: Product[];

    sellers: Seller[];

    receipts: Receipt[];

    drivers: Driver[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productEntryService: ProductEntryService,
        private productService: ProductService,
        private sellerService: SellerService,
        private receiptService: ReceiptService,
        private driverService: DriverService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productEntry', 'salesType', 'salesPlace', 'defectFlag', 'virtualFlag', 'receiptStatus']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
        this.sellerService.query().subscribe(
            (res: Response) => { this.sellers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.receiptService.query().subscribe(
            (res: Response) => { this.receipts = res.json(); }, (res: Response) => this.onError(res.json()));
        this.driverService.query().subscribe(
            (res: Response) => { this.drivers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.productEntry.id !== undefined) {
            this.productEntryService.update(this.productEntry)
                .subscribe((res: ProductEntry) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.productEntryService.create(this.productEntry)
                .subscribe((res: ProductEntry) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ProductEntry) {
        this.eventManager.broadcast({ name: 'productEntryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackSellerById(index: number, item: Seller) {
        return item.id;
    }

    trackReceiptById(index: number, item: Receipt) {
        return item.id;
    }

    trackDriverById(index: number, item: Driver) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-entry-popup',
    template: ''
})
export class ProductEntryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private productEntryPopupService: ProductEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.productEntryPopupService
                    .open(ProductEntryDialogComponent, params['id']);
            } else {
                this.modalRef = this.productEntryPopupService
                    .open(ProductEntryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
