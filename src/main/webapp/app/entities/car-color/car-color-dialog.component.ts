import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CarColor } from './car-color.model';
import { CarColorPopupService } from './car-color-popup.service';
import { CarColorService } from './car-color.service';
@Component({
    selector: 'jhi-car-color-dialog',
    templateUrl: './car-color-dialog.component.html'
})
export class CarColorDialogComponent implements OnInit {

    carColor: CarColor;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private carColorService: CarColorService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['carColor']);
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
        if (this.carColor.id !== undefined) {
            this.carColorService.update(this.carColor)
                .subscribe((res: CarColor) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.carColorService.create(this.carColor)
                .subscribe((res: CarColor) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CarColor) {
        this.eventManager.broadcast({ name: 'carColorListModification', content: 'OK'});
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
    selector: 'jhi-car-color-popup',
    template: ''
})
export class CarColorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carColorPopupService: CarColorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.carColorPopupService
                    .open(CarColorDialogComponent, params['id']);
            } else {
                this.modalRef = this.carColorPopupService
                    .open(CarColorDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
