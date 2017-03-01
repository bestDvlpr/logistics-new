import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PaymentTypeComponent } from './payment-type.component';
import { PaymentTypeDetailComponent } from './payment-type-detail.component';
import { PaymentTypePopupComponent } from './payment-type-dialog.component';
import { PaymentTypeDeletePopupComponent } from './payment-type-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PaymentTypeResolvePagingParams implements Resolve<any> {

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

export const paymentTypeRoute: Routes = [
  {
    path: 'payment-type',
    component: PaymentTypeComponent,
    resolve: {
      'pagingParams': PaymentTypeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.paymentType.home.title'
    }
  }, {
    path: 'payment-type/:id',
    component: PaymentTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.paymentType.home.title'
    }
  }
];

export const paymentTypePopupRoute: Routes = [
  {
    path: 'payment-type-new',
    component: PaymentTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.paymentType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'payment-type/:id/edit',
    component: PaymentTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.paymentType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'payment-type/:id/delete',
    component: PaymentTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.paymentType.home.title'
    },
    outlet: 'popup'
  }
];
