import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AlertService, EventManager, JhiLanguageService} from "ng-jhipster";
import {Receipt} from "./receipt.model";
import {ReceiptService} from "./receipt.service";
import {ProductEntry} from "../product-entry/product-entry.model";
import {Client} from "../client/client.model";
import {DataHolderService} from "./data-holder.service";
import {Address} from "../address/address.model";
import {Response} from "@angular/http";
import {Car} from "../car/car.model";
import {CarService} from "../car/car.service";

@Component({
    selector: 'jhi-receipt-product-to-car',
    templateUrl: 'receipt-product-to-car.component.html'
})
export class ReceiptProductToCarComponent implements OnInit {

    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    address: Address;
    productEntries: ProductEntry[];
    productsSelected: ProductEntry[] = [];
    prodsWithoutCar = false;
    isSaving: boolean;
    isAllChecked: boolean = false;
    acObjects: string[];
    cars: Car[];
    selectedCarNumber: string;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                private alertService: AlertService,
                private router: Router,
                private carService: CarService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(
            ['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address', 'car']
        );
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        this.address = this.dataHolderService._address;
        this.productEntries = this.dataHolderService._receipt.productEntries;
        this.checkProductCar();
        this.loadCars();
    }

    /** Checks whether one of the receipt products has not been attached car */
    private checkProductCar() {
        for (let a of this.dataHolderService._receipt.productEntries) {
            if (a.attachedCarId === null && a.attachedCarNumber === null) {
                this.prodsWithoutCar = true;
            }
        }
    }

    /** Find receipt by id */
    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    productChecked(product: ProductEntry) {
        let indexProd: number = this.productEntries.indexOf(product);
        if (indexProd !== null && indexProd !== -1) {
            let productEntry = this.productEntries[indexProd];
            if (!productEntry.selected) {
                this.productsSelected.push(productEntry);
                if (this.productsSelected.length === this.productEntries.length) {
                    this.isAllChecked = true;
                }
            } else {
                this.productsSelected.splice(this.productsSelected.indexOf(productEntry), 1);
                if (this.isAllChecked) {
                    this.isAllChecked = false;
                }
            }
            productEntry.selected = !productEntry.selected;
        }
    }

    productAllChecked() {
        if (!this.isAllChecked) {
            this.productsSelected = [];
            this.productEntries.forEach(entry => {
                entry.selected = true;
                this.productsSelected.push(entry);
            });
            this.isAllChecked = !this.isAllChecked;
        } else {
            this.productEntries.forEach(entry => entry.selected = false);
            this.productsSelected = [];
            this.isAllChecked = !this.isAllChecked;
        }
    }

    attach() {
        if (this.selectedCarNumber !== null) {
            let filter = this.cars.find((x) => x.number === this.selectedCarNumber);
            let scar = this.cars[this.cars.indexOf(filter)];
            for (let selObj of this.productsSelected) {
                let index: number = this.receipt.productEntries.indexOf(selObj, 0);
                this.receipt.productEntries[index].attachedCarNumber = scar.number;
                this.receipt.productEntries[index].attachedCarId = scar.id;
            }
            this.dataHolderService._receipt = this.receipt;
        }
        this.prodsWithoutCar = false;
        this.checkProductCar();
    }

    attachCars() {
        if (!this.prodsWithoutCar) {
            this.receiptService.attachDrivers(this.receipt).subscribe(
                (res: Receipt) => this.onSaveSuccess(res),
                (res: Response) => this.onSaveError(res.json())
            );
        }
        this.router.navigate(['/accepted-receipts']);
    }

    private onSaveSuccess(data) {
        this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    loadCars() {
        this.carService.allWithoutPagination().subscribe((res) => {
            this.cars = res.json();
            this.setACObjects(this.cars);
        });
    }

    private setACObjects(cars: Car[]) {
        if (cars !== null && cars.length > 0) {
            this.acObjects = [];
            for (let car of cars) {
                this.acObjects.push(car.number);
            }
        }
    }
}
