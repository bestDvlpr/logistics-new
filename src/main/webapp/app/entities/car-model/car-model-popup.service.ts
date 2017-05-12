import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CarModel } from './car-model.model';
import { CarModelService } from './car-model.service';
@Injectable()
export class CarModelPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private carModelService: CarModelService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.carModelService.find(id).subscribe(carModel => {
                this.carModelModalRef(component, carModel);
            });
        } else {
            return this.carModelModalRef(component, new CarModel());
        }
    }

    carModelModalRef(component: Component, carModel: CarModel): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.carModel = carModel;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
