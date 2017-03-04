import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { PayType } from './pay-type.model';
import { PayTypePopupService } from './pay-type-popup.service';
import { PayTypeService } from './pay-type.service';

@Component({
    selector: 'jhi-pay-type-delete-dialog',
    templateUrl: './pay-type-delete-dialog.component.html'
})
export class PayTypeDeleteDialogComponent {

    payType: PayType;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private payTypeService: PayTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['payType', 'paymentType']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
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

    constructor (
        private route: ActivatedRoute,
        private payTypePopupService: PayTypePopupService
    ) {}

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
