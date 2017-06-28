import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {LoyaltyCard} from './loyalty-card.model';
import {LoyaltyCardPopupService} from './loyalty-card-popup.service';
import {LoyaltyCardService} from './loyalty-card.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
@Component({
    selector: 'jhi-loyalty-card-dialog',
    templateUrl: './loyalty-card-dialog.component.html'
})
export class LoyaltyCardDialogComponent implements OnInit {

    loyaltyCard: LoyaltyCard;
    authorities: any[];
    isSaving: boolean;
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private loyaltyCardService: LoyaltyCardService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.loyaltyCard.id !== undefined) {
            this.loyaltyCardService.update(this.loyaltyCard)
                .subscribe((res: LoyaltyCard) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.loyaltyCardService.create(this.loyaltyCard)
                .subscribe((res: LoyaltyCard) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: LoyaltyCard) {
        this.eventManager.broadcast({name: 'loyaltyCardListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-loyalty-card-popup',
    template: ''
})
export class LoyaltyCardPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private loyaltyCardPopupService: LoyaltyCardPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.loyaltyCardPopupService
                    .open(LoyaltyCardDialogComponent, params['id']);
            } else {
                this.modalRef = this.loyaltyCardPopupService
                    .open(LoyaltyCardDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
