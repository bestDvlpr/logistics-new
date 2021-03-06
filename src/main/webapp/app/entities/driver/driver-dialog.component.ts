import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {Driver} from './driver.model';
import {DriverPopupService} from './driver-popup.service';
import {DriverService} from './driver.service';
import {Car, CarService} from '../car';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
@Component({
    selector: 'jhi-driver-dialog',
    templateUrl: './driver-dialog.component.html'
})
export class DriverDialogComponent implements OnInit {

    driver: Driver;
    authorities: any[];
    isSaving: boolean;
    cars: Car[];
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private driverService: DriverService,
                private carService: CarService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.carService.query().subscribe(
            (res: Response) => {
                this.cars = res.json();
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
        if (this.driver.id !== undefined) {
            this.driverService.update(this.driver)
                .subscribe((res: Driver) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.driverService.create(this.driver)
                .subscribe((res: Driver) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Driver) {
        this.eventManager.broadcast({name: 'driverListModification', content: 'OK'});
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

    trackCarById(index: number, item: Car) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-driver-popup',
    template: ''
})
export class DriverPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private driverPopupService: DriverPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.driverPopupService
                    .open(DriverDialogComponent, params['id']);
            } else {
                this.modalRef = this.driverPopupService
                    .open(DriverDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
