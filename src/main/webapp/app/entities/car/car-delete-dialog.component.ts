import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiEventManager} from "ng-jhipster";

import {Car} from "./car.model";
import {CarPopupService} from "./car-popup.service";
import {CarService} from "./car.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-car-delete-dialog',
    templateUrl: './car-delete-dialog.component.html'
})
export class CarDeleteDialogComponent implements OnInit {
    ngOnInit(): void {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    car: Car;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private carService: CarService,
                public activeModal: NgbActiveModal,
                private eventManager: JhiEventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.carService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carListModification',
                content: 'Deleted an car'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-car-delete-popup',
    template: ''
})
export class CarDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private carPopupService: CarPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.carPopupService
                .open(CarDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
