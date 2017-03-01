import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { DriverStatus } from './driver-status.model';
import { DriverStatusPopupService } from './driver-status-popup.service';
import { DriverStatusService } from './driver-status.service';

@Component({
    selector: 'jhi-driver-status-delete-dialog',
    templateUrl: './driver-status-delete-dialog.component.html'
})
export class DriverStatusDeleteDialogComponent {

    driverStatus: DriverStatus;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private driverStatusService: DriverStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['driverStatus']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.driverStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'driverStatusListModification',
                content: 'Deleted an driverStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-driver-status-delete-popup',
    template: ''
})
export class DriverStatusDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private driverStatusPopupService: DriverStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.driverStatusPopupService
                .open(DriverStatusDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
