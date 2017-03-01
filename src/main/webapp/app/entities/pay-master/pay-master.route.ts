import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PayMasterComponent } from './pay-master.component';
import { PayMasterDetailComponent } from './pay-master-detail.component';
import { PayMasterPopupComponent } from './pay-master-dialog.component';
import { PayMasterDeletePopupComponent } from './pay-master-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PayMasterResolvePagingParams implements Resolve<any> {

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

export const payMasterRoute: Routes = [
  {
    path: 'pay-master',
    component: PayMasterComponent,
    resolve: {
      'pagingParams': PayMasterResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payMaster.home.title'
    }
  }, {
    path: 'pay-master/:id',
    component: PayMasterDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payMaster.home.title'
    }
  }
];

export const payMasterPopupRoute: Routes = [
  {
    path: 'pay-master-new',
    component: PayMasterPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payMaster.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'pay-master/:id/edit',
    component: PayMasterPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payMaster.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'pay-master/:id/delete',
    component: PayMasterDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.payMaster.home.title'
    },
    outlet: 'popup'
  }
];
