import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { PayMaster } from './pay-master.model';
import { PayMasterPopupService } from './pay-master-popup.service';
import { PayMasterService } from './pay-master.service';
@Component({
    selector: 'jhi-pay-master-dialog',
    templateUrl: './pay-master-dialog.component.html'
})
export class PayMasterDialogComponent implements OnInit {

    payMaster: PayMaster;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private payMasterService: PayMasterService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['payMaster']);
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
        if (this.payMaster.id !== undefined) {
            this.payMasterService.update(this.payMaster)
                .subscribe((res: PayMaster) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.payMasterService.create(this.payMaster)
                .subscribe((res: PayMaster) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: PayMaster) {
        this.eventManager.broadcast({ name: 'payMasterListModification', content: 'OK'});
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
    selector: 'jhi-pay-master-popup',
    template: ''
})
export class PayMasterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private payMasterPopupService: PayMasterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.payMasterPopupService
                    .open(PayMasterDialogComponent, params['id']);
            } else {
                this.modalRef = this.payMasterPopupService
                    .open(PayMasterDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
