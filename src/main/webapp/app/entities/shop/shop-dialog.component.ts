import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Shop } from './shop.model';
import { ShopPopupService } from './shop-popup.service';
import { ShopService } from './shop.service';
import { Address, AddressService } from '../address';
@Component({
    selector: 'jhi-shop-dialog',
    templateUrl: './shop-dialog.component.html'
})
export class ShopDialogComponent implements OnInit {

    shop: Shop;
    authorities: any[];
    isSaving: boolean;

    addresses: Address[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private shopService: ShopService,
        private addressService: AddressService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['shop']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.addressService.getAll().subscribe(
            (res: Address[]) => { this.addresses = res; }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.shop.id !== undefined) {
            this.shopService.update(this.shop)
                .subscribe((res: Shop) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.shopService.create(this.shop)
                .subscribe((res: Shop) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Shop) {
        this.eventManager.broadcast({ name: 'shopListModification', content: 'OK'});
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

    trackAddressById(index: number, item: Address) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shop-popup',
    template: ''
})
export class ShopPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private shopPopupService: ShopPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.shopPopupService
                    .open(ShopDialogComponent, params['id']);
            } else {
                this.modalRef = this.shopPopupService
                    .open(ShopDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
