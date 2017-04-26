import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';

import {Client} from './client.model';
import {ClientPopupService} from './client-popup.service';
import {ClientService} from './client.service';
import {PhoneNumber} from '../phone-number/phone-number.model';
import {EnumAware} from '../receipt/doctypaware.decorator';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
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
    public myForm: FormGroup; // our form model

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private clientService: ClientService,
                private eventManager: EventManager,
                private formBuilder: FormBuilder) {
        this.jhiLanguageService.setLocations(
            ['client',
                'receipt',
                'productEntry',
                'address',
                'phoneNumber',
                'product',
                'phoneType',
                'receiptStatus'
            ]
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];

        if (this.client.numbers.length > 0) {
            this.myForm = this.formBuilder.group({
                id: [],
                regDate: [],
                firstName: ['', [Validators.required, Validators.minLength(2)]],
                lastName: ['', [Validators.required, Validators.minLength(2)]],
                numbers: this.formBuilder.array([]),
            });
            let numbers: FormArray = this.formBuilder.array([]);
            for (let number of this.client.numbers) {
                let num: FormGroup = this.initPhoneNumbers();
                num.controls['id'].setValue(number.id);
                num.controls['number'].setValue(number.number);
                num.controls['type'].setValue(number.type);
                num.controls['client'].setValue(number.client);
                (<FormArray> this.myForm.controls['numbers']).push(num);
            }
            (<FormControl> this.myForm.controls['id']).setValue(this.client.id);
            (<FormControl> this.myForm.controls['regDate']).setValue(this.client.regDate);
            (<FormControl> this.myForm.controls['firstName']).setValue(this.client.firstName);
            (<FormControl> this.myForm.controls['lastName']).setValue(this.client.lastName);

        } else {
            this.myForm = this.formBuilder.group({
                id: [],
                firstName: ['', [Validators.required, Validators.minLength(2)]],
                lastName: ['', [Validators.required, Validators.minLength(2)]],
                numbers: this.formBuilder.array([
                    this.initPhoneNumbers(),
                ])
            });
        }
    }

    initPhoneNumbers() {
        return this.formBuilder.group({
            id: [null],
            number: ['', [Validators.required, Validators.pattern('\\+\\d{12}')]],
            type: ['', [Validators.required]],
            client: [null]
        });
    }

    addPhoneNumber() {
        // add address to the list
        const control = <FormArray>this.myForm.controls['numbers'];
        control.push(this.initPhoneNumbers());
    }

    removeNumber(i: number) {
        // remove address from the list
        const control = <FormArray>this.myForm.controls['numbers'];
        control.removeAt(i);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save(model: FormGroup) {
        this.isSaving = true;
        /*if (this.homePhoneNumber !== null && this.mobilePhoneNumber !== null) {
         this.homePhoneNumber.type = PhoneType.HOME;
         this.mobilePhoneNumber.type = PhoneType.MOBILE;

         this.client.numbers = [];
         this.client.numbers.push(this.homePhoneNumber, this.mobilePhoneNumber);
         }*/
        if (model.value.id !== undefined) {
            this.clientService.update(model.value)
                .subscribe((res: Client) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.clientService.create(model.value)
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
