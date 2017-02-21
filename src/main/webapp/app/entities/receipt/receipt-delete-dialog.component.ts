import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptPopupService } from './receipt-popup.service';
import { ReceiptService } from './receipt.service';

@Component({
    selector: 'jhi-receipt-delete-dialog',
    templateUrl: './receipt-delete-dialog.component.html'
})
export class ReceiptDeleteDialogComponent {

    receipt: Receipt;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptService: ReceiptService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['receipt', 'docType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.receiptService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'receiptListModification',
                content: 'Deleted an receipt'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-receipt-delete-popup',
    template: ''
})
export class ReceiptDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptPopupService: ReceiptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.receiptPopupService
                .open(ReceiptDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
