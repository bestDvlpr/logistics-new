import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {Address} from "./address.model";
import {AddressPopupService} from "./address-popup.service";
import {AddressService} from "./address.service";
import {Location, LocationService} from "../location";
import {Client, ClientService} from "../client";
import {Receipt, ReceiptService} from "../receipt";
import {DataHolderService} from "../receipt/data-holder.service";
import {EnumAware} from "../receipt/doctypaware.decorator";
import {LocationType} from "../location/location.model";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-address-dialog',
    templateUrl: './address-dialog.component.html'
})
@EnumAware
export class AddressDialogComponent implements OnInit {

    address: Address;
    authorities: any[];
    isSaving: boolean;
    countries: Location[];
    regions: Location[];
    districts: Location[];
    clients: Client[];
    receipts: Receipt[];
    locations: Location[];
    locationTypeEnum = LocationType;
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private addressService: AddressService,
                private locationService: LocationService,
                private clientService: ClientService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.locationService.getAll().subscribe(
            (res: Location[]) => {
                this.locations = [];
                this.locations = res;
                this.setCountries();
                if (this.address.id !== null) {
                    this.setRegions();
                    this.setDistricts();
                }
            }, (res: Response) => this.onError(res.json()));
        this.clientService.query().subscribe(
            (res: Response) => {
                this.clients = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.receiptService.getAll().subscribe(
            (res: Receipt[]) => {
                this.receipts = res;
            }, (res: Response) => this.onError(res.json()));
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    setCountries() {
        if (this.locations.length > 0) {
            this.countries = [];
            for (let location of this.locations) {
                if (location.type === this.locationTypeEnum.COUNTRY) {
                    this.countries.push(location);
                }
            }
        }
    }

    setRegions() {
        if (this.locations.length > 0) {
            this.regions = [];
            for (let location of this.locations) {
                if (location.parent !== null && location.parent.id === this.address.countryId) {
                    this.regions.push(location);
                }
            }
        }
    }

    setDistricts() {
        if (this.locations.length > 0) {
            this.districts = [];
            for (let location of this.locations) {
                if (location.parent !== null && location.parent.id === this.address.regionId) {
                    this.districts.push(location);
                }
            }
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dataHolderService._client !== null && this.dataHolderService._client.id !== null) {
            this.address.clientId = this.dataHolderService._client.id;
        }
        if (this.dataHolderService._company !== null && this.dataHolderService._company.id !== null) {
            this.address.companyId = this.dataHolderService._company.id;
        }

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
