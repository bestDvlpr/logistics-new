import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DriverStatus } from './driver-status.model';
import { DriverStatusService } from './driver-status.service';
@Injectable()
export class DriverStatusPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private driverStatusService: DriverStatusService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.driverStatusService.find(id).subscribe(driverStatus => {
                this.driverStatusModalRef(component, driverStatus);
            });
        } else {
            return this.driverStatusModalRef(component, new DriverStatus());
        }
    }

    driverStatusModalRef(component: Component, driverStatus: DriverStatus): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.driverStatus = driverStatus;
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
