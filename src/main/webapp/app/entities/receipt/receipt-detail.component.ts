import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import {Receipt} from './receipt.model';
import {ReceiptService} from './receipt.service';

@Component({
    selector: 'jhi-receipt-detail',
    templateUrl: './receipt-detail.component.html'
})
export class ReceiptDetailComponent implements OnInit, OnDestroy {

    receipt: Receipt;
    private subscription: any;

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                private route: ActivatedRoute) {
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'receiptStatus']);
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
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    printBlock(): void {
        let printContents, popupWin;
        printContents = document.getElementById('print-section').innerHTML;
        popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
        popupWin.document.open();
        popupWin.document.write(
            `<html>
                <head>
                    <base href="/" />
                    <meta charset="utf-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <title>Print tab</title>
                    <meta name="description" content="">
                    <meta name="google" value="notranslate">
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                </head>
                <body onload="window.print();window.close()">${printContents}</body>
              </html>`
        );
        popupWin.document.close();
    }

}
