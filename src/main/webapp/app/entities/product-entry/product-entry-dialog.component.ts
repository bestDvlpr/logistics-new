import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Response} from "@angular/http";

import {NgbActiveModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {JhiAlertService, JhiEventManager} from "ng-jhipster";

import {ProductEntry} from "./product-entry.model";
import {ProductEntryPopupService} from "./product-entry-popup.service";
import {ProductEntryService} from "./product-entry.service";
import {Product, ProductService} from "../product";
import {Seller, SellerService} from "../seller";
import {Receipt, ReceiptService} from "../receipt";
import {Driver, DriverService} from "../driver";
import {Car, CarService} from "../car";
import {Address, AddressService} from "../address";
import {User, UserService} from "../../shared";
import {Company} from "../company/company.model";
import {CompanyService} from "../company/company.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";
@Component({
    selector: 'jhi-product-entry-dialog',
    templateUrl: './product-entry-dialog.component.html'
})
export class ProductEntryDialogComponent implements OnInit {

    productEntry: ProductEntry;
    authorities: any[];
    isSaving: boolean;
    products: Product[];
    sellers: Seller[];
    receipts: Receipt[];
    drivers: Driver[];
    cars: Car[];
    companies: Company[];
    addresses: Address[];
    users: User[];
    languages: any[];

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private alertService: JhiAlertService,
                private productEntryService: ProductEntryService,
                private productService: ProductService,
                private sellerService: SellerService,
                private receiptService: ReceiptService,
                private driverService: DriverService,
                private carService: CarService,
                private addressService: AddressService,
                private userService: UserService,
                private companyService: CompanyService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => {
                this.products = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.sellerService.query().subscribe(
            (res: Response) => {
                this.sellers = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.receiptService.getAll().subscribe(
            (res: Receipt[]) => {
                this.receipts = res;
            }, (res: Response) => this.onError(res.json()));
        this.driverService.query().subscribe(
            (res: Response) => {
                this.drivers = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.carService.query().subscribe(
            (res: Response) => {
                this.cars = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.addressService.query().subscribe(
            (res: Response) => {
                this.addresses = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.userService.query().subscribe(
            (res: Response) => {
                this.users = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.companyService.all().subscribe(
            (res: Response) => {
                this.companies = res.json();
            }, (res: Response) => this.onError(res.json()));
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.productEntry.id !== undefined) {
            this.productEntryService.update(this.productEntry)
                .subscribe((res: ProductEntry) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.productEntryService.create(this.productEntry)
                .subscribe((res: ProductEntry) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: ProductEntry) {
        this.eventManager.broadcast({name: 'productEntryListModification', content: 'OK'});
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

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackSellerById(index: number, item: Seller) {
        return item.id;
    }

    trackReceiptById(index: number, item: Receipt) {
        return item.id;
    }

    trackDriverById(index: number, item: Driver) {
        return item.id;
    }

    trackCarById(index: number, item: Car) {
        return item.id;
    }

    trackAddressById(index: number, item: Address) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-entry-popup',
    template: ''
})
export class ProductEntryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private productEntryPopupService: ProductEntryPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if (params['id']) {
                this.modalRef = this.productEntryPopupService
                    .open(ProductEntryDialogComponent, params['id']);
            } else {
                this.modalRef = this.productEntryPopupService
                    .open(ProductEntryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
