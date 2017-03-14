import {Component, OnInit} from '@angular/core';
import {JhiLanguageService, AlertService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Product} from "../product/product.model";
import {TranslateService} from 'ng2-translate'

@Component({
    selector: 'jhi-receipt-send-client',
    templateUrl: 'receipt-send-address.component.html'
})
export class ReceiptSendAddressComponent implements OnInit {
    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    productEntries: ProductEntry[];
    private clientSelected: boolean = false;
    public isCollapsed = true;
    public productsSelected: Product[] = [];
    public addressSelected: number;

    constructor(private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private translateService: TranslateService,
                public dataHolderService: DataHolderService) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']);
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
    }

    previousState() {
        window.history.back();
    }

    public toggleClientSelected() {
        this.clientSelected = !this.clientSelected;
    }

    public productChecked(product: Product) {
        let indexProd: number = this.productsSelected.indexOf(product);
        if (indexProd != null && indexProd != -1) {
            console.log(indexProd);
            this.productsSelected.splice(indexProd, 1);
            console.log(this.productsSelected);
        } else {
            this.productsSelected.push(product);
            console.log(this.productsSelected);
        }
    }

    public assignAddressToProduct() {
        if (this.addressSelected == null ||
            this.addressSelected === undefined ||
            this.productsSelected.length <= 0) {
            this.alertService.error(this.translateService.instant('error.emailexists'));
            console.log(this.alertService.get());
        } else {
            console.log(this.addressSelected);
            console.log(this.productsSelected);
        }
    }
}
