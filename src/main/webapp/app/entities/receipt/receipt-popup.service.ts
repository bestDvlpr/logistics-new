import {Component, Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {Receipt} from "./receipt.model";
import {ReceiptService} from "./receipt.service";
@Injectable()
export class ReceiptPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private receiptService: ReceiptService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.receiptService.find(id).subscribe(receipt => {
                this.receiptModalRef(component, receipt);
            });
        } else {
            return this.receiptModalRef(component, new Receipt());
        }
    }

    receiptModalRef(component: Component, receipt: Receipt): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.receipt = receipt;
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
