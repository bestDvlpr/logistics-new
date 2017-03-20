import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {PaginationUtil} from 'ng-jhipster';

import {ReceiptComponent} from './receipt.component';
import {ReceiptDetailComponent} from './receipt-detail.component';
import {ReceiptPopupComponent} from './receipt-dialog.component';
import {ReceiptDeletePopupComponent} from './receipt-delete-dialog.component';
import {ReceiptSendClientComponent} from './receipt-send-client.component';

import {Principal} from '../../shared';
import {ReceiptSendAddressComponent} from './receipt-send-address.component';
import {ReceiptSendProductComponent} from './receipt-send-product.component';
import {ClientPopupComponent} from '../client/client-dialog.component';
import {ReceiptNewComponent} from './receipt-new.component';
import {ReceiptAppliedComponent} from './receipt-applied.component';
import {ReceiptProductToCarComponent} from './receipt-product-to-car.component';

@Injectable()
export class ReceiptResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const receiptRoute: Routes = [
    {
        path: 'receipt',
        component: ReceiptComponent,
        resolve: {
            'pagingParams': ReceiptResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'new-receipts',
        component: ReceiptNewComponent,
        resolve: {
            'pagingParams': ReceiptResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'applied-receipts',
        component: ReceiptAppliedComponent,
        resolve: {
            'pagingParams': ReceiptResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'receipt/:id',
        component: ReceiptDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'receipt/:id/send/client',
        component: ReceiptSendClientComponent,
        data: {
            authorities: ['ROLE_CASHIER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'receipt-send-address',
        component: ReceiptSendAddressComponent,
        data: {
            authorities: ['ROLE_CASHIER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'receipt-send-product',
        component: ReceiptSendProductComponent,
        data: {
            authorities: ['ROLE_CASHIER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }, {
        path: 'receipt-product-to-car',
        component: ReceiptProductToCarComponent,
        data: {
            authorities: ['ROLE_CASHIER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        }
    }
];

export const receiptPopupRoute: Routes = [
    {
        path: 'receipt-new',
        component: ReceiptPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'receipt/:id/edit',
        component: ReceiptPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'receipt/:id/delete',
        component: ReceiptDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.receipt.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'receipt/client-new',
        component: ClientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'logisticsApp.client.home.title'
        },
        outlet: 'popup'
    }
];
