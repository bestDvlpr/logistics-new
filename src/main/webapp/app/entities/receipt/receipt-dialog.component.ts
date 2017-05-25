import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Receipt } from './receipt.model';
import { ReceiptPopupService } from './receipt-popup.service';
import { ReceiptService } from './receipt.service';
import { PayMaster, PayMasterService } from '../pay-master';
import { LoyaltyCard, LoyaltyCardService } from '../loyalty-card';
import { Client, ClientService } from '../client';
import { ProductEntry, ProductEntryService } from '../product-entry';
import { Car, CarService } from '../car';
import { Address, AddressService } from '../address';
import {Company} from '../company/company.model';
import {CompanyService} from '../company/company.service';
@Component({
    selector: 'jhi-receipt-dialog',
    templateUrl: './receipt-dialog.component.html'
})
export class ReceiptDialogComponent implements OnInit {

    receipt: Receipt;
    authorities: any[];
    isSaving: boolean;

    paymasters: PayMaster[];

    loyaltycards: LoyaltyCard[];

    clients: Client[];

    productentries: ProductEntry[];

    cars: Car[];

    companies: Company[];

    addresses: Address[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private receiptService: ReceiptService,
        private payMasterService: PayMasterService,
        private loyaltyCardService: LoyaltyCardService,
        private clientService: ClientService,
        private productEntryService: ProductEntryService,
        private carService: CarService,
        private addressService: AddressService,
        private eventManager: EventManager,
        private companyService: CompanyService
    ) {
        this.jhiLanguageService.setLocations(
            [
                'receipt',
                'docType',
                'wholeSaleFlag',
                'receiptStatus',
                'address',
                'company'
            ]
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.payMasterService.query().subscribe(
            (res: Response) => { this.paymasters = res.json(); }, (res: Response) => this.onError(res.json()));
        this.loyaltyCardService.query().subscribe(
            (res: Response) => { this.loyaltycards = res.json(); }, (res: Response) => this.onError(res.json()));
        this.clientService.query().subscribe(
            (res: Response) => { this.clients = res.json(); }, (res: Response) => this.onError(res.json()));
        this.productEntryService.query().subscribe(
            (res: Response) => { this.productentries = res.json(); }, (res: Response) => this.onError(res.json()));
        this.carService.query().subscribe(
            (res: Response) => { this.cars = res.json(); }, (res: Response) => this.onError(res.json()));
        this.addressService.query().subscribe(
            (res: Response) => { this.addresses = res.json(); }, (res: Response) => this.onError(res.json()));
        this.companyService.all().subscribe(
            (res: Response) => { this.companies = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.receipt.id !== undefined) {
            this.receiptService.update(this.receipt)
                .subscribe((res: Receipt) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.receiptService.create(this.receipt)
                .subscribe((res: Receipt) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Receipt) {
        this.eventManager.broadcast({ name: 'receiptListModification', content: 'OK'});
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

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-receipt-popup',
    template: ''
})
export class ReceiptPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private receiptPopupService: ReceiptPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.receiptPopupService
                    .open(ReceiptDialogComponent, params['id']);
            } else {
                this.modalRef = this.receiptPopupService
                    .open(ReceiptDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
