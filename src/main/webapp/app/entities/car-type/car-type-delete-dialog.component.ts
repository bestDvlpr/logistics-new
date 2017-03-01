import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CarType } from './car-type.model';
import { CarTypePopupService } from './car-type-popup.service';
import { CarTypeService } from './car-type.service';

@Component({
    selector: 'jhi-car-type-delete-dialog',
    templateUrl: './car-type-delete-dialog.component.html'
})
export class CarTypeDeleteDialogComponent {

    carType: CarType;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carTypeService: CarTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['carType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.carTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carTypeListModification',
                content: 'Deleted an carType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-car-type-delete-popup',
    template: ''
})
export class CarTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carTypePopupService: CarTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.carTypePopupService
                .open(CarTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
