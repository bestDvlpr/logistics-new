import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {Client} from '../client/client.model';
import {DataHolderService} from './data-holder.service';
import {Address} from '../address/address.model';

@Component({
    selector: 'jhi-collapsed-receipt',
    templateUrl: 'collapsed-receipt.component.html'
})
export class CollapsedReceiptComponent implements OnInit {

    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    address: Address;
    productEntries: ProductEntry[];
    public productsSelected: ProductEntry[] = [];
    public isCollapsed = false;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                private router: Router) {
        this.jhiLanguageService.setLocations(
            ['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber', 'address']
        );
    }

    ngOnInit() {
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        this.address = this.dataHolderService._address;
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    public productChecked(product: ProductEntry) {
        let indexProd: number = this.productsSelected.indexOf(product);
        if (indexProd !== null && indexProd !== -1) {
            this.productsSelected.splice(indexProd, 1);
        } else {
            this.productsSelected.push(product);
        }
    }

    public goClientSelectStep() {
        this.dataHolderService._client = this.client;
        this.dataHolderService._receipt = this.receipt;
        this.dataHolderService._address = this.address;
        for (let prod of this.productsSelected) {
            for (let pro of this.receipt.productEntries) {
                if (prod.id === pro.id) {
                    pro.addressId = this.address.id;
                    pro.address = this.address;
                }
            }
        }
        this.router.navigate(['../receipt/' + this.receipt.id + '/send/client']);
    }
}
