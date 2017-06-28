import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {ProductEntry} from './product-entry.model';
import {ProductEntryPopupService} from './product-entry-popup.service';
import {ProductEntryService} from './product-entry.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-product-entry-delete-dialog',
    templateUrl: './product-entry-delete-dialog.component.html'
})
export class ProductEntryDeleteDialogComponent implements OnInit {

    productEntry: ProductEntry;
    languages: any[];

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    constructor(private languageHelper: JhiLanguageHelper,
                private productEntryService: ProductEntryService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productEntryService.delete(id).subscribe(() => {
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

    constructor(private route: ActivatedRoute,
                private productEntryPopupService: ProductEntryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.productEntryPopupService
                .open(ProductEntryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
