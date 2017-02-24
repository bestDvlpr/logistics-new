import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ReceiptStatus } from './receipt-status.model';
import { ReceiptStatusPopupService } from './receipt-status-popup.service';
import { ReceiptStatusService } from './receipt-status.service';
@Component({
    selector: 'jhi-receipt-status-dialog',
    templateUrl: './receipt-status-dialog.component.html'
})
export class ReceiptStatusDialogComponent implements OnInit {

    receiptStatus: ReceiptStatus;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private receiptStatusService: ReceiptStatusService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['receiptStatus']);
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
        if (this.receiptStatus.id !== undefined) {
            this.receiptStatusService.update(this.receiptStatus)
                .subscribe((res: ReceiptStatus) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.receiptStatusService.create(this.receiptStatus)
                .subscribe((res: ReceiptStatus) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ReceiptStatus) {
        this.eventManager.broadcast({ name: 'receiptStatusListModification', content: 'OK'});
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
    selector: 'jhi-receipt-status-popup',
    template: ''
})
export class ReceiptStatusPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptStatusPopupService: ReceiptStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.receiptStatusPopupService
                    .open(ReceiptStatusDialogComponent, params['id']);
            } else {
                this.modalRef = this.receiptStatusPopupService
                    .open(ReceiptStatusDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
