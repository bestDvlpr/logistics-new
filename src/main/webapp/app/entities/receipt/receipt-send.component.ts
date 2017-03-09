import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService, AlertService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ProductEntry} from '../product-entry/product-entry.model';
import {ProductEntryService} from '../product-entry/product-entry.service';
import {Response} from '@angular/http';
import {PhoneNumberService} from '../phone-number/phone-number.service';
import {ClientService} from '../client/client.service';
import {Client} from '../client/client.model';

@Component({
    selector: 'jhi-receipt-send',
    templateUrl: './receipt-send.component.html'
})
export class ReceiptSendComponent implements OnInit {

    receipt: Receipt;
    private subscription: any;
    phoneNumber: string;
    client: Client;
    productEntries: ProductEntry[];

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                private route: ActivatedRoute,
                private alertService: AlertService,
                private clientService: ClientService,
                private productEntryService: ProductEntryService) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'productEntry', 'product', 'client', 'phoneNumber']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
        this.productEntryService.byReceipt(id).subscribe((res: Response) => {
            this.productEntries = res.json();
        }, (res: Response) => this.onError(res.json()));
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    trackProductEntryById(index: number, item: ProductEntry) {
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

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    /**
     *
     * ng2-select component functions
     *
     * */
    private value: any = {};
    private _disabledV: string = '0';
    private disabled: boolean = false;

    private get disabledV(): string {
        return this._disabledV;
    }

    private set disabledV(value: string) {
        this._disabledV = value;
        this.disabled = this._disabledV === '1';
    }

    public selected(value: any): void {
        console.log('Selected value is: ', value);
    }

    public removed(value: any): void {
        console.log('Removed value is: ', value);
    }

    public typed(value: any): void {
        console.log('New search input: ', value);
    }

    public refreshValue(value: any): void {
        this.value = value;
    }

    public findClient() {
        this.clientService.byPhoneNumber(this.phoneNumber).subscribe((res: Response) => {
            this.client = res.json();
            console.log(this.client);
        });
    }

}
