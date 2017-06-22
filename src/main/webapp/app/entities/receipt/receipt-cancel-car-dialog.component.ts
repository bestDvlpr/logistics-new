import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {Receipt} from "./receipt.model";
import {ReceiptPopupService} from "./receipt-popup.service";
import {ReceiptService} from "./receipt.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-receipt-cancel-car-dialog',
    templateUrl: './receipt-cancel-car-dialog.component.html'
})
export class ReceiptCancelCarDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    receipt: Receipt;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private receiptService: ReceiptService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
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

    constructor(private route: ActivatedRoute,
                private receiptPopupService: ReceiptPopupService) {
    }

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
