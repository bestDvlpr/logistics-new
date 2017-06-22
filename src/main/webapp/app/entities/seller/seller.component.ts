import {Component, OnDestroy, OnInit} from "@angular/core";
import {Response} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs/Rx";
import {
    JhiAlertService,
    JhiEventManager,
    JhiPaginationUtil,
    JhiParseLinks
} from "ng-jhipster";

import {Seller} from "./seller.model";
import {SellerService} from "./seller.service";
import {ITEMS_PER_PAGE, Principal} from "../../shared";
import {PaginationConfig} from "../../blocks/config/uib-pagination.config";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-seller',
    templateUrl: './seller.component.html'
})
export class SellerComponent implements OnInit, OnDestroy {

    currentAccount: any;
    sellers: Seller[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private sellerService: SellerService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAll() {
        this.sellerService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/seller'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/seller', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSellers();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Seller) {
        return item.id;
    }


    registerChangeInSellers() {
        this.eventSubscriber = this.eventManager.subscribe('sellerListModification', (response) => this.loadAll());
    }

    sort() {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.sellers = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
