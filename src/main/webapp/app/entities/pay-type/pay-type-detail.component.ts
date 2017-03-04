import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { PayType } from './pay-type.model';
import { PayTypeService } from './pay-type.service';

@Component({
    selector: 'jhi-pay-type-detail',
    templateUrl: './pay-type-detail.component.html'
})
export class PayTypeDetailComponent implements OnInit, OnDestroy {

    payType: PayType;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private payTypeService: PayTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['payType', 'paymentType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.payTypeService.find(id).subscribe(payType => {
            this.payType = payType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
