import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService} from 'ng-jhipster';
import {Receipt, ReceiptStatus} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {Response} from '@angular/http';
import {DataHolderService} from './data-holder.service';
import {EnumAware} from './doctypaware.decorator';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-receipt-detail',
    templateUrl: './receipt-detail.component.html'
})
@EnumAware
export class ReceiptDetailComponent implements OnInit, OnDestroy {

    receipt: Receipt;
    private subscription: any;
    deliveredTime: any;
    deliveredDate: any;
    receiptStatusEnum = ReceiptStatus;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private receiptService: ReceiptService,
                private router: Router,
                private alertService: JhiAlertService,
                private dataHolderService: DataHolderService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(id) {
        this.receiptService.find(id).subscribe((receipt) => {
            this.receipt = receipt;
            this.dataHolderService._receipt = this.receipt;
            this.dataHolderService._client = this.receipt.client;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    takenOut() {
        this.receiptService.takenOut(this.receipt).subscribe(
            (res: Response) => this.onSuccess(res.json()),
            (res: Response) => this.onError(res.json())
        );
    }

    delivered() {

        if (this.deliveredTime !== null && this.deliveredDate !== null) {
            const formattedTime = ((this.deliveredTime.hour < 10) ? '0' + this.deliveredTime.hour : this.deliveredTime.hour) +
                ':' + ((this.deliveredTime.minute < 10) ? '0' + this.deliveredTime.minute : this.deliveredTime.minute) +
                ':00';
            const formattedDate = this.deliveredDate.year +
                '-' + ((this.deliveredDate.month < 10) ? '0' + this.deliveredDate.month : this.deliveredDate.month) +
                '-' + ((this.deliveredDate.day < 10) ? '0' + this.deliveredDate.day : this.deliveredDate.day);
            this.receipt.deliveredDateTime = formattedDate + ' ' + formattedTime;
        }
        this.receiptService.delivered(this.receipt).subscribe(
            (res: Response) => this.onSuccess(res.json()),
            (res: Response) => this.onError(res.json())
        );
    }

    private onSuccess(res: Receipt) {
        if (res !== null) {
            this.router.navigate(['../../receipt']);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
