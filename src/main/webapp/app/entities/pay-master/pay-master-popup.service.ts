import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PayMaster } from './pay-master.model';
import { PayMasterService } from './pay-master.service';
@Injectable()
export class PayMasterPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private payMasterService: PayMasterService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.payMasterService.find(id).subscribe(payMaster => {
                this.payMasterModalRef(component, payMaster);
            });
        } else {
            return this.payMasterModalRef(component, new PayMaster());
        }
    }

    payMasterModalRef(component: Component, payMaster: PayMaster): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.payMaster = payMaster;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
