import {Component, OnInit, OnDestroy} from '@angular/core';
import {Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService} from 'ng-jhipster';

import {Receipt, ReceiptStatus} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {DocTypeEnumAware} from './doctypaware.decorator';
import {DataHolderService} from './data-holder.service';

@Component({
    selector: 'jhi-receipt-applied',
    templateUrl: './receipt-applied.component.html'
})
@DocTypeEnumAware
export class ReceiptAppliedComponent implements OnInit, OnDestroy {

    currentAccount: any;
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
    receiptStatusEnum = ReceiptStatus;
    appliedReceipts: Receipt[];

    constructor(private jhiLanguageService: JhiLanguageService,
                private receiptService: ReceiptService,
                private parseLinks: ParseLinks,
                private alertService: AlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: EventManager,
                private dataHolderService: DataHolderService) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
        this.jhiLanguageService.setLocations(['receipt', 'docType', 'wholeSaleFlag', 'receiptStatus']);
    }

    loadAllApplied() {
        this.receiptService.appliedReceipts({
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
        this.router.navigate(['/receipt'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAllApplied();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/receipt', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAllApplied();
    }

    ngOnInit() {
        this.loadAllApplied();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReceipts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Receipt) {
        return item.id;
    }


    registerChangeInReceipts() {
        this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAllApplied());
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
        this.appliedReceipts = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    public goClientSelectionStep(receiptId: number) {
        this.dataHolderService.clearAll();
        let receipt: Receipt;
        for (let res of this.appliedReceipts) {
            if (res.id === receiptId) {
                receipt = res;
            }
        }
        this.dataHolderService._receipt = receipt;
        this.router.navigate(['../receipt/' + receiptId + '/send/client']);
    }

    public attachToDriver() {

    }
}
