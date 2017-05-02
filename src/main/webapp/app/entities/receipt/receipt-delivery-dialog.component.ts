import {Response} from '@angular/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptPopupService } from './receipt-popup.service';
import { ReceiptService } from './receipt.service';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'jhi-receipt-delivery-dialog',
    templateUrl: './receipt-delivery-dialog.component.html'
})
export class ReceiptDeliveryDialogComponent {

    receipt: Receipt;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptService: ReceiptService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'receiptStatus', 'address', 'productEntry']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelivery (id: number) {
        this.receiptService.downloadReceipt(id).subscribe((res: Response) => {
            this.onSuccessDocx(res, id);
            this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
            this.activeModal.dismiss(true);
        });
    }

    private onSuccessDocx(res: Response, receiptId: number) {
        let mediaType = 'application/octet-stream;charset=UTF-8';
        let blob = new Blob([res.blob()], {type: mediaType});
        let receiptNumber = receiptId + '';
        let filename = receiptNumber + '_invoice.docx';

        try {
            window.navigator.msSaveOrOpenBlob(blob, filename);
        } catch (ex) {
            FileSaver.saveAs(blob, filename);
        }
    }
}

@Component({
    selector: 'jhi-receipt-delivery-popup',
    template: ''
})
export class ReceiptDeliveryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptPopupService: ReceiptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.receiptPopupService
                .open(ReceiptDeliveryDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
