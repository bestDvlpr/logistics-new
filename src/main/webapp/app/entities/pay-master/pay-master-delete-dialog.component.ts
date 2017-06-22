import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {PayMaster} from "./pay-master.model";
import {PayMasterPopupService} from "./pay-master-popup.service";
import {PayMasterService} from "./pay-master.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-pay-master-delete-dialog',
    templateUrl: './pay-master-delete-dialog.component.html'
})
export class PayMasterDeleteDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    payMaster: PayMaster;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private payMasterService: PayMasterService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.payMasterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'payMasterListModification',
                content: 'Deleted an payMaster'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pay-master-delete-popup',
    template: ''
})
export class PayMasterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private payMasterPopupService: PayMasterPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.payMasterPopupService
                .open(PayMasterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
