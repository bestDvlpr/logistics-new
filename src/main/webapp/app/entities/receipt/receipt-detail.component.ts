import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Receipt } from './receipt.model';
import { ReceiptService } from './receipt.service';

@Component({
    selector: 'jhi-receipt-detail',
    templateUrl: './receipt-detail.component.html'
})
export class ReceiptDetailComponent implements OnInit, OnDestroy {

    receipt: Receipt;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptService: ReceiptService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'receiptStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
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

}
