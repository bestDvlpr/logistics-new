import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PhoneNumberComponent } from './phone-number.component';
import { PhoneNumberDetailComponent } from './phone-number-detail.component';
import { PhoneNumberPopupComponent } from './phone-number-dialog.component';
import { PhoneNumberDeletePopupComponent } from './phone-number-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PhoneNumberResolvePagingParams implements Resolve<any> {

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

export const phoneNumberRoute: Routes = [
  {
    path: 'phone-number',
    component: PhoneNumberComponent,
    resolve: {
      'pagingParams': PhoneNumberResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.phoneNumber.home.title'
    }
  }, {
    path: 'phone-number/:id',
    component: PhoneNumberDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.phoneNumber.home.title'
    }
  }
];

export const phoneNumberPopupRoute: Routes = [
  {
    path: 'phone-number-new',
    component: PhoneNumberPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.phoneNumber.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'phone-number/:id/edit',
    component: PhoneNumberPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.phoneNumber.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'phone-number/:id/delete',
    component: PhoneNumberDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'logisticsApp.phoneNumber.home.title'
    },
    outlet: 'popup'
  }
];
