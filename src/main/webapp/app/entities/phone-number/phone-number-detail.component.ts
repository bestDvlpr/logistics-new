import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PhoneNumber} from './phone-number.model';
import {PhoneNumberService} from './phone-number.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-phone-number-detail',
    templateUrl: './phone-number-detail.component.html'
})
export class PhoneNumberDetailComponent implements OnInit, OnDestroy {

    phoneNumber: PhoneNumber;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private phoneNumberService: PhoneNumberService,
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
        this.phoneNumberService.find(id).subscribe((phoneNumber) => {
            this.phoneNumber = phoneNumber;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
