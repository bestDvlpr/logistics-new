import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { PhoneNumber } from './phone-number.model';
import { PhoneNumberPopupService } from './phone-number-popup.service';
import { PhoneNumberService } from './phone-number.service';

@Component({
    selector: 'jhi-phone-number-delete-dialog',
    templateUrl: './phone-number-delete-dialog.component.html'
})
export class PhoneNumberDeleteDialogComponent {

    phoneNumber: PhoneNumber;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private phoneNumberService: PhoneNumberService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['phoneNumber']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.phoneNumberService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'phoneNumberListModification',
                content: 'Deleted an phoneNumber'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-phone-number-delete-popup',
    template: ''
})
export class PhoneNumberDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private phoneNumberPopupService: PhoneNumberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.phoneNumberPopupService
                .open(PhoneNumberDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
