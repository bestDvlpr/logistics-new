import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { DriverStatus } from './driver-status.model';
import { DriverStatusPopupService } from './driver-status-popup.service';
import { DriverStatusService } from './driver-status.service';
@Component({
    selector: 'jhi-driver-status-dialog',
    templateUrl: './driver-status-dialog.component.html'
})
export class DriverStatusDialogComponent implements OnInit {

    driverStatus: DriverStatus;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private driverStatusService: DriverStatusService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['driverStatus']);
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
        if (this.driverStatus.id !== undefined) {
            this.driverStatusService.update(this.driverStatus)
                .subscribe((res: DriverStatus) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.driverStatusService.create(this.driverStatus)
                .subscribe((res: DriverStatus) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: DriverStatus) {
        this.eventManager.broadcast({ name: 'driverStatusListModification', content: 'OK'});
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
    selector: 'jhi-driver-status-popup',
    template: ''
})
export class DriverStatusPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private driverStatusPopupService: DriverStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.driverStatusPopupService
                    .open(DriverStatusDialogComponent, params['id']);
            } else {
                this.modalRef = this.driverStatusPopupService
                    .open(DriverStatusDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
