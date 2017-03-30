import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Shop } from './shop.model';
import { ShopService } from './shop.service';

@Component({
    selector: 'jhi-shop-detail',
    templateUrl: './shop-detail.component.html'
})
export class ShopDetailComponent implements OnInit, OnDestroy {

    shop: Shop;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private shopService: ShopService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['shop']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.shopService.find(id).subscribe(shop => {
            this.shop = shop;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
