import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {JhiLanguageService, EventManager} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Address} from '../address/address.model';

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
    public productsSelected: ProductEntry[] = [];
    public prodsWithoutCar = false;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
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
        this.checkProductCar();
    }

    private checkProductCar() {
        for (let a of this.dataHolderService._receipt.productEntries) {
            if (a.attachedCarId === null && a.attachedCarNumber === null) {
                this.prodsWithoutCar = true;
            }
        }
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    productChecked(product: ProductEntry) {
        let indexProd: number = this.productsSelected.indexOf(product);
        if (indexProd !== null && indexProd !== -1) {
            this.productsSelected.splice(indexProd, 1);
        } else {
            this.productsSelected.push(product);
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

    attachDrivers() {
        if (!this.prodsWithoutCar) {
            this.receiptService.attachDrivers(this.receipt).subscribe(res => {
                this.receipt = res;
            });
        }
        this.router.navigate(['receipt']);
        this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
    }
}
