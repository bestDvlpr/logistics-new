import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { PayMaster } from './pay-master.model';
import { PayMasterService } from './pay-master.service';

@Component({
    selector: 'jhi-pay-master-detail',
    templateUrl: './pay-master-detail.component.html'
})
export class PayMasterDetailComponent implements OnInit, OnDestroy {

    payMaster: PayMaster;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private payMasterService: PayMasterService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['payMaster']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.payMasterService.find(id).subscribe(payMaster => {
            this.payMaster = payMaster;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
