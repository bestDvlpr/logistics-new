import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LoyaltyCardComponent } from './loyalty-card.component';
import { LoyaltyCardDetailComponent } from './loyalty-card-detail.component';
import { LoyaltyCardPopupComponent } from './loyalty-card-dialog.component';
import { LoyaltyCardDeletePopupComponent } from './loyalty-card-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class LoyaltyCardResolvePagingParams implements Resolve<any> {

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

export const loyaltyCardRoute: Routes = [
  {
    path: 'loyalty-card',
    component: LoyaltyCardComponent,
    resolve: {
      'pagingParams': LoyaltyCardResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.loyaltyCard.home.title'
    }
  }, {
    path: 'loyalty-card/:id',
    component: LoyaltyCardDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.loyaltyCard.home.title'
    }
  }
];

export const loyaltyCardPopupRoute: Routes = [
  {
    path: 'loyalty-card-new',
    component: LoyaltyCardPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.loyaltyCard.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'loyalty-card/:id/edit',
    component: LoyaltyCardPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.loyaltyCard.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'loyalty-card/:id/delete',
    component: LoyaltyCardDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.loyaltyCard.home.title'
    },
    outlet: 'popup'
  }
];
