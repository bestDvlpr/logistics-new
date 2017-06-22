import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Seller} from "./seller.model";
import {SellerService} from "./seller.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-seller-detail',
    templateUrl: './seller-detail.component.html'
})
export class SellerDetailComponent implements OnInit, OnDestroy {

    seller: Seller;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private sellerService: SellerService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(id) {
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
