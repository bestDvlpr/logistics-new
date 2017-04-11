import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ProductEntry } from './product-entry.model';
import { ProductEntryService } from './product-entry.service';

@Component({
    selector: 'jhi-product-entry-detail',
    templateUrl: './product-entry-detail.component.html'
})
export class ProductEntryDetailComponent implements OnInit, OnDestroy {

    productEntry: ProductEntry;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private productEntryService: ProductEntryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(
            ['productEntry', 'salesType', 'salesPlace', 'defectFlag', 'virtualFlag', 'receiptStatus', 'shop']
        );
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
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
