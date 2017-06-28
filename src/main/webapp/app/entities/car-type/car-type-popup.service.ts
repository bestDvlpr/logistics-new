import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {CarType} from './car-type.model';
import {CarTypeService} from './car-type.service';
@Injectable()
export class CarTypePopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private carTypeService: CarTypeService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.carTypeService.find(id).subscribe((carType) => {
                this.carTypeModalRef(component, carType);
            });
        } else {
            return this.carTypeModalRef(component, new CarType());
        }
    }

    carTypeModalRef(component: Component, carType: CarType): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.carType = carType;
        modalRef.result.then((result) => {
            console.log(`Closed with: ${result}`);
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        });
        return modalRef;
    }
}
