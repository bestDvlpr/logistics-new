import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ShopComponent } from './shop.component';
import { ShopDetailComponent } from './shop-detail.component';
import { ShopPopupComponent } from './shop-dialog.component';
import { ShopDeletePopupComponent } from './shop-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ShopResolvePagingParams implements Resolve<any> {

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

export const shopRoute: Routes = [
  {
    path: 'shop',
    component: ShopComponent,
    resolve: {
      'pagingParams': ShopResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.shop.home.title'
    }
  }, {
    path: 'shop/:id',
    component: ShopDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.shop.home.title'
    }
  }
];

export const shopPopupRoute: Routes = [
  {
    path: 'shop-new',
    component: ShopPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.shop.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'shop/:id/edit',
    component: ShopPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.shop.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'shop/:id/delete',
    component: ShopDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.shop.home.title'
    },
    outlet: 'popup'
  }
];
