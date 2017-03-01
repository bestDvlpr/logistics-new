import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { LoyaltyCard } from './loyalty-card.model';
import { LoyaltyCardService } from './loyalty-card.service';

@Component({
    selector: 'jhi-loyalty-card-detail',
    templateUrl: './loyalty-card-detail.component.html'
})
export class LoyaltyCardDetailComponent implements OnInit, OnDestroy {

    loyaltyCard: LoyaltyCard;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private loyaltyCardService: LoyaltyCardService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['loyaltyCard']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.loyaltyCardService.find(id).subscribe(loyaltyCard => {
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
