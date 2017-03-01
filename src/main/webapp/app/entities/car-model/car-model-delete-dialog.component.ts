import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CarModel } from './car-model.model';
import { CarModelPopupService } from './car-model-popup.service';
import { CarModelService } from './car-model.service';

@Component({
    selector: 'jhi-car-model-delete-dialog',
    templateUrl: './car-model-delete-dialog.component.html'
})
export class CarModelDeleteDialogComponent {

    carModel: CarModel;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carModelService: CarModelService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['carModel']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.carModelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carModelListModification',
                content: 'Deleted an carModel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-car-model-delete-popup',
    template: ''
})
export class CarModelDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carModelPopupService: CarModelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.carModelPopupService
                .open(CarModelDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
