import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {JhiLanguageService} from "ng-jhipster";
import {Receipt, WholeSaleFlag} from "./receipt.model";
import {ReceiptService} from "./receipt.service";
import {ProductEntry} from "../product-entry/product-entry.model";
import {Client} from "../client/client.model";
import {DataHolderService} from "./data-holder.service";
import {Address} from "../address/address.model";
import {NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {Company} from "../company/company.model";
import {EnumAware} from "./doctypaware.decorator";
@Component({
    selector: 'jhi-receipt-send-product',
    templateUrl: 'receipt-send-product.component.html'
})
@EnumAware
export class ReceiptSendProductComponent implements OnInit {

    receipt: Receipt;
    phoneNumber: string;
    client: Client;
    company: Company;
    address: Address;
    productEntries: ProductEntry[];
    productsSelected: ProductEntry[] = [];
    isCollapsed = false;
    isAllChecked: boolean = false;
    formattedStartTime: string;
    startTime: any = null;
    endTime: any = null;
    deliveryDate: any = null;
    minDate: NgbDateStruct;
    wholeSaleFlagEnum = WholeSaleFlag;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                public dataHolderService: DataHolderService,
                private router: Router) {
        this.jhiLanguageService.setLocations(
            [
                'receipt',
                'docType',
                'wholeSaleFlag',
                'productEntry',
                'product',
                'client',
                'phoneNumber',
                'address',
                'car',
                'receiptStatus',
                'salesType'
            ]
        );
    }

    ngOnInit() {
        let date = new Date();
        this.receipt = this.dataHolderService._receipt;
        this.client = this.dataHolderService._client;
        this.company = this.dataHolderService._company;
        this.address = this.dataHolderService._address;
        this.productEntries = [];
        for (let prod of this.dataHolderService._receipt.productEntries) {
            if (prod.addressId === null) {
                this.productEntries.push(prod);
            }
        }
        this.minDate = {year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate()};
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
    }

    previousState() {
        window.history.back();
    }

    public goClientSelectStep() {
        if (this.startTime !== null && this.endTime !== null) {
            this.receipt.fromTime = ((this.startTime.hour < 10) ? '0' + this.startTime.hour : this.startTime.hour) +
                ':' + ((this.startTime.minute < 10) ? '0' + this.startTime.minute : this.startTime.minute);
            this.receipt.toTime = ((this.endTime.hour < 10) ? '0' + this.endTime.hour : this.endTime.hour) +
                ':' + ((this.endTime.minute < 10) ? '0' + this.endTime.minute : this.endTime.minute);
        }

        const deliveryDateTime = new Date(this.deliveryDate.year +
            '-' + ((this.deliveryDate.month < 10) ? '0' + this.deliveryDate.month : this.deliveryDate.month) +
            '-' + ((this.deliveryDate.day < 10) ? '0' + this.deliveryDate.day : this.deliveryDate.day)).getTime();

        this.receipt.deliveryDate = deliveryDateTime;

        this.dataHolderService._receipt = this.receipt;
        this.dataHolderService._client = this.client;
        this.dataHolderService._company = this.company;
        this.dataHolderService._address = this.address;
        for (let prod of this.productsSelected) {
            for (let pro of this.receipt.productEntries) {
                if (prod.id === pro.id) {
                    pro.addressId = this.address.id;
                    pro.address = this.address;
                    pro.deliveryDate = deliveryDateTime;
                }
            }
        }
        this.router.navigate(['../receipt/' + this.receipt.id + '/send/client']);
    }

    productChecked(product: ProductEntry) {
        let indexProd: number = this.productEntries.indexOf(product);
        if (indexProd !== null && indexProd !== -1) {
            if (!product.selected) {
                this.productsSelected.push(product);
                if (this.productsSelected.length === this.productEntries.length) {
                    this.isAllChecked = true;
                }
            } else {
                this.productsSelected.splice(this.productsSelected.indexOf(product), 1);
                if (this.isAllChecked) {
                    this.isAllChecked = false;
                }
            }
            product.selected = !product.selected;
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
            this.isAllChecked = !this.isAllChecked;
            this.productsSelected = [];
        }
    }
}
