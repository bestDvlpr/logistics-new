import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LocationType } from './location-type.model';
import { LocationTypeService } from './location-type.service';
@Injectable()
export class LocationTypePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private locationTypeService: LocationTypeService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.locationTypeService.find(id).subscribe(locationType => {
                this.locationTypeModalRef(component, locationType);
            });
        } else {
            return this.locationTypeModalRef(component, new LocationType());
        }
    }

    locationTypeModalRef(component: Component, locationType: LocationType): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.locationType = locationType;
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
