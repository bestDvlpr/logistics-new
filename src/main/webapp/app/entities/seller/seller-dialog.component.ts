import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {Seller} from "./seller.model";
import {SellerPopupService} from "./seller-popup.service";
import {SellerService} from "./seller.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-seller-dialog',
    templateUrl: './seller-dialog.component.html'
})
export class SellerDialogComponent implements OnInit {

    seller: Seller;
    authorities: any[];
    isSaving: boolean;
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private sellerService: SellerService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'];
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seller.id !== undefined) {
            this.sellerService.update(this.seller)
                .subscribe((res: Seller) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sellerService.create(this.seller)
                .subscribe((res: Seller) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Seller) {
        this.eventManager.broadcast({name: 'sellerListModification', content: 'OK'});
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
    selector: 'jhi-seller-popup',
    template: ''
})
export class SellerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private sellerPopupService: SellerPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.sellerPopupService
                    .open(SellerDialogComponent, params['id']);
            } else {
                this.modalRef = this.sellerPopupService
                    .open(SellerDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
