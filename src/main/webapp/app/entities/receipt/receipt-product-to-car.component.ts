import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {JhiLanguageService, EventManager, AlertService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Address} from '../address/address.model';
import {Response} from '@angular/http';

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

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                private alertService: AlertService,
                private router: Router,
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
    }

    /**
     * Checks whether one of the receipt products has not been attached car
     **/
    private checkProductCar() {
        for (let a of this.dataHolderService._receipt.productEntries) {
            if (a.attachedCarId === null && a.attachedCarNumber === null) {
                this.prodsWithoutCar = true;
            }
        }
    }

    /**
     * Find receipt by id
     * @param id
     * @return receipt
     * */
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
        if (this.dataHolderService._autocompleteSelected !== null) {
            for (let selObj of this.productsSelected) {
                let index: number = this.receipt.productEntries.indexOf(selObj, 0);
                this.receipt.productEntries[index].attachedCarNumber = this.dataHolderService._autocompleteSelected.name;
                this.receipt.productEntries[index].attachedCarId = this.dataHolderService._autocompleteSelected.id;
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
        this.router.navigate(['receipt']);
    }

    private onSaveSuccess(data) {
        this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
        this.isSaving = false;
        console.log(data);
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
