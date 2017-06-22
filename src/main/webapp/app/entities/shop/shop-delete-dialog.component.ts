import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {Shop} from "./shop.model";
import {ShopPopupService} from "./shop-popup.service";
import {ShopService} from "./shop.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-shop-delete-dialog',
    templateUrl: './shop-delete-dialog.component.html'
})
export class ShopDeleteDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    shop: Shop;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private shopService: ShopService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shopService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'shopListModification',
                content: 'Deleted an shop'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shop-delete-popup',
    template: ''
})
export class ShopDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private shopPopupService: ShopPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.shopPopupService
                .open(ShopDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
