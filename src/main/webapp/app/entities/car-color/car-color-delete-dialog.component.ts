import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CarColor } from './car-color.model';
import { CarColorPopupService } from './car-color-popup.service';
import { CarColorService } from './car-color.service';

@Component({
    selector: 'jhi-car-color-delete-dialog',
    templateUrl: './car-color-delete-dialog.component.html'
})
export class CarColorDeleteDialogComponent {

    carColor: CarColor;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carColorService: CarColorService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['carColor']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.carColorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carColorListModification',
                content: 'Deleted an carColor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-car-color-delete-popup',
    template: ''
})
export class CarColorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carColorPopupService: CarColorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.carColorPopupService
                .open(CarColorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}