import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PayType} from './pay-type.model';
import {PayTypeService} from './pay-type.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-pay-type-detail',
    templateUrl: './pay-type-detail.component.html'
})
export class PayTypeDetailComponent implements OnInit, OnDestroy {

    payType: PayType;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private payTypeService: PayTypeService,
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
        this.payTypeService.find(id).subscribe((payType) => {
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
