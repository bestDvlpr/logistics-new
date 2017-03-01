import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { LocationType } from './location-type.model';
import { LocationTypePopupService } from './location-type-popup.service';
import { LocationTypeService } from './location-type.service';

@Component({
    selector: 'jhi-location-type-delete-dialog',
    templateUrl: './location-type-delete-dialog.component.html'
})
export class LocationTypeDeleteDialogComponent {

    locationType: LocationType;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private locationTypeService: LocationTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['locationType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.locationTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'locationTypeListModification',
                content: 'Deleted an locationType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-location-type-delete-popup',
    template: ''
})
export class LocationTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private locationTypePopupService: LocationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.locationTypePopupService
                .open(LocationTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
