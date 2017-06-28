import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {PhoneNumber} from './phone-number.model';
import {PhoneNumberService} from './phone-number.service';
@Injectable()
export class PhoneNumberPopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private phoneNumberService: PhoneNumberService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.phoneNumberService.find(id).subscribe((phoneNumber) => {
                this.phoneNumberModalRef(component, phoneNumber);
            });
        } else {
            return this.phoneNumberModalRef(component, new PhoneNumber());
        }
    }

    phoneNumberModalRef(component: Component, phoneNumber: PhoneNumber): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.phoneNumber = phoneNumber;
        modalRef.result.then(() => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        });
        return modalRef;
    }
}
