import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PayMaster} from './pay-master.model';
import {PayMasterService} from './pay-master.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-pay-master-detail',
    templateUrl: './pay-master-detail.component.html'
})
export class PayMasterDetailComponent implements OnInit, OnDestroy {

    payMaster: PayMaster;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private payMasterService: PayMasterService,
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
        this.payMasterService.find(id).subscribe((payMaster) => {
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
