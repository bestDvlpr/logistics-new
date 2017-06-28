import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {LoyaltyCard} from './loyalty-card.model';
import {LoyaltyCardPopupService} from './loyalty-card-popup.service';
import {LoyaltyCardService} from './loyalty-card.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-loyalty-card-delete-dialog',
    templateUrl: './loyalty-card-delete-dialog.component.html'
})
export class LoyaltyCardDeleteDialogComponent implements OnInit {

    loyaltyCard: LoyaltyCard;
    languages: any[];

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    constructor(private languageHelper: JhiLanguageHelper,
                private loyaltyCardService: LoyaltyCardService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loyaltyCardService.delete(id).subscribe(() => {
            this.eventManager.broadcast({
                name: 'loyaltyCardListModification',
                content: 'Deleted an loyaltyCard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-loyalty-card-delete-popup',
    template: ''
})
export class LoyaltyCardDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private loyaltyCardPopupService: LoyaltyCardPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.loyaltyCardPopupService
                .open(LoyaltyCardDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
