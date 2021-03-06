import {Component, OnDestroy, OnInit} from '@angular/core';
import {Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from 'ng-jhipster';

import {DocType, Receipt, ReceiptStatus, WholeSaleFlag} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';
import {EnumAware} from './doctypaware.decorator';
import {DataHolderService} from './data-holder.service';
import {isUndefined} from 'util';
import * as FileSaver from 'file-saver';
import {UploadService} from './upload.service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-receipt',
    templateUrl: './receipt.component.html'
})
@EnumAware
export class ReceiptComponent implements OnInit, OnDestroy {

    currentAccount: any;
    receipts: Receipt[];
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
    uploadForm = false;
    receiptStatusEnum = ReceiptStatus;
    docTypeEnum = DocType;
    wholeSaleFlagEnum = WholeSaleFlag;
    isDCEmployee = false;
    isAdmin = false;
    receiptFile: any;
    isWarehouseUser = false;
    receipt: Receipt;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private receiptService: ReceiptService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private uploadService: UploadService,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private dataHolderService: DataHolderService,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAccepted() {
        this.receiptService.accepted({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );

        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    loadAll() {
        this.receiptService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    loadAllByCompanyId() {
        this.receiptService.allByCompanyId({
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
        this.isDCEmployee = false;
        const authorities = this.currentAccount.authorities;
        for (const auth of authorities) {
            if (auth === 'ROLE_ADMIN' ||
                auth === 'ROLE_MANAGER' ||
                auth === 'ROLE_DISPATCHER') {
                this.isDCEmployee = true;
                if (auth === 'ROLE_ADMIN') {
                    this.isAdmin = true;
                }
            }
        }

        if (this.isAdmin) {
            this.router.navigate(['/receipt'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
            });
            this.loadAll();
        } else if (this.isDCEmployee) {
            this.router.navigate(['/accepted-receipts'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
            });
            this.loadAccepted();
        } else {
            this.router.navigate(['/accepted-receipts'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
            });
            this.loadAllByCompanyId();
        }
    }

    clear() {
        this.page = 0;
        if (this.isAdmin) {
            this.router.navigate(['/receipt'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
            });
            this.loadAll();
        } else {
            this.router.navigate(['/accepted-receipts'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
            });
            this.loadAccepted();
        }
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.isDCEmployee = false;
            const authorities = this.currentAccount.authorities;
            for (const auth of authorities) {
                if (auth === 'ROLE_ADMIN' ||
                    auth === 'ROLE_MANAGER' ||
                    auth === 'ROLE_DISPATCHER') {
                    this.isDCEmployee = true;
                    if (auth === 'ROLE_ADMIN') {
                        this.isAdmin = true;
                    }
                }
                if (auth === 'ROLE_WAREHOUSE') {
                    this.isWarehouseUser = true;
                }
            }
            if (this.isDCEmployee) {
                if (this.isAdmin) {
                    this.loadAll();
                } else {
                    this.loadAccepted();
                }
            } else {
                this.loadAllByCompanyId();
            }
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
        this.isDCEmployee = false;
        if (this.currentAccount === null || isUndefined(this.currentAccount)) {
            this.principal.identity().then((account) => {
                this.currentAccount = account;
                for (const auth of this.currentAccount.authorities) { // todo extract block to a method
                    if (auth === 'ROLE_ADMIN' ||
                        auth === 'ROLE_MANAGER' ||
                        auth === 'ROLE_DISPATCHER') {
                        this.isDCEmployee = true;
                    }
                }

                if (this.isDCEmployee) {
                    if (this.isAdmin) {
                        this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAll());
                    } else {
                        this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAccepted());
                    }
                } else {
                    this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAllByCompanyId());
                }
            });
        } else {
            for (const auth of this.currentAccount.authorities) {
                if (auth === 'ROLE_ADMIN' ||
                    auth === 'ROLE_MANAGER' ||
                    auth === 'ROLE_DISPATCHER') {
                    this.isDCEmployee = true;
                    if (auth === 'ROLE_ADMIN') {
                        this.isAdmin = true;
                    }
                }
            }

            if (this.isDCEmployee) {
                if (this.isAdmin) {
                    this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAll());
                } else {
                    this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAccepted());
                }
            } else {
                this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAllByCompanyId());
            }
        }
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
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
        this.receipts = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    goClientSelectionStep(receiptId: number) {
        this.saveToDataHolder(receiptId);
        this.router.navigate(['../receipt/' + receiptId + '/send/client']);
    }

    attachToDriver(receiptId: number) {
        this.saveToDataHolder(receiptId);
        this.router.navigate(['../receipt-product-to-car']);
    }

    private saveToDataHolder(receiptId: number) {
        this.dataHolderService.clearAll();
        let receipt: Receipt;
        for (const res of this.receipts) {
            if (res.id === receiptId) {
                receipt = res;
            }
        }
        for (const prod of receipt.productEntries) {
            prod.attachedCarId = null;
            prod.attachedCarNumber = null;
            prod.attachedToCarTime = null;
            prod.attachedToDriverById = null;
        }
        this.dataHolderService._receipt = receipt;
        if (this.dataHolderService._receipt !== null && this.dataHolderService._receipt.client !== null) {
            this.dataHolderService._client = receipt.client;
        }
    }

    downloadReceipt(receiptId: number) {
        this.receiptService.downloadReceipt(receiptId).subscribe((res: Response) => {
            this.onSuccessDocx(res, receiptId);
        });
    }

    private onSuccessDocx(res: Response, receiptId: number) {
        const mediaType = 'application/octet-stream;charset=UTF-8';
        const blob = new Blob([res.blob()], {type: mediaType});
        const receiptNumber = receiptId + '';
        const filename = receiptNumber + '_invoice.docx';

        try {
            window.navigator.msSaveOrOpenBlob(blob, filename);
        } catch (ex) {
            FileSaver.saveAs(blob, filename);
        }
        this.eventManager.broadcast({name: 'receiptListModification', content: 'OK'});
        // this.router.navigate(['/']);
    }

    toggleUploadForm() {
        this.uploadForm = !this.uploadForm;
    }

    fileChangeEvent(fileInput: any) {
        this.receiptFile = fileInput.target.files[0];
    }

    uploadReceipt() {
        console.log(this.receiptFile);
        const postData = {name: this.receiptFile.name, size: this.receiptFile.size};
        this.uploadService.upload(postData, this.receiptFile).subscribe((res: Response) => {
            this.receipt = res.json();
            this.router.navigate(['/']);
        });
    }
}
