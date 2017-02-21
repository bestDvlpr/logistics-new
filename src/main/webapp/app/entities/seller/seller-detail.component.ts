import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Seller } from './seller.model';
import { SellerService } from './seller.service';

@Component({
    selector: 'jhi-seller-detail',
    templateUrl: './seller-detail.component.html'
})
export class SellerDetailComponent implements OnInit, OnDestroy {

    seller: Seller;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sellerService: SellerService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['seller']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.sellerService.find(id).subscribe(seller => {
            this.seller = seller;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
