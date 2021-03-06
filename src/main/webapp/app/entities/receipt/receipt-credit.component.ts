import {Component, OnDestroy, OnInit} from '@angular/core';
import {Headers, Response} from '@angular/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {DocType, Receipt, ReceiptStatus, WholeSaleFlag} from './receipt.model';
import {ReceiptService} from './receipt.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';
import {EnumAware} from './doctypaware.decorator';
import {DataHolderService} from './data-holder.service';
import {Car} from '../car/car.model';
import {ACElement} from '../../shared/autocomplete/element.model';
import {CarService} from '../car/car.service';
import * as FileSaver from 'file-saver';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-receipt-credit',
    templateUrl: './receipt-credit.component.html'
})
@EnumAware
export class ReceiptCreditComponent implements OnInit, OnDestroy {

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
    docTypeEnum = DocType;
    wholeSaleFlagEnum = WholeSaleFlag;
    creditReceipts: Receipt[];
    receipts: Receipt[];
    uploadForm = false;
    receiptFile: any;
    receipt: Receipt;
    isDCEmployee = false;
    docTypeSelected: DocType;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private receiptService: ReceiptService,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private carService: CarService,
                private dataHolderService: DataHolderService,
                private paginationConfig: PaginationConfig) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    loadAllCredited() {
        this.receiptService.creditedReceipts({
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
        this.router.navigate(['/receipt-credit'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAllCredited();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/receipt-credit', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAllCredited();
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            for (const auth of this.currentAccount.authorities) {
                if (auth === 'ROLE_ADMIN' ||
                    auth === 'ROLE_MANAGER' ||
                    auth === 'ROLE_DISPATCHER') {
                    this.isDCEmployee = true;
                }
            }
            this.loadAllCredited();
        });
        this.registerChangeInReceipts();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Receipt) {
        return item.id;
    }

    registerChangeInReceipts() {
        this.eventSubscriber = this.eventManager.subscribe('receiptListModification', (response) => this.loadAllCredited());
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
        this.creditReceipts = [];
        this.creditReceipts = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    loadCars() {
        this.carService.allWithoutPagination().subscribe((cars: Response) => {
            this.setACObjects(cars.json());
            this.router.navigate(['receipt-product-to-car']);
        });
    }

    private setACObjects(cars: Car[]) {
        if (cars !== null && cars.length > 0) {
            const acObjects: ACElement[] = [];
            for (const car of cars) {
                const elem: ACElement = {};
                elem.name = car.number;
                elem.id = car.id;
                acObjects.push(elem);
            }
            this.dataHolderService._autocompleteObjects = acObjects;
        }
    }

    attachToDriver(receiptId: number) {
        this.saveToDataHolder(receiptId);
        this.loadCars();
    }

    private saveToDataHolder(receiptId: number) {
        this.dataHolderService.clearAll();
        let receipt: Receipt;
        for (const res of this.creditReceipts) {
            if (res.id === receiptId) {
                receipt = res;
            }
        }
        this.dataHolderService._receipt = receipt;
        if (this.dataHolderService._receipt !== null && this.dataHolderService._receipt.client !== null) {
            this.dataHolderService._client = receipt.client;
        }
    }

    viewReceipt(receiptId: number) {
        for (const receipt of this.creditReceipts) {
            if (receipt.id === receiptId) {
                this.dataHolderService._receipt = receipt;
                this.dataHolderService._client = receipt.client;
            }
        }
        this.router.navigate(['../receipt', receiptId]);
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
        const headers = new Headers();
        headers.append('Content-Type', 'multipart/form-data');
        headers.append('Accept', 'application/json');

        const formData: FormData = new FormData();
        formData.append('file', this.receiptFile, this.receiptFile.name);

        // For multiple files
        // for (let i = 0; i < files.length; i++) {
        //     formData.append(`files[]`, files[i], files[i].name);
        // }

        if (postData !== undefined && postData !== null) {
            for (const property in postData) {
                if (postData.hasOwnProperty(property)) {
                    formData.append(property, postData[property]);
                }
            }
        }
        this.receiptService.uploadReceipt(formData, this.docTypeSelected, '').subscribe((res: Response) => {
            this.receipt = res.json();
            this.router.navigate(['/']);
        });
    }

    goClientSelectionStep(receiptId: number) {
        this.saveToDataHolder(receiptId);
        this.router.navigate(['../receipt/' + receiptId + '/send/client']);
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
}
