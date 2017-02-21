import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { PaymentType } from './payment-type.model';
import { PaymentTypeService } from './payment-type.service';

@Component({
    selector: 'jhi-payment-type-detail',
    templateUrl: './payment-type-detail.component.html'
})
export class PaymentTypeDetailComponent implements OnInit, OnDestroy {

    paymentType: PaymentType;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private paymentTypeService: PaymentTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['paymentType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.paymentTypeService.find(id).subscribe(paymentType => {
            this.paymentType = paymentType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
