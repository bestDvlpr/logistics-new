import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {PayType} from './pay-type.model';
import {PayTypeService} from './pay-type.service';
@Injectable()
export class PayTypePopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private payTypeService: PayTypeService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.payTypeService.find(id).subscribe((payType) => {
                this.payTypeModalRef(component, payType);
            });
        } else {
            return this.payTypeModalRef(component, new PayType());
        }
    }

    payTypeModalRef(component: Component, payType: PayType): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payType = payType;
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
