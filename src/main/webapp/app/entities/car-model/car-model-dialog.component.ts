import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CarModel } from './car-model.model';
import { CarModelPopupService } from './car-model-popup.service';
import { CarModelService } from './car-model.service';
@Component({
    selector: 'jhi-car-model-dialog',
    templateUrl: './car-model-dialog.component.html'
})
export class CarModelDialogComponent implements OnInit {

    carModel: CarModel;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private carModelService: CarModelService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['carModel']);
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
        if (this.carModel.id !== undefined) {
            this.carModelService.update(this.carModel)
                .subscribe((res: CarModel) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.carModelService.create(this.carModel)
                .subscribe((res: CarModel) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CarModel) {
        this.eventManager.broadcast({ name: 'carModelListModification', content: 'OK'});
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
    selector: 'jhi-car-model-popup',
    template: ''
})
export class CarModelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carModelPopupService: CarModelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.carModelPopupService
                    .open(CarModelDialogComponent, params['id']);
            } else {
                this.modalRef = this.carModelPopupService
                    .open(CarModelDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
