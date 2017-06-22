import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ProductEntry} from "./product-entry.model";
import {ProductEntryService} from "./product-entry.service";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-product-entry-detail',
    templateUrl: './product-entry-detail.component.html'
})
export class ProductEntryDetailComponent implements OnInit, OnDestroy {

    productEntry: ProductEntry;
    private subscription: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private productEntryService: ProductEntryService,
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
        this.productEntryService.find(id).subscribe(productEntry => {
            this.productEntry = productEntry;
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
