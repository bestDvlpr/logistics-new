import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ReceiptStatus } from './receipt-status.model';
import { ReceiptStatusService } from './receipt-status.service';
@Injectable()
export class ReceiptStatusPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private receiptStatusService: ReceiptStatusService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.receiptStatusService.find(id).subscribe(receiptStatus => {
                this.receiptStatusModalRef(component, receiptStatus);
            });
        } else {
            return this.receiptStatusModalRef(component, new ReceiptStatus());
        }
    }

    receiptStatusModalRef(component: Component, receiptStatus: ReceiptStatus): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.receiptStatus = receiptStatus;
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
