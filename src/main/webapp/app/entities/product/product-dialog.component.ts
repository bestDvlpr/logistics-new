import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {Product} from "./product.model";
import {ProductPopupService} from "./product-popup.service";
import {ProductService} from "./product.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-product-dialog',
    templateUrl: './product-dialog.component.html'
})
export class ProductDialogComponent implements OnInit {

    product: Product;
    authorities: any[];
    isSaving: boolean;
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private productService: ProductService,
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
        if (this.product.id !== undefined) {
            this.productService.update(this.product)
                .subscribe((res: Product) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.productService.create(this.product)
                .subscribe((res: Product) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Product) {
        this.eventManager.broadcast({name: 'productListModification', content: 'OK'});
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
    selector: 'jhi-product-popup',
    template: ''
})
export class ProductPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private productPopupService: ProductPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.productPopupService
                    .open(ProductDialogComponent, params['id']);
            } else {
                this.modalRef = this.productPopupService
                    .open(ProductDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
