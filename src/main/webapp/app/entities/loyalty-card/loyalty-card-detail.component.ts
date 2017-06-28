import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {LoyaltyCard} from './loyalty-card.model';
import {LoyaltyCardService} from './loyalty-card.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-loyalty-card-detail',
    templateUrl: './loyalty-card-detail.component.html'
})
export class LoyaltyCardDetailComponent implements OnInit, OnDestroy {

    loyaltyCard: LoyaltyCard;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private loyaltyCardService: LoyaltyCardService,
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
        this.loyaltyCardService.find(id).subscribe((loyaltyCard) => {
            this.loyaltyCard = loyaltyCard;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
