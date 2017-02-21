import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Seller } from './seller.model';
import { SellerPopupService } from './seller-popup.service';
import { SellerService } from './seller.service';

@Component({
    selector: 'jhi-seller-delete-dialog',
    templateUrl: './seller-delete-dialog.component.html'
})
export class SellerDeleteDialogComponent {

    seller: Seller;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sellerService: SellerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['seller']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.sellerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sellerListModification',
                content: 'Deleted an seller'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-seller-delete-popup',
    template: ''
})
export class SellerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sellerPopupService: SellerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.sellerPopupService
                .open(SellerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
