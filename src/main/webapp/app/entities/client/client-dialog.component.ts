import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import {Client} from './client.model';
import {ClientPopupService} from './client-popup.service';
import {ClientService} from './client.service';
import {PhoneNumber, PhoneType} from '../phone-number/phone-number.model';
import {EnumAware} from '../receipt/doctypaware.decorator';
@Component({
    selector: 'jhi-client-dialog',
    templateUrl: './client-dialog.component.html'
})
@EnumAware
export class ClientDialogComponent implements OnInit {

    client: Client;
    mobilePhoneNumber: PhoneNumber = new PhoneNumber;
    homePhoneNumber: PhoneNumber = new PhoneNumber;
    authorities: any[];
    isSaving: boolean;

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private clientService: ClientService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['client', 'receipt', 'productEntry', 'address', 'phoneNumber', 'product']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        if (this.client !== null && this.client.numbers !== null) {
            for (let number of this.client.numbers) {
                if (number.type === PhoneType.MOBILE) {
                    this.mobilePhoneNumber = number;
                }
                if (number.type === PhoneType.HOME) {
                    this.homePhoneNumber = number;
                }
            }
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.homePhoneNumber !== null && this.mobilePhoneNumber !== null) {
            this.homePhoneNumber.type = PhoneType.HOME;
            this.mobilePhoneNumber.type = PhoneType.MOBILE;

            this.client.numbers = [];
            this.client.numbers.push(this.homePhoneNumber, this.mobilePhoneNumber);
        }
        if (this.client.id !== undefined) {
            this.clientService.update(this.client)
                .subscribe((res: Client) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.clientService.create(this.client)
                .subscribe((res: Client) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Client) {
        this.eventManager.broadcast({name: 'clientListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-client-popup',
    template: ''
})
export class ClientPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private clientPopupService: ClientPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.clientPopupService
                    .open(ClientDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientPopupService
                    .open(ClientDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
