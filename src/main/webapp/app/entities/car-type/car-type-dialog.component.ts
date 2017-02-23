import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CarType } from './car-type.model';
import { CarTypePopupService } from './car-type-popup.service';
import { CarTypeService } from './car-type.service';
@Component({
    selector: 'jhi-car-type-dialog',
    templateUrl: './car-type-dialog.component.html'
})
export class CarTypeDialogComponent implements OnInit {

    carType: CarType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private carTypeService: CarTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['carType']);
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
        if (this.carType.id !== undefined) {
            this.carTypeService.update(this.carType)
                .subscribe((res: CarType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.carTypeService.create(this.carType)
                .subscribe((res: CarType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CarType) {
        this.eventManager.broadcast({ name: 'carTypeListModification', content: 'OK'});
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
    selector: 'jhi-car-type-popup',
    template: ''
})
export class CarTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carTypePopupService: CarTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.carTypePopupService
                    .open(CarTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.carTypePopupService
                    .open(CarTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
