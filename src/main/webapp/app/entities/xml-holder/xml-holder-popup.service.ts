import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {DatePipe} from '@angular/common';
import {XmlHolder} from './xml-holder.model';
import {XmlHolderService} from './xml-holder.service';
@Injectable()
export class XmlHolderPopupService {
    private isOpen = false;

    constructor(private datePipe: DatePipe,
                private modalService: NgbModal,
                private router: Router,
                private xmlHolderService: XmlHolderService) {
    }

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.xmlHolderService.find(id).subscribe((xmlHolder) => {
                xmlHolder.date = this.datePipe.transform(xmlHolder.date, 'yyyy-MM-ddThh:mm');
                this.xmlHolderModalRef(component, xmlHolder);
            });
        } else {
            return this.xmlHolderModalRef(component, new XmlHolder());
        }
    }

    xmlHolderModalRef(component: Component, xmlHolder: XmlHolder): NgbModalRef {
        const modalRef = this.modalService.open(component, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.xmlHolder = xmlHolder;
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
