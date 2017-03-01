import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { LoyaltyCard } from './loyalty-card.model';
import { LoyaltyCardPopupService } from './loyalty-card-popup.service';
import { LoyaltyCardService } from './loyalty-card.service';

@Component({
    selector: 'jhi-loyalty-card-delete-dialog',
    templateUrl: './loyalty-card-delete-dialog.component.html'
})
export class LoyaltyCardDeleteDialogComponent {

    loyaltyCard: LoyaltyCard;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private loyaltyCardService: LoyaltyCardService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['loyaltyCard']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.loyaltyCardService.delete(id).subscribe(response => {
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

    constructor (
        private route: ActivatedRoute,
        private loyaltyCardPopupService: LoyaltyCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.loyaltyCardPopupService
                .open(LoyaltyCardDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
