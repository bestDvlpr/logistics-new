import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { PhoneNumber } from './phone-number.model';
import { PhoneNumberService } from './phone-number.service';

@Component({
    selector: 'jhi-phone-number-detail',
    templateUrl: './phone-number-detail.component.html'
})
export class PhoneNumberDetailComponent implements OnInit, OnDestroy {

    phoneNumber: PhoneNumber;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private phoneNumberService: PhoneNumberService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['phoneNumber']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.phoneNumberService.find(id).subscribe(phoneNumber => {
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
