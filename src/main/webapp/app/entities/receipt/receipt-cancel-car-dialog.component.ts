import { Component, OnInit, OnDestroy } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptPopupService } from './receipt-popup.service';
import { ReceiptService } from './receipt.service';

@Component({
    selector: 'jhi-receipt-cancel-car-dialog',
    templateUrl: './receipt-cancel-car-dialog.component.html'
})
export class ReceiptCancelCarDialogComponent {

    receipt: Receipt;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptService: ReceiptService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'address', 'wholeSaleFlag', 'receiptStatus']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    cancelAttachedCar(receiptId: number) {
        this.receiptService.cancelAttachedCar(receiptId).subscribe((res: Receipt) => {
            this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
            this.activeModal.dismiss(true);
        })
    }
}

@Component({
    selector: 'jhi-receipt-cancel-car-popup',
    template: ''
})
export class ReceiptCancelCarPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptPopupService: ReceiptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.receiptPopupService
                .open(ReceiptCancelCarDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
