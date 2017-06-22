import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {CarType} from "./car-type.model";
import {CarTypePopupService} from "./car-type-popup.service";
import {CarTypeService} from "./car-type.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-car-type-dialog',
    templateUrl: './car-type-dialog.component.html'
})
export class CarTypeDialogComponent implements OnInit {

    carType: CarType;
    authorities: any[];
    isSaving: boolean;
    languages: any[];

    constructor(
        public activeModal: NgbActiveModal,
        private languageHelper: JhiLanguageHelper,
        private alertService: JhiAlertService,
        private carTypeService: CarTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.carType.id !== undefined) {
            this.carTypeService.update(this.carType)
                .subscribe((res: CarType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.carTypeService.create(this.carType)
                .subscribe((res: CarType) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CarType) {
        this.eventManager.broadcast({ name: 'carTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-car-type-popup',
    template: ''
})
export class CarTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private carTypePopupService: CarTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.carTypePopupService
                    .open(CarTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.carTypePopupService
                    .open(CarTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
