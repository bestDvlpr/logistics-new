import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { XmlHolder } from './xml-holder.model';
import { XmlHolderPopupService } from './xml-holder-popup.service';
import { XmlHolderService } from './xml-holder.service';
@Component({
    selector: 'jhi-xml-holder-dialog',
    templateUrl: './xml-holder-dialog.component.html'
})
export class XmlHolderDialogComponent implements OnInit {

    xmlHolder: XmlHolder;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private xmlHolderService: XmlHolderService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['xmlHolder']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.xmlHolder.id !== undefined) {
            this.xmlHolderService.update(this.xmlHolder)
                .subscribe((res: XmlHolder) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.xmlHolderService.create(this.xmlHolder)
                .subscribe((res: XmlHolder) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: XmlHolder) {
        this.eventManager.broadcast({ name: 'xmlHolderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-xml-holder-popup',
    template: ''
})
export class XmlHolderPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private xmlHolderPopupService: XmlHolderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.xmlHolderPopupService
                    .open(XmlHolderDialogComponent, params['id']);
            } else {
                this.modalRef = this.xmlHolderPopupService
                    .open(XmlHolderDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
