import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {CarColor} from './car-color.model';
import {CarColorService} from './car-color.service';
@Injectable()
export class CarColorPopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private carColorService: CarColorService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.carColorService.find(id).subscribe((carColor) => {
                this.carColorModalRef(component, carColor);
            });
        } else {
            return this.carColorModalRef(component, new CarColor());
        }
    }

    carColorModalRef(component: Component, carColor: CarColor): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.carColor = carColor;
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
