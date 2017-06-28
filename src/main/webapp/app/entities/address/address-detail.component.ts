import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Address} from './address.model';
import {AddressService} from './address.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-address-detail',
    templateUrl: './address-detail.component.html'
})
export class AddressDetailComponent implements OnInit, OnDestroy {

    address: Address;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private addressService: AddressService,
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
        this.addressService.find(id).subscribe((address) => {
            this.address = address;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
