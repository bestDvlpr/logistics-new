import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { LocationType } from './location-type.model';
import { LocationTypePopupService } from './location-type-popup.service';
import { LocationTypeService } from './location-type.service';
@Component({
    selector: 'jhi-location-type-dialog',
    templateUrl: './location-type-dialog.component.html'
})
export class LocationTypeDialogComponent implements OnInit {

    locationType: LocationType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private locationTypeService: LocationTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['locationType']);
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
        if (this.locationType.id !== undefined) {
            this.locationTypeService.update(this.locationType)
                .subscribe((res: LocationType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.locationTypeService.create(this.locationType)
                .subscribe((res: LocationType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: LocationType) {
        this.eventManager.broadcast({ name: 'locationTypeListModification', content: 'OK'});
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
    selector: 'jhi-location-type-popup',
    template: ''
})
export class LocationTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private locationTypePopupService: LocationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.locationTypePopupService
                    .open(LocationTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.locationTypePopupService
                    .open(LocationTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
