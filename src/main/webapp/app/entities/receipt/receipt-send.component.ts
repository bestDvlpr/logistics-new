import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {JhiLanguageService, AlertService} from 'ng-jhipster';
import { Receipt } from './receipt.model';
import { ReceiptService } from './receipt.service';
import {ProductEntry} from "../product-entry/product-entry.model";
import {ProductEntryService} from "../product-entry/product-entry.service";
import { Response } from '@angular/http';

@Component({
    selector: 'jhi-receipt-send',
    templateUrl: './receipt-send.component.html'
})
export class ReceiptSendComponent implements OnInit, OnDestroy {

    receipt: Receipt;
    private subscription: any;

    productentries: ProductEntry[];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptService: ReceiptService,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private productEntryService: ProductEntryService
    ) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.productEntryService.query().subscribe(
            (res: Response) => { this.productentries = res.json(); }, (res: Response) => this.onError(res.json()));
    }

    load (id) {
        this.receiptService.find(id).subscribe(receipt => {
            this.receipt = receipt;
        });
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

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

}
