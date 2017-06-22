import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {PayType} from "./pay-type.model";
import {PayTypePopupService} from "./pay-type-popup.service";
import {PayTypeService} from "./pay-type.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-pay-type-delete-dialog',
    templateUrl: './pay-type-delete-dialog.component.html'
})
export class PayTypeDeleteDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    payType: PayType;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private payTypeService: PayTypeService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'payTypeListModification',
                content: 'Deleted an payType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pay-type-delete-popup',
    template: ''
})
export class PayTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private payTypePopupService: PayTypePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.payTypePopupService
                .open(PayTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
