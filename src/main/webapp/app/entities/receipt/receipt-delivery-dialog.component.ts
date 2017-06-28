import {Response} from '@angular/http';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {Receipt} from './receipt.model';
import {ReceiptPopupService} from './receipt-popup.service';
import {ReceiptService} from './receipt.service';
import * as FileSaver from 'file-saver';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-receipt-delivery-dialog',
    templateUrl: './receipt-delivery-dialog.component.html'
})
export class ReceiptDeliveryDialogComponent implements OnInit {

    receipt: Receipt;
    languages: any[];

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    constructor(private languageHelper: JhiLanguageHelper,
                private receiptService: ReceiptService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelivery(id: number) {
        this.receiptService.downloadReceipt(id).subscribe((res: Response) => {
            this.onSuccessDocx(res, id);
            this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
            this.activeModal.dismiss(true);
        });
    }

    private onSuccessDocx(res: Response, receiptId: number) {
        const mediaType = 'application/octet-stream;charset=UTF-8';
        const blob = new Blob([res.blob()], {type: mediaType});
        const receiptNumber = receiptId + '';
        const filename = receiptNumber + '_invoice.docx';

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

    constructor(private route: ActivatedRoute,
                private receiptPopupService: ReceiptPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.receiptPopupService
                .open(ReceiptDeliveryDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
