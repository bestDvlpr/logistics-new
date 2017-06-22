import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {Car} from "./car.model";
import {CarPopupService} from "./car-popup.service";
import {CarService} from "./car.service";
import {CarModel, CarModelService} from "../car-model";
import {CarColor, CarColorService} from "../car-color";
import {CarType, CarTypeService} from "../car-type";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-car-dialog',
    templateUrl: './car-dialog.component.html'
})
export class CarDialogComponent implements OnInit {

    car: Car;
    authorities: any[];
    isSaving: boolean;
    carmodels: CarModel[];
    carcolors: CarColor[];
    cartypes: CarType[];
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private carService: CarService,
                private carModelService: CarModelService,
                private carColorService: CarColorService,
                private carTypeService: CarTypeService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.carModelService.query().subscribe(
            (res: Response) => {
                this.carmodels = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.carColorService.query().subscribe(
            (res: Response) => {
                this.carcolors = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.carTypeService.query().subscribe(
            (res: Response) => {
                this.cartypes = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.car.id !== undefined) {
            this.carService.update(this.car)
                .subscribe((res: Car) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.carService.create(this.car)
                .subscribe((res: Car) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Car) {
        this.eventManager.broadcast({name: 'carListModification', content: 'OK'});
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

    trackCarModelById(index: number, item: CarModel) {
        return item.id;
    }

    trackCarColorById(index: number, item: CarColor) {
        return item.id;
    }

    trackCarTypeById(index: number, item: CarType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-car-popup',
    template: ''
})
export class CarPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private carPopupService: CarPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.carPopupService
                    .open(CarDialogComponent, params['id']);
            } else {
                this.modalRef = this.carPopupService
                    .open(CarDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
