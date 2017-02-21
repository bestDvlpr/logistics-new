import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { PayType } from './pay-type.model';
import { PayTypePopupService } from './pay-type-popup.service';
import { PayTypeService } from './pay-type.service';
import { PaymentType, PaymentTypeService } from '../payment-type';
@Component({
    selector: 'jhi-pay-type-dialog',
    templateUrl: './pay-type-dialog.component.html'
})
export class PayTypeDialogComponent implements OnInit {

    payType: PayType;
    authorities: any[];
    isSaving: boolean;

    paymenttypes: PaymentType[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private payTypeService: PayTypeService,
        private paymentTypeService: PaymentTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['payType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.paymentTypeService.query().subscribe(
            (res: Response) => { this.paymenttypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.payType.id !== undefined) {
            this.payTypeService.update(this.payType)
                .subscribe((res: PayType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.payTypeService.create(this.payType)
                .subscribe((res: PayType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: PayType) {
        this.eventManager.broadcast({ name: 'payTypeListModification', content: 'OK'});
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

    trackPaymentTypeById(index: number, item: PaymentType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pay-type-popup',
    template: ''
})
export class PayTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private payTypePopupService: PayTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.payTypePopupService
                    .open(PayTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.payTypePopupService
                    .open(PayTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
