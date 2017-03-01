import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PayTypeComponent } from './pay-type.component';
import { PayTypeDetailComponent } from './pay-type-detail.component';
import { PayTypePopupComponent } from './pay-type-dialog.component';
import { PayTypeDeletePopupComponent } from './pay-type-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PayTypeResolvePagingParams implements Resolve<any> {

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

export const payTypeRoute: Routes = [
  {
    path: 'pay-type',
    component: PayTypeComponent,
    resolve: {
      'pagingParams': PayTypeResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payType.home.title'
    }
  }, {
    path: 'pay-type/:id',
    component: PayTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payType.home.title'
    }
  }
];

export const payTypePopupRoute: Routes = [
  {
    path: 'pay-type-new',
    component: PayTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'pay-type/:id/edit',
    component: PayTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payType.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'pay-type/:id/delete',
    component: PayTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payType.home.title'
    },
    outlet: 'popup'
  }
];
