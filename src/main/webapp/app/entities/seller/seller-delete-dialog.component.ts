import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {Seller} from './seller.model';
import {SellerPopupService} from './seller-popup.service';
import {SellerService} from './seller.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {JhiEventManager} from 'ng-jhipster';

@Component({
    selector: 'jhi-seller-delete-dialog',
    templateUrl: './seller-delete-dialog.component.html'
})
export class SellerDeleteDialogComponent implements OnInit {

    seller: Seller;
    languages: any[];

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    constructor(private languageHelper: JhiLanguageHelper,
                private sellerService: SellerService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sellerService.delete(id).subscribe(() => {
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

    constructor(private route: ActivatedRoute,
                private sellerPopupService: SellerPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.sellerPopupService
                .open(SellerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
