import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import {Address} from './address.model';
import {AddressPopupService} from './address-popup.service';
import {AddressService} from './address.service';
import {Location, LocationService} from '../location';
import {Client, ClientService} from '../client';
import {Receipt, ReceiptService} from '../receipt';
import { FormsModule } from '@angular/forms';
@Component({
    selector: 'jhi-address-dialog',
    templateUrl: './address-dialog.component.html'
})
export class AddressDialogComponent implements OnInit {

    address: Address;
    authorities: any[];
    isSaving: boolean;
    countries: Location[];
    regions: Location[];
    cities: Location[];
    districts: Location[];
    clients: Client[];
    receipts: Receipt[];

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private addressService: AddressService,
                private locationService: LocationService,
                private clientService: ClientService,
                private receiptService: ReceiptService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['address', 'phoneNumber', 'receipt', 'productEntry', 'address', 'client', 'product']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.locationService.findCountries().subscribe(
            (res: Response) => {
                this.countries = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.clientService.query().subscribe(
            (res: Response) => {
                this.clients = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.receiptService.query().subscribe(
            (res: Response) => {
                this.receipts = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    setRegions() {
        this.locationService.findChildren(this.address.countryId).subscribe(
            (res: Response) => {
                this.regions = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    setCities() {
        this.locationService.findChildren(this.address.regionId).subscribe(
            (res: Response) => {
                this.cities = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    setDistricts() {
        this.locationService.findChildren(this.address.cityId).subscribe(
            (res: Response) => {
                this.districts = res.json();
            }, (res: Response) => this.onError(res.json()));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.address.id !== undefined) {
            this.addressService.update(this.address)
                .subscribe((res: Address) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.addressService.create(this.address)
                .subscribe((res: Address) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Address) {
        this.eventManager.broadcast({name: 'addressListModification', content: 'OK'});
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

    trackLocationById(index: number, item: Location) {
        return item.id;
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackReceiptById(index: number, item: Receipt) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-address-popup',
    template: ''
})
export class AddressPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private addressPopupService: AddressPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.addressPopupService
                    .open(AddressDialogComponent, params['id']);
            } else {
                this.modalRef = this.addressPopupService
                    .open(AddressDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
