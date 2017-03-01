import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ReceiptStatusComponent } from './receipt-status.component';
import { ReceiptStatusDetailComponent } from './receipt-status-detail.component';
import { ReceiptStatusPopupComponent } from './receipt-status-dialog.component';
import { ReceiptStatusDeletePopupComponent } from './receipt-status-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ReceiptStatusResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

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

export const receiptStatusRoute: Routes = [
  {
    path: 'receipt-status',
    component: ReceiptStatusComponent,
    resolve: {
      'pagingParams': ReceiptStatusResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.receiptStatus.home.title'
    }
  }, {
    path: 'receipt-status/:id',
    component: ReceiptStatusDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.receiptStatus.home.title'
    }
  }
];

export const receiptStatusPopupRoute: Routes = [
  {
    path: 'receipt-status-new',
    component: ReceiptStatusPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.receiptStatus.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'receipt-status/:id/edit',
    component: ReceiptStatusPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.receiptStatus.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'receipt-status/:id/delete',
    component: ReceiptStatusDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.receiptStatus.home.title'
    },
    outlet: 'popup'
  }
];
