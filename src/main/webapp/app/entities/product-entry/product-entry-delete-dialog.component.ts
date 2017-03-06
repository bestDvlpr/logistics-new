import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ProductEntry } from './product-entry.model';
import { ProductEntryPopupService } from './product-entry-popup.service';
import { ProductEntryService } from './product-entry.service';

@Component({
    selector: 'jhi-product-entry-delete-dialog',
    templateUrl: './product-entry-delete-dialog.component.html'
})
export class ProductEntryDeleteDialogComponent {

    productEntry: ProductEntry;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productEntryService: ProductEntryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productEntry', 'salesType', 'salesPlace', 'defectFlag', 'virtualFlag', 'receiptStatus']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.productEntryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productEntryListModification',
                content: 'Deleted an productEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-entry-delete-popup',
    template: ''
})
export class ProductEntryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private productEntryPopupService: ProductEntryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.productEntryPopupService
                .open(ProductEntryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
