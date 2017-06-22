import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {JhiAlertService, JhiParseLinks} from "ng-jhipster";
import {Client} from "./client.model";
import {ClientService} from "./client.service";
import {ReceiptService} from "../receipt/receipt.service";
import {Response} from "@angular/http";
import {DocType, Receipt, ReceiptStatus} from "../receipt/receipt.model";
import {EnumAware} from "../receipt/doctypaware.decorator";
import {JhiLanguageHelper} from "../../shared/language/language.helper";

@Component({
    selector: 'jhi-client-detail',
    templateUrl: './client-detail.component.html'
})
@EnumAware
export class ClientDetailComponent implements OnInit, OnDestroy {

    client: Client;
    routeData: any;
    private subscription: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    itemsPerPage: any;
    link: any;
    totalItems: any;
    queryCount: any;
    receiptStatusEnum = ReceiptStatus;
    docTypeEnum = DocType;
    links: any;
    receipts: Receipt[];
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private clientService: ClientService,
                private route: ActivatedRoute,
                private parseLinks: JhiParseLinks,
                private activatedRoute: ActivatedRoute,
                private alertService: JhiAlertService,
                private receiptService: ReceiptService,
                private router: Router) {
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
            this.loadSalesHistory(params['id']);
        });
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(id) {
        this.clientService.find(id).subscribe(client => {
            this.client = client;
        });
    }

    loadSalesHistory(id) {
        this.receiptService.getByClientId(id, {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json()));
    }

    sort() {
        let result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.receipts = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackId(index: number, item: Receipt) {
        return item.id;
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['../client', this.client.id], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadSalesHistory(this.client.id);
    }
}
