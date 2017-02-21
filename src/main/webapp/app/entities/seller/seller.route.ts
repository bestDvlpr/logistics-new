import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SellerComponent } from './seller.component';
import { SellerDetailComponent } from './seller-detail.component';
import { SellerPopupComponent } from './seller-dialog.component';
import { SellerDeletePopupComponent } from './seller-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class SellerResolvePagingParams implements Resolve<any> {

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

export const sellerRoute: Routes = [
  {
    path: 'seller',
    component: SellerComponent,
    resolve: {
      'pagingParams': SellerResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.seller.home.title'
    }
  }, {
    path: 'seller/:id',
    component: SellerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.seller.home.title'
    }
  }
];

export const sellerPopupRoute: Routes = [
  {
    path: 'seller-new',
    component: SellerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.seller.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'seller/:id/edit',
    component: SellerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.seller.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'seller/:id/delete',
    component: SellerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.seller.home.title'
    },
    outlet: 'popup'
  }
];
