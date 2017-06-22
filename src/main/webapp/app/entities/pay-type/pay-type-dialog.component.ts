import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {PayType} from "./pay-type.model";
import {PayTypePopupService} from "./pay-type-popup.service";
import {PayTypeService} from "./pay-type.service";
import {Receipt, ReceiptService} from "../receipt";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-pay-type-dialog',
    templateUrl: './pay-type-dialog.component.html'
})
export class PayTypeDialogComponent implements OnInit {

    payType: PayType;
    authorities: any[];
    isSaving: boolean;
    receipts: Receipt[];
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private payTypeService: PayTypeService,
                private receiptService: ReceiptService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.receiptService.getAll().subscribe(
            (res: Receipt[]) => {
                this.receipts = res;
            }, (res: Response) => this.onError(res.json()));
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.payType.id !== undefined) {
            this.payTypeService.update(this.payType)
                .subscribe((res: PayType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.payTypeService.create(this.payType)
                .subscribe((res: PayType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: PayType) {
        this.eventManager.broadcast({name: 'payTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackReceiptById(index: number, item: Receipt) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pay-type-popup',
    template: ''
})
export class PayTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private payTypePopupService: PayTypePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.payTypePopupService
                    .open(PayTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.payTypePopupService
                    .open(PayTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
