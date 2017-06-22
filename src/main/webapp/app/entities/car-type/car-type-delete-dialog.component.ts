import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {CarType} from "./car-type.model";
import {CarTypePopupService} from "./car-type-popup.service";
import {CarTypeService} from "./car-type.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-car-type-delete-dialog',
    templateUrl: './car-type-delete-dialog.component.html'
})
export class CarTypeDeleteDialogComponent implements OnInit {

    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    carType: CarType;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private carTypeService: CarTypeService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.carTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carTypeListModification',
                content: 'Deleted an carType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-car-type-delete-popup',
    template: ''
})
export class CarTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private carTypePopupService: CarTypePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.carTypePopupService
                .open(CarTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
