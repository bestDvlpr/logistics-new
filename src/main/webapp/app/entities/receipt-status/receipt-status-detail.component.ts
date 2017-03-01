import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ReceiptStatus } from './receipt-status.model';
import { ReceiptStatusService } from './receipt-status.service';

@Component({
    selector: 'jhi-receipt-status-detail',
    templateUrl: './receipt-status-detail.component.html'
})
export class ReceiptStatusDetailComponent implements OnInit, OnDestroy {

    receiptStatus: ReceiptStatus;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private receiptStatusService: ReceiptStatusService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['receiptStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.receiptStatusService.find(id).subscribe(receiptStatus => {
            this.receiptStatus = receiptStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
