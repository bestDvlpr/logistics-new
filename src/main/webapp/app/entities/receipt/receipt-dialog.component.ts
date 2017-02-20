import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptPopupService } from './receipt-popup.service';
import { ReceiptService } from './receipt.service';
@Component({
    selector: 'jhi-receipt-dialog',
    templateUrl: './receipt-dialog.component.html'
})
export class ReceiptDialogComponent implements OnInit {

    receipt: Receipt;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private receiptService: ReceiptService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['receipt']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.receipt.id !== undefined) {
            this.receiptService.update(this.receipt)
                .subscribe((res: Receipt) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.receiptService.create(this.receipt)
                .subscribe((res: Receipt) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Receipt) {
        this.eventManager.broadcast({ name: 'receiptListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-receipt-popup',
    template: ''
})
export class ReceiptPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptPopupService: ReceiptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.receiptPopupService
                    .open(ReceiptDialogComponent, params['id']);
            } else {
                this.modalRef = this.receiptPopupService
                    .open(ReceiptDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
