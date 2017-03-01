import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ReceiptStatus } from './receipt-status.model';
import { ReceiptStatusPopupService } from './receipt-status-popup.service';
import { ReceiptStatusService } from './receipt-status.service';

@Component({
    selector: 'jhi-receipt-status-delete-dialog',
    templateUrl: './receipt-status-delete-dialog.component.html'
})
export class ReceiptStatusDeleteDialogComponent {

    receiptStatus: ReceiptStatus;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptStatusService: ReceiptStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['receiptStatus']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.receiptStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'receiptStatusListModification',
                content: 'Deleted an receiptStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-receipt-status-delete-popup',
    template: ''
})
export class ReceiptStatusDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptStatusPopupService: ReceiptStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.receiptStatusPopupService
                .open(ReceiptStatusDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
