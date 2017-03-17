import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { PhoneNumber } from './phone-number.model';
import { PhoneNumberPopupService } from './phone-number-popup.service';
import { PhoneNumberService } from './phone-number.service';
import { Client, ClientService } from '../client';
@Component({
    selector: 'jhi-phone-number-dialog',
    templateUrl: './phone-number-dialog.component.html'
})
export class PhoneNumberDialogComponent implements OnInit {

    phoneNumber: PhoneNumber;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private phoneNumberService: PhoneNumberService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['phoneNumber', 'receipt', 'productEntry', 'address', 'client', 'product']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService.query().subscribe(
            (res: Response) => { this.clients = res.json(); }, (res: Response) => this.onError(res.json())
        );
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.phoneNumber.id !== undefined) {
            this.phoneNumberService.update(this.phoneNumber)
                .subscribe((res: PhoneNumber) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.phoneNumberService.create(this.phoneNumber)
                .subscribe((res: PhoneNumber) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: PhoneNumber) {
        this.eventManager.broadcast({ name: 'phoneNumberListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-phone-number-popup',
    template: ''
})
export class PhoneNumberPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private phoneNumberPopupService: PhoneNumberPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.phoneNumberPopupService
                    .open(PhoneNumberDialogComponent, params['id']);
            } else {
                this.modalRef = this.phoneNumberPopupService
                    .open(PhoneNumberDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
